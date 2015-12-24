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

    private Node node;
    private Pane root;

    private ImageView card1;
    private ImageView card2;

    private Integer betMoney;

    private UserLight userLight;

    private Integer currentMoney;

    public Integer getCurrentMoney() {
        return currentMoney;
    }

    public UserLight getUserLight() {
        return userLight;
    }

    public void setUserLight(UserLight userLight) {
        this.userLight = userLight;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setNodes(Pane root,Node node) {
        this.root = root;
        this.node = node;
    }

    public Integer getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(Integer betMoney) {
        this.betMoney = betMoney;
    }

    public ImageView getCard1() {
        return card1;
    }

    public void setCard1(ImageView card1) {
        this.card1 = card1;
    }

    public ImageView getCard2() {
        return card2;
    }

    public void setCard2(ImageView card2) {
        this.card2 = card2;
    }

    public Pane getRoot() {
        return root;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }

    public void destroyGraphic(){
        root.getChildren().remove(node);
    }

    public PlayerController(){

    }

    public void initialize() {
        betMoney = 0;
    }

    public void updateMoney(Integer money){
        this.currentMoney = money;
        updateMoney();
    }

    public void updateMoney() {
        if(currentMoney==-1)
            playerMoneyLabel.setText("?????");
        else
            playerMoneyLabel.setText(currentMoney.toString()+" $");
    }

    public void decreaseMoney(int amount) {
        currentMoney -= amount;
        updateMoney();
    }

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

    public void setPositions(Point2D coords){
        playerBox.setLayoutX(coords.getX() - 40);
        playerBox.setLayoutY(coords.getY() - 55);
    }

    public void setCheckStatus(){
        playerInfos.setVisible(true);
        playerInfos.setText("Check");
    }

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

    public void setThinkingStatus(){
        playerInfos.setVisible(true);
        playerInfos.setText("Réfléchit...");
        if(!playerInfos.getStyleClass().contains("money"))
            playerInfos.getStyleClass().add("money");
    }
    
    public void setYouAreThinkingStatus(){
        playerInfos.setVisible(true);
        playerInfos.setText("Votre tour...");
    }

    public void setBetMoneyAmount(Integer amount){
        playerInfos.setVisible(true);
        betMoney = amount;
        if(!playerInfos.getStyleClass().contains("money"))
            playerInfos.getStyleClass().add("money");
        if(amount==-1)
            playerInfos.setText("Couché");
        else
        {
            playerInfos.setText(amount.toString()+" $");
        }
    }

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
