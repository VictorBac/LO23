package fr.utc.lo23.client.ihm_table.controllers;

import fr.utc.lo23.client.ihm_table.IHMTable;
import fr.utc.lo23.client.ihm_table.TableUtils;
import fr.utc.lo23.client.ihm_table.views.BetMoneyView;
import fr.utc.lo23.client.ihm_table.views.PlayerView;
import fr.utc.lo23.common.data.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

public class TableController {

    private IHMTable ihmTable;
    private Table table;
    private HashMap<UserLight,PlayerController> playerControllerMap;
    private HashMap<UserLight,BetMoneyController> betMoneyControllerMap;
    private ArrayList<PlayerController> controllersList; //This is used to find where you can seat
    private Image defaultImage;
    private Action actionToFill;

    private boolean isHost = true;

    public Table getTable() {
        return table;
    }

    public PlayerController getPlayerControllerOf(UserLight user){
        return playerControllerMap.get(user);
    }

    public void setInterface(IHMTable ihmTable){
        this.ihmTable = ihmTable;
        ihmTable.setTableController(this);
    }

    public void setTable(Table table) {
        this.table = table;
        playerInitializer();
        chatInitializer();
    }

    @FXML
    private Slider actionBetMoneySelector;
    @FXML
    private Label betLabel;

    public TableController(){
        playerControllerMap = new HashMap<UserLight,PlayerController>();
        betMoneyControllerMap = new HashMap<UserLight,BetMoneyController>();
        defaultImage = new Image(getClass().getResource("../images/default.png").toExternalForm());
    }

