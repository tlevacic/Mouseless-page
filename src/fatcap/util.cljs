(ns fatcap.util)

(defn as-vec [val]
  (if (sequential? val) (vec val) [val]))
