package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.EnumerationTypeOfUser;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.UUID;

/**
 * Created by Jean-Côme on 01/12/2015.
 */
public class AcceptJoinTableMessage extends Message {

    private EnumerationTypeOfUser mode;
    private UUID idTab;

    public AcceptJoinTableMessage(UUID tableToJoinID, EnumerationTypeOfUser mode){
        this.idTab=tableToJoinID;
        this.mode=mode;
    }

    @Override
    public void process(ConnectionThread threadServer) {

    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().tableJoinAccepted(idTab,  mode);
    }
}
