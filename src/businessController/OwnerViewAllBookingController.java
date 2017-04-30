package businessController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import database.Booking;
import database.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import systemController.ViewController;

/**
 * View all bookings for one business owner.
 * @author ranlu
 *
 */
public class OwnerViewAllBookingController implements Initializable{

	private ViewController viewController;
	private DatabaseManager databaseManager;
	private String user;

	@FXML
	private TableColumn<Booking,String> dateColumn,timeColumn,employeeColumn,serviceColumn,customerColumn,contactColumn;

	@FXML
	private TableView<Booking> bookingTable;


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

	@SuppressWarnings("unchecked")
	@FXML
	public void populateTable(){
		dateColumn = new TableColumn<Booking,String>("Date");
		dateColumn.setMinWidth(90);
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

		timeColumn = new TableColumn<Booking,String>("Time");
		timeColumn.setMinWidth(90);
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

		employeeColumn = new TableColumn<Booking,String>("Employee");
		employeeColumn.setMinWidth(100);
		employeeColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));

		serviceColumn = new TableColumn<Booking,String>("Service");
		serviceColumn.setMinWidth(100);
		serviceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));

		customerColumn = new TableColumn<Booking,String>("Customer");
		customerColumn.setMinWidth(100);
		customerColumn.setCellValueFactory(new PropertyValueFactory<>("customer"));

		contactColumn = new TableColumn<Booking,String>("Contact");
		contactColumn.setMinWidth(100);
		contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

		bookingTable.setItems(getBooking());
		bookingTable.getColumns().addAll(dateColumn,timeColumn,employeeColumn,serviceColumn,customerColumn,contactColumn);
	}

	public ObservableList<Booking> getBooking(){
		ArrayList<ArrayList<String>> allBookings = databaseManager.getBookingForBusiness(user);
		ObservableList<Booking> bookings = FXCollections.observableArrayList();
		for(ArrayList<String> temp : allBookings){
			ArrayList<String> name = databaseManager.searchOneEmployee(temp.get(2));
			Booking entity = new Booking();
			entity.setDate(temp.get(0));										//date
			entity.setTime(temp.get(1));										//time
			entity.setEmployee(name.get(0) + " " + name.get(1));				//employee's full name
			entity.setService(temp.get(3));										//service
			entity.setCustomer(temp.get(4) + " " + temp.get(5));				//customer's full name
			entity.setContact(temp.get(6));										//contact number
			bookings.add(entity);
		}
		return bookings;
	}

	@FXML
	private void mainMenu(){
		viewController.gotoBusinessMenu();
	}
}
