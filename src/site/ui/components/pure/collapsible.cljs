(ns site.ui.components.pure.collapsible
  (:require [reagent.core :as r]
            [keechma.toolbox.css.core :refer-macros [defelement]]))

(defelement -wrap
            :class [:bd-light-gray :bw1 :rounded :mb2 :mt2 :col-12])

(defelement -header
            :class [:fw-700 :fs-20 :justify-start :flex :.align-center]
            :style [{:text-align "left"
                     :font-weight "normal"}])

(defelement -content-wrap
            :class [:pt1 :col-12 :lg-col-8 :md-col-8 :sm-col-12 :lh-25]
            :style [{:color "rgba(242, 242, 242, 0.5)"}])

(defn render [title & children]
  (let [collapsed?$ (r/atom true)]
    (fn [title & children]
      (let [collapsed? @collapsed?$]
        [-wrap
         [:button.c-white.pl0 {:type     :button
                               :on-click #(swap! collapsed?$ not) :style {:background-color "#03063D" :border "none"}}
          [-header title
           (if collapsed?
             [:i.fas.fa-arrow-down.fs-15.pl2]
             [:i.fas.fa-arrow-up.fs-15.pl2])]]
         (when-not collapsed?
           (into [-content-wrap] children))]))))
