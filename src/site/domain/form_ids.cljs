(ns site.domain.form-ids
  (:require [clojure.core.match :refer-macros [match]]))

(defn run-mounter [app-db route mounter]
  (if (fn? mounter)
    (mounter route app-db)
    mounter))

(defn on-page [page mounter]
  (fn [route app-db]
    (when (= page (:page route))
      (run-mounter app-db route mounter))))

(defn on-type [type mounter]
   (fn [route app-db]
     (when (= type (:type route))
       (run-mounter app-db route mounter))))

(defn on-substep [substep mounter]
   (fn [route app-db]
     (when (= substep (:substep route))
       (run-mounter app-db route mounter))))

(defn on-modal [modal mounter]
  (fn [route app-db]
    (when (= modal (:modal route))
      (run-mounter app-db route mounter))))

(defn route-contains [info mounter]
  (fn [route app-db]
    (when (contains? route info)
      (run-mounter app-db route mounter))))
