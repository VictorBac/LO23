package fr.utc.lo23.common.data.exceptions;

import java.util.UUID;

/**
 * Created by R�my on 31/10/2015.
 */
public class UserNotFoundException extends Exception {


    public UserNotFoundException(UUID userId){
        System.out.println("La recherche n'a pas trouv� de User pour l'ID : "+ userId.toString());
    }
}
