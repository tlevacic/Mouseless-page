(ns site.ui.pages.anon.login
  (:require [keechma.ui-component :as ui]
            [site.ui.components.pure.inputs :as i]
            [keechma.toolbox.forms.ui :as forms-ui]
            [site.ui.pages.anon.shared :refer [render-errors]]))

(defn render-content [ctx form-props]
  [:div
   [:h2 {} "Log In"]
   [:form {:on-submit #(forms-ui/<submit ctx form-props %)}
    [render-errors ctx form-props]
    [i/render-all ctx form-props
     [:email 
      {:input/label "Email"
       :placeholder "username@example.com"}]
     [:password
      {:input/label "Password"
       :type        :password
       :placeholder "••••••••"}]] 
    [:div
     [:button.btn "Submit"]]]])

(defn render [ctx]
  (let [form-props [:anon/login :form]]
    [(ui/component ctx :layout/auth)
     {:fatcap.ui.template/slots
      (-> {[:content] [render-content ctx form-props]})}]))

(def component
  {:renderer render
   :component-deps [:layout/auth]})
