package controller;


import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import database.DatabaseManager;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class manages functions of adding logging information and go to all pages.
 * All variables received from system main method.
 * @author ranlu
 */
public class ViewController{

	private String username;
	private DatabaseManager databaseManager;
	private Stage stage;
    private AnchorPane container;

	public ViewController(){}

    /**
     * create appointment booking system database,
     * create all tables and insert entities for them.
     * @throws IOException If any exceptions occur.
     */
    public void initDatabase(DatabaseManager databaseManager) throws IOException{

    	this.databaseManager = databaseManager;
    	databaseManager.deleteDatabase();
    	databaseManager.createNewDatabase("AppointmentBookingSystem.db");

    	databaseManager.setConnection();
    	databaseManager.createBusinessTable();
    	databaseManager.createCustomerInfoTable();
    	databaseManager.createEmployeeTable();
    	databaseManager.createBusinessTimeTable();
    	databaseManager.createWorkingTimeTable();
    	databaseManager.createBookingTable();
    	databaseManager.createServiceTable();

    	databaseManager.insertInitialEntitiesForBusiness();
    	databaseManager.insertInitialEntitiesForCustomerInfo();
        databaseManager.insertInitialEntitiesForEmployee();
        databaseManager.insertInitialEntitiesForBusinessHours();
        databaseManager.insertInitialEntitiesForWorkingTime();
    	databaseManager.insertInitialEntitiesForBooking();
        databaseManager.insertInitialEntitiesForService();
    }

    /**
     * Pass username as a primary key.
     * @param username received from loginController, pass to next level controller.
     */
	public void setUserName(String username){
		this.username = username;
	}


	/**
	 * Add logging information to console.
	 * @param from - the class name for printed information.
	 * @param message - print to console.
	 */
    public void add(String from, String message)
    {
        // Get the date and format it for output
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        // Create the output message
        String logMsg = "[" + dateFormat.format(date) + "] "+ from +": "+ message;
        // Output to console
        System.out.println(logMsg);
    }


    /**
     * Initialize the first page - The login page - and shows the window.
     */
    public void initStage(Stage stage)
    {
        this.stage = stage;
        createMain();
        gotoLogin();
        stage.show();
    }

    public void setContainer(AnchorPane container)
    {
        this.container = container;
    }

