package systemController;


import java.net.URL;

import java.util.ResourceBundle;

import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * System login page. Authenticate the user's username and password and go to business owner menu or customer menu.
 * @author ranlu
 *
 */
public class LoginController implements Initializable{

    private ViewController viewController;
    private DatabaseManager databaseManager;

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

	public void initViewController(ViewController viewController) {
		this.viewController = viewController;
	}


	public void initDatabaseManager(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}


	/**
	 * Attempt to login when a user press the login button,
	 * change to business owner menu or customer menu.
	 */
	@FXML
	private void login(){
		Alert alert;
			if(databaseManager.searchBusiness(username.getText(),password.getText()) == true){
				viewController.setUserName(username.getText());
				viewController.gotoBusinessMenu();
			}
			else if(databaseManager.searchCustomer(username.getText(), password.getText()) == true){
				viewController.setUserName(username.getText());
				viewController.gotoCustomerMenu();
			}
			else{
				alert = new Alert(AlertType.ERROR,
						"Incorrect credentials!");
				alert.showAndWait();
			}
	}

    /**
     * Switch views to customer register page.
     */
    @FXML
    private void customerRegister()
    {
        // Switch to customer register view
        viewController.gotoCustomerRegister();
    }


    /**
     * Switch views to business owner register page.
     */
    @FXML
    private void ownerRegister()
    {
        // Switch to  owner register view
        viewController.gotoOwnerRegister();
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
