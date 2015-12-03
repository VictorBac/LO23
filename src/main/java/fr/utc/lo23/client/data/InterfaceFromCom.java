package fr.utc.lo23.client.data;

import fr.utc.lo23.client.ihm_table.interfaces.ITableToDataListener;
import fr.utc.lo23.client.network.InterfaceClient;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.ExistingUserException;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.exceptions.TableException;
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
        try {
            dManagerClient.getListTablesLocal().addUserToTable(idTable,userWhoJoinTheTable,typeOfUserWhoJoinTable);
            //search the Table and send it to IHMMain dManagerClient.getInterToIHMMain();
            //TODO dManagerClient.getInterToIHMMain().userJoinedTable(dManagerClient.getListTablesLocal().getListTable().get(dManagerClient.getListTablesLocal().getTableById(idTable)),userWhoJoinTheTable,typeOfUserWhoJoinTable);

        } catch (TableException e) {
            Console.log(TAG +"User already on the table");
            e.printStackTrace();
        }
    }


    public void transmitLeaveGame(UUID idTable, UserLight userLightDistant, EnumerationTypeOfUser typeOfUserWhoLeftTable) {
        int indexOfTableInTableList = dManagerClient.getListTablesLocal().getTableById(idTable);
        Console.log(TAG +"transmitLeaveGame()");
        if(typeOfUserWhoLeftTable.equals(EnumerationTypeOfUser.PLAYER)){
            try {
                dManagerClient.getListTablesLocal().getListTable().get(indexOfTableInTableList).playerLeaveTable(userLightDistant);
               // dManagerClient.getTableLocal().playerLeaveTable(userLightDistant); //TODO check in if it is necessary, maybe already done since it it a reference
            } catch (TableException e) {
                e.printStackTrace();
            }
        }
        else if (typeOfUserWhoLeftTable.equals(EnumerationTypeOfUser.SPECTATOR)){
            try {
                dManagerClient.getListTablesLocal().getListTable().get(indexOfTableInTableList).spectatorLeaveTable(userLightDistant);
                //dManagerClient.getTableLocal().spectatorLeaveTable(userLightDistant);//TODO check in if it is necessary, maybe already done since it it a reference
            } catch (TableException e) {
                e.printStackTrace();
            }

        }
        //TODO dManagerClient.getInterToIHMMain().userLeftTable(dManagerClient.getListTablesLocal().getListTable().get(indexOfTableInTableList),userWhoJoinTheTable,typeOfUserWhoJoinTable);
    }

    public void tableJoinAccepted(UUID idTableLocalUserJoined, EnumerationTypeOfUser modeUserLocal) {
            //TODO see if necessary to contact ihm main or ihm table
        try {
            dManagerClient.getListTablesLocal().addUserToTable(idTableLocalUserJoined, dManagerClient.getUserLocal().getUserLight(),modeUserLocal);
        } catch (TableException e) {
            e.printStackTrace();
        }
        //TODO dManagerClient.getInterToIHMTable().showTable();
    }

    public UserLightList getPlayerList() {
        return null;
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
