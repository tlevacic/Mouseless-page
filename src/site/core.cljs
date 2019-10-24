(ns site.core
  (:require [reagent.core :as reagent]
            [keechma.app-state :as app-state]
            [keechma.toolbox.dataloader.app :as dataloader]
            [keechma.toolbox.forms.app :as forms]
            [site.controllers :refer [controllers]]
            [site.ui :refer [ui]]
            [site.subscriptions :refer [subscriptions]]
            [site.edb :refer [edb-schema]]
            [site.datasources :refer [datasources]]
            [site.forms :as site-forms]
            [site.util.forms :refer [auto-mount-fns forms]]
            [site.styles :refer [stylesheet]]
            [keechma.toolbox.css.app :as css]
            [fatcap.app :as fatcap]
            [site.domain.route :as route]))

(set! *print-level* 30)
(declare reload)

(def app-definition
  (-> {:components    ui
       :controllers   controllers
       :subscriptions subscriptions
       :html-element  (.getElementById js/document "app")
       :route-processor route/processor
       :routes [["" {:page "homepage"}]
                ":page"]
       :context {:app/reload reload}}
      (css/install (stylesheet))
      (dataloader/install datasources edb-schema)
      (forms/install (forms site-forms/forms) (auto-mount-fns site-forms/forms))
      (fatcap/install)))

(defonce running-app (clojure.core/atom nil))

(defn start-app! []
  (reset! running-app (app-state/start! app-definition)))

(defn dev-setup []
  (when ^boolean js/goog.DEBUG
    (enable-console-print!)
    (println "dev mode")))

(defn reload []
  (let [current @running-app]
    (if current
      (app-state/stop! current start-app!)
      (start-app!))))

(defn ^:export main []
  (dev-setup)
  (start-app!))
