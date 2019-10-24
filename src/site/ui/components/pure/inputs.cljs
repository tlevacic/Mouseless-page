(ns site.ui.components.pure.inputs
  (:require [fatcap.ui.components.pure.inputs :as inputs]
            [keechma.toolbox.forms.ui :as forms-ui]
            [keechma.toolbox.css.core :refer-macros [defelement]]
            [site.forms.validators :as validators]
            [fatcap.file-upload.ui :as fc-file-upload-ui]
            [fatcap.file-upload.core :as fc-file-upload]))

(defelement -wrap
  :class [:form-group]
  :style [])

(defelement -error-messages-wrap
  :tag :ul
  :class [:list-unstyled :text-danger :mt-1 :ml-1]
  :style [{:font-size "90%"}])


(defelement -file-input-wrap
  :class [:relative :pointer :bg-blue :p1 :inline-block :c-white :rounded])

(defelement -file-input-dropzone
  :tag :label
  :class [:overflow-hidden :absolute :top-0 :left-0 :right-0 :bottom-0 :flex :justify-center :items-center])

(defelement -file-input
  :tag :input
  :class [:block :absolute :pointer :top-0 :right-0 :left-0 :bottom-0]
  :style [{:min-width "100%"
           :opacity 0
           :text-align "right"
           :min-height "100%"
           :font-size "999px"
           :z-index 5}])

(defelement -file-preview
  :class [:rounded :bw1 :bd-gray :p1 :flex :justify-between :items-center])


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
        errors (forms-ui/errors-in> ctx form-props attr)]
    [-wrap {:class (:wrapper/class props)}
     (when label
       [:label
        {:for   (inputs/get-input-name form-props attr)
         :class [(when (seq errors) "is-invalid")]}
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
                            (when (seq errors) "is-invalid")])])
     [render-errors errors]]))

(defmethod wrapped-input :radio-group [ctx form-props attr props]
  (let [label (:input/label props)
        errors (forms-ui/errors-in> ctx form-props attr)]
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
        errors (forms-ui/errors-in> ctx form-props attr)]
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
