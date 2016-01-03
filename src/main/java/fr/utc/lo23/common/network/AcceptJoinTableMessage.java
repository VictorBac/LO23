package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.EnumerationTypeOfUser;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.UUID;

/**
 * Created by Jean-Côme on 01/12/2015.
 * Message to inform the player that he has been
 * accepted to the requested table
 */
public class AcceptJoinTableMessage extends Message {

    private EnumerationTypeOfUser mode;
    private UUID idTable;

    public AcceptJoinTableMessage(UUID idTableToJoin, EnumerationTypeOfUser mode){
        this.idTable=idTableToJoin;
        this.mode=mode;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        //No need for a server-side usage (yet)
    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().tableJoinAccepted(idTable,  mode);
    }
}
