package fr.utc.lo23.client.ihm_table.views;

import fr.utc.lo23.client.ihm_table.controllers.BetMoneyController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by Zangdar on 30/11/2015.
 */
public class BetMoneyView {

    private Node node;

    public BetMoneyController createBetMoneyBox(Pane root,Point2D coords) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/BetMoneyPlayerBox.fxml"));
            node = (Node) loader.load();
            root.getChildren().add(node);
            BetMoneyController betMoneyController = (BetMoneyController) loader.getController();
            betMoneyController.setPositions(coords);

            return betMoneyController;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Node getNode() {
        return node;
    }

}
