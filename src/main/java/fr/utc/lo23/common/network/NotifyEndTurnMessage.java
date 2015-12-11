package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by rbonneau on 11/12/2015.
 */
public class NotifyEndTurnMessage extends Message {

    UserLight ul;

    public NotifyEndTurnMessage(UserLight u) {
        ul = u;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        threadServer.getMyServer().getNetworkManager().getDataInstance().endTurnConfirmation(ul);
    }

    @Override
    public void process(ServerLink threadClient) {

    }
}
