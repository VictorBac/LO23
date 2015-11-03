package lo23poker.IHMMain.interfaces;

import fr.utc.lo23.common.data.Table;

/**
 * Created by jbmartin on 20/10/15.
 */
public interface InterfaceCom {
    void waitForStart();
    void notifyNewTable(Table t);
    void returnHome();
}
