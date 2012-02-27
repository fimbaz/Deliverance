(ns deliverance.views.common
  (:use [noir.core :only [defpartial]]
        [clojure.string :only [capitalize]]
        [hiccup.page-helpers :only [include-css html5]]))


(defpartial layout [& content]
            (html5
              [:head
               [:title "deliverance"]
               (include-css "/css/reset.css")]
              [:body
               [:div#wrapper
                content]]))
