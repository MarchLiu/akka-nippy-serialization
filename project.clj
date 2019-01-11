(defproject serialization "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src/main/clojure"]
  :java-source-paths ["src/main/java"]
  :test-paths ["src/test/clojure" "src/test/java"]
  :resource-paths ["resources/main"]
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.typesafe.akka/akka-actor_2.12 "2.5.19"]
                 [com.typesafe.akka/akka-remote_2.12 "2.5.19"]
                 [liu.mars/jaskell "0.1.2"]
                 [liu.mars/akka-clojure "0.1.0"]
                 [com.taoensso/nippy "2.14.0"]
                 [com.github.romix.akka/akka-kryo-serialization_2.12 "0.5.2"]]
  :junit ["src/test/java"]
  :profiles {:left    {:main     liu.mars.left
                       :jvm-opts ["-Dconfig.resource=left.conf"]}
             :right   {:main     liu.mars.Right
                       :jvm-opts ["-Dconfig.resource=right.conf"]}
             :test    {:dependencies      [[junit/junit "4.12"]
                                           [com.typesafe.akka/akka-testkit_2.12 "2.5.19"]]
                       :resource-paths    ["resources/test"]
                       :java-source-paths ["src/test/java"]
                       :jvm-opts ["-Dconfig.resource=test.conf"]}
             :gorilla {:source-paths ["src/notebook"]
                       :plugins      [[org.clojars.benfb/lein-gorilla "0.5.3"]]}})
