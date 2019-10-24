(ns site.ui
  (:require [fatcap.ui :as fcui]
            [fatcap.ui.composition :as composition]
            [fatcap.util.ui :refer [wrap-renderer configure]]
            [keechma.ui-component :as ui]
            [fatcap.ui.template :as template :refer-macros [deftemplate]]

            [site.ui.main :as main]
            [site.ui.pages.not-found :as not-found]
            [site.ui.pages.loading :as loading]

            [site.ui.layouts.auth]
            [site.ui.layouts.admin]
            [site.ui.layouts.mobile]

            [site.ui.pages.anon.login]
        
            [site.ui.pages.admin.homepage]

            [site.ui.components.header]))

(defn init-component [c]
  (if (not= ui/UIComponent (type c))
    (ui/constructor c)
    c))

(defn init-components [components]
  (into {} (map (fn [[k c]] [k (init-component c)]) components)))

(def ui
  (-> {:main                               site.ui.main/component
       :loading                            site.ui.pages.loading/component
       :not-found                          site.ui.pages.not-found/component

       :layout/auth                        site.ui.layouts.auth/component
       :layout/admin                       site.ui.layouts.admin/component
       :layout/mobile                      site.ui.layouts.mobile/component
       
       :anon/login                         site.ui.pages.anon.login/component
      
       :admin/homepage                    site.ui.pages.admin.homepage/component

       :c/header                           site.ui.components.header/component}

      init-components
      fcui/install))
