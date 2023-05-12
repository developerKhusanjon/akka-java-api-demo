package dev.khusanjon;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import dev.khusanjon.supervision.Master;

public class Main {
    public static void main(String[] args) {
        ActorRef<String> master = ActorSystem.create(Master.create(), "master-actor");
        master.tell("failWorker");
        master.tell("stop");
    }
}