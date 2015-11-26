package fr.utc.lo23.client.ihm_table.views;

import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.client.ihm_table.IHMTable;
import fr.utc.lo23.client.ihm_table.controllers.PlayerController;
import fr.utc.lo23.client.ihm_table.controllers.TableController;
import fr.utc.lo23.common.data.Table;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Created by Zangdar on 26/11/2015.
 */
public class PlayerView {

    public PlayerController createPlayer(Pane root,UserLight user,Point2D coords) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/PlayerBox.fxml"));
            root.getChildren().add((Node) loader.load());
            PlayerController playerController = (PlayerController) loader.getController();
            playerController.setName(user);
            playerController.setPositions(coords);



            return playerController;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}