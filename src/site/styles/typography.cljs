(ns site.styles.typography
  (:require [garden-basscss.vars :refer [vars]]
            [site.styles.colors :refer [colors]]
            [garden.units :refer [px rem em]]))

(def font-base-px 16)

(defn px->rem
  [target-px]
  (str (/ target-px font-base-px) "rem"))


(defn generate-fws []
  (let [font-weights ["100" "200" "300" "400" "500" "600" "700" "800" "900"]]
    (mapv (fn [fw]
            (let [class-name (str ".fw-" fw)
                  style-prop {:font-weight fw}]
              [(keyword class-name) style-prop])) font-weights)))

(defn generate-fs []
  (let [font-sizes (range 8 53)]
    (mapv (fn [fs]
            (let [class-name (str ".fs-" fs)
                  style-prop {:font-size (px->rem fs)}]
              [(keyword class-name) style-prop])) font-sizes)))

(defn generate-lh []
  (let [line-heights (range 8 53)]
    (mapv (fn [lh]
            (let [class-name (str ".lh-" lh)
                  style-prop {:line-height (px->rem lh)}]
              [(keyword class-name) style-prop])) line-heights)))

(defn stylesheet []
  [(generate-fws)
   (generate-fs)
   (generate-lh)
   [:.h1 :h1
    {:font-size "30px"
     :font-weight "36px"}]
   [:.h2 :h2
    {:font-size "26px"
     :line-height "31px"}]
   [:.h3 :h3
    {:font-size "24px"
     :line-height "24px"}]
   [:.h4 :h4
    {:font-size "20px"
     :line-height "24px"}]
   [:.h5 :h5
    {:font-size "14px"
     :line-height "16px"}]
   [:.h6 :h6
    {:font-size "16px"
     :line-height "21px"}]
   [:.t-caption
    {:font-size "14px"
     :font-weight 500
     :line-height "16px"}]
   [:.t-button-label
    {:font-size "15px"
     :font-weight 600}]
   [:.t-body :p
    {:font-size "16px"
     :line-height "21px"
     :margin-bottom "16px"}]
   [:a {:text-decoration "none"
        :color (colors :turq)}]])
