package fr.utc.lo23.client.ihm_main.interfaces;

import fr.utc.lo23.common.data.*;

import java.util.List;

/**
 * Created by jbmartin on 20/10/15.
 */

/**
 * Interface to communicate with Data.
 * All the methods are called by Data to inform IHM-Main of any change or to exchange data
 */
public interface InterfaceMainToData {
    /**
     * This method is called when a remote user is connected
     * @param remoteUser remote user connected
     */
    void remoteUserConnected(UserLight remoteUser);

    /**
     * This method is called to display a remote user profile
     * @param remoteUser remote user who we need to display profile
     * @param st statistic of the remote user
     */
    void remoteUserProfile(UserLight remoteUser, Stats st);

    /**
     * This method is called when a remote user disconnects
     * @param remoteUser remote user recently disconnected
     */
    void remoteUserDisconnected(UserLight remoteUser);

    /**
     * This method is called when the application receive a contact notification
     * from a user
     * @param remoteUser remote user that sends the contact notification
     */
    void contactNotificationEvent(UserLight remoteUser);

    /**
     * This method is called to transmit the online users
     * @param userList online users
     */
    void onlineUsers(List<UserLight> userList);

    /**
     * This method is called when a new table has been created
     * @param t recently created table
     */
    void notifyNewTable(Table t);

    /**
     * This method is called when a table has been deleted
     * @param t recently deleted table
     */
    void notifyDeletedTable(Table t);

    /**
     * This method is called when a game is done
     * to return to the main window
     */
    void returnHome();

    /**
     * This method is called to transmit the current (existing) tables
     * @param currentTables list of current table
     */
    void currentTables(List<Table> currentTables);

    /**
     * This method is called when a remote user joins a table
     * @param t table joined
     * @param user remote user that just joined the table
     * @param type type of user
     */
    void userJoinedTableRemote(Table t, UserLight user, EnumerationTypeOfUser type);

    /**
     * This method is called when a user joins a remote table
     * @param tableTheUserhaveleft table the user has just left
     * @param userLightLeavingGame user that left the game
     * @param typeOfUserWhoLeftTable type of the user
     */
    void userLeftTableRemote(Table tableTheUserhaveleft,UserLight userLightLeavingGame, EnumerationTypeOfUser typeOfUserWhoLeftTable);

    /**
     * This method is called when a user joins a local table
     * @param tableTheUserhaveleft table the user has just left
     * @param userLightLeavingGame user that left the game
     * @param typeOfUserWhoLeftTable type of the user
     */
    void userLeftTableLocal(Table tableTheUserhaveleft,UserLight userLightLeavingGame, EnumerationTypeOfUser typeOfUserWhoLeftTable);

    /**
     * This method is called when a remote user stats have been updated
     * @param user the user whom stats have been updated
     * @param st updated stats of the user
     */
    void userStatsUpdated(UserLight user, Stats st);

    /**
     * This method is called when a table join request is accepted
     * @param t table joined
     * @param type type of user
     */
    void tableJoinAccepted(Table t, EnumerationTypeOfUser type);

    /**
     * This method is called when a table join request is refused
     * @param t table wanted to join
     */
    void tableJoinRefused(Table t);

    /**
     * This method is called when a remote user's profile data have been retrieved
     * @param profileReturnedByTheServer user's profile data
     */
    void profileRemoteUserFromServer(User profileReturnedByTheServer);

    /**
     * This method is called when a table is successfully created on the server
     * @param tableCreatedOnServer table created
     */
    void tableCreated(Table tableCreatedOnServer);

    /**
     * This method is called when a user joins a local table
     * @param userWhoJoinTheTable user who joined the table
     * @param typeOfUserWhoJoinTable type of the user
     */
    void userJoinedTableLocal(UserLight userWhoJoinTheTable, EnumerationTypeOfUser typeOfUserWhoJoinTable);

    /**
     * This method is called when the user is successfully logged in
     */
    void notifyLoginAccepted();

    /**
     * This method is called when the user login is refused
     * @param reason reason of the login failure
     */
    void notifyLoginRefused(String reason);

    /**
     * This method is called when the profile of the user has been successfully updated on the server
     * @param newProfileRemoteUser
     */
    void updateUserRemote(UserLight newProfileRemoteUser);
}
