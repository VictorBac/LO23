package fr.utc.lo23.client.data;

import fr.utc.lo23.client.data.exceptions.*;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.exceptions.network.FullTableException;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.ProfileNotFoundOnServerException;

import java.net.InetAddress;
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
        User userLocal = (User) Serialization.deserializationObject(login);
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
            dManagerClient.getInterToCom().requestLoginServer(userLogin);
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
        Serialization.serializationObject(userLocal, login);
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
                dManagerClient.getUserLocal().getLogin()+Serialization.pathSavedGame);
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
    @SuppressWarnings("unchecked")
    public List<Server> getServersList() {
        ArrayList<Server> listServers = dManagerClient.getListServers();
        if (listServers != null) {
            return listServers;
        }
        else {

            listServers = (ArrayList<Server>) Serialization.deserializationObject(
                    dManagerClient.getUserLocal().getLogin() + Serialization.pathServerList);
            dManagerClient.setListServers(listServers);
            return listServers;
        }
    }

    /**
     * Add server
     * @param ip
     * @param port
     */
    @SuppressWarnings("unchecked")
    public void addServer(InetAddress ip, String port) {
        ArrayList<Server> listServers = dManagerClient.getListServers();
        if (listServers == null) {
            listServers = (ArrayList<Server>) Serialization.deserializationObject(
                    dManagerClient.getUserLocal().getLogin() + Serialization.pathServerList);
        }
        listServers.add(new Server(ip, port));
        dManagerClient.setListServers(listServers);
    }

    /**
     * remove server from server list
     * @param server server to remove
     */
    @SuppressWarnings("unchecked")
    public void removeServer(Server server) {
        ArrayList<Server> listServers = dManagerClient.getListServers();
        if (listServers == null) {
            listServers = (ArrayList<Server>) Serialization.deserializationObject(
                    dManagerClient.getUserLocal().getLogin() + Serialization.pathServerList);
        }
        listServers.remove(server);
        dManagerClient.setListServers(listServers);
    }

    /**
     * get local user profile
     * @return local user
     */
    public User getLocalUserProfile() {
        return dManagerClient.getUserLocal();
    }

}
