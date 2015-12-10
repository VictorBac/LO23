package fr.utc.lo23.client.ihm_main.interfaces;

import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToData;
import fr.utc.lo23.common.data.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by leclercvictor on 24/11/2015.
 */
    public class InterData implements InterfaceMainToData {

    private IHMMainClientManager managerMain;

    public InterData(IHMMainClientManager mngMain) {
        managerMain = mngMain;
    }

    @Override
    public void remoteUserConnected(UserLight remoteUser) {
        managerMain.getControllerMain().getMainWindowController().addUser(remoteUser);
    }

    @Override
    public void remoteUserProfile(UserLight remoteUser, Stats st) {

    }

    @Override
    public void remoteUserDisconnected(UserLight remoteUser) {
        managerMain.getControllerMain().getMainWindowController().removeUser(remoteUser);
    }

    @Override
    public void contactNotificationEvent(UserLight remoteUser) {

    }

    @Override
    public void onlineUsers(List<UserLight> userList) {
        /*
        List<String> received_list = new ArrayList<String>();

        Iterator<UserLight> iterator = userList.iterator();
        while (iterator.hasNext()) {
            received_list.add(iterator.next().getPseudo());
        }
        ObservableList<String> items = FXCollections.observableArrayList(received_list);

        IHMMainClientManager.getControllerMain().getMainWindowController().getListViewConnectedUsers().setItems(items);*/
        managerMain.getControllerMain().getMainWindowController().addUsers(userList);

    }

    @Override
    public void updateView() {

    }

    @Override
    public void notifyNewTable(Table t) {

    }

    @Override
    public void returnHome() {

    }

    @Override
    public void currentTables(List<Table> currentTables) {
        managerMain.getControllerMain().getMainWindowController().addTables(currentTables);
    }

    @Override
    public void userJoinedTable(Table t, UserLight user, EnumerationTypeOfUser type) {
        
    }

    @Override
    public void userLeftTable(Table t, UserLight user, EnumerationTypeOfUser type) {

    }

    @Override
    public void userStatsUpdated(UserLight user, Stats st) {

    }

    @Override
    public void tableJoinRefused(Table t) {

    }

    @Override
    public void profileRemoteUserFromServer(User profileReturnedByTheServer) {
        
    }


}
