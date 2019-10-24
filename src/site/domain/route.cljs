(ns site.domain.route
  (:require [site.domain.db :as db]
            [keechma.toolbox.logging :as l]
            [clojure.core.match :refer-macros [match]]))

(defmulti process-route-data-for-role (fn [_ app-db] (db/get-account-role app-db)))

(defmethod process-route-data-for-role :default [route-data _ ] route-data)

(defn process-route-data [route app-db]
  (-> (cond
        (not (db/get-initialized? app-db)) {:page "loading"}
        :else route)
      (process-route-data-for-role app-db)))

(defn log-route [route-data processed-route-data]
  (if (= route-data processed-route-data)
    (do (l/group "Route")
        (l/pp route-data)
        (l/group-end))
    (do (l/group "Route / Processed Route")
        (l/pp route-data)
        (l/pp processed-route-data)
        (l/group-end))))

(defn processor [route app-db]
  (let [route-data (route :data)
        processed-route-data (process-route-data route-data app-db)]
    (log-route route-data processed-route-data)
    (if (not= route-data processed-route-data)
        (assoc route
               :data processed-route-data
               :original-data route-data)
        route)))
