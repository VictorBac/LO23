package fr.utc.lo23.common.data;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by R�my on 20/10/2015.
 */
public class UserLightList {

    private ArrayList<UserLight> listUserLights;


    /**
     * recherche un UserLight dans l'arrayList � partir de son UUID
     * @param userId l'UUUID du joueur � trouver
     * @return le joueur voulu, null si non trouv�
     
     */
    public UserLight getUser(UUID userId) throws UserLightNotFoundException{
        for (UserLight cur : listUserLights)
        {
            if (cur.getIdUser().equals(userId))
                return cur;
        }
        throw new UserLightNotFoundException(userId);
    }

}
