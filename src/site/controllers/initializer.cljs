(ns site.controllers.initializer
  (:require [keechma.toolbox.pipeline.core :as pp :refer-macros [pipeline!]]
            [keechma.toolbox.pipeline.controller :as pp-controller]
            [keechma.toolbox.dataloader.controller :refer [wait-dataloader-pipeline!]]
            [site.domain.db :as db]))

(def controller
  (pp-controller/constructor
   {:params (constantly true)}
   {:on-start (pipeline! [value app-db]
                (wait-dataloader-pipeline!)
                (pp/commit! (db/assoc-initialized? app-db true))
                (pp/reroute!))}))

(defn register
  ([] (register {}))
  ([controllers] (assoc controllers ::id controller)))
