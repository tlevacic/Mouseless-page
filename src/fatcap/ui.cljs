(ns fatcap.ui
  (:require [fatcap.ui.blueprint :as blueprint]))

(defn install
  ([] (install {}))
  ([ui]
   (assoc ui
          :fatcap/blueprint blueprint/component)))
