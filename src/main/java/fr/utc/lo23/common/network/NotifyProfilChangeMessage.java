package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by Jean-Côme on 29/12/2015.
 */
public class NotifyProfilChangeMessage extends Message {

    private User user;

    public NotifyProfilChangeMessage(User user){
        this.user=user;
    }

    @Override
    public void process(ConnectionThread threadServer) {

    }

    @Override
    public void process(ServerLink threadClient) {
        //TODO : Faire l'appel en local quand la fonction sera ok -> remoteUserProfilChange(user)
        // threadClient.getNetworkManager().getDataInstance().
    }
}
