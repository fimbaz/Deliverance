(ns deliverance.views.user
  (:require [deliverance.models.user :as user]
            [noir.cookies :as cookies])
  (:use [noir.core :only [defpartial defpage]]))

(defpage [:get "/user/:username"] {:keys [username]}
  (cookies/put! :been-here "yesooo")
  (if (user/name-taken? username)
    (str "Hey ho " username (cookies/get :been-here))
    (str "I don't believe we've met, " username)))

(defpage [:post "/user"] {:keys [username password email]}
  (user/create username password email))

