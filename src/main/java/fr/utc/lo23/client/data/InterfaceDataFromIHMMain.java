package fr.utc.lo23.client.data;

import fr.utc.lo23.client.data.exceptions.LoginNotFoundException;
import fr.utc.lo23.client.data.exceptions.WrongPasswordException;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.exceptions.network.FullTableException;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.ProfileNotFoundOnServerException;
import java.util.UUID;

/**
 * This is the interface which will be used by the IHM Main module on the client's side
 * Created by Jianghan on 20-10-15.
 */
public interface InterfaceDataFromIHMMain {
    /**
     * Method to log in
     * @param login
     * @param password
     */
    void logUser(String login, String password) throws LoginNotFoundException, WrongPasswordException;

    /**
     * Method to get the user's all information
     * @param userlight
     */
    void getUser(UserLight userlight) throws ProfileNotFoundOnServerException, NetworkFailureException;

    /**
     * Method to save new profile
     * @param userLocal
     */
    void saveNewProfile(User userLocal);

    /**
     * Methode to join a table with mode
     * @param table
     * @param mode
     */
    void joinTableWithMode(UUID table, EnumerationTypeOfUser mode) throws FullTableException, NetworkFailureException;

    /**
     * Methode to pass on when accepted to join a table
     * @param table
     * @param mode
     */
    void tableJoinAccepted(Table table, String mode);

    /**
     * Method to get UserList online
     * @return User Light List
     */
    void getPlayerList() throws NetworkFailureException;

    /**
     * Method to get TableList online
     * @return Table List
     */
    void getTableList() throws NetworkFailureException;

    /**
     * Method to get saved game list
     * @return
     */
    TableList getSavedGamesList();

    /**
     * Method to play game on the table
     * @param tableId
     */
    void playGame(UUID tableId) throws NetworkFailureException;

    /**
     * Method to ask to exit
     */
    void exitAsked();
}
