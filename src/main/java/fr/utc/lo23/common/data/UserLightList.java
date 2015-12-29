package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.ExistingUserException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Remy on 20/10/2015.
 */
public class UserLightList implements Serializable{

    private ArrayList<UserLight> listUserLights;
    private static final long serialVersionUID = 1L;

    /**
     * Default Constructor
     */
    public UserLightList(){
        listUserLights = new ArrayList<UserLight>();
    }

    /**
     * Search a UserLight in the ArrayList with its UUID
     * @param userId UUID of the UserLight that we are searching for
     * @return UserLight that was found
     * @throws UserLightNotFoundException  thrown if no UserLight corresponds to the UUID
     */
    public UserLight getUser(UUID userId) throws UserLightNotFoundException{
        for (UserLight cur : listUserLights)
        {
            if (cur.getIdUser().equals(userId))
                return cur;
        }

        throw new UserLightNotFoundException(userId);
    }

    /**
     * Method that get the ArrayList of UserLight
     * @return ArrayList<UserLight>
     */
    public ArrayList<UserLight> getListUserLights() {
        return listUserLights;
    }

    /**
     * Setter for the ArrayList of UserLight
     * @param newList ArrayList<UserLight> to set
     */
    public void setUserList(ArrayList<UserLight> newList){
        this.listUserLights = newList;
    }

    /**
     * Method to add a UserLight
     * @param toAdd  UserLight to add to the UserLight ArrayList
     * @throws ExistingUserException thrown if the UserLight already exist
     */
    public void addUser(UserLight toAdd) throws ExistingUserException {
        if (listUserLights.contains(toAdd))
            throw new ExistingUserException(toAdd);
        else
            listUserLights.add(toAdd);
    }


    /**
     * Search a UserLight in listUserLights using his id (UUID)
     * @param userId UUID the id of the UserLight to find
     * @return UserLight found
     * @throws UserLightNotFoundException thrown if no UserLight was found that corresponds to the userId
     */
    public UserLight findUser(UUID userId) throws UserLightNotFoundException {

        for (UserLight cur : this.listUserLights){
            if (cur.getIdUser().equals(userId))
                return cur;
        }
        throw new UserLightNotFoundException(userId);

    }

    /**
     * Removes a UserLight from the list
     * @param toRemove UserLight to remove
     * @throws UserLightNotFoundException thrown if no UserLight was found that corresponds to the UserLight
     */
    public void remove(UserLight toRemove) throws UserLightNotFoundException{
        try {
            this.findUser(toRemove.getIdUser());
            this.listUserLights.remove(toRemove);
        }

            catch(UserLightNotFoundException e)
        {
            throw e;
        }

    }

    /**
     * Method to update the profile of a UserLight
     * @param newUserLightProfile UserLight that corresponds to the new Profile
     */
    public void changeUserLightProfile(UserLight newUserLightProfile){
        listUserLights.remove(newUserLightProfile);
        listUserLights.add(newUserLightProfile);
    }
}
