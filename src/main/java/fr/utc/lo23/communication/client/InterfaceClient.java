/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.communication.client;

import java.util.ArrayList;
import lo23.communication.exception.*;

/**
 *
 * @author Jean-Côme D LO23
 */
public interface InterfaceServer {
    
    /**
     * Creation d'un utilisateur par le client
     * @param u 
     */
    public void sendProfile(User u) throws NetworkFailureException;
    
  
    /**
     * 
     * @param u
     */
    public void consultProfile(UserLight u) throws NetworkFailureException, ProfileNotFoundOnServerException;
    
    /**
     * 
     * @param tablToCreate
     * @param user 
     */
    public void createTable(
            //TODO: Pas passer de table mais les paramètres de la table
            TableParameters tablToCreate, UserLight user) throws NetworkFailureException, TooManyTablesException;
    
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
    
    /**
     *
     * @param logGame
     */
    public void launchSavedGame(Log logGame) throws NetworkFailureException,IncorrectFileException;
    
    /**
     *
     * @param chat
     */
    public void sensPacket(ChatPacket chat) throws NetworkFailureException;
    
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
    
    
