package fr.utc.lo23.client.ihm_table;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MainController {
	
	@FXML
	public Pane IAmYourPaneLuke;
	@FXML
	public Button testbtn;

	public MainController(){
		//constructor
	}
	
	@FXML
	private void launchTable(javafx.event.ActionEvent event) {
		System.out.println("test");
		TableView nex = new TableView();
		nex.createTable(IAmYourPaneLuke);
	}
	
}
