package fr.utc.lo23.client.ihm_table.controllers;

import fr.utc.lo23.client.ihm_table.IHMTable;
import fr.utc.lo23.client.ihm_table.TableUtils;
import fr.utc.lo23.client.ihm_table.views.PlayerView;
import fr.utc.lo23.common.data.MessageChat;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;

public class TableController {

    private IHMTable ihmTable;
    private Table table;
    private HashMap<UserLight,PlayerController> playerControllerMap;

    private boolean isHost = true;

    public void setInterface(IHMTable ihmTable){
        this.ihmTable = ihmTable;
        ihmTable.setTableController(this);
    }

    public void setTable(Table table) {
        this.table = table;
    }

	public TableController(){
        playerControllerMap = new HashMap<UserLight,PlayerController>();
	}

    public void initialize(){
        if(isHost) btnLaunchGame.setVisible(true);
    }

    public void playerInitializer(){
        int i=1;
        Image defaultImage = new Image(getClass().getResource("../images/default.png").toExternalForm());
        for(UserLight user : table.getListPlayers().getListUserLights())
        {
            Point2D coords = TableUtils.getPlayerPosition(i,table.getNbPlayerMax());
            PlayerView playerView = new PlayerView();
            playerControllerMap.put(user,playerView.createPlayer(tablePane,user,coords,defaultImage));
            i++;
        }
    }

	@FXML
	private TextField messageToSend;
    @FXML
    private ListView<String> chatList;
    @FXML
    private Button btnLaunchGame;
    @FXML
    private Pane tablePane;

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
        //ihmTable.getDataInterface(). fucking fontion qui existe pas

        //TO DELETE
        addChatMessage(message);
    }

    public void addChatMessage(MessageChat message) {
        chatList.getItems().add(message.getSender().getPseudo() + " : " + message.getText());
        chatList.setStyle("-fx-graphic:red;");
    }
}
