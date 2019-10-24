(ns site.ui.components.pure.inputs
  (:require [fatcap.ui.components.pure.inputs :as inputs]
            [keechma.toolbox.forms.ui :as forms-ui]
            [keechma.toolbox.css.core :refer-macros [defelement]]
            [site.forms.validators :as validators]
            [fatcap.file-upload.ui :as fc-file-upload-ui]
            [fatcap.file-upload.core :as fc-file-upload]
            [garden.stylesheet :refer [at-media]]
            [garden.units :refer [px]]
            [oops.core :refer [oget]]
            [site.ui.components.pure.icons :refer [check-mark error-circle chevron-down radio-unchecked radio-checked]]))

(defelement -wrap
            :class [:form-group :mt2 :mb3]
            :style [{}
                    [:label {:font-size   "14px"
                             :line-height "22px"
                             :position    "relative"
                             :color       "#444444"}
                     [:&.initial {:font-size   "16px"
                                  :line-height "24px"
                                  :margin-left "0"}]
                     [:&.filled-check [:&:after {:content           "''"
                                                 :width             "24px"
                                                 :height            "24px"
                                                 :position          "absolute"
                                                 :right             "12px"
                                                 :top               "40px"
                                                 :z-index           "5"
                                                 :background-size   "contain"
                                                 :background-image  check-mark
                                                 :background-repeat "no-repeat"}]]
                     [:&.is-invalid [:&:after {:content           "''"
                                               :width             "24px"
                                               :height            "24px"
                                               :position          "absolute"
                                               :right             "10px"
                                               :top               "37px"
                                               :z-index           "5"
                                               :background-size   "contain"
                                               :background-image  error-circle
                                               :background-repeat "no-repeat"}]]]
                    [:input {:max-height    "54px"
                             :position      "relative"
                             :width         "100%"
                             :font-size     "16px"
                             :padding       "1rem 1rem"
                             :padding-right "28px"
                             :box-sizing    "border-box"
                             :transition    "border-color .2s ease-in-out"
                             :border-radius "6px"
                             :border        "2px solid #B5BCC4"}
                     [:&.is-invalid {:outline      "none"
                                     :border-color "#FC5640"}]
                     [:&:focus {:outline      "none"
                                :border-color "#000"}]]
                    [:select {:width              "100%"
                              :outline            "none"
                              :height             "54px"
                              :border-radius      "6px"
                              :font-size          "16px"
                              :padding            "1rem 1rem"
                              :background-color   "#fff"
                              :border             "2px solid #B5BCC4"
                              :transition         "border-color .2s ease-in-out"
                              :position           "relative"
                              :-webkit-appearance "none"}
                     [:&:focus {:border-color "#000"}]
                     [:&.is-invalid {:outline      "none"
                                     :border-color "#FC5640"}]]
                    [:label.select-input {:position "relative"}
                     [:&:after {:content           "''"
                                :width             "20px"
                                :height            "20px"
                                :position          "absolute"
                                :right             "7px"
                                :top               "39px"
                                :background-size   "contain"
                                :z-index           "5"
                                :pointer-events    "none"
                                :background-image  chevron-down
                                :background-repeat "no-repeat"}]]])

(defelement -error-messages-wrap
            :tag :ul
            :class [:list-reset :mt1 :ml1]
            :style [{:font-size "90%"
                     :color "#f00"}])


(defelement -file-input-wrap
            :class [:relative :pointer :bg-blue :p1 :inline-block :c-white :rounded])

(defelement -file-input-dropzone
            :tag :label
            :class [:overflow-hidden :absolute :top-0 :left-0 :right-0 :bottom-0 :flex :justify-center :items-center])

(defelement -file-input
            :tag :input
            :class [:block :absolute :pointer :top-0 :right-0 :left-0 :bottom-0]
            :style [{:min-width  "100%"
                     :opacity    0
                     :text-align "right"
                     :min-height "100%"
                     :font-size  "999px"
                     :z-index    5}])

(defelement -file-preview
            :class [:rounded :bw1 :bd-gray :p1 :flex :justify-between :items-center])

