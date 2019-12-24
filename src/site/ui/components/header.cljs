(ns site.ui.components.header
  (:require [keechma.ui-component :as ui]
            [keechma.toolbox.ui :refer [sub> <cmd]]
            [site.ui.components.pure.elements :as e]
            [keechma.toolbox.css.core :refer-macros [defelement]]
            [oops.core :refer [oget]]))

(def items
  [{:value "Features" :href {:page "features"}}
   {:value "Download" :href {:page "download"} :icon "fab mr1 fa-apple"}
   {:value "Buy"      :href {:page "buy"}}])

(defelement -wrap
            :class [:.w-100p :flex :items-center :justify-between :pt3 :mb4])

(defelement -menu
            :class [:justify-between :flex :lg-col-8 :md-col-8 :sm-col-12]
            :style [{:width "300px"}])

(defelement -logo
            :tag :img
            :class [:lg-col-4 :md-col-4 :sm-col-12]
            :style [:width "120px"])

(defn render []
  [-wrap
   [-logo {:src "keechma-logo.png" :alt "AAA" :style {:object-fit "contain" :width "300px"}}]
   [-menu
    (map (fn [ele]
           [:div
            (when (:icon ele)
              [:i {:class [(:icon ele)]}])
            [e/-text-link {:href (:href ele)} (:value ele)]])
         items)]])

(def component
  {:renderer render})
