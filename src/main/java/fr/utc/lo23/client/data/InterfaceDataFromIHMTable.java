package fr.utc.lo23.client.data;

import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.Groupe;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;

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
     * method to create a new table
     * @param name
     * @param acceptSpectator
     * @param acceptChatSpectator
     * @param nbPlayerMax
     * @param nbPlayerMin
     * @param abandonAmiable
     * @param maxMise
     * @param timeforAction
     */
    public void tableToCreate(String name, boolean acceptSpectator, boolean acceptChatSpectator, int nbPlayerMax, int nbPlayerMin, boolean abandonAmiable, int maxMise, int timeforAction);

    /**
     * method to save the game
     */
    public void saveGame();



    /**
     * method to play a game
     * @param idTable : table on which starting the game
     */
//    public void playGame(UUID idTable);

    /**
     * method to get an user
     * @param user : user to get
     */
//   public void getUser(UserLight user);

    /**
     * confirmation of reception of a card
     */
//    public void confirmationCardRecieved();

    /**
     * method to replay an action
     * @param action : action à rejouer
     */
//    public void replayAction(Action action);

    /**
     * confirma reception of an action
     * @param action : action to send
     */
//    public void confirmationActionRecieved(Action action);

    /**
     * confirm the end of turn
     */
//    public void confirmationEndTurn();
}
