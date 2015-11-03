package fr.utc.lo23.common.data;

import java.util.ArrayList;

/**
 * Created by Jianghan on 20/10/2015.
 */
public class Groupe {

    /**
     * nomGroupe = nom du groupe
     * listContact = ?
     */
    private String nomGroupe;
    private ArrayList<UserLight> listContact;

    /**
     * group default constructor
     */
    public Groupe() {

    }

    /**
     * group constructor
     * @param name the name of group to create
     */
    public Groupe(String name) {
        this.nomGroupe = name;
        this.listContact = new ArrayList<UserLight>();
    }

    /**
     * getter de nomGroupe TODO nom?
     * @return l'attribut nomGroupe
     */
    public String getGroupeName(){
        return nomGroupe;
    }

    /**
     * getter of listContact
     * @return istContact
     */
    public ArrayList<UserLight> getContacts(){
        return listContact;
    }

    /**
     * method to add a contact to the list listContact
     * @param newUser : the userLight to add
     */
    public void addContact(UserLight newUser){
        listContact.add(newUser);
    }
}
