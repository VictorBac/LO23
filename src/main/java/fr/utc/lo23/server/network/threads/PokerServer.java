package fr.utc.lo23.server.network.threads;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.Params;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.common.network.Message;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.PlayerNotConnectedException;
import fr.utc.lo23.server.network.NetworkManagerServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class PokerServer extends Thread {
    private NetworkManagerServer networkManager;
    private ServerSocket listeningSocket;
    private boolean running;
    private ArrayList<ConnectionThread> threadsClientList;

    /**
     * Constructeur
     * Usage: new PokerServer() ==> server.start() / server.shutdown()
     * @param portToListen default port to listen is defined in const var, if you would like to change it,
     *                     give an other number here
     */
    public PokerServer(NetworkManagerServer manager, Integer portToListen) throws NetworkFailureException{
        Console.logn("Lancement du serveur.... ");
        networkManager = manager;
        threadsClientList = new ArrayList<ConnectionThread>();

        // Change port if needed
        if (portToListen == null) portToListen = Params.DEFAULT_SERVER_PORT;

        initSocket(portToListen);
        Console.log("ok");
        Console.log("Le serveur écoute sur " + listeningSocket.getInetAddress() + ":" + listeningSocket.getLocalPort());
        //Console.log("Nombre d'utilisateurs maximum fixé à " + Params.NB_MAX_USER);
    }

    /**
     * Create the socket
     * @throws Exception
     */
    public void initSocket(int portToListen) throws NetworkFailureException {
        if (null != listeningSocket) throw new NetworkFailureException("Le socket serveur existe déjà");
        try {
            listeningSocket = new ServerSocket(portToListen);
        }
        catch (Exception e){
            throw new NetworkFailureException("Impossible de prendre la main sur le port: " + portToListen);
        }
    }

    /**
     * Shutdown the server
     * @throws Exception
     */
    public void shutdown() throws NetworkFailureException {
        //TODO: Deconnecter les clients en premier si possible
        try {
            listeningSocket.close();
        }
        catch (Exception e){
            throw new NetworkFailureException("Impossible de fermer la socket d'attente proprement");
        }
        running = false;
    }

    /**
     * Run method (thread) - Waitting for new clients
     */
    @Override
    public synchronized void run() {
        Console.log("Attente des clients...");
        running = true;
        while (running) {
            try {
                Socket soClient = listeningSocket.accept();
                ConnectionThread thread = new ConnectionThread(soClient, this);
                thread.start();
                threadsClientList.add(thread);
                for(ConnectionThread thr : threadsClientList) {
                    Console.log("=============" + thr.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Enleve le thread de la liste des threads, retourne true si réussi sinon retourne false
     * @return boolean
     * @param userId
     */
    public boolean userDisconnect(UUID userId) throws Exception{
        for (ConnectionThread threadClient : threadsClientList) {
            if(threadClient.getUserId() == userId) {
                threadsClientList.remove(threadClient);
                this.networkManager.notifyDisconnection(this.networkManager.getDataInstance().getUserById(userId));
                //Console.log("broadcast user disconnect");
                return true;
            }
        }
        throw new PlayerNotConnectedException("L'utilisateur n'est pas inscrit sur le serveur");
    }

    private ConnectionThread getThreadFromUserId(UUID userId){
        for (ConnectionThread threadClient : threadsClientList) {
            if (threadClient.getUserId() == userId) return threadClient;
        }
        return null; // If not found
    }

    public void sendToAll(Message message){
        //Console.log("Send to all: " + message);
        for (ConnectionThread threadClient : threadsClientList) {
            threadClient.send(message);
        }
    }

    public void sendToListOfUsers(ArrayList<UserLight> list, Message message){
        int compt=list.size();
        int i = 0;

       for(ConnectionThread thread : threadsClientList ){
           UserLight user = getNetworkManager().getDataInstance().getUserById(thread.getUserId()).getUserLight();
           if(list.contains(user)){
               thread.send(message);
           }
        }
    }

    /**
     * Return the number of users connected
     * @return int
     */
    public int getNbUsers() {
        return threadsClientList.size();
    }

    /**
     * Return the instance of the module networkManager
     * @return
     */
    public NetworkManagerServer getNetworkManager() {
        return networkManager;
    }
}
