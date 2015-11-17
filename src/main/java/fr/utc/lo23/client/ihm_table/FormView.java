package fr.utc.lo23.client.ihm_table;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * Load the create table form fxml file
 */
public class FormView {
    public void createTable(Pane root) {
        try {
            root.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("tableForm.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
