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
}
