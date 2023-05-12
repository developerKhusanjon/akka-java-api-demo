package dev.khusanjon.startstop;

import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public class StartStopActorParent extends AbstractBehavior<String> {

    public static Behavior<String> create() {
        return Behaviors.setup(StartStopActorParent::new);
    }

    private StartStopActorParent(ActorContext<String> context) {
        super(context);
        System.out.println("Parent started!");

        context.spawn(StartStopActorChild.create(), "child-actor");
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("stop", Behaviors::stopped)
                .onSignal(PostStop.class, signal -> onPostStop())
                .build();
    }

    private Behavior<String> onPostStop() {
        System.out.println("Parent stopped!");
        return this;
    }
}
