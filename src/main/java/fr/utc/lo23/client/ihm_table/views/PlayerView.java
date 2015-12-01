package fr.utc.lo23.client.ihm_table.views;

import fr.utc.lo23.common.data.UserLight;
import fr.utc.lo23.client.ihm_table.IHMTable;
import fr.utc.lo23.client.ihm_table.controllers.PlayerController;
import fr.utc.lo23.client.ihm_table.controllers.TableController;
import fr.utc.lo23.common.data.Table;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.io.IOException;

/*
 * Created by Zangdar on 26/11/2015.
 */
public class PlayerView {

    private Node node;

    public PlayerController createPlayer(Pane root,UserLight user,Point2D coords,Image defaultImage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/PlayerBox.fxml"));
            node = (Node) loader.load();
            root.getChildren().add(node);
            PlayerController playerController = (PlayerController) loader.getController();
            playerController.setNameAndAvatar(user,defaultImage);
            playerController.setPositions(coords);
            playerController.setNodes(root,node);
            return playerController;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Node getNode() {
        return node;
    }
}