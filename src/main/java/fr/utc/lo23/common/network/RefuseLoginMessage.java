package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.io.IOException;

/**
 * Created by rbonneau on 15/11/2015.
 */
public class RefuseLoginMessage extends Message {

    public RefuseLoginMessage() {
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
        threadClient.getNetworkManager().getDataInstance().refuseLogin();
        // The Thread is reset to be able to be connected with other servers
        try {
            threadClient.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
