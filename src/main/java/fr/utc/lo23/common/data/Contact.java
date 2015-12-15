package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.ContactException;
import fr.utc.lo23.common.data.exceptions.ExistingUserException;
import fr.utc.lo23.common.data.exceptions.GroupeNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jianghan on 20/10/2015.
 */
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * group list
     */
    private ArrayList<Group> groupList;

    /**
     * Method to add a UserLight to a group
     * @param groupName : the name of group to add the contact
     * @param newContact : the name of contact to add
     */
    public void addToGroup(String groupName, UserLight newContact) throws ContactException, ExistingUserException {
        Group toAddTo = new Group();
        for (Group cur : groupList)
        {
            if (cur.getGroupName().equals(groupName))
                toAddTo = cur;
        }
        if (toAddTo != null) {
            toAddTo.addUser(newContact);
        } else {
            throw new ContactException("group to add does not exist");
        }
    }

    /**
     * Method to delete a UserLight from a group
     * @param toDelete
     */
    public void removeFromGroup(UserLight toDelete, String fromGroup) throws GroupeNotFoundException, UserLightNotFoundException {
        Group toRemoveFrom = new Group();
        for (Group cur : groupList)
        {
            if (cur.getGroupName().equals(fromGroup))
                toRemoveFrom = cur;
        }
        if (toRemoveFrom != null) {
            toRemoveFrom.removeUser(toDelete);
        }
        else
            throw new GroupeNotFoundException(fromGroup);
    }


    /**
     * Method to add a new group to the group list
     * @param groupName name of group to add
     */
    public void addGroup(String groupName) throws ContactException {
        if (Arrays.asList(groupList).contains(groupName)) {
            throw new ContactException("group to create already exist ");
        } else {
            Group newGroup = new Group(groupName);
            groupList.add(newGroup);
        }
    }

    /** 
     * Method to remove a group with its name
     * @param groupName name of the group to remove
     */
    public void removeGroup(String groupName) throws ContactException {
        Group toRemove = new Group();
        for (Group cur : groupList)
        {
            if (cur.getGroupName().equals(groupName))
                toRemove = cur;
        }
        if (toRemove != null) {
            groupList.remove(toRemove);
        } else {
            throw new ContactException("group to remove does not exist");
        }
    }
}
