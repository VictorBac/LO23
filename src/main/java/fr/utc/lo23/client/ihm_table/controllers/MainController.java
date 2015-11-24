package fr.utc.lo23.client.ihm_table.controllers;

import fr.utc.lo23.client.ihm_table.IHMTable;
import fr.utc.lo23.common.data.Game;
import fr.utc.lo23.common.data.Table;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class MainController {

	IHMTable table = new IHMTable();

	@FXML
	public Pane IAmYourPaneLuke;

	public MainController(){
		//constructor
	}
	
	@FXML
	private void launchTable(javafx.event.ActionEvent event) {
		table.getTableToMainListener().showTableCreationForm(IAmYourPaneLuke);
	}

	@FXML
	private void joinTable(javafx.event.ActionEvent event) {
		table.getTableToMainListener().joinTable(IAmYourPaneLuke,
				new Table("",true,true,5,10,true,100,30));
    }
	
}
