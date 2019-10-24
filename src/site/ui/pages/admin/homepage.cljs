(ns site.ui.pages.admin.homepage
  (:require [keechma.ui-component :as ui]
            [site.ui.components.pure.inputs :as i]
            [keechma.toolbox.forms.ui :as forms-ui]
            [site.ui.pages.anon.shared :refer [render-errors]]))

(defn render-content [ctx]
  [:div
   [:h2 {} "HOMEPAGE"]])

(defn render [ctx]
  [(ui/component ctx :layout/admin)
   {:fatcap.ui.template/slots
    (-> {[:content] [render-content ctx]})}])

(def component
  {:renderer render
   :component-deps [:layout/admin]})