package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.EnumerationTypeOfUser;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.UUID;

/**
 * Created by Jean-Côme on 01/12/2015.
 */
public class RefuseJoinTableMessage extends Message {
    private EnumerationTypeOfUser mode;
    private UUID idTab;

    public RefuseJoinTableMessage(UUID tableToJoinID, EnumerationTypeOfUser mode){
        this.idTab=tableToJoinID;
        this.mode=mode;
    }

    @Override
    public void process(ConnectionThread threadServer) {

    }

    @Override
    public void process(ServerLink threadClient) {
        Console.log("Impossible de se connecter");
        threadClient.getNetworkManager().getDataInstance().tableJoinRefused(idTab,  mode);
    }
}
