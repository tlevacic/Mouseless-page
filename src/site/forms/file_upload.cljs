(ns site.forms.file-upload
  (:require [keechma.toolbox.forms.core :as forms-core]
            [keechma.toolbox.pipeline.core :as pp :refer-macros [pipeline!]]
            [fatcap.file-upload.core :as file-upload :refer [get-files]]
            [site.domain.file-upload :as ced-file-upload]
            [oops.core :refer [ocall oget oset!]]
            [promesa.core :as p]
            [site.gql :as gql]
            [site.domain.db :as db]
            [clojure.walk :refer [postwalk-replace postwalk]]))


(defn uploader! [value on-progress ctrl app-db-atom _]
  (let [{:keys [url file]} value]
    (p/promise
     (fn [resolve reject]
       (let [xhr (js/XMLHttpRequest.)
             mime-type (oget file "type")]

         (ocall xhr "upload.addEventListener" "progress"
                (fn [e]
                  (let [app-db @app-db-atom
                        new-app-db (on-progress e value app-db)]
                    (when (not= app-db new-app-db)
                      (reset! app-db-atom new-app-db))))
                false)

         (oset! xhr "onreadystatechange"
                (fn []
                  (when (= 4 (oget xhr "readyState"))
                    (if (= 200 (oget xhr "status"))
                      (resolve)
                      (reject (ex-info "File Upload Failed" {}))))))

         (ocall xhr "open" "PUT" url)
         (ocall xhr "setRequestHeader" "Content-Type" mime-type)
         (ocall xhr "send" file))))))

(defn upload-s3!
  ([value] (upload-s3! value (fn [_ value app-db] app-db)))
  ([value on-progress]
   (with-meta (partial uploader! value on-progress) {:pipeline? true})))

(defn pipeline-all! [pipelines]
  (with-meta
    (fn [controller app-db-atom value _]
      (->> (p/all (map (fn [p] (p controller app-db-atom value)) pipelines))
           (p/map vec)))
    {:pipeline? true}))

(defn upload-file! [file]
  (let [input {:name (file-upload/filename file)
               :contentType (file-upload/content-type file)}]
    (pipeline! [value app-db] 
      (gql/m! [:create-file :createFile] {:input input} (db/get-jwt app-db))
      (upload-s3! {:url (:presignedPutUrl value) :file (file-upload/file file)})
      [file (ced-file-upload/->UploadedFile value)])))

(defn replace-uploaded-files-with-ids [data]
  (postwalk
   (fn [x]
     (if (= ced-file-upload/UploadedFile (type x))
       (:id (file-upload/file x))
       x))
   data))

(defn upload-files! [data]
  (let [files (get-files data)]
    (if (seq files)
      (pipeline! [value app-db]
        (pipeline-all! (map upload-file! files))
        (postwalk-replace (into {} value) data))
      data)))
