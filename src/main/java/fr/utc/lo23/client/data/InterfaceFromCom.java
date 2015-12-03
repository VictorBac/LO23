package fr.utc.lo23.client.data;

import fr.utc.lo23.client.ihm_table.interfaces.ITableToDataListener;
import fr.utc.lo23.client.network.InterfaceClient;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.ExistingUserException;
import fr.utc.lo23.common.data.Table;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.UUID;

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
            dManagerClient.getInterToIHMMain().remoteUserDisconnected(userLightDistant);
            //TODO ihmain method
        } catch (UserLightNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void notifyNewTable(Table tableCreatedOnServer) {
        Console.log(TAG +"notifyNewTable()");
        dManagerClient.getListTablesLocal().newTable(tableCreatedOnServer);
        dManagerClient.getInterToIHMMain().notifyNewTable(tableCreatedOnServer);
    }

    public void userJoinedTable(UUID idTable, UserLight userWhoJoinTheTable, EnumerationTypeOfUser typeOfUserWhoJoinTable) {
        Console.log(TAG +"userJoinedTable()");
        dManagerClient.getListTablesLocal().addUserToTable(idTable,userWhoJoinTheTable,typeOfUserWhoJoinTable);
        dManagerClient.getListTablesLocal();
        dManagerClient.getInterToIHMMain();
        //TODO search the Table and send it to IHMMain dManagerClient.getInterToIHMMain();
    }



    public void transmitLeaveGame(UserLight userLightDistant) {

    }

    public UserLightList getPlayerList() {
        return null;
    }

    public void tableJoinAccepted(Table tableLocalUserJoined, EnumerationTypeOfUser modeUserLocal) {

    }

    public void currentConnectedUser(ArrayList<UserLight> listUserLightConnectedOnServer) {
        //TODO test
        Console.log(TAG +"currentConnectedUser()");
        dManagerClient.getListUsersLightLocal().setUserList(listUserLightConnectedOnServer);
        dManagerClient.getInterToIHMMain().onlineUsers(listUserLightConnectedOnServer);

    }

    public void currentTables(ArrayList<Table> listOfTableListOnServer) {
        Console.log(TAG +"currentTables()");
        dManagerClient.getListTablesLocal().setListTable(listOfTableListOnServer);
        dManagerClient.getInterToIHMMain().currentTables(listOfTableListOnServer);
        //TODO missing IHM main interface for table list
    }

    public UserLight getUserLightLocal() {
        return dManagerClient.getUserLocal().getUserLight();
    }

    public void stockCards(PlayerHand playerHandUserLocal) {

        //TODO see with ihm table if we send all cards at the beginning of the game or at the end of a hand
    }

    public void askAction(ArrayList<Action> listActionPossibleForUserLocal) {

    }

    public void notifyAction(Action action) {

    }

    public void informEndTurn(ArrayList<UserLight> listWinner, ArrayList<Integer> listGain) {

    }

    public void saveLogGame(Table table) {

    }

    public void transmitMessage(MessageChat messageSendByRemoteUser) {
        dManagerClient.getTableLocal().getCurrentGame().getChatGame().newMessage(messageSendByRemoteUser);
        dManagerClient.getInterToIHMTable().notifyNewChatMessage(messageSendByRemoteUser);
    }

/*
    public void userJoinedTable() { //Useless
        //TODO should just increase able counter --> need id Table
        //dManagerClient.getInterToIHMMain();
        //TODO or we take UserLight as a parameter and add it to the Table (maybe type Player/Spectator) waiting harold
    }

    //TODO add a parameter discuss with Com to add id of the Table or maybe delete this one and see with userJoinedTable()
    public void addPlayer(UserLight userLightDistant) {//useless
    }*/


}
