package fr.utc.lo23.client.ihm_table;

import fr.utc.lo23.common.data.MessageChat;
import fr.utc.lo23.common.data.UserLight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.Timestamp;
import java.util.Calendar;

public class TableController {

	public TableController(){
		
	}

	@FXML
	private TextField messageToSend;
    @FXML
    private ListView<String> chatList;
    ObservableList<String> items = FXCollections.observableArrayList ();

	@FXML
	private void sendMessage(javafx.event.ActionEvent event) {
        //TODO : getUserLight
        UserLight self = new UserLight();

        Timestamp time = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        MessageChat message = new MessageChat(self,time,messageToSend.getText());

        sendPacket(message);
        messageToSend.clear();
	}

    private void sendPacket(MessageChat message) {
        //TODO : Raccorder à DATA
        addChatMessage(message);
    }

    private void addChatMessage(MessageChat message) {
        items.add("[" + message.getTime() + "] " + message.getSender().getPseudo() + " : " + message.getText());
        chatList.setItems(items);
    }
}
