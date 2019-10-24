(ns fatcap.util.ui
  (:require [fatcap.util :refer [as-vec]]))

(defn get-config [ctx config p]
  (let [path (as-vec p)
        ctx-config-value (get-in (:fatcap.ui/config ctx) path)]
    (get-in config path ctx-config-value)))

(defn configure [ctx config-fn]
  (let [config (or (:fatcap.ui/config ctx) {})]
    (assoc ctx :fatcap.ui/config (config-fn config))))

(defn wrap-renderer [ctx renderer]
  (let [original-renderer (:renderer ctx)]
    (assoc ctx :renderer
           (fn [ctx & args]
             (apply renderer (assoc ctx :renderer original-renderer) args)))))


