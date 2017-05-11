package businessController;

import java.net.URL;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import systemController.ViewController;

public class OwnerCustomizeLayoutController implements Initializable{

	private ViewController viewController;

	@FXML
	private TextField textMessage;

	@FXML
	private ColorPicker colorHeader,colorFooter;

	private AnchorPane header,footer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {}


	@FXML
	private void Confirm(){
		Alert alert;

		if(colorHeader.getValue() != null){
			viewController.setHeader(header);
		}
		if(colorFooter.getValue() != null){
			viewController.setFooter(footer);
		}
		if(textMessage.getText().trim().isEmpty() == false){
			viewController.setMessage(textMessage.getText());
		}
		if(colorHeader.getValue() != null || colorFooter.getValue() != null || textMessage.getText().trim().isEmpty() == false){
			alert = new Alert(AlertType.INFORMATION,"Customized layout has been saved.");
			alert.showAndWait();
			viewController.gotoBusinessMenu();
		}
	}


	@FXML
	private void MainMenu(){
		viewController.gotoBusinessMenu();
	}

	public void initViewController(ViewController viewController) {
		this.viewController = viewController;
	}
}
