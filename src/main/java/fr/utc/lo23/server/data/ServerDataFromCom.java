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

    private UserLightList connectedUsers;
    private TableList tables;

    public ServerDataFromCom(){

    }

    /**
     * Notifies a new user connection and creates a userlight
     * @param connectingUser
     * @return the new userlight created
     * @throws ExistingUserException
     */
    public UserLight userConnection(User connectingUser) throws ExistingUserException {
        connectedUsers.addUser(connectingUser);
        UserLight connectedUserLight = new UserLight(connectingUser);
        return connectedUserLight;
    }

    /**
     *
     * @return  the list of currently connected users
     */
    public ArrayList<UserLight> getConnectedUsers() {
        return connectedUsers.getListUserLights();
    }

    /**
     * @return the list of current tables
     */
    public ArrayList<Table> getTableList() {
        return tables.getListTable();
    }

    /**
     * creates a new table
     * @param maker
     * @param newTb
     */
    public void createTable(UserLight maker, Table newTb) {
        tables.newTable(newTb);
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

        for (Table cur : tables.getListTable())
        {
            if (cur.getIdTable().equals(idTable))
                cur.startGame(cur.getCurrentGame());
                return cur.getCurrentGame();
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
        if (connectedUsers.getListUserLights().contains(deletedUsr))
            connectedUsers.getListUserLights().remove(deletedUsr);
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
