(ns site.ui.components.pure.modal
  (:require [react-aria-modal]
            [oops.core :refer [ocall]]))

(defn render [& args]
  (let [first-arg (first args)
        has-props? (or (nil? first-arg) (map? first-arg))
        props (when has-props? first-arg)
        children (if has-props? (rest args) args)]
    [:> react-aria-modal
     (merge {:title-text (str (gensym :modal))
             :get-pplication-node #(ocall js/document :getElementById "app")
             :focus-dialog true
             :vertically-center true}
            props)
     (into [:div.bg-white.sh5] children)]))
