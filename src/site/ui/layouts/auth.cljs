(ns site.ui.layouts.auth
  (:require [fatcap.ui.template :as template :refer-macros [deftemplate]]
            [keechma.toolbox.css.core :refer-macros [defelement]]
            [keechma.ui-component :as ui]))

(defelement -wrap
            :tag :div
            :class [:bg-blue :flex :justify-center :items-start]
            :style [{:height "100vh"
                     :width  "100vw"}])

(defelement -content-wrap
            :tag :div
            :class [:bg-white :mt4 :p4 :rounded]
            :style [{:width "100%"
                     :max-width "70vw"
                     :height "100%"
                     :max-height "50vh"
                     :box-shadow "5px 5px 5px 5px rgba(0,0,0,0.15)"}])

(deftemplate render [ctx]
             [-wrap
              [-content-wrap
               (slot :content)]])

(def component
  (template/constructor
    {:renderer render}))
