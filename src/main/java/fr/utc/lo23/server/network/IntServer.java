/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.utc.lo23.server.network;

import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

import java.util.ArrayList;

/**
 *
 * @author Jean-Côme
 */
public class IntServer implements InterfaceServer {

    @Override
    public void sendTableList(ArrayList<Table> tableList) throws NetworkFailureException {

    }

    @Override
    public void sendPlayers(ArrayList<UserLight> userList) throws NetworkFailureException {

    }

    @Override
    public void sendLogGame() throws NetworkFailureException {

    }

    @Override
    public void notifyNewPlayer(UserLight userDistant) throws NetworkFailureException {

    }

    @Override
    public void notifyAction(Action act) throws NetworkFailureException {

    }

    @Override
    public void notifyNewTable(Table newTable) throws NetworkFailureException {

    }

    @Override
    public void notifyDisconnection(UserLight distantUser) throws NetworkFailureException {

    }

    @Override
    public void sendChatPacket() throws NetworkFailureException {

    }
}
