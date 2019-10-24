(ns site.ui.pages.anon.shared
  (:require [site.util.gql :refer [gql-error-messages]]
            [keechma.toolbox.forms.ui :as forms-ui]))

(defn render-errors [ctx form-props]
  (let [form-state (forms-ui/form-state> ctx form-props)]
    (if (= :submit-failed (get-in form-state [:state :type]))
      (let [cause (get-in form-state [:state :cause])]
        (into [:<>] (interpose [:br] (gql-error-messages cause)))))))