(defelement -radio-label
            :tag :label
            :class [:relative :flex :items-center :justify-start :mr2]
            :style [{:padding-left "30px"
                     :cursor       "pointer"
                     :height       "54px"}
                    [:input {:display "none"}]
                    [:&.checked [:&:after
                                 {:background-image    radio-checked
                                  :background-repeat   "no-repeat"
                                  :color               "red"
                                  :background-size     "25px"
                                  :background-position "center"
                                  :opacity             1}]]
                    [:&:after {:content             "''"
                               :position            "absolute"
                               :height              "25px"
                               :width               "25px"
                               :opacity             "0.5"
                               :transition          "all .2s ease-in-out"
                               :left                "0"
                               :background-image    radio-unchecked
                               :background-repeat   "no-repeat"
                               :background-size     "25px"
                               :background-position "center"}]])

(defelement -checkbox-label
            :tag :label
            :class [:relative :checkbox :flex :items-center :justify-start]
            :style [{:padding-left "35px"
                     :padding-top  "16px"
                     :cursor       "pointer"
                     :font-size    "18px"
                     :width        "100%"
                     :max-width    "200px"
                     :white-space  "nowrap"}
                    (at-media {:max-width "831px"}
                              [:& {:white-space "initial"
                                   :max-width   "500px"}])
                    [:&.initial {:padding-top "0"}
                     ;;[:&:after {:top "-2px"}]
                     ]
                    [:input {:display "none"}]
                    [:&.checkbox.checked [:&:after
                                          {:background-image    check-mark
                                           :background-repeat   "no-repeat"
                                           :background-size     "18px"
                                           :background-position "center"}]]
                    [:&:after {:content          "''"
                               :position         "absolute"
                               :left             "0"
                               ;;:top              "18px"
                               :height           "24px"
                               :width            "24px"
                               :border-radius    "3px"
                               :background-color "#fff"
                               :border           "1px solid #444"}]
                    ])


(defmethod inputs/input :file [ctx form-props attr props]
  (let [value (forms-ui/value-in> ctx form-props attr)]
    [:<>
     (if value
       [-file-preview
        (fc-file-upload/filename value)
        [:button
         {:type     :button
          :on-click (fn [_]
                      (fc-file-upload/release value)
                      (forms-ui/<set-value ctx form-props attr nil))}
         "Remove"]]
       [-file-input-wrap
        [-file-input-dropzone
         [-file-input
          (-> (inputs/get-element-props
                {:type      :file
                 :name      (inputs/get-input-name form-props attr)
                 :on-change (fn [e]
                              (when value (fc-file-upload/release value))
                              (fc-file-upload-ui/<on-change-single ctx form-props attr e))}
                props)
              (dissoc :multiple))]]
        (:file/label props)])]))

