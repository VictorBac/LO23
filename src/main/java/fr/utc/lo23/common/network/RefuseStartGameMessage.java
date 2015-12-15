package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by Jean-Côme on 14/12/2015.
 */
public class RefuseStartGameMessage extends Message {
    @Override
    public void process(ConnectionThread threadServer) {

    }

    @Override
    public void process(ServerLink threadClient) {
        //threadClient.getNetworkManager().getDataInstance().tableCreatorRequestToStartGameRejected();
    }
}
