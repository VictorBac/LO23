package fr.utc.lo23.client.ihm_table;

import fr.utc.lo23.client.data.InterfaceFromIHMTable;
import fr.utc.lo23.client.ihm_table.controllers.FormController;
import fr.utc.lo23.client.ihm_table.controllers.TableController;
import javafx.scene.layout.Pane;

/**
 * Created by thibault on 03/11/2015.
 */
public class IHMTable {

    private TableToMainListener tableToMainListener;
    private TableToDataListener tableToDataListener;
    private InterfaceFromIHMTable dataInterface;
    //private FuckingInterfaceFromIHMmain qu'ils ont pas codé l'objet qui implémente l'interface
    private TableController tableController = null;
    private FormController formController = null;

    public TableToMainListener getTableToMainListener() {
        return tableToMainListener;
    }

    private void setTableToMainListener(TableToMainListener tableToMainListener) {
        this.tableToMainListener = tableToMainListener;
    }

    public TableToDataListener getTableToDataListener() {
        return tableToDataListener;
    }

    private void setTableToDataListener(TableToDataListener tableToDataListener) {
        this.tableToDataListener = tableToDataListener;
    }

    public InterfaceFromIHMTable getDataInterface() {
        return dataInterface;
    }

    public void setDataInterface(InterfaceFromIHMTable dataInterface) {
        this.dataInterface = dataInterface;
    }

    public TableController getTableController() {
        return tableController;
    }

    public void setTableController(TableController tableController) {
        this.tableController = tableController;
    }

    public FormController getFormController() {
        return formController;
    }

    public void setFormController(FormController formController) {
        this.formController = formController;
    }

    //Constructeur
    public IHMTable(){
        setTableToDataListener(new TableToDataListener(this));
        setTableToMainListener(new TableToMainListener(this));
    }

}
