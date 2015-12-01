package fr.utc.lo23.client.data;

import fr.utc.lo23.client.data.exceptions.*;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.ProfileNotFoundOnServerException;
import java.util.UUID;


/**
 * Created by Jianghan on 01-11-15.
 */
public class InterfaceFromIHMMain implements InterfaceDataFromIHMMain{

    private DataManagerClient dManagerClient;
    private User userLogin;

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
        // Get the login and password local.
        String loginLocal = userLocal.getUserLight().getPseudo();
        String passwordLocal = userLocal.getPwd();
        // Check correctness of login and password
        if (login.equals(loginLocal)) {
            throw new LoginNotFoundException(login);
        } else if (password.equals(passwordLocal)) {
            throw new WrongPasswordException();
        } else {
            dManagerClient.getInterToCom().requestLoginServer(userLocal);
            userLogin = userLocal;
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
    public void joinTableWithMode(UUID tableId, EnumerationTypeOfUser mode) {
        // TODO wait network interface
        //dManagerClient.getInterToCom().joinTable(userLogin.getUserLight(), tableId, mode);
    }

    /**
     * TODO
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
        return null;
    }

    /**
     * Ask server to play a game.
     * @param tableId
     */
    public void playGame(UUID tableId) {
        // TODO wait network interface
        // dManagerClient.getInterToCom().requestPlayGame(userLogin.getUserLight(), tableId);
    }

    /**
     *
     */
    public void exitAsked() {
        // TODO wait network interface
        // dManagerClient.getInterToCom().askDisconnection(userLogin.getUserLight());
    }

}
