package lo23poker.IHMMain.interfaces;

/**
 * Created by jbmartin on 20/10/15.
 */
public interface InterfaceTable {
    void notifyNewTable(Table t);
    void quitGame();
    void TableJoinedNotify(Table t);
}
