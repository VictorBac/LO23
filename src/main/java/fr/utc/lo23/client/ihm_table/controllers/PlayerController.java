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

    private UserLight userLight;

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

    }

    public void updateMoney(Integer money){
        playerMoneyLabel.setText(money.toString());
    }

    public void setNameAndAvatar(UserLight user,Image defaultImage){
        playerNameLabel.setText(user.getPseudo());
        avatarImageView.setImage(defaultImage);
        setUserLight(user);
    }

    public void setPositions(Point2D coords){
        playerBox.setLayoutX(coords.getX()-40);
        playerBox.setLayoutY(coords.getY()-55);
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

    public void clearReadyStatus(){
        playerInfos.setVisible(false);
        playerInfos.getStyleClass().remove("refuse");
        playerInfos.getStyleClass().remove("ready");
        playerInfos.setText("");
    }
}
