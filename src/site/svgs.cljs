(ns site.svgs
  (:require [site.styles.colors :refer [colors]]))

(def default-colors
  {:picto (:blue colors)
   :text (:dark colors)
   :mono (:dark colors)
   :white (:white colors)})

(defn logo-picto
  ([] (logo-picto (:picto default-colors)))
  ([fill]
   [:svg]))

(def logo-picto-mono (partial logo-picto (:mono default-colors)))

(def logo-picto-white (partial logo-picto (:white default-colors)))

(defn logo-mono
  ([] (logo-mono (:mono default-colors)))
  ([fill]
   [:svg]))

(defn logo
  ([] (logo {}))
  ([fills]
   (let [logo-fill (:brand-purple colors)]
     [:svg])))
