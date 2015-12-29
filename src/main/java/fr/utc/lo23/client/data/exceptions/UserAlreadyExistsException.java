package fr.utc.lo23.client.data.exceptions;

/**
 * Created by polo4 on 29/12/2015.
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String login){
        System.out.println("The User " + login + " already exists ! Operation cancelled...");
    }
}
