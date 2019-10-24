(ns fatcap.file-upload.ui
  (:require [fatcap.file-upload.core :as core]
            [keechma.toolbox.forms.ui :as forms-ui]
            [oops.core :refer [oget oset!]]))

(defn <on-change-single [ctx form-props attr ev]
  (let [target (oget ev :target)
        file (oget target :?files.?0)]
    (when file
      (oset! target :value nil)
      (forms-ui/<set-value ctx form-props attr (core/->File file)
                           ))))
