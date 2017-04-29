package controller;

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

public class BusinessHoursController implements Initializable{

	private ViewController viewController;
	private DatabaseManager databaseManager;
	private String user;

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
		SimpleDateFormat tf = new SimpleDateFormat("HH:mm");

		if(open1.getValue() != null && close1.getValue() != null){
			if(tf.parse(open1.getValue()).compareTo(tf.parse(close1.getValue())) < 0 ){
				if(databaseManager.searchBusinessHours("Monday", user) == true){
					databaseManager.updateBusinessHours("Monday", user, open1.getValue(), close1.getValue());
				}
				else
					databaseManager.setBusinessTime("Monday", user, open1.getValue(), close1.getValue());
			}
			else{
				alert = new Alert(AlertType.ERROR,"Open time need to be earlier than closing time!");
				alert.showAndWait();
				viewController.gotoBusinessMenu();
			}
		}

		if(open2.getValue() != null && close2.getValue() != null){
			if(tf.parse(open2.getValue()).compareTo(tf.parse(close2.getValue())) < 0 ){
				if(databaseManager.searchBusinessHours("Tuesday", user) == true){
					databaseManager.updateBusinessHours("Tuesday", user, open2.getValue(), close2.getValue());

				}
				else
					databaseManager.setBusinessTime("Tuesday", user, open2.getValue(), close2.getValue());
			}
			else{
				alert = new Alert(AlertType.ERROR,"Open time need to be earlier than closing time!");
				alert.showAndWait();
				viewController.gotoBusinessMenu();
			}
		}

		if(open3.getValue() != null && close3.getValue() != null){
			if(tf.parse(open3.getValue()).compareTo(tf.parse(close3.getValue())) < 0 ){
				if(databaseManager.searchBusinessHours("Wednesday", user) == true){
					databaseManager.updateBusinessHours("Wednesday", user, open3.getValue(), close3.getValue());
				}
				else
					databaseManager.setBusinessTime("Wednesday", user, open3.getValue(), close3.getValue());
			}
			else{
				alert = new Alert(AlertType.ERROR,"Open time need to be earlier than closing time!");
				alert.showAndWait();
				viewController.gotoBusinessMenu();
			}
		}
		if(open4.getValue() != null && close4.getValue() != null){
			if(tf.parse(open4.getValue()).compareTo(tf.parse(close4.getValue())) < 0 ){
				if(databaseManager.searchBusinessHours("Thursday", user) == true){
					databaseManager.updateBusinessHours("Thursday", user, open4.getValue(), close4.getValue());
				}
				else
					databaseManager.setBusinessTime("Thursday", user, open4.getValue(), close4.getValue());
			}
			else{
				alert = new Alert(AlertType.ERROR,"Open time need to be earlier than closing time!");
				alert.showAndWait();
				viewController.gotoBusinessMenu();
			}
		}
		if(open5.getValue() != null && close5.getValue() != null){
			if(tf.parse(open5.getValue()).compareTo(tf.parse(close5.getValue())) < 0 ){
				if(databaseManager.searchBusinessHours("Friday", user) == true){
					databaseManager.updateBusinessHours("Friday", user, open5.getValue(), close5.getValue());
				}
				else
					databaseManager.setBusinessTime("Friday", user, open5.getValue(), close5.getValue());
			}
			else{
				alert = new Alert(AlertType.ERROR,"Open time need to be earlier than closing time!");
				alert.showAndWait();
				viewController.gotoBusinessMenu();
			}
		}
		if(open6.getValue() != null && close6.getValue() != null){
			if(tf.parse(open6.getValue()).compareTo(tf.parse(close6.getValue())) < 0 ){
				if(databaseManager.searchBusinessHours("Saturday", user) == true){
					databaseManager.updateBusinessHours("Saturday", user, open6.getValue(), close6.getValue());
				}
				else
					databaseManager.setBusinessTime("Saturday", user, open6.getValue(), close6.getValue());
			}
			else{
				alert = new Alert(AlertType.ERROR,"Open time need to be earlier than closing time!");
				alert.showAndWait();
				viewController.gotoBusinessMenu();
			}
		}
		if(open7.getValue() != null && close7.getValue() != null){
			if(tf.parse(open7.getValue()).compareTo(tf.parse(close7.getValue())) < 0 ){
				if(databaseManager.searchBusinessHours("Sunday", user) == true){
					databaseManager.updateBusinessHours("Sunday", user, open7.getValue(), close7.getValue());
				}
				else
					databaseManager.setBusinessTime("Sunday", user, open7.getValue(), close7.getValue());
			}
			else{
				alert = new Alert(AlertType.ERROR,"Open time need to be earlier than closing time!");
				alert.showAndWait();
				viewController.gotoBusinessMenu();
			}
		}
		viewController.gotoBusinessMenu();
	}


	@FXML
	private void MainMenu(){
		viewController.gotoBusinessMenu();
	}
}
