package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.UUID;

/**
 * Send the action of a player to the server
 * Created by rbonneau on 11/12/2015.
 */
public class SendActionMessage extends Message {

    Action act;
    UUID tableID;

    public SendActionMessage(Action a, UUID idTable) {
        this.act = a;
        this.tableID = idTable;
    }


    @Override
    public void process(ConnectionThread threadServer) {
        threadServer.getMyServer().getNetworkManager().getDataInstance().replyAction(tableID,act);

    }

    @Override
    public void process(ServerLink threadClient) {
        //No need for a client-side usage (yet)
    }
}
