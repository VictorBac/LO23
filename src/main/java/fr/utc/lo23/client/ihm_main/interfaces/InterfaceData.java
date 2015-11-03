package lo23poker.IHMMain.interfaces;

import java.util.List;

/**
 * Created by jbmartin on 20/10/15.
 */
public interface InterfaceData {
    void remoteUserConnected(UserLight remoteUser);
    void remoteUserProfile(UserLight remoteUser, Stats st);
    void remoteUserDisconnected(UserLight remoteUser);
    void contactNotificationEvent(userLight remoteUser);
    void onlineUsers(List<UserLight> userList);
    void updateView();
}
