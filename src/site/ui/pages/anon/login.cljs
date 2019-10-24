(ns site.ui.pages.anon.login
  (:require [keechma.ui-component :as ui]
            [site.ui.components.pure.inputs :as i]
            [keechma.toolbox.forms.ui :as forms-ui]
            [site.ui.pages.anon.shared :refer [render-errors]]))

(defn render-content [ctx form-props]
  [:div
   [:h2.h1 {} "Log In"]
   [:form {:on-submit #(forms-ui/<submit ctx form-props %)}
    [render-errors ctx form-props]
    [i/render-all ctx form-props
     [:email
      {:input/label   "Email"
       :placeholder   "username@example.com"
       :wrapper/class "flex my2 flex-column"}]
     [:password
      {:input/label   "Password"
       :type          :password
       :placeholder   "••••••••"
       :wrapper/class "flex my2 flex-column"}]
     [:country {:input/type     :select
                :input/label    "Country of residence"
                :input/name     :country
                :select/placeholder "Select"
                :select/options [{:label "US of A" :value "USA"}
                                 {:label "Some 3rd World Country" :value "OTHER"}]
                :wrapper/class  [:flex :flex-column :col-12]}]
     [:account-type {:input/type    :radio-group
                     :input/label   "Type of account"
                     :input/name    :account-type
                     :radio/options [{:label "Admin" :value "ADMIN"}
                                     {:label "Pro" :value "PRO"}
                                     {:label "Basic" :value "BASIC"}]}]
     [:subscribe {:input/type     :checkbox
                  :input/label    "I want to receive spam from you"
                  :input/name     :subscribe
                  :checkbox/label "Of course"}]]
    [:div
     [:button "Submit"]]]])

(defn render [ctx]
  (let [form-props [:anon/login :form]]
    [(ui/component ctx :layout/auth)
     {:fatcap.ui.template/slots
      (-> {[:content] [render-content ctx form-props]})}]))

(def component
  {:renderer       render
   :component-deps [:layout/auth]})