    /**
     * Create the main page, set default container.
     */
    public void createMain()
    {
        try {
            MainController main = (MainController) createScene("Main.fxml");
            main.initViewController(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the view login.
     */
    public void gotoLogin()
    {
        try {
            LoginController login = (LoginController) setScene("Login.fxml");
            login.initViewController(this);
            login.initDatabaseManager(databaseManager);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the business menu.
     */
    public void gotoBusinessMenu()
    {
        try {
        	BusinessMenuController businessMenu = (BusinessMenuController) setScene("BusinessMenu.fxml");
        	businessMenu.initViewController(this);
        	businessMenu.initDatabaseManager(databaseManager);
        	businessMenu.setUsername(username);
        	businessMenu.welcomeMessage();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the customer menu.
     */
    public void gotoCustomerMenu()
    {
        try {
        	CustomerMenuController customerMenu = (CustomerMenuController) setScene("CustomerMenu.fxml");
        	customerMenu.initViewController(this);
        	customerMenu.initDatabaseManager(databaseManager);
        	customerMenu.setUsername(username);
        	customerMenu.welcomeMessage();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the customer register.
     */
    public void gotoCustomerRegister()
    {
        try {
        	CustomerRegisterController customerRegister = (CustomerRegisterController) setScene("CustomerRegister.fxml");
        	customerRegister.initViewController(this);
        	customerRegister.initDatabaseManager(databaseManager);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the customer menu.
     */
    public void gotoOwnerRegister()
    {
        try {
        	OwnerRegisterController ownerRegister = (OwnerRegisterController) setScene("OwnerRegister.fxml");
        	ownerRegister.initViewController(this);
        	ownerRegister.initDatabaseManager(databaseManager);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the add new service.
     */
    public void gotoService()
    {
        try {
            ServiceController service = (ServiceController) setScene("AddServices.fxml");
            service.initViewController(this);
            service.initDatabaseManager(databaseManager);
            service.setUsername(username);
            service.initDuration();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the add new employee or update employee information.
     */
    public void gotoEmployee()
    {
        try {
            AddEmployeeController employee = (AddEmployeeController) setScene("AddEmployee.fxml");
            employee.initViewController(this);
            employee.initDatabaseManager(databaseManager);
            employee.setUsername(username);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the add or update business hours.
     */
    public void gotoBusinessHours()
    {
        try {
        	BusinessHoursController business = (BusinessHoursController) setScene("AddBusinessHours.fxml");
        	business.initViewController(this);
        	business.initDatabaseManager(databaseManager);
        	business.setUsername(username);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to view all bookings.
     */
    public void gotoAllBookings()
    {
        try {
            stage.setMinWidth(600);
            stage.setMinHeight(490);
        	ViewBookingController booking = (ViewBookingController) setScene("ViewAllBookings.fxml");
        	booking.initViewController(this);
        	booking.initDatabaseManager(databaseManager);
        	booking.setUsername(username);
        	booking.populateTable();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to view new bookings, new bookings are those after current date and time.
     */
    public void gotoNewBookings()
    {
        try {
            stage.setMinWidth(600);
            stage.setMinHeight(490);
        	ViewBookingController booking = (ViewBookingController) setScene("ViewNewBookings.fxml");
        	booking.initViewController(this);
        	booking.initDatabaseManager(databaseManager);
        	booking.setUsername(username);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Switch to the page for creating a booking.
     */
    public void gotoOwnerCreateBooking()
    {
        try {
        	CreateBookingController booking = (CreateBookingController) setScene("OwnerCreateBooking.fxml");
        	booking.initViewController(this);
        	booking.initDatabaseManager(databaseManager);
        	booking.setUsername(username);
        	booking.getEmployee();
        	booking.getService();
        	booking.SetHour();
        	booking.SetMinute();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Switch to the page for viewing all workers' availabilities.
     */
    public void gotoViewWorkers()
    {
        try {
            stage.setMinWidth(600);
            stage.setMinHeight(490);
        	ViewEmployeeController employee = (ViewEmployeeController) setScene("ShowAllWorkers.fxml");
        	employee.initViewController(this);
        	employee.initDatabaseManager(databaseManager);
        	employee.setUsername(username);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Switch to the page for customer creating a booking.
     */
    public void gotoCustomerCreateBooking()
    {
        try {
        	CustomerCreateBookingController customer = (CustomerCreateBookingController) setScene("CustomerCreateBooking.fxml");
        	customer.initViewController(this);
        	customer.initDatabaseManager(databaseManager);
        	customer.setUsername(username);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Customer checks booking availability.
     */
    public void gotoViewBookingAvailability(){
        try {
        	BookingAvailabilityController booking = (BookingAvailabilityController) setScene("ViewBookingAvailability.fxml");
        	booking.initViewController(this);
        	booking.initDatabaseManager(databaseManager);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Sets the scene to the supplied fxml document - Used for initially
     * creating the  stage with Main.fxml.
     * @param  fxml The filename of the view to switch to.
     * @return Returns the controller object to allow objects to be passed to
     * it.
     *
     * This was based on the tutorial FXML-LoginDemo provided by Oracle.
     */
    public Initializable createScene(String fxml) throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = getClass().getResourceAsStream("../view/"+fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(getClass().getResource("../view/"+fxml));
        Parent pane;
        try {
            pane = (Parent) loader.load(in);
        }
        finally {
            in.close();
        }
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        return (Initializable) loader.getController();
    }

    /* Switches the contents of the scene to another fxml document provided */
    public Initializable setScene(String fxml) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("../view/"+fxml));
        Node node = (Node) loader.load();
        /* Anchor to all sides */
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        container.getChildren().setAll(node);
        return (Initializable) loader.getController();
    }
}
