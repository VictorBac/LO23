package fr.utc.lo23.server.network.main;

import fr.utc.lo23.server.network.threads.PokerServer;

/**
 * Created by guixii on 03/11/15.
 */
public class Main {
    public static void main(String[] args){
        PokerServer pokerPokerServer = new PokerServer(null); // instance de la classe principale
        pokerPokerServer.start();
    }
}
