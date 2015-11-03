package fr.utc.lo23.common.data;

import java.util.ArrayList;

/**
 * Created by Rémy on 20/10/2015.
 */
public class Groupe {

    /**
     * nomGroupe = nom du groupe
     * listContact = ?
     */
    private String nomGroupe;
    private ArrayList<UserLight> listContact;

    /**
     * constructeur vide pour groupe
     */
    public Groupe() {

    }

    /**
     * constructeur pour Groupe
     *
     * @param name le nom du groupe à créer
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
     * méthode ajoutant un contact à la liste listContact
     *
     * @param newUser : le Userlight à ajouter
     */
    public void addContact(UserLight newUser) {
        listContact.add(newUser);
    }

    /**
     * supprime un contact de la listeContact
     *
     * @param toDelete : Userlight à supprimer
     */
    public void delContact(UserLight toDelete) throws UserLightNotFoundException {
        if (listContact.contains(toDelete)) {
            listContact.remove(toDelete);
        } else {
            throw new UserLightNotFoundException(toDelete.getIdUser());
        }
    }

}