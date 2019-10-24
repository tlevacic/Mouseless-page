(ns site.ui.components.header
  (:require [keechma.ui-component :as ui]
            [keechma.toolbox.ui :refer [sub> <cmd]]
            [keechma.toolbox.css.core :refer-macros [defelement]]
            [oops.core :refer [oget]]))

(defelement -wrap
  :class [:flex :flex-row :justify-between :py2])

(defelement -menu-wrap
  :tag :ul
  :class [:flex :flex-row ])

(defmulti render-buttons (fn [_ role] role))

(defmethod render-buttons :default [_ _])

(defmethod render-buttons :anon [ctx _]
  [:div
   [:a.inline-block.mr1 {:href (ui/url ctx {:page "login"})} "Log In"]
   [:a.inline-block.ml1 {:href (ui/url ctx {:page "signup"})} "Sign Up"]])

(def menu-items
  {:anon             []})

(defn render-menu [ctx account-role]
  (let [account-role-menu-items (menu-items account-role)]
    [-menu-wrap
     (map 
      (fn [[title url-params]]
        ^{:key title}
        [:li.mx2
         [:a {:href (ui/url ctx url-params)} title]])
      account-role-menu-items)]))

(defn render [ctx]
  (let [account-role (sub> ctx :account-role)]
    [-wrap
     [render-menu ctx account-role]
     [render-buttons ctx account-role]]))

(def component
  {:renderer render
   :subscription-deps [:account-role]})
