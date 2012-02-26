(ns deliverance.test
  (:require [deliverance.models.user :as user])
  (:use  [lein-test.core] :reload)
  (:use [clojure.test]))


(deftest basic-user-manipulation
  (fact (user/create "test" "testing123" "testuser@test.com")
        => true)
  
  (fact (user/name-taken? "test")
        => true)
  
  (fact (user/delete "test")
        => true)
  
  (fact (user/name-taken? "test")
        => false))