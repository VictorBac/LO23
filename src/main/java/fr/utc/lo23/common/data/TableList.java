package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.TableException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class representing the list of available tables
 * Created by Haroldcb on 21/10/2015.
 */
public class TableList implements Serializable {
    /**
     * List of tables
     */
    private ArrayList<Table> listTable;


    /**
     * Constructor
     * @param listTable
     */
    public TableList(ArrayList<Table> listTable) {
        this.listTable = listTable;
    }

    /**
     * Default constructor
     */
    public TableList() {
        this.listTable = new ArrayList<Table>();
    }


    /**
     * add a new table in the list
     * @param table : table to add
     */
    public void newTable(Table table){
        this.listTable.add(table);
    }


    /**
     * Method to delete a table from the list
     * @param table
     */
    public void deleteTable(Table table) throws TableException{
        if(this.getListTable().contains(table)) {
            this.listTable.remove(table);
        }
        else
            throw new TableException("Impossible to delete the table from the list!");
    }


/********************* Getters and Setters *****************************/

    public ArrayList<Table> getListTable() {
        return listTable;
    }

    public void setListTable(ArrayList<Table> listTable) {
        this.listTable = listTable;
    }

}
