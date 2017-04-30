package businessController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import systemController.ViewController;

/**
 * This class contains functions for adding a new service.
 * @author ranlu
 *
 */
public class OwnerAddServiceController implements Initializable{

	private ViewController viewController;
	private DatabaseManager databaseManager;
	private String user;

	@FXML
	private TextField name;

	@FXML
	private ComboBox<String> duration;

	@FXML
	private TextArea description;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void initViewController(ViewController viewController) {
		this.viewController = viewController;
	}

	public void initDatabaseManager(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}

	public void setUsername(String username) {
		user = username;
	}


	/**
	 * Initiate drop down menu for duration for a business owner.
	 */
	@FXML
	public void initDuration(){
		ArrayList<String> durationList = new ArrayList<String>();
		durationList.add("30");
		durationList.add("60");
		durationList.add("90");
		durationList.add("120");
		durationList.add("150");
		durationList.add("180");
		durationList.add("210");
		durationList.add("240");
		durationList.add("270");
		durationList.add("300");

		duration.getItems().addAll(durationList);
	}

	@FXML
	private void Confirm(){
		Alert alert;
		if(name.getText().trim().isEmpty() == false && duration.getValue() != null){
			if(databaseManager.searchService(name.getText(), duration.getValue(), user) == false){
				databaseManager.addService(name.getText(), duration.getValue(), user, description.getText());
				alert = new Alert(AlertType.INFORMATION,"A new service has been created!");
				alert.showAndWait();
				viewController.gotoBusinessMenu();
			}
			else{
				alert = new Alert(AlertType.ERROR,"A service with the same name and duration has already existed!");
				alert.showAndWait();
			}
		}
		else{
			alert = new Alert(AlertType.ERROR,"Name and duration must be filled in!");
			alert.showAndWait();
		}
	}

	@FXML
	private void MainMenu(){
		viewController.gotoBusinessMenu();
	}
}
