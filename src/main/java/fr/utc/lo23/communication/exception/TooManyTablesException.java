/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.communication.exception;

/**
 *
 * @author Jean-Côme
 */
public class TooManyTablesException extends Exception {
    
    public TooManyTablesException() {
        super();
    }
    
    public TooManyTablesException(String s) {
        super(s);
    }
}
