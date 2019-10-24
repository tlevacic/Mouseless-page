(def foreign-libs
  '[{:file     "dist/index.js"
     :file-min "dist/index.js"
     :provides ["react"
                "react-dom"
                "react-aria-modal"
                "moment"]

     :global-exports {react            React
                      react-dom        ReactDOM
                      react-aria-modal ReactAriaModal
                      moment           Moment}}])

(defproject site "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.339"]
                 [reagent "0.8.1"]
                 [org.clojars.mihaelkonjevic/path-matcher "0.1.0"]
                 [com.rpl/specter "1.1.2"]
                 [org.clojure/core.match "0.3.0"]
                 [keechma/entitydb "0.1.6"]
                 [org.clojars.mihaelkonjevic/garden-basscss "0.2.2"]
                 [binaryage/oops "0.6.2"]
                 [medley "1.0.0"]
                 [floatingpointio/graphql-builder "0.1.8"]
                 [keechma/forms "0.1.5"]
                 [hodgepodge "0.1.3"]
                 [reagent-utils "0.3.3"]
                 [keechma "0.3.13" :exclusions [cljsjs/react-with-addons cljsjs/react-dom cljsjs/react-dom-server]]
                 [keechma/toolbox "0.1.24" :exclusions [cljsjs/react-with-addons cljsjs/react-dom cljsjs/react-dom-server]]]

  :min-lein-version "2.5.3"

  :source-paths ["src"]

  :plugins [[lein-cljsbuild "1.1.7"]]

  :clean-targets ^{:protect false} ["resources/public/js"
                                    "target"
                                    "test/js"]

  :figwheel {:css-dirs ["resources/public/css"]}


  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :profiles
  {:dev
   {:dependencies [[figwheel-sidecar "0.5.10"]
                   [com.cemerick/piggieback "0.2.1"]
                   [binaryage/devtools "0.8.2"]]

    :plugins      [[lein-figwheel "0.5.16"]
                   [lein-doo "0.1.7"]]}}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src"]
     :figwheel     {:on-jsload "site.core/reload"}
     :compiler     {:main                 site.core
                    :optimizations        :none
                    :output-to            "resources/public/js/app.js"
                    :output-dir           "resources/public/js/dev"
                    :asset-path           "js/dev"
                    :source-map-timestamp true
                    :preloads             [devtools.preload]
                    :external-config
                    {:devtools/config
                     {:features-to-install    [:formatters :hints]
                      :fn-symbol              "F"
                      :print-config-overrides true}}
                    :infer-externs        true
                    :npm-deps             false
                    :foreign-libs ~foreign-libs}}

    {:id           "min"
     :source-paths ["src"]
     :compiler     {:main            site.core
                    :optimizations   :advanced
                    :output-to       "resources/public/js/app.js"
                    :output-dir      "resources/public/js/min"
                    :elide-asserts   true
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false
                    :infer-externs        true
                    :npm-deps             false
                    :pseudo-names true
                    :foreign-libs ~foreign-libs}}

    {:id           "test"
     :source-paths ["src/cljs" "test/cljs"]
     :compiler     {:output-to     "resources/public/js/test.js"
                    :output-dir    "resources/public/js/test"
                    :main          site.runner
                    :optimizations :none
                    :infer-externs        true
                    :npm-deps             false
                    :foreign-libs ~foreign-libs}}
    ]})
