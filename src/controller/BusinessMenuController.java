package controller;

import java.net.URL;
import java.util.ResourceBundle;

import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class BusinessMenuController implements Initializable {

	private ViewController viewController;
	private DatabaseManager databaseManager;
	private	String user;

	@FXML
	private TextField welcomeMessage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void initViewController(ViewController viewController) {
		this.viewController = viewController;

	}

	public void initDatabaseManager(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}

	public void setUsername(String username){
		user = username;
	}

	@FXML
	public void welcomeMessage(){
		welcomeMessage.setText("Welcome! " + databaseManager.getBusinessName(user));
	}

	@FXML
	private void addNewService(){
		viewController.gotoService();
	}

	@FXML
	private void updateEmployee(){
		viewController.gotoEmployee();
	}

	@FXML
	private void changeBusinessHours(){
		viewController.gotoBusinessHours();
	}

	@FXML
	private void viewAllBookings(){
		viewController.gotoAllBookings();
	}

	@FXML
	private void viewNewBookings(){
		viewController.gotoNewBookings();
	}

	@FXML
	private void createABooking(){
		viewController.gotoCreateBookings();
	}

	@FXML
	private void viewAvailability(){
		viewController.gotoViewWorkers();
	}
}
