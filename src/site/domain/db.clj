(ns site.domain.db)

(defn make-get-in-kv [path])
(defn make-assoc-in-kv [path])
(defn make-dissoc-in-kv [path])

(defmacro defkvaccess [& attrs]
  (let [defs (mapv
                (fn [attr]
                  (let [[base path]
                        (if (keyword? attr)
                          [(name attr) attr]
                          [(name (first attr)) (second attr)])
                        dissoc-name (symbol (str "dissoc-" base))
                        assoc-name (symbol (str "assoc-" base))
                        get-name (symbol (str "get-" base))]
                    
                    [`(def ~dissoc-name (site.domain.db/make-dissoc-in-kv ~path))
                     `(def ~get-name (site.domain.db/make-get-in-kv ~path))
                     `(def ~assoc-name (site.domain.db/make-assoc-in-kv ~path))]))
                attrs)]
    `(do ~@(apply concat defs))))
