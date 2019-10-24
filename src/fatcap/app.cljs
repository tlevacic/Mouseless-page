(ns fatcap.app
  (:require [path-matcher.core :as pm]
            [fatcap.util :refer [as-vec]]))

(defn vectorize-keys [m]
  (into {} (map (fn [[k v]] [(as-vec k) v]) m)))

(defn prepend-to-keys [m prepend]
  (into {} (map (fn [[k v]] [(vec (concat (as-vec prepend) (as-vec k))) v]) m)))


(defn apply-interceptors [ctx interceptors]
  (let [path (:path ctx)
        ctx-interceptors (pm/get-matching interceptors path)]
    (reduce (fn [acc i] (i acc)) ctx ctx-interceptors)))


(defn apply-root-interceptors [ctx interceptors]
  (let [component-name (last (:path ctx))
        interceptor (get interceptors component-name)]
    (if interceptor
      (interceptor ctx)
      ctx)))

(defn ctx-processor [interceptors ctx]
  (-> ctx
      (apply-interceptors (:config interceptors))
      (apply-interceptors (:components interceptors))
      (apply-root-interceptors (:root interceptors))))

(defn extract-component-interceptors [components]
  (reduce-kv
   (fn [m k v]
     (let [interceptors (:fatcap.ui.ctx/interceptors v)]
       (if interceptors
         (let [component-root-interceptor (get interceptors :>)
               interceptors' (dissoc interceptors :>)]
           (-> m
               (update :components #(merge % (prepend-to-keys interceptors' [:* k])))
               (update :root #(if component-root-interceptor (assoc % k component-root-interceptor) %))))
         m)))
   {}
   components))

(defn extract-interceptors [app-state config]
  (let [config-interceptors (:fatcap.ui.ctx/interceptors config)
        {:keys [components root]} (extract-component-interceptors (:components app-state))]
    {:config (vectorize-keys config-interceptors)
     :components components
     :root root}))

(defn make-ctx-processor [app-state config]
  (let [current-ctx-processor (or (:keechma.ui-component/ctx-processor app-state) identity)
        interceptors (extract-interceptors app-state config)]
    (fn [ctx]
      (->> ctx
           current-ctx-processor
           (ctx-processor interceptors)))))

(defn install 
  ([app-state] (install app-state {}))
  ([app-state config]
   (-> app-state
       (assoc :keechma.ui-component/ctx-processor (make-ctx-processor app-state config)))))
