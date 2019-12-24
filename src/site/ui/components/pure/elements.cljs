(ns site.ui.components.pure.elements
 (:require [keechma.toolbox.css.core :refer-macros [defelement]]))

(defelement -three-column-cards-wrap
  :class [:flex :flex-wrap :justify-between]
  :style [[:>* {:flex "0 1 calc(33.3% - 1rem)"}]])

(defelement -two-column-cards-wrap
  :class [:flex :flex-wrap :justify-between]
  :style [[:>* {:flex "0 1 calc(50% - 1rem)"}]])

(defelement -text-link
            :tag :a
            :class [:c-black]
            :style [{:font-size "1.15rem"
                     :font-weight "700"}])

(defelement -counter
            ;TODO fix padding and margin
            :tag :div
            :class [:mt3 :px2 :py2]
            :style [{:width "300px"
                     :height "45px"
                     :border-radius "13px"
                     :opacity "0.1"
                     :background-color "rgba(18,18,18,.5)"}])

(defelement -title
            :tag :p
            :class [:items-center :mb3 :.w-80p]
            :style [{:font-weight "900"
                     :font-size "60px"
                     :line-height "60px"
                     :text-align "center"}])

(defelement -text
            :tag :p
            :class [:.w-70p :inline-block]
            :style [{:font-size "20px"
                     :text-align "left"
                     :line-height "28px"}])

(defelement -mihael
            :tag :img
            :style [{:width  "600px"
                     :object-fit "contain"
                     :height "480px"}])

(defelement -card
            :class [:flex :flex-row :.align-center :.w-85p :mt4 :.mx-auto :flex-wrap]
            :style [{:border-color "white"}])

(defelement -card-picture
            :tag :img
            :class [:border]
            :style [{:border-radius "30px"}])

(defelement -card-title
            :tag :h1
            :class [:fs-40 :bold :c-white :mb2])
