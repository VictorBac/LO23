package fr.utc.lo23.client.data;

import fr.utc.lo23.client.ihm_table.ITableToDataListener;
import fr.utc.lo23.client.network.InterfaceClient;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.ExistingUserException;

import java.util.ArrayList;

/**
 * Created by Mar on 24/10/2015.
 */
public class InterfaceFromCom implements InterfaceDataFromCom{

    private DataManagerClient dManagerClient;


    public InterfaceFromCom(DataManagerClient dManagerClient) {

        this.dManagerClient = dManagerClient;
    }

    public void updateStats(Stats statsLocalUser) {

    }

    public void remoteUserConnected(UserLight userLightDistant) {
        try {//TODO handle exception and test
            dManagerClient.getListUsers().addUser(userLightDistant);
            dManagerClient.getInterToIHMMain().remoteUserConnected(userLightDistant);
        } catch (ExistingUserException e) {
            e.printStackTrace();
        }
    }

    public void remoteUserDisonnected(UserLight userLightDistant) {

    }

    public void notifyNewTable(Table tableCreatedOnServer) {

    }

    public void userJoinedTable() {

    }

    public void addPlayer(UserLight userLightDistant) {

    }

    public void transmitLeaveGame(UserLight userLightDistant) {

    }

    public UserLightList getPlayerList() {
        return null;
    }

    public void tableJoinAccepted(Table tableLocalUserJoined, String modeUserLocal) {

    }

    public void currentConnectedUser(ArrayList<UserLight> listUserLightConnectedOnServer) {
        //TODO test
        dManagerClient.getListUsers().setUserList(listUserLightConnectedOnServer);
        dManagerClient.getInterToIHMMain().onlineUsers(listUserLightConnectedOnServer);

    }

    public void currentTables(TableList tableListOnServer) {

    }

    public UserLight getUserLightLocal() {
        return null;
    }

    public void stockCards(PlayerHand playerHandUserLocal) {

    }

    public void askAction(ArrayList<Action> listActionPossibleForUserLocal) {

    }

    public void notifyAction(Action action, UserLight userLight) {

    }

    public void informEndTurn(ArrayList<UserLight> listWinner, ArrayList<Integer> listGain) {

    }

    public void saveLogGame(Table table) {

    }
}
