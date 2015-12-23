package fr.utc.lo23.client.ihm_main.interfaces;

import fr.utc.lo23.common.data.*;

import java.util.List;

/**
 * Created by jbmartin on 20/10/15.
 */
public interface InterfaceMainToData {
    void remoteUserConnected(UserLight remoteUser);
    void remoteUserProfile(UserLight remoteUser, Stats st);
    void remoteUserDisconnected(UserLight remoteUser);
    void contactNotificationEvent(UserLight remoteUser);
    void onlineUsers(List<UserLight> userList);
    void notifyNewTable(Table t);
    void notifyDeletedTable(Table t);
    void returnHome();
    void currentTables(List<Table> currentTables);
    void userJoinedTableRemote(Table t, UserLight user, EnumerationTypeOfUser type);
    void userLeftTableRemote(Table tableTheUserhaveleft,UserLight userLightLeavingGame, EnumerationTypeOfUser typeOfUserWhoLeftTable);
    void userLeftTableLocal(Table tableTheUserhaveleft,UserLight userLightLeavingGame, EnumerationTypeOfUser typeOfUserWhoLeftTable);
    void userStatsUpdated(UserLight user, Stats st);
    void tableJoinAccepted(Table t, EnumerationTypeOfUser type);
    void tableJoinRefused(Table t);
    void profileRemoteUserFromServer(User profileReturnedByTheServer);

    void tableCreated(Table tableCreatedOnServer);

    void userJoinedTableLocal(UserLight userWhoJoinTheTable, EnumerationTypeOfUser typeOfUserWhoJoinTable);

    void notifyLoginAccepted();
    void notifyLoginRefused();
}
