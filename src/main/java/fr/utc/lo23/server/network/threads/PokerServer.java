package fr.utc.lo23.server.network.threads;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.exceptions.network.NetworkFailureException;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class PokerServer extends Thread {
    static int PORT = 1904;
    static int NB_MAX_USER = 100;

    private ServerSocket listeningSocket;
    private boolean running;
    private int nbUsers;
    private HashMap<User, ObjectOutputStream> userLinksOut;

    /**
     * Constructeur
     * Usage: new PokerServer() ==> server.start() / server.shutdown()
     * @param portToListen default port to listen is defined in const var, if you would like to change it,
     *                     give an other number here
     */
    public PokerServer(Integer portToListen) {
        Console.log("Lancement du serveur....");
        Console.log("Nombre d'utilisateurs maximum fixé à " + NB_MAX_USER);
        nbUsers = 0;

        // Change port if needed
        if (portToListen != null) PokerServer.PORT = portToListen;

        try {
            initSocket();
        } catch (Exception e) {
            Console.err("ERR: Serveur main: Initialisation du socket");
            e.printStackTrace();
        }
    }

    /**
     * Create the socket
     * @throws Exception
     */
    public void initSocket() throws Exception {
        if (null != listeningSocket) throw new NetworkFailureException("La socket serveur existe déjà");

        listeningSocket = new ServerSocket(PORT);
        Console.log("Le serveur écoute sur " + listeningSocket.getInetAddress() + ":" + listeningSocket.getLocalPort());
    }

    /**
     * Shutdown the server
     * @throws Exception
     */
    public void shutdown() throws IOException {
        running = false;
        listeningSocket.close();
    }

    /**
     * Run method (thread) - Waitting for new clients
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
     * Must be called to register the output stream with a User
     * @param out
     * @param user
     */
    public void registerOutputStream(ObjectOutputStream out, User user){
        userLinksOut.put(user, out);
    }

    /**
     * Actualise la table interne qui a tous les clients
     * @return boolean
     * @param u User
     */
    public void userDisconnect(User u) {
        //TODO: Actualiser la table en enlevant le user u
    }

    /**
     * Return the number of users connected
     * @return int
     */
    public int getNbUsers() {
        return nbUsers;
    }
}
