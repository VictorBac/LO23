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
 * Interface provided to data server-side
 * @author Jean-Côme D LO23
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

    /**
     * Sends data's log to the client
     * @param LogGame logs
     * @throws NetworkFailureException
     */
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
     * Notifies the disconnection of another player
     * @param distantUser
     */
    public void notifyDisconnection(User distantUser) throws NetworkFailureException;

    /**
     * Sends a chat packet
     * @throws NetworkFailureException
     */
    public void sendChatPacket() throws NetworkFailureException;

    /**
     * Sends a message to start the table
     * @param tableToStart table
     * @throws NetworkFailureException
     */
    public void startGame(Table tableToStart)throws NetworkFailureException;

    /**
     * Sends a message to all players in a game
     * notifying that the Round begins
     * @param aPlayers all table's players
     */
    public void newRound(ArrayList<UserLight> aPlayers);

    /**
     * Sends a message to all players in a game
     * notifying that the Turn begins
     * @param aPlayers all table's players
     */
    public void newTurn(ArrayList<UserLight> aPlayers);

    /**
     * Sends a message to all players in a game
     * notifying the end of the Round
     * @param aPlayers all table's players
     */
    public void endRound(ArrayList<UserLight> aPlayers, ArrayList<Seat> aSeat);

    /**
     * Sends a message to all players in a game
     * notifying the end of the Turn
     * @param aPlayers all table's players
     *
     */
    public void endTurn(ArrayList<UserLight> aPlayers, Integer pot);

    /**
     * Sends a message to all players in a table
     * asking them if they are ready
     * @param aPlayers all table's players
     */
    public void askIfReady(ArrayList<UserLight> aPlayers);

    /**
     * Transmits a validated action to others players in the game
     * @param a action
     * @param aPlayers all table's players
     * @param user
     */
    public void transmitAction(Action a,ArrayList<UserLight> aPlayers,UserLight user);

    /**
     * Asks a player which will be his next action
     * @param a empty action to be filled
     * @param enAct enumation of possible actions
     */
    public void askActionToPLayer(ArrayList<UserLight> aPlayers, Action a, EnumerationAction[] enAct);


    /**
     * Sends the new cards to a player
     * @param aPlayers list of table's players
     * @param ph list of hands
     * @throws NetworkFailureException
     */
    public void sendCards(ArrayList<UserLight> aPlayers,ArrayList<PlayerHand> ph) throws NetworkFailureException;

    public void stop();

    public void sendLogGame() throws NetworkFailureException;

    }