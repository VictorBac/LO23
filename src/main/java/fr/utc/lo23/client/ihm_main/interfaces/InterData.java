package fr.utc.lo23.client.ihm_main.interfaces;

import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import fr.utc.lo23.client.ihm_main.controllers.ViewAutreProfilController;
import fr.utc.lo23.common.data.*;
import javafx.application.Platform;

import java.util.List;

/**
 * Interface for DATA
 * Created by leclercvictor on 24/11/2015.
 */
    public class InterData implements InterfaceMainToData {

    /**
     * Instance of our IHMMainClientManager
     */
    private IHMMainClientManager managerMain;

    /**
     * Constructor of the interface for DATA
     * @param mngMain Instance of our IHMMainClientManager
     */
    public InterData(IHMMainClientManager mngMain) {
        managerMain = mngMain;
    }

    /**
     * Add a user in the List of connected users and refresh ListViews on the Main Window if needed
     * @param remoteUser UserLight of the connected user
     */
    @Override
    public void remoteUserConnected(UserLight remoteUser) {
        managerMain.addConnectedUser(remoteUser);
    }

    @Override
    public void remoteUserProfile(UserLight remoteUser, Stats st) {

    }

    /**
     * Remove a user from the List of connected users and refresh ListViews on the Main Window if needed
     * @param remoteUser UserLight of the disconnected user
     */
    @Override
    public void remoteUserDisconnected(UserLight remoteUser) {
       managerMain.removeConnectedUser(remoteUser);
    }

    @Override
    public void contactNotificationEvent(UserLight remoteUser) {

    }


    /**
     * Called after a successful connection. Set the List of connected users
     * @param userList List of UserLight of connected users on the server
     */
    @Override
    public void onlineUsers(List<UserLight> userList) {
        managerMain.setConnectedUsers(userList);
    }

    /**
     * Called after a new table created on the server to add it to the List of Table and refresh the ListViews
     * @param t Table created
     */
    @Override
    public void notifyNewTable(Table t) {
        managerMain.addTable(t);
    }

    /**
     * Called after a table deleted on the server to remove it from the List of Table and refresh the ListView
     * @param t Table deleted
     */
    @Override
    public void notifyDeletedTable(Table t) {
        managerMain.removeTable(t);
    }

    /**
     * Called after the local user successfully left the table to display the main window
     */
    @Override
    public void returnHome() {
        Platform.runLater(() -> managerMain.getControllerMain().getMainWindowController().backFromGame());
    }

    /**
     * Called after a successful connection to the server to set the List of current tables on the server
     * @param currentTables List of Table on the server
     */
    @Override
    public void currentTables(List<Table> currentTables) {
        managerMain.setTables(currentTables);
    }

    /**
     * Called after a distant user joined a remote table to refresh information about the table
     * @param t Table where the user joined
     * @param user UserLight of the user who joined the table
     * @param type EnumerationTypeOfUser of the user who joined the table
     */
    @Override
    public void userJoinedTableRemote(Table t, UserLight user, EnumerationTypeOfUser type) {
        Platform.runLater(() -> managerMain.updateTable(t));
    }

    /**
     * Called after a remote user left a remote table to refresh information about the table
     * @param tableTheUserhaveleft Table where the user left
     * @param userLightLeavingGame UserLight of the user who left the table
     * @param typeOfUserWhoLeftTable EnumerationTypeOfUser of the user who left the table
     */
    @Override
    public void userLeftTableRemote(Table tableTheUserhaveleft, UserLight userLightLeavingGame, EnumerationTypeOfUser typeOfUserWhoLeftTable) {
        Platform.runLater(() -> managerMain.updateTable(tableTheUserhaveleft));
    }

    /**
     * Called after a remote user left the local table to refresh ListViews on the table
     * @param tableTheUserhaveleft Table where the user left
     * @param userLightLeavingGame UserLight of the user who left the table
     * @param typeOfUserWhoLeftTable EnumerationTypeOfUser of the user who left the table
     */
    @Override
    public void userLeftTableLocal(Table tableTheUserhaveleft, UserLight userLightLeavingGame, EnumerationTypeOfUser typeOfUserWhoLeftTable) {
        Platform.runLater(() -> managerMain.getControllerMain().getMainWindowController().userLeftTableLocal(userLightLeavingGame, typeOfUserWhoLeftTable));
    }

    @Override
    public void userStatsUpdated(UserLight user, Stats st) {

    }

    /**
     * Called after a positive response from the server to join a table
     * @param t Table the local user wants to join
     * @param e EnumerationTypeOfUser the local user is
     */
    @Override
    public void tableJoinAccepted(Table t, EnumerationTypeOfUser e) {
        Platform.runLater(() -> managerMain.getControllerMain().getMainWindowController().joinAcceptedTable(t, e));
    }

    /**
     * Called after a negative response from the server to join a table; displays a popup
     * @param t Table the local user wants to join
     */
    @Override
    public void tableJoinRefused(Table t) {
        Platform.runLater(() -> managerMain.getControllerMain().getMainWindowController().joinRefusedTable());
    }

    /**
     * Called after a response on a full user profile of a remote user to display information
     * @param profileReturnedByTheServer User the local user wants information about
     */
    @Override
    public void profileRemoteUserFromServer(User profileReturnedByTheServer) {
        Platform.runLater(() -> {
            ViewAutreProfilController controller = managerMain.getControllerMain().getViewAutreProfilWindowController();
            if (controller != null) {
                controller.setFullData(profileReturnedByTheServer);
            }
        });
    }

    /**
     * Called after a successful response from the server for a creation of a table.
     * @param tableCreatedOnServer Table created on the server
     */
    @Override
    public void tableCreated(Table tableCreatedOnServer) {
        Platform.runLater(() ->
            managerMain.getControllerMain().getMainWindowController().joinCreatedTable(tableCreatedOnServer));
    }

    /**
     * Called after a remote user joined the local table
     * @param userWhoJoinTheTable UserLight of the user who joined the table
     * @param typeOfUserWhoJoinTable EnumerationTypeOfUser of the user who joined the table
     */
    @Override
    public void userJoinedTableLocal(UserLight userWhoJoinTheTable, EnumerationTypeOfUser typeOfUserWhoJoinTable) {
        Platform.runLater(() -> managerMain.getControllerMain().getMainWindowController()
                .userJoinTableLocal(userWhoJoinTheTable, typeOfUserWhoJoinTable));
    }

    /**
     * Called after a successful response for a connection action. Show the main window
     */
    @Override
    public void notifyLoginAccepted() {
        Platform.runLater(() -> managerMain.getControllerMain().userLoggedIn());
    }

    /**
     * Called after a negative response from the server for a connection action.
     * @param reason Reason why the local user has been refused
     */
    @Override
    public void notifyLoginRefused(String reason) {
        Platform.runLater(() -> managerMain.getControllerMain().getConnectionWindowController().loginRefused(reason));
    }

    /**
     * Called after a remote user changes information about him to refresh List Views
     * @param newProfileRemoteUser UserLight of the user
     */
    @Override
    public void updateUserRemote(UserLight newProfileRemoteUser) {
        Platform.runLater(() -> managerMain.updateConnectedUser(newProfileRemoteUser));
    }
}
