package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.ArrayList;

/**
 * Created by raphael on 03/11/15.
 */
public class AcceptLoginMessage extends Message{

    private ArrayList<UserLight> usersArray;

    public AcceptLoginMessage(ArrayList<UserLight> aUser) {usersArray=aUser;}

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
     * @param threadServer
     */
    @Override
    public void process (ConnectionThread threadServer){
    }

    /**
     * Client-side process
     * @param threadClient
     */
    @Override
    public void process(ServerLink threadClient) {
        //dataInterface.currentConnectedUser(usersArray);
    }

}