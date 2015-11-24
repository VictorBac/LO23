package fr.utc.lo23.client.network;

import fr.utc.lo23.common.data.Action;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.User;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.exceptions.network.*;

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
    public void RequestUserList()throws NetworkFailureException;

    /**
     * Demande d'envoi de la liste des tables
     * @throws NetworkFailureException
     */
    public void RequestTableList()throws NetworkFailureException;

    /**
     * 
     * @param u
     */
    public void consultProfile(UserLight u) throws NetworkFailureException, ProfileNotFoundOnServerException;
    

    public void createTable() throws NetworkFailureException, TooManyTablesException;
    
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
     *
     * @param userLocal
     * @param IdTable
     */
    public void joinTable(UserLight userLocal, int IdTable) throws NetworkFailureException, FullTableException;
        
    /**
     *
     */
    public void hearthBeat() throws NetworkFailureException;
    
    /**
     *
     * @param act
     * @param userLocal
     */
    public void sendAction(Action act,UserLight userLocal) throws NetworkFailureException, IncorrectActionException;
    
    /**
     *
     * @param userLocal
     * @param IdTable
     */
    public void leaveTable(UserLight userLocal, int IdTable) throws NetworkFailureException;
    
    /**
     *
     * @param userLocal
     */
    public void requestLogGame(UserLight userLocal) throws NetworkFailureException;

    public void launchSavedGame() throws NetworkFailureException,IncorrectFileException;

    public void requestLoginServer(User u);

    public void sendPacket() throws NetworkFailureException;
    
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
     * @param userLocal
     * @param activeTable
     */
    public void requestPlayGame(UserLight userLocal, Table activeTable) throws NetworkFailureException;
}
    

