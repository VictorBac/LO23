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
public class FailedToSendMessageException extends Exception {
    
    public FailedToSendMessageException() {
        super();
    }
    
    public FailedToSendMessageException(String s) {
        super(s);
    }
}