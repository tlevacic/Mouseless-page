(ns site.ui.pages.loading
  (:require [keechma.toolbox.css.core :refer-macros [defelement]]
            [site.svgs :refer [logo-picto]]))


(defelement -logo-wrap
            :class [:center]
            :style [[:svg {:width "65px"
                           :height "65px"
                           :display "inline-block"
                           :animation [[:pulse "1s" :ease-in-out :alternate :infinite]]}]])

(defelement -loading-wrap
            :class [:flex :justify-center :items-center]
            :style [{:height "100vh"
                     :max-height "100%"}])

(defn render [ctx]
  [-loading-wrap
   [-logo-wrap [logo-picto]
    [:div.c-blue "Loading"]]])

(def component
  {:renderer render})
