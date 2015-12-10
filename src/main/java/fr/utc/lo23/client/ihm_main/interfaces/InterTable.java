package fr.utc.lo23.client.ihm_main.interfaces;

import fr.utc.lo23.client.ihm_main.IHMMainClientManager;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToTable;
import fr.utc.lo23.common.data.Table;

/**
 * Created by leclercvictor on 24/11/2015.
 */
public class InterTable implements InterfaceMainToTable {


    public void notifyNewTable(Table t) {

    }

    public void quitGame() {

    }

    public void TableJoinedNotify(Table t) {

    }

    private IHMMainClientManager managerMain;

    public InterTable(IHMMainClientManager mngMain) {
        managerMain = mngMain;
    }
}
