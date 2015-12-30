package fr.utc.lo23.client.data;

import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.Table;

import java.util.UUID;

/**
 * Interface offered by the Data module to IHM Table
 * Created by Haroldcb on 20/10/2015.
 */
public interface InterfaceDataFromIHMTable {
    /**
     * Add new game to current table
     */
    public void addNewGameToTable();

    /**
     * Method to let the tables's creator ask the end of the game to other players
     */
    public void askStopGame();

    /**
     * Function to vote if the game can stop
     * @param answer : true if accept to stop the Game
     */
    public void vote(boolean answer);


     /**
     * Table is created by IHM Table, which transmit it as parameter
     * This function transmit the table to COM to create it on the server.
     * @param table : Table to transmit
     */
    public void tableToCreate(Table table);

    /**
     *  Method to save the game
     * @param idTable UUID of the Table
     * @param idGameToSave UUID of the Game that the local User wants to save
     */
    public void saveGame(UUID idTable, UUID idGameToSave);


    /**
     * Send chat message to the server
     * @param message : message to send
     * @param idTableLocale : table's id from which message is sent
     */
    public void sendMessage(MessageChat message, UUID idTableLocale);

    /**
     * method to play a game
     * @param idTable : table on which starting the game
     */
    public void playGame(UUID idTable);

    /**
     * method to reply an action
     * @param action : action to reply
     */
    public void replyAction(Action action);

    /**
     * Transmit the "leave game" request
     * @param player : player who send request
     */
    public void transmitRequest(UserLight player);

    /**
     * Return the local user
     * @return : local user
     */
    public UserLight getUser();

    /**
     * Method to ask to quit game
     */
    public void quitGame();

    /**
     * set the beginning amount of money
     * @param amount : amount to set
     */
    public void setStartAmount(int amount);

    /**
     * send to server that player is ready
     * @param status : player's status
     */
    public void isReady(boolean status);
}
