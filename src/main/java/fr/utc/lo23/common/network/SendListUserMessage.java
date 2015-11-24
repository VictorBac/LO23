package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.ArrayList;

/**
 * Created by Jean-Côme on 16/11/2015.
 */
public class SendListUserMessage extends Message {

    private ArrayList<User> userList;
    public SendListUserMessage(ArrayList<User> users) {
        userList=users;
    }

    /**
     * Generic process
     */
    @Override
    public void process() {
        Console.logn("Envoi de la table");
    }

    /**
     * Check if we can login in the server, and send a confirmation (or not ?)
     * @param threadServer
     */
    @Override
    public void process (ConnectionThread threadServer){
        Console.logn("Envoi de la table");
    }

    /**
     * Client-side process
     * @param threadClient
     */
    @Override
    public void process(ServerLink threadClient) {

    }

}
