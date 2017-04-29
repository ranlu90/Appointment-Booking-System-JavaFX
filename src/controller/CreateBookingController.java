package controller;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	TextField firstname, lastname, contactNumber;

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
		minutes.add("00");
		minutes.add("30");
		minute.getItems().addAll(minutes);
	}


	/**
	 * 1. Check if the employee works on selected date.
	 * 2. Check if a same booking already exists.
	 * 3. Check if the employee is available at that time.
	 * If the employee works on one day but doesn't have time(already have customer's booking), inform owner the message.
	 * If the employee works on one day but doesn't work at that time, inform owner the message.
	 * @throws ParseException
	 */
	@FXML
	private void Confirm() throws ParseException{
		Alert alert;
		if(employee.getValue() != null && service.getValue() != null && date.getValue() != null &&
				hour.getValue() != null && minute.getValue() != null && firstname.getText().trim().isEmpty() == false &&
				lastname.getText().trim().isEmpty() == false && contactNumber.getText().trim().isEmpty() == false){

		ArrayList<String> dayList = new ArrayList<String>();
		String workingDay = "";
		String timeMessage = "";		//show employee's working time
		boolean dayCheck = false;		//check if the day matches employee's working day

		//add duration of the service to the start time of booking and create a new end time
		int duration = Integer.parseInt(databaseManager.getDuration(service.getValue()));
		int totalMinutes = (Integer.parseInt(hour.getValue()) * 60) + Integer.parseInt(minute.getValue()) + duration;
		int hours = totalMinutes / 60;
		int minutes = totalMinutes % 60;
		String start_time = hour.getValue() + ":" + minute.getValue();

		//split employee's full name by whitespace and check his email in the database by firstname and lastname
		String[] name = employee.getValue().split(" ");
		String employee_email = databaseManager.searchEmployeeEmailByName(name[0], name[1]);
		ArrayList<ArrayList<String>> workingTime = databaseManager.getWorkingTime(employee_email);


		for(ArrayList<String> temp : workingTime){
			dayList.add(temp.get(0));
		}

		for(String temp : dayList){
			if(date.getValue().getDayOfWeek().toString().compareToIgnoreCase(temp) == 0){
				dayCheck = true;
			}
		}

			if(dayCheck == false){			//The employee doesn't work on the selected day.
				for(ArrayList<String> t : workingTime){
					workingDay += t.get(0) + " ";
				}
				alert = new Alert(AlertType.ERROR,"The employee only works on " + workingDay + "!");
				alert.showAndWait();
			}

			else if(databaseManager.searchBooking(date.getValue().toString(), start_time, employee_email, user) == true){
				alert = new Alert(AlertType.ERROR,"The same booking already exists!");
				alert.showAndWait();
			}
			else if(false){
				for(ArrayList<String> t : workingTime){
					timeMessage += t.get(0) + " " + t.get(1) + "-" + t.get(2) + " ";
				}
				alert = new Alert(AlertType.ERROR,"The employee's working time is " + timeMessage + "Please select a different time.");
				alert.showAndWait();
			}

			else{
				databaseManager.setBooking(date.getValue().toString(), start_time, employee_email, service.getValue(), user, firstname.getText(),lastname.getText(),contactNumber.getText());
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

	/**
	 * Check if the employee works on the day, then check if he/she already got a booking at the same time.
	 * @return true if the employee is available at that time.
	 * @throws ParseException
	 */
	public boolean CheckEmployeeWorkingTime(String email, String date, String startTime, String endTime, String day) throws ParseException{
		ArrayList<ArrayList<String>> workingTime = databaseManager.getWorkingTime(email);
		SimpleDateFormat tf = new SimpleDateFormat("HH:mm");

		for(ArrayList<String> temp:workingTime){
			if(temp.get(0).compareToIgnoreCase(day) == 0){
				Date start = tf.parse(temp.get(1));
				Date end = tf.parse(temp.get(2));
				if(tf.parse(startTime).compareTo(start) < 0 || tf.parse(endTime).compareTo(end) > 0){
					return true; 					//the chosen booking time is not in employee's working time
				}
			}
		}
		return true;
	}
}
