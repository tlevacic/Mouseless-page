(ns fatcap.file-upload.core
  (:require [oops.core :refer [ocall oget]]))

(def file-preview$ (atom {}))

(defprotocol IFile
  (preview [_])
  (release [_])
  (file [_])
  (filename [_])
  (content-type [_]))

(deftype File [handle]
  IFile
  (preview [_]
    (if-let [preview (get @file-preview$ handle)]
      preview
      (try
        (let [preview (ocall js/URL :createObjectURL handle)]
          (swap! file-preview$ assoc handle preview)
          preview)
        (catch :default e
          nil))))
  (file [_] handle)
  (filename [_]
    (oget handle :name))
  (content-type [_]
    (oget handle :type))
  (release [this]
    (let [preview (get @file-preview$ handle)]
      (ocall js/URL :revokeObjectURL preview)
      (swap! file-preview$ dissoc handle))))

(defn get-files [root]
  (let [f (fn rec [node acc]
            (reduce-kv 
             (fn [acc k v]
               (cond
                 (= File (type v)) (conj acc v)
                 (map? v) (rec v acc)
                 (vector? v) (vec (concat acc (mapcat #(rec % []) v)))
                 :else acc))
             acc
             node))]
     (f root [])))
