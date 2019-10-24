(ns site.forms.anon.login
  (:require [keechma.toolbox.forms.core :as forms-core]
            [site.forms.validators :as v]
            [keechma.toolbox.pipeline.core :as pp :refer-macros [pipeline!]]
            [site.domain.db :as db]
            [site.gql :refer [m!]]
            [site.util.local-storage :refer [ls-set!]]
            [site.domain.settings :refer [jwt-ls-name]]
            [keechma.toolbox.dataloader.controller :refer [run-dataloader! wait-dataloader-pipeline!]]))

(defrecord Form [validator])

(defmethod forms-core/submit-data Form [_ app-db _ data]
  ;;do something on submit
  )

(defmethod forms-core/on-submit-success Form [this app-db _ data]
  (pipeline! [value app-db]
    (pp/commit! (db/assoc-jwt app-db data))
    (ls-set! jwt-ls-name data)
    (run-dataloader! [:*])
    (wait-dataloader-pipeline!)
    (pp/redirect! {:page "homepage"})))

(defn constructor []
  (->Form (v/to-validator {:email [:email :not-empty]
                           :password [:not-empty :ok-password]})))
