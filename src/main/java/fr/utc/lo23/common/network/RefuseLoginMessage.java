package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.io.IOException;

/**
 * Created by rbonneau on 15/11/2015.
 * The player can't loggin to the server
 */
public class RefuseLoginMessage extends Message {

    String reason;
    public RefuseLoginMessage(String reason) {
        this.reason = reason;
    }

    @Override
    public void process (ConnectionThread threadServer){
        //No need for a server-side usage (yet)
    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().refuseLogin(reason);
        // The Thread is reset to be able to be connected with other servers
        try {
            threadClient.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
