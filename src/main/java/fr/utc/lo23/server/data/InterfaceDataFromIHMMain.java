package fr.utc.lo23.server.data;

/**
 * Created by Jianghan on 15-10-20.
 */
public interface InterfaceDataFromIHMMain {
    void logUser(String login,String password):
    void exitAsked():
    void saveNewProfile(User userLocal):
    void joinTableWithMode(Table table, String mode);
    void tableJoinAcccepted (Table table, String mode);
    UserLightList getPlayerList();
    TableList getTableList();
    void getListeSavedGame();
    TableList getSavedGamesList();
    void playGame(int idTable);
    void getUser(UserLight userlight);
}
