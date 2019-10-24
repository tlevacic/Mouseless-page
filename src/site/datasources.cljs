(ns site.datasources
  (:require [site.domain.settings :refer [jwt-ls-name]]
            [site.util.local-storage :refer [ls-get]]
            [site.gql :as gql]
            [keechma.toolbox.dataloader.subscriptions :refer [map-loader]]
            [camel-snake-kebab.core :refer [->kebab-case-keyword]]
            [site.util.processor-pipeline :as pr :refer [select select-not params-pipeline pipeline guard all any ensure]]
            [site.edb :as edb]
            [com.rpl.specter :as s]))

(defn pred-contains? [& values]
  (let [values' (set values)]
    (fn [value]
      (contains? values' value))))

(def ignore-datasource!
  :keechma.toolbox.dataloader.core/ignore)

(def params-loader
  (map-loader #(:params %)))

(def nil-loader
  (map-loader #(constantly nil)))

(defn connection-processor [res]
  (map :node (:edges res)))

(def jwt
  {:target [:kv :jwt]
   :loader (map-loader #(ls-get jwt-ls-name))
   :params (fn [prev _ _]
             (when (:data prev)
               ignore-datasource!))})

(def current-account
  {:target [:edb/named-item :account/current]
   :deps   [:jwt]
   :loader gql/loader
   :params (fn [_ _ {:keys [jwt]}]
             (when jwt
               {:query [:current-account [:currentAccount]]
                :token jwt}))})

(def account-role
  {:target [:kv :account-role]
   :deps   [:current-account]
   :loader params-loader
   :params (fn [_ _ {:keys [current-account]}]
             :anon)})


(def datasources
  {:jwt                    jwt
   :current-account        current-account
   :account-role           account-role
   })
