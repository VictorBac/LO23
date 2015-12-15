package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by Jean-Côme on 14/12/2015.
 */
public class AskMoneyMessage extends Message {
    @Override
    public void process(ConnectionThread threadServer) {

    }

    @Override
    public void process(ServerLink threadClient) {
        Console.log("AskReadyMoney reçu");

        //TODO : decommenter quand la fonction existe
        //threadClient.getNetworkManager().getDataInstance().askAmountMoney();
    }
}
