package customerController;

import java.net.URL;

import java.util.ResourceBundle;

import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import systemController.ViewController;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * This class includes function for customer registration. First get user input from GUI, if all information passed format check, go to login,
 * otherwise stay at this page.
 * @author ranlu
 */
public class CustomerRegisterController implements Initializable{
	private ViewController viewController;
	private DatabaseManager databaseManager;


    @FXML
    private TextField firstname,lastname,address,contactNumber,username;
    @FXML
    private PasswordField password,password2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void initViewController(ViewController viewController) {
		this.viewController = viewController;

	}

	public void initDatabaseManager(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}


	/**
	 * This method will check if firstname, lastname, email and contact number meet the requirements. It will also check if username already exists
	 * in the database.
	 */
	@FXML
	private void customerRegister(){
		Alert alert;
		if(firstname.getText() != null && lastname.getText() != null && address.getText().trim().isEmpty() == false && contactNumber.getText() != null &&
				username.getText() != null && password.getText() != null && password2.getText() != null){
			if(!firstname.getText().matches("[a-zA-Z]{1,}")){
				alert = new Alert(AlertType.ERROR,"First name can only contain letters!");
				alert.showAndWait();
			}
			else if(!lastname.getText().matches("[a-zA-Z]{1,}")){
				alert = new Alert(AlertType.ERROR,"Last name can only contain letters!");
				alert.showAndWait();
			}
			else if(!contactNumber.getText().matches("([0-9+]*[ ()]*[0-9]*[ ()]*[0-9]*[ -]*[0-9]+)")){
				alert = new Alert(AlertType.ERROR,"Contact number can only contain digits, space and + ( ) !");
				alert.showAndWait();
			}
			else if(!username.getText().matches("^[0-9a-zA-Z@.]{1,}$")){
				alert = new Alert(AlertType.ERROR,"Username can only contain digits, letters and @ . !");
				alert.showAndWait();
			}
			else if(!password.getText().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$")){
				alert = new Alert(AlertType.ERROR,"Password must contain 1 uppercase, 1 lowercase, 1 digit, no space and minimum length of 6.");
				alert.showAndWait();
			}
			else if(!password.getText().matches(password2.getText())){
				alert = new Alert(AlertType.ERROR,"Password and confirmed password must match !");
				alert.showAndWait();
			}
			else{
				if(databaseManager.searchCustomerUserName(username.getText()) == false && databaseManager.searchBusinessUserName(username.getText()) == false){
							databaseManager.insertIntoCustomer(firstname.getText(),lastname.getText(),address.getText(),contactNumber.getText(),username.getText(),password.getText());
							alert = new Alert(AlertType.INFORMATION,"Your customer account has been successfully created !");
							alert.showAndWait();
							viewController.gotoLogin();
				}
				else{
					alert = new Alert(AlertType.ERROR,"This username has already been taken !");
					alert.showAndWait();
				}
			}
		}
		else{
			alert = new Alert(AlertType.ERROR,"All fields must be filled !");
			alert.showAndWait();
		}
	}


	/**
	 * Go to login menu.
	 */
	@FXML
	private void login(){
		viewController.gotoLogin();
	}
}
