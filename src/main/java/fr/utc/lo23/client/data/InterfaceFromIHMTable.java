package fr.utc.lo23.client.data;

import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.exceptions.network.IncorrectActionException;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.TooManyTablesException;

import java.util.UUID;

/**
 * This is the interface which will be used by the IHMTable module on the client's side
 * Created by Haroldcb on 21/10/2015.
 */
public class InterfaceFromIHMTable implements InterfaceDataFromIHMTable {

    private DataManagerClient dManagerClient;

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
     * forwards the request to communication module : askStopGame()
     */
    public void askStopGame() {
        try {
            dManagerClient.getInterToCom().askStopGame();
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        }
    }

    //TODO Com must implement vote()
    public void vote(boolean answer){
        //dManagerClient.getInterToCom().vote(answer);
    }

    //TODO
    //params : idGame, idTable?
    public void saveGame() {
        //TODO pour com cloner table sans liste games, ins�rer juste le bon game, retourner
    }


    /**
     * Send message to the server
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
     * ask to quit game
     */
    public void quitGame(){
        try {
            dManagerClient.getInterToCom().leaveTable(getUser(),dManagerClient.getTableLocal().getIdTable());
        } catch (NetworkFailureException e) {
            e.printStackTrace();
        }
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
