package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.server.network.threads.ConnectionThread;

/**
 * Created by Jean-Côme on 10/12/2015.
 * Notifies the other players that a new table has been created
 */
public class NotifyNewTableMessage extends Message {

    public Table tab;
    public NotifyNewTableMessage(Table table){
        this.tab=table;
    }

    @Override
    public void process(ConnectionThread threadServer) {
        //No need for a server-side usage (yet)
    }

    @Override
    public void process(ServerLink threadClient) {
        Console.log("Je recois une table : "+tab);
        threadClient.getNetworkManager().getDataInstance().notifyNewTable(tab);
    }
}
