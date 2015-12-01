package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by Ghark on 25/11/2015.
 */
public class HeartbeatMessage extends Message {
    @Override
    public void process() {

    }

    @Override
    public void process(ConnectionThread threadServer) {
        threadServer.updateHeartbeat();
    }

    @Override
    public void process(ServerLink threadClient) {

    }
}
