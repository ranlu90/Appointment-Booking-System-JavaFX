package controller;

import java.net.URL;
import java.util.ResourceBundle;

import database.DatabaseManager;
import javafx.fxml.Initializable;

public class OwnerRegisterController implements Initializable{
	@SuppressWarnings("unused")
	private ViewController viewController;
	@SuppressWarnings("unused")
	private DatabaseManager databaseManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void initViewController(ViewController viewController) {
		this.viewController = viewController;

	}

	public void initDatabaseManager(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}

}
