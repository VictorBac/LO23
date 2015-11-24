package fr.utc.lo23.common.data;

import fr.utc.lo23.common.data.exceptions.ExistingUserException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Rémy on 20/10/2015.
 */
public class UserLightList implements Serializable{

    private ArrayList<UserLight> listUserLights;


    /**
     * recherche un UserLight dans l'arrayList à partir de son UUID
     * @param userId l'UUUID du joueur à trouver
     * @return le joueur voulu, nexception si non trouvé

     */
    public UserLight getUser(UUID userId) throws UserLightNotFoundException{
        for (UserLight cur : listUserLights)
        {
            if (cur.getIdUser().equals(userId))
                return cur;
        }
        throw new UserLightNotFoundException(userId);
    }

    public ArrayList<UserLight> getListUserLights() {
        return listUserLights;
    }

    public void setUserList(ArrayList<UserLight> newList){
        this.listUserLights = newList;
    }

    public void addUser(UserLight toAdd) throws ExistingUserException {
        if (listUserLights.contains(toAdd))
            throw new ExistingUserException(toAdd);
        else
            listUserLights.add(toAdd);
    }

    /**
     * finds a user in listUserLights using his id
     * @param userId the id of the userlight to find
     * @return the userlight found, or returns an exception
     */
    public UserLight findUser(UUID userId) throws UserLightNotFoundException {

        for (UserLight cur : this.listUserLights){
            if (cur.getIdUser().equals(userId))
                return cur;
        }
        throw new UserLightNotFoundException(userId);

    }

    /**
     * removes a userlight from the list
     * @param toRemove the userlight to remove
     * @throws UserLightNotFoundException
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
}
