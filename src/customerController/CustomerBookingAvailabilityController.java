package customerController;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import database.BookingAvailability;
import database.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import systemController.ViewController;

/**
 * Customers view business hours for one business owner.
 * @author ranlu
 *
 */
public class CustomerBookingAvailabilityController implements Initializable{
	private ViewController viewController;
	private DatabaseManager databaseManager;
	private String receivedName,receivedAddress,receivedPhone,ownerID;

	@FXML
	private TableView<BookingAvailability> availabilityTable;

	@FXML
	private DatePicker date;

	@FXML
	private Text name,address,phone;

	@FXML
	private TableColumn<BookingAvailability,String> timeColumn,availabilityColumn;

	@FXML
	private ComboBox<String> ownerList,employee;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void initViewController(ViewController viewController) {
		this.viewController = viewController;

	}

	public void initDatabaseManager(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}
	@FXML
	public void initOwnerList(){
		ArrayList<ArrayList<String>> business = databaseManager.getAllBusiness();
		ArrayList<String> nameList = new ArrayList<String>();
		for(ArrayList<String> temp: business){
			nameList.add(temp.get(0));
		}
		ownerList.getItems().addAll(nameList);
	}

	/**
	 * When the customer select one business owner, create employee table for this owner.
	 */
	@FXML
	private void pupulateEmployee(){
		ArrayList<String> oneBusiness = databaseManager.getOneBusiness(ownerList.getValue());
		receivedName = oneBusiness.get(0);
		receivedAddress = oneBusiness.get(1);
		receivedPhone = oneBusiness.get(2);
		ownerID = oneBusiness.get(3);
		name.setText(receivedName);
		address.setText(receivedAddress);
		phone.setText(receivedPhone);
		if(employee.getItems() != null){		//update employee list for each selection of business owner
			employee.getItems().clear();
		}
		ArrayList<ArrayList<String>> employeelist = databaseManager.getEmployee(ownerID);
		ArrayList<String> temp = new ArrayList<String>();
		for(ArrayList<String> employee: employeelist){
			String name = employee.get(0) + " " + employee.get(1);
			temp.add(name);
		}
		employee.getItems().addAll(temp);
	}


	public ObservableList<BookingAvailability> getAvailability(){

		String[] name = employee.getValue().split(" ");
		String employee_email = databaseManager.searchEmployeeEmailByName(name[0], name[1]);

		ObservableList<BookingAvailability> list = FXCollections.observableArrayList();
		HashMap<Integer, Boolean> check = CheckTimeSlot(employee_email, date.getValue().getDayOfWeek().toString(), date.getValue().toString());
		HashMap<Integer, String> allTime = new HashMap<Integer, String>();

		for(int i = 0; i <= 47; i ++){
			if(i % 2 == 0){
				allTime.put(i, (i / 2) +":" + "00");
			}
			else{
				allTime.put(i, (i / 2) +":" + "30");
			}
		}

		for(int i = 1; i <= 48; i ++){
			BookingAvailability entity = new BookingAvailability();
			entity.setTime(allTime.get(i-1));

			if(check.get(i) == true){
				entity.setAvailability("Available");
			}
			else{
				entity.setAvailability("N/A");
			}
			list.add(entity);
		}
		return list;
	}

	@FXML
	private void MainMenu(){
		viewController.gotoCustomerMenu();
	}

	/**
	 * Get the employee's availability on the date.
	 */
	@SuppressWarnings("unchecked")
	public void populateAvailability(){
		if(ownerList.getValue() != null && employee.getValue() != null && date.getValue() != null){
			if(name.getText().trim().isEmpty() == false){
				availabilityTable.getColumns().clear();
			}
			timeColumn = new TableColumn<BookingAvailability,String>("Time");
			timeColumn.setMinWidth(90);
			timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

			availabilityColumn = new TableColumn<BookingAvailability,String>("Availability");
			availabilityColumn.setMinWidth(90);
			availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));
			availabilityTable.setItems(getAvailability());
			availabilityTable.getColumns().addAll(timeColumn,availabilityColumn);
		}
	}


	/**
	 * Check if the employee works on that time, then check if he/she already got a booking.
	 * @return true if the employee is available at that time.
	 */
	public HashMap<Integer, Boolean> CheckTimeSlot(String email, String day, String date){

		ArrayList<ArrayList<String>> workingTime = databaseManager.getWorkingTime(email);
		ArrayList<ArrayList<String>> businessHours = databaseManager.getBusinessTime(ownerID);
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
			int duration = Integer.parseInt(databaseManager.getDuration(temp.get(1),ownerID));
			int slot4 = ((Integer.parseInt(str3[0]) * 60) + Integer.parseInt(str3[1]) + duration) / 30;
			for(int i = slot3; i <= slot4; i ++){
				timeSlot.put(i, false);
			}
		}

		/**
		 * Remove all time slots except business hours time slots. This is used when the business hours have changed,
		 * but no employees' working time have been added to the new business hours.
		 */
		for(ArrayList<String> temp:businessHours){
			if(temp.get(0).compareToIgnoreCase(day) == 0){
				String[] str = temp.get(1).split(":");			//business open time
				String[] str2 = temp.get(2).split(":");			//business closing time
				int slot1 = ((Integer.parseInt(str[0]) * 60) + Integer.parseInt(str[1])) / 30 + 1;
				int slot2 = ((Integer.parseInt(str2[0]) * 60) + Integer.parseInt(str2[1])) / 30;
				for(int i = 1; i <= slot1 - 1; i ++){
					timeSlot.put(i, false);
				}
				for(int i = slot2 + 1; i <= 48; i ++){
					timeSlot.put(i, false);
				}
			}
		}
		return timeSlot;
	}

	/**
	 * Clear table when the user select another employee, and havn't change the date value.
	 */
	public void clear(){
		if(name.getText().trim().isEmpty() == false){
			availabilityTable.getColumns().clear();
		}
	}
}

