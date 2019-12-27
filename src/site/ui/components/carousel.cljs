(ns site.ui.components.carousel
  (:require [keechma.ui-component :as ui]
            [site.react :refer [slider]]
            ))


(defn render-carousel [images]
  [:div.col-12
   [slider {:slidesToShow   3
            :slidesToScroll 1}
    (map (fn [image]
           ^{:key image}
           [:div.col-12.md-col-2
            [:img.w-100p {:src image}]]) images)]])