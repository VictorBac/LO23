package fr.utc.lo23.common.network;


import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.exceptions.UserNotFoundException;
import fr.utc.lo23.server.network.threads.ConnectionThread;
import fr.utc.lo23.server.network.threads.PokerServer;

/**
 * Created by raphael on 17/11/15.
 * Message unique pour la déconnection. Le traitement sera juste différencié par le serveur et le client.
 */
public class NotifyDisconnectionMessage extends Message {

private User u;

    public NotifyDisconnectionMessage(User u_init) {
        u = u_init;
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
        try {
            // Je suppose que c'est la fonction pour enlever l'user. Pas de commentaire de cette fonction sur interface data
            myServ.getNetworkManager().getDataInstance().deletePlayer(u.getUserLight());
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        //Appel de la fonction d'interface DATA pour update les tables dans DataServer

        Console.log("Refreshing UserLight Table in Server");
        try {
            myServ.userDisconnect(u.getUserLight().getIdUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Update de la liste de UserLight contenue dans le Thread Server.


        // Notification à tous les users de la déconnection
        NotifyDisconnectionMessage NotifyD = new NotifyDisconnectionMessage(u);
        myServ.sendToAll(NotifyD);

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
