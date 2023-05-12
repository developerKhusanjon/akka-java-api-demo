package dev.khusanjon.startstop;

import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class StartStopActorChild extends AbstractBehavior<String> {

    static Behavior<String> create() {
        return Behaviors.setup(StartStopActorChild::new);
    }

    private StartStopActorChild(ActorContext<String> context) {
        super(context);
        System.out.println("ChildActor started!");
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onSignal(PostStop.class, signal -> onPostStop())
                .build();
    }

    private Behavior<String> onPostStop() {
        System.out.println("ChildActor stopped!");
        return this;
    }
}
