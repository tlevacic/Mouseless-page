(ns site.ui.pages.homepage
  (:require [keechma.ui-component :as ui]
            [site.ui.components.pure.inputs :as i]
            [site.ui.components.pure.elements :as e]
            [site.ui.components.header :as header]
            [keechma.toolbox.forms.ui :as forms-ui]
            [reagent.core :as r]
            [site.ui.pages.anon.shared :refer [render-errors]]))

(def title "Keechma Framework")
(def text "Keechma is pluggable micro framework for Reagent written in ClojureScript.")


(def faq
  [{:id    0
    :title "Is there a list of supported apps?\n"
    :text  "Mouseless comes with exercises for Gmail, Slack, Adobe XD, Figma, Sketch, VS Code, Bear, Google Docs, iA Writer, Notion, Things, Trello, Chrome, Finder, Firefox and macOS.\n\nHowever, the menu bar dropdown supports every app that is currently open."}
   {:id    1
    :title "Do you offer a trial?\n"
    :text  "We didn’t implement a trial yet, but no worries—feel free to purchase the app. If it’s not for you send a short message. We’ll refund your order."}
   {:id    2
    :title "The menubar app doesn’t work. What can I do?\n"
    :text  "Mouseless requires the accessibility permission in “System Preferences › Security & Privacy › Privacy › Accessibility”.\n\nFor macOS 10.15 (Catalina) the screen recording permission in “System Preferences › Security & Privacy › Privacy › Screen Recording” is required too. Don’t worry—we don’t record your screen."}
   {:id    3
    :title "Is there a Windows app?\n"
    :text  "Nope, not for now. But give us a shout and we’ll consider it."}
   {:id    4
    :title "Something’s wrong. How do I get in touch?\n"
    :text  "Drop us a line, we’ll be happy to help!"}])

(def toggle-map (r/atom [{:id 0 :toggle -1} {:id 1 :toggle -1} {:id 2 :toggle -1}]))

(defn give-toggle []
  (map (fn [e]
         (println "ELEMENT e " e))
       toggle-map))

(defn find-id [id]
  (into {} (filter #(= id (:id %)) toggle-map)))

(defn reset-map [id]
  (map (fn [e]
         (if (= id (:id e))
           (reset! (:toggle e) (* -1 @(:toggle e)))))
       (give-toggle)))


(defn new-map [id]
  (map (fn [e]
         (if (= (:id e) id)
           (assoc e :toggle (* -1 (:toggle e)))))
       toggle-map))

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
     [:div.lg-col-6.md-col-6.sm-col-12
      [e/-card-picture {:src "slika1.png"}]]
     [:div.pl5.lg-col-6.md-col-6.sm-col-12 {:style {:text-align "right"}}
      [e/-card-title "Interactive Training"]
      [e/-text {:style {:color "hsla(0,0%,94.9%,.5)"}} "A session takes less than 5 minutes and covers about 10 shortcuts. Apply your new skills right away to reinforce your learning experience."]]]
    [e/-card
     [:div.pr5.lg-col-6.md-col-6.sm-col-12
      [e/-card-title "Boost your productivity"]
      [e/-text {:style {:color "hsla(0,0%,94.9%,.5)"}} "Stop chasing your mouse and save up to 8 days a year. Surely, you’ll find something better to do with your time."]]
     [:div.lg-col-6.md-col-6.sm-col-12 {:style {:text-align "right"}}
      [e/-card-picture {:src "slika2.png"}]]]
    [:pre {:class ["c-white fs-45 mt5 fw-700"] :style {:line-height "1.2"}}
     "     Mouseless caters shortcuts
     for your favorite apps in bite-
     sized exercises to you.
     Interactive drills train the
     muscle-memoryrequired
     to have your fingers flying
     across the keyboard."]
    [e/-card
     [:div.lg-col-6.md-col-6.sm-col-12 [e/-card-picture {:src "slika3.png" :style {:width "350px"}}]]
     [:div.pl5.lg-col-6.md-col-6.sm-col-12 {:style {:text-align "right"}}
      [e/-card-title "Need to cheat?"]
      [e/-text {:style {:color "hsla(0,0%,94.9%,.5)"}} "Look up a shortcut within your current app. Works with every app you’ve ever installed."]]]
    [e/-card
     [:div.lg-col-6.md-col-6.sm-col-12
      [e/-card-title "All your favorites in one place"]
      [e/-text {:style {:color "hsla(0,0%,94.9%,.5)"}} "Stop chasing your mouse and save up to 8 days a year. Surely, you’ll find something better to do with your time."]]
     [:div.lg-col-6.md-col-6.sm-col-12 {:style {:text-align "right"}}
      [e/-card-picture {:src "slika5.png" :style {:width "350px"}}]]]

    [:div.flex.flex-row.mt4.flex-wrap
     [:div.lg-col-4.md-col-4.sm-col-12 {:style {:text-align "center"}}
      [:h1.c-white "1.000+ Shortcuts"]
      [e/-text {:style {:color "hsla(0,0%,94.9%,.5)" :font-size "1rem"}} "With our database you’ve got more than 1.000 keyboard commands at your fingertips, subdivided by app and category."]]
     [:div.lg-col-4.md-col-4.sm-col-12 {:style {:text-align "center"}}
      [:h1.c-white "Keyboard Optimization"]
      [e/-text {:style {:color "hsla(0,0%,94.9%,.5)" :font-size "1rem"}} "Mouseless translates shortcuts to the language requirements of your keyboard."]]
     [:div.lg-col-4.md-col-4.sm-col-12 {:style {:text-align "center"}}
      [:h1.c-white "Offline Support"]
      [e/-text {:style {:color "hsla(0,0%,94.9%,.5)" :font-size "1rem"}} "No Wi-fi – no problem. Mouseless runs just as smoothly in a rustic cabin as on a plane."]]]
    [:div.w-80p.mt4
     [:div.lg-col-12.md-col-12.sm-col-12.px4.pt4.flex.flex-column.bg-yellow {:style {:border-radius "10px"}}
      [:div.flex.flex.row
       [:div
        [:h1.c-black.bold.mb2 "Get it done faster."]
        [e/-text {:style {:color "rgba(18,18,18,.5)"}} "Master all of the magic keystrokes for your favorite apps & tools."]]
       [:img {:src "K.png" :class ["pl4"]}]]
      [e/-counter "COUNTER"]
      [e/-text {:class ["mt1 italic mb4 center"] :style {:color "rgba(18,18,18,.5)"}} "25% off on Hanukkah · 30-Day Money-Back Guarantee\n  "]]]
    [:div.w-80p.mt4.c-white
     [:h1.pt3.bold.pb2 {:style {:font-size "44px"}} "FAQ"]
     (map (fn [e]
            [:div
             [:button.c-white.bold.pb2.pl0 {:style    {:background-color "#03063D" :font-size "18px" :border "none"}
                                            :on-click (fn []
                                                        (println (new-map (:id e)))
                                                        ;(reset! toggle-map (new-map (:id e)))
                                                        )} (:title e)]
             ])

          faq)]
    ]])


(defn render [ctx]
  [(ui/component ctx :layout/homepage)
   {:fatcap.ui.template/slots
    (-> {[:content] [render-content ctx]})}])

(def component
  {:renderer       render
   :component-deps [:layout/homepage]})