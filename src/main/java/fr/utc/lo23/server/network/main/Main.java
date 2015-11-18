package fr.utc.lo23.server.network.main;

import fr.utc.lo23.server.network.threads.PokerServer;

/**
 * Created by guixii on 03/11/15.
 * Main for test launch a server without GUI
 */
public class Main {
    public static void main(String[] args){
        PokerServer pokerPokerServer = new PokerServer(null); // instance de la classe principale
        //TODO Quand data aura commit l'implémentation de son interface, l'ajouter à pokerserveur pour pouvoir ajouter les méthodes qui vont bien
        pokerPokerServer.start();
    }
}
