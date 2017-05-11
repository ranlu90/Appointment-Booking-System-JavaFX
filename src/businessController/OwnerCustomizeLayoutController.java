package businessController;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

	@FXML
	private AnchorPane header,footer;

	@Override
	public void initialize(URL location, ResourceBundle resources) {}

    public void setHeader(AnchorPane header){
    	this.header = header;
    }

    public void setFooter(AnchorPane footer){
    	this.footer = footer;
    }
	@FXML
	private void Confirm() throws IOException{
		Alert alert;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CustomizeLayout.fxml"));
        Node node = (Node) loader.load();
        header.getChildren().setAll(node);
        footer.getChildren().setAll(node);

		header.setPrefHeight(60);
		header.setPrefWidth(600);
		header.setStyle("-fx-background-color: " + colorHeader.getValue());
		footer.setPrefHeight(60);
		footer.setPrefWidth(600);
		footer.setStyle("-fx-background-color: " + colorFooter.getValue());

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
			//viewController.gotoBusinessMenu();
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
