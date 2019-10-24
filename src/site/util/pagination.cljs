(ns site.util.pagination
  (:require [keechma.ui-component :as ui]
            [keechma.toolbox.css.core :refer-macros [defelement]]
            [keechma.toolbox.ui :refer [sub> route>]]))

(def page-window 9)

(defn calculate-visible-pages [total current]
  (let [min-page (min 1 (max 1 (- current page-window)))
        max-page total
        around-current (filter #(and (pos? %)
                                     (<= % max-page))
                               (range (- current page-window)
                                      (+ current page-window)))]
    (filter (complement nil?)
            (flatten [(when (< min-page (first around-current)) 1)
                      (when (< min-page (dec (first around-current))) :break-left)
                      around-current
                      (when (> max-page (inc (last around-current))) :break-right)
                      (when (> max-page (last around-current)) max-page)]))))


(defn render-pagination [ctx page-info]
  (let [route (route> ctx)
        offset (:pageSize page-info)
        current (+ 1 (:currentPage page-info))
        visible  (calculate-visible-pages (:totalPages page-info) (:currentPage page-info))]
    (when (and (seq page-info) (< 1 (:totalPages page-info)))
      [:ul.pagination
       (map (fn [page]
              (let [calculated-offset (- (* offset page) offset)
                    base-url (assoc route :offset calculated-offset)]
                (if (or (= page :break-left) (= page :break-right))
                  [:span.page-link "..."]
                  [:li.page-item {:class (if (= page current) :active)
                                  :key   page}
                   [:a.page-link
                    {:href (ui/url ctx base-url)}
                    page]])))
            visible)])))