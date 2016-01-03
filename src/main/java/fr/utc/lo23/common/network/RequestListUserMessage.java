package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.util.ArrayList;

/**
 * Ask the players list to the server
 * Created by rbonneau on 14/11/2015.
 */
public class RequestListUserMessage extends Message {

    public RequestListUserMessage() {
    }

    /**
     * Send the list of connected users to concerned thread.
     * @param threadServer
     */
    @Override
    public void process (ConnectionThread threadServer){
        PokerServer myServ = threadServer.getMyServer();
        Console.log("Request list of users");
        ArrayList<UserLight> listUsers = myServ.getNetworkManager().getDataInstance().getConnectedUsers();
        SendListUserMessage listUserMess = new SendListUserMessage(listUsers);
        threadServer.send(listUserMess);
    }

    @Override
    public void process(ServerLink threadClient) {
        //No need for a client-side usage (yet)
    }

}
