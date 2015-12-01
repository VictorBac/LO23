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
     * method to create a new table
     * @param table
     */
    public void tableToCreate(Table table);


    /**
     * method to save the game
     */
    public void saveGame();


    /**
     * method to send a chat message
     */
    public void sendMessage(MessageChat message);

    /**
     * method to play a game
     * @param idTable : table on which starting the game
     */
    public void playGame(UUID idTable);


    /**
     * confirmation of reception of a card
     */
    public void confirmationCardReceived();

    /**
     * method to reply an action
     * @param action : action to reply
     */
    public void replyAction(Action action);

    /**
     * ask aconfirmation reception of an action
     * @param action : action to send
     */
    public void confirmationActionReceived(Action action);

    /**
     * confirm the end of turn
     */
    public void confirmationEndTurn();


    /**
     * Transmit the "leave game" request
     */
    public void transmitRequest();


    /**
     * Return the local user
     * @return
     */
    public UserLight getUser();


    public void quitGame();
}
