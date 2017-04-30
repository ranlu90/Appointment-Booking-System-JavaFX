package customerController;

import java.net.URL;

import java.util.ResourceBundle;

import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import systemController.ViewController;

/**
 * Customer menu.
 * @author ranlu
 *
 */
public class CustomerMenuController implements Initializable{

	private ViewController viewController;
	private DatabaseManager databaseManager;
	private	String user;

	@FXML
	private Text welcomeMessage;

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
		welcomeMessage.setText("Welcome! " + databaseManager.getCustomerName(user));
	}

	@FXML
	private void CreateBooking(){
		viewController.gotoCustomerCreateBooking();
	}

	@FXML
	private void viewBookingAvailability(){
		viewController.gotoViewBookingAvailability();
	}

	@FXML
	private void logout(){
		viewController.gotoLogin();
	}
}
