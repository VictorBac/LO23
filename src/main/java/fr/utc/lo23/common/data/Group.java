package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Jianghan on 20/10/2015.
 */
public class Group implements Serializable {
    private static final long serialVersionUID = 1L;
    private String groupName;
    private ArrayList<UserLight> contactList;

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
        this.contactList = new ArrayList<UserLight>();
    }

    /**
     * getter of groupName
     *
     * @return l'attribute groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * getter of contactList
     *
     * @return l'attribute contactList
     */
    public ArrayList<UserLight> getContacts() {
        return contactList;
    }

    /**
     * method to add a contact to the list contactList
     *
     * @param newUser : the Userlight to add
     */
    public void addContact(UserLight newUser) {
        contactList.add(newUser);
    }

    /**
     * method to delete a contact from the contactList
     *
     * @param toDelete : Userlight to delete
     */
    public void delContact(UserLight toDelete) throws UserLightNotFoundException {
        if (contactList.contains(toDelete)) {
            contactList.remove(toDelete);
        } else {
            throw new UserLightNotFoundException(toDelete.getIdUser());
        }
    }

}
