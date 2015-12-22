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
import java.util.UUID;


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
     * Notifies other players from the table of a player action
     * @param ArrayList<UserLight> tablePlayers
     * @param Action act
     * @throws NetworkFailureException
     */
    public void notifyOtherPlayerAction(ArrayList<UserLight> tablePlayers, Action act) throws NetworkFailureException;

    /**
     *Notifies that a new Table has been created
     *It is sent to all players within the Users connected
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
     * Sends a message to all players in a game
     * notifying that the Round begins
     * @param ArrayList<UserLight> aPlayers
     */
    public void newRound(ArrayList<UserLight> aPlayers);

    /**
     * Sends a message to all players in a game
     * notifying that the Turn begins
     * @param ArrayList<UserLight> aPlayers
     */
    public void newTurn(ArrayList<UserLight> aPlayers);

    /**
     * Sends a message to all players in a game
     * notifying the end of the Round
     * @param ArrayList<UserLight> aPlayers
     */
    public void endRound(ArrayList<UserLight> aPlayers, ArrayList<Seat> aSeat, ArrayList<PlayerHand> apl);

    /**
     * Sends a message to all players in a game
     * notifying the end of the Turn
     * @param ArrayList<UserLight> aPlayers
     */
    public void endTurn(ArrayList<UserLight> aPlayers, Integer pot);

    /**
     * Sends a message to all players in a table
     * asking them if they are ready
     * @param ArrayList<UserLight> aPlayers
     */
    public void askIfReady(ArrayList<UserLight> aPlayers);

    /**
     * Transmit a validated action to others players in the game
     * @param a
     * @param aPlayers
     * @param user
     */
    public void transmitAction(Action a,ArrayList<UserLight> aPlayers,UserLight user);
    /**
     * Asks a player which will be his next action
     * @param Action a
     * @param EnumerationAction enAct
     */
    public void askActionToPLayer(ArrayList<UserLight> aPlayers, Action a, EnumerationAction[] enAct);


    /**
     * Asks a player which will be his next action
     * @param ArrayList<UserLight> aPlayers
     * @param ArrayList<PlayerHand> ph
     */
    public void sendCards(ArrayList<UserLight> aPlayers,ArrayList<PlayerHand> ph) throws NetworkFailureException;



    }