    public void initialize(){
        //TODO : je suis pas certain qu'il faille mettre ça ici, mais bon, ça fonctionne
        betLabel.setText(Math.round(actionBetMoneySelector.getValue()) + "");
        actionBetMoneySelector.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (newValue == null) {
                    betLabel.setText("");
                    return;
                }
                betLabel.setText(Math.round(newValue.intValue()) + "");
            }
        });

        hideActionBox();
        disableAllActions();
        addLogEntry("Vous avez rejoint la salle.");
    }

    public void playerInitializer(){

        //Initialisation de la liste des sièges
        controllersList = new ArrayList<PlayerController>(table.getNbPlayerMax());
        for(int ite = 0; ite < table.getNbPlayerMax(); ++ite) {
            controllersList.add(ite, null);
        }

        //Affichage des joueurs déjà présents, dont moi
        for(int i=0;i<table.getListPlayers().getListUserLights().size();i++)
        {
            addPlayer(i, table.getListPlayers().getListUserLights().get(i), false);
        }
    }

    public void addPlayer(UserLight user) {
        addPlayer(getFirstAvailableSeat(), user, true);
    }

    /**
     * Add a player to the game
     * @param id
     * @param user
     * @param showLog : this boolean is used to show log only after the player joined the game
     */
    public void addPlayer(int id, UserLight user, boolean showLog) {
        Point2D coords = TableUtils.getPlayerPosition(id, table.getNbPlayerMax());
        PlayerView playerView = new PlayerView();
        PlayerController playerController = playerView.createPlayer(tablePane, user, coords, defaultImage);
        playerControllerMap.put(user, playerController);
        controllersList.set(id, playerController);
        if (showLog) addLogEntry(user.getPseudo()+" a rejoint la salle.");
        if(isHost && table.getListPlayers().getListUserLights().size() >= table.getNbPlayerMin())
            btnLaunchGame.setVisible(true);
    }

    public void chatInitializer(){
        //TODO: modifier ça quand ils auront modifié leur fucking fonction getCurrentGame
        for(MessageChat msg : table.getCurrentGame().getChatGame().getListMessages())
        {
            addChatMessage(msg);
        }
    }

    //TODO: appeler cette fonction après avoir réorganisé les joueurs, après le lancement d'une game.
    public void betMoneyBoxInitializer(){
        Point2D betPlayerBoxWidthHeight = new Point2D(585.0, 155.0);
        Point2D betPlayerBoxCenter = new Point2D(510.0, 215.0);
        int i=0;
        for(PlayerController playerController : controllersList)
        {
            if(playerController!=null)
            {
                Point2D coords = TableUtils.getPlayerPosition(i,table.getListPlayers().getListUserLights().size(),betPlayerBoxCenter,betPlayerBoxWidthHeight);
                BetMoneyView betMoneyView = new BetMoneyView();
                BetMoneyController betMoneyController = betMoneyView.createBetMoneyBox(tablePane, coords);
                betMoneyControllerMap.put(playerController.getUserLight(),betMoneyController);
            }
            i++;
        }

    }

    public int getFirstAvailableSeat() {
        for(int ite = 0; ite < table.getNbPlayerMax(); ++ite) {
            if(controllersList.get(ite) == null) {
                return ite;
            }
        }
        //Should never happen because player should not be added if table is full
        System.out.println("ERROR: Il y a plus de joueurs que le max, crash intercontinental de l'univers !! Poulpe.");
        System.exit(0);
        return -1;
    }

    public void removePlayer(UserLight user) {
        PlayerController playerController = playerControllerMap.get(user);
        if(playerController == null) {
            //This user doesn't exist
            System.out.println("ERROR: Cet utilisateur a déjà été supprimé.");
            System.exit(0);
            return;
        }
        playerControllerMap.remove(user);
        addLogEntry(user.getPseudo() + " a quitté la partie.");
        controllersList.set(controllersList.indexOf(playerController), null);
        if(isHost && table.getListPlayers().getListUserLights().size() < table.getNbPlayerMin())
            btnLaunchGame.setVisible(false);
        //Si on est au milieu d'une partie, on cache juste le betMoneyBox correspondant à l'utilisateur
        if(betMoneyControllerMap.get(user)!=null)
            betMoneyControllerMap.get(user).hideBetMoneyBox();
        playerController.destroyGraphic();
    }

    public void reorderPlayers(){
        //On vire les sièges vides
        controllersList.removeAll(Collections.singleton(null));
        //On réorganise les sièges équitablement.
        int i=0;
        for(PlayerController playerController : controllersList)
        {
            Point2D coords = TableUtils.getPlayerPosition(i, table.getListPlayers().getListUserLights().size());
            playerController.setPositions(coords);
            i++;
        }
        //On crée les betMoneyBox
        betMoneyBoxInitializer();
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
    private ListView<String> logView;

    @FXML
    private Pane actionBox;
    @FXML
    private Button actionFold;
    @FXML
    private Button actionCheck;
    @FXML
    private Button actionCall;
    @FXML
    private Button actionBet;
    @FXML
    private Button actionAllin;

    @FXML
    private TitledPane popupAmount;
    @FXML
    private Button popupAmountButton;
    @FXML
    private TextField popupAmountInput;


    @FXML
    private TitledPane popupLeave;
    @FXML
    private Button popupLeaveAccept;
    @FXML
    private Button popupLeaveRefuse;

    @FXML
    private TitledPane popupReady;
    @FXML
    private Button popupReadyAccept;
    @FXML
    private Button popupReadyRefuse;

	@FXML
	private void sendMessage(javafx.event.ActionEvent event) {
        if(messageToSend.getText().isEmpty())
            return;

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
    private void launchGame(javafx.event.ActionEvent event){
        btnLaunchGame.setVisible(false);
        //ihmTable.getDataInterface().playGame(table.getIdTable());

    }

    public void addChatMessage(MessageChat message){
        chatList.getItems().add(message.getSender().getPseudo() + " : " + message.getText());
        chatList.setStyle("-fx-graphic:red;");
    }

    public void showActionBox(){
        actionBox.setVisible(true);
    }

    public void hideActionBox(){
        actionBox.setVisible(false);
    }

    public void saveActionToFill(Action actionToFill) { this.actionToFill = actionToFill; }

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

    public void enableActionCall(){
        actionCall.getStyleClass().remove("action_call_off");
        actionCall.getStyleClass().add("active");
        actionCall.getStyleClass().add("action_call_on");
    }

    public void disableActionCall(){
        actionCall.getStyleClass().remove("action_call_on");
        actionCall.getStyleClass().remove("active");
        actionCall.getStyleClass().add("action_call_off");
    }

    public void enableActionBet(){
        actionBet.getStyleClass().remove("action_bet_off");
        actionBet.getStyleClass().add("active");
        actionBet.getStyleClass().add("action_bet_on");
        actionBetMoneySelector.setMin(0);   //TODO : get last raise
        actionBetMoneySelector.setMax(150); //TODO: get allin value (self.money)
        actionBetMoneySelector.setVisible(true);
    }

    public void disableActionBet(){
        actionBet.getStyleClass().remove("action_bet_on");
        actionBet.getStyleClass().remove("active");
        actionBet.getStyleClass().add("action_bet_off");
        actionBetMoneySelector.setVisible(false);
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

    public void enableAction(EnumerationAction action) {
        if(action == EnumerationAction.allIn) enableActionAllin();
        else if(action == EnumerationAction.bet) enableActionBet();
        else if(action == EnumerationAction.check) enableActionCheck();
        else if(action == EnumerationAction.fold) enableActionFold();
        else if(action == EnumerationAction.call) enableActionCall();
    }

    public void disableAllActions() {
        disableActionAllin();
        disableActionBet();
        disableActionCheck();
        disableActionFold();
        disableActionCall();
    }

    @FXML
    public void fold(javafx.event.ActionEvent event) {
        if(actionFold.getStyleClass().contains("active")) {
            //TODO : "quand ces connards auront mis des setters"
            //actionToFill.setAction(EnumerationAction.fold);
            System.out.println("DODO");

        }
    }

    @FXML
    public void check(javafx.event.ActionEvent event) {
        if (actionCheck.getStyleClass().contains("active")) {
            //TODO : "quand ces *** auront mis des setters"
            //actionToFill.setAction(EnumerationAction.check);
            System.out.println("CHECK");

        }
    }

    @FXML
    public void call(javafx.event.ActionEvent event) {
        if (actionCall.getStyleClass().contains("active")) {
            //TODO : "quand ces *** auront mis des setters"
            //actionToFill.setAction(EnumerationAction.call);
            System.out.println("APPEL");

        }
    }

    @FXML
    public void allIn(javafx.event.ActionEvent event) {
        if (actionAllin.getStyleClass().contains("active")) {
            //TODO : "quand ces *** auront mis des setters"
            //actionToFill.setAction(EnumerationAction.allIn);
            System.out.println("ALLIN");

        }
    }

    @FXML
    public void bet(javafx.event.ActionEvent event) {
        if(actionBet.getStyleClass().contains("active")) {
            //TODO : "quand ces *** auront mis des setters"
            //actionToFill.setAction(EnumerationAction.bet);
            //actionToFill.setAmount((int) actionBetMoneySelector.getValue());
        }
    }


    public void addLogEntry(String msg){
        logView.getItems().add(msg);
    }

    public void showPopupAmount(){
        popupAmount.setVisible(true);
    }

    public void hidePopupAmount(){
        popupAmount.setVisible(false);
    }

    public void setPopupAmountMaxMoney(Integer maxAmount){
        popupAmount.setText("Montant Initial (Max: " + maxAmount + "€)");
    }

    @FXML
    public void sendMoneyAmount(javafx.event.ActionEvent event){
        int amount;
        if(popupAmountInput.getText().isEmpty())
            return;
        amount = Integer.parseInt(popupAmountInput.getText());
        if(amount <= 0 || amount > table.getMaxMise())
            return;
        //TODO: Envoyer Integer.parseInt(popupAmountInput.getText()); à data

        hidePopupAmount();
    }

    public void showPopupReady(){
        popupReady.setVisible(true);
    }

    public void hidePopupReady(){
        popupReady.setVisible(false);
    }

    @FXML
    public void sendReadyAccept(javafx.event.ActionEvent event){
        //TODO: Envoyer true à data

        hidePopupReady();
    }

    @FXML
    public void sendReadyRefuse(javafx.event.ActionEvent event){
        //TODO: Envoyer false à data

        hidePopupReady();
    }

    @FXML
    public void showPopupLeave(javafx.event.ActionEvent event) { popupLeave.setVisible(true); }

    @FXML
    public void sendLeaveAccept(javafx.event.ActionEvent event) {
       // TODO :  ihmTable.getDataInterface(). data notify self leave game
        ihmTable.getMainInterface().quitGame();
    }

    @FXML
    public void sendLeaveRefuse(javafx.event.ActionEvent event) { popupLeave.setVisible(false); }

    /**
     * Affiche la réponse d'un utilisateur au vote de fin de jeu en rajoutant une bordure verte (accepte)
     * ou rouge (refuse) autour de la box du player
     * @param player
     * @param accept
     */
    public void notifyPlayerVoteEndGameAnswer(UserLight player,boolean accept) {
        if(accept) {
            addLogEntry(player.getPseudo() + " a accepté de mettre fin au jeu.");
        } else {
            addLogEntry(player.getPseudo() + " a refusé de mettre fin au jeu.");

        }
        getPlayerControllerOf(player).showVoteEndGame(accept);
    }

    /**
     * Arrête la partie en cours en réinitialisant les joueurs et en masquant les actions
     * @param game
     */
    public void stopGame(Game game) {
        for(PlayerController p : controllersList){
            removePlayer(p.getUserLight());
        }
        playerInitializer();
        hideActionBox();
        disableAllActions();
    }
}
