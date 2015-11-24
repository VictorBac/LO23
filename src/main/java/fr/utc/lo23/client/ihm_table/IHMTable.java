package fr.utc.lo23.client.ihm_table;

import fr.utc.lo23.client.data.InterfaceFromIHMTable;

/**
 * Created by thibault on 03/11/2015.
 */
public class IHMTable {

    private TableToMainListener tableToMainListener;
    private TableToDataListener tableToDataListener;
    private InterfaceFromIHMTable dataInterface;

    public IHMTable(){
        setTableToDataListener(new TableToDataListener(this));
        setTableToMainListener(new TableToMainListener(this));
    }

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
}
