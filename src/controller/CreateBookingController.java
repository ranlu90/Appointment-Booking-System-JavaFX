package controller;

import java.net.URL;


import java.util.ArrayList;
import java.util.ResourceBundle;
import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CreateBookingController implements Initializable{

	private ViewController viewController;
	private DatabaseManager databaseManager;
	private String user;

	@FXML
	ComboBox<String> employee,service,hour,minute;

	@FXML
	TextField firstname, lastname;

	@FXML
	Button confirm, mainMenu;

	@FXML
	DatePicker date;

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
	 * Populate employee list for a specific business owner by using owner's username.
	 */
	@FXML
	public void getEmployee(){
		ArrayList<ArrayList<String>> employeelist = databaseManager.getEmployee(user);
		ArrayList<String> temp = new ArrayList<String>();
		for(ArrayList<String> employee: employeelist){
			String name = employee.get(0) + " " + employee.get(1);
			temp.add(name);
		}
		employee.getItems().addAll(temp);
	}


	/**
	 * Populate employee list for a specific business owner by using owner's username.
	 */
	@FXML
	public void getService(){
		ArrayList<ArrayList<String>> list = databaseManager.getService(user);
		ArrayList<String> temp = new ArrayList<String>();
		for(ArrayList<String> service: list){
			String name = service.get(0);
			temp.add(name);
		}
		service.getItems().addAll(temp);
	}


	/**
	 * Set drop down list for hours.
	 */
	@FXML
	public void SetHour(){
		ArrayList<String> hours = new ArrayList<String>();
		for(int i = 0; i <= 23; i ++){
			hours.add(Integer.toString(i));
		}
		hour.getItems().addAll(hours);
	}


	/**
	 * Set drop down list for minutes.
	 */
	@FXML
	public void SetMinute(){
		ArrayList<String> minutes = new ArrayList<String>();
		for(int i = 0; i <= 9; i ++){
			minutes.add("0" + Integer.toString(i));
		}
		for(int i = 10; i <= 59; i ++){
			minutes.add(Integer.toString(i));
		}
		minute.getItems().addAll(minutes);
	}


	/**
	 * 1. Check if the employee works on selected date.
	 * 2. Check if a same booking already exists.
	 * 3. Check if the employee is available at that time.
	 * If the employee works on one day but doesn't have time(already have customer's booking), inform owner the message.
	 */
	@FXML
	private void Confirm(){
		ArrayList<String> dayList = new ArrayList<String>();
		String workingDay = "";
		boolean dayCheck = false;		//check if the day matches employee's working day
		Alert alert;

		//add duration of the service to the start time of booking and create a new end time
		int duration = Integer.parseInt(databaseManager.getDuration(service.getValue()));
		int totalMinutes = (Integer.parseInt(hour.getValue()) * 60) + Integer.parseInt(minute.getValue()) + duration;
		int hours = totalMinutes / 60;
		int minutes = totalMinutes % 60;
		String start_time = hour.getValue() + ":" + minute.getValue();
		String end_time = Integer.toString(hours) + ":" + Integer.toString(minutes);

		//split employee's full name by whitespace and check his email in the database by firstname and lastname
		String[] name = employee.getValue().split(" ");
		String employee_email = databaseManager.searchEmployeeEmailByName(name[0], name[1]);
		String customer_ID = databaseManager.searchCustomerID(firstname.getText(), lastname.getText());

		ArrayList<ArrayList<String>> workingTime = databaseManager.getWorkingTime(employee_email);

		for(ArrayList<String> temp : workingTime){
			dayList.add(temp.get(0));
		}

		for(String temp : dayList){
			if(date.getValue().getDayOfWeek().toString().compareToIgnoreCase(temp) == 0){
				dayCheck = true;
			}
		}

		if(employee.getValue() != null && service.getValue() != null && date.getValue() != null &&
				hour.getValue() != null && minute.getValue() != null && firstname.getText() != null && lastname.getText() != null){
			if(dayCheck == false){			//The employee doesn't work on the selected day.
				ArrayList<ArrayList<String>> temp1 = databaseManager.getWorkingTime(employee_email);	//select working time by email
				for(ArrayList<String> t : temp1){
					workingDay += t.get(0) + " ";
				}
				alert = new Alert(AlertType.ERROR,"The employee only works on " + workingDay + "!");
				alert.showAndWait();
			}

			else if(databaseManager.searchBooking(date.getValue().toString(), start_time, end_time, employee_email, user) == true){
				alert = new Alert(AlertType.ERROR,"The same booking already exists!");
				alert.showAndWait();
			}

			else{
				databaseManager.setBooking(date.getValue().toString(), start_time, end_time, employee_email, service.getValue(), user, firstname.getText(),lastname.getText());
				alert = new Alert(AlertType.INFORMATION,"A new booking has been successfully created!");
				alert.showAndWait();
				viewController.gotoBusinessMenu();
			}
		}
		else{
			alert = new Alert(AlertType.ERROR,"All fields must be filled in!");
			alert.showAndWait();
		}
	}


	@FXML
	private void MainMenu(){
		viewController.gotoBusinessMenu();
	}
}
