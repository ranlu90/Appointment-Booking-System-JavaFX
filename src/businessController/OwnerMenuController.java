package businessController;

import java.io.File;
import java.net.URL;


import java.util.ResourceBundle;

import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import systemController.ViewController;

/**
 * Business owner menu.
 * @author ranlu
 *
 */
public class OwnerMenuController implements Initializable {

	private ViewController viewController;
	private DatabaseManager databaseManager;
	private	String user;
	@SuppressWarnings("unused")
	private String message;

	@FXML
	private Text welcomeMessage;
	@FXML
	private AnchorPane header,footer;
	@FXML
	private ImageView logo;

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
	public void initMessage(String message){
		this.message = message;
		if(message != null)
			welcomeMessage.setText(message);
		else
			welcomeMessage.setText("Welcome! " + databaseManager.getBusinessName(user));
	}

	public void initViewController(ViewController viewController) {
		this.viewController = viewController;

	}

	public void initDatabaseManager(DatabaseManager databaseManager) {
		this.databaseManager = databaseManager;
	}

	public void setUsername(String username){
		user = username;
	}

	@FXML
	public void CustomizeLayout(){
		viewController.gotoLayout();
	}

	@FXML
	private void addNewService(){
		viewController.gotoService();
	}

	@FXML
	private void updateEmployee(){
		viewController.gotoEmployee();
	}

	@FXML
	private void changeBusinessHours(){
		viewController.gotoBusinessHours();
	}

	@FXML
	private void viewAllBookings(){
		viewController.gotoAllBookings();
	}

	@FXML
	private void viewNewBookings(){
		viewController.gotoNewBookings();
	}

	@FXML
	private void createABooking(){
		viewController.gotoOwnerCreateBooking();
	}

	@FXML
	private void viewAvailability(){
		viewController.gotoViewWorkers();
	}

	@FXML
	private void logout(){
		viewController.gotoLogin();
	}


	public void initLogo(String logoURL) {
		File f =  new File("src/image/" + user + ".jpg");

		if(logoURL != null){
			logo.setImage(new Image(new File(logoURL).toURI().toString()));
		}
		else{
			if(f.exists()){
				logo.setImage(new Image("image/" + user + ".jpg"));
			}
		}
	}
}
