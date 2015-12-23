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
     * method to let the tables's creator ask the end of the game to other players
     */
    public void askStopGame();

    /**
     * Function to vote if the game can stop
     * @param answer : true if accept
     */
    public void vote(boolean answer);


     /**
     * Table is created by IHM Table, which transmit it as parameter
     * This function transmit the table to COM to create it on the server.
     * @param table : Table to transmit
     */
    public void tableToCreate(Table table);


    /**
     * method to save the game
     */
    public void saveGame();


    /**
     * method to send a chat message
     * @param message : message to send
     * @param idTableLocale : table's id
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

    public void quitGame();

    public void setStartAmount(int amount);

    public void isReady(boolean status);
}
