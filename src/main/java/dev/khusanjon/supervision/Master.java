package dev.khusanjon.supervision;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Master extends AbstractBehavior<String> {

    public static Behavior<String> create() {
        return Behaviors.setup(Master::new);
    }

    private final ActorRef<String> worker;

    private Master(ActorContext<String> context) {
        super(context);
        worker =
             context.spawn(
                 Behaviors.supervise(Slave.create()).onFailure(SupervisorStrategy.restart()),
                 "supervised-actor");
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("failWorker", this::onFailWorker)
                .onMessageEquals("stop", Behaviors::stopped)
                .onSignal(PostStop.class, signal -> onPostStop())
                .build();
    }

    private Behavior<String> onFailWorker() {
        worker.tell("fail-now");
        return this;
    }

    private Behavior<String> onPostStop() {
        System.out.println("Master stopped!!!");
        return this;
    }
}
