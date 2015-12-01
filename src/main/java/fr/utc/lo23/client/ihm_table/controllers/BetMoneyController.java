package fr.utc.lo23.client.ihm_table.controllers;

import fr.utc.lo23.common.data.UserLight;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Created by Zangdar on 30/11/2015.
 */
public class BetMoneyController {
    @FXML
    public Label betMoneyAmount;
    @FXML
    public VBox betMoneyBox;

    public BetMoneyController(){

    }

    public void initialize() {

    }

    public void updateMoney(Integer amount){
        betMoneyAmount.setText(amount.toString());
    }

    public void clearMoney(){
        betMoneyAmount.setText("");
    }

    public void setPositions(Point2D coords){
        betMoneyBox.setLayoutX(coords.getX()-35);
        betMoneyBox.setLayoutY(coords.getY()-22.5);
    }

    public void showBetMoneyBox(){
        betMoneyBox.setVisible(true);
    }

    public void hideBetMoneyBox(){
        betMoneyBox.setVisible(false);
    }
}
