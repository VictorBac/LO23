package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Message permettant de transmettre la réponse
 * au ready d'un joueur à tous les autres joueurs de la table
 * Created by rbonneau on 19/12/2015.
 */
public class NotifyAnswerReadyGameMessage extends Message {

    private UserLight otherPlayer;
    private boolean answer;

    public NotifyAnswerReadyGameMessage(UserLight u, boolean a) {
        this.answer = a;
        this.otherPlayer = u;
    }

    @Override
    public void process(ConnectionThread threadServer) {
    }

    @Override
    public void process(ServerLink threadClient) {
        //threadClient.getNetworkManager().getDataInstance().inforReadyAnswerFromOtherPlayer(otherPlayer, answer);//TODO mettre bon nom de méthode
    }
}
