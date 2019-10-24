(ns site.domain.file-upload
  (:require [fatcap.file-upload.core :as file-upload :refer [get-files]]))

(deftype UploadedFile [uploaded-file]
  file-upload/IFile
  (preview [_]
    (or (:publicUrl uploaded-file) (:presignedGetUrl uploaded-file)))
  (filename [_]
    (:name uploaded-file))
  (content-type [_]
    (:contentType uploaded-file))
  (file [_] uploaded-file)
  (release [_]))
