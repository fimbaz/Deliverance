(ns deliverance.views.user
  (:require [deliverance.models.user :as user]
            [noir.cookies :as cookies])
  (:use [noir.core :only [defpartial defpage]]
        [hiccup.core]
        [deliverance.views.common]))



(defpartial new-user-form [{:keys [username-message password-message email-address-message]}]
  [:form {:name "new-user-form"
          :action "/user/"
          :method "POST"}
   [:table
    [:tr
     [:td "Username:"][:td [:input {:name "username"
                                    :type "text"}] username-message]] 
    [:tr
     [:td"Password:"][:td [:input {:name "password"
                                   :type "password"}] password-message]]
    [:tr
     [:td "Repeat Password:"][:td [:input {:name "repeat-password"
                                           :type "password"}]]]
    [:tr
     [:td "Email Address:"][:td [:input {:name "email-address"
                                         :type "text"}] email-address-message]]
    
    [:tr [:td [:input {:value "join uss..." :name "submit" :type "submit"}]]]]])
                       
      


(defpage [:get "/user/"] {:keys [username]}
  (new-user-form {}))
  
(defn form-rule [messages pred field message]
  (if (pred)
    (assoc messages field message)
    messages))


(defpage [:post "/user/"] {:keys [username password repeat-password email]}
  (-> {}
      (form-rule
       #(not (user/is-valid? username))
       :username-message "username is not valid!")
      (form-rule
       #(user/is-taken? username)
       :username-message "username is taken!")
      (form-rule
       #(not (= password repeat-password))
       :password-message "passwords do not match!")
      (form-rule
       #(not (user/is-password-valid? password))
       :password-message "password is invalid")
      (form-rule
       #(= 0 (count password))
       :password-message "you forgot to enter a password, bro!")
      (new-user-form)))
      

