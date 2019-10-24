(ns site.styles.colors)

(def colors
  {:transparent  "transparent"
   :white        "#FFFFFF"
   :black        "#000000"
   :blue         "#00a1e4"})

(defn transition [prop]
  (str (name prop) " 0.10s ease-in-out"))

(defn gen-colors-styles [class-name prop]
  (map (fn [[color-name val]]
         (let [color-name (name color-name)
               normal-class (str "." class-name "-" color-name)
               hover-class (str "." class-name "-h-" color-name)
               hover ":hover"
               make-important #(str %1 " !important")]
           [[normal-class {prop val}]
            [(str hover-class hover) {prop val}]

            [(str normal-class "-i") {prop (make-important val)}]
            [(str hover-class "-i" hover) {prop (make-important val)}]])) colors))

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