(defmethod inputs/input :checkbox [ctx form-props attr props]
  (let [checked? (:checkbox/checked? props)
        is-checked-fn (if (nil? checked?) #(boolean (forms-ui/value-in> ctx form-props attr)) checked?)
        on-change-fn (or (:checkbox/on-change props)
                         #(forms-ui/<set-value ctx form-props attr (oget % :target.checked)))
        errors (forms-ui/errors-in> ctx form-props attr)]
    [-checkbox-label {:class [(if (boolean (:wrapper/inline props)) :initial)
                              (when (boolean (forms-ui/value-in> ctx form-props attr)) :checked)]}
     [:input
      (inputs/get-element-props
        {:on-change on-change-fn
         :checked   (if (fn? is-checked-fn) (is-checked-fn) is-checked-fn)
         :type      "checkbox"
         :name      (inputs/get-input-name form-props attr)
         :class     (when (seq errors) :has-errors)}
        props)]
     (:checkbox/label props)]))

(defmethod inputs/input :empty []
  [:div.col-12 ""])

(defmethod inputs/input :radio-group [ctx form-props attr props]
  (let [{:keys [radio/options]} props
        errors (forms-ui/errors-in> ctx form-props attr)
        value (forms-ui/value-in> ctx form-props attr)
        input-props (inputs/get-element-props {} props)]
    [:div.radio-group.flex
     (map
       (fn [item]
         ^{:key (:value item)}
         [-radio-label {:class (when (= (:value item) value) :checked)}
          [:input
           {:checked   (= (:value item) value)
            :value     (:value item)
            :type      "radio"
            :name      (inputs/get-input-name form-props attr)
            :on-change (fn [e]
                         (when (oget e :target.checked)
                           (forms-ui/<set-value ctx form-props attr (:value item))))}]
          (:label item)])
       options)]))

(defn render-errors [attr-errors]
  (when-let [errors (get-in attr-errors [:$errors$ :failed])]
    (into [-error-messages-wrap]
          (doall (map (fn [e]
                        [:li (validators/get-validator-message e)])
                      errors)))))

(defmulti wrapped-input
          (fn [_ _ _ props]
            (or (:input/type props) :text)))

(defn add-default-class [props default-class]
  (let [current-class (:class props)]
    (assoc props :class (if current-class
                          (flatten [current-class default-class])
                          default-class))))

(defmethod wrapped-input :default [ctx form-props attr props]
  (let [label (:input/label props)
        errors (forms-ui/errors-in> ctx form-props attr)
        filled (and (not (seq errors)) (seq (forms-ui/value-in> ctx form-props attr)))]
    [-wrap {:class (:wrapper/class props)}
     (when label
       [:label
        {:for   (inputs/get-input-name form-props attr)
         :class [(when filled :filled-check) (when (seq errors) "is-invalid")]}
        label])

     (if (or (:prepend/text props) (:append-text/text props))

       [:div.input-group
        (when (:prepend/text props)
          [:div.input-group-prepend [:span.input-group-text (:prepend/text props)]])
        [inputs/render ctx form-props attr
         (add-default-class props ["form-control" (when (seq errors) "is-invalid")])]
        (when (:append/text props)
          [:div.input-group-append [:span.input-group-text (:append/text props)]])]

       [inputs/render ctx form-props attr
        (add-default-class props
                           ["form-control"
                            (when (seq errors) "is-invalid")])]
       )
     [render-errors errors]]))

(defmethod wrapped-input :radio-group [ctx form-props attr props]
  (let [label (:input/label props)
        errors (forms-ui/errors-in> ctx form-props attr)
        filled (and (not (seq errors)) (seq (forms-ui/value-in> ctx form-props attr)))]
    [-wrap {:class (:wrapper/class props)}
     (when label
       [:label
        {:for   (inputs/get-input-name form-props attr)
         :class [(when (seq errors) "is-invalid")]}
        label])

     [inputs/render ctx form-props attr
      (add-default-class props
                         ["form-control"
                          (when (seq errors) "is-invalid")])]

     [render-errors errors]]))

(defmethod wrapped-input :checkbox [ctx form-props attr props]
  (let [label (:input/label props)
        errors (forms-ui/errors-in> ctx form-props attr)]
    (if (:wrapper/inline props)
      [-wrap {:class (:wrapper/class props)
              :style {:flex-direction "row"}}
       (when label
         [:label.md-col-6.col-12.initial.my1
          {:class [(when (seq errors) "is-invalid")]}
          label])
       [inputs/render ctx form-props attr
        (add-default-class props ["form-check-input" (when (seq errors) "is-invalid")])]
       [render-errors errors]]

      [-wrap {:class (:wrapper/class props)}
       (when label
         [:label
          {:class [(when (seq errors) "is-invalid")]}
          label])
       [inputs/render ctx form-props attr (add-default-class props ["form-check-input" (when (seq errors) "is-invalid")])]
       [render-errors errors]])))

(defmethod wrapped-input :select [ctx form-props attr props]
  (let [label (:input/label props)
        errors (forms-ui/errors-in> ctx form-props attr)
        filled (and (not (seq errors)) (seq (forms-ui/value-in> ctx form-props attr)))]
    [-wrap {:class (:wrapper/class props)}
     (when label
       [:label.select-input
        {:for   (inputs/get-input-name form-props attr)
         :class [(when (seq errors) "is-invalid")]}
        label])
     [:div.form-select
      [inputs/render ctx form-props attr
       (add-default-class props ["form-select-input" (when (seq errors) "is-invalid")])]]
     [render-errors errors]]))

(defn render [ctx form-props attr props]
  [wrapped-input ctx form-props attr props])

(defn render-all [ctx form-props & fields]
  (->> (map
         (fn [[attr props]]
           ^{:key attr}
           [render ctx form-props attr props])
         (filter (complement nil?) fields))
       (into [:<>])))
