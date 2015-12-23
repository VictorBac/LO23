package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.ExistingUserException;
import java.io.Serializable;

/**
 * Created by Jianghan on 20/10/2015.
 */
public class Group implements Serializable {
    private static final long serialVersionUID = 1L;



    private String groupName;
    private UserLightList contactList;

    /**
     * group default constructor
     */
    public Group() {

    }

    /**
     * group constructor
     *
     * @param name the name of group to create
     */
    public Group(String name) {
        this.groupName = name;
        this.contactList = new UserLightList();
    }

    /**
     * getter of groupName
     * @return getter of groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * setter of groupName
     * @param groupName groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * getter of contactList
     * @return contactList
     */
    public UserLightList getContacts() {
        return contactList;
    }

    /**
     * method to add a Userlight to the list
     *
     * @param toAdd : the Userlight to add
     */
    public void addUser(UserLight toAdd) throws ExistingUserException {
        contactList.addUser(toAdd);
    }

    /**
     * method to delete a contact from the contactList
     *
     * @param toDelete : Userlight to delete
     */
    public void removeUser(UserLight toDelete) throws UserLightNotFoundException {
        contactList.remove(toDelete);
    }

}
