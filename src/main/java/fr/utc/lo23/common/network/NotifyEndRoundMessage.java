package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.PlayerHand;
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
    private ArrayList<PlayerHand> playerHands;

    public NotifyEndRoundMessage(ArrayList<Seat> a, ArrayList<PlayerHand> pla) {
        aSeat = a;
        playerHands = pla;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        //No need for a server-side usage (yet)
    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().informEndHand(aSeat,playerHands);
    }
}
