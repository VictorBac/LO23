package fr.utc.lo23.client.data;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.exceptions.TableException;
import fr.utc.lo23.exceptions.network.IncorrectActionException;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.TooManyTablesException;

import java.util.UUID;

/**
 * This is the interface which will be used by the IHMTable module on the client's side
 * Created by Haroldcb on 21/10/2015.
 */
public class InterfaceFromIHMTable implements InterfaceDataFromIHMTable {
    private final String TAG ="InterfaceFromIHMTable - ";
    private DataManagerClient dManagerClient;

    /**
     * Constructor
     * @param dManagerClient DataManagerClient
     */
    public InterfaceFromIHMTable(DataManagerClient dManagerClient) {
        this.dManagerClient = dManagerClient;
    }


    /**
     * Table is created by IHM Table, which transmit it as parameter
     * This function transmit the table to COM to create it on the server.
     * @param table : Table to transmit
     */
    public void tableToCreate(Table table){
        try {
            //initialize local table
            dManagerClient.setTableLocal(table);
            dManagerClient.getInterToCom().createTable(getUser(),table);
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        } catch (TooManyTablesException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add new game to current table
     */
    public void addNewGameToTable() {
        dManagerClient.getInterToCom().addNewGameToTable(dManagerClient.getTableLocal().getIdTable(), dManagerClient.getUserLocal().getUserLight());
    }

    /**
     * Method to let the tables's creator ask the end of the game to other players
     * forwards the request to communication module : askStopGame()
     */
    public void askStopGame() {
        try {
            dManagerClient.getInterToCom().askStopGame();
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to vote if the game can stop
     * @param answer : true if accept to stop the Game
     */
    public void vote(boolean answer){
        //dManagerClient.getInterToCom().vote(answer);
        //TODO Com must implement vote()
    }

    /**
     *  Method to save the game
     * @param idTable UUID of the Table
     * @param idGameToSave UUID of the Game that the local User wants to save
     */
    public void saveGame(UUID idTable, UUID idGameToSave) {
        //TODO pour com cloner table with only the Game that matters in the list of Game
    }


    /**
     * Send chat message to the server
     * @param message : message to send
     * @param idTableLocale : table's id from which message is sent
     */
    public void sendMessage(MessageChat message, UUID idTableLocale){
        dManagerClient.getInterToCom().sendMessage(message, idTableLocale);
    }

    /**
     * ask to start the game
     * @param idTable : table on which starting the game
     */
    public void playGame(UUID idTable) {
        try {
            dManagerClient.getInterToCom().LaunchGame(idTable,dManagerClient.getUserLocal().getUserLight());
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        }
    }

    /**
     * send an action to server
     * @param action : action to reply
     */
    public void replyAction(Action action) {
        try {
            dManagerClient.getInterToCom().sendAction(action,dManagerClient.getTableLocal().getIdTable());
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        } catch (IncorrectActionException e) {
            e.printStackTrace();
        }
    }


    /**
     * transmit a request to server
     * @param player : player who sent the request
     */
    public void transmitRequest(UserLight player){
        dManagerClient.getInterToCom().transmitRequestServer(player);
    }

    /**
     * return the local user
     * @return : local user
     */
    public UserLight getUser(){
        return dManagerClient.getUserLocal().getUserLight();
    }


    /**
     * Method to ask to quit game
     */
    public void quitGame(){
        try {
            EnumerationTypeOfUser localUserType = findTypeOfUserIsLocalPlayer(getUser());
            if(localUserType!=null) //if error not
            {
                dManagerClient.getInterToCom().leaveTable(getUser(), dManagerClient.getTableLocal().getIdTable(), localUserType);
                if (localUserType.equals(EnumerationTypeOfUser.PLAYER))
                    try {
                        dManagerClient.getTableLocal().playerLeaveTable(getUser());
                    } catch (TableException e) {
                        e.printStackTrace();
                    }
                else
                    try {
                        dManagerClient.getTableLocal().spectatorLeaveTable(getUser());
                    } catch (TableException e) {
                        e.printStackTrace();
                    }
            }
            else
                Console.log(TAG + "localUser has no type");
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method to find the type of the user on the local Table
     * @param userToFindType UserLight which its type we have to find
     * @return EnumerationTypeOfUser of the user given in parameter on the local Table, return null if not existing
     */
    private EnumerationTypeOfUser findTypeOfUserIsLocalPlayer(UserLight userToFindType){
        EnumerationTypeOfUser typeOfLocalUser = null;
        try {
            if(dManagerClient.getTableLocal().getListPlayers().findUser(userToFindType.getIdUser())!=null)
                typeOfLocalUser = EnumerationTypeOfUser.PLAYER;
            else if(dManagerClient.getTableLocal().getListSpectators().findUser(userToFindType.getIdUser())!=null)
                typeOfLocalUser = EnumerationTypeOfUser.SPECTATOR;
        } catch (UserLightNotFoundException e) {
            typeOfLocalUser = null;
            e.printStackTrace();
        }
        return typeOfLocalUser;
    }

    /**
     * set the beginning amount of money
     * @param amount : amount to set
     */
    public void setStartAmount(int amount){
        dManagerClient.getInterToCom().sendMoneyAmount(dManagerClient.getTableLocal().getIdTable(),dManagerClient.getUserLocal().getUserLight(),amount);
    }

    /**
     * send to server that player is ready
     * @param status : player's status
     */
    public void isReady(boolean status) {
        dManagerClient.getInterToCom().notifyAnswerAskReadyGame(dManagerClient.getTableLocal().getIdTable(),getUser(),status);
    }
}
