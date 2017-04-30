package app;

import database.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;
import systemController.ViewController;


/**
 * This is the main class for the system. All data will be initiated including viewController and database
 * and launch the main menu.
 * @author ranlu
 *
 */
public class AppointmentBookingSystem extends Application{

	public static void main(String[] args) throws Exception{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		ViewController view = new ViewController();
		DatabaseManager databaseManager = new DatabaseManager();
		view.initDatabase(databaseManager);
		view.initStage(primaryStage);

	}
}