package lo23poker.IHMMain.interfaces;

/**
 * Created by jbmartin on 20/10/15.
 */
public interface InterfaceCom {
    void waitForStart();
    void notifyNewTable(Table t);
    void returnHome();
}
