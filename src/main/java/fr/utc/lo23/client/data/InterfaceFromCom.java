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
     * @param dManagerClient DataManagerClient
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
        try {
            Console.log(TAG +"remoteUserConnected");
            dManagerClient.getListUsersLightLocal().addUser(userLightDistant);
            dManagerClient.getInterToIHMMain().remoteUserConnected(userLightDistant);
        } catch (ExistingUserException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to update the list of UserLight disconnected  removing a userLight
     *
     * @param userLightDistant UserLight that need to be removed
     */
    public void remoteUserDisonnected(UserLight userLightDistant) {
        try {
            Console.log(TAG +"remoteUserDisonnected");

            dManagerClient.getListUsersLightLocal().remove(userLightDistant);
            dManagerClient.getInterToIHMMain().remoteUserDisconnected(userLightDistant);
        } catch (UserLightNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method to update the Table List with a new Table created on the server
     *
     * @param tableCreatedOnServer new Table that was created on the server
     */
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

    /**
     * Method to call when a remote user is connected on a specific Table,
     * Warning :  DO Not use it for local user (=userWhoJoinTheTable)
     *
     * @param idTable                Table on which the remote user connect to
     * @param userWhoJoinTheTable    the UserLight for the remote User
     * @param typeOfUserWhoJoinTable type of User Spectator/Player
     */
    public void userJoinedTable(UUID idTable, UserLight userWhoJoinTheTable, EnumerationTypeOfUser typeOfUserWhoJoinTable) {
        Console.log(TAG +"userJoinedTableRemote()");
        try {
            dManagerClient.getListTablesLocal().addUserToTable(idTable,userWhoJoinTheTable,typeOfUserWhoJoinTable);

            //search the Table and send it to IHMMain
            dManagerClient.getInterToIHMMain().userJoinedTableRemote(dManagerClient.getListTablesLocal().getTable(idTable), userWhoJoinTheTable, typeOfUserWhoJoinTable);

            //if the player has join the same Table as the local user we inform IHMTable
            if(dManagerClient.getTableLocal() != null && idTable.equals(dManagerClient.getTableLocal().getIdTable())) {
                dManagerClient.getInterToIHMTable().notifyNewUser(userWhoJoinTheTable, typeOfUserWhoJoinTable.equals(EnumerationTypeOfUser.PLAYER) );
                dManagerClient.getInterToIHMMain().userJoinedTableLocal(userWhoJoinTheTable, typeOfUserWhoJoinTable);
            }
        } catch (TableException e) {
            Console.log(TAG +"User already on the table");
            e.printStackTrace();
        }
    }





    /**
     * Method to notify that a user left the game
     *
     * @param idTable                id of the table the user left
     * @param userLightLeavingGame       UserLight of a user who left a Game
     * @param typeOfUserWhoLeftTable the user is a spectator or a player
     */
    public void transmitLeaveGame(UUID idTable, UserLight userLightLeavingGame, EnumerationTypeOfUser typeOfUserWhoLeftTable) {
        Console.log(TAG +"transmitLeaveGame()");
        //if it corresponds to the local user : Userlight ==localUser
        if(dManagerClient.getUserLocal().getUserLight().equals(userLightLeavingGame)){
            //nothing
        }
        else{ //if it corresponds to a remote User
            //if it is local Table : idTable==idTableLocal
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





    /**
     * Method to confirm that the local user has actually correctly join the table
     *
     * @param idTableLocalUserJoined the id of the Table the User joined
     * @param modeUserLocal          an EnumerationTypeOfUser the mode which he has chosen to adopt spectator or player
     */
    public void tableJoinAccepted(UUID idTableLocalUserJoined, EnumerationTypeOfUser modeUserLocal) {
        Console.log(TAG +"tableJoinAccepted()");

        try {
            dManagerClient.setTableLocal(dManagerClient.getListTablesLocal().addUserToTable(idTableLocalUserJoined, dManagerClient.getUserLocal().getUserLight(), modeUserLocal));
            dManagerClient.getInterToIHMMain().tableJoinAccepted(dManagerClient.getListTablesLocal().getTable(idTableLocalUserJoined), modeUserLocal);
            } catch (TableException e) {
               e.printStackTrace();
        }

    }



    /**
     * Method to notify that the local user is not able to join the table
     *
     * @param idTableLocalUserJoined the id of the Table the User has not joined
     * @param modeUserLocal          an EnumerationTypeOfUser the mode which he has chosen to adopt spectator or player
     */
    public void tableJoinRefused(UUID idTableLocalUserJoined, EnumerationTypeOfUser modeUserLocal){
        Console.log(TAG +"tableJoinRefused()");
        dManagerClient.getInterToIHMMain().tableJoinRefused(dManagerClient.getListTablesLocal().getTable(idTableLocalUserJoined));
    }


    /**
     * Method to keep locally the list of UserLight connected to the server
     *
     * @param listUserLightConnectedOnServer ArrayList of  UserLight connected on the server
     */
    public void currentConnectedUser(ArrayList<UserLight> listUserLightConnectedOnServer) {
        Console.log(TAG +"currentConnectedUser()");
        dManagerClient.getListUsersLightLocal().setUserList(listUserLightConnectedOnServer);
        dManagerClient.getInterToIHMMain().onlineUsers(listUserLightConnectedOnServer);

    }

    /**
     * Method to keep locally the list of Table currently on the Server
     *
     * @param listOfTableListOnServer ArrayList of Table from the server
     */
    public void currentTables(ArrayList<Table> listOfTableListOnServer) {
        Console.log(TAG +"currentTables()");
        dManagerClient.getListTablesLocal().setListTable(listOfTableListOnServer);
        dManagerClient.getInterToIHMMain().currentTables(listOfTableListOnServer);
    }

    /**
     * Method to get the UserLight of the local user
     *
     * @return UserLight that corresponds to the local user
     */
    public UserLight getUserLightLocal() {
        return dManagerClient.getUserLocal().getUserLight();
    }

    /**
     * Method to receive playersHand which contain the cards
     * If there is only one PlayerHand it corresponds either to the card of the local User or to the card of the field (if the UserLight is null)
     * If there are more than one PlayerHand then it corresponds to the cards of the other player
     * @param playerHandsOfTheGame ArrayList<PlayerHand> of the Game
     */
    public void stockCards(ArrayList<PlayerHand> playerHandsOfTheGame) {
        Console.log(TAG +"stockCards()");
        //we send first cards at the beginning of the hand from the local user and  we show other cards later
        if(playerHandsOfTheGame.size()==1)
        {
            if(playerHandsOfTheGame.get(0).getPlayer()==null)
                dManagerClient.getInterToIHMTable().notifyCommonCards(playerHandsOfTheGame.get(0).getListCardsHand()); //card of the field
            else
                dManagerClient.getInterToIHMTable().notifyPlayersCards(playerHandsOfTheGame); //card of the local player
        }
        else {
            dManagerClient.getInterToIHMTable().notifyPlayersCards(playerHandsOfTheGame);//card of other player
        }
    }

    /**
     * Method to receive a message send by a remote UserLight to display it to the local user
     *
     * @param messageSendByRemoteUser MessageChat received by the local User
     */
    public void transmitMessage(MessageChat messageSendByRemoteUser) {
        Console.log(TAG + "transmitMessage()");
        dManagerClient.getInterToIHMTable().notifyNewChatMessage(messageSendByRemoteUser);
    }

    /**
     * Method that allow the local User to retrieve the profile of a remote User
     *
     * @param profileReturnedByTheServer User of a remote User which contain the profile of the user
     */
    public void remoteUserProfile(User profileReturnedByTheServer){
        Console.log(TAG + "remoteUserProfile()");
        dManagerClient.getInterToIHMMain().profileRemoteUserFromServer(profileReturnedByTheServer);
    }

    /**
     * Method to add new statistics to the local user
     *
     * @param statsLocalUser stats that need to be added into the user stats data
     */
    public void updateStats(Stats statsLocalUser) {
        dManagerClient.getUserLocal().setStatsUser(statsLocalUser);
        //notifying the player that its Stats has changed
        dManagerClient.getInterToIHMMain().userStatsUpdated(dManagerClient.getUserLocal().getUserLight(),statsLocalUser );
    }

    /**
     * Method to notify the player that the Game starts
     * @param idTable UUID of the Table the user is connected to
     */
    public void startGame(UUID idTable){
        Console.log(TAG + "startGame()");
        dManagerClient.getInterToIHMTable().notifyStartGame(dManagerClient.getListTablesLocal().getTable(idTable));
    }

    /**
     * Method that has to be called if the creator of a Table decide to start the Game but the server has rejected the request
     */
    public void tableCreatorRequestToStartGameRejected(){
        Console.log(TAG +"tableCreatorRequestToStartGameRejected()");
        dManagerClient.getInterToIHMTable().notifyRefuseStartGame();
    }

    /**
     * Method that has to be called if the creator of a Table decide to start the Game and the server has accepted the request
     */
    public void tableCreatorRequestToStartGameAccepted(){
        Console.log(TAG +"tableCreatorRequestToStartGameAccepted()");
        dManagerClient.getInterToIHMTable().notifySuccessStartGame();
    }

    /**
     * Method to ask the local Player for his money he is going to start with
     */
    public void askAmountMoney(){
        Console.log(TAG +"askAmountMoney()");
        dManagerClient.getInterToIHMTable().askMoneyAmount();
    }

    /**
     * Method that returns the confirmation from the server that its amount was valid
     * @param player User light of the local User (Player)
     * @param amount Integer amount asked by IHMTable, -1 if error >0 if valid
     */
    public void notifyMoneyAmountAnswerFromServer(UserLight player, Integer amount){
        Console.log(TAG +"notifyMoneyAmountAnswerFromServer()");
        dManagerClient.getInterToIHMTable().notifyMoneyAmountAnswer(player, amount);
    }

    /**
     * Method used to ask to each player if he accept the money of each player and if he is ready to start the game
     */
    public void askReadyGame(){
        Console.log(TAG +"askReadyGame()");
        dManagerClient.getInterToIHMTable().askReadyGame();
    }

    /**
     * Method to propose for the local user the list of Action that he can do, and use this list to fill the Action
     * @param actionEmpty an emptyAction that the Player has to fill
     * @param listActionPossibleForUserLocal Array of EnumerationAction that are possible to do for this turn (not the object Turn)
     */
    public void askAction(Action actionEmpty, EnumerationAction[] listActionPossibleForUserLocal) {
        Console.log(TAG +"askAction()");
        dManagerClient.getInterToIHMTable().askAction(actionEmpty, listActionPossibleForUserLocal);
    }

    /**
     * Method to notify the local User of the Action of a Player
     * @param action Action that a user played
     */
    public void notifyAction(Action action) {
        Console.log(TAG +"notifyAction()");
        dManagerClient.getInterToIHMTable().notifyAction(action);
    }

    /**
     * Method to inform the user that a new Hand had started
     */
    public void informNewHand(){
        Console.log(TAG +"informNewHand()");
        dManagerClient.getInterToIHMTable().notifyStartHand();
    }

    /**
     * Method to inform the user that a new turn had started
     */
    public void informNewTurn(){
        Console.log(TAG +"informNewTurn()");
        dManagerClient.getInterToIHMTable().notifyStartTour();
    }

    /**
     * Method to inform the local user that the Turn ended with the Pot at the end of Turn
     * @param potForThisTurn Integer containing the amount of money at the end of the Turn in the pot
     */
    public void informEndTurn(Integer potForThisTurn){
        Console.log(TAG +"informEndTurn()");
        dManagerClient.getInterToIHMTable().notifyEndTour(potForThisTurn);
    }

    /**
     * Method to inform the local user that the Hand ended with a list of Seat, their amount of money has changed depending on how won
     * @param listSeat ArrayList of Seat with their latest amount of money
     * @param listOfPlayer ArrayList<PlayerHand>
     */
    public void informEndHand(ArrayList<Seat> listSeat,ArrayList<PlayerHand> listOfPlayer) {
        Console.log(TAG +"informEndHand()");
        dManagerClient.getInterToIHMTable().notifyEndHand(listSeat,listOfPlayer);
    }

    /**
     * Method to save a game
     * @param tableThatContainGameToSave : table on which the game was played
     */
    public void saveLogGame(Table tableThatContainGameToSave) {
        Console.log(TAG +"saveLogGame()");
        TableList listOfAllTableSaved = (TableList) Serialization.deserializationObject(Serialization.dirLocalSavedFiles + dManagerClient.getUserLocal().getLogin()+Serialization.pathSavedGame);

        if (listOfAllTableSaved==null){
            listOfAllTableSaved = new TableList();
        }

        listOfAllTableSaved.getListTable().add(tableThatContainGameToSave);
        Serialization.serializationObject(listOfAllTableSaved,Serialization.dirLocalSavedFiles + dManagerClient.getUserLocal().getLogin() + Serialization.pathSavedGame);
        //TODO inform table or main for saved game?

    }

    /**
     * Method to call when the Game the user is playing on is stopped
     */
    public void stopGame(){
        dManagerClient.getInterToIHMTable().stopGame();
    }

    /**
     * Method to notify the local User of the answer of remotePlayer concerning the AskReady
     * @param player UserLight of the remote User who voted
     * @param answer Boolean true if the player is Ready, false if not
     */
    public void infosReadyAnswerFromOtherPlayer(UserLight player, Boolean answer){
        Console.log(TAG +"answerReadyGame()");
        dManagerClient.getInterToIHMTable().notifyReadyGameAnswer(player,answer);
    }

    /**
     * Method to notify that a Table was destroyed on the server
     * @param idTableDestroyed  UUID of the Table which is destroyed
     */
    public void deleteTable(UUID idTableDestroyed){
        Table t = dManagerClient.getListTablesLocal().getTable(idTableDestroyed);
        try {
            dManagerClient.getListTablesLocal().deleteTable(t);
        } catch (TableException e) {
            e.printStackTrace();
        }
        dManagerClient.getInterToIHMMain().notifyDeletedTable(t);
    }

    /**
     * Method to notify the local User that the connection to the server was accepted
     */
    public void acceptLogin() {
        dManagerClient.getInterToIHMMain().notifyLoginAccepted();
    }

    /**
     * Method to notify the local User that the connection to the server was refused
     */
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

    /**
     * Method to notify spectators that cards have been distributed among players
     */
    public void notifyCardsReceived() {
        Console.log(TAG +"notifyCardsReceived()");
        dManagerClient.getInterToIHMTable().notifySpectatorsCards();

    }
}
