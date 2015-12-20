package fr.utc.lo23.client.network;

import fr.utc.lo23.common.data.*;
import fr.utc.lo23.exceptions.network.*;

import java.util.UUID;

/**
 *
 * @author Jean-Côme D LO23
 */
public interface InterfaceClient {
    
    /**
     * Creation d'un utilisateur par le client
     * @param u 
     */
    public void sendProfile(User u) throws NetworkFailureException;

    /**
     * Demande d'envoi de la liste des users connectes
     * @throws NetworkFailureException
     */
    public void requestUserList()throws NetworkFailureException;

    /**
     * Demande d'envoi de la liste des tables
     * @throws NetworkFailureException
     */
    public void requestTableList()throws NetworkFailureException;

    /**
     * 
     * @param u
     */
    public void consultProfile(UserLight u) throws NetworkFailureException, ProfileNotFoundOnServerException;
    

    public void createTable(UserLight maker, Table t) throws NetworkFailureException, TooManyTablesException;
    
    /**
     *
     * @param userLocal
     */
    public void updateProfile(User userLocal) throws NetworkFailureException;
    
    /**
     *
     * @param userLocal
     */
    public void leaveRoom(UserLight userLocal) throws NetworkFailureException;

    /**
     * @param idTable
     * @param userInit
     */
    public void LaunchGame(UUID idTable, UserLight userInit) throws NetworkFailureException;


    /**
     *
     * @param userLocal
     * @param tableToJoin
     */
    public void joinTable(UserLight userLocal, UUID tableToJoin, EnumerationTypeOfUser mode) throws NetworkFailureException, FullTableException;
        
    /**
     *
     */
    public void sendHeartbeat() throws NetworkFailureException;
    
    /**
     * Envoie l'action d'un joueur au serveur
     * @param Action act
     */
    public void sendAction(Action act) throws NetworkFailureException, IncorrectActionException;
    
    /**
     *
     * @param userLocal
     * @param IdTable
     */
    public void leaveTable(UserLight userLocal, UUID IdTable) throws NetworkFailureException;
    
    /**
     *
     * @param userLocal
     */
    public void requestLogGame(UserLight userLocal) throws NetworkFailureException;

    public void launchSavedGame() throws NetworkFailureException,IncorrectFileException;

    public void requestLoginServer(User u, String socketIp, int socketPort);

    public void sendPacket() throws NetworkFailureException;

    public void requestPlayGame(UserLight userLocal, UUID tableId) throws NetworkFailureException;

    /**
     *
     * @param userLocal
     */
    public void requestUserStats(UserLight userLocal) throws NetworkFailureException;
    
    /**
     *
     */
    public void queryNextStepReplay() throws NetworkFailureException;
    
    /**
     *
     */
    public void askStopGame() throws NetworkFailureException;
    /**
     *
     */
    public void notifyDisconnection(User maker) throws NetworkFailureException;

    public void sendMessage(MessageChat message,UUID tableID);

    public void confirmationCardReceived(UserLight ul);

    public void replayAction(Action action, UserLight player);

    public void confirmationEndTurn(UserLight ul);

    public void transmitRequestServer(UserLight player);

    /**
     * Répond à la demande de askReadyGame
     * @param UserLigh player
     * @param boolean answer
     */
    public void notifyAnswerAskReadyGame(UUID table, UserLight player, boolean answer);
}
    

