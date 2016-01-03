package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.EnumerationAction;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Ask a player which action he wants to perform
 * Created by raphael on 15/12/15.
 */
public class AskActionMessage extends Message{

    Action act;
    EnumerationAction[] enAct;

    public AskActionMessage(Action a, EnumerationAction[] en) {
        this.act = a;
        this.enAct = en;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        //No need for a server-side usage (yet)
    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().askAction(act,enAct);
    }
}
