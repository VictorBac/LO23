package fr.utc.lo23.server.data;

import com.sun.xml.internal.ws.developer.*;
import fr.utc.lo23.common.data.*;
import fr.utc.lo23.common.data.exceptions.ExistingUserException;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Rémy on 03/11/2015.
 */
public class ServerDataFromCom implements InterfaceServerDataFromCom {

    private DataManagerServer myManager;

    public ServerDataFromCom(DataManagerServer manager ){
        this.myManager = manager;
    }

    /**
     * Notifies a new user connection and creates a user
     * @param connectingUser
     * @return the new user created
     * @throws ExistingUserException
     */
    public UserLight userConnection(User connectingUser) throws ExistingUserException {
        myManager.getUsers().addUser(connectingUser);
        return connectingUser.getUserLight();
    }

    /**
     * creates a list of userlights from the list of users
     * @return  the list of currently connected users
     */
    public ArrayList<UserLight> getConnectedUsers() {
        ArrayList<UserLight> created = new ArrayList<UserLight>();
        for (User current : myManager.getUsers().getList())
        {
            created.add(current.getUserLight());
        }
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
     * @param newTb
     */
    public void createTable(UserLight maker, Table newTb) {
        myManager.getTables().newTable(newTb);
    }

    /**
     *
     */
    public void updateTableList() {

    }

    /**
     * checks if a user can join a table
     * @param joiner
     * @param wantedTable
     * @param mode
     * @return
     */
    public boolean canJoinTableUser(UserLight joiner, Table wantedTable, String mode) {

        if (wantedTable.getListPlayers().getListUserLights().size() > wantedTable.getNbPlayerMax())
            return false;
        else if (mode.equals("Spectator") && !wantedTable.isAcceptSpectator())
            return false;
        else
            return true;
    }

    public void validateMessage(UserLight sender, MessageChat msgSent) {

    }

    public Game sendLogGame(UserLight player) {
        return null;
    }

    /**
     * starts a game with a given ID
     * @param idTable
     * @param player
     * @return
     */
    public Game startGame(UUID idTable, UserLight player) {
        Table toStart;
        for (Table cur : myManager.getTables().getListTable())
        {
            if (cur.getIdTable().equals(idTable))
                toStart = cur;
//                cur.startGame(cur.getCurrentGame());
//                return cur.getCurrentGame();
        }
        return null;
    }

    public void nextStepReplay() {

    }

    /**
     * deletes a player from the list connectedusers
     * Todo exception
     * @param deletedUsr
     */
    public void deletePlayer(UserLight deletedUsr) {
        for (User current : myManager.getUsers().getList())
        {
            if (current.getUserLight().equals(deletedUsr))
                myManager.getUsers().getList().remove(current);
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
}
