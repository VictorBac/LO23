package fr.utc.lo23.client.ihm_main.interfaces;

import fr.utc.lo23.common.data.Table;

/**
 * Created by jbmartin on 20/10/15.
 */
public interface InterfaceMainToTable {
    void notifyNewTable(Table t);
    void quitGame();
    void TableJoinedNotify(Table t);
}
