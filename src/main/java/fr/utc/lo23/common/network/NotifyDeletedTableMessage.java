package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.UUID;

/**
 * Created by Jean-Côme on 27/12/2015.
 */
public class NotifyDeletedTableMessage extends Message {

    private final UUID table;

    public NotifyDeletedTableMessage(UUID table) {
        this.table = table;
    }

    @Override
    public void process(ConnectionThread threadServer) {

    }

    @Override
    public void process(ServerLink threadClient) {
        threadClient.getNetworkManager().getDataInstance().deleteTable(this.table);
    }
}
