(ns site.domain.db
  (:require [medley.core :refer [dissoc-in]]
            [site.edb :as edb])
  (:require-macros [site.domain.db :refer [defkvaccess]]))

(defn path->kv-path [path]
  (if (vector? path)
    (concat [:kv] path)
    [:kv path]))

(defn make-get-in-kv [path]
  (fn [app-db]
    (get-in app-db (path->kv-path path))))

(defn make-assoc-in-kv [path]
  (fn [app-db value]
    (assoc-in app-db (path->kv-path path) value)))

(defn make-dissoc-in-kv [path]
  (fn [app-db]
    (dissoc-in app-db (path->kv-path path))))

(defn get-current-account [app-db]
  (edb/get-named-item app-db :account :current))

(defn set-current-account [app-db current-account]
  (edb/insert-named-item app-db :account :current current-account))

(defn remove-current-account [app-db]
  (edb/remove-named-item app-db :account :current))

(defkvaccess
  :jwt
  :initialized?
  :account-role)
