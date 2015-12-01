package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by rbonneau on 25/11/2015.
 */
public class CreateTableMessage extends Message {

    private UserLight maker;
    private Table newTable;

    public CreateTableMessage(UserLight u, Table t) {maker = u; newTable=t;}


    @Override
    public void process() {

    }

    @Override
    public void process(ConnectionThread threadServer) {
        threadServer.getMyServer().getNetworkManager().getDataInstance().createTable(maker,newTable);

    }

    @Override
    public void process(ServerLink threadClient) {

    }
}
