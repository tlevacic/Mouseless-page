(ns site.subscriptions
  (:require [keechma.toolbox.dataloader.subscriptions :as dataloader]
            [site.edb :refer [edb-schema]]
            [site.datasources  :refer [datasources]]
            [site.domain.db :as db])
  (:require-macros [reagent.ratom :refer [reaction]]))

(defn as-sub [getter-fn]
  (fn [app-db-atom & args]
    (reaction
     (apply getter-fn @app-db-atom args))))

(def subscriptions
  {})
