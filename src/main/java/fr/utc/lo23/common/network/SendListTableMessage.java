package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.ArrayList;

/**
 * Created by Jean-Côme on 24/11/2015.
 * Sends the list of table
 */
public class SendListTableMessage extends Message {

    private ArrayList<Table> tableList;
    public SendListTableMessage(ArrayList<Table> tables) {
        tableList=tables;
    }


    @Override
    public void process (ConnectionThread threadServer){
        //No need for a server-side usage (yet)
    }


    @Override
    public void process(ServerLink threadClient) {
        Console.logn("Envoi des tables disponibles");
        threadClient.getNetworkManager().getDataInstance().currentTables(tableList);
        Console.logn(tableList+"Envoyée");
    }
}
