package controller;

import java.net.URL;
import java.util.ResourceBundle;

import database.DatabaseManager;
import javafx.fxml.Initializable;

public class BookingAvailabilityController implements Initializable{
	private ViewController viewController;
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
