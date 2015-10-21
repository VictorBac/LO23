package fr.utc.lo23.common.data;

import java.util.ArrayList;

/**
 * Created by R�my on 20/10/2015.
 */
public class Contact {

    /**
     * listGroups : liste des groupes de contacts
     */
    private ArrayList<Groupe> listGroups;

    /**
     * m�thode permettant d'ajouter un contact � un groupe
     * @param groupName : nom du groupe � modifier
     * @param newContact : nom du contact � ajouter
     * TODO : g�rer exception en cas de groupe null
     */
    public void updateContacts(String groupName, UserLight newContact){
        Groupe toAddTo = new Groupe();
        for (Groupe cur : listGroups)
        {
            if (cur.getNomGroupe().equals(groupName))
                toAddTo = cur;
        }
        if (toAddTo != null)
            toAddTo.addContact(newContact);
    };

    /**
     * supprime le contact d'un groupe
     * @param toDelete
     */
    public void deleteContact(UserLight toDelete){};

    /**
     * ajoute un nouveau groupe � la liste listGroups
     * @param groupName nom du groupe � cr�er
     * TODO : exception si groupe d�j� existant
     */
    public void createContactList(String groupName){
        Groupe newGroupe = new Groupe(groupName);
        listGroups.add(newGroupe);
    };

    /**
     * Supprime un groupe de listGroups � partir de son nom
     * @param groupName nom du groupe � supprimer
     * TODO : exception si groupe non existant
     */
    public void deleteContactList(String groupName){
        Groupe toDelete = new Groupe();
        for (Groupe cur : listGroups)
        {
            if (cur.getNomGroupe().equals(groupName))
                toDelete = cur;
        }
        if (toDelete != null)
            listGroups.remove(toDelete);
    };
}
