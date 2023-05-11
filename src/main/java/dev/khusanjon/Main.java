package dev.khusanjon;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import dev.khusanjon.hierarchy.FirstActor;

public class Main {
    public static void main(String[] args) {
        ActorRef<String> testSystem = ActorSystem.create(FirstActor.create(), "testSystem");
        testSystem.tell("show-hierarchy");
        testSystem.tell("stop-yourself");
    }
}