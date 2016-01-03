package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.UUID;

/**
 * Created by Primate on 30/12/2015.
 */
public class AddGameToTableMessage extends Message {
    private UUID tableUUID;
    private UserLight user;

    public AddGameToTableMessage(UUID tableUUID, UserLight user) {
        this.tableUUID = tableUUID;
        this.user = user;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        threadServer.getMyServer().getNetworkManager().getDataInstance().addGameUsingCurrent(tableUUID, user);
    }

    @Override
    public void process(ServerLink threadClient) {
        //No need for a client-side usage (yet)
    }
}
