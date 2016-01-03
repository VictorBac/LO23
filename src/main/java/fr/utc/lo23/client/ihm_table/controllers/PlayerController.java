package fr.utc.lo23.client.ihm_table.controllers;

import fr.utc.lo23.common.data.UserLight;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by Bastien F. on 24/11/15.
 */
public class PlayerController{
    @FXML
    public Label playerNameLabel;
    @FXML
    public ImageView avatarImageView;
    @FXML
    public Label playerMoneyLabel;
    @FXML
    public VBox playerBox;
    @FXML
    public Label playerInfos;

    /**
     * View data associated to player
     */
    private Node node;
    private Pane root;

    /**
     * Player cards
     */
    private ImageView card1;
    private ImageView card2;

    /**
     * Money bet by player during current turn
     */
    private Integer betMoney;

    /**
     * Userlight associated to player
     */
    private UserLight userLight;

    /**
     * Current money of player
     */
    private Integer currentMoney;

    /**
     * Getter for @currentMoney
     * @return
     */
    public Integer getCurrentMoney() {
        return currentMoney;
    }

    /**
     * Getter for @userLight
     * @return
     */
    public UserLight getUserLight() {
        return userLight;
    }

    /**
     * Setter for @userLight
     * @param userLight
     */
    public void setUserLight(UserLight userLight) {
        this.userLight = userLight;
    }

    /**
     * Getter for @node
     * @return
     */
    public Node getNode() {
        return node;
    }

    /**
     * Setter for @node
     * @param node
     */
    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * Used to define Pane and Node associated to table
     * @param root
     * @param node
     */
    public void setNodes(Pane root,Node node) {
        this.root = root;
        this.node = node;
    }

    /**
     * Getter for betMoney
     * @return
     */
    public Integer getBetMoney() {
        return betMoney;
    }

    /**
     * Setter for betMoney
     * @param betMoney
     */
    public void setBetMoney(Integer betMoney) {
        this.betMoney = betMoney;
    }

    /**
     * Getter for card1
     * @return
     */
    public ImageView getCard1() {
        return card1;
    }

    /**
     * Setter for card1
     * @param card1
     */
    public void setCard1(ImageView card1) {
        this.card1 = card1;
    }

    /**
     * Getter for card2
     * @return
     */
    public ImageView getCard2() {
        return card2;
    }

    /**
     * Setter for card2
     * @param card2
     */
    public void setCard2(ImageView card2) {
        this.card2 = card2;
    }

    /**
     * Getter for root
     * @return
     */
    public Pane getRoot() {
        return root;
    }

    /**
     * Setter for root
     * @param root
     */
    public void setRoot(Pane root) {
        this.root = root;
    }

    /**
     * Called to remove player from table
     */
    public void destroyGraphic(){
        root.getChildren().remove(node);
    }

    public PlayerController(){

    }

    /**
     * Called when controller is created
     */
    public void initialize() {
        betMoney = 0;
    }

    /**
     * Update player money amount
     * @param money
     */
    public void updateMoney(Integer money){
        this.currentMoney = money;
        updateMoney();
    }

    /**
     * Set money value
     */
    public void updateMoney() {
        if(currentMoney==-1)
            playerMoneyLabel.setText("?????");
        else
            playerMoneyLabel.setText(currentMoney.toString()+" $");
    }

    /**
     * Decrease player money by @amount
     * @param amount
     */
    public void decreaseMoney(int amount) {
        currentMoney -= amount;
        updateMoney();
    }

    /**
     * Define name and avatar for player
     * @param user
     * @param defaultImage
     */
    public void setNameAndAvatar(UserLight user,Image defaultImage){
        playerNameLabel.setText(user.getPseudo());
            if(user.getAvatar()!=null) {
                try {
                    avatarImageView.setImage(user.getAvatar().getImageAvatar());
                } catch (IOException e) {
                    avatarImageView.setImage(defaultImage);
                }
            }
            else
                avatarImageView.setImage(defaultImage);
        setUserLight(user);
    }

    /**
     * Define position of player on table (View)
     * @param coords
     */
    public void setPositions(Point2D coords){
        playerBox.setLayoutX(coords.getX() - 40);
        playerBox.setLayoutY(coords.getY() - 55);
    }

    /**
     * Define player status as Check
     */
    public void setCheckStatus(){
        playerInfos.setVisible(true);
        playerInfos.setText("Check");
    }

    /**
     * Define player status as Ready/Refuse
     */
    public void setReadyStatus(boolean isReady){
        playerInfos.setVisible(true);
        if(isReady) {
            playerInfos.setText("Prêt");
            playerInfos.getStyleClass().add("ready");
        }
        else {
            playerInfos.setText("Refus");
            playerInfos.getStyleClass().add("refuse");
        }
    }

    /**
     * Define player status as Thinking
     */
    public void setThinkingStatus(){
        playerInfos.setVisible(true);
        playerInfos.setText("Réfléchit...");
        if(!playerInfos.getStyleClass().contains("money"))
            playerInfos.getStyleClass().add("money");
    }

    /**
     * Define player status as You Are Thinking
     */
    public void setYouAreThinkingStatus(){
        playerInfos.setVisible(true);
        if(!playerInfos.getStyleClass().contains("money"))
            playerInfos.getStyleClass().add("money");
        playerInfos.setText("Votre tour...");
    }

    /**
     * Define player bet money amount
     * @param amount
     */
    public void setBetMoneyAmount(Integer amount){
        playerInfos.setVisible(true);
        betMoney = amount;
        if(!playerInfos.getStyleClass().contains("money"))
            playerInfos.getStyleClass().add("money");
        if(amount==-1) {
            playerInfos.setText("Couché");
        }
        else
        {
            playerInfos.setText(amount.toString()+" $");
        }
    }

    /**
     * Add amount to betMoney
     * @param amount
     */
    public void addBetMoneyAmount(int amount) {
        playerInfos.setVisible(true);
        betMoney += amount;
        if(!playerInfos.getStyleClass().contains("money"))
            playerInfos.getStyleClass().add("money");
        if(amount==-1)
            playerInfos.setText("Couché");
        else
        {
            playerInfos.setText(amount +" $");
        }
    }

    /**
     * Return true if status is ready, false otherwise
     * @return
     */
    public Boolean isReadyStatus(){
        if(playerInfos.getStyleClass().contains("refuse") || playerInfos.getStyleClass().contains("ready"))
            return true;
        else
            return false;
    }

    /**
     * Reset player status
     */
    public void clearReadyStatus(){
        playerInfos.setVisible(false);
        playerInfos.getStyleClass().remove("refuse");
        playerInfos.getStyleClass().remove("ready");
        playerInfos.getStyleClass().remove("money");
        playerInfos.setText("");
    }

    /**
     * Modifie la bordure de l'avatar du joueur selon son vote pour mettre fin à une partie
     * Si accept = true, la bordure est verte
     * Sinon, elle est rouge
     * @param accept
     */
    public void showVoteEndGame(boolean accept) {
        if(accept) {
            playerBox.setStyle("-fx-border-color: green ; -fx-border-width: 3px ;");
        } else {
            playerBox.setStyle("-fx-border-color: red ; -fx-border-width: 3px ;");
        }
    }

    /**
     * Réinitialise le style de la bordure de playerbox
     */
    public void cleanPlayerBox(){
        playerBox.setStyle("-fx-border-color: black ; -fx-border-width: 1px ;");
    }


}
