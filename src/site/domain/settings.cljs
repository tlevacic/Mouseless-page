(ns site.domain.settings)

(def gql-endpoint
  (if js/goog.DEBUG
    "https://fakeql.com/graphql/87547972e4e4ca28878e5abb38826fe0"
    "http://localhost:3000/graphql"))

(def jwt-ls-name "init-jwt")
