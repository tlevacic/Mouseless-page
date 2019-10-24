(ns site.edb
  (:require [entitydb.core]
            [keechma.toolbox.edb :refer-macros [defentitydb]]
            [site.domain.file-upload :as file-upload]
            [clojure.walk :refer [prewalk]]
            [com.rpl.specter :as s]))

(defn wrap-image [i]
  (when i
    (file-upload/->UploadedFile i)))

(defn flatten-connections [data]
  (prewalk
   (fn [x]
     (if (and (map? x) (:edges x))
       (mapv :node (:edges x))
       x))
   data))

(def edb-schema 
  {})

(defentitydb edb-schema)
