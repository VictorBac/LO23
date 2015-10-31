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
     * TODO : exception au lieu de null
     */
    public UserLight getUser(UUID userId){
        for (UserLight cur : listUserLights)
        {
            if (cur.getIdUser().equals(userId))
                return cur;
        }
        return null;
    }

    public ArrayList<UserLight> getListUserLights() {
        return listUserLights;
    }
}
