package fr.utc.lo23.common.network;


import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.server.network.threads.ConnectionThread;
import fr.utc.lo23.server.network.threads.PokerServer;

/**
 * Message to notify the disconnection
 * Created by raphael on 17/11/15.
 */
public class NotifyDisconnectionMessage extends Message {

private User u;

    public NotifyDisconnectionMessage(User uInit) {
        u = uInit;
    }

    /**
     * For message processed server-side
     * @param threadServer
     */
    @Override
    public void process (ConnectionThread threadServer){
        PokerServer myServ = threadServer.getMyServer();

        Console.log("Notify disconnection message received");

        Console.log("Sending to DataServer");
        //Appel de la fonction d'interface DATA pour update les tables dans DataServer

        Console.log("Refreshing UserLight Table in Server");
        // On met à jour / notifie les autres que l'utilisateur est parti, on le supprime de la table des users
        try {
            myServ.userDisconnect(threadServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Console.log("Closing the ConnectionThread/Socket");
    }

    /**
     * Client-side process
     * @param threadClient
     */
    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().remoteUserDisonnected(u.getUserLight());
    }


}
