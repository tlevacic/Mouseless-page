(ns site.ui.layouts.homepage
  (:require [fatcap.ui.template :as template :refer-macros [deftemplate]]
            [keechma.toolbox.css.core :refer-macros [defelement]]
            [keechma.ui-component :as ui]))

(defelement -wrap
            :tag :div
            :class [:flex :.w-100vw]
            :style [{:background-image  "linear-gradient(to bottom, #D1351B, #fdd231 50%, #03063D 50%)"}])

(defelement -content-wrap
            :tag :div
            :class [:.w-80p :.mx-auto :.w-100p])

(deftemplate render [ctx]
             [-wrap
              [-content-wrap
               (slot :content)]])

(def component
  (template/constructor
    {:renderer render}))
