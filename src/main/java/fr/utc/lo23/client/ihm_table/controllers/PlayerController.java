package fr.utc.lo23.client.ihm_table.controllers;

import fr.utc.lo23.common.data.UserLight;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    public PlayerController(){

    }

    public void initialize() {

    }

    public void updateMoney(){

    }

    public void setAvatar(){

    }

    public void setNameAndAvatar(UserLight user,Image defaultImage){
        playerNameLabel.setText(user.getPseudo());
        avatarImageView.setImage(defaultImage);
    }

    public void setPositions(Point2D coords){
        playerBox.setLayoutX(coords.getX()-40);
        playerBox.setLayoutY(coords.getY()-35);
    }
}
