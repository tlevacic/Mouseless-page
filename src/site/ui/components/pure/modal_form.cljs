(ns site.ui.components.pure.modal-form
  (:require [site.ui.components.pure.modal :as modal]
            [keechma.toolbox.css.core :refer-macros [defelement]]))

(defelement -wrap
  :style [{:width "600px"
           :max-height "80vh"}])
(defelement -header
  :class [:flex :justify-between :p2])

(defelement -content-wrap
  :class [:bg-lighter-gray :bd-gray :bwt1])

(defn render [props & children]
  [modal/render props
   [-wrap
    [-header
     [:h3 (:modal/title props)]
     (when-let [on-exit (:on-exit props)]
       [:button {:type :button :on-click (:on-exit props)} "×"])]
    (into [-content-wrap] children)]])
