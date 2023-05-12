package dev.khusanjon.hierarchy;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class FirstActor extends AbstractBehavior<String> {

    public static Behavior<String> create() {
        return Behaviors.setup(FirstActor::new);
    }

    private FirstActor(ActorContext<String> context) {
        super(context);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("show-hierarchy", this::printRef)
                .onMessageEquals("stop-yourself", this::shutDownYourself)
                .build();
    }

    private Behavior<String> printRef() {
        ActorRef<String> refForSecond = getContext().spawn(SecondActor.create(), "second-actor");
        System.out.println("Path of FirstActor: " + getContext().getSelf());
        System.out.println("Path of SecondActor: " + refForSecond);
        refForSecond.tell("show-refs");
        return this;
    }

    private Behavior<String> shutDownYourself() {
        getContext().stop(getContext().getSelf());
        return Behaviors.stopped();
    }
}
