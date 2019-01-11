(ns liu.mars.basic-test
  (:require [clojure.test :refer :all])
  (:import (akka.actor ActorSystem)
           (akka.testkit.javadsl TestKit)
           (java.util.function Supplier Function)
           (clojure.lang PersistentArrayMap PersistentVector PersistentHashMap)))

(testing "test basic serialization workflow"
  (let [map-message {:1  1 :2 1 :3 2 :4 3 :5 5 :6 8 :7 13 :8 21 :9 33 :10 54
                     :11 [1 1 2 3 5 8 13 21 33 54 87]}
        vec-message [1 1 2 3 5 8 13 21 33 54 87]
        str-message "This is a text message."
        system (ActorSystem/create "test")
        test-kit (TestKit. system)
        self (.getRef test-kit)
        await #(.awaitCond test-kit (reify Supplier (get [this] (.msgAvailable test-kit))))
        remote-actor (.actorSelection system "akka.tcp://serialization@127.0.0.1:25520/user/left")]
    (try
      (.tell remote-actor
             map-message
             self)
      (await)
      (.expectMsgPF test-kit "check map message reply"
                    (reify Function
                      (apply [this message]
                        (is (or
                              (instance? PersistentHashMap message)
                              (instance? PersistentArrayMap message)))
                        (is (= map-message message)))))
      (.tell remote-actor vec-message self)
      (await)
      (.expectMsgPF test-kit "check vector message reply"
                    (reify Function
                      (apply [this message]
                        (is (instance? PersistentVector message))
                        (is (= vec-message message)))))
      (.tell remote-actor str-message self)
      (await)
      (.expectMsgPF test-kit "check text message reply"
                    (reify Function
                      (apply [this message]
                        (testing "text message check"
                          (is (instance? String message))
                          (is (= str-message message))))))
      (finally
        (TestKit/shutdownActorSystem system)))))
