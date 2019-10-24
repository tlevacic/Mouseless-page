(ns site.styles.typography
  (:require [garden-basscss.vars :refer [vars]]
            [site.styles.colors :refer [colors]]
            [garden.units :refer [px rem em]]))

(defn stylesheet []
  [[:a {:text-decoration "none"
        :color "inherit"}]
   [:.outline-none {:outline "none"}]
   [:.f-12 {:font-size ".75rem"
            :line-height "1rem"}]
   [:.f-14 {:font-size ".875rem"
            :line-height "1.125rem"}]
   [:.f-16 {:font-size ".875rem"}]
   [:.f-18 {:font-size "1.125rem"
            :line-height "1.625rem"}]
   [:.f-22 {:font-size "1.325rem"
            :line-height "1.825rem"}]
   [:.f-30 {:font-size "1.875rem"
            :line-height "2.375rem"}]
   [:.f-54 {:font-size "3.375rem"
            :line-height "3.875rem"}]
   [:.f-72 {:font-size "4.5rem"
            :line-height "5rem"}]
   [:.f-106 {:font-size "6.625rem"
             :line-height "7.125rem"}]])