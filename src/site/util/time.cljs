(ns site.util.time
  (:require [reagent.format :as rf]
            [oops.core :refer [ocall oget]]))

(def moment (oget js/window "moment"))

(defn moment-date [date-str]
  (moment date-str))

(defn format-datetime [date]
  (let [date (moment-date date)]
    (ocall date :format "MM/DD/YYYY HH:mm:ss")))

(defn ->time [datetime]
  (format-datetime datetime))
