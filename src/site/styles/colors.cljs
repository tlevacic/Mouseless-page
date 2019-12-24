(ns site.styles.colors
  (:require [garden.color :as color]))

(def colors
  {:white       "#FFFFFF"
   :black       "#000000"
   :gray-light  "#9DA6B3"
   :gray        "#4F5762"
   :turq        "#0CD1E6"
   :gray-6      "#252631"
   :gray-5      "#778CA2"
   :gray-4      "#98A9BC"
   :gray-3      "#E8ECEF"
   :gray-2      "#F2F4F6"
   :gray-1      "#F8FAFB"
   :blue        "#4D7CFE"
   :mustard     "#FFAB2B"
   :green       "#6DD230"
   :red         "#FE4D97"
   :warning     "#FC5640"
   :red-dark    "#EE4B56"
   :yellow "#fdd231"
   :blue-light  "#0CD1E6"
   :alert		    "#FCC304"
   :exception	  "#0CD1E6"})

(defn transition [prop]
  (str (name prop) " 0.10s ease-in-out"))

(defn make-color-variations [colors]
  (reduce-kv (fn [m k v]
               (let [base-name (name k)]
                 (assoc m
                   k v
                   (keyword (str base-name "-l")) (color/as-hex (color/lighten v 10))
                   (keyword (str base-name "-d")) (color/as-hex (color/darken v 10))))) {} colors))

(def colors-with-variations (make-color-variations colors))

(defn gen-colors-styles [class-name prop]
  (map (fn [[color-name val]]
         (let [color-name (name color-name)
               normal-class (str "." class-name "-" color-name)
               hover-class (str "." class-name "-h-" color-name)
               darken-val (color/darken val 10)
               lighten-val (color/lighten val 10)
               hover ":hover"
               make-important #(str %1 " !important")]
           [[normal-class {prop val}]
            [(str normal-class "-d") {prop darken-val}]
            [(str normal-class "-l") {prop lighten-val}]
            [(str hover-class hover) {prop val}]
            [(str hover-class "-d" hover) {prop darken-val}]
            [(str hover-class "-l" hover) {prop lighten-val}]

            [(str normal-class "-i") {prop (make-important val)}]
            [(str normal-class "-d-i") {prop (make-important darken-val)}]
            [(str normal-class "-l-i") {prop (make-important lighten-val)}]
            [(str hover-class "-i" hover) {prop (make-important val)}]
            [(str hover-class "-d-i" hover) {prop (make-important darken-val)}]
            [(str hover-class "-l-i" hover) {prop (make-important lighten-val)}]])) colors))


(defn stylesheet []
  [[:.bg-transparent {:background "transparent"}]
   (gen-colors-styles "bg" :background-color)
   (gen-colors-styles "c" :color)
   (gen-colors-styles "f" :fill)
   (gen-colors-styles "bd" :border-color)
   (gen-colors-styles "bdt" :border-top-color)
   (gen-colors-styles "bdb" :border-bottom-color)
   (gen-colors-styles "bdl" :border-left-color)
   (gen-colors-styles "bdr" :border-right-color)
   [:.t-c {:transition (transition :color)}]
   [:.t-bg {:transition (transition :background-color)}]
   [:.t-bd {:transition (transition :border-color)}]])

