package fr.utc.lo23.client.data;

import fr.utc.lo23.client.data.exceptions.*;
import fr.utc.lo23.common.data.*;


/**
 * Created by Jianghan on 01-11-15.
 */
public class InterfaceFromIHMMain implements InterfaceDataFromIHMMain{

    // Constructor

    private DataManagerClient dManagerClient;

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
        User userLocal = (User) Serialization.deserializationObject(Serialization.pathUserLocal);
        // Get the login and password local.
        String loginLocal = userLocal.getUserLight().getPseudo();
        String passwordLocal = userLocal.getPwd();
        // Check correctness of login and password
        if (login.equals(loginLocal)) {
            throw new LoginNotFoundException(login);
        } else if (password.equals(passwordLocal)) {
            throw new WrongPasswordException();
        } else {
            //TODO: wait com interface to be ready.
            //dManagerClient.getInterToCom().requestLoginServer(localUser);
        }
    }

    public void exitAsked() {

    }

    /**
     * Write userLocal into the local data file
     * @param userLocal
     */
    public void saveNewProfile(User userLocal) {

        Serialization.serializationObject(userLocal, Serialization.pathUserLocal);
    }

    public void joinTableWithMode(Table table, String mode) {

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

    public void getUser(UserLight userlight) {

    }
}
