package fr.utc.lo23.client.network.main;

import fr.utc.lo23.client.network.threads.ServerLink;

/**
 * Created by guixii on 03/11/15.
 * Main for test with 2 clients virtually launched
 */
public class Main {
    public static void main(String[] args){
        Console.log("Main: Lancement client 1");
        ServerLink localClient = new ServerLink();
        localClient.start();

        // Pause between 2 clients
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Console.log("Main: Lancement client 2");
        ServerLink localClient2 = new ServerLink();
        localClient2.start();
    }
}

