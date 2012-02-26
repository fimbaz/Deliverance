(ns deliverance.models.common
  (:use clojure.java.jdbc))

(def cxn
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :user "postgres"
   :password "cheese"
   :auto-commit true
   :fetch-size 500
   :subname "//localhost:5432/deliverance"})
