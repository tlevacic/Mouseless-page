(ns fatcap.ui.template
  (:require [fatcap.ui-component :as fcui]
            [keechma.ui-component :as ui]
            [fatcap.util.ui :refer [get-config]]))

(declare fill-slot)

(defn keys-in
  "Returns a sequence of all key paths in a given map using DFS walk."
  [m]
  (letfn [(children [node]
            (let [v (get-in m node)]
              (if (map? v)
                (map (fn [x] (conj node x)) (keys v))
                [])))
          (branch? [node] (-> (children node) seq boolean))]
    (->> (keys m)
         (map vector)
         (mapcat #(tree-seq branch? children %)))))

(defn wrap-template [template-fn slots]
  (let [slots-map (into {} (map (fn [k] [(vec (flatten k)) nil]) (keys-in slots)))]
    (with-meta template-fn {::slot? true ::slots slots-map})))

(defn slot? [val]
  (and (fn? val) (::slot? (meta val))))

(defn subslot? [val]
  (and (vector? val) (= fill-slot (first val))))

(defn track-path [ctx name]
  (let [path (or (::path ctx) [])]
    (assoc ctx ::path (conj path name))))

(defn fill-children-slots [ctx children]
  (if (slot? children)
    [children ctx]
    (or children "")))

(defn render-slot-insert [ctx slot-insert]
  (cond
    (fn? slot-insert) [slot-insert ctx]
    (keyword? slot-insert) [(ui/component ctx slot-insert)]
    :else [:<> slot-insert]))

(defn child-slot-filled? [ctx config path]
  (let [slots (get-config ctx config ::slots)
        path-length (count path)
        matching-slots (map second (filter (fn [[k v]] 
                                             (if (< (count k) path-length)
                                               false
                                               (= path (subvec k 0 path-length)))) slots))]
    (some boolean matching-slots)))

(defn fill-slot [ctx config children]
  (let [path (::path ctx)
        slot-insert (get-config ctx config [::slots path])
        optional? (::optional? ctx)]
    (if slot-insert
      (render-slot-insert
       (assoc ctx ::original #(fill-children-slots ctx children)) 
       slot-insert)
      (when (or (not optional?)
                (child-slot-filled? ctx config path))
        (fill-children-slots ctx children)))))

(defn prefix-keys [prefix val]
  (reduce-kv
   (fn [m k v]
     (assoc m (vec (concat prefix k)) v))
   {} val))

(defn add-slots-from-config-templates [slots]
  (reduce-kv 
   (fn [m k v]
     (if (fn? v)
       (if-let [v-slots (::slots (meta v))]
         (merge (prefix-keys k v-slots) m)
         m)
       m)) slots slots))

(defn constructor
  ([component] (constructor component {}))
  ([component config]
   (let [renderer (:renderer component)
         renderer-slots (::slots (meta renderer))
         config-slots (::slots config)
         merged-slots (merge renderer-slots (add-slots-from-config-templates config-slots))
         component-deps (:component-deps component)
         merged-component-deps (vec (concat component-deps (filter keyword? (vals merged-slots))))]
     (fcui/constructor 
      (assoc component :component-deps merged-component-deps)
      (assoc config ::slots merged-slots)))))
