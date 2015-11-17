package fr.utc.lo23.client.network.main;

import fr.utc.lo23.client.network.threads.ServerLink;

/**
 * Created by guixii on 03/11/15.
 */
public class Main {
    public static void main(String[] args){
        Console.log("Main: Lancement client 1");
        ServerLink localClient = new ServerLink();
        localClient.start();

        Console.log("Main: Lancement client 2");
        ServerLink localClient2 = new ServerLink();
        localClient2.start();
    }
}

