package fr.utc.lo23.common.network;

import fr.utc.lo23.client.data.InterfaceDataFromCom;
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
        InterfaceDataFromCom dataInterface = threadClient.getNetworkManager().getDataInstance();

        UserLight luser = null;
        for(UserLight us : usersArray) {
            if(dataInterface.getUserLightLocal().getIdUser().equals(us.getIdUser())){
                luser = us;
            }
        }
        usersArray.remove(luser);

        dataInterface.currentConnectedUser(usersArray);
        dataInterface.currentTables(tablesArray);
        /*Console.log("liste User reçu");
        for(UserLight u:usersArray){
            Console.log(u.toString());
        }*/
    }

}