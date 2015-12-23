package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Class to represent a message of a chat
 * Created by Ying on 21/10/2015.
 */
public class MessageChat implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * sender : sender of the message
     * time : moment of the message, used for the coordination
     * text : content of the message
     */
    private UserLight sender;
    private Timestamp time;
    private String text;

    /**
     * Constructor
     * @param sender
     * @param time
     * @param text
     */
    public MessageChat(UserLight sender, Timestamp time, String text) {
        this.sender = sender;
        this.time = time;
        this.text = text;
    }

    /*********************Getters & Setters*********************/

    /**
     * Getter to return the sender of the message
     * @return
     */
    public UserLight getSender() {
        return sender;
    }

    /**
     * Getter to return the time of the message
     * @return
     */
    public Timestamp getTime() {
        return time;
    }

    /**
     * Getter to return the content of the message
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * Setter to modify the sender of the message
     * @param sender
     */
    public void setSender(UserLight sender) {
        this.sender = sender;
    }

    /**
     * Setter to modify the time of the message
     * @param time
     */
    public void setTime(Timestamp time) {
        this.time = time;
    }

    /**
     * Setter to modify the content of the message
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

}
