package fr.utc.lo23.client.ihm_main.interfaces;

import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import fr.utc.lo23.client.ihm_main.controllers.ViewAutreProfilController;
import fr.utc.lo23.common.data.*;
import javafx.application.Platform;

import java.util.List;

/**
 * Created by leclercvictor on 24/11/2015.
 */
    public class InterData implements InterfaceMainToData {

    private IHMMainClientManager managerMain;

    public InterData(IHMMainClientManager mngMain) {
        managerMain = mngMain;
    }

    /**
     * ajoute un nouveau joueur dans la liste des joueurs connectés
     * @param remoteUser
     */
    @Override
    public void remoteUserConnected(UserLight remoteUser) {
        managerMain.addConnectedUser(remoteUser);
    }

    @Override
    public void remoteUserProfile(UserLight remoteUser, Stats st) {

    }

    @Override
    public void remoteUserDisconnected(UserLight remoteUser) {
       managerMain.removeConnectedUser(remoteUser);
    }

    @Override
    public void contactNotificationEvent(UserLight remoteUser) {

    }


    /**
     * affiche la liste des joueurs connectés
     * @param userList
     */
    @Override
    public void onlineUsers(List<UserLight> userList) {
        managerMain.setConnectedUsers(userList);
    }

    @Override
    public void notifyNewTable(Table t) {
        managerMain.addTable(t);
    }

    @Override
    public void notifyDeletedTable(Table t) {
        managerMain.removeTable(t);
    }

    @Override
    public void returnHome() {
        Platform.runLater(() -> managerMain.getControllerMain().getMainWindowController().backFromGame());
    }

    /**
     * affiche la liste des tables
     * @param currentTables
     */
    @Override
    public void currentTables(List<Table> currentTables) {
        managerMain.setTables(currentTables);
    }

    @Override
    public void userJoinedTableRemote(Table t, UserLight user, EnumerationTypeOfUser type) {
        Platform.runLater(() -> managerMain.updateTable(t));
    }

    @Override
    public void userLeftTableRemote(Table tableTheUserhaveleft, UserLight userLightLeavingGame, EnumerationTypeOfUser typeOfUserWhoLeftTable) {
        Platform.runLater(() -> managerMain.updateTable(tableTheUserhaveleft));
    }

    @Override
    public void userLeftTableLocal(Table tableTheUserhaveleft, UserLight userLightLeavingGame, EnumerationTypeOfUser typeOfUserWhoLeftTable) {
        Platform.runLater(() -> managerMain.getControllerMain().getMainWindowController().userLeftTableLocal(userLightLeavingGame, typeOfUserWhoLeftTable));
    }

    @Override
    public void userStatsUpdated(UserLight user, Stats st) {

    }

    @Override
    public void tableJoinAccepted(Table t, EnumerationTypeOfUser e) {
        Platform.runLater(() -> managerMain.getControllerMain().getMainWindowController().joinAcceptedTable(t, e));
    }

    @Override
    public void tableJoinRefused(Table t) {
        Platform.runLater(() -> managerMain.getControllerMain().getMainWindowController().joinRefusedTable());
    }

    @Override
    public void profileRemoteUserFromServer(User profileReturnedByTheServer) {
        Platform.runLater(() -> {
            ViewAutreProfilController controller = managerMain.getControllerMain().getViewAutreProfilWindowController();
            if (controller != null) {
                controller.setFullData(profileReturnedByTheServer);
            }
        });
    }

    @Override
    public void tableCreated(Table tableCreatedOnServer) {
        Platform.runLater(() ->
            managerMain.getControllerMain().getMainWindowController().joinCreatedTable(tableCreatedOnServer));
    }

    @Override
    public void userJoinedTableLocal(UserLight userWhoJoinTheTable, EnumerationTypeOfUser typeOfUserWhoJoinTable) {
        Platform.runLater(() -> managerMain.getControllerMain().getMainWindowController()
                .userJoinTableLocal(userWhoJoinTheTable, typeOfUserWhoJoinTable));
    }

    @Override
    public void notifyLoginAccepted() {
        Platform.runLater(() -> managerMain.getControllerMain().userLoggedIn());
    }

    @Override
    public void notifyLoginRefused() {
        Platform.runLater(() -> managerMain.getControllerMain().getConnectionWindowController().loginRefused());
    }

    @Override
    public void updateUserRemote(UserLight newProfileRemoteUser) {
        Platform.runLater(() -> managerMain.updateConnectedUser(newProfileRemoteUser));
    }
}
