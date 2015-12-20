package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Jean-Côme on 14/12/2015.
 */
public class SetMoneyAmountMessage extends Message {
    UUID table;
    UserLight user;
    Integer amount;


    public SetMoneyAmountMessage(UUID table,UserLight user,Integer amount) {
        this.table = table;
        this.amount = amount;
        this.user = user;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        threadServer.getMyServer().getNetworkManager().getDataInstance().setMoneyPlayer(table,user,amount);

        //Informe les autres joueurs de la table de l'argent du joueur
        ArrayList<UserLight> aPlayers = threadServer.getMyServer().getNetworkManager().getDataInstance().getPlayersByTable(table);
        SetMoneyAmountMessage notifyAnswerToOthers = new SetMoneyAmountMessage(table,user,amount);

        for (UserLight u : aPlayers) {
            if (u.getIdUser() != user.getIdUser()) {
                threadServer.send(notifyAnswerToOthers);
            }
        }
    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().notifyMoneyAmountAnswerFromServer(user, amount);
    }
}
