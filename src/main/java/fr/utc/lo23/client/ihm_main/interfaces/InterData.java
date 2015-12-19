package fr.utc.lo23.client.ihm_main.interfaces;

import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import fr.utc.lo23.client.ihm_main.controllers.MainWindowController;
import fr.utc.lo23.client.ihm_main.controllers.ViewAutreProfilController;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToData;
import fr.utc.lo23.common.data.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Iterator;
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
    public void updateView() {

    }

    @Override
    public void notifyNewTable(Table t) {
        managerMain.addTable(t);
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
    public void userJoinedTable(Table t, UserLight user, EnumerationTypeOfUser type) {
        
    }

    @Override
    public void userLeftTable(Table t, UserLight user, EnumerationTypeOfUser type) {

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
        Platform.runLater(() -> managerMain.getControllerMain().getMainWindowController().joinRefusedTable(t));
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
}
