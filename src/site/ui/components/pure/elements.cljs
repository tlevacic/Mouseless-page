(ns site.ui.components.pure.elements
 (:require [keechma.toolbox.css.core :refer-macros [defelement]]))

(defelement -three-column-cards-wrap
  :class [:flex :flex-wrap :justify-between]
  :style [[:>* {:flex "0 1 calc(33.3% - 1rem)"}]])

(defelement -two-column-cards-wrap
  :class [:flex :flex-wrap :justify-between]
  :style [[:>* {:flex "0 1 calc(50% - 1rem)"}]])
