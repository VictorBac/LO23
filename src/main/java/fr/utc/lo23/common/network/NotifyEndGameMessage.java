package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by Primate on 30/12/2015.
 * Message permettant de notifier au joueur que le jeu est fini
 */
public class NotifyEndGameMessage extends Message {
    @Override
    public void process(ConnectionThread threadServer) {
        //No need for a server-side usage (yet)
    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().stopGame();
    }
}
