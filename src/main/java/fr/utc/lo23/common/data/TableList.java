package fr.utc.lo23.common.data;

import java.util.ArrayList;

/**
 * Classe représentant la liste des tables disponibles
 * Created by Haroldcb on 21/10/2015.
 */
public class TableList {
    /**
     * Liste des tables
     */
    private ArrayList<Table> listTable;

    /**
     * Constructeur
     * @param listTable
     */
    public TableList(ArrayList<Table> listTable) {
        this.listTable = listTable;
    }

    /**
     * Constructeur par défaut
     */
    public TableList() {
        this.listTable = new ArrayList<Table>();
    }

    public ArrayList<Table> getListTable() {
        return listTable;
    }

    public void setListTable(ArrayList<Table> listTable) {
        this.listTable = listTable;
    }

    /**
     * ajout d'une nouvelle table dans la liste
     * @param table : table à ajouter
     */
    public void newTable(Table table){
        this.listTable.add(table);
    }
}
