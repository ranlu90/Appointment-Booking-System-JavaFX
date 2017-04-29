package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import database.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewBookingController implements Initializable{

	private ViewController viewController;
	private DatabaseManager databaseManager;
	private String user;

	@FXML
	private TableColumn<ArrayList<String>,String> date,time,employee,service,customer,contact;

	@FXML
	private TableView<ArrayList<String>> bookingTable;

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
	public void populateAll(){
		date.setCellValueFactory(new PropertyValueFactory<>(Booking.get(0)));
		time.setCellValueFactory(new PropertyValueFactory<>(Booking.get(1)));
		employee.setCellValueFactory(new PropertyValueFactory<>(Booking.get(2)));
		service.setCellValueFactory(new PropertyValueFactory<>(Booking.get(3)));
		customer.setCellValueFactory(new PropertyValueFactory<>(Booking.get(4)));
		contact.setCellValueFactory(new PropertyValueFactory<>(Booking.get(5)));
		bookingTable.setItems(getBooking());
		bookingTable.getColumns().addAll(date,time,employee,service,customer,contact);
	}

	public ObservableList<ArrayList<String>> getBooking(){
		ArrayList<ArrayList<String>> allBookings = databaseManager.getBookingForBusiness(user);
		ObservableList<ArrayList<String>> bookings = FXCollections.observableArrayList();
		for(ArrayList<String> temp : allBookings){
			ArrayList<String> name = databaseManager.searchEmployeeFullName(temp.get(2));
			ArrayList<String> Booking = new ArrayList<String>();
			Booking.add(temp.get(0));				//date
			Booking.add(temp.get(1));				//time
			Booking.add(name.get(0) + " " + name.get(1));			//employee's full name
			Booking.add(temp.get(3));								//service
			Booking.add(temp.get(4) + " " + temp.get(5));			//customer's full name
			Booking.add(temp.get(6));								//contact number
			bookings.add(Booking);
		}
		return bookings;
	}

	@FXML
	private void mainMenu(){
		viewController.gotoBusinessMenu();
	}
}
