/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.utc.lo23.server.network;

import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

import java.util.ArrayList;



/**
 *
 * @author Jean-CÃ´me D LO23
 */
public interface InterfaceServer {

    /**
     * DEPRECATED, envoyé au passage dans AcceptLoginMessage
     * @param tableList
     */
    public void sendTableList(ArrayList<Table> tableList) throws NetworkFailureException;

    /**
     * DEPRECATED, envoyé au passage dans AcceptLoginMessage
     * @param userList
     */
    public void sendPlayers(ArrayList<UserLight> userList) throws NetworkFailureException;


    public void sendLogGame() throws NetworkFailureException;

    /**
     * DEPRECATED, envoyé au passage dans NotifyNewPlayerMessage
     * @param userDistant
     */
    public void notifyNewPlayer(UserLight userDistant) throws NetworkFailureException;

    /**
     *
     * @param act
     */
    public void notifyAction(Action act) throws NetworkFailureException;

    /**
     *
     * @param newTable
     */
    public void notifyNewTable(Table newTable) throws NetworkFailureException;

    /**
     *
     * @param distantUser
     */
    public void notifyDisconnection(User distantUser) throws NetworkFailureException;

    public void sendChatPacket() throws NetworkFailureException;

    /**
     * Send a message to start the table
     * @param tableToStart
     * @throws NetworkFailureException
     */
    public void startGame(Table tableToStart)throws NetworkFailureException;

}