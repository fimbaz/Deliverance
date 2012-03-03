(ns deliverance.views.home
  (:require [deliverance.views.common :as common]
            [noir.content.getting-started]
            [noir.cookies :as cookies]
            [noir.request :as request]
            [deliverance.models.session :as session]
            [deliverance.models.user :as user])
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers :only [include-css]]
        [cssgen]))

(def sample-request-map {:user "Bong-O-Bob"
                         :location "Chatsworth, CA"
                         :delivered-by "9:00 PM"
                         :willing-to-pay "10 bucks"
                         :notes "2 Supreme Chalupas from Taco Bell, a large Mountain Dew, 8 fire sauces."
                         :phone-number "805-791-5389"
                         :phone-number-public? false})

(def colors (shuffle common/colors))
(def style-css
  (css
   (rule "body"
         :background-color (nth colors 0))
   (rule "#banner"
         :text-align :center
         :color (nth colors 1)
         :font-size "40px")
   (rule "#subtitle"
         :color (nth colors 2)
         :font-size "20px"
         :text-align :center)
   (rule "#main-board"
         :width  "80%"
         :float :left)
   (rule "#nav"
         :background-color (nth colors 3)
         :width "20%"
         :height "100%"
         :float :left)
   (rule "#want-to-deliver"
         :width "50%"
         :float :left)
   (rule "#want-a-delivery"
         :float :left
         :width "50%")
   (rule "#welcome-user"
         :float :right)
   (rule "#banner"
         :clear :both)))
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



(defpartial not-signed-in-welcome []
  [:a {:href "/user/"} "Register"] "/"
  [:a {:href "/login/"} "Sign in"])

(defpartial signed-in-welcome [username]
  (str "Hello " username))


(defpartial welcome-user []
  (let [username (session/info
                  (cookies/get :deliverance-session)
                  ((request/ring-request) :remote-addr))]
    [:h3#welcome-user
     (if username
       (signed-in-welcome username)
       (not-signed-in-welcome))]))

(defpartial header [page]
    [:head
     [:title "Deliverance!"]
     (include-css "/css/reset.css")
     [:style style-css]
     [:body
      page]])
   


(defpartial mantra []
  [:h1#banner "Deliverance!"]
  [:h2#subtitle "Get some schmuck from across the street to bring you Taco Bell or whatever"])

  
(defpage "/index.html" []
  (header
   [:span
    (welcome-user)
    (mantra)
    (nav)
    (main-board)]))
  