(ns site.styles
  (:require [keechma.toolbox.css.core :as css]
            [garden-basscss.core :as core]
            [site.styles.reset :as reset]
            [site.styles.typography :as typography]
            [site.styles.colors :as colors]
            [site.styles.helpers :as helpers]
            [garden.units :refer [px rem em]])
  (:require-macros [garden.def :refer [defkeyframes]]))

(defn stylesheet []
  [(reset/stylesheet)
   (helpers/stylesheet)
   (typography/stylesheet)
   (core/stylesheet)
   (colors/stylesheet)
   @css/component-styles
   [:html {:height "100%"
           :font-size "16px"
           :background (:gray colors/colors)
           :font-family "\"Open Sans\", sans-serif"
           :font-weight "400"
           :overflow-x "hidden"
           :-webkit-font-smoothing "antialiased"}]
   [:#app {:height "100%"}]
   [:body {:height "100%"}]])


