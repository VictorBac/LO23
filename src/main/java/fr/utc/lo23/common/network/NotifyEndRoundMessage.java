package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Seat;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.ArrayList;

/**
 * Message pour informer les joueurs
 * de la fin d'une manche
 * Created by rbonneau on 18/12/2015.
 */
public class NotifyEndRoundMessage extends Message {

    private ArrayList<Seat> aSeat;

    public NotifyEndRoundMessage(ArrayList<Seat> a) {
        aSeat = a;
    }

    @Override
    public void process(ConnectionThread threadServer) {

    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().informEndHand(aSeat);
    }
}
