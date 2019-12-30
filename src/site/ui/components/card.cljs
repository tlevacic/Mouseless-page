(ns site.ui.components.card
  (:require [keechma.ui-component :as ui]
            [site.ui.components.pure.elements :as e]))


(defn render-cards [data]
  (let [text (:text data)
        title (:title data)
        img (:img data)
        order (:order data)]
    (if (= order 1)
      (do
        [:div.col-12.mt4
        [:div.max-width-4.mx-auto.flex.flex-row.flex-wrap
         [e/-card-wrap
          [e/-image-div-size
           [e/-card-picture {:src img}]]]
         [:div.lg-col-6.md-col-6.sm-col-12.col-11.mx-auto
          [e/-text-div-size
           [e/-card-title title]
           [e/-text {:style {:color "hsla(0,0%,94.9%,.5)"}} text]]]]])
      (do
        [:div.col-12.mt4
        [:div.max-width-4.mx-auto.flex.flex-row.flex-wrap
         [:div.lg-col-6.md-col-6.sm-col-12.col-11.mx-auto
          [e/-text-div-size
           [e/-card-title title]
           [e/-text {:style {:color "hsla(0,0%,94.9%,.5)"}} text]]]
         [e/-card-wrap {:class ["justify-end"]}
          [e/-image-div-size
           [e/-card-picture {:src img}]]]]]
       ))))
