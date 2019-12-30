(ns site.ui.layouts.homepage
  (:require [fatcap.ui.template :as template :refer-macros [deftemplate]]
            [keechma.toolbox.css.core :refer-macros [defelement]]
            [site.styles.helpers :refer [at-screen]]
            [keechma.ui-component :as ui]))

(defelement -wrap
            :tag :div
            :class [:flex]
            :style [{:background-image  "linear-gradient(to bottom, #D1351B, #fdd231 60%, #03063D 50%)"}])

(defelement -content-wrap
            :tag :div
            :class [:.w-80p :.mx-auto :.w-100p]
            :style [{}
                    (at-screen :xs
                               [:& {:width "100%"}])])

(deftemplate render [ctx]
             [-wrap
              [-content-wrap
               (slot :content)]])

(def component
  (template/constructor
    {:renderer render}))
