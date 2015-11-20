package fr.utc.lo23.common.network;

import fr.utc.lo23.client.data.InterfaceDataFromCom;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.server.network.threads.PokerServer;

import java.io.ObjectOutputStream;

/**
 * Message to be sent to every users connected to notify them
 * That there is a new player
 * Created by rbonneau on 18/11/2015.
 */
public class NotifyNewPlayerMessage extends Message {

    private User newUser;

    public NotifyNewPlayerMessage(User u) {
        newUser = u;
    }

    @Override
    public void process() {

    }

    @Override
    public void process(PokerServer myServ, ObjectOutputStream out) {

    }

    @Override
    public void process(InterfaceDataFromCom dataInterface) {
        dataInterface.remoteUserConnected(newUser);
    }
}
