(ns deliverance.models.user
  (:require [clojure.java.jdbc :as sql]
            [deliverance.models.common :as common]))

(defn create [username password email]
  (sql/with-connection common/cxn
    (sql/do-prepared
     (str "INSERT INTO users (username,password,email) VALUES"
          "(?,crypt(?,gen_salt('md5')),?)") [username password email]))
  true)


(defn password-ok? [username password]
  (sql/with-connection common/cxn
    (sql/with-query-results res
      ["SELECT password = crypt(?,password) FROM users WHERE username = ?"
       password username]
      (if ((into {} res) :?column?)
        true
        false))))


(defn is-taken? [username]
  (sql/with-connection common/cxn
    (sql/with-query-results res
      ["SELECT true FROM users WHERE username = ?"
       username]
      (if ((into {} res) :bool)
        true
        false))))


(defn delete [username] 
  (sql/with-connection common/cxn
    (let [modified
          (sql/do-prepared "DELETE FROM users WHERE username = ?"
                           [username])]
      (if (= (first modified) 1)
        true
        false))))

(defn is-valid? [username]
  (if (nil? (re-matches #"[0-9a-zA-Z]{1,32}" username))
    false
    true))

;(defn is-email-taken [email]
;  (sql/with-connection common/cxn
;    (let 
(defn is-password-valid? [password]
  (if (nil? (re-matches #"\w{6,32}" password))
    false
    true))