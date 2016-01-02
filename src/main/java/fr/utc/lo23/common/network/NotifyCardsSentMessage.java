package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by polo4 on 01/01/2016.
 */
public class NotifyCardsSentMessage extends Message {

    public NotifyCardsSentMessage() {
    }

    @Override
    public void process(ConnectionThread threadServer) {

    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().notifyCardsReceived();
    }
}