(ns site.ui.pages.homepage
  (:require [keechma.ui-component :as ui]
            [site.ui.components.pure.inputs :as i]
            [site.ui.components.pure.elements :as e]
            [site.ui.components.header :as header]
            [keechma.toolbox.forms.ui :as forms-ui]
            [site.ui.pages.anon.shared :refer [render-errors]]))

(def title "Keechma Framework")
(def text "Keechma is pluggable micro framework for Reagent written in ClojureScript.")


(defn render-content [ctx]
  [:div
   [header/render]
   [:div.flex.flex-column.justify-center.align-center
    [e/-title title]
    [e/-text {:style {:text-align "center" :color "rgba(18,18,18,.5)"}} text]
    [e/-counter "COUNTER"]
    [e/-text {:class ["mt1 italic mb5 center"] :style {:color "rgba(18,18,18,.5)"}} "25% off on Hanukkah · 30-Day Money-Back Guarantee\n  "]
    [e/-mihael {:src "image.png"}]
    [e/-card
     [:div [e/-card-picture {:src "slika1.png"}]]
     [:div.pl5
      [e/-card-title "Interactive Training"]
      [e/-text {:style {:color "hsla(0,0%,94.9%,.5)"}} "A session takes less than 5 minutes and covers about 10 shortcuts. Apply your new skills right away to reinforce your learning experience."]]]
    [e/-card
     [:div.pr5
      [e/-card-title "Boost your productivity"]
      [e/-text {:style {:color "hsla(0,0%,94.9%,.5)"}} "Stop chasing your mouse and save up to 8 days a year. Surely, you’ll find something better to do with your time."]]
     [:div [e/-card-picture {:src "slika2.png"}]]]
    [:pre {:class ["c-white fs-45 mt5 fw-700"] :style {:line-height "1.2"}}
     "     Mouseless caters shortcuts
     for your favorite apps in bite-
     sized exercises to you.
     Interactive drills train the
     muscle-memoryrequired
     to have your fingers flying
     across the keyboard."]
    [e/-card
     [:div [e/-card-picture {:src "slika3.png" :style {:width "350px"}}]]
     [:div.pl5
      [e/-card-title "Need to cheat?"]
      [e/-text {:style {:color "hsla(0,0%,94.9%,.5)"}} "Look up a shortcut within your current app. Works with every app you’ve ever installed."]]]
    [e/-card
     [:div.pr5
      [e/-card-title "All your favorites in one place"]
      [e/-text {:style {:color "hsla(0,0%,94.9%,.5)"}} "Stop chasing your mouse and save up to 8 days a year. Surely, you’ll find something better to do with your time."]]
     [:div
      [e/-card-picture {:src "slika5.png" :style {:width "350px"}}]]]
    [:div.flex.flex-row.justify-center.align-center.mt3.clearfix
     [:div.lg-col-4
      [:h1 "1.000+ Shortcuts"]
      [e/-text {:style {:color "hsla(0,0%,94.9%,.5)" :font-size "1rem"}} "With our database you’ve got more than 1.000 keyboard commands at your fingertips, subdivided by app and category."]]
     [:div.lg-col-4
      [:h1 "Keyboard Optimization"]
      [e/-text {:style {:color "hsla(0,0%,94.9%,.5)" :font-size "1rem"}} "Mouseless translates shortcuts to the language requirements of your keyboard."]]
     [:div.lg-col-4
      [:h1 "Offline Support"]
      [e/-text {:style {:color "hsla(0,0%,94.9%,.5)" :font-size "1rem"}} "No Wi-fi – no problem. Mouseless runs just as smoothly in a rustic cabin as on a plane."]]]
    ]])


(defn render [ctx]
  [(ui/component ctx :layout/homepage)
   {:fatcap.ui.template/slots
    (-> {[:content] [render-content ctx]})}])

(def component
  {:renderer       render
   :component-deps [:layout/homepage]})
