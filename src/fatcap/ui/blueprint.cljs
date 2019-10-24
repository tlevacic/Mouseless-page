(ns fatcap.ui.blueprint
  (:require [fatcap.ui-component :as ui]
            [fatcap.util.ui :refer [get-config]]
            [fatcap.util :refer [as-vec]]))

(defn select-children [[start end] children]
  (let [c-count (count children)]
    (if (and start end)
      (cond
        (and (pos? start) (pos? end))
        (subvec children start end)

        (and (pos? start) (neg? end))
        (subvec children start (+ c-count end))
        
        (and (pos? start) (zero? end))
        (subvec children start c-count)

        :else [])
      (cond
        (pos? start)
        (subvec children 0 (max 0 start))
        
        (neg? start)
        (subvec children (max 0 (+ c-count start)) c-count)

        :else []))))

(defn partition-children [ctx partition children]
  (if partition
    (mapv
     (fn [p]
       (let [[el _] p
             selector (vec (drop 1 p))
             selected (select-children selector children)]
         (if (fn? el)
           (into [el ctx] selected)
           (into (as-vec el) selected))))
     partition)
    children))

(defn render [ctx config & children]
  (let [partition (get-config ctx config :partition)
        wrap-el (or (get-config ctx config :wrap) :div)
        partitioned (partition-children ctx partition (vec children))]
    (if (fn? wrap-el)
      (into [wrap-el ctx] partitioned)
      (into (as-vec wrap-el) partitioned))))

(def component
  (ui/constructor 
   {:renderer render}
   {:wrap :<>}))
