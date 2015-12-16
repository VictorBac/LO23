package fr.utc.lo23.common.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to represent a chat
 * Created by Ying on 03/11/2015.
 */
public class Chat implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * listMessages : table of messages sent
     * @see  fr.utc.lo23.common.data.MessageChat
     */
    private ArrayList<MessageChat> listMessages;

    /**
     * Constructor by default
     */
    public Chat() {
        this.listMessages = new ArrayList<MessageChat>();
    }

    /**
     * Constructor
     * @param listMessages
     */
    public Chat(ArrayList<MessageChat> listMessages) {
        this.listMessages = listMessages;
    }

    /**
     * method to add a new message in the chat
     * @param message : message to send
     * @see  fr.utc.lo23.common.data.MessageChat
     */
    public void newMessage(MessageChat message){
        this.listMessages.add(message);
    }

    /*********************Getters & Setters*********************/

    /**
     * Getter to return a list messages
     * @return
     */
    public ArrayList<MessageChat> getListMessages() {
        return listMessages;
    }

    /**
     * Setter to modify a list messages
     * @param listMessages
     */
    public void setListMessages(ArrayList<MessageChat> listMessages) {
        this.listMessages = listMessages;
    }

}
