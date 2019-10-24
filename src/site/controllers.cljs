(ns site.controllers
  (:require [site.controllers.initializer :as initializer]
            [site.controllers.actions :as actions]))

(def controllers
  (-> {}
      initializer/register
      actions/register))
