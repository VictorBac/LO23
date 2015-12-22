package fr.utc.lo23.client.network;

import fr.utc.lo23.common.data.*;
import fr.utc.lo23.exceptions.network.*;

import java.net.InetAddress;
import java.util.UUID;

/**
 * Interface provided to data, client-side
 * @author Jean-Côme D LO23
 */
public interface InterfaceClient {
    
    /**
     * Creation of a user by the client
     * @param u User's modified profile
     */
    public void sendProfile(User u) throws NetworkFailureException;

    /**
     * Asking the list of connected users
     * @throws NetworkFailureException
     */
    public void requestUserList()throws NetworkFailureException;

    /**
     * Asking a list of active tables
     * @throws NetworkFailureException
     */
    public void requestTableList()throws NetworkFailureException;

    /**
     *  Consult a profile
     * @param  u Profil we want to see
     */
    public void consultProfile(UserLight u) throws NetworkFailureException, ProfileNotFoundOnServerException;

    /**
     * Table  creation
     * @param  maker creator of the table
     * @param  t table he created
     * @throws NetworkFailureException
     * @throws TooManyTablesException
     */
    public void createTable(UserLight maker, Table t) throws NetworkFailureException, TooManyTablesException;
    
    /**
     *  Change made on your profile
     * @param userLocal UserLight local
     */
    public void updateProfile(User userLocal) throws NetworkFailureException;

    /**
     * The player sets him ready to launch the game
     * @param idTable Table launched
     * @param userInit User who is ready
     */
    public void LaunchGame(UUID idTable, UserLight userInit) throws NetworkFailureException;


    /**
     *  A player want to join the table
     * @param userLocal User to join
     * @param tableToJoin Table to join
     */
    public void joinTable(UserLight userLocal, UUID tableToJoin, EnumerationTypeOfUser mode) throws NetworkFailureException, FullTableException;
        
    /**
     * HeartBeat
     * Every 10 second, client thread send a message to the server to say he's alive.
     */
    public void sendHeartbeat() throws NetworkFailureException;

    /**
     * Send action of a player to the server
     * @param  act Action made
     * @param  IdTable Table on which it has been made
     * @throws NetworkFailureException
     * @throws IncorrectActionException
     */
    public void sendAction(Action act, UUID IdTable) throws NetworkFailureException, IncorrectActionException;
    
    /**
     * User leaves a table
     * @param userLocal user
     * @param IdTable table
     */
    public void leaveTable(UserLight userLocal, UUID IdTable, EnumerationTypeOfUser type) throws NetworkFailureException;
    
    /**
     *  Request the game to be logged
     * @param userLocal user who wants it logged
     */
    public void requestLogGame(UserLight userLocal) throws NetworkFailureException;

    /**
     * Launch a saved game to rewatch it
     * @throws NetworkFailureException
     * @throws IncorrectFileException
     */
    public void launchSavedGame() throws NetworkFailureException,IncorrectFileException;

    /**
     * Login to the server
     * @param u User
     * @param socketIp ip of the server
     * @param socketPort port of the server
     */
    public void requestLoginServer(User u, String socketIp, int socketPort);

    /**
     * Send a packet
     * @throws NetworkFailureException
     */
    public void sendPacket() throws NetworkFailureException;

    /**
     * Ask the server to join a game
     * @param userLocal User requesting to play
     * @param tableId Table to join
     * @throws NetworkFailureException
     */
    public void requestPlayGame(UserLight userLocal, UUID tableId) throws NetworkFailureException;

    /**
     * Ask the user's stat
     * @param userLocal User
     */
    public void requestUserStats(UserLight userLocal) throws NetworkFailureException;

    /**
     * Ask the game to be stopped
     * @throws NetworkFailureException
     */
    public void askStopGame() throws NetworkFailureException;

    /**
     * Notify the disconnection of a player
     * @param maker User
     * @throws NetworkFailureException
     */
    public void notifyDisconnection(User maker) throws NetworkFailureException;

    /**
     * Send a chat message
     * @param message chat message
     * @param tableID table on which it has been sent
     */
    public void sendMessage(MessageChat message,UUID tableID);

    /**
     * Confirm we have received the card
     * @param ul user who received the card
     */
    public void confirmationCardReceived(UserLight ul);

    /**
     * Request an action to be replayed
     * @param action
     * @param player
     */
    public void replayAction(Action action, UserLight player);

    public void confirmationEndTurn(UserLight ul);

    public void transmitRequestServer(UserLight player);

    /**
     * Notify if the player is ready to play
     * @param table Table id
     * @param player Player who responded
     * @param answer Answer
     */
    public void notifyAnswerAskReadyGame(UUID table, UserLight player, boolean answer);

    /**
     * Send the amount of money chosen by the player
     * @param table Table id
     * @param player User
     * @param money Amount chosen
     */
    public void sendMoneyAmount(UUID table, UserLight player, Integer money);

}
    

