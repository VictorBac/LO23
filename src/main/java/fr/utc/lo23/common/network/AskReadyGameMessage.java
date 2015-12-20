package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by raphael on 15/12/15.
 */
public class AskReadyGameMessage extends Message {

    private boolean Answer;

    public AskReadyGameMessage(boolean answer) {
        this.Answer = answer;
    }

    @Override
    public void process(ConnectionThread threadServer) {



    }

    @Override
    public void process(ServerLink threadClient) {
        Console.log("AskReadyMessage reçu");
       //threadClient.getNetworkManager().getDataInstance().askReadyGame();
    }
}
