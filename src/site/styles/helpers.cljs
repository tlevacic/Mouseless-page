(ns site.styles.helpers
  (:require [garden-basscss.vars :refer [vars]]
            [garden.stylesheet :refer [at-media]]))

(def breakpoints (:breakpoints @vars))

(defn at-screen [screen-size & args]
  (if (keyword? screen-size)
    (at-media (screen-size breakpoints) args)
    (at-media screen-size args)))

(defn stylesheet []
  [[:.pointer {:cursor "pointer"}]
   [:.uppercase {:text-transform "uppercase"}]
   [:.underline {:text-decoration "underline"}]
   [:.align-center {:align-items "center"}]
   [:.w-100p {:width "100%"}]
   [:.h-100p {:height "100%"}]
   [:.w-100vw {:width "100vw"}]
   [:.h-100vh {:height "100vh"}]
   [:.flex-1 {:display "flex" :flex 1}]])
