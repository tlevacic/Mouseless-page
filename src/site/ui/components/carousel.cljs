(ns site.ui.components.carousel
  (:require [keechma.ui-component :as ui]
            [site.react :refer [slider]]
            ))


(defn render-carousel [images]
  [:div.col-12
   [slider {:slidesToShow   3
            :slidesToScroll 1
            :dots true
            :infinite true
            :autoplay true
            :autoplaySpeed 1500
            :cssEase "linear"
            :arrows false
            :pauseOnHover true
            :responsive [
                         {
                          :breakpoint 1024,
                          :settings {
                                     :slidesToShow 3,
                                     :slidesToScroll 3,
                                     :infinite true,
                                     :dots true
                                     }
                          },
                         {
                          :breakpoint 800,
                          :settings {
                                     :slidesToShow 2,
                                     :slidesToScroll 2,
                                     :initialSlide 2
                                     }
                          },
                         {
                          :breakpoint 550,
                          :settings {
                                     :slidesToShow 1,
                                     :slidesToScroll 1
                                     }
                          }
                         ]}
    (map (fn [image]
           ^{:key image}
           [:div.col-12.md-col-2
            [:div {:style {:height "250px"}} [:img.w-100p {:src image :height "100%"}]]]) images)]])