(defproject deliverance "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [noir "1.2.1"]
                           [cssgen "0.2.6"]
                           [org.clojure/java.jdbc "0.1.1"]
                           [postgresql/postgresql "8.4-702.jdbc4"]]
            :dev-dependencies [[midje "1.3.1-SNAPSHOT"]]
            :main deliverance.server)

