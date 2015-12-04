package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by rbonneau on 25/11/2015.
 */
public class UpdateProfileMessage extends Message {

    private User newProfile;

    public UpdateProfileMessage(User profile) {newProfile=profile;}

    @Override
    public void process() {

    }

    @Override
    public void process(ConnectionThread threadServer) {
        threadServer.getMyServer().getNetworkManager().getDataInstance().updateProfile(newProfile);
    }

    @Override
    public void process(ServerLink threadClient) {

    }
}
