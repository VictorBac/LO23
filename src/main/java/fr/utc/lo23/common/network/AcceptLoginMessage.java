package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by raphael on 03/11/15.
 */
public class AcceptLoginMessage extends Message{

    private ArrayList<User> usersArray;

    public AcceptLoginMessage(ArrayList<User> aUser) {
        aUser=usersArray;
    }

    /**
     * Generic process (both server and client)
     */
    @Override
    public void process() {
        Console.log("Login accepted");
        //TODO
    }

    /**
     * For message processed server-side
     * @param myServ
     * @param out
     */
    @Override
    public void process (PokerServer myServ,  ObjectOutputStream out){
    }

}