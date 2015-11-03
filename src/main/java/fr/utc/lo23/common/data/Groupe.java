package fr.utc.lo23.common.data;

import java.util.ArrayList;

/**
 * Created by Jianghan on 20/10/2015.
 */
public class Groupe {

    /**
     * nomGroupe = nom du groupe
     * listContact = list of contact in this group
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
     *
     * @param name le nom du groupe � cr�er
     */
    public Groupe(String name) {
        this.nomGroupe = name;
        this.listContact = new ArrayList<UserLight>();
    }

    /**
     * getter de nomGroupe
     *
     * @return l'attribut nomGroupe
     */
    public String getNomGroupe() {
        return nomGroupe;
    }

    /**
     * getter de listContact
     *
     * @return l'attribut listContact
     */
    public ArrayList<UserLight> getContacts() {
        return listContact;
    }

    /**
     * m�thode ajoutant un contact � la liste listContact
     *
     * @param newUser : le Userlight � ajouter
     */
    public void addContact(UserLight newUser) {
        listContact.add(newUser);
    }

    /**
     * supprime un contact de la listeContact
     *
     * @param toDelete : Userlight � supprimer
     */
    public void delContact(UserLight toDelete) throws UserLightNotFoundException {
        if (listContact.contains(toDelete)) {
            listContact.remove(toDelete);
        } else {
            throw new UserLightNotFoundException(toDelete.getIdUser());
        }
    }

}
