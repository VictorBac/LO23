package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.EnumerationTypeOfUser;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.UUID;

/**
 * Created by Jean-Côme on 10/12/2015.
 */
public class NotifyNewPlayerInTableMessage extends Message {

    private UserLight user;
    private UUID idTab;
    private EnumerationTypeOfUser mode;

    public NotifyNewPlayerInTableMessage(UserLight userLocal, UUID tableToJoinID, EnumerationTypeOfUser mode) {
        super();
        this.user = userLocal;
        this.idTab=tableToJoinID;
        this.mode=mode;
    }

    @Override
    public void process(ConnectionThread threadServer) {

    }

    @Override
    public void process(ServerLink threadClient) {
        if(!threadClient.getNetworkManager().getDataInstance().getUserLightLocal().equals(user)) {
            threadClient.getNetworkManager().getDataInstance().userJoinedTable(idTab, user, mode);
        }
    }
}
