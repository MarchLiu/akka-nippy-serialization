package liu.mars;

import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import clojure.lang.PersistentArrayMap;
import clojure.lang.PersistentList;
import clojure.lang.PersistentVector;

public class Right extends AbstractActor {
    private LoggingAdapter log = Logging.getLogger(this.context().system(), this.getClass());
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create().match(PersistentArrayMap.class, msg -> {
            log.info("get a clojure map {} from remote", msg);
        }).match(PersistentVector.class, msg -> {
            log.info("get a clojure vector {} from remote", msg);
        }).match(PersistentList.class, msg -> {
            log.info("get a clojure list {} from remote", msg);
        }).build();
    }

    public static Props props() {
        return Props.create(Right.class, Right::new);
    }

    public static void main(String[] args) {
        var system = ActorSystem.create("serialization");
        var actor = system.actorOf(Right.props(), "right");

        system.registerOnTermination(()->{
            system.stop(actor);
        });
    }
}
