package fr.utc.lo23.common.data;

import java.sql.Timestamp;

/**
 * Class to represent a message of a chat
 * Created by Haroldcb on 21/10/2015.
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
}
