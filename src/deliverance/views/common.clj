(ns deliverance.views.common
  (:use [noir.core :only [defpartial]]
        [clojure.string :only [capitalize]]
        [hiccup.page-helpers :only [include-css html5]]))



(def colors
  ["#E5955C"
   "#F2F2B6"
   "#FF7300"
   "#5C0000"
   "736B99"])


(defpartial layout [& content]
  (html5
   [:head
    [:title "deliverance"]
    (include-css "/css/reset.css")]
   [:body
    [:div#wrapper
     content]]))
