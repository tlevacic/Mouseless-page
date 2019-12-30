(ns site.ui.components.pure.elements
 (:require [keechma.toolbox.css.core :refer-macros [defelement]]
           [site.styles.helpers :refer [at-screen]]))

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
            :tag :div
            :class [:max-width-4]
            :style [{:font-size "20px"
                     :text-align "left"
                     :line-height "28px"}
                    (at-screen :xs
                               [:& {:width "100%"}])])

(defelement -mihael
            :tag :img
            :style [{:width  "600px"
                     :object-fit "contain"
                     :height "480px"}])



;TODO fix picture size on xs
(defelement -card-picture
            :tag :img
            :class [:col-12]
            :style [{:border-radius "30px"}])

(defelement -card-title
            :tag :h1
            :class [:fs-40 :bold :c-white :mb2]
            :style [{}
                    (at-screen :xs
                               [:& {:font-size  "30px"
                                    :margin-top "12px"}])])

(defelement -card-wrap
            :class [:col-12 :lg-col-6 :md-col-6 :sm-col-12 :flex]
            :style [{}
                    (at-screen :xs
                               [:& {:justify-content "center"}])])
{:justify-content "center"}

(defelement -link-element
            :tag :div
            :class [:col-3 :lg-col-3 :md-col-4 :sm-col-6 :fs-16 :px3 :fw-700 :lh-20]
            :style [{:color "rgba(242, 242, 242, 0.5)"
                     :padding-top "10px"}])


(defelement -big-text
            :tag :div
            :class [:c-white :fs-45 :mt5 :fw-700 :col-12 :.mx-auto :md-col-10 :sm-col-11 :lg-col-8]
            :style [{:line-height "1.2"}
                    (at-screen :xs
                                 [:& {:font-size  "30px" :padding "13px"}])])


;MEDIA QUERY NOT WORKING!!!!!!!
(defelement -image-div-size
            :class [:col-11 :sm-col-12 :md-col-8 :lg-col-8])

(defelement -text-div-size
            :class [:col-12])