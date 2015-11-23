package fr.utc.lo23.common.network;


import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created by raphael on 17/11/15.
 * Message unique pour la déconnection. Le traitement sera juste différencié par le serveur et le client.
 */
public class NotifyDisconnectionMessage extends Message {

private User u;

    public NotifyDisconnectionMessage() {
    }

    /**
     * Generic process
     */
    @Override
    public void process() {
        Console.log("Disconnection of Player");
        //TODO
        /**
         *
         *
         *
         */
    }

    /**
     * For message processed server-side
     * @param myServ
     * @param out
     */
    @Override
    public void process (PokerServer myServ, ObjectOutputStream out){
        Console.log("Notify disconnection message received");

        Console.log("Sending to DataServer");

        //Appel de la fonction d'interface DATA pour update les tables dans DataServer
        //

        Console.log("Refreshing UserLight Table in Server");
        myServ.userDisconnect(u);
        // Update de la liste de UserLight contenue dans le Thread Server.


        // Notification à tous les users de la déconnection
        NotifyDisconnectionMessage NotifyD = new NotifyDisconnectionMessage(u);
        myServ.sendToAllDisconnection(NotifyD);

        Console.log("Closing the ConnectionThread/Socket");
        //
        out.close();
        // On ferme le socket lié à cet User


    }

    /**
     * Client-side process
     * @param dataInterface
     */
    @Override
    public void process(InterfaceDataFromCom dataInterface) {

    }


}
