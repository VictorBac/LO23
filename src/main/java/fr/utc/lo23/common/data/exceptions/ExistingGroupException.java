package fr.utc.lo23.common.data.exceptions;

/**
 * Created by Remy on 31/10/2015.
 */
public class ExistingGroupException extends Exception {

    public ExistingGroupException(String name) {
        System.out.println("Il existe deja un groupe avec le nom "+name);
    }
}
