package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.ContactException;
import fr.utc.lo23.common.data.exceptions.GroupeNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jianghan on 20/10/2015.
 */
public class Contact {

    /**
     * listGroups : liste des groupes de contacts
     */
    private ArrayList<Group> listGroups;

    /**
     * Method to add a contact to a group
     * @param groupName : the name of group to add the contact
     * @param newContact : the name of contact to add
     */
    public void updateContacts(String groupName, UserLight newContact) throws ContactException {
        Group toAddTo = new Group();
        for (Group cur : listGroups)
        {
            if (cur.getGroupName().equals(groupName))
                toAddTo = cur;
        }
        if (toAddTo != null) {
            toAddTo.addContact(newContact);
        } else {
            throw new ContactException("group to add does not exist");
        }
    }

    /**
     * Method to delete a contact
     * @param toDelete
     */
    public void deleteContact(UserLight toDelete, String fromGroup) throws GroupeNotFoundException, UserLightNotFoundException {
        Group toDelFrom = new Group();
        for (Group cur : listGroups)
        {
            if (cur.getGroupName().equals(fromGroup))
                toDelFrom = cur;
        }
        if (toDelFrom != null) {
            toDelFrom.delContact(toDelete);
        }
        else
            throw new GroupeNotFoundException(fromGroup);
    };


    /**
     * Method to add a new group to the list listGroups
     * @param groupName name of group to add
     */
    public void createContactList(String groupName) throws ContactException {
        if (Arrays.asList(listGroups).contains(groupName)) {
            throw new ContactException("group to create already exist ");
        } else {
            Group newGroup = new Group(groupName);
            listGroups.add(newGroup);
        }
    }

    /** 
     * Method to delete a group with its name
     * @param groupName name of the group to delete
     */
    public void deleteContactList(String groupName) throws ContactException {
        Group toDelete = new Group();
        for (Group cur : listGroups)
        {
            if (cur.getGroupName().equals(groupName))
                toDelete = cur;
        }
        if (toDelete != null) {
            listGroups.remove(toDelete);
        } else {
            throw new ContactException("group to delete does not exist");
        }
    }
}
