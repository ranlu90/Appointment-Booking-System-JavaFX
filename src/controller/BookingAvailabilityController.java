package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import database.BusinessOwner;
import database.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class BookingAvailabilityController implements Initializable{
	private ViewController viewController;
	private DatabaseManager databaseManager;
	private String receivedName,receivedAddress,receivedPhone;

	@FXML
	private TableView<BusinessOwner> businessTimeTable;

	@FXML
	private Text name,address,phone;

	@FXML
	private TableColumn<BusinessOwner,String> dayColumn,timeColumn;

	@FXML
	private ComboBox<String> ownerList;

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
	 * When the customer select one business owner, create business hours table for this owner.
	 */
	@SuppressWarnings("unchecked")
	@FXML
	private void pupulateBusinessHours(){

		dayColumn = new TableColumn<BusinessOwner,String>("Day");
		dayColumn.setMinWidth(90);
		dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
		timeColumn = new TableColumn<BusinessOwner,String>("Time");
		timeColumn.setMinWidth(90);
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
		businessTimeTable.setItems(getBusiness());
		businessTimeTable.getColumns().addAll(dayColumn,timeColumn);
		name.setText(receivedName);
		address.setText(receivedAddress);
		phone.setText(receivedPhone);
	}


	public ObservableList<BusinessOwner> getBusiness(){
		ArrayList<String> oneBusiness = databaseManager.getOneBusiness(ownerList.getValue());
		ArrayList<ArrayList<String>> businessHours = databaseManager.getBusinessTime(oneBusiness.get(3));

		ObservableList<BusinessOwner> list = FXCollections.observableArrayList();

		for(ArrayList<String> temp : businessHours){
			BusinessOwner entity = new BusinessOwner();
			receivedName = oneBusiness.get(0);
			receivedAddress = oneBusiness.get(1);
			receivedPhone = oneBusiness.get(2);

			entity.setBusinessName(oneBusiness.get(0));						//name
			entity.setAddress(oneBusiness.get(1));							//address
			entity.setPhone(oneBusiness.get(2));							//phone
			entity.setDay(temp.get(0));										//Day
			entity.setTime(temp.get(1) + "-" + temp.get(2));				//Time
			list.add(entity);
		}
		return list;
	}

	@FXML
	private void MainMenu(){
		viewController.gotoCustomerMenu();
	}
}
