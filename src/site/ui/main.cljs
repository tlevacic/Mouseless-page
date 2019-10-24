(ns site.ui.main
  (:require [keechma.ui-component :as ui]
            [keechma.toolbox.ui :refer [sub> <cmd route>]]
            [keechma.toolbox.css.core :refer-macros [defelement]]
            [clojure.core.match :refer-macros [match]]))

(defelement -wrap
  :style [{:max-width "1000px"
           :margin "50px auto"}])

(defelement -header-wrap
  :style {:background-color "green"})

(defn get-page [ctx]
  (let [account-role (sub> ctx :account-role)
        route        (route> ctx)]
    (match [account-role route]
           [_ {:page "loading"}] :loading

           [:anon {:page "login"} ]                 :anon/login
           [:anon {:page "homepage"} ]              :anon/login
        
           [:admin {:page "homepage"}]                        :admin/homepage

      :else :not-found)))

(defn render [ctx]
  (let [page (get-page ctx)
        component (ui/component ctx page)]
    [component]))

(def component
  {:renderer render
   :subscription-deps [:account-role]
   :component-deps [:loading
                    :not-found
                    :anon/login
                    :admin/homepage]})

