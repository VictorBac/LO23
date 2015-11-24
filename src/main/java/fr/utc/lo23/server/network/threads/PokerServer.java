package fr.utc.lo23.server.network.threads;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.common.network.Message;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.server.network.NetworkManagerServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.UUID;

public class PokerServer extends Thread {
    public static int NB_MAX_USER = 100;
    private static int PORT = 1904;

    private NetworkManagerServer networkManager;
    private ServerSocket listeningSocket;
    private boolean running;
    private HashMap<UUID, ObjectOutputStream> userLinksOut;
    private ArrayList<ConnectionThread> threadsClientList;

    /**
     * Constructeur
     * Usage: new PokerServer() ==> server.start() / server.shutdown()
     * @param portToListen default port to listen is defined in const var, if you would like to change it,
     *                     give an other number here
     */
    public PokerServer(Integer portToListen) {
        Console.log("Lancement du serveur....");
        Console.log("Nombre d'utilisateurs maximum fixé à " + NB_MAX_USER);
        ArrayList<ConnectionThread> threadsClientList = new ArrayList<ConnectionThread>();


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
                ConnectionThread thread = new ConnectionThread(soClient, this);
                thread.start();
                threadsClientList.add(thread);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Enleve l'utilsateur u de la l'array userLinksOut
     * @return
     * @param u User
     */
    public void userDisconnect(User u) {

        //TODO cedric
        // On ferme le socket lié à cet User

        //TODO: mettre le connectionThread dans userLinksOut pour pouvoir appeller: userLinksOut connectionThread shutdown ()

        //Actualise la table en enlevant le user u
        //this.userLinksOut.remove(u.getIdUser());
    }

    private ConnectionThread getThreadFromUserLight(UserLight u){
        for (ConnectionThread threadClient : threadsClientList) {
            if(threadClient.getUserId() == u.getIdUser()) return threadClient;
        }
        // If not found
        return null;
    }

    public void sendToAll(Message message){
        for (ConnectionThread threadClient : threadsClientList) {
            threadClient.send(message);
        }
    }

    /**
     * Return the number of users connected
     * @return int
     */
    public int getNbUsers() {
        return threadsClientList.size();
    }
}
