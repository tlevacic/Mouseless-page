(ns site.ui.pages.homepage
  (:require [keechma.ui-component :as ui]
            [site.ui.components.pure.inputs :as i]
            [site.ui.components.pure.elements :as e]
            [site.ui.components.header :as header]
            [keechma.toolbox.forms.ui :as forms-ui]
            [site.ui.components.pure.collapsible :as c]
            [reagent.core :as r]
            [site.site-data :as d]
            [site.ui.components.card :refer [render-cards]]
            [garden.stylesheet :refer [at-media]]
            [site.ui.components.carousel :refer [render-carousel]]
            [site.ui.pages.anon.shared :refer [render-errors]]))


(defn render-content [ctx]
  [:div
   [header/render]
   [:div.flex.flex-column.justify-center.align-center
    [e/-title d/title]
    [e/-text {:style {:text-align "center" :color "rgba(18,18,18,.5)"}} d/text]
    [e/-counter "COUNTER"]
    [e/-text {:class ["mt1 italic mb5"] :style {:color "rgba(18,18,18,.5)" :text-align "center"}} "25% off on Hanukkah · 30-Day Money-Back Guarantee\n  "]
    [render-carousel
     ["slika2.png"
      "image.png"
      "slika3.png"
      "slika5.png"
      "slika1.png"]]

    (map (fn [ele]
           (render-cards ele))
         (take 2 d/card-content))

    [:div.col-12
     [e/-big-text
           "Mouseless caters shortcuts
           for your favorite apps in bite-
           sized exercises to you.
           Interactive drills train the
           muscle-memoryrequired
           to have your fingers flying
           across the keyboard."]]

    (map (fn [ele]
           (render-cards ele))
         (drop 2 d/card-content))

    [:div.flex.flex-row.mt4.flex-wrap.lg-col-10.md-col-8.sm-col-12.px2
     (map (fn [ele]
            [:div.lg-col-4.md-col-12.sm-col-12
             [:h1.c-white.pb1 (:title ele)]
             [e/-text {:style {:color "hsla(0,0%,94.9%,.5)" :font-size "1rem"}} (:text ele)]])
          d/three-card-content)]



    [:div.w-80p.mt4
     [:div.lg-col-12.md-col-12.sm-col-12.px4.pt4.flex.flex-column.bg-yellow {:style {:border-radius "10px"}}
      [:div.flex.flex-column
       [:h1.c-black.bold.mb2 "Get it done faster."]
       [:div.flex
        [e/-text {:style {:color "rgba(18,18,18,.5)"}} "Master all of the magic keystrokes for your favorite apps & tools."]
        [:div {:class ["mx-auto"]} [:img {:src "K.png" :class ["col-12"]}]]]]
      [e/-counter "COUNTER"]
      [e/-text {:class ["mt1 italic mb4 center"] :style {:color "rgba(18,18,18,.5)"}} "25% off on Hanukkah · 30-Day Money-Back Guarantee\n  "]]]
    [:div.w-80p.mt4.c-white.mb4
     [:h1.pt3.bold.pb2 {:style {:font-size "44px"
                                :border-bottom "1px solid rgba(242, 242, 242, 0.5)"}} "FAQ"]

     
     [:div.flex.flex-row.flex-wrap
      (map (fn [e]
             [c/render (:title e) (:text e)])
           d/faq)]]

    [:div.w-80p.mt4.flex.flex-row.flex-wrap.py3.mb3 {:style {:border-top    "1px solid rgba(242, 242, 242, 0.5)"
                                                             :border-bottom "1px solid rgba(242, 242, 242, 0.5)"}}
     (map (fn [e]
            [e/-link-element e])
          d/links)]]])


(defn render [ctx]
  [(ui/component ctx :layout/homepage)
   {:fatcap.ui.template/slots
    (-> {[:content] [render-content ctx]})}])

(def component
  {:renderer       render
   :component-deps [:layout/homepage]})