package fr.utc.lo23.client.ihm_table.controllers;

import fr.utc.lo23.client.ihm_table.IHMTable;
import fr.utc.lo23.client.ihm_table.TableUtils;
import fr.utc.lo23.client.ihm_table.views.PlayerView;
import fr.utc.lo23.common.data.Game;
import fr.utc.lo23.common.data.MessageChat;
import fr.utc.lo23.common.data.Table;
import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.common.data.exceptions.ExistingUserException;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;

public class TableController {

    private IHMTable ihmTable;
    private Table table;
    private HashMap<UserLight,PlayerController> playerControllerMap;
    private Image defaultImage;

    private boolean isHost = true;

    public void setInterface(IHMTable ihmTable){
        this.ihmTable = ihmTable;
        ihmTable.setTableController(this);
    }

    public void setTable(Table table) {
        this.table = table;
        playerInitializer();
        chatInitializer();
    }

    public TableController(){
        playerControllerMap = new HashMap<UserLight,PlayerController>();
        defaultImage = new Image(getClass().getResource("../images/default.png").toExternalForm());
    }

    public void initialize(){
        if(isHost) btnLaunchGame.setVisible(true);
        showActionBox();
        enableActionFold();
        enableActionCheck();
        enableActionFollow();
        enableActionBet();
        enableActionAllin();
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

    public void chatInitializer(){
        //TODO: modifier ça quand ils auront modifié leur fucking fonction getCurrentGame
        for(MessageChat msg : table.getListGames().get(table.getCurrentGame()).getChatGame().getListMessages())
        {
            addChatMessage(msg);
        }
    }

    public void addPlayer(UserLight user) {
        addPlayer(table.getListPlayers().getListUserLights().size(), user, defaultImage);
    }

    public void addPlayer(int id, UserLight user, Image image) {
        Point2D coords = TableUtils.getPlayerPosition(id, table.getNbPlayerMax());
        PlayerView playerView = new PlayerView();
        playerControllerMap.put(user, playerView.createPlayer(tablePane, user, coords, image));
        //Add new object and move all others
        //TranslateTransition animation = new TranslateTransition(new Duration(500), playerView.getNode());
        //setFrom - setTo
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
    private Pane actionBox;
    @FXML
    private Button actionFold;
    @FXML
    private Button actionCheck;
    @FXML
    private Button actionFollow;
    @FXML
    private Button actionBet;
    @FXML
    private Slider actionBetMoneySelector;
    @FXML
    private Button actionAllin;

	@FXML
	private void sendMessage(javafx.event.ActionEvent event) {
        //TODO : getUserLight
        // à voir comment on fait ? est-ce qu'on doit le récupérer de data ou de ihm main ? plutot de data, donc il nous faut une fonction
        //TO DELETE
        UserLight self = new UserLight();

        Timestamp time = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        MessageChat message = new MessageChat(self,time,messageToSend.getText());

        //TODO : Raccorder à DATA
        //ihmTable.getDataInterface(). fucking fontion qui existe pas

        //TO DELETE
        addChatMessage(message);

        messageToSend.clear();
	}

    @FXML
    private void launchGame(javafx.event.ActionEvent event) {
        System.out.println("LAUNCHED");
        btnLaunchGame.setVisible(false);
        //ihmTable.getDataInterface().playGame(table.getIdTable());
    }

    public void addChatMessage(MessageChat message) {
        chatList.getItems().add(message.getSender().getPseudo() + " : " + message.getText());
        chatList.setStyle("-fx-graphic:red;");
    }

    public void showActionBox(){
        actionBox.setVisible(true);
    }

    public void hideActionBox(){
        actionBox.setVisible(false);
    }

    public void enableActionFold(){
        actionFold.getStyleClass().remove("action_fold_off");
        actionFold.getStyleClass().add("active");
        actionFold.getStyleClass().add("action_fold_on");
    }

    public void disableActionFold(){
        actionFold.getStyleClass().remove("action_fold_on");
        actionFold.getStyleClass().remove("active");
        actionFold.getStyleClass().add("action_fold_off");
    }

    public void enableActionCheck(){
        actionCheck.getStyleClass().remove("action_check_off");
        actionCheck.getStyleClass().add("active");
        actionCheck.getStyleClass().add("action_check_on");
    }

    public void disableActionCheck(){
        actionCheck.getStyleClass().remove("action_check_on");
        actionCheck.getStyleClass().remove("active");
        actionCheck.getStyleClass().add("action_check_off");
    }

    public void enableActionFollow(){
        actionFollow.getStyleClass().remove("action_follow_off");
        actionFollow.getStyleClass().add("active");
        actionFollow.getStyleClass().add("action_follow_on");
    }

    public void disableActionFollow(){
        actionFollow.getStyleClass().remove("action_follow_on");
        actionFollow.getStyleClass().remove("active");
        actionFollow.getStyleClass().add("action_follow_off");
    }

    public void enableActionBet(){
        actionBet.getStyleClass().remove("action_bet_off");
        actionBet.getStyleClass().add("active");
        actionBet.getStyleClass().add("action_bet_on");
    }

    public void disableAction(){
        actionBet.getStyleClass().remove("action_bet_on");
        actionBet.getStyleClass().remove("active");
        actionBet.getStyleClass().add("action_bet_off");
    }

    public void enableActionAllin(){
        actionAllin.getStyleClass().remove("action_allin_off");
        actionAllin.getStyleClass().add("active");
        actionAllin.getStyleClass().add("action_allin_on");
    }

    public void disableActionAllin(){
        actionAllin.getStyleClass().remove("action_allin_on");
        actionAllin.getStyleClass().remove("active");
        actionAllin.getStyleClass().add("action_allin_off");
    }



}
