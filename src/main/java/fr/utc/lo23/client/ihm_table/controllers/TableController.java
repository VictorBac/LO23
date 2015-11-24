package fr.utc.lo23.client.ihm_table.controllers;

import fr.utc.lo23.client.ihm_table.IHMTable;
import fr.utc.lo23.common.data.MessageChat;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.Timestamp;
import java.util.Calendar;

public class TableController {

    private IHMTable ihmTable;
    private Table table;

    private boolean isHost = true;

    public void setInterface(IHMTable ihmTable){ this.ihmTable = ihmTable; }
    public void setTable(Table table) { this.table = table; }

	public TableController(){

	}

    public void initialize(){
        /*ListChangeListener<String> chatListener;
        chatListener = (changed) -> {
            changed.next();
            if(changed.wasAdded())
            {
                chatList.getItems().add(changed.getAddedSubList().get(0));
            }
        };
        items.addListener(chatListener);*/
        if(isHost) btnLaunchGame.setVisible(true);
    }

	@FXML
	private TextField messageToSend;
    @FXML
    private ListView<String> chatList;
    @FXML
    private Button btnLaunchGame;

	@FXML
	private void sendMessage(javafx.event.ActionEvent event) {
        //TODO : getUserLight
        UserLight self = new UserLight();

        Timestamp time = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        MessageChat message = new MessageChat(self,time,messageToSend.getText());

        sendPacket(message);
        messageToSend.clear();
	}

    @FXML
    private void launchGame(javafx.event.ActionEvent event) {
        System.out.println("LAUNCHED");
        btnLaunchGame.setVisible(false);
        //ihmTable.getDataInterface().playGame(table.getIdTable());
    }

    private void sendPacket(MessageChat message) {
        //TODO : Raccorder à DATA
        addChatMessage(message);
    }

    private void addChatMessage(MessageChat message) {
        chatList.getItems().add(message.getSender().getPseudo() + " : " + message.getText());
        chatList.setStyle("-fx-graphic:red;");
    }
}
