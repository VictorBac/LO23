package fr.utc.lo23.common.network;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.client.network.threads.ServerLink;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.server.network.threads.ConnectionThread;

import java.util.ArrayList;

/**
 * Created by raphael on 03/11/15.
 */
public class AcceptLoginMessage extends Message{

    private ArrayList<UserLight> usersArray;
    private ArrayList<Table> tablesArray;

    public AcceptLoginMessage(ArrayList<UserLight> aUser, ArrayList<Table> aTable) {
        usersArray=aUser;
        tablesArray=aTable;
    }

    /**
     * For message processed server-side
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
        for(UserLight us : usersArray){
            if(threadClient.getNetworkManager().getDataInstance().getUserLightLocal().getIdUser().equals(us.getIdUser())){
                usersArray.remove(us);
            }
        }
        threadClient.getNetworkManager().getDataInstance().currentConnectedUser(usersArray);
        threadClient.getNetworkManager().getDataInstance().currentTables(tablesArray);
        Console.log("liste User reçu");
        for(UserLight u:usersArray){
            Console.log(u.toString());
        }
    }

}