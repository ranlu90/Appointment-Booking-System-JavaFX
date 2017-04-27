package controller;


import java.net.*;
import java.util.*;

import javafx.fxml.*;

import javafx.scene.layout.*;

/**
 * This class sets the default container for all scenes.
 * @author ranlu
 *
 */
public class MainController implements Initializable {

    private ViewController view;

    @FXML
    private AnchorPane container;

    /**
     * Pass the ViewController to the current pages controller to allow
     * switching between pages.
     * @param viewController The ViewController reference being passed.
     */
    public void initViewController(ViewController viewController)
    {
        view = viewController;
        view.setContainer(container);
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources)
    {
        assert container != null : "fx:id=\"container\" was not "
            +"injected.";
    }
}
