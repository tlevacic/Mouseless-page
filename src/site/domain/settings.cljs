(ns site.domain.settings)

(def gql-endpoint
  (if js/goog.DEBUG
    "http://localhost:3000/graphql"
    "http://localhost:3000/graphql"))

(def jwt-ls-name "init-jwt")
