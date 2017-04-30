package controller;

import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;
import database.DatabaseManager;
import database.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewEmployeeController implements Initializable{

	private ViewController viewController;
	private DatabaseManager databaseManager;
	private String user;

	@FXML
	private TableColumn<Employee,String> firstnameColumn,lastnameColumn,emailColumn,contactColumn,dayColumn,timeColumn;

	@FXML
	private TableView<Employee> employeeTable;


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
		firstnameColumn = new TableColumn<Employee,String>("First Name");
		firstnameColumn.setMinWidth(90);
		firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));

		lastnameColumn = new TableColumn<Employee,String>("Last Name");
		lastnameColumn.setMinWidth(90);
		lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));

		emailColumn = new TableColumn<Employee,String>("Email");
		emailColumn.setMinWidth(100);
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

		contactColumn = new TableColumn<Employee,String>("Contact");
		contactColumn.setMinWidth(100);
		contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));

		dayColumn = new TableColumn<Employee,String>("Day");
		dayColumn.setMinWidth(100);
		dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));

		timeColumn = new TableColumn<Employee,String>("Time");
		timeColumn.setMinWidth(100);
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

		employeeTable.setItems(getEmployee());
		employeeTable.getColumns().addAll(firstnameColumn,lastnameColumn,emailColumn,contactColumn,dayColumn,timeColumn);
	}

	public ObservableList<Employee> getEmployee(){
		ArrayList<ArrayList<String>> employee = databaseManager.getEmployee(user);
		ObservableList<Employee> employees = FXCollections.observableArrayList();
		for(ArrayList<String> temp : employee){
			ArrayList<ArrayList<String>> workingTime = databaseManager.getWorkingTime(temp.get(2));
			for(ArrayList<String> t : workingTime){
				Employee entity = new Employee();
				entity.setDay(t.get(0));											//day
				entity.setTime(t.get(1) + "-" + t.get(2));							//time
				entity.setFirstname(temp.get(0));									//first name
				entity.setLastname(temp.get(1));									//last name
				entity.setEmail(temp.get(2));										//employee's email
				entity.setContact(temp.get(3));										//contact number
				employees.add(entity);
			}
		}
		return employees;
	}

	@FXML
	private void mainMenu(){
		viewController.gotoBusinessMenu();
	}
}
