(ns deliverance.models.user
  (:require [clojure.java.jdbc :as sql]
            [deliverance.models.common :as common]))

(defn create [{:keys [username bounty price note time-to-live]}]
  (sql/with-connection common/cxn
    (try
      (sql/do-prepared
       (str "INSERT INTO requests (username,bounty,price,note,time_to_live,creation_time)"
            "VALUES (?,?,?,?,?::interval,current_timestamp)")
       [username bounty price note time-to-live])
      (catch java.sql.BatchUpdateException p (sql/print-sql-exception-chain p)))))

(defn get [username]
  (sql/with-connection common/cxn
    (sql/with-query-results res
      ["SELECT * FROM requests WHERE username=?"
       username]
      (into [] res))))

(get "fimbaz")
(defn get-active []
  (sql/with-connection common/cxn
    (sql/with-query-results res
      ["SELECT * FROM requests WHERE creation_time + time_to_live > now"]
      (into [] res))))

(get-active)
(create
 {:username "fimbaz"
  :bounty 5.00
  :price 0.50
  :note "shave and a haircut"
  :time-to-live "1 day"})
              