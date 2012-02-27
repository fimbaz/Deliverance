(ns deliverance.views.user
  (:require [deliverance.models.user :as user]
            [noir.cookies :as cookies]
            [noir.validation :as vali]
            [deliverance.views.home :as home])
  (:use [noir.core :only [defpartial defpage render]]
        [noir.response :only [redirect]]
        [hiccup.core]
        [deliverance.views.common]))



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


(defpage [:get "/test/"] []
  (vali/rule false [:test "you sock!"])
  (test))



(defpage [:get "/user/"] {:keys [username]}
  (new-user-form))
  
(defn form-rule [messages pred field message]
  (if (pred)
    (assoc messages field message)
    messages))



(vali/rule #(user/is-valid? "fim!!") [:username "sucks"])
(defpage [:post "/user/"] {:keys [username password repeat-password email]}
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
   [:password "you forgot to enter a password, bro!"])
  (if (not (vali/errors? :username :password :email))
    (do (user/create username password email)
        (redirect "/index.html"))
    (new-user-form)))
  




