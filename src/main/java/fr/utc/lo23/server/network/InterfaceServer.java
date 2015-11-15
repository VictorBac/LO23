/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.utc.lo23.server.network;

import java.util.ArrayList;

import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.exceptions.network.NetworkFailureException;
import fr.utc.lo23.exceptions.network.*;



/**
 *
 * @author Jean-Côme D LO23
 */
public interface InterfaceServer {
    
    /**
     *
     * @param tableList
     */
    public void sendTableList(ArrayList<Table> tableList) throws NetworkFailureException;

    /**
     *
     * @param userList
     */
    public void sendPlayers(ArrayList<UserLight> userList) throws NetworkFailureException;


    public void sendLogGame() throws NetworkFailureException;

    /**
     *
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
    public void notifyDisconnection(UserLight distantUser) throws NetworkFailureException;

    public void sendChatPacket() throws NetworkFailureException;
    

}
    
    
