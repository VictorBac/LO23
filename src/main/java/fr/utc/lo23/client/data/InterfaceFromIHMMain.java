package fr.utc.lo23.client.data;

import fr.utc.lo23.client.data.exceptions.*;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.Params;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.exceptions.network.FullTableException;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.ProfileNotFoundOnServerException;

import java.net.InetAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Created by Jianghan on 01-11-15.
 */
public class InterfaceFromIHMMain implements InterfaceDataFromIHMMain{

    private DataManagerClient dManagerClient;

    public InterfaceFromIHMMain(DataManagerClient dManagerClient) {
        this.dManagerClient = dManagerClient;
    }


    /**
     * Connexion with login and password, call com interface
     * @param login
     * @param password
     */
    public void logUser(String login, String password) throws LoginNotFoundException, WrongPasswordException {
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

            //TODO: set socketIp and socketPort with IHM data
            String socketIp = Params.DEFAULT_SERVER_ADDRESS;
            int socketPort = Params.DEFAULT_SERVER_PORT;
            dManagerClient.getInterToCom().requestLoginServer(userLogin, socketIp, socketPort);
        }
    }

    /**
     * Method to get the user's all information
     * @param userlight
     * @throws ProfileNotFoundOnServerException
     * @throws NetworkFailureException
     */
    public void getUser(UserLight userlight) throws ProfileNotFoundOnServerException, NetworkFailureException {
        dManagerClient.getInterToCom().consultProfile(userlight);
    }

    /**
     * Write userLocal into the local data file
     * @param userLocal
     */
    public void saveNewProfile(User userLocal) {
        String login = userLocal.getLogin();
        dManagerClient.setUserLocal(userLocal);
        Serialization.serializationObject(userLocal, Serialization.dirLocalSavedFiles + login);
    }

    /**
     *
     * @param tableId
     * @param mode
     */
    public void joinTableWithMode(UUID tableId, EnumerationTypeOfUser mode) throws FullTableException, NetworkFailureException {
        dManagerClient.getInterToCom().joinTable(dManagerClient.getUserLocal().getUserLight(), tableId, mode);
    }

    /**
     * Ask server to return UserLightList
     * @return
     */
    public void getPlayerList() throws NetworkFailureException {
        dManagerClient.getInterToCom().requestUserList();
    }

    /**
     * Ask server to return TableList
     * @throws NetworkFailureException  Network Failure
     */
    public void getTableList() throws NetworkFailureException {
        dManagerClient.getInterToCom().requestTableList();
    }

    /**
     * Get local saved game list.
     * @return
     */
    public TableList getSavedGamesList() {
        return (TableList)Serialization.deserializationObject(
                Serialization.dirLocalSavedFiles + dManagerClient.getUserLocal().getLogin() + Serialization.pathSavedGame);
    }

    /**
     * Ask server to play a game.
     * @param tableId
     */
    public void playGame(UUID tableId) throws NetworkFailureException {
        dManagerClient.getInterToCom().requestPlayGame(dManagerClient.getUserLocal().getUserLight(), tableId);
    }

    /**
     * Ask server for disconnection
     */
    public void exitAsked() throws NetworkFailureException {
        dManagerClient.getInterToCom().notifyDisconnection(dManagerClient.getUserLocal());
    }

    /**
     * get servers list
     * @return server list
     */
    public List<Server> getServersList() {
        return dManagerClient.getListServers();
    }

    /**
     * Add server
     * @param ip
     * @param port
     */
    public void addServer(InetAddress ip, String port) {
        ArrayList<Server> listServers = dManagerClient.getListServers();
        listServers.add(new Server(ip, port));
        Serialization.serializationObject(listServers, Serialization.dirLocalSavedFiles + Serialization.pathServerList);
        dManagerClient.setListServers(listServers);
    }

    /**
     * remove server from server list
     * @param server server to remove
     */
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
    public User getLocalUserProfile() {
        return dManagerClient.getUserLocal();
    }

    /**
     * send local user
     */
    public void sendLocalUser() throws NetworkFailureException {
        dManagerClient.getInterToCom().sendProfile(dManagerClient.getUserLocal());
    }

    @Override
    public void importFiles(String folderPath) {
        try {
            Serialization.moveFiles(folderPath, Serialization.dirLocalSavedFiles);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportFiles(String folderPath) {
        try {
            Serialization.moveFiles(Serialization.dirLocalSavedFiles, folderPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void importProfileFile(String filePath) {
        User profile = (User) Serialization.deserializationObject(filePath);
        saveNewProfile(profile);
    }

    @Override
    public void exportProfileFile(String filePath) {
        Serialization.serializationObject(dManagerClient.getUserLocal(), filePath);
    }


}
