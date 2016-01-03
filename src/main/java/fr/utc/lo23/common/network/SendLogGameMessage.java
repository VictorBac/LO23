package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by raphael on 15/12/15.
 * Sends the log of the game to a players
 */
public class SendLogGameMessage extends Message {

    private Table logGame;

    public SendLogGameMessage(Table logGame) {
        this.logGame = logGame;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        //No need for a server-side usage (yet)
    }

    @Override
    public void process(ServerLink threadClient) {
        // Probleme dans la fonction de data , argument table mais devrait etre game ?
        threadClient.getNetworkManager().getDataInstance().saveLogGame(logGame);

    }
}
