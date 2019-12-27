(ns site.ui.components.pure.collapsible
  (:require [reagent.core :as r]
            [keechma.toolbox.css.core :refer-macros [defelement]]))

(defelement -wrap
  :class [:bd-light-gray :bw1 :rounded :mb2 :col-5 :mx2 :px2])

(defelement -header
  :class [:fw-700 :fs-20 :justify-start :flex]
            :style [{:text-align "left"}])

(defelement -content-wrap
  :class [:pt3])

(defn render [title & children]
  (let [collapsed?$ (r/atom true)]
    (fn [title & children]
      (let [collapsed? @collapsed?$]
        [-wrap
         [:button.c-white.pl0 {:type     :button
                           :on-click #(swap! collapsed?$ not) :style {:background-color "#03063D" :border "none"}}
          [-header title]]
         (when-not collapsed?
           (into [-content-wrap] children))]))))
