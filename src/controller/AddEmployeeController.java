package controller;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AddEmployeeController implements Initializable{

	private ViewController viewController;
	private DatabaseManager databaseManager;
	private String user;
	private boolean check = false;

	@FXML
	private ComboBox<String> employeeList,open1,open2,open3,open4,open5,open6,open7,close1,close2,close3,close4,close5,close6,close7;

	@FXML
	private TextField firstname, lastname, email, contact;
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
	public void initTimeUnit(){
		ArrayList<String> temp = new ArrayList<String>();
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

		ArrayList<ArrayList<String>> list = databaseManager.getEmployee(user);
		ArrayList<String> temp1 = new ArrayList<String>();
		for(ArrayList<String> employee: list){
			String name = employee.get(0) + " " + employee.get(1);
			temp1.add(name);
		}
		employeeList.getItems().addAll(temp1);
	}

	@FXML
	private void Confirm() throws ParseException{
		Alert alert;

		if(firstname.getText().trim().isEmpty() == false && lastname.getText().trim().isEmpty() == false &&
				email.getText().trim().isEmpty() == false && contact.getText().trim().isEmpty() == false){
			if(databaseManager.searchEmployeeEmail(email.getText()) == false){
				if(firstname.getText().matches("[a-zA-Z]+([ ]?[a-zA-Z]*){1,2}") && lastname.getText().matches("[a-zA-Z]+([ ]?[a-zA-Z]*){1,2}") &&
						email.getText().matches("([0-9a-zA-Z._-]+)@((?:[0-9a-zA-Z]+.)+)([a-zA-Z]{2,4})") && contact.getText().matches("([0-9+]*[ ()]*[0-9]*[ ()]*[0-9]*[ -]*[0-9]+)")){
					databaseManager.setEmployee(firstname.getText(), lastname.getText(), user, email.getText(), contact.getText());
					alert = new Alert(AlertType.INFORMATION,"A new employee's information has been created!");
					alert.showAndWait();
				}
				else{
					alert = new Alert(AlertType.ERROR,"First name can only contain letters and one space." + System.lineSeparator()
							+ "Last name can only contain letters and one space." + System.lineSeparator()
							+ "Email address format is invalid. " + System.lineSeparator()
							+ "Contact number can only contain digits, space and + ( ) ");
					alert.showAndWait();
				}
			}
			addTime("Monday", open1.getValue(), close1.getValue(),email.getText());
			addTime("Tuesday", open2.getValue(), close2.getValue(),email.getText());
			addTime("Wednesday", open3.getValue(), close3.getValue(),email.getText());
			addTime("Thursday", open4.getValue(), close4.getValue(),email.getText());
			addTime("Friday", open5.getValue(), close5.getValue(),email.getText());
			addTime("Saturday", open6.getValue(), close6.getValue(),email.getText());
			addTime("Sunday", open7.getValue(), close7.getValue(),email.getText());
		}
		if(check == true){
			alert = new Alert(AlertType.INFORMATION,"New working time has been updated.");
			alert.showAndWait();
		}
		viewController.gotoBusinessMenu();
	}


	/**
	 * This method check if selected working time is within the business owner's business hours.
	 * Return true if the time period for working time can be added.
	 */
	public boolean CheckTime(String day, String startTime, String endTime){

		ArrayList<ArrayList<String>> businessHours = databaseManager.getBusinessTime(user);
		HashMap<Integer, Boolean> timeSlot = new HashMap<Integer,Boolean>();
		for(int i = 1; i <= 48; i ++){
			timeSlot.put(i, false);
		}

		//add time slots by owner's business hours divided by 30
		for(ArrayList<String> temp:businessHours){
			if(temp.get(0).compareToIgnoreCase(day) == 0){
				String[] str = temp.get(1).split(":");			//business open time
				String[] str2 = temp.get(2).split(":");			//business closing time
				int slot1 = ((Integer.parseInt(str[0]) * 60) + Integer.parseInt(str[1])) / 30 + 1;
				int slot2 = ((Integer.parseInt(str2[0]) * 60) + Integer.parseInt(str2[1])) / 30;
				for(int i = slot1; i <= slot2; i ++){
					timeSlot.put(i, true);
				}
			}
		}

		//check if selected time slots are within business hours
		String[] str3 = startTime.split(":");			//work starts
		String[] str4 = endTime.split(":");				//work ends
 		int slot3 = ((Integer.parseInt(str3[0]) * 60) + Integer.parseInt(str3[1])) / 30 + 1;
		int slot4 = ((Integer.parseInt(str4[0]) * 60) + Integer.parseInt(str4[1])) / 30;
		for(int i = slot3; i <= slot4; i ++){
			if(timeSlot.get(i) == false){			//the time slot has been occupied
				return false;
			}
		}
		return true;
	}


	/**
	 * If a new employee has been created, insert all selected working time. IF an existing employee has been selected, insert or update
	 * working time.
	 * @param email Employee's email
	 */
	public void addTime(String day, String startTime, String endTime, String email) throws ParseException{
		Alert alert;
		SimpleDateFormat tf = new SimpleDateFormat("HH:mm");

		if(startTime != null && endTime != null){
			if(tf.parse(startTime).compareTo(tf.parse(endTime)) < 0 ){
				if(databaseManager.searchWorkingTime(day, startTime, endTime, email) == false){
					if(CheckTime(day,startTime,endTime) == true){
						databaseManager.setWorkingTime(day, startTime, endTime,email);
						check = true;
					}
					else{
						alert = new Alert(AlertType.ERROR,"Working time has to been within business hours!");
						alert.showAndWait();
						viewController.gotoBusinessMenu();
					}
				}
				else{
					alert = new Alert(AlertType.ERROR,"A same working time already exists!");
					alert.showAndWait();
					viewController.gotoBusinessMenu();
				}
			}
			else{
				alert = new Alert(AlertType.ERROR,"Start time need to be earlier than end time!");
				alert.showAndWait();
				viewController.gotoBusinessMenu();
			}
		}
	}


	@FXML
	private void MainMenu(){
		viewController.gotoBusinessMenu();
	}

	@FXML
	private void setText(){
		String[] name = employeeList.getValue().split(" ");
		String employee_email = databaseManager.searchEmployeeEmailByName(name[0], name[1]);
		ArrayList<String> info = databaseManager.searchOneEmployee(employee_email);
		firstname.setText(info.get(0));
		lastname.setText(info.get(1));
		email.setText(employee_email);
		contact.setText(info.get(2));
		firstname.setEditable(false);
		lastname.setEditable(false);
		email.setEditable(false);
		contact.setEditable(false);
	}
}
