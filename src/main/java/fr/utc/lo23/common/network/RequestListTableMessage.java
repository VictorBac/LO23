package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.server.network.threads.ConnectionThread;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.util.ArrayList;

/**
 * Created by Jean-Côme on 24/11/2015.
 * Ask the table currently created on the server
 */
public class RequestListTableMessage extends Message {


    public RequestListTableMessage() {

    }

    @Override
    public void process (ConnectionThread threadServer){
        PokerServer myServ = threadServer.getMyServer();
        Console.log("Request list of table");
        ArrayList<Table> listTables = myServ.getNetworkManager().getDataInstance().getTableList();
        SendListTableMessage listTableMess = new SendListTableMessage(listTables);
        threadServer.send(listTableMess);
    }

    @Override
    public void process(ServerLink threadClient) {
        //No need for a client-side usage (yet)
    }

}
