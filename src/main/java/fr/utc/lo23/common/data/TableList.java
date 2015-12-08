package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.TableException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

/**
 * Class representing the list of available tables
 * Created by Haroldcb on 21/10/2015.
 */
public class TableList implements Serializable {
    /**
     * List of tables
     */
    private ArrayList<Table> listTable;
    private static final long serialVersionUID = 1L;

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
     * add a new table to the list
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

    /**
     * Add a player/spectator in a table, if it's in the list
     * @param idTable : Table's id in which we want to add the player/spectator
     * @param newUser : User to add
     * @param typeOfUser : player or spectator
     * @return Table the User is in
     * @throws TableException
     */
    public Table addUserToTable(UUID idTable, UserLight newUser, EnumerationTypeOfUser typeOfUser) throws TableException {
        //get the index of the table  if it is in the list
        int ind = getIDTable(idTable);
        //The table is in the table list
        if(ind != -1) {
            //ask to add a player
            if (typeOfUser == EnumerationTypeOfUser.PLAYER) {
                //Call method in Table
                this.listTable.get(ind).playerJoinTable(newUser);
            }
            //ask to add a spectator
            else if (typeOfUser == EnumerationTypeOfUser.SPECTATOR){
                //Call method in Table
                this.listTable.get(ind).spectatorJoinTable(newUser);
            }
            return this.listTable.get(ind);
        }
        else throw new TableException("Can't add this user to the table");
    }


    /**
     *
     * @param idTable
     * @return
     */
    private int getIDTable(UUID idTable) {
        int index = 0;
        Iterator<Table> iter = this.listTable.iterator();
        while (iter.hasNext())
        {
            if(iter.next().getIdTable() == idTable){
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Return the index of the table if it's in the tables list
     * @param table : table to check
     * @return : index of the table if contained, -1 if not
     */
    public int getIDTable(Table table){
        return this.getListTable().indexOf(table);
    }

    /**
     * Return the table which got idTable as UUID, if contained in the list
     * @param idTable : UUID of the Table to find
     * @return : Table if found, null otherwise
     */
    public Table getTable(UUID idTable){
        int index = getIDTable(idTable);
        if(index != -1)
            return this.listTable.get(getIDTable(idTable));
        else return null;
    }


/********************* Getters and Setters *****************************/

    public ArrayList<Table> getListTable() {
        return listTable;
    }

    public void setListTable(ArrayList<Table> listTable) {
        this.listTable = listTable;
    }

}
