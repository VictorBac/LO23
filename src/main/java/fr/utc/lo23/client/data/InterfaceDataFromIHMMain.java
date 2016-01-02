package fr.utc.lo23.client.data;

import fr.utc.lo23.client.data.exceptions.LoginNotFoundException;
import fr.utc.lo23.client.data.exceptions.UserAlreadyExistsException;
import fr.utc.lo23.client.data.exceptions.WrongPasswordException;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.exceptions.network.FullTableException;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.ProfileNotFoundOnServerException;
import java.util.List;
import java.util.UUID;

/**
 * This is the interface which will be used by the IHM Main module on the client's side
 * Created by Jianghan on 20-10-15.
 */
public interface InterfaceDataFromIHMMain {

    /**
     * Connexion with login and password, call com interface
     * @param login login of the user
     * @param password password
     * @param ip ip address
     * @param port port number
     * @throws Exception
     */
    void logUser(String login, String password, String ip, Integer port) throws Exception;

    /**
     * Method to get the user's all information
     * @param userlight get more information of the user
     * @throws ProfileNotFoundOnServerException
     * @throws NetworkFailureException
     */
    void getUser(UserLight userlight) throws ProfileNotFoundOnServerException, NetworkFailureException;

    /**
     * Write userLocal into the local data file
     * @param userLocal save profile of the user to local data
     */
    void saveNewProfile(User userLocal) throws UserAlreadyExistsException;

    /**
     * Delete former user local and write the new userLocal into the local data file and notify the server
     * @param userLocal local user
     * @throws NetworkFailureException
     * @throws UserAlreadyExistsException
     */
    void updateProfile(User userLocal) throws NetworkFailureException, UserAlreadyExistsException;

    /**
     * join table with mode
     * @param tableId table UUID
     * @param mode mode number
     */
    void joinTableWithMode(UUID tableId, EnumerationTypeOfUser mode) throws FullTableException, NetworkFailureException;

    /**
     * Ask server to return UserLightList
     * @throws NetworkFailureException
     */
    void getPlayerList() throws NetworkFailureException;

    /**
     * Ask server to return TableList
     * @throws NetworkFailureException  Network Failure
     */
    void getTableList() throws NetworkFailureException;

    /**
     * Method to get saved game list
     * @return table list
     */
    TableList getSavedGamesList();

    /**
     * Ask server to play a game.
     * @param tableId tabe UUID
     */
    void playGame(UUID tableId) throws NetworkFailureException;

    /**
     * Method to ask to exit
     */
    void exitAsked() throws NetworkFailureException;

    /**
     * Methode to get servers list
     * @return server list
     */
    List<Server> getServersList();

    /**
     * Add server
     * @param ip ip address
     * @param port port number
     */
    void addServer(String ip, Integer port);

    /**
     * Method to remove server
     * @param server server
     */
    void removeServer(Server server);

    /**
     * Method to get local user
     * @return local user
     */
    User getLocalUserProfile();

    /**
     * send local user
     */
    void sendLocalUser() throws NetworkFailureException;

    /**
     * Import files
     * @param folderPath path of the folder where are the file
     */
    void importFiles(String folderPath);

    /**
     * Export files
     * @param folderPath path of the folder where have to be copy the files
     */
    void exportFiles(String folderPath);

    /**
     * Import a profile
     * @param filePath path of the profile to import
     * @throws UserAlreadyExistsException
     */
    void importProfileFile(String filePath) throws UserAlreadyExistsException;

    /**
     * Export a profile
     * @param filePath path to copy the profile in
     */
    void exportProfileFile(String filePath);


}
