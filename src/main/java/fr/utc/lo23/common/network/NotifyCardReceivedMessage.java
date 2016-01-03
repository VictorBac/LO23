package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by Ghark on 11/12/2015.
 */
public class NotifyCardReceivedMessage extends Message {

    UserLight ul;

    public NotifyCardReceivedMessage (UserLight u){
        ul = u;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        //USELESS CE MESSAGE OU BIEN ??  threadServer.getMyServer().getNetworkManager().getDataInstance().confirmationCardReceived(ul);
    }

    @Override
    public void process(ServerLink threadClient) {
        //No need for a client-side usage (yet)
    }
}
