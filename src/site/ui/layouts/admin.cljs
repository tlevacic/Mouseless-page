(ns site.ui.layouts.admin
  (:require [fatcap.ui.template :as template :refer-macros [deftemplate]]
            [keechma.toolbox.css.core :refer-macros [defelement]]
            [keechma.ui-component :as ui]
            [keechma.toolbox.ui :refer [<cmd sub> route>]]))

(defelement -wrap
  :style [{:height "100vh"
           :width "100vw"
           :display "flex"}])

(defelement -sidebar
  :class [:navbar-nav :bg-gradient-primary :sidebar :sidebar-dark :accordion])

(defelement -content-wrap
  :class [:d-flex :flex-column :bg-light]
  :style [{:width "100%"
           :overflow-x "hidden"}])

(defelement -top-navbar
  :class [:navbar :navbar-expand :navbar-light :bg-white :topbar :mb4 :static-top :shadow])

(defn active-menu-item? [route-data url-params]
  (= (select-keys route-data (keys url-params))
     url-params))

(defmulti get-menu-items #(sub> % :account-role))

(defmethod get-menu-items :default [_]
  [])

(defn render-sidebar-menu [ctx menu-items]
  (let [route (route> ctx)]
    [:<>
     (map-indexed
      (fn [idx [group & items]]
        [:div {:key [idx group]}
         [:hr.sidebar-divider]
         (when group
           [:div.sidebar-heading.mb-2 group])
         [:div
          (map-indexed
           (fn [jdx [title url]]
             (let [active? (active-menu-item? route url)]
               [:li.nav-item 
                {:key [idx jdx title]
                 :class (when active? "active")} 
                [:a.nav-link.pt-0.pb-1
                 {:href (ui/url ctx url)}
                 (when active?
                   [:i.fas.fa-fw.fa-chevron-right])
                 [:span title]]]))
           items)]])
      menu-items)]))

(deftemplate render [ctx]
  (let [menu-items (get-menu-items ctx)]
    [-wrap
     [-sidebar
      [:a.sidebar-brand.d-flex.align-items-center.justify-content-center
       {:href (ui/url ctx {:page "homepage"})}
       [:div.sidebar-brand-text.mx3 "KEECHMA INIT"]]
      [render-sidebar-menu ctx menu-items]]
     [-content-wrap
      [-top-navbar 
       [:ul.navbar-nav.ml-auto
        [:li.nav-item
         [:button.btn.btn-outline-secondary.btn-sm
          {:on-click #(<cmd ctx [:actions :logout])}
          "Logout"]]]]
      [:div.container-fluid
       (slot :content)]]]))

(def component
  (template/constructor
   {:renderer render
    :subscription-deps [:account-role]}))
