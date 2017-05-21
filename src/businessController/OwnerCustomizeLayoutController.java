package businessController;

import java.io.File;
import java.net.URL;



import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import systemController.ViewController;

/**
 * This is the GUI customization page. The client can modify home page message, logo, header color and footer color in this page.
 * @author ranlu
 *
 */
public class OwnerCustomizeLayoutController implements Initializable{

	private ViewController viewController;
	private Stage stage;

	@FXML
	private TextField filepath;

	@FXML
	private TextField textMessage;

	@FXML
	private ColorPicker colorHeader,colorFooter;

	@FXML
	private AnchorPane header,footer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {}

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
	@FXML
	private void Confirm(){
		Alert alert;

		if(colorHeader.getValue() != null){
			String headerColor;
			headerColor = "-fx-background-color: " + "#" + colorHeader.getValue().toString().substring(2, 8);
			viewController.setHeader(headerColor);
		}
		if(colorFooter.getValue() != null){
			String footerColor;
			footerColor = "-fx-background-color: "  + "#" + colorFooter.getValue().toString().substring(2, 8);
			viewController.setFooter(footerColor);
		}
		if(textMessage.getText().trim().isEmpty() == false){
			viewController.setMessage(textMessage.getText());
		}
		if(filepath.getText().trim().isEmpty() == false){
			viewController.setLogoURL(filepath.getText());
		}
		if(colorHeader.getValue() != null || colorFooter.getValue() != null || filepath.getText().trim().isEmpty() == false || textMessage.getText().trim().isEmpty() == false){
			alert = new Alert(AlertType.INFORMATION,"Customized layout has been saved.");
			alert.showAndWait();
			viewController.gotoBusinessMenu();
		}
	}
	
	/**
	 * Select image file from the system, the file that can be chosen are limited to the following file formats.
	 */
	@FXML
	private void ChooseFile(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif", "*.jpeg"));
		File file = fileChooser.showOpenDialog(stage);
		if(file != null){
			filepath.setText(file.getAbsolutePath());
		}
	}

	@FXML
	private void MainMenu(){
		viewController.gotoBusinessMenu();
	}

	public void initViewController(ViewController viewController) {
		this.viewController = viewController;
	}
	public void initStage(Stage stage){
		this.stage = stage;
	}
}
