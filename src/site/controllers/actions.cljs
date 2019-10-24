(ns site.controllers.actions
  (:require [keechma.toolbox.pipeline.core :as pp :refer-macros [pipeline!]]
            [keechma.toolbox.pipeline.controller :as pp-controller]
            [keechma.toolbox.dataloader.controller :refer [wait-dataloader-pipeline! run-dataloader!]]
            [site.edb :as edb]
            [site.gql :as gql]
            [site.domain.db :as db]
            [site.util.local-storage :refer [ls-set! ls-remove!]]
            [com.rpl.specter :as s]
            [site.domain.settings :refer [jwt-ls-name]]
            [promesa.core :as p]))

(defn validate-role! [app-db role]
  (let [account-role (db/get-account-role app-db)
        valid? (if (set? role)
                 (contains? role account-role)
                 (= role account-role))]
    (when-not valid?
      :keechma.toolbox.pipeline.core/break)))

(def controller
  (pp-controller/constructor
    (constantly true)
    {:logout (pipeline! [value app-db context]
               (ls-remove! jwt-ls-name)
               (pp/redirect! {})
               (when-let [reload! (:app/reload context)]
                 (reload!)))}))

(defn register
  ([] (register {}))
  ([controllers] (assoc controllers :actions controller)))
