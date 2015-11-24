package fr.utc.lo23.client.data;

import fr.utc.lo23.client.data.exceptions.LoginNotFoundException;
import fr.utc.lo23.client.data.exceptions.WrongPasswordException;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.ProfileNotFoundOnServerException;

/**
 * This is the interface which will be used by the IHM Main module on the client's side
 * Created by Jianghan on 20-10-15.
 */
public interface InterfaceDataFromIHMMain {
    /**
     * Methode to log in
     * @param login
     * @param password
     */
    void logUser(String login, String password) throws LoginNotFoundException, WrongPasswordException;

    /**
     * Methode to ask to exit
     */
    void exitAsked();

    /**
     * Methode to save new profile
     * @param userLocal
     */
    void saveNewProfile(User userLocal);

    /**
     * Methode to join a table with mode
     * @param table
     * @param mode
     */
    void joinTableWithMode(Table table, String mode);

    /**
     * Methode to pass on when accepted to join a table
     * @param table
     * @param mode
     */
    void tableJoinAccepted(Table table, String mode);

    /**
     * Methode to get UserList online
     * @return User Light List
     */
    UserLightList getPlayerList();

    /**
     * Methode to get TableList online
     * @return Table List
     */
    TableList getTableList();

    /**
     * Methode to get saved game list
     * @return
     */
    TableList getSavedGamesList();

    /**
     * Methode to play game on the table
     * @param idTable
     */
    void playGame(int idTable);

    /**
     * Methode to get the user's all information
     * @param userlight
     */
    void getUser(UserLight userlight) throws ProfileNotFoundOnServerException, NetworkFailureException;
}
