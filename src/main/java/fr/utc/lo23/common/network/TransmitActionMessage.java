package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.ArrayList;

/**
 * Created by Jean-Côme on 21/12/2015.
 */
public class TransmitActionMessage extends Message {
    private Action act;
    private UserLight user;
    public TransmitActionMessage(Action a, UserLight user) {
        this.act=a;
        this.user = user;
    }

    @Override
    public void process(ConnectionThread threadServer) {

    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().notifyAction(act);
    }
}
