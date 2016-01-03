package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by Jean-Côme on 21/12/2015.
 * Transmits the action of a player
 */
public class TransmitActionMessage extends Message {
    private Action act;
    private UserLight user;
    public TransmitActionMessage(Action a, UserLight u) {
        this.act=a;
        this.user = u;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        //No need for a client-side usage (yet)
    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().notifyAction(act);
    }
}
