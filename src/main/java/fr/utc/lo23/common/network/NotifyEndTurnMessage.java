package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Informs the players the end of the turn
 * Created by rbonneau on 11/12/2015.
 */
public class NotifyEndTurnMessage extends Message {

    Integer pot;

    public NotifyEndTurnMessage(Integer turnPot) {
        pot = turnPot;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        //No need for a server-side usage (yet)
    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().informEndTurn(pot);
    }
}
