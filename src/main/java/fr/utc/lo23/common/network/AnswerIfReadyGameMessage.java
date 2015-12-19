package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Message permettant de répondre à la demande de ready du serveur
 * pour joueur
 * Created by rbonneau on 19/12/2015.
 */
public class AnswerIfReadyGameMessage extends Message {

    UUID tableId;
    private UserLight player;
    private boolean answer;

    public AnswerIfReadyGameMessage(UUID t, UserLight p, boolean a) {
        this.tableId = t;
        this.player = p;
        this.answer = a;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        threadServer.getMyServer().getNetworkManager().getDataInstance().setReadyAnswer(tableId, player, answer);

        //Informe les autres joueurs de la table de la réponse du joueur
        ArrayList<UserLight> aPlayers = threadServer.getMyServer().getNetworkManager().getDataInstance().getPlayersByTable(tableId);
        NotifyAnswerReadyGameMessage notifyAnswerToOthers = new NotifyAnswerReadyGameMessage(player, answer);
        
        for (UserLight u : aPlayers) {
            if (u.getIdUser() != player.getIdUser()) {
                threadServer.send(notifyAnswerToOthers);
            }
        }
    }

    @Override
    public void process(ServerLink threadClient) {
    }
}
