package fr.utc.lo23.server.data;

import fr.utc.lo23.client.network.main.Console;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.*;
import fr.utc.lo23.common.data.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by R�my on 03/11/2015.
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
        //Console.log(TAG + "\tUser connected.");
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
        //Console.log(TAG + "\tListe des joueurs.");
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

    public void addPlayerToTable(UUID idTable, UserLight player, EnumerationTypeOfUser mode) {
        Table toAdd = getTableFromId(idTable);
        try {
            if(mode == EnumerationTypeOfUser.PLAYER) {
                toAdd.playerJoinTable(player);
            } else {
                toAdd.spectatorJoinTable(player);
            }
            Console.log(TAG + "\tPlayer joined table");
        }
        catch(Exception e)
        {
            Console.log(TAG + "\tPlayer could't join table");
        }
    }

    public void validateMessage(UserLight sender, MessageChat msgSent) {

    }

    public Game sendLogGame(UserLight player) {
        return null;
    }

    /*
     * starts a game with a given ID
     *
     * @param idTable the id of the table of the game
     * @param player the player launching the game
     * @return the created game
     */
    public Boolean startGame(UUID idTable, UserLight player) {
        Table toStart = getTableFromId(idTable);
        if(toStart.getCurrentGame().startGame() && player==toStart.getCreator())
        {
            //TODO: ajouter une fonction pour envoyer les demandes de ready, depuis chez nous ou chez com ?
            return true;
        }
        else
        {
            return false;
        }
    }



    public void nextStepReplay() {

    }

    /**
     * deletes a player from the list connectedusers
     * @param deletedUsr the user to delete
     */
    public void deletePlayer(UserLight deletedUsr) throws UserNotFoundException {
        User userToDelete = null;
        for (User current : myManager.getUsers().getList()) {
            if (current.getUserLight().equals(deletedUsr)) {
                userToDelete = current;
                break;
            }
        }

        if (userToDelete == null) {
            throw new fr.utc.lo23.common.data.exceptions.UserNotFoundException(deletedUsr.getIdUser());
        }
        else {
            myManager.getUsers().getList().remove(userToDelete);
            //Console.log(TAG + "\tPlayer deleted.");
        }
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

    /**
     * looks for the table with the corresponding UUID
     * @param idTable the id to look for
     * @return the table if found, else null
     */
    private Table getTableFromId(UUID idTable){
        ArrayList<Table> tableList = getTableList();
        for (Table cur : tableList){
            if (cur.getIdTable().equals(idTable))
                return cur;
        }
        return null;
    }

    /*
     * Permet à com de transmettre les start money des utilisateurs pour validation ou refus, et sauvegarde coté server si validation
     */
    public boolean setMoneyPlayer(UUID idTable, UserLight user, Integer startAmount){
        if(myManager.getTables().getTable(idTable).getCurrentGame().getMaxStartMoney()<startAmount && startAmount<myManager.getTables().getTable(idTable).getCurrentGame().getBlind()*2) {
            return false;
        }
        else {
            myManager.getTables().getTable(idTable).getCurrentGame().createPlayerSeat(user, startAmount);
            //Si cet utilisateur est le dernier à répondre, lancer les demandes de ready
            if(myManager.getTables().getTable(idTable).getCurrentGame().getListSeatPlayerWithPeculeDepart().size()==myManager.getTables().getTable(idTable).getListPlayers().getListUserLights().size())
            {
                //TODO: Appeler la fonction d'envoi des demandes de ready, depuis com ou depuis chez nous ?
            }
            return true;
        }
    }

    /*
     * Permet à com de transmettre les ready answer des utilisateurs pour sauvegarde coté server
     */
    public void setReadyAnswer(UUID idTable, UserLight user, Boolean answer){
        myManager.getTables().getTable(idTable).getCurrentGame().getReadyUserAnswers().put(user,answer);
        //Si cet utilisateur est le dernier à répondre, lancer la partie
        if(myManager.getTables().getTable(idTable).getCurrentGame().getListSeatPlayerWithPeculeDepart().size()==myManager.getTables().getTable(idTable).getCurrentGame().getReadyUserAnswers().size())
        {
            myManager.getTables().getTable(idTable).getCurrentGame().startGame();
            myManager.getTables().getTable(idTable).playGame();
        }
    }

}