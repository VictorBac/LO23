/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.utc.lo23.server.network;

import fr.utc.lo23.common.data.*;
import fr.utc.lo23.exceptions.network.NetworkFailureException;

import java.lang.reflect.Array;
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


    public void sendLogGame(Game LogGame) throws NetworkFailureException;

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

    /**
     * Envoie un message à tous les joueurs pour notifier
     * le début d'une nouvelle manche
     * @param ArrayList<UserLight> aPlayers
     */
    public void newRound(ArrayList<UserLight> aPlayers);

    public void newTurn(ArrayList<UserLight> aPlayers);

    public void endRound(ArrayList<UserLight> aPlayers);

    public void endTurn(ArrayList<UserLight> aPlayers);

}