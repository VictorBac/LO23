package fr.utc.lo23.client.ihm_main.controllers;

import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToData;
import fr.utc.lo23.common.data.Stats;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;

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
}
