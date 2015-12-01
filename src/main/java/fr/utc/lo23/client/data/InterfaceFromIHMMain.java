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




    // Interface functions.

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

    public void exitAsked() {

    }

    /**
     * Write userLocal into the local data file
     * @param userLocal
     */
    public void saveNewProfile(User userLocal) {
        String login = userLocal.getUserLight().getPseudo();
        Serialization.serializationObject(userLocal, login);
    }

    public void joinTableWithMode(UUID tableId, EnumerationTypeOfUser mode) {
        // TODO wait network interface
        //dManagerClient.getInterToCom().joinTable(userLogin.getUserLight(), tableId, mode);
    }

    public void tableJoinAccepted(Table table, String mode) {

    }

    public UserLightList getPlayerList() {
        return null;
    }

    public TableList getTableList() {
        return null;
    }

    public TableList getSavedGamesList() {
        return null;
    }

    public void playGame(int idTable) {

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
}
