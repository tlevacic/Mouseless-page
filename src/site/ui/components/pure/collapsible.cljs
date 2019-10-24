(ns site.ui.components.pure.collapsible
  (:require [reagent.core :as r]
            [keechma.toolbox.css.core :refer-macros [defelement]]))

(defelement -wrap
  :class [:bd-light-gray :bw1 :rounded :mb2])

(defelement -header
  :class [:flex :justify-between :items-center :p2])

(defelement -content-wrap
  :class [:bg-lighter-gray :bwt1 :bd-light-gray :p2])

(defn render [title & children]
  (let [collapsed?$ (r/atom false)]
    (fn [title & children]
      (let [collapsed? @collapsed?$]
        [-wrap
         [-header
          title
          [:button
           {:type :button
            :on-click #(swap! collapsed?$ not)}
           (if collapsed? "↓" "↑")]]
         (when-not collapsed?
           (into [-content-wrap] children))]))))
