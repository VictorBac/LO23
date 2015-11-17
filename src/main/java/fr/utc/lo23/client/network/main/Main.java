package fr.utc.lo23.client.network.main;

import fr.utc.lo23.client.network.IntClient;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.network.Message;

/**
 * Created by guixii on 03/11/15.
 * Main for test with 2 clients virtually launched
 */
public class Main {
    public static void main(String[] args){
        Console.log("Main: Lancement client 1");
        //ServerLink localClient = new ServerLink();
        IntClient clientInterface = new IntClient();
        // localClient.start();
    }
}

