package fr.utc.lo23.common.data.exceptions;

import java.util.UUID;

/**
 * Created by R�my on 31/10/2015.
 */
public class UserLightNotFoundException extends Exception {

    public UserLightNotFoundException(UUID id){
        System.out.println("La recherche n'a pas trouv� de UserLight pour l'ID : "+id.toString());
    }

}
