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
     * method to let the creator of the table to ask the end of the game to other players
     */
    public void askStopGame();

    /**
     * method to create a new table
     * @param table : table to create
     */
    public void tableToCreate(Table table);

    /**
     * method to play a game
     * @param idTable : table on which starting the game
     */
    public void playGame(UUID idTable);



    /**
     * method to save the game
     */
    public void saveGame();

    /**
     * method to get an user
     * @param user : user to get
     */
    public void getUser(UserLight user);

    /**
     * confirmation de reception d'une carte distribuée
     */
    public void confirmationCardRecieved();

    /**
     * méthode permettant de rejouer une action
     * @param action : action à rejouer
     */
    public void replayAction(Action action);

    /**
     * confirmation de reception d'une action effectuée
     * @param action : action envoyée
     */
    public void confirmationActionRecieved(Action action);

    /**
     * confirmation de fin de tour
     */
    public void confirmationEndTurn();
}
