package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Message permettant de prévenir les joueurs
 * qu'un nouveau tour va commencer
 * Created by rbonneau on 18/12/2015.
 */
public class NotifyNewTurnMessage extends Message {
    @Override
    public void process(ConnectionThread threadServer) {
        //No need for a server-side usage (yet)
    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().informNewTurn();
    }
}
