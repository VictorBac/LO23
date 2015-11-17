package fr.utc.lo23.common.data;

import java.util.ArrayList;

/**
 * Class to represent a chat
 * Created by Ying on 03/11/2015.
 */
public class Chat {
    /**
     * listMessages : table of messages sended
     * @see  fr.utc.lo23.common.data.MessageChat
     */
    private ArrayList<MessageChat> listMessages;

    /**
     * Constructor Default
     */
    public Chat() { this.listMessages = new ArrayList<MessageChat>();
    }

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
     * method to add a new message in the chat
     * @param message : message to send
     * @see  fr.utc.lo23.common.data.MessageChat
     */
    public void newMessage(MessageChat message){

        this.listMessages.add(message);

    }

}
