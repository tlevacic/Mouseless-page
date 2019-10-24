(ns keechma.toolbox.forms.pipeline-helpers
  (:require [keechma.toolbox.forms.core :as forms-core]
            [keechma.toolbox.pipeline.core :as pp]))

(defn dissoc-in
  "Dissociate a value in a nested assocative structure, identified by a sequence
  of keys. Any collections left empty by the operation will be dissociated from
  their containing structures."
  ([m ks]
   (if-let [[k & ks] (seq ks)]
     (if (seq ks)
       (let [v (dissoc-in (get m k) ks)]
         (if (empty? v)
           (dissoc m k)
           (assoc m k v)))
       (dissoc m k))
     m))
  ([m ks & kss]
   (if-let [[ks' & kss] (seq kss)]
     (recur (dissoc-in m ks) ks' kss)
     (dissoc-in m ks))))

(defn mount-form! [form-props]
  (pp/send-command! [forms-core/id-key :mount-form] form-props))

(defn unmount-form! [form-props]
  (pp/send-command! [forms-core/id-key :unmount-form] form-props))

(defn make-form-data-path [form-props path]
  (concat [:kv forms-core/id-key :states form-props :data] path))

(defn make-form-state-path [form-props path]
  (concat [:kv forms-core/id-key :states form-props] path))

(defn assoc-in-form-data [app-db form-props path value]
  (assoc-in app-db (make-form-data-path form-props path) value))

(defn update-in-form-data [app-db form-props path update-fn]
  (update-in app-db (make-form-data-path form-props path) update-fn))

(defn get-in-form-data
  ([app-db form-props path] (get-in-form-data app-db form-props path nil))
  ([app-db form-props path default-value]
   (get-in app-db (make-form-data-path form-props path) default-value)))

(defn get-form-data [app-db form-props]
  (get-in app-db (make-form-data-path form-props [])))

(defn dissoc-in-form-data [app-db form-props path]
  (dissoc-in app-db (make-form-data-path form-props path)))

(defn assoc-in-form-state [app-db form-props path value]
  (assoc-in app-db (make-form-state-path form-props path) value))

(defn update-in-form-state [app-db form-props path update-fn]
  (update-in app-db (make-form-state-path form-props path) update-fn))

(defn get-in-form-state
  ([app-db form-props path] (get-in-form-state app-db form-props path nil))
  ([app-db form-props path default-value]
   (get-in app-db (make-form-state-path form-props path) default-value)))

(defn get-form-state [app-db form-props]
  (get-in app-db (make-form-state-path form-props [])))

(defn dissoc-in-form-state [app-db form-props path]
  (dissoc-in app-db (make-form-state-path form-props path)))
