package fr.utc.lo23.server.data;

import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.*;

import java.util.ArrayList;
import java.util.UUID;

public interface InterfaceServerDataFromCom {

    /**
     * Notifies a new user connection and creates a user
     * @param connectingUser
     * @return the new user created
     * @throws ExistingUserException
     */
    UserLight userConnection(User connectingUser) throws ExistingUserException;

    /**
     * creates a list of userlights from the list of users
     * @return the list of currently connected users
     */
    ArrayList<UserLight> getConnectedUsers();

    /**
     * @return the list of current tables
     */
    ArrayList<Table> getTableList();

    /**
     * creates a new table
     * @param maker
     * @param newTb TODO : remove the maker attribute ?
     */
    void createTable(UserLight maker, Table newTb);

    /**
     * notifies the tables list has been modified to all observers
     */
    void updateTableList();

    /**
     * checks if a user can join a table
     * @param joiner the user trying to join
     * @param idTable the id of the table to join
     * @param mode the connection mode (player / spectator)
     * @return a boolean
     */
    boolean canJoinTableUser(UserLight joiner, UUID idTable, EnumerationTypeOfUser mode);

    void validateMessage(UserLight sender, MessageChat msgSent);

    Game sendLogGame(UserLight player);

    /**
     * Add game to table using current game
     * @param idTable
     * @param player
     * @return
     */
    Boolean addGameUsingCurrent(UUID idTable, UserLight player);

    /**
     * starts a game with a given ID
     * @param idTable the id of the table of the game
     * @param player the player launching the game
     * @return the created game
     */
    Boolean startGame(UUID idTable, UserLight player);

    void nextStepReplay();

    /**
     * deletes a player from the list connectedusers
     * @param deletedUsr the user to delete
     */
    void deletePlayer(UserLight deletedUsr) throws fr.utc.lo23.common.data.exceptions.UserNotFoundException;

    void replyAction(UUID table, Action playedAction);

    /**
     * returns the user corresponding to a userlight in the Userlist of mymanager
     * @param core the userlight to compare to
     * @return the corresponding user, null if not found
     */
    User getProfile(UserLight core);

    /**
     * updates a user info with a whole new User
     * @param newUser the new information
     */
    void updateProfile(User newUser);

    User getUserById(UUID idUser);

    ArrayList<UserLight> getPlayersByTable(UUID tableID);

//    public boolean canLaunchGame(UUID idGame);

    public void askMoneyMax(UUID idTable);

    public void askAction (Table table, UserLight player, EnumerationAction[] availableActions);

    public void updateStats(Game idGame);

    public ArrayList<Seat> endHand(Table idTable);

    /*
   * Permet à com de transmettre les start money des utilisateurs pour validation ou refus, et sauvegarde coté server si validation
   */
    boolean setMoneyPlayer(UUID idTable, UserLight user, Integer startAmount);

    /*
     * Permet à com de transmettre les ready answer des utilisateurs pour sauvegarde coté server
     */
    void setReadyAnswer(UUID idTable, UserLight user, Boolean answer);

    void addPlayerToTable(UUID idTable, UserLight player, EnumerationTypeOfUser mode);

    void removePlayerFromTable(UUID idTable, UserLight player, EnumerationTypeOfUser mode);

    void checkIfEverybodyIsReady(UUID idtable);

    Table getTableFromId(UUID idTable);
}
