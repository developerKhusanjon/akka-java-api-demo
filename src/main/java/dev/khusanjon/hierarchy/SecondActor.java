package dev.khusanjon.hierarchy;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class SecondActor extends AbstractBehavior<String> {

    static Behavior<String> create() {
        return Behaviors.setup(SecondActor::new);
    }

    private SecondActor(ActorContext<String> context) {
        super(context);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder().onMessageEquals("show-refs", this::printRef).build();
    }

    private Behavior<String> printRef() {
        ActorRef<String> refForEmptyActor = getContext().spawn(Behaviors.empty(), "empty-actor");
        System.out.println("SelfRef: " + getContext().getSelf());
        System.out.println("EmptyRef: " + refForEmptyActor);
        return this;
    }
}
