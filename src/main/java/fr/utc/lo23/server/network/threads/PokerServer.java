package fr.utc.lo23.server.network.threads;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.main.TestSerialize;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PokerServer extends Thread {
    static int port = 1904;

    private ServerSocket listeningSocket;
    private boolean running = false;


    public PokerServer(Integer portToListen) {
        Console.log("Lancement du serveur....");
        // Change port if needed
        if (portToListen != null) PokerServer.port = portToListen.intValue();

        try {
            initSocket();
        } catch (Exception e) {
            Console.err("ERR: Serveur main");
            e.printStackTrace();
        }
    }

    public void initSocket() throws Exception {
        if (null != listeningSocket) {
            Console.err("La socket existait déjà");
            return;
        }

        listeningSocket = new ServerSocket(port);
        Console.log("Le serveur écoute sur " + listeningSocket.getInetAddress() + ":" + listeningSocket.getLocalPort());
    }

    public void shutdown() throws Exception {
        running = false;
        listeningSocket.close();
    }

    @Override
    public synchronized void run() {

        Console.log("Srv: Lancement Thread...");
        running = true;
        while (running) {
            try {
                Console.log("Srv: Attente des connexions clients...");
                Socket soClient = listeningSocket.accept();

                new ConnectionThread(soClient, this).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
