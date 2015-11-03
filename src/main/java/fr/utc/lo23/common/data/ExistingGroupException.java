package fr.utc.lo23.common.data;

/**
 * Created by Rémy on 31/10/2015.
 */
public class ExistingGroupException extends Exception {

    public ExistingGroupException(String name){
        System.out.println("Il existe déjà un groupe avec le nom "+name);
    }
}
