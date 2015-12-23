/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.utc.lo23.exceptions.network;

/**
 *
 * @author Jean-Côme
 */
public class ConnectionLostException extends Exception {
    
    public ConnectionLostException() {
        super();
    }
    
    public ConnectionLostException(String s) {
        super(s);
    }
}