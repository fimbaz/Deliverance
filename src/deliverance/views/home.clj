(ns deliverance.views.home
  (:require [deliverance.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.core :only [html]]
        [cssgen]))

(def sample-request-map {:user "Bong-O-Bob"
                         :location "Chatsworth, CA"
                         :delivered-by "9:00 PM"
                         :willing-to-pay "10 bucks"
                         :notes "2 Supreme Chalupas from Taco Bell, a large Mountain Dew, 8 fire sauces."
                         :phone-number "805-791-5389"
                         :phone-number-public? false})
(def style-css
  (css
   (rule "#banner"
         :text-align :center)
   (rule "#subtitle"
         :text-align :center)
   (rule "#main-board"
         :width  "80%"
         :float :left)
   (rule "#nav"
         :width "20%"
         :height "100%"
         :float :left)
   (rule "#want-to-deliver"
         :width "50%"
         :float :left)
   (rule "#want-a-delivery"
         :float :left
         :width "50%")))

(defpartial nav []
  [:div#nav
   (interpose [:br]
              [[:a {:href "google.com"} "About Us"]
               [:a {:href "google.com"} "Local Food"]
               [:a {:href "google.com"} "FAQ"]
               [:a {:href "google.com"} "Contact"]
               [:a {:href "google.com"} "Donate"]])])

(defpartial delivery-request [request-map]
  (interpose [:br] [
                    [:span.location     (request-map :location)]
                    [:span.delivered-by (request-map :delivered-by)]
                    [:span.willing-to-pay (request-map :willing-to-pay)]
                    [:span.notes (request-map :notes)]
                    [:span.phone-number (request-map :phone-number)]]))


(defpartial want-to-deliver []
  [:table#want-to-deliver
   [:tr
    [:td "I want to deliver"]]])

(defpartial want-a-delivery []
  [:table#want-a-delivery
   [:tr
    [:td "I want a delivery"]]
   [:tr
    [:td (delivery-request sample-request-map)]]])

(defpartial main-board []
  [:div#main-board (want-to-deliver) (want-a-delivery)])



(defpartial header [page]
    [:head
     [:title "Deliverance!"]
     [:style style-css]
     [:body
      page]])
   


(defpartial mantra []
  [:h1#banner "Deliverance!"]
  [:h2#subtitle "Get some schmuck from across the street to bring you Taco Bell or whatever"])

  
(defpage "/index.html" []
  (header
   [:div
    (mantra)
    (nav)
    (main-board)]))
  