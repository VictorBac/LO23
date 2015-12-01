package fr.utc.lo23.common.data.exceptions;

import fr.utc.lo23.common.data.UserLight;

/**
 * Created by Rémy on 03/11/2015.
 */
public class ExistingUserException extends Exception {

    public ExistingUserException(UserLight user){
        System.out.println("Il exise déjà un utilisateur avec l'ID "+ user.getIdUser());
    }
}
