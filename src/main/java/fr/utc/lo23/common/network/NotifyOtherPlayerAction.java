package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Message permettant de notifier les autres joueurs
 * de l'action d'un joueur de la table
 * Created by rbonneau on 20/12/2015.
 */
public class NotifyOtherPlayerAction extends Message {

    Action act;

    public NotifyOtherPlayerAction(Action a) {
        this.act = a;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        //threadServer.getMyServer().getNetworkManager().getDataInstance().notifyAction(act);//TODO décommenter après merge
    }

    @Override
    public void process(ServerLink threadClient) {

    }
}
