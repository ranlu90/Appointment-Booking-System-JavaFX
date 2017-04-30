package businessController;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import systemController.ViewController;

/**
 * Create or update business hours for one business owner.
 * @author ranlu
 *
 */
public class OwnerBusinessHoursController implements Initializable{

	private ViewController viewController;
	private DatabaseManager databaseManager;
	private String user;
	private boolean check = false;

	@FXML
	private ComboBox<String> open1,open2,open3,open4,open5,open6,open7,close1,close2,close3,close4,close5,close6,close7;

	@FXML
	private Text businessHours;

	@FXML
	public void initBusinessHours(){
		String f = "";					//contain all business hours information
		ArrayList<String> temp = new ArrayList<String>();
		ArrayList<ArrayList<String>> business = databaseManager.getBusinessTime(user);
		for(int i = 0; i <= 23; i ++){
			temp.add(Integer.toString(i) +":" + "00");
			temp.add(Integer.toString(i) +":" + "30");
		}
		open1.getItems().addAll(temp);
		open2.getItems().addAll(temp);
		open3.getItems().addAll(temp);
		open4.getItems().addAll(temp);
		open5.getItems().addAll(temp);
		open6.getItems().addAll(temp);
		open7.getItems().addAll(temp);
		close1.getItems().addAll(temp);
		close2.getItems().addAll(temp);
		close3.getItems().addAll(temp);
		close4.getItems().addAll(temp);
		close5.getItems().addAll(temp);
		close6.getItems().addAll(temp);
		close7.getItems().addAll(temp);
		for(ArrayList<String> oneDay: business){
			f += oneDay.get(0) + " " + oneDay.get(1) + "-" + oneDay.get(2) + System.lineSeparator();
		}
		businessHours.setText(f);
	}

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

	@FXML
	private void Confirm() throws ParseException{
		Alert alert;
		addBusinessHours("Monday",open1.getValue(),close1.getValue());
		addBusinessHours("Tuesday",open2.getValue(),close2.getValue());
		addBusinessHours("Wednesday",open3.getValue(),close3.getValue());
		addBusinessHours("Thursday",open4.getValue(),close4.getValue());
		addBusinessHours("Friday",open5.getValue(),close5.getValue());
		addBusinessHours("Saturday",open6.getValue(),close6.getValue());
		addBusinessHours("Sunday",open7.getValue(),close7.getValue());
		if(check == true){
			alert = new Alert(AlertType.INFORMATION,"New business hours has been updated.");
			alert.showAndWait();
		}
		viewController.gotoBusinessMenu();
	}


	@FXML
	private void MainMenu(){
		viewController.gotoBusinessMenu();
	}

	public void addBusinessHours(String day, String open_time, String closing_time) throws ParseException{
		Alert alert;
		SimpleDateFormat tf = new SimpleDateFormat("HH:mm");

		if(open_time != null && closing_time != null){
			if(tf.parse(open_time).compareTo(tf.parse(closing_time)) < 0 ){
				if(databaseManager.searchBusinessHours(day, user) == true){
					databaseManager.updateBusinessHours(day, user, open_time, closing_time);
				}
				else{
					databaseManager.setBusinessTime(day, user, open_time, closing_time);
				}
				check = true;
			}
			else{
				alert = new Alert(AlertType.ERROR,"Open time need to be earlier than closing time!");
				alert.showAndWait();
				viewController.gotoBusinessMenu();
			}
		}
	}
}
