(ns site.util.currency
  (:require [reagent.format :as rf]))

(def currency-symbols
  {"USD" "$"})

(defn ->symbol [currency]
  (get currency-symbols currency currency))

(defn format [value]
  (when value
    (rf/format  "%.2f" value)))
