package fr.utc.lo23.server.network.threads;

import fr.utc.lo23.client.network.main.Console;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PokerServer extends Thread {
    static int port = 1904;
    static int nbMaxUser = 100;

    private ServerSocket listeningSocket;
    private boolean running = false;
    private int nbUsers;


    public PokerServer(Integer portToListen) {
        Console.log("Lancement du serveur....");

        Console.log("Nombre d'utilisateurs maximum à " + nbMaxUser);
        nbUsers = 0;

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

    /**
     * Create the socket
     * @throws Exception
     */
    public void initSocket() throws Exception {
        if (null != listeningSocket) {
            Console.err("La socket existait déjà");
            return;
        }

        listeningSocket = new ServerSocket(port);
        Console.log("Le serveur écoute sur " + listeningSocket.getInetAddress() + ":" + listeningSocket.getLocalPort());
    }

    /**
     * Shutdown the server
     * @throws Exception
     */
    public void shutdown() throws Exception {
        running = false;
        listeningSocket.close();
    }

    /**
     *
     */
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

    /**
     * Return the number of users connected
     * @return int
     */
    public int getNbUsers() {
        return nbUsers;
    }


    /**
     * Check if there is room for one more user
     * @return boolean
     */
    public boolean checkIfUserCanConnect () {

        if (nbUsers < nbMaxUser) {
            nbUsers++;
            return true;
        }
        return false;
    }
}
