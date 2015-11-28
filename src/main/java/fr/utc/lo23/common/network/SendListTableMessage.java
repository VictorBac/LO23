package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.ArrayList;

/**
 * Created by Jean-Côme on 24/11/2015.
 */
public class SendListTableMessage extends Message {

    private ArrayList<Table> TableList;
    public SendListTableMessage(ArrayList<Table> tables) {
        TableList=tables;
    }

    /**
     * Generic process
     */
    @Override
    public void process() {

    }

    /**
     *
     * @param threadServer
     */
    @Override
    public void process (ConnectionThread threadServer){

    }

    /**
     * Client-side process
     * @param threadClient
     */
    @Override
    public void process(ServerLink threadClient) {
        Console.logn("Envoi des tables disponibles");
        threadClient.getNetworkManager().getDataInstance().currentTables(TableList);
        Console.logn(TableList+"Envoyée");
    }
}
