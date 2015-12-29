package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.ExistingUserException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Remy on 20/10/2015.
 */
public class UserList implements Serializable{

    private ArrayList<User> listUser;
    private static final long serialVersionUID = 1L;

    /**
     * Constructor by default
     */
    public UserList() {
        listUser = new ArrayList<User>();
    }

    /**
     * Search a User in the ArrayList with its UUID
     * @param userId  UUID of the User that we are searching for
     * @return User that was found
     * @throws UserNotFoundException thrown if no User corresponds to the UUID
     */
    public User getUser(UUID userId) throws UserNotFoundException {
        for (User cur : listUser) {
            if (cur.getUserLight().getIdUser().equals(userId))
                return cur;
        }
        throw new UserNotFoundException(userId);
    }


    /**
     * Method to add a User to the ArrayList of User
     * @param connectingUser User of the new User
     * @throws ExistingUserException thrown if the User already exist in the list
     */
    public void addUser(User connectingUser)  throws ExistingUserException{
        if (this.listUser.contains(connectingUser))
            throw new ExistingUserException(connectingUser.getUserLight());
        else
            this.listUser.add(connectingUser);
    }

    /**
     * Method to get the ArrayList of User
     * @return ArrayList<User>
     */
    public ArrayList<User> getList(){
        return listUser;
    }


    /**
     * Method to update the profile of a User
     * @param newUserProfile User that corresponds to the new Profile
     */
    public void changeUserProfile(User newUserProfile){
        listUser.remove(newUserProfile);
        listUser.add(newUserProfile);

    }


}
