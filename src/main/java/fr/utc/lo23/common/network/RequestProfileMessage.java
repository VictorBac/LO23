package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by Ghark on 25/11/2015.
 */
public class RequestProfileMessage extends Message {

    private UserLight profile;

    public RequestProfileMessage(UserLight u) {profile=u;}

    @Override
    public void process() {

    }

    @Override
    public void process(ConnectionThread threadServer) {

    }

    @Override
    public void process(ServerLink threadClient) {

    }
}
