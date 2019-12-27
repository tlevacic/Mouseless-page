(ns site.react
  (:require [reagent.core :as r]
            [oops.core :refer [oget]]
            [react-slick]))


(def slider (r/adapt-react-class react-slick))