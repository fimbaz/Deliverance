(ns deliverance.models.session
  (:require [clojure.java.jdbc :as sql]
            [deliverance.models.common :as common]
            [deliverance.models.user :as user]))

(defn valid-uuid? [uuid]
  (if (nil? uuid)
    false
    (not (nil? (re-matches #"\p{XDigit}{8}-\p{XDigit}{4}-\p{XDigit}{4}-\p{XDigit}{4}-\p{XDigit}{12}" uuid)))))


(defn create [username ip]
  (let [new-uuid (.toString (java.util.UUID/randomUUID))]
    (sql/with-connection common/cxn
      (sql/do-prepared
        (str "INSERT INTO sessions (username,key,expiration,ip) VALUES"
             "(?,"
             "uuid(?),"
             "current_timestamp + interval '1 day',"
             "inet(?));") [username new-uuid ip]))
    new-uuid))

(defn info [uuid ip]
  (if (valid-uuid? uuid)
    (sql/with-connection common/cxn
      (sql/with-query-results res
       ["SELECT username,expiration > current_timestamp FROM sessions WHERE key=uuid(?) AND ip=inet(?)"
        uuid ip]
       (let [result (into {} res)
             valid (if (result :?column?) true false)]
         (if valid
           (result :username)
           nil))))))
            
