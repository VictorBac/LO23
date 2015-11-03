package fr.utc.lo23.common.data.exceptions;

/**
 * Created by Remy on 31/10/2015.
 */
public class GroupeNotFoundException extends Exception {

    public GroupeNotFoundException(String name){
        System.out.println("Aucun groupe trouve pour le nom "+ name);
    }
}
