package fr.utc.lo23.client.data;

import fr.utc.lo23.client.data.exceptions.*;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.exceptions.network.FullTableException;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.ProfileNotFoundOnServerException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Data Interface for IHM Main
 * Created by Jianghan on 01-11-15.
 */
public class InterfaceFromIHMMain implements InterfaceDataFromIHMMain{

    private DataManagerClient dManagerClient;

    /**
     * Constructor
     * @param dManagerClient data manager
     */
    public InterfaceFromIHMMain(DataManagerClient dManagerClient) {
        this.dManagerClient = dManagerClient;
    }


    /**
     * Connexion with login and password, call com interface
     * @param login login of the user
     * @param password password
     * @param ip ip address
     * @param port port number
     * @throws Exception
     */
    @Override
    public void logUser(String login, String password, String ip, Integer port) throws Exception {
        User userLocal = (User) Serialization.deserializationObject(Serialization.dirLocalSavedFiles + login);
        if (userLocal == null) {
            throw new LoginNotFoundException(login);
        }
        // Get the login and password local.
        String loginLocal = userLocal.getUserLight().getPseudo();
        String passwordLocal = userLocal.getPwd();
        // Check correctness of login and password
        if (!login.equals(loginLocal)) {
            throw new LoginNotFoundException(login);
        } else if (!password.equals(passwordLocal)) {
            throw new WrongPasswordException();
        } else {
            Console.log("loguser "+ userLocal.toString());
            //remove the psw and send userLocal to server
            dManagerClient.setUserLocal(userLocal);
            User userLogin = new User(userLocal);
            userLogin.setPwd(null);
            dManagerClient.getInterToCom().requestLoginServer(userLogin, ip, port);
        }
    }

    /**
     * Method to get the user's all information
     * @param userlight get more information of the user
     * @throws ProfileNotFoundOnServerException
     * @throws NetworkFailureException
     */
    @Override
    public void getUser(UserLight userlight) throws ProfileNotFoundOnServerException, NetworkFailureException {
        dManagerClient.getInterToCom().consultProfile(userlight);
    }

    /**
     * Write userLocal into the local data file
     * @param userLocal save profile of the user to local data
     */
    @Override
    public void saveNewProfile(User userLocal) {
        String login = userLocal.getLogin();
        dManagerClient.setUserLocal(userLocal);
        Serialization.serializationObject(userLocal, Serialization.dirLocalSavedFiles + login);
    }

    /**
     * join table with mode
     * @param tableId table UUID
     * @param mode mode number
     */
    @Override
    public void joinTableWithMode(UUID tableId, EnumerationTypeOfUser mode) throws FullTableException, NetworkFailureException {
        dManagerClient.getInterToCom().joinTable(dManagerClient.getUserLocal().getUserLight(), tableId, mode);
    }

    /**
     * Ask server to return UserLightList
     * @throws NetworkFailureException
     */
    @Override
    public void getPlayerList() throws NetworkFailureException {
        dManagerClient.getInterToCom().requestUserList();
    }

    /**
     * Ask server to return TableList
     * @throws NetworkFailureException  Network Failure
     */
    @Override
    public void getTableList() throws NetworkFailureException {
        dManagerClient.getInterToCom().requestTableList();
    }

    /**
     * Get local saved game list.
     * @return table list
     */
    @Override
    public TableList getSavedGamesList() {
        return (TableList)Serialization.deserializationObject(
                Serialization.dirLocalSavedFiles + dManagerClient.getUserLocal().getLogin() + Serialization.pathSavedGame);
    }

    /**
     * Ask server to play a game.
     * @param tableId tabe UUID
     */
    @Override
    public void playGame(UUID tableId) throws NetworkFailureException {
        dManagerClient.getInterToCom().requestPlayGame(dManagerClient.getUserLocal().getUserLight(), tableId);
    }

    /**
     * Ask server for disconnection
     */
    @Override
    public void exitAsked() throws NetworkFailureException {
        dManagerClient.getInterToCom().notifyDisconnection(dManagerClient.getUserLocal());
    }

    /**
     * get servers list
     * @return server list
     */
    @Override
    public List<Server> getServersList() {
        return dManagerClient.getListServers();
    }

    /**
     * Add server
     * @param ip ip address
     * @param port port number
     */
    @Override
    public void addServer(String ip, Integer port) {
        ArrayList<Server> listServers = dManagerClient.getListServers();
        listServers.add(new Server(ip, port));
        Serialization.serializationObject(listServers, Serialization.dirLocalSavedFiles + Serialization.pathServerList);
        dManagerClient.setListServers(listServers);
    }

    /**
     * remove server from server list
     * @param server server to remove
     */
    @Override
    public void removeServer(Server server) {
        ArrayList<Server> listServers = dManagerClient.getListServers();
        listServers.remove(server);
        Serialization.serializationObject(listServers, Serialization.dirLocalSavedFiles + Serialization.pathServerList);
        dManagerClient.setListServers(listServers);
    }

    /**
     * get local user profile
     * @return local user
     */
    @Override
    public User getLocalUserProfile() {
        return dManagerClient.getUserLocal();
    }

    /**
     * send local user
     * @throws NetworkFailureException
     */
    @Override
    public void sendLocalUser() throws NetworkFailureException {
        dManagerClient.getInterToCom().sendProfile(dManagerClient.getUserLocal());
    }

    /**
     * import local data
     * @param folderPath folder path
     */
    @Override
    public void importFiles(String folderPath) {
        try {
            Serialization.moveFiles(folderPath, Serialization.dirLocalSavedFiles);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * export local data
     * @param folderPath folder path
     */
    @Override
    public void exportFiles(String folderPath) {
        try {
            Serialization.moveFiles(Serialization.dirLocalSavedFiles, folderPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * import the user profile
     * @param filePath file path
     */
    @Override
    public void importProfileFile(String filePath) {
        User profile = (User) Serialization.deserializationObject(filePath);
        saveNewProfile(profile);
    }

    /**
     * export the user profile
     * @param filePath file path
     */
    @Override
    public void exportProfileFile(String filePath) {
        Serialization.serializationObject(dManagerClient.getUserLocal(), filePath);
    }


}
