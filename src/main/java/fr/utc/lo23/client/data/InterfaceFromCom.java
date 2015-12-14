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
            dManagerClient.getListUsersLightLocal().remove(userLightDistant);
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
             dManagerClient.getInterToIHMMain().userJoinedTable(dManagerClient.getListTablesLocal().getTable(idTable),userWhoJoinTheTable,typeOfUserWhoJoinTable);

        } catch (TableException e) {
            Console.log(TAG +"User already on the table");
            e.printStackTrace();
        }
    }


    public void transmitLeaveGame(UUID idTable, UserLight userLightDistant, EnumerationTypeOfUser typeOfUserWhoLeftTable) {

        Console.log(TAG +"transmitLeaveGame()");
        if(typeOfUserWhoLeftTable.equals(EnumerationTypeOfUser.PLAYER)){
            try {
                dManagerClient.getListTablesLocal().getTable(idTable).playerLeaveTable(userLightDistant);
                dManagerClient.getInterToIHMMain().userLeftTable(dManagerClient.getListTablesLocal().getTable(idTable),userLightDistant,typeOfUserWhoLeftTable);
            } catch (TableException e) {
                e.printStackTrace();
            }
        }
        else if (typeOfUserWhoLeftTable.equals(EnumerationTypeOfUser.SPECTATOR)){
            try {
                dManagerClient.getListTablesLocal().getTable(idTable).spectatorLeaveTable(userLightDistant);
                dManagerClient.getInterToIHMMain().userLeftTable(dManagerClient.getListTablesLocal().getTable(idTable),userLightDistant,typeOfUserWhoLeftTable);
            } catch (TableException e) {
                e.printStackTrace();
            }
        }

    }

    public void tableJoinAccepted(UUID idTableLocalUserJoined, EnumerationTypeOfUser modeUserLocal) {
        Console.log(TAG +"tableJoinAccepted()");
        try {
            dManagerClient.setTableLocal(dManagerClient.getListTablesLocal().addUserToTable(idTableLocalUserJoined, dManagerClient.getUserLocal().getUserLight(), modeUserLocal));
            //TODO need to contact IHMMain missing interface   dManagerClient.getInterToIHMMain();
        } catch (TableException e) {
            e.printStackTrace();
        }
    }

    public void tableJoinRefused(UUID idTableLocalUserJoined, EnumerationTypeOfUser modeUserLocal){
        Console.log(TAG +"tableJoinRefused()");
        dManagerClient.getInterToIHMMain().tableJoinRefused(dManagerClient.getListTablesLocal().getTable(idTableLocalUserJoined));
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
    }

    public UserLight getUserLightLocal() {
        Console.log("dManagerClient : " + dManagerClient);
        Console.log("dManagerClient.getUserLocal() : " + dManagerClient.getUserLocal());
        return dManagerClient.getUserLocal().getUserLight();
    }

    public void stockCards(PlayerHand playerHandUserLocal) {
        Console.log(TAG +"stockCards()");
        dManagerClient.getTableLocal().getCurrentGame().getCurrentHand().getListPlayerHand().add(playerHandUserLocal);
        dManagerClient.getInterToIHMTable().notifyPlayersCards(dManagerClient.getTableLocal().getCurrentGame().getCurrentHand().getListPlayerHand());
        //we send first cards at the beginning of the hand from the local user and at the end of the hand we show other cards
    }

    public void transmitMessage(MessageChat messageSendByRemoteUser) {
        Console.log(TAG +"transmitMessage()");
        dManagerClient.getInterToIHMTable().notifyNewChatMessage(messageSendByRemoteUser);
    }


    public void remoteUserProfile(User profileReturnedByTheServer){
        Console.log(TAG +"remoteUserProfile()");
        //TODO add this line after integration dManagerClient.getInterToIHMMain().profileRemoteUserFromServer(profileReturnedByTheServer);
    }

    public void updateStats(Stats statsLocalUser) {

        //TODO if this the correct way to change the stats or add the latest stats
        dManagerClient.getUserLocal().setStatsUser(statsLocalUser);
        //for notifying the player that its Stats has changed
        dManagerClient.getInterToIHMMain().userStatsUpdated(dManagerClient.getUserLocal().getUserLight(),statsLocalUser );
    }

    public void startGame(UUID idTable){
        Console.log(TAG +"startGame()");
        dManagerClient.getInterToIHMTable().notifyStartGame(dManagerClient.getListTablesLocal().getTable(idTable));
    }

    public void tableCreatorRequestToStartGameRejected(){
        Console.log(TAG +"tableCreatorRequestToStartGameRejected()");
        //TODO fill this method with ihmTable method dManagerClient.getInterToIHMTable();
    }

    public void tableCreatorRequestToStartGameAccepted(){
        Console.log(TAG +"tableCreatorRequestToStartGameAccepted()");
        //TODO fill this method with ihmTable method dManagerClient.getInterToIHMTable();
    }


    public void askAmountMoney(){
        Console.log(TAG +"askAmountMoney()");
        dManagerClient.getInterToIHMTable().askMoneyAmount();
    }

    public void notifyMoneyAmountAnswerFromServer(UserLight player, Integer amount){
        Console.log(TAG +"notifyMoneyAmountAnswerFromServer()");
        dManagerClient.getInterToIHMTable().notifyMoneyAmountAnswer(player,amount);
    }

    public void askReadyGame(){
        Console.log(TAG +"askReadyGame()");
        dManagerClient.getInterToIHMTable().askReadyGame();
    }


    public void askAction(Action actionEmpty, EnumerationAction[] listActionPossibleForUserLocal) {
        Console.log(TAG +"askAction()");
        dManagerClient.getInterToIHMTable().askAction(actionEmpty, listActionPossibleForUserLocal);
    }

    public void notifyAction(Action action) {
        Console.log(TAG +"notifyAction()");
        dManagerClient.getInterToIHMTable().notifyAction(action);
    }

    public void informEndTurn(Integer potForThisTurn){
        Console.log(TAG +"informEndTurn()");
        dManagerClient.getInterToIHMTable().notifyEndTour(potForThisTurn);
    }

    public void informEndHand(ArrayList<Seat> listSeat) {
        Console.log(TAG +"informEndHand()");
        dManagerClient.getInterToIHMTable().notifyEndHand(listSeat);
    }

    public void saveLogGame(Table tableThatContainGameToSave) {
        //TODO need to test
        Console.log(TAG +"saveLogGame()");
        TableList listOfAllTableSaved = (TableList) Serialization.deserializationObject(dManagerClient.getUserLocal().getLogin()+Serialization.pathSavedGame);

        if (listOfAllTableSaved==null){
            listOfAllTableSaved = new TableList();
        }

        listOfAllTableSaved.getListTable().add(tableThatContainGameToSave);
        Serialization.serializationObject(listOfAllTableSaved,dManagerClient.getUserLocal().getLogin()+Serialization.pathSavedGame);

        //TODO inform table or main for saved game?

    }

























    public UserLightList getPlayerList() {
        return null;
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
