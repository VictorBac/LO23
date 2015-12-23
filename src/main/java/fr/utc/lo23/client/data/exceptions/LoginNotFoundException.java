package fr.utc.lo23.client.data.exceptions;

import java.util.UUID;

/**
 * Created by Jianghan on 20/11/2015.
 */
public class LoginNotFoundException extends Exception {

    public LoginNotFoundException(String login){
        System.out.println("Can not find login: "+ login + ", connexion fails.");
    }
}
