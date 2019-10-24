(ns fatcap.ui.template
  (:require [clojure.walk :refer [postwalk]]))


(declare extract-slots)

(defn get-slot-subtemplate [slot]
  (let [subtemplate (nth slot 2 nil)]
    (if (symbol? subtemplate)
      `(when (fn? ~subtemplate)
         (::slots (meta ~subtemplate)))
      (extract-slots subtemplate))))


(defn slot? [leaf]
  (and (list? leaf) (= 'slot (first leaf))))

(defn optional-slot? [leaf]
  (and (list? leaf) (= 'optional-slot (first leaf))))


(defn extract-slots 
  ([children] (extract-slots {} children))
  ([slots children]
   (loop [s slots
          c children]
     (let [f (first c)]
       (cond
         (or (slot? f) (optional-slot? f))
         (recur (assoc s (second f) (get-slot-subtemplate f)) (rest c))

         (vector? f)
         (recur (merge s (extract-slots s f)) (rest c))
         
         (map? f)
         (recur (merge s (extract-slots s (vals f))) (rest c))

         (nil? f) (if (empty? s) nil s)

         :else (recur s (rest c)))))))

(defn wrap-slot [ctx-sym optional-slot? config-sym [_ slot-name & children]]
  `(let [~ctx-sym (fatcap.ui.template/track-path (assoc ~ctx-sym ::optional? ~optional-slot?) ~slot-name)]
     [fatcap.ui.template/fill-slot ~ctx-sym ~config-sym ~@children]))

(defn process-slots [ctx-sym config-sym children]
  (postwalk
   (fn [leaf]
     (let [s? (slot? leaf)
           os? (optional-slot? leaf)]
       (if (or s? os?) 
         (wrap-slot ctx-sym os? config-sym leaf)
         leaf)))
   children))

(defn ensure-args [args]
  (let [args-count (count args)]
    (case args-count
      0 '[ctx config]
      1 (conj args 'config)
      args)))

(defmacro deftemplate [fname args & children]
  (let [args' (ensure-args args)
        ctx-sym (first args')
        config-sym (second args')
        slots (extract-slots children)
        body (process-slots ctx-sym config-sym children)]
    `(def ~fname (fatcap.ui.template/wrap-template (fn ~args' ~@body) ~slots))))
