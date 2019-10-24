(ns site.util.gql)

(defn gql-error-messages [error]
  (let [error-data (ex-data error)]
    (map :message error-data)))
