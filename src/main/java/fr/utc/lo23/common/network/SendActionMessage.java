package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by rbonneau on 11/12/2015.
 */
public class SendActionMessage extends Message {

    Action act;
    UserLight userLocal;

    public SendActionMessage(Action a, UserLight ul) {
        act = a;
        userLocal = ul;
    }


    @Override
    public void process(ConnectionThread threadServer) {
        threadServer.getMyServer().getNetworkManager().getDataInstance().replyAction(act,userLocal);

    }

    @Override
    public void process(ServerLink threadClient) {

    }
}
