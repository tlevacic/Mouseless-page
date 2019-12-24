(ns site.ui.layouts.homepage
  (:require [fatcap.ui.template :as template :refer-macros [deftemplate]]
            [keechma.toolbox.css.core :refer-macros [defelement]]
            [keechma.ui-component :as ui]))

;TODO remove height
(defelement -wrap
            :tag :div
            :class [:flex :.w-100vw]
            :style [{:background-image  "linear-gradient(to bottom, #fdd231, #fdd231 50%, black 50%)"}])

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
