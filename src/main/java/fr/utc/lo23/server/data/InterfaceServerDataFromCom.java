package fr.utc.lo23.server.data;

import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.*;

import java.util.ArrayList;
import java.util.UUID;

public interface InterfaceServerDataFromCom {

    /**
     * Notifies a new user connection and creates a UserLight
     * @param connectingUser User profile
     * @return the new UserLight connected
     * @throws ExistingUserException
     */
    UserLight userConnection(User connectingUser) throws ExistingUserException;

    /**
     * Get a list of UserLights from the list of users
     * @return ArrayList<UserLight> the list of currently connected users
     */
    ArrayList<UserLight> getConnectedUsers();

    /**
     * Get the list of current tables on the Server
     * @return ArrayList<Table> the list of current tables
     */
    ArrayList<Table> getTableList();

    /**
     * Creates a new table on the Server
     * @param maker UserLight of the Creator of the Table TODO : remove the maker attribute ?
     * @param newTb Table that is created
     */
    void createTable(UserLight maker, Table newTb);

    /**
     * Notifies that the tables list has been modified to all observers
     */
    void updateTableList();

    /**
     * checks if a user can join a table
     * @param joiner UserLight of the user who is trying to join the table
     * @param idTable the id of the table to join
     * @param mode the connection mode (player / spectator)
     * @return a boolean true if the user can join
     */
    boolean canJoinTableUser(UserLight joiner, UUID idTable, EnumerationTypeOfUser mode);


    /**
     * Method to validate chat message
     * @param idTable UUId id of the Table this message is associated to
     * @param msgSent Message that is send by player or a spectator
     */
    void saveMessageChat(UUID idTable, MessageChat msgSent);

    /**
     * Method to send the Table containing only the Game to be saved
     * @param player UserLight of the user who want to save the Game locally
     * @return Table containing only the Game that matter (the Game where the User is)
     */
    Table sendLogGame(UserLight player);

    /**
     * Add game to table using current game
     * @param idTable UUID of the Table on which the creator of the Table wants to start a new Game
     * @param player UserLight of the Creator of the Table
     * @return True if the creation of the Table was a success, false if not
     */
    Boolean addGameUsingCurrent(UUID idTable, UserLight player);

    /**
     * starts a game with a given UUID
     * @param idTable the id of the table of the game
     * @param player the player launching the game
     * @return the created game
     */
    Boolean startGame(UUID idTable, UserLight player);

    /**
     * Method used to ask for the next step of the Game that is replayed
     * @param idTable UUID idTable on which is replayed the Game
     * @param idGame UUID of the Game which is replayed
     * @param master UserLight of the spectator who has decided to replay the Game
     */
    void nextStepReplay(UUID idTable, UUID idGame, UserLight master);


    /**
     * Deletes a player from the list connected users
     * @param deletedUsr the user to delete
     * @throws fr.utc.lo23.common.data.exceptions.UserNotFoundException if the UserLight was not found in the list of connected users
     */
    void deletePlayer(UserLight deletedUsr) throws fr.utc.lo23.common.data.exceptions.UserNotFoundException;

    /**
     * Method that handles an Action that was played by a user
     * @param table UUID id of the Table on which the player has made his Action
     * @param playedAction Action that was played
     */
    void replyAction(UUID table, Action playedAction);

    /**
     * Get the User corresponding to a UserLight in the Userlist of mymanager
     * @param core the UserLight corresponding to the Profile that is searched
     * @return the corresponding User, null if not found
     */
    User getProfile(UserLight core);

    /**
     * Method to update a user info with a whole new User
     * @param newUser the new profile of the user
     */
    void updateProfile(User newUser);

    /**
     * Get a whole User which is connected on a server that corresponds to a specific UUID
     * @param userId UUID which is the id of the User
     * @return User containing the profile of the User, null if no User corresponds to the UUID
     */
    User getUserById(UUID userId);

    /**
     * Get the list of all the players of the given table
     * @param tableID the UUID of the table to look for
     * @return an ArrayList of UserLight with all the players
     */
    ArrayList<UserLight> getPlayersByTable(UUID tableID);

    /**
     * Method to update the stats of the players that have played a Game
     * @param idTable UUID id of the Table
     * @param idGame UUID id of the Game that will be used to updated the Stats of the Users
     */
    void updateStats(UUID idTable,UUID idGame);

    /**
     * Method to notify the players about the money a Player decide to start with.
     * It checks the amount if it is a valid amount then save it on the server else refuse it
     * Allow Network module to transmit the startmoney of the users for validation or rejection, and save it on the server side if ok
     * @param idTable UUID idTable
     * @param user UserLight of the player who has decided of its amount
     * @param startAmount Integer amount of money the player is going to start playing with
     * @return true if the amount is valid, else false
     */
    boolean setMoneyPlayer(UUID idTable, UserLight user, Integer startAmount);


    /**
     * Method to save the ready answer of the players on the server
     * @param idTable UUID id of the Table
     * @param user UserLight of a player who send its answer
     * @param answer Boolean answer of the player, true if he is ready to start the Game, else false
     */
    void setReadyAnswer(UUID idTable, UserLight user, Boolean answer);

    /**
     * Method to add a User to the Table. Throw an Exception if the User can't join the Table.
     * @param idTable UUID id of the Table the User want to join
     * @param userJoiningTable UserLight of a user that want to join the Table
     * @param mode EnumerationTypeOfUser the user can be a Spectator or a Player
     */
    void addPlayerToTable(UUID idTable, UserLight userJoiningTable, EnumerationTypeOfUser mode);

    /**
     * Method to remove a User from a Table.
     * @param idTable UUID id of the Table the User want to leave
     * @param userLeavingTable UserLight of a user that want to leave the Table
     * @param mode EnumerationTypeOfUser the user can be a Spectator or a Player
     */
    void removePlayerFromTable(UUID idTable, UserLight userLeavingTable, EnumerationTypeOfUser mode);

    /**
     * Method to check if all the player of a Table are ready to start the Game
     * @param idTable UUID id of a Table
     */
    void checkIfEverybodyIsReady(UUID idTable);

    /**
     * Get a Table corresponding to a specific UUID
     * @param idTable UUID id of the Table to look for
     * @return Table if found, else null
     */
    Table getTableFromId(UUID idTable);
}
