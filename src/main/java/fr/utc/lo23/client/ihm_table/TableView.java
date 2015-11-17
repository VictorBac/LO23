package fr.utc.lo23.client.ihm_table;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TableView{
	

	public void createTable(Pane root) {
		try {
			root.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource("tableForm.fxml")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
