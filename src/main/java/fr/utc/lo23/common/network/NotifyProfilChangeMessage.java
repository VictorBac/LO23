package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by Jean-Côme on 29/12/2015.
 * Notifies a porofile change
 */
public class NotifyProfilChangeMessage extends Message {

    private UserLight user;

    public NotifyProfilChangeMessage(UserLight user){
        this.user=user;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        //No need for a server-side usage (yet)
    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().profileRemoteUserChange(user);
    }
}
