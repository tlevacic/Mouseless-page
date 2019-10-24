(ns fatcap.ui.composition
  (:require [keechma.ui-component :as ui]
            [fatcap.ui-component :as fcui]
            [fatcap.util.ui :refer [get-config]]))

(defn render [ctx config]
  (let [components (get-config ctx config :components)]
    (into [(ui/component ctx :fatcap/blueprint) {}]
          (map
           (fn [c]
             (cond
               (keyword? c) [(ui/component ctx c)]
               (fn? c) [c ctx]
               :else c))
           components))))

(defn compose [& components]
  (fcui/constructor
   {:renderer render
    :component-deps (into [:fatcap/blueprint] 
                          (filter keyword? components))}
   {:components (vec components)}))
