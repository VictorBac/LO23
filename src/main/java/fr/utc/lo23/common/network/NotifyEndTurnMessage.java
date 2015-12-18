package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Message pour informer les joueurs
 * de la fin d'un tour
 * Created by rbonneau on 11/12/2015.
 */
public class NotifyEndTurnMessage extends Message {

    @Override
    public void process(ConnectionThread threadServer) {
        //threadServer.getMyServer().getNetworkManager().getDataInstance().endTurnConfirmation();//TODO éclaircir s'il y a usage côté serveur
    }

    @Override
    public void process(ServerLink threadClient) {
        //threadClient.getNetworkManager().getDataInstance().informEndTurn();//TODO mettre le port?
    }
}
