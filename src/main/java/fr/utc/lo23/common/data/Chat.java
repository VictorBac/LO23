package fr.utc.lo23.common.data;

import java.util.ArrayList;

/**
 * Class to represent a chat
 * Created by Haroldcb on 21/10/2015.
 */
public class Chat {
    /**
     * listMessages : table of messages sended
     * @see  fr.utc.lo23.common.data.MessageChat
     */
    private ArrayList<MessageChat> listMessages;

    /**
     * Constructor
     * @param listMessages
     */
    public Chat(ArrayList<MessageChat> listMessages) {
        this.listMessages = listMessages;
    }

    //getter
    public ArrayList<MessageChat> getListMessages() {
        return listMessages;
    }
    
    //setter
    public void setListMessages(ArrayList<MessageChat> listMessages) {
        this.listMessages = listMessages;
    }

    /**
     * method that permit to send a new message in the chat
     * @param message : message to send
     * @see  fr.utc.lo23.common.data.MessageChat
     */
    public void newMessage(MessageChat message){}
}

