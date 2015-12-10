package fr.utc.lo23.client.data;

import fr.utc.lo23.client.data.exceptions.*;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.exceptions.network.FullTableException;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.ProfileNotFoundOnServerException;
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
        //User userLocal = new User(login, password); //Create a User to test
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
        String login = userLocal.getUserLight().getPseudo();
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
     * table join accepted
     * @param table
     * @param mode
     */
    public void tableJoinAccepted(Table table, String mode) {
        // TODO wait network interface
        //dManagerClient.getInterToCom().tableJoinAccepted();
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
     * @return
     */
    public void getTableList() throws NetworkFailureException {
        dManagerClient.getInterToCom().requestTableList();
    }

    /**
     * TODO
     * @return
     */
    public TableList getSavedGamesList() {
        Serialization.deserializationObject(dManagerClient.getUserLocal().getLogin()+Serialization.pathSavedGame);
        return null;
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
    public void exitAsked() {
        // TODO wait network interface
        // dManagerClient.getInterToCom().askDisconnection(userLogin.getUserLight());
    }

    /**
     * getServersList TODO
     * @return server list
     */
    public List<Server> getServersList() {
        return null;
    }

    /**
     * Add server TODO
     * @param ip
     * @param port
     */
    public void addServer(String ip, String port) {

    }

    /**
     * remove server TODO
     * @param server server to remove
     */
    public void removeServer(Server server) {

    }

}
