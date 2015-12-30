package fr.utc.lo23.client.data;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.ExistingUserException;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.exceptions.TableException;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Mar on 24/10/2015.
 * Class that implement of the interfaceDataFromCom
 */
public class InterfaceFromCom implements InterfaceDataFromCom{
//TODO handle when userlocal leave Table update local value Table and listTable
    private final String TAG ="InterfaceFromCom ";
    private DataManagerClient dManagerClient;

    /**
     * Constructor
     * @param dManagerClient
     */
    public InterfaceFromCom(DataManagerClient dManagerClient) {
        this.dManagerClient = dManagerClient;
    }


    /**
     * Method to update the list of UserLight connected with a new Remote userLight
     *
     * @param userLightDistant new UserLight that need to be add
     */
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
            dManagerClient.getInterToIHMMain().tableCreated(tableCreatedOnServer);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    dManagerClient.getInterToIHMTable().showTable(tableCreatedOnServer);
                }
            });
        }
    }


    public void userJoinedTable(UUID idTable, UserLight userWhoJoinTheTable, EnumerationTypeOfUser typeOfUserWhoJoinTable) {
        Console.log(TAG +"userJoinedTableRemote()");
        try {
            dManagerClient.getListTablesLocal().addUserToTable(idTable,userWhoJoinTheTable,typeOfUserWhoJoinTable);

            //search the Table and send it to IHMMain
            dManagerClient.getInterToIHMMain().userJoinedTableRemote(dManagerClient.getListTablesLocal().getTable(idTable), userWhoJoinTheTable, typeOfUserWhoJoinTable);

            //if the player has join the same Table as the local user we inform IHMTable
            if(dManagerClient.getTableLocal() != null && idTable.equals(dManagerClient.getTableLocal().getIdTable())) {
                dManagerClient.getInterToIHMTable().notifyNewUser(userWhoJoinTheTable, typeOfUserWhoJoinTable == EnumerationTypeOfUser.PLAYER);
                dManagerClient.getInterToIHMMain().userJoinedTableLocal(userWhoJoinTheTable, typeOfUserWhoJoinTable);
            }
        } catch (TableException e) {
            Console.log(TAG +"User already on the table");
            e.printStackTrace();
        }
    }


    public void transmitLeaveGame(UUID idTable, UserLight userLightLeavingGame, EnumerationTypeOfUser typeOfUserWhoLeftTable) {
//TODO handle when Userlight ==localUser and idTable==idTableLocal and player or spectator
        Console.log(TAG +"transmitLeaveGame()");
        //if it corresponds to the local user
        if(dManagerClient.getUserLocal().getUserLight().equals(userLightLeavingGame)){
            //nothing
        }
        else{ //if it corresponds to a remote User
            //if it is local Table
            if(dManagerClient.getTableLocal() != null && idTable.equals(dManagerClient.getTableLocal().getIdTable())){
                dManagerClient.getInterToIHMMain().userLeftTableLocal(dManagerClient.getListTablesLocal().getTable(idTable),userLightLeavingGame,typeOfUserWhoLeftTable);
                if(typeOfUserWhoLeftTable.equals(EnumerationTypeOfUser.PLAYER)){
                    try {
                        dManagerClient.getListTablesLocal().getTable(idTable).playerLeaveTable(userLightLeavingGame);
                        dManagerClient.getInterToIHMTable().notifyUserLeft(userLightLeavingGame,true);
                    } catch (TableException e) {
                        e.printStackTrace();
                    }
                }
                else if (typeOfUserWhoLeftTable.equals(EnumerationTypeOfUser.SPECTATOR)){
                    try {
                        dManagerClient.getListTablesLocal().getTable(idTable).spectatorLeaveTable(userLightLeavingGame);
                        dManagerClient.getInterToIHMTable().notifyUserLeft(userLightLeavingGame,false);
                    } catch (TableException e) {
                        e.printStackTrace();
                    }
                }
            }
            else{            //if it is remote Table
                if(typeOfUserWhoLeftTable.equals(EnumerationTypeOfUser.PLAYER)){
                    try {
                        dManagerClient.getListTablesLocal().getTable(idTable).playerLeaveTable(userLightLeavingGame);
                    } catch (TableException e) {
                        e.printStackTrace();
                    }
                }
                else if (typeOfUserWhoLeftTable.equals(EnumerationTypeOfUser.SPECTATOR)){
                    try {
                        dManagerClient.getListTablesLocal().getTable(idTable).spectatorLeaveTable(userLightLeavingGame);
                    } catch (TableException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        dManagerClient.getInterToIHMMain().userLeftTableRemote(dManagerClient.getListTablesLocal().getTable(idTable), userLightLeavingGame, typeOfUserWhoLeftTable);
    }

    public void tableJoinAccepted(UUID idTableLocalUserJoined, EnumerationTypeOfUser modeUserLocal) {
        Console.log(TAG +"tableJoinAccepted()");

        try {
            dManagerClient.setTableLocal(dManagerClient.getListTablesLocal().addUserToTable(idTableLocalUserJoined, dManagerClient.getUserLocal().getUserLight(), modeUserLocal));
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

    public void stopGame(){
        dManagerClient.getInterToIHMTable().stopGame();
    }

    public void infosReadyAnswerFromOtherPlayer(UserLight player, Boolean answer){
        Console.log(TAG +"answerReadyGame()");
        dManagerClient.getInterToIHMTable().notifyReadyGameAnswer(player,answer);
    }

    public void deleteTable(UUID idTableDestroyed){
        Table t = dManagerClient.getListTablesLocal().getTable(idTableDestroyed);
        try {
            dManagerClient.getListTablesLocal().deleteTable(t);
        } catch (TableException e) {
            e.printStackTrace();
        }
        dManagerClient.getInterToIHMMain().notifyDeletedTable(t);
    }


    public void acceptLogin() {
        dManagerClient.getInterToIHMMain().notifyLoginAccepted();
    }

    public void refuseLogin(String reason) {
        dManagerClient.getInterToIHMMain().notifyLoginRefused(reason);
    }

    /**
     * Method which is called when a remote User has change his profile. User to inform other user of this modification
     * @param newProfileRemoteUser UserLight with its modification
     */
    public void profileRemoteUserChange(UserLight newProfileRemoteUser){
        Console.log(TAG +"profileRemoteUserChange");
        if (!dManagerClient.getUserLocal().getUserLight().getIdUser().equals(newProfileRemoteUser.getIdUser())) {
            dManagerClient.getListUsersLightLocal().changeUserLightProfile(newProfileRemoteUser);
            dManagerClient.getInterToIHMMain().updateUserRemote(newProfileRemoteUser);
        }
    }
}
