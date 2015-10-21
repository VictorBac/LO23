package fr.utc.lo23.common.data;

import java.util.ArrayList;

/**
 * Created by Rémy on 20/10/2015.
 */
public class Contact {

    /**
     * listGroups : liste des groupes de contacts
     */
    private ArrayList<Groupe> listGroups;

    /**
     * méthode permettant d'ajouter un contact à un groupe
     * @param groupName : nom du groupe à modifier
     * @param newContact : nom du contact à ajouter
     * TODO : gérer exception en cas de groupe null
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
     * ajoute un nouveau groupe à la liste listGroups
     * @param groupName nom du groupe à créer
     * TODO : exception si groupe déjà existant
     */
    public void createContactList(String groupName){
        Groupe newGroupe = new Groupe(groupName);
        listGroups.add(newGroupe);
    };

    /**
     * Supprime un groupe de listGroups à partir de son nom
     * @param groupName nom du groupe à supprimer
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
