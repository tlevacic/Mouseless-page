(ns site.forms
  (:require [site.domain.form-ids :refer [on-page on-modal on-type on-substep route-contains]]
            [site.util.processor-pipeline :as pr :refer [select select-not params-pipeline pipeline guard all any ensure]]
            [site.util.forms :refer [provide]]
            [site.domain.db :as db]
            [clojure.string :as str]

            [site.forms.anon.login]
            [site.forms.anon.signup]))

(defn check-role [role automount-fn]
  (let [role' (keyword (first (str/split role ".")))]
    (fn [route app-db]
      (when (= role' (db/get-account-role app-db))
        (automount-fn route app-db)))))

(defn rolify [forms]
  (->> (map
         (fn [[form-id config]]
           (if-let [form-id-ns (namespace form-id)]
             [form-id (update config :auto-mount-fn #(check-role form-id-ns %))]
             [form-id config]))
         forms)
       (into {})))

(def forms
  (-> {:anon/login
       (provide
         site.forms.anon.login/constructor
         (on-page "homepage" :form))} rolify))
