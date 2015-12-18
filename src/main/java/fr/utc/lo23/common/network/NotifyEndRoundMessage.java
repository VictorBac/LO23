package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Message pour informer les joueurs
 * de la fin d'une manche
 * Created by rbonneau on 18/12/2015.
 */
public class NotifyEndRoundMessage extends Message {
    @Override
    public void process(ConnectionThread threadServer) {

    }

    @Override
    public void process(ServerLink threadClient) {
        //threadClient.getNetworkManager().getDataInstance().informEndRound();//TODO mettre le port?
    }
}
