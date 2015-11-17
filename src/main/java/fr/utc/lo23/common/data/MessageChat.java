package fr.utc.lo23.common.data;

import java.sql.Timestamp;

/**
 * Class to represent a message of a chat
 * Created by Ying on 21/10/2015.
 */
public class MessageChat {

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

    //getters

    public UserLight getSender() { return sender; }

    public Timestamp getTime() { return time; }

    public String getText() { return text; }

    //setters

    public void setSender(UserLight sender) { this.sender = sender; }

    public void setTime(Timestamp time) { this.time = time; }

    public void setText(String text) { this.text = text; }


}
