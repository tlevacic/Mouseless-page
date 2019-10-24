(ns fatcap.config)

#_(defn configure
  ([root] (configure root root))
  ([root leaf]
   (println "------------" (map? leaf))
   (if (map? leaf)
     (reduce-kv
      (fn [m k v]
        (println "!!!!!"))
      {:root root
       :leaf {}}
      leaf)
     root)))
