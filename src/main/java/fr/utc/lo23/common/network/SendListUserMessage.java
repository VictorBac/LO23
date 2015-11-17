package fr.utc.lo23.common.network;

import fr.utc.lo23.client.data.Userlight;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
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
        Console.lognl("Envoi de la table");
    }

    /**
     * Check if we can login in the server, and send a confirmation (or not ?)
     * @param myServ
     * @param out
     */
    @Override
    public void process (PokerServer myServ, ObjectOutputStream out){
        Console.lognl("Envoi de la table");
    }

}
