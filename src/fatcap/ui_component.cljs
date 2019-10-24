(ns fatcap.ui-component
  (:require [keechma.ui-component :as ui]))

(defn constructor
  ([component]
   (constructor component {}))
  ([component config]
   (assoc (ui/constructor component)
          :fatcap.ui/config config)))
