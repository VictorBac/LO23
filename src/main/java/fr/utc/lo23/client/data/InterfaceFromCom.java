package fr.utc.lo23.client.data;

import fr.utc.lo23.client.ihm_table.ITableToDataListener;
import fr.utc.lo23.client.network.InterfaceClient;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.ExistingUserException;
import fr.utc.lo23.common.data.Table;
import sun.rmi.runtime.Log;

import java.util.ArrayList;

/**
 * Created by Mar on 24/10/2015.
 */
public class InterfaceFromCom implements InterfaceDataFromCom{

    private final String TAG ="InterfaceFromCom";
    private DataManagerClient dManagerClient;


    public InterfaceFromCom(DataManagerClient dManagerClient) {
        this.dManagerClient = dManagerClient;
    }

    public void updateStats(Stats statsLocalUser) {

    }

    public void remoteUserConnected(UserLight userLightDistant) {
        try {//TODO handle exception and test
            Console.log(TAG +"remoteUserConnected");
            dManagerClient.getListUsersLightLocal().addUser(userLightDistant);
            dManagerClient.getInterToIHMMain().remoteUserConnected(userLightDistant);
        } catch (ExistingUserException e) {
            e.printStackTrace();
        }
    }

    public void remoteUserDisonnected(UserLight userLightDistant) {
        try {//TODO handle exception and test
            Console.log(TAG +"remoteUserDisonnected");
            dManagerClient.getListUsersLightLocal().remove(userLightDistant); //TODO add log for deconnection
            //TODO ihmain method
        } catch (UserLightNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void notifyNewTable(Table tableCreatedOnServer) {
        Console.log(TAG +"notifyNewTable()");
        dManagerClient.getListTablesLocal().newTable(tableCreatedOnServer);

        //TODO missing IHM main interface for new Table
    }

    public void userJoinedTable() {

    }

    public void addPlayer(UserLight userLightDistant) {

    }

    public void transmitLeaveGame(UserLight userLightDistant) {

    }

    public UserLightList getPlayerList() {
        return null;
    }

    public void tableJoinAccepted(Table tableLocalUserJoined, String modeUserLocal) {

    }

    public void currentConnectedUser(ArrayList<UserLight> listUserLightConnectedOnServer) {
        //TODO test
        dManagerClient.getListUsersLightLocal().setUserList(listUserLightConnectedOnServer);
        dManagerClient.getInterToIHMMain().onlineUsers(listUserLightConnectedOnServer);

    }

    public void currentTables(ArrayList<Table> listOfTableListOnServer) {
        dManagerClient.getListTablesLocal().setListTable(listOfTableListOnServer);
        //TODO missing IHM main interface for table list
    }

    public UserLight getUserLightLocal() {
        return dManagerClient.getUserLocal().getUserLight();
    }

    public void stockCards(PlayerHand playerHandUserLocal) {

    }

    public void askAction(ArrayList<Action> listActionPossibleForUserLocal) {

    }

    public void notifyAction(Action action, UserLight userLight) {

    }

    public void informEndTurn(ArrayList<UserLight> listWinner, ArrayList<Integer> listGain) {

    }

    public void saveLogGame(Table table) {

    }
}
