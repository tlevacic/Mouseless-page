(ns site.ui.layouts.mobile
  (:require [fatcap.ui.template :as template :refer-macros [deftemplate]]
            [keechma.toolbox.css.core :refer-macros [defelement]]
            [keechma.ui-component :as ui]
            [site.svgs :refer [logo-picto-white]]
            [keechma.toolbox.ui :refer [<cmd sub> route>]]))

(defelement -wrap
  :class [:bg-gradient-primary]
  :style [{:height "100vh"
           :width "100vw"}])

(defelement -logo-wrap
  :class [:text-center :pt-4]
  :style [[:svg {:height "40px"
                 :width "auto"}]])

(def -top-navbar
  :div.px-3>div.row.justify-content-end)

(def -content-wrap
  :div.px-3>div.row.justify-content-center>div.col-xl-8.col-lg-10.col-md-12>div.card.o-hidden.border-0.shadow-lg.mt-3>div.card-body)

(deftemplate render [ctx]
  [-wrap
   [-logo-wrap
    [logo-picto-white]]
   [-content-wrap
    [-top-navbar
     [:ul.navbar-nav.ml-auto
      [:li.nav-item
       [:button.btn.btn-outline-secondary.btn-sm
        {:on-click #(<cmd ctx [:actions :logout])}
        "Logout"]]]]
    [:div.container
     (slot :content)]]])

(def component
  (template/constructor
   {:renderer render}))
