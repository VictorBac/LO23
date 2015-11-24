package fr.utc.lo23.client.data.exceptions;

/**
 * Created by Jianghan on 20/11/2015.
 */
public class WrongPasswordException extends Exception {

    public WrongPasswordException(){
        System.out.println("Wrong password, connexion fails!");
    }
}
