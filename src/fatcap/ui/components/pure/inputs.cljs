(ns fatcap.ui.components.pure.inputs
  (:require [reagent.core :as r]
            [keechma.toolbox.forms.ui :as forms-ui]
            [oops.core :refer [oget]]))

(defn get-element-props [default-props props]
  (let [element-props (into {} (filter (fn [[k v]] (simple-keyword? k)) props))]
    (reduce-kv
     (fn [m k v]
       (let [prev-v (get k m)
             val (cond
                   (and (fn? prev-v) (fn? v))
                   (fn [& args] (apply prev-v args) (apply v args))
                   
                   (and (= :class k) (:class m)) 
                   (flatten [v (:class m)])
                   
                   :else v)]
         (assoc m k val)))
     
     default-props
     props)))

(defn get-input-name [[form-type _] attr]
  (str "i-" (hash attr)))

(defn make-input-with-composition-support [tag]
  ;; This function implements input fields that handle composition based inputs correctly
  (fn [props]
    (let [el-ref-atom (atom nil)
          composition-atom? (atom false)]
      (r/create-class
       {:reagent-render (fn [props]
                          (let [props-ref  (or (:ref props) identity)
                                props-on-change (or (:on-change props) identity)
                                props-value (:value props)
                                props-without-value (dissoc props :value)]
                            [tag (merge props-without-value
                                        {:on-change (fn [e]
                                                      (when-not @composition-atom?
                                                        (props-on-change e)))
                                         :on-composition-start #(reset! composition-atom? true)
                                         :on-composition-update #(reset! composition-atom? true)
                                         :on-composition-end (fn [e]
                                                               (reset! composition-atom? false)
                                                               (props-on-change e))
                                         :default-value props-value
                                         :ref (fn [el]
                                                (reset! el-ref-atom el)
                                                (props-ref el))})]))
        :component-will-update (fn [comp [_ new-props _]]
                                 (let [el @el-ref-atom
                                       composition? @composition-atom?]
                                   (when (and el (not composition?))
                                     (set! (.-value el) (or (:value new-props) "")))))}))))

(def input-with-composition-support (make-input-with-composition-support :input))
(def textarea-with-composition-support (make-input-with-composition-support :textarea))

(defmulti input
  (fn [_ _ _ props] 
    (or (:input/type props) :text)))

(defmethod input :default [_ _ _ _])

(defmethod input :text [ctx form-props attr props]
  (let [errors (forms-ui/errors-in> ctx form-props attr)]
    [input-with-composition-support
     (get-element-props
      {:on-change #(forms-ui/<on-change ctx form-props attr %)
       :on-blur   #(forms-ui/<on-blur ctx form-props attr %)
       :value     (forms-ui/value-in> ctx form-props attr)
       :name      (get-input-name form-props attr)
       :id        (get-input-name form-props attr)
       :class     (when (seq errors) :has-errors)}
      props)]))

(defmethod input :textarea [ctx form-props attr props]
  (let [errors (forms-ui/errors-in> ctx form-props attr)]
    [textarea-with-composition-support
     (get-element-props
      {:on-change #(forms-ui/<on-change ctx form-props attr %)
       :on-blur   #(forms-ui/<on-blur ctx form-props attr %)
       :value     (forms-ui/value-in> ctx form-props attr)
       :name      (get-input-name form-props attr)
       :id        (get-input-name form-props attr)
       :class     (when (seq errors) :has-errors)}
      props)]))

(defmethod input :select [ctx form-props attr props]
  (let [{:keys [select/options select/optgroups select/placeholder]} props]
    (let [errors (forms-ui/errors-in> ctx form-props attr)]
      [:select
       (get-element-props
        {:on-change #(forms-ui/<on-change ctx form-props attr %)
         :on-blur   #(forms-ui/<on-blur ctx form-props attr %)
         :value     (or (forms-ui/value-in> ctx form-props attr) "")
         :name      (get-input-name form-props attr)
         :id        (get-input-name form-props attr)
         :class     (when (seq errors) :has-errors)}
        props)
       (when placeholder
         [:<>
          [:option {:value ""} placeholder]
          [:option {:value ""} "-"]])
       (if optgroups
         (map 
          (fn [{:keys [label options]}]
            (when (seq options)
              [:optgroup {:label label :key label}
               (map 
                (fn [{:keys [value label]}]
                  [:option 
                   {:value value 
                    :key   value} 
                   label]) 
                (sort-by :label options))]))
          (sort-by :label optgroups))
         (map 
          (fn [{:keys [value label]}]
            [:option 
             {:value value 
              :key   value} 
             label]) 
          (sort-by :label options)))])))

(defmethod input :checkbox [ctx form-props attr props]
  (let [errors (forms-ui/errors-in> ctx form-props attr)]
    [:input
     (get-element-props
      {:type      "checkbox"
       :on-change (fn [e]
                    (let [value    (:value props)
                          checked? (oget e :target.checked)
                          set-value
                          (cond 
                            (and value checked?)       value 
                            (and (not value) checked?) true
                            :else                      nil)]
                      (forms-ui/<set-value ctx form-props attr set-value))) 
       :checked   (boolean (forms-ui/value-in> ctx form-props attr))
       :value     (or (forms-ui/value-in> ctx form-props attr) "")
       :name      (get-input-name form-props attr)
       :id        (get-input-name form-props attr)
       :class     (when (seq errors) :has-errors)}
      props)]))

(defn render [ctx form-props attr props]
  [input ctx form-props attr props])
