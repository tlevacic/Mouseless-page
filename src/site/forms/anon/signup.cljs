(ns site.forms.anon.signup
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
  ;do something on submit
  )


(defn constructor []
  (->Form (v/to-validator {:name [:not-empty]
                           :phone-number [:not-empty]
                           :email [:email :not-empty]
                           :password [:not-empty :ok-password]})))
