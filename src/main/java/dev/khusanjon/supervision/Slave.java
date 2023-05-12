package dev.khusanjon.supervision;

import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.PreRestart;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class Slave extends AbstractBehavior<String> {

    static Behavior<String> create() {
        return Behaviors.setup(Slave::new);
    }

    private Slave(ActorContext<String> context) {
        super(context);
        System.out.println("worker actor started!!!");
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("fail-now", this::fail)
                .onSignal(PreRestart.class, signal -> preRestart())
                .onSignal(PostStop.class, signal -> postStop())
                .build();
    }

    private Behavior<String> fail() {
        System.out.println("worker actor fails now...");
        throw new RuntimeException("I failed!");
    }

    private Behavior<String> preRestart() {
        System.out.println("worker actor will be restarted...");
        return this;
    }

    private Behavior<String> postStop() {
        System.out.println("worker actor stopped.");
        return this;
    }
}
