package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.UUID;

/**
 * Message permettant de répondre à la demande de ready du serveur
 * pour joueur
 * Created by rbonneau on 19/12/2015.
 */
public class AnswerIfReadyGameMessage extends Message {

    UUID table;
    private UserLight player;
    private boolean answer;

    public AnswerIfReadyGameMessage(UUID t, UserLight p, boolean a) {
        this.table = t;
        this.player = p;
        this.answer = a;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        threadServer.getMyServer().getNetworkManager().getDataInstance().setReadyAnswer(table, player, answer);
    }

    @Override
    public void process(ServerLink threadClient) {
    }
}
