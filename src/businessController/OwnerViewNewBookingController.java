package businessController;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.layout.AnchorPane;
import systemController.ViewController;

/**
 * View new bookings after current date and time.
 * @author ranlu
 *
 */
public class OwnerViewNewBookingController implements Initializable{

	private ViewController viewController;
	private DatabaseManager databaseManager;
	private String user;
	@FXML
	private AnchorPane header,footer;
	@FXML
	private TableColumn<Booking,String> dateColumn,timeColumn,employeeColumn,serviceColumn,customerColumn,contactColumn;

	@FXML
	private TableView<Booking> bookingTable;


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

	@SuppressWarnings("unchecked")
	@FXML
	public void populateTable() throws ParseException{
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

	public ObservableList<Booking> getBooking() throws ParseException{
		ArrayList<ArrayList<String>> allBookings = databaseManager.getBookingForBusiness(user);
		ObservableList<Booking> bookings = FXCollections.observableArrayList();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat tf = new SimpleDateFormat("HH:mm");
		Date currentDate = df.parse(df.format(new Date()));
		Date currentTime = tf.parse(tf.format(new Date()));

		for(ArrayList<String> temp : allBookings){
			ArrayList<String> name = databaseManager.searchOneEmployee(temp.get(2));
			Booking entity = new Booking();
			entity.setDate(temp.get(0));										//date
			entity.setTime(temp.get(1));										//time
			entity.setEmployee(name.get(0) + " " + name.get(1));				//employee's full name
			entity.setService(temp.get(3));										//service
			entity.setCustomer(temp.get(4) + " " + temp.get(5));				//customer's full name
			entity.setContact(temp.get(6));										//contact number
			if( df.parse(temp.get(0)).compareTo(currentDate) > 0){
				bookings.add(entity);
			}
			if(df.parse(temp.get(0)).compareTo(currentDate) == 0 && tf.parse(temp.get(1)).compareTo(currentTime) >= 0){
				bookings.add(entity);
			}
		}
		return bookings;
	}

	@FXML
	private void mainMenu(){
		viewController.gotoBusinessMenu();
	}
}
