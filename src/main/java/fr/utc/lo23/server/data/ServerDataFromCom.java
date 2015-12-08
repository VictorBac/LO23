package fr.utc.lo23.server.data;

import com.sun.xml.internal.ws.developer.*;
import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.*;
import fr.utc.lo23.common.data.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Rémy on 03/11/2015.
 */
public class ServerDataFromCom implements InterfaceServerDataFromCom {

    private DataManagerServer myManager;
    private final String TAG = "ServerDataFromCom";

    public ServerDataFromCom(DataManagerServer manager) {
        this.myManager = manager;
    }

    /**
     * Notifies a new user connection and creates a user
     *
     * @param connectingUser
     * @return the new user created
     * @throws ExistingUserException
     */
    public UserLight userConnection(User connectingUser) throws ExistingUserException {
        myManager.getUsers().addUser(connectingUser);
        Console.log(TAG + "\tUser connected.");
        return connectingUser.getUserLight();
    }

    /**
     * creates a list of userlights from the list of users
     *
     * @return the list of currently connected users
     */
    public ArrayList<UserLight> getConnectedUsers() {
        ArrayList<UserLight> created = new ArrayList<UserLight>();
        for (User current : myManager.getUsers().getList()) {
            created.add(current.getUserLight());
        }
        Console.log(TAG + "\tListe des joueurs.");
        return created;
    }

    /**
     * @return the list of current tables
     */
    public ArrayList<Table> getTableList() {
        return myManager.getTables().getListTable();
    }

    /**
     * creates a new table
     * @param maker
     * @param newTb TODO : remove the maker attribute ?
     */
    public void createTable(UserLight maker, Table newTb) {

        myManager.getTables().newTable(newTb);
        Console.log(TAG + "\tTable created.");
    }

    /**
     * notifies the tables list has been modified to all observers
     */
    public void updateTableList() {
        myManager.getTables().getListTable().notifyAll();
    }

    /**
     * checks if a user can join a table
     * @param joiner the user trying to join
     * @param idTable the id of the table to join
     * @param mode the connection mode (player / spectator)
     * @return a boolean
     */
    public boolean canJoinTableUser(UserLight joiner, UUID idTable, EnumerationTypeOfUser mode) {
        boolean ok;
        Table wantedTable = getTableFromId(idTable);
        if (wantedTable.getListPlayers().getListUserLights().size() > wantedTable.getNbPlayerMax()) {
            ok = false;
            Console.log(TAG + "\tPlayer can't join, table full ");
        }
        else if (mode.equals(EnumerationTypeOfUser.SPECTATOR) && !wantedTable.isAcceptSpectator()) {
            ok = false;
            Console.log(TAG + "\tPlayer can't join, no spectators allowed");
        }
        else
            ok = true;
        Console.log(TAG + "\tPlayer can join : " + Boolean.toString(ok));
        return ok;
    }

    public void validateMessage(UserLight sender, MessageChat msgSent) {

    }

    public Game sendLogGame(UserLight player) {
        return null;
    }

    /**
     * starts a game with a given ID
     *
     * @param idTable the id of the table of the game
     * @param player the player launching the game
     * @return the created game
     */
    public Game startGame(UUID idTable, UserLight player) {
        Table toStart;
        for (Table cur : myManager.getTables().getListTable()) {
            if (cur.getIdTable().equals(idTable)) {
                toStart = cur;
                try {
                    cur.startGame(cur.getCurrentGame());
                    Console.log(TAG + "\tGame started.");
                    return cur.getCurrentGame();
                }
                catch(TableException e){
                    Console.log(TAG + "\tGame failed to start.");
                }


            }
        }
        return null;
    }

    public void nextStepReplay() {

    }

    /**
     * deletes a player from the list connectedusers
     * @param deletedUsr the user to delete
     */
    public void deletePlayer(UserLight deletedUsr) throws UserNotFoundException {
        boolean found = false;
        for (User current : myManager.getUsers().getList()) {
            if (current.getUserLight().equals(deletedUsr))
                found = true;
            myManager.getUsers().getList().remove(current);
            Console.log(TAG + "\tPlayer deleted.");
        }
        if (found == false)
            throw new fr.utc.lo23.common.data.exceptions.UserNotFoundException(deletedUsr.getIdUser());
    }

    public void confirmationCardReceived(UserLight player) {

    }

    public Action replyAction(Action playedAction, UserLight player) {
        return null;
    }

    public void confirmationActionReceived(UserLight sender) {

    }

    public void endTurnConfirmation(UserLight player) {

    }

    /**
     * updates a user info with a whole new User
     * @param newUser the new information
     */
    public void updateProfile(User newUser) {
        for (User cur : myManager.getUsers().getList()) {
            if (cur.equals(newUser)) {
                myManager.getUsers().getList().remove(cur);
                myManager.getUsers().getList().add(newUser);
            }
        }
    }

    /**
     * returns the user corresponding to a userlight in the Userlist of mymanager
     * @param core the userlight to compare to
     * @return the corresponding user, null if not found
     */
    public User getProfile(UserLight core) {
        for (User cur : myManager.getUsers().getList()) {
            if (cur.getUserLight().equals(core))
                return cur;
        }
        return null;
    }

    public User getUserById(UUID userId)
    {
        for (User cur : myManager.getUsers().getList()){
            if (cur.getUserLight().getIdUser().equals(userId))
                return cur;
        }
        return null;
    }

    /**
     * returns a list of all the players of the given table
     * @param tableID the UUID of the table to look for
     * @return an arrayList of Userlight with all the players
     */
    public ArrayList<UserLight> getPlayersByTable(UUID tableID){
        ArrayList<UserLight> players = new ArrayList<UserLight>();
        Table current = getTableFromId(tableID);
        return current.getListPlayers().getListUserLights();
    }

    private Table getTableFromId(UUID idTable){
        Table wantedTable = null;
        ArrayList<Table> tableList = getTableList();
        for (Table cur : tableList){
            if (cur.getIdTable().equals(idTable))
                return cur;
        }
        return null;
    }
}