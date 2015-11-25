package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by Ghark on 25/11/2015.
 */
public class CreateTableMessage extends Message {

    private Table newTable;

    public CreateTableMessage(Table t) {newTable=t;}


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
