package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Message permettant d'envoyer au serveur l'action d'un joueur
 * Created by rbonneau on 11/12/2015.
 */
public class SendActionMessage extends Message {

    Action act;

    public SendActionMessage(Action a) {
        act = a;
    }


    @Override
    public void process(ConnectionThread threadServer) {
        threadServer.getMyServer().getNetworkManager().getDataInstance().replyAction(act, act.getUserLightOfPlayer());
    }

    @Override
    public void process(ServerLink threadClient) {

    }
}
