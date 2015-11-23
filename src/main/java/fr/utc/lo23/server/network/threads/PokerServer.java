package fr.utc.lo23.server.network.threads;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.network.NotifyDisconnectionMessage;
import fr.utc.lo23.common.network.NotifyNewPlayerMessage;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.UUID;

public class PokerServer extends Thread {
    static int PORT = 1904;
    static int NB_MAX_USER = 100;

    private ServerSocket listeningSocket;
    private boolean running;
    private HashMap<UUID, ObjectOutputStream> userLinksOut;

    /**
     * Constructeur
     * Usage: new PokerServer() ==> server.start() / server.shutdown()
     * @param portToListen default port to listen is defined in const var, if you would like to change it,
     *                     give an other number here
     */
    public PokerServer(Integer portToListen) {
        Console.log("Lancement du serveur....");
        Console.log("Nombre d'utilisateurs maximum fixé à " + NB_MAX_USER);
        userLinksOut = new HashMap<UUID, ObjectOutputStream>();

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
        userLinksOut.put(user.getIdUser(), out);
    }

    /**
     * Enleve l'utilsateur u de la l'array userLinksOut
     * @return
     * @param u User
     */
    public void userDisconnect(User u) {
        // On ferme le socket lié à cet User
        //TODO: mettre le connectionThread dans userLinksOut pour pouvoir appeller: userLinksOut connectionThread shutdown ()

        //Actualise la table en enlevant le user u
        this.userLinksOut.remove(u.getIdUser());
    }

    /**
     * Get all outputStream for inform other
     * @return
     */
    public ArrayList<ObjectOutputStream> getAllOutputStream(){

        return null;
    }

    /**
     * Return the number of users connected
     * @return int
     */
    public int getNbUsers() {
        return userLinksOut.size();
    }


    /**
     * Permet d'ajouter le nouvel user aux users connectés sur le serveur
     * De notifier les autres utilisateurs du nouvel user connecté
     * De récupérer la liste des utilisateurs connectés
     * @param u
     * @return
     */
    public ArrayList<User> stockUserAndNotifyOthers(User u) {
        //TODO Appeler interface data pour stocker l'user U

        //TODO Notify les autres users de la connection d'un nouvel utilisateur
        notifyNewPlayer(u);


        //TODO retourner arraylist des autres users pour le donner au message accept login
        //ArrayList<Users> userList = InterfaceServerDataFromCom.getConnectedUsers(); Le nom de la classe n'est pas le bon, il faudra juste le changer par la classe implémentant leur int.
        //return userList;
        return null;
    }

    public void sendToAllDisconnection(NotifyDisconnectionMessage NotifyD) {
        for (HashMap.Entry<UUID, ObjectOutputStream> userOut: userLinksOut.entrySet()) {
            try {
                userOut.getValue().writeObject(NotifyD);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void notifyNewPlayer(User u) {

        NotifyNewPlayerMessage newPMessage = new NotifyNewPlayerMessage(u);
        for (HashMap.Entry<UUID, ObjectOutputStream> userOut: userLinksOut.entrySet()) {
            try {
                userOut.getValue().writeObject(newPMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
