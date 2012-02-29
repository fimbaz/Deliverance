(ns deliverance.views.user
  (:require [deliverance.models.user :as user]
            [noir.cookies :as cookies]
            [noir.validation :as vali]
            [noir.request :as request]
            [deliverance.views.home :as home]
            [noir.cookies :as cookies]
            [deliverance.models.session :as session])
  (:use [noir.core :only [defpartial defpage render]]
        [noir.response :only [redirect]]
        [hiccup.core]
        [deliverance.views.common]))


(defn login! [username password ip]
  (if (user/password-ok? username password)
    (cookies/put! :deliverance-session
                  (session/create username ip))))

(defpartial form-error [message]
  [:b (first message)])

(defpartial new-user-form []
  [:form {:name "new-user-form"
          :action "/user/"
          :method "POST"}
   [:table
    [:tr
     [:td "Username:"][:td [:input {:name "username"
                                    :type "text"}] (vali/on-error :username form-error)]] 
    [:tr
     [:td"Password:"][:td [:input {:name "password"
                                   :type "password"}] (vali/on-error :password form-error)]]
    [:tr
     [:td "Repeat Password:"][:td [:input {:name "repeat-password"
                                           :type "password"}]]]
    [:tr
     [:td "Email Address:"][:td [:input {:name "email-address"
                                         :type "text"}] (vali/on-error :email form-error)]]
    
    [:tr [:td [:input {:value "join uss..." :name "submit" :type "submit"}]]]]])
                       
      

(defpartial error-msg [err]
  [:h1 (first err)])



(defpage [:get "/user/"] {:keys [username]}
  (new-user-form))

(defn validate-form! [{:keys [username password repeat-password email]}]
  (vali/rule
   (user/is-valid? username)
   [:username "username is not valid!"])
  (vali/rule
   (not (user/is-taken? username))
   [:username "username is taken!"])
  (vali/rule
   (= password repeat-password)
   [:password "passwords do not match!"])
  (vali/rule
   (user/is-password-valid? password)
   [:password "password is invalid"])
  (vali/rule
   (not (= 0 (count password)))
   [:password "you forgot to enter a password, bro!"]))


(defpage [:post "/user/"] {:as fields}
  (noir-session/put! :beeeep "beeeep")
  (println (request/ring-request))
  (validate-form! fields)
  (if (not (vali/errors? :username :password :email))
    (do (user/create
         (fields :username)
         (fields :password)
         (fields :email))
        (login! (fields :username)
                (fields :password)
                ((request/ring-request) :remote-addr))
        (redirect "/index.html"))
    (new-user-form)))