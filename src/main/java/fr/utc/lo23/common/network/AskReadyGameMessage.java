package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Message demandant aux autres joueurs s'ils sont
 * prêts à jouer
 * Created by raphael on 15/12/15.
 */
public class AskReadyGameMessage extends Message {

    @Override
    public void process(ConnectionThread threadServer) {
        //No need for a server-side usage (yet)
    }

    @Override
    public void process(ServerLink threadClient) {
        Console.log("AskReadyMessage reçu");
        threadClient.getNetworkManager().getDataInstance().askReadyGame();
    }
}
