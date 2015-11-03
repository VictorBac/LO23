package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.ExistingGroupException;
import fr.utc.lo23.common.data.exceptions.GroupeNotFoundException;

import java.util.ArrayList;

/**
 * Created by Jianghan on 20/10/2015.
 */
public class Contact {

    /**
     * listGroups : liste des groupes de contacts
     */
    private ArrayList<Groupe> listGroups;

    /**
     * methode permettant d'ajouter un contact a un groupe
     * @param groupName : nom du groupe a modifier
     * @param newContact : nom du contact a ajouter
     */
    public void updateContacts(String groupName, UserLight newContact) throws GroupeNotFoundException {
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
     * @param toDelete le Userlight a supprimer
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
     * ajoute un nouveau groupe a la liste listGroups
     * @param groupName nom du groupe a creer
     */
    public void createContactList(String groupName) throws ExistingGroupException {
        Groupe newGroupe = new Groupe(groupName);
        if (listGroups.contains(newGroupe))
            throw new ExistingGroupException(groupName);
        else
            listGroups.add(newGroupe);

    }

    /**
     * Supprime un groupe de listGroups a partir de son nom
     * @param groupName nom du groupe a supprimer
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