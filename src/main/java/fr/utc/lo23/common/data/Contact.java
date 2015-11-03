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
     */
    public void updateContacts(String groupName, UserLight newContact) throws GroupeNotFoundException{
        Groupe toAddTo = new Groupe();
        for (Groupe cur : listGroups)
        {
            if (cur.getNomGroupe().equals(groupName))
                toAddTo = cur;
        }
        if (toAddTo != null)
            toAddTo.addContact(newContact);
        else
            throw new GroupeNotFoundException(groupName);
    };

    /**
     * supprime le contact d'un groupe
     * @param toDelete le Userlight � supprimer
     * @param fromGroup : le groupe dont on supprime le contact
     */
    public void deleteContact(UserLight toDelete, String fromGroup) throws GroupeNotFoundException, UserLightNotFoundException {
        Groupe toDelFrom = new Groupe();
        for (Groupe cur : listGroups)
        {
            if (cur.getNomGroupe().equals(fromGroup))
                toDelFrom = cur;
        }
        if (toDelFrom != null) {
            toDelFrom.delContact(toDelete);
        }
        else
            throw new GroupeNotFoundException(fromGroup);
    };


    /**
     * ajoute un nouveau groupe � la liste listGroups
     * @param groupName nom du groupe � cr�er
     */
    public void createContactList(String groupName) throws ExistingGroupException{
        Groupe newGroupe = new Groupe(groupName);
        if (listGroups.contains(newGroupe))
            throw new ExistingGroupException(groupName);
        else
            listGroups.add(newGroupe);

    }

    /**
     * Supprime un groupe de listGroups � partir de son nom
     * @param groupName nom du groupe � supprimer
     */
    public void deleteContactList(String groupName) throws GroupeNotFoundException{
        Groupe toDelete = new Groupe();
        for (Groupe cur : listGroups)
        {
            if (cur.getNomGroupe().equals(groupName))
                toDelete = cur;
        }
        if (toDelete != null)
            listGroups.remove(toDelete);
        else
            throw new GroupeNotFoundException(groupName);
    }
}