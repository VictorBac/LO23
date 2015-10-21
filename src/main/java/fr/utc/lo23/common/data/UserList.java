package fr.utc.lo23.common.data;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Rémy on 20/10/2015.
 */
public class UserList {

    private ArrayList<User> listUser;


    /**
     * recherche un User dans l'arrayList à partir de son UUID
     * @param userId l'UUUID du joueur à trouver
     * @return le joueur voulu, null si non trouvé
     * TODO : exception au lieu de null
     */
    public User getUser(UUID userId){
        for (User cur : listUser) {
            if (cur.getIdUser().equals(userId))
                return cur;
        }
        return null;
    }
}
