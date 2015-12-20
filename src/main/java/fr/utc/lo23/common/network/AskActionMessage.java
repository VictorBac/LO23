package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.EnumerationAction;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Message permettant de demander à un joueur
 * quelle sera son action parmis la liste
 * proposée
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

    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().askAction(act,enAct);
    }
}
