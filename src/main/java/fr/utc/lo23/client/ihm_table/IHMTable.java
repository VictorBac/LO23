package fr.utc.lo23.client.ihm_table;

import fr.utc.lo23.client.data.InterfaceDataFromIHMTable;
import fr.utc.lo23.client.data.InterfaceFromIHMTable;
import fr.utc.lo23.client.ihm_main.interfaces.InterfaceMainToTable;
import fr.utc.lo23.client.ihm_table.controllers.FormController;
import fr.utc.lo23.client.ihm_table.controllers.TableController;
import javafx.scene.layout.Pane;

/**
 * Created by thibault on 03/11/2015.
 */
public class IHMTable {

    private TableToMainListener tableToMainListener;
    private TableToDataListener tableToDataListener;
    private InterfaceDataFromIHMTable dataInterface;
    private InterfaceMainToTable mainInterface;

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

    public InterfaceDataFromIHMTable getDataInterface() {
        return dataInterface;
    }

    public void setDataInterface(InterfaceDataFromIHMTable dataInterface) {
        this.dataInterface = dataInterface;
    }

    public InterfaceMainToTable getMainInterface() {
        return mainInterface;
    }

    public void setMainInterface(InterfaceMainToTable mainInterface) {
        this.mainInterface = mainInterface;
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
