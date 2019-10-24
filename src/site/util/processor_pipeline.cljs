(ns site.util.processor-pipeline
  (:require [com.rpl.specter :as s]))


(defn select-all [selector]
  [:op/select-all (if (vector? selector) selector [selector])])

(defn select [selector]
  [:op/select (if (vector? selector) selector [selector])])

(defn select-not [selector]
  [:op/select-not (if (vector? selector) selector [selector])])

(defn all [& ops]
  (into [:op/all] ops))

(defn any [& ops]
  (into [:op/any] ops))

(defn process [process-fn]
  [:op/process process-fn])

(defn guard
  ([op]
   [:op/guard op])
  ([g-op op]
   [:op/guard g-op op]))

(defn guard-not
  ([op]
   [:op/guard-not op])
  ([g-op op]
   [:op/guard-not g-op op]))

(defn ensure [op]
  [:op/guard op])

(defn ensure-not [op]
  [:op/guard-not op])

(defn op? [tree]
  (if (vector? tree)
    (let [first-el (first tree)
          first-el-keyword? (keyword? first-el)]
      (and first-el-keyword? (= "op" (namespace first-el))))
    false))

(defn result? [val]
  (and (not (nil? val))
       (not= ::unprocessable val)))

(declare apply-ops)

(defn apply-select-all [args [op-args]]
  (let [res (s/select op-args args)]
    (if (seq res)
      res
      nil)))

(defn apply-select [args [op-args]]
  (s/select-one op-args args))

(defn apply-select-not [args [op-args]]
  (if (s/select-one op-args args)
    ::unprocessable
    true))

(defn apply-all [args op-args]
  (let [res (map #(apply-ops args %) op-args)]
    (if (every? result? res)
      res
      ::unprocessable)))

(defn apply-any [args op-args]
  (first (filter result? (map #(apply-ops args %) op-args))))

(defn apply-process [args [op-processor]]
  (op-processor args))

(defn apply-guard [args op-args]
  (if (= 1 (count op-args))
    (let [res (apply-ops args (first op-args))]
      (if (result? res)
        res
        ::unprocessable))
    (let [guard-res (apply-ops args (first op-args))
          res (apply-ops args (last op-args))]
      (if (and (result? guard-res)
               (result? res))
        res
        ::unprocessable))))

(defn apply-guard-not [args op-args]
  (if (= 1 (count op-args))
    (let [res (apply-ops args (first op-args))]
      (if (not (result? res))
        res
        ::unprocessable))
    (let [guard-res (apply-ops args (first op-args))
          res (apply-ops args (last op-args))]
      (if (not (and (result? guard-res)
                    (result? res)))
        res
        ::unprocessable))))

(defn apply-op [args op]
  (let [op-type    (first op)
        op-args    (rest op)
        op-applier (case op-type
                     :op/guard      apply-guard
                     :op/guard-not  apply-guard-not
                     :op/all        apply-all
                     :op/any        apply-any
                     :op/select     apply-select
                     :op/select-not apply-select-not
                     :op/select-all apply-select-all
                     :op/process    apply-process
                     nil)]
    (if op-applier
      (op-applier args op-args)
      ::unprocessable)))

(defn apply-ops [args leaf]
  (cond
    (map? leaf)
    (reduce-kv
     (fn [m k v] 
       (let [res (apply-ops args v)]
         (if (= ::unprocessable res)
           (reduced ::unprocessable)
           (assoc m k res))))
     {}
     leaf)
    
    (op? leaf) 
    (apply-op args leaf)

    (vector? leaf) 
    (reduce
     (fn [acc v]
       (let [res (apply-ops args v)]
         (if (= ::unprocessable res)
           (reduced ::unprocessable)
           (conj acc res))))
     []
     leaf) 
    
    (and (fn? leaf) (::pipeline? (meta leaf)))
    (leaf args)

    :else leaf))

(defn pipeline [& steps]
  (with-meta
    (fn [args]
      (loop [steps steps
             result args]
        (let [step (first steps)]
          (if step
            (let [step-result (apply-ops result step)]
              (if (= ::unprocessable step-result)
                ::unprocessable
                (recur (rest steps) step-result)))
            result))))
    {::pipeline? true}))

(defn params-pipeline [& steps]
  (let [pipeline-runner (apply pipeline steps)]
    (fn [prev route deps]
      (let [r (pipeline-runner {:prev prev :route route :deps deps})]
        (if (= ::unprocessable r) nil r)))))
