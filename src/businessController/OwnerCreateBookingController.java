package businessController;

import java.net.URL;



import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import systemController.ViewController;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * Owner creates bookings for one customer.
 * @author ranlu
 *
 */
public class OwnerCreateBookingController implements Initializable{

	private ViewController viewController;
	private DatabaseManager databaseManager;
	private String user;

	@FXML
	ComboBox<String> employee,service,time,existingCustomer;

	@FXML
	TextField firstname, lastname, contactNumber;

	@FXML
	private AnchorPane header,footer;

	@FXML
	DatePicker date;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
    public void setHeader(String headerColor){
    	if(headerColor != null){
    		header.setStyle(headerColor);
    	}
    }

    public void setFooter(String footerColor){
    	if(footerColor != null){
    		footer.setStyle(footerColor);
    	}
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
	 * Set drop down list for available time.
	 */
	@FXML
	public void SetTime(){
		time.getItems().clear();
		
		if(employee.getValue() != null && date.getValue() != null){
			ArrayList<String> timeList = new ArrayList<String>();
			String[] name = employee.getValue().split(" ");
			String employee_email = databaseManager.searchEmployeeEmailByName(name[0], name[1]);
			HashMap<Integer, Boolean> check = getTimeList(employee_email, date.getValue().getDayOfWeek().toString(), date.getValue().toString());
			for(int i = 0; i <= 47; i ++){
				if(check.get(i+1) == true){
					if(i % 2 == 0){
						timeList.add((i / 2) +":" + "00");
					}
					else{
						timeList.add((i / 2) +":" + "30");
					}
				}
			}
			time.getItems().addAll(timeList);
		}
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
	private void Confirm(){
		Alert alert;
		if(employee.getValue() != null && service.getValue() != null && date.getValue() != null &&
				time.getValue() != null && firstname.getText().trim().isEmpty() == false &&
				lastname.getText().trim().isEmpty() == false && contactNumber.getText().trim().isEmpty() == false){

		ArrayList<String> dayList = new ArrayList<String>();
		String workingDay = "";
		boolean dayCheck = false;		//check if the day matches employee's working day
		String start_time = time.getValue();
		int duration = Integer.parseInt(databaseManager.getDuration(service.getValue(),user));

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
			else if(CheckTimeSlot(employee_email, date.getValue().getDayOfWeek().toString(), date.getValue().toString(), start_time,  service.getValue()) == false){
				int selectedDuration = Integer.parseInt(databaseManager.getDuration(service.getValue(),user));
				alert = new Alert(AlertType.ERROR,"The employee is not available for " + selectedDuration + " Minutes' " + service.getValue() + ". Please select a different time.");
				alert.showAndWait();
			}
			else if(CheckTime(date.getValue().getDayOfWeek().toString(),start_time, duration) == false){
				alert = new Alert(AlertType.ERROR,"Booking time has to be within business hours.");
				alert.showAndWait();
			}
			else if(!contactNumber.getText().matches("([0-9+]*[ ()]*[0-9]*[ ()]*[0-9]*[ -]*[0-9]+)")){
				alert = new Alert(AlertType.ERROR,"Contact number can only contain digits, space and + ( ) ");
				alert.showAndWait();
			}
			else {
				String message = "A new booking has been created." + System.lineSeparator() +
						 "Employee: " + employee.getValue() + System.lineSeparator() +
						 "Service: " + service.getValue() + System.lineSeparator() +
						 "Date: " + date.getValue() + System.lineSeparator() +
						 "Time: " + time.getValue() + System.lineSeparator() +
						 "Customer: " + firstname.getText()	+ " "+ lastname.getText() + System.lineSeparator() +
						 "Contact Number: " + contactNumber.getText()	+ System.lineSeparator();
				databaseManager.setBooking(date.getValue().toString(), start_time, employee_email, service.getValue(), user, firstname.getText(),lastname.getText(),contactNumber.getText());
				alert = new Alert(AlertType.INFORMATION, message);
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
	 * Check if the employee works on that time, then check if he/she already got a booking.
	 * @return true if the employee is available at that time.
	 */
	public boolean CheckTimeSlot(String email, String day, String date, String startTime, String serviceName){

		ArrayList<ArrayList<String>> workingTime = databaseManager.getWorkingTime(email);
		ArrayList<ArrayList<String>> booking = databaseManager.getBookingForEmployee(email, date);
		HashMap<Integer, Boolean> timeSlot = new HashMap<Integer,Boolean>();
		for(int i = 1; i <= 48; i ++){
			timeSlot.put(i, false);
		}

		//add time slots by employee's working time divided by 30
		for(ArrayList<String> temp:workingTime){
			if(temp.get(0).compareToIgnoreCase(day) == 0){
				String[] str = temp.get(1).split(":");			//working start time
				String[] str2 = temp.get(2).split(":");			//working end time
				int slot1 = ((Integer.parseInt(str[0]) * 60) + Integer.parseInt(str[1])) / 30 + 1;
				int slot2 = ((Integer.parseInt(str2[0]) * 60) + Integer.parseInt(str2[1])) / 30;
				for(int i = slot1; i <= slot2; i ++){
					timeSlot.put(i, true);
				}
			}
		}

		//remove time slots by employee's existing booking
		for(ArrayList<String> temp:booking){
			String[] str3 = temp.get(0).split(":");			//booking start time
			int slot3 = ((Integer.parseInt(str3[0]) * 60) + Integer.parseInt(str3[1])) / 30 + 1;
			int duration = Integer.parseInt(databaseManager.getDuration(temp.get(1),user));
			int slot4 = ((Integer.parseInt(str3[0]) * 60) + Integer.parseInt(str3[1]) + duration) / 30;
			for(int i = slot3; i <= slot4; i ++){
				timeSlot.put(i, false);
			}
		}
		
		//check if selected time slots are available by selected booking time and service
		String[] str4 = startTime.split(":");			//selected booking start time
		int slot5 = ((Integer.parseInt(str4[0]) * 60) + Integer.parseInt(str4[1])) / 30 + 1;
		int selectedDuration = Integer.parseInt(databaseManager.getDuration(serviceName,user));
		int slot6 = ((Integer.parseInt(str4[0]) * 60) + Integer.parseInt(str4[1]) + selectedDuration) / 30;
		for(int i = slot5; i <= slot6; i ++){
			if(timeSlot.get(i) == false){			//the time slot has been occupied
				return false;
			}
		}
		return true;
	}


	/**
	 * This method check if selected booking time is within the business owner's business hours.
	 * Return true if the time period for booking time can be added.
	 */
	public boolean CheckTime(String day, String startTime, int duration){

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
		String[] str3 = startTime.split(":");			//booking starts
 		int slot3 = ((Integer.parseInt(str3[0]) * 60) + Integer.parseInt(str3[1])) / 30 + 1;
		int slot4 = ((Integer.parseInt(str3[0]) * 60) + Integer.parseInt(str3[1]) + duration) / 30;
		for(int i = slot3; i <= slot4; i ++){
			if(timeSlot.get(i) == false){			//the time slot has been occupied
				return false;
			}
		}
		return true;
	}

	@FXML
	private void CheckExistingCustomer(){
		if(existingCustomer.getItems() != null){
			existingCustomer.getItems().clear();
		}
		if(databaseManager.searchCustomerByNumber(contactNumber.getText()) != null){
			ArrayList<ArrayList<String>> customerList = databaseManager.searchCustomerByNumber(contactNumber.getText());
			ArrayList<String> temp = new ArrayList<String>();
			for(ArrayList<String> customer: customerList){
				String name = customer.get(0) + " " + customer.get(1);
				temp.add(name);
			}
			existingCustomer.getItems().addAll(temp);
		}
	}

	@FXML
	private void setCustomer(){
		if(existingCustomer.getValue() != null){
		String temp[] = existingCustomer.getValue().split(" ");
		firstname.setText(temp[0]);
		lastname.setText(temp[1]);
		}
	}
	
	
	/**
	 * Get the available time list for an employee on a day.
	 */
	public HashMap<Integer, Boolean> getTimeList(String email, String day, String date){
		ArrayList<ArrayList<String>> workingTime = databaseManager.getWorkingTime(email);
		ArrayList<ArrayList<String>> booking = databaseManager.getBookingForEmployee(email, date);
		HashMap<Integer, Boolean> timeSlot = new HashMap<Integer,Boolean>();
		for(int i = 1; i <= 48; i ++){
			timeSlot.put(i, false);
		}

		//add time slots by employee's working time divided by 30
		for(ArrayList<String> temp:workingTime){
			if(temp.get(0).compareToIgnoreCase(day) == 0){
				String[] str = temp.get(1).split(":");			//working start time
				String[] str2 = temp.get(2).split(":");			//working end time
				int slot1 = ((Integer.parseInt(str[0]) * 60) + Integer.parseInt(str[1])) / 30 + 1;
				int slot2 = ((Integer.parseInt(str2[0]) * 60) + Integer.parseInt(str2[1])) / 30;
				for(int i = slot1; i <= slot2; i ++){
					timeSlot.put(i, true);
				}
			}
		}

		//remove time slots by employee's existing booking
		for(ArrayList<String> temp:booking){
			String[] str3 = temp.get(0).split(":");			//booking start time
			int slot3 = ((Integer.parseInt(str3[0]) * 60) + Integer.parseInt(str3[1])) / 30 + 1;
			int duration = Integer.parseInt(databaseManager.getDuration(temp.get(1),user));
			int slot4 = ((Integer.parseInt(str3[0]) * 60) + Integer.parseInt(str3[1]) + duration) / 30;
			for(int i = slot3; i <= slot4; i ++){
				timeSlot.put(i, false);
			}
		}
		//get the available time list for an employee
		return timeSlot;
	}
}
