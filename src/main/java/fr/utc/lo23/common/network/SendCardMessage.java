package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.PlayerHand;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.ArrayList;

/**
 * Created by raphael on 22/12/15.
 */
public class SendCardMessage extends Message {

    private ArrayList<PlayerHand> pHand;

    public SendCardMessage(ArrayList<PlayerHand> pHand) {
        this.pHand = pHand;
    }

    @Override
    public void process(ConnectionThread threadServer) {

    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().stockCards(pHand);
    }
}
