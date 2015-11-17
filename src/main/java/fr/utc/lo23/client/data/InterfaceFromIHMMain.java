package fr.utc.lo23.client.data;

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
    
    public void logUser(String login, String password) {
        // TODO Check existance of this login and check if password is correct Serialization
        User localUser = new User(login, password);
        //dManagerClient.getInterToCom().requestLoginServer(localUser);
    }

    public void exitAsked() {

    }


    public void saveNewProfile(User userLocal) {

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
