package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by rbonneau on 25/11/2015.
 */
public class RequestProfileMessage extends Message {

    private UserLight profile;

    public RequestProfileMessage(UserLight u) {profile=u;}

    @Override
    public void process(ConnectionThread threadServer) {
        //TODO remplacer par le bon nom de méthode
        //User Profile threadServer.getMyServer().getNetworkManager().getDataInstance().getUserFromUserLight();
        //SendRequestedProfileMessage reqProf = new SendRequestedProfile(profile);
        //threadServer.send(reqProf);
    }

    @Override
    public void process(ServerLink threadClient) {

    }
}
