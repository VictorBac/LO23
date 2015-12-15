package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by Jean-Côme on 14/12/2015.
 */
public class SetMoneyAmountMessage extends Message {
    int amount;
    UserLight user;

    public SetMoneyAmountMessage(int amount,UserLight user ) {
        this.amount = amount;
        this.user=user;
    }

    @Override
    public void process(ConnectionThread threadServer) {
      //  if(threadServer.getMyServer().getNetworkManager().getDataInstance().checkMoneyMax(user, amount, UUID idTable)){
            //TODO: Envoyer aux autres clients la somme choisie
      //  }
      //  if( threadServer.getMyServer().getNetworkManager().getDataInstance().isEveryoneAmountMoneySelected()){
            //TODO : Si tous les clients sont prêts, on envoi un askReady
      //  }
    }

    @Override
    public void process(ServerLink threadClient) {
      //TODO : decommenter quand la fonction existe
        //  threadClient.getNetworkManager().getDataInstance().notifyMoneyAmountAnswer(user, amount);
    }
}
