package fr.utc.lo23.client.data;

import fr.utc.lo23.client.ihm_table.ITableToDataListener;
import fr.utc.lo23.client.network.InterfaceClient;
import fr.utc.lo23.common.data.*;

import java.util.ArrayList;

/**
 * Created by Mar on 24/10/2015.
 */
public class InterfaceFromCom implements InterfaceDataFromCom{
    private InterfaceFromIHMTable interFromIHMTable;
    private InterfaceFromIHMMain interFromIHMMain;

    private ITableToDataListener interToIHMTable;
    private InterfaceClient interToCom;
    //private InterfaceData interToIHMMain;

    private User userLocal;
    private UserLightList listUsers;
    private TableList listTables;

    public InterfaceFromCom(InterfaceFromIHMTable interFromIHMTable, InterfaceFromIHMMain interFromIHMMain, ITableToDataListener interToIHMTable, InterfaceClient interToCom, User userLocal, UserLightList listUsers, TableList listTables) {
        this.interFromIHMTable = interFromIHMTable;
        this.interFromIHMMain = interFromIHMMain;

        this.interToIHMTable = interToIHMTable;
        this.interToCom = interToCom;

        this.userLocal = userLocal;
        this.listUsers = listUsers;
        this.listTables = listTables;
    }

    public void updateStats(Stats statsLocalUser) {

    }

    public void remoteUserConnected(UserLight userLightDistant) {

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
}
