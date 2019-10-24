(ns site.ui.layouts.auth
  (:require [fatcap.ui.template :as template :refer-macros [deftemplate]]
            [keechma.toolbox.css.core :refer-macros [defelement]]
            [keechma.ui-component :as ui]))

(defelement -wrap
  :class [:bg-gradient-primary]
  :style [{:height "100vh"
           :width "100vw"}])

(def -content-wrap
  :div.container>div.row.justify-center>div.col-xl-8.col-lg-10.col-md-12>div.card.o-hidden.border-0.shadow-lg.my-5>div.card-body)

(deftemplate render [ctx]
  [-wrap
   [-content-wrap
    (slot :content)]])

(def component
  (template/constructor
   {:renderer render}))
