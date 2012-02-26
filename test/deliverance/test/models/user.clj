(ns deliverance.test.models.user
  (:use [clojure.test]
        [deliverance.models.user]))

(deftest basic-user-manipulation
  (is (= true (create "testuser" "cheese" "testuser@test.ing")))
  (is (= true (name-taken? "testuser")))
  (is (= true (password-ok?  "testuser" "cheese")))
  (is (= false (password-ok? "testuser" "please")))
  (is (= true (delete "testuser")))
  (is (= false (name-taken? "testuser"))))
