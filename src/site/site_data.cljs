(ns site.site-data)

(def title "Keechma Framework")
(def text "Keechma is pluggable micro framework for Reagent written in ClojureScript.")

;todo add functional counter

(def faq
  [{:title "Is there a list of supported apps "
    :text  "Mouseless comes with exercises for Gmail, Slack, Adobe XD, Figma, Sketch, VS Code, Bear, Google Docs, iA Writer, Notion, Things, Trello, Chrome, Finder, Firefox and macOS.\n\nHowever, the menu bar dropdown supports every app that is currently open."}
   {:title "Do you offer a trial "
    :text  "We didn’t implement a trial yet, but no worries—feel free to purchase the app. If it’s not for you send a short message. We’ll refund your order."}
   {:title "The menubar app doesn’t work. What can I do"
    :text  "Mouseless requires the accessibility permission in “System Preferences › Security & Privacy › Privacy › Accessibility”.\n\nFor macOS 10.15 (Catalina) the screen recording permission in “System Preferences › Security & Privacy › Privacy › Screen Recording” is required too. Don’t worry—we don’t record your screen."}
   {:title "Is there a Windows app "
    :text  "Nope, not for now. But give us a shout and we’ll consider it."}
   {:title "Something’s wrong. How do I get in touch "
    :text  "Drop us a line, we’ll be happy to help!"}])

(def card-content
  [{:order 1
    :title "Interactive Training"
    :text "A session takes less than 5 minutes and covers about 10 shortcuts. Apply your new skills right away to reinforce your learning experience."
    :img "https://picsum.photos/id/124/300/250"}
   {:order 2
    :title "Boost your productivity"
    :text "Stop chasing your mouse and save up to 8 days a year. Surely, you’ll find something better to do with your time."
    :img "https://picsum.photos/id/125/300/250"}
   {:order 1
    :title "Need to cheat?"
    :text "Look up a shortcut within your current app. Works with every app you’ve ever installed."
    :img "https://picsum.photos/id/126/300/250"}
   {:order 2
    :title "All your favorites in one place"
    :text "Stop chasing your mouse and save up to 8 days a year. Surely, you’ll find something better to do with your time."
    :img "https://picsum.photos/id/127/300/250"}])

(def links
  ["Code" "Shortcuts" "Trello" "Shortcuts" "Things" "Shortcuts" "Slack" "Sketch" "Notion" "Shortcuts" "macOS" "Shortcuts" "Bear" "Shortcuts" "Google" "Docs" "Shortcuts" "Gmail" "Shortcuts" "Firefox"])

(def three-card-content
  [{:title "1.000+ Shortcuts"
    :text "With our database you’ve got more than 1.000 keyboard commands at your fingertips, subdivided by app and category."}
   {:title "Keyboard Optimization"
    :text "Mouseless translates shortcuts to the language requirements of your keyboard."}
   {:title "Offline Support"
    :text "No Wi-fi – no problem. Mouseless runs just as smoothly in a rustic cabin as on a plane."}])