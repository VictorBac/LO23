package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.ExistingUserException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by R�my on 20/10/2015.
 */
public class UserList implements Serializable{

    private ArrayList<User> listUser;
    private static final long serialVersionUID = 1L;

    public UserList() {
        listUser = new ArrayList<User>();
    }

    /**
     * recherche un User dans l'arrayList � partir de son UUID
     * @param userId l'UUUID du joueur � trouver
     * @return le joueur voulu, null si non trouv�
     */
    public User getUser(UUID userId) throws UserNotFoundException {
        for (User cur : listUser) {
            if (cur.getUserLight().getIdUser().equals(userId))
                return cur;
        }
        throw new UserNotFoundException(userId);
    }

    /**
     * @param connectingUser
     */
    public void addUser(User connectingUser)  throws ExistingUserException{
        if (this.listUser.contains(connectingUser))
            throw new ExistingUserException(connectingUser.getUserLight());
        else
            this.listUser.add(connectingUser);
    }

    public ArrayList<User> getList(){
        return listUser;
    }
}
