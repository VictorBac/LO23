package fr.utc.lo23.client.ihm_main.interfaces;

import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToData;
import fr.utc.lo23.common.data.Stats;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by leclercvictor on 24/11/2015.
 */
public class InterData implements InterfaceMainToData {
    @Override
    public void remoteUserConnected(UserLight remoteUser) {

    }

    @Override
    public void remoteUserProfile(UserLight remoteUser, Stats st) {

    }

    @Override
    public void remoteUserDisconnected(UserLight remoteUser) {

    }

    @Override
    public void contactNotificationEvent(UserLight remoteUser) {

    }

    @Override
    public void onlineUsers(List<UserLight> userList) {
        List<String> received_list = new ArrayList<String>();

        Iterator<UserLight> iterator = userList.iterator();
        while (iterator.hasNext()) {
            received_list.add(iterator.next().getPseudo());
        }
        ObservableList<String> items = FXCollections.observableArrayList(received_list);

        listViewConnectedUsers.setItems(items);

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


    private IHMMainClientManager managerMain;

    public InterData(IHMMainClientManager mngMain) {
        managerMain = mngMain;
    }
}
