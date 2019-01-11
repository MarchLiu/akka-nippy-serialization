(ns liu.mars.left
  (:import (akka.actor ActorSystem)
           (clojure.lang PersistentArrayMap PersistentVector PersistentList PersistentHashMap)
           (liu.mars ClojureActor)))

(defmulti left (fn [this msg] (.getClass msg)))
(defmethod left PersistentHashMap [this msg]
  (println (str "get a hash map " msg " from remote"))
  (.tell (.getSender this) msg (.getSelf this)))
(defmethod left PersistentArrayMap [this msg]
  (println (str "get a array map " msg " from remote"))
  (.tell (.getSender this) msg (.getSelf this)))
(defmethod left PersistentVector [this msg]
  (println (str "get a vector " msg " from remote"))
  (.tell (.getSender this) msg (.getSelf this)))
(defmethod left PersistentList [this msg]
  (println (str "get a list " msg " from remote"))
  (.tell (.getSender this) msg (.getSelf this)))
(defmethod left String [this msg]
  (println (str "get a String " msg " from remote"))
  (.tell (.getSender this) msg (.getSelf this)))
(defmethod left :default [this msg]
  (println (str "get a uncatch " msg " from remote"))
  (.tell (.getSender this) msg (.getSelf this)))

(defn -main
  [& args]
  (let [system (ActorSystem/create "serialization")
        actor (.actorOf system (ClojureActor/props left) "left")]
    (.registerOnTermination system (reify Runnable
                                     (run [this]
                                       (.stop system actor))))))