package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by rbonneau on 01/12/2015.
 */

public class SendRequestedProfileMessage  extends Message {

    private User reqProfile;


    public SendRequestedProfileMessage(User u) {reqProfile=u;}

    @Override
    public void process(ConnectionThread threadServer) {

    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().remoteUserProfile(reqProfile);
    }

}
