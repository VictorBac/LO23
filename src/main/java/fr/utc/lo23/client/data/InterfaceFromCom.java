package fr.utc.lo23.client.data;

import fr.utc.lo23.client.ihm_table.interfaces.ITableToDataListener;
import fr.utc.lo23.client.network.InterfaceClient;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.ExistingUserException;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.exceptions.TableException;
import javafx.application.Platform;
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
            //TODO: handle spectateur et player
            dManagerClient.getListUsersLightLocal().addUser(userLightDistant);
            dManagerClient.getInterToIHMMain().remoteUserConnected(userLightDistant);
        } catch (ExistingUserException e) {
            e.printStackTrace();
        }
    }

    public void remoteUserDisonnected(UserLight userLightDistant) {
        try {//TODO handle exception and test
            Console.log(TAG +"remoteUserDisonnected");
            //TODO: handle spectateur et player
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
        System.out.println(tableCreatedOnServer.getCreator());
        System.out.println(dManagerClient.getUserLocal());
        if(tableCreatedOnServer.getCreator().equals(dManagerClient.getUserLocal().getUserLight())) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    dManagerClient.getInterToIHMTable().showTable(tableCreatedOnServer);
                }
            });
        }
    }


    public void userJoinedTable(UUID idTable, UserLight userWhoJoinTheTable, EnumerationTypeOfUser typeOfUserWhoJoinTable) {
        Console.log(TAG +"userJoinedTable()");
        try {
            dManagerClient.getListTablesLocal().addUserToTable(idTable,userWhoJoinTheTable,typeOfUserWhoJoinTable);

            //search the Table and send it to IHMMain
            dManagerClient.getInterToIHMMain().userJoinedTable(dManagerClient.getListTablesLocal().getTable(idTable), userWhoJoinTheTable, typeOfUserWhoJoinTable);

            //if the player has join the same Table as the local user we inform IHMTable
            if(idTable.equals(dManagerClient.getTableLocal().getIdTable()))
                dManagerClient.getInterToIHMTable().notifyNewUser(userWhoJoinTheTable, typeOfUserWhoJoinTable == EnumerationTypeOfUser.PLAYER);

        } catch (TableException e) {
            Console.log(TAG +"User already on the table");
            e.printStackTrace();
        }
    }


    public void transmitLeaveGame(UUID idTable, UserLight userLightLeavingGame, EnumerationTypeOfUser typeOfUserWhoLeftTable) {
//TODO handle when Userlight ==localUser and idTable==idTableLocal and player or spectator
        Console.log(TAG +"transmitLeaveGame()");
        //if it corresponds to the local user
       /* if(dManagerClient.getUserLocal().equals(userLightLeavingGame)){

            if(typeOfUserWhoLeftTable.equals(EnumerationTypeOfUser.PLAYER)){
                try {
                    dManagerClient.getListTablesLocal().getTable(idTable).playerLeaveTable(userLightLeavingGame);
                    dManagerClient.getInterToIHMMain().userLeftTable(dManagerClient.getListTablesLocal().getTable(idTable),userLightLeavingGame,typeOfUserWhoLeftTable);
                } catch (TableException e) {
                    e.printStackTrace();
                }
            }
            else if (typeOfUserWhoLeftTable.equals(EnumerationTypeOfUser.SPECTATOR)){
                try {
                    dManagerClient.getListTablesLocal().getTable(idTable).spectatorLeaveTable(userLightLeavingGame);
                    dManagerClient.getInterToIHMMain().userLeftTable(dManagerClient.getListTablesLocal().getTable(idTable),userLightLeavingGame,typeOfUserWhoLeftTable);
                } catch (TableException e) {
                    e.printStackTrace();
                }
            }

        }
        else{ //if it corresponds to a remote User
            if(typeOfUserWhoLeftTable.equals(EnumerationTypeOfUser.PLAYER)){
                try {
                    dManagerClient.getListTablesLocal().getTable(idTable).playerLeaveTable(userLightLeavingGame);
                    dManagerClient.getInterToIHMMain().userLeftTable(dManagerClient.getListTablesLocal().getTable(idTable),userLightLeavingGame,typeOfUserWhoLeftTable);
                } catch (TableException e) {
                    e.printStackTrace();
                }
            }
            else if (typeOfUserWhoLeftTable.equals(EnumerationTypeOfUser.SPECTATOR)){
                try {
                    dManagerClient.getListTablesLocal().getTable(idTable).spectatorLeaveTable(userLightLeavingGame);
                    dManagerClient.getInterToIHMMain().userLeftTable(dManagerClient.getListTablesLocal().getTable(idTable),userLightLeavingGame,typeOfUserWhoLeftTable);
                } catch (TableException e) {
                    e.printStackTrace();
                }
            }

        }*/
        if(typeOfUserWhoLeftTable.equals(EnumerationTypeOfUser.PLAYER)){
            try {
                dManagerClient.getListTablesLocal().getTable(idTable).playerLeaveTable(userLightLeavingGame);
                dManagerClient.getInterToIHMMain().userLeftTable(dManagerClient.getListTablesLocal().getTable(idTable),userLightLeavingGame,typeOfUserWhoLeftTable);
            } catch (TableException e) {
                e.printStackTrace();
            }
        }
        else if (typeOfUserWhoLeftTable.equals(EnumerationTypeOfUser.SPECTATOR)){
            try {
                dManagerClient.getListTablesLocal().getTable(idTable).spectatorLeaveTable(userLightLeavingGame);
                dManagerClient.getInterToIHMMain().userLeftTable(dManagerClient.getListTablesLocal().getTable(idTable),userLightLeavingGame,typeOfUserWhoLeftTable);
            } catch (TableException e) {
                e.printStackTrace();
            }
        }
    }

    public void tableJoinAccepted(UUID idTableLocalUserJoined, EnumerationTypeOfUser modeUserLocal) {
        Console.log(TAG +"tableJoinAccepted()");
        try {
            dManagerClient.setTableLocal(dManagerClient.getListTablesLocal().addUserToTable(idTableLocalUserJoined, dManagerClient.getUserLocal().getUserLight(), modeUserLocal));
            Console.log(TAG+"id Table local"+dManagerClient.getTableLocal().getIdTable());
            dManagerClient.getInterToIHMMain().tableJoinAccepted(dManagerClient.getListTablesLocal().getTable(idTableLocalUserJoined), modeUserLocal);
        } catch (TableException e) {
            e.printStackTrace();
        }
    }

    public void tableJoinRefused(UUID idTableLocalUserJoined, EnumerationTypeOfUser modeUserLocal){
        Console.log(TAG +"tableJoinRefused()");
        dManagerClient.getInterToIHMMain().tableJoinRefused(dManagerClient.getListTablesLocal().getTable(idTableLocalUserJoined));
    }

    public void currentConnectedUser(ArrayList<UserLight> listUserLightConnectedOnServer) {
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
        //Console.log("dManagerClient : " + dManagerClient);
        //Console.log("dManagerClient.getUserLocal() : " + dManagerClient.getUserLocal());
        return dManagerClient.getUserLocal().getUserLight();
    }

    public void stockCards(ArrayList<PlayerHand> playerHandUserLocal) {
        Console.log(TAG +"stockCards()");
        if(playerHandUserLocal.size()==1)
        {
            if(playerHandUserLocal.get(0).getPlayer()==null)
                dManagerClient.getInterToIHMTable().notifyCommonCards(playerHandUserLocal.get(0).getListCardsHand());
            else
                dManagerClient.getInterToIHMTable().notifyPlayersCards(playerHandUserLocal);
        }
        else {
            //dManagerClient.getTableLocal().getCurrentGame().getCurrentHand().getListPlayerHand().add(playerHandUserLocal);
            dManagerClient.getInterToIHMTable().notifyPlayersCards(playerHandUserLocal);
            //we send first cards at the beginning of the hand from the local user and at the end of the hand we show other cards
        }
    }

    public void transmitMessage(MessageChat messageSendByRemoteUser) {
        Console.log(TAG + "transmitMessage()");
        dManagerClient.getInterToIHMTable().notifyNewChatMessage(messageSendByRemoteUser);
    }


    public void remoteUserProfile(User profileReturnedByTheServer){
        Console.log(TAG + "remoteUserProfile()");
        dManagerClient.getInterToIHMMain().profileRemoteUserFromServer(profileReturnedByTheServer);
    }

    public void updateStats(Stats statsLocalUser) {
        //TODO if this the correct way to change the stats or add the latest stats
        dManagerClient.getUserLocal().setStatsUser(statsLocalUser);
        //for notifying the player that its Stats has changed
        dManagerClient.getInterToIHMMain().userStatsUpdated(dManagerClient.getUserLocal().getUserLight(),statsLocalUser );
    }

    public void startGame(UUID idTable){
        Console.log(TAG + "startGame()");
        dManagerClient.getInterToIHMTable().notifyStartGame(dManagerClient.getListTablesLocal().getTable(idTable));
    }

    public void tableCreatorRequestToStartGameRejected(){
        Console.log(TAG +"tableCreatorRequestToStartGameRejected()");
        dManagerClient.getInterToIHMTable().notifyRefuseStartGame();
    }

    public void tableCreatorRequestToStartGameAccepted(){
        Console.log(TAG +"tableCreatorRequestToStartGameAccepted()");
        dManagerClient.getInterToIHMTable().notifySuccessStartGame();
    }


    public void askAmountMoney(){
        Console.log(TAG +"askAmountMoney()");
        dManagerClient.getInterToIHMTable().askMoneyAmount();
    }

    public void notifyMoneyAmountAnswerFromServer(UserLight player, Integer amount){
        Console.log(TAG +"notifyMoneyAmountAnswerFromServer()");
        dManagerClient.getInterToIHMTable().notifyMoneyAmountAnswer(player, amount);
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


    public void informNewHand(){
        Console.log(TAG +"informNewHand()");
        dManagerClient.getInterToIHMTable().notifyStartHand();
    }

    public void informNewTurn(){
        Console.log(TAG +"informNewTurn()");
        dManagerClient.getInterToIHMTable().notifyStartTour();
    }


    public void informEndTurn(Integer potForThisTurn){
        Console.log(TAG +"informEndTurn()");
        dManagerClient.getInterToIHMTable().notifyEndTour(potForThisTurn);
    }

    public void informEndHand(ArrayList<Seat> listSeat,ArrayList<PlayerHand> apla) {
        Console.log(TAG +"informEndHand()");
        dManagerClient.getInterToIHMTable().notifyEndHand(listSeat,apla);
    }

    public void saveLogGame(Table tableThatContainGameToSave) {
        //TODO need to test
        Console.log(TAG +"saveLogGame()");
        TableList listOfAllTableSaved = (TableList) Serialization.deserializationObject(Serialization.dirLocalSavedFiles + dManagerClient.getUserLocal().getLogin()+Serialization.pathSavedGame);

        if (listOfAllTableSaved==null){
            listOfAllTableSaved = new TableList();
        }

        listOfAllTableSaved.getListTable().add(tableThatContainGameToSave);
        Serialization.serializationObject(listOfAllTableSaved,Serialization.dirLocalSavedFiles + dManagerClient.getUserLocal().getLogin() + Serialization.pathSavedGame);

        //TODO inform table or main for saved game?

    }


    public void stopGame(Game gameOfTheUserLocal){
        dManagerClient.getInterToIHMTable().stopGame(gameOfTheUserLocal);
    }



    public void infosReadyAnswerFromOtherPlayer(UserLight player, Boolean answer){
        dManagerClient.getInterToIHMTable().notifyReadyGameAnswer(player,answer);
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
