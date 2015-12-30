package fr.utc.lo23.client.data;

import fr.utc.lo23.client.data.exceptions.LoginNotFoundException;
import fr.utc.lo23.client.data.exceptions.WrongPasswordException;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.exceptions.network.FullTableException;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.ProfileNotFoundOnServerException;

import java.net.InetAddress;
import java.net.URI;
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
    void saveNewProfile(User userLocal);

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
     * Get local saved game list.
     * @return table list
     */
    TableList getSavedGamesList();

    /**
     * Ask server to play a game.
     * @param tableId tabe UUID
     */
    void playGame(UUID tableId) throws NetworkFailureException;

    /**
     * Ask server for disconnection
     */
    void exitAsked() throws NetworkFailureException;

    /**
     * get servers list
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
     * remove server from server list
     * @param server server to remove
     */
    void removeServer(Server server);

    /**
     * get local user profile
     * @return local user
     */
    User getLocalUserProfile();

    /**
     * send local user
     * @throws NetworkFailureException
     */
    void sendLocalUser() throws NetworkFailureException;

    /**
     * import local data
     * @param folderPath folder path
     */
    void importFiles(String folderPath);

    /**
     * export local data
     * @param folderPath folder path
     */
    void exportFiles(String folderPath);

    /**
     * import the user profile
     * @param filePath file path
     */
    void importProfileFile(String filePath);

    /**
     * export the user profile
     * @param filePath file path
     */
    void exportProfileFile(String filePath);


}
