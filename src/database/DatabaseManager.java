package database;

import java.io.File;
import java.sql.*;

import controller.ViewController;

/**
 * Manage databases include delete database if existed, create a AppointmentBookingSystem database,
 * create Business and Customerinfo tables, insert initial entities for them, search username or password,
 * and update new customer in the Customerinfo.
 * @author ranlu
 *
 */
public class DatabaseManager {

	public DatabaseManager(){}

	ViewController view = new ViewController();

	/**
	 * delete existing database in local files, the path is /Users/'username'/AppointmentBookingSystem.db
	 */
	public void deleteDatabase(){
       	String username = System.getProperty("user.name");
        String url = "/Users/" + username + "/" + "AppointmentBookingSystem.db";
        File f = new File(url);
    	f.delete();
    	view.add("DatabaseManager", "The existing database has been deleted.");
	}


	/**
	 * This method will create a database in /Users/me/ using SQLite.
	 * @param fileName will be retrieved from clientModel, default will be AppointmentBookingSystem.
	 */
    public void createNewDatabase(String fileName) {
    	String username = System.getProperty("user.name");
        String url = "jdbc:sqlite:/Users/" + username + "/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
            	view.add("DatabaseManager", fileName + " database has been created.");
            	conn.close();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * create new table business
     */
    public void createBusinessTable(){
        Connection c = null;
        Statement stmt = null;
    	String username = System.getProperty("user.name");
        String url = "jdbc:sqlite:/Users/" + username + "/" + "AppointmentBookingSystem.db";

        try {
          c = DriverManager.getConnection(url);
          stmt = c.createStatement();
          String sql = "CREATE TABLE Business " +
                       "(business_name	        TEXT," +
                       " business_owner_name	TEXT, " +
                       " address            	TEXT, " +
                       " phone        			TEXT, " +
                       " business_hours        	TEXT, " +
                       " username        		TEXT     PRIMARY KEY     NOT NULL, " +
                       " password         		TEXT     NOT NULL)";
          stmt.executeUpdate(sql);
          stmt.close();
          c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    	view.add("DatabaseManager", "Business table has been created.");
    }


    /**
     * Insert entities for business table.
     */
    public void insertInitialEntitiesForBusiness(){
        Connection c = null;
        Statement stmt = null;
       	String username = System.getProperty("user.name");
        String url = "jdbc:sqlite:/Users/" + username + "/" + "AppointmentBookingSystem.db";

        try {
          c = DriverManager.getConnection(url);
          c.setAutoCommit(false);

          stmt = c.createStatement();
          String sql = "INSERT INTO Business (business_name,business_owner_name,address,phone,business_hours,username,password) " +
                       "VALUES ('Da Guido Melbourne la Pasta', 'Williams','130 Lygon St, Carlton, Victoria 3053', '+61 3 8528 4547', '11:00 - 9:00 Monday to Sunday', 'daguido','daguido' );";
          stmt.executeUpdate(sql);

          sql = "INSERT INTO Business (business_name,business_owner_name,address,phone,business_hours,username,password) " +
                "VALUES ('TONI&GUY Georges', 'Tom', '195 Little Collins St, Melbourne, Victoria 3000', '(03) 9654 9444', '9:00 - 20:00 Monday to Saturday', 'toniguy', 'toniguy');";
          stmt.executeUpdate(sql);

          sql = "INSERT INTO Business (business_name,business_owner_name,address,phone,business_hours,username,password) " +
                "VALUES ('System Testing Account',null,null,null, '9:00 - 6:00 Monday to Friday', 'owner','owner');";
          stmt.executeUpdate(sql);

          stmt.close();
          c.commit();
          c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    	view.add("DatabaseManager", "Business entities have been inserted.");
    }


    /**
     * create new table customerinfo
     */
    public void createCustomerInfoTable(){
        Connection c = null;
        Statement stmt = null;
    	String username = System.getProperty("user.name");
        String url = "jdbc:sqlite:/Users/" + username + "/" + "AppointmentBookingSystem.db";

        try {
          c = DriverManager.getConnection(url);
          stmt = c.createStatement();
          String sql = "CREATE TABLE Customerinfo " +
                       "(first_name	        TEXT     NOT NULL," +
                       " last_name			TEXT     NOT NULL, " +
                       " address            TEXT, " +
                       " contact_number     TEXT, " +
                       " username        	TEXT     PRIMARY KEY     NOT NULL, " +
                       " password         	TEXT     NOT NULL)";
          stmt.executeUpdate(sql);
          stmt.close();
          c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    	view.add("DatabaseManager", "Customerinfo table has been created.");
    }


    /**
     * Insert entities for customerinfo table.
     */
    public void insertInitialEntitiesForCustomerInfo(){
        Connection c = null;
        Statement stmt = null;
       	String username = System.getProperty("user.name");
        String url = "jdbc:sqlite:/Users/" + username + "/" + "AppointmentBookingSystem.db";

        try {
          c = DriverManager.getConnection(url);
          c.setAutoCommit(false);

          stmt = c.createStatement();
          String sql = "INSERT INTO Customerinfo (first_name,last_name,address,contact_number,username,password) " +
                       "VALUES ('David', 'Beckham','London', '0123 456 789', 'david','david' );";
          stmt.executeUpdate(sql);

          sql = "INSERT INTO Customerinfo (first_name,last_name,address,contact_number,username,password) " +
                "VALUES ('Bruce', 'Wayne', 'New York City', '0345 689 910', 'bruce', 'bruce');";
          stmt.executeUpdate(sql);

          sql = "INSERT INTO Customerinfo (first_name,last_name,address,contact_number,username,password) " +
                "VALUES ('" + username + "','" + username + "',null,null,'customer','customer');";
          stmt.executeUpdate(sql);

          stmt.close();
          c.commit();
          c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    	view.add("DatabaseManager", "Customerinfo entities have been inserted.");
    }


	/**
	 * Search business database to find if username and password exist and they are in the business name.
	 */
	public boolean searchBusiness(String username, String password){
		try{
	    	String user = System.getProperty("user.name");
	        String url = "jdbc:sqlite:/Users/" + user + "/" + "AppointmentBookingSystem.db";

			//connect to appointment booking system in the database
			Connection c = DriverManager.getConnection(url);
			//create a query searching username and password
			final String business = "select * from Business where username = '" + username + "' and password = '" + password + "'";
			//create a statement
			final Statement stmt = c.createStatement();
			//get all tuples from business table that matches the input
			ResultSet result1 = stmt.executeQuery(business);

			if(result1.next()){
				return true;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}


	/**
	 *
	 * @return true if username exists in the customerinfo database and password matches username in the same row
	 */
	public boolean searchCustomer(String username, String password){
		try{
	    	String user = System.getProperty("user.name");
	        String url = "jdbc:sqlite:/Users/" + user + "/" + "AppointmentBookingSystem.db";

			//connect to appointment booking system in the database
			Connection c = DriverManager.getConnection(url);

			//create a query searching username and password
			final String customer = "select * from Customerinfo where username = '" + username + "' and password = '" + password + "'";
			//create a statement
			final Statement stmt = c.createStatement();
			//get all tuples from customer table that matches the input
			ResultSet result = stmt.executeQuery(customer);

			if(result.next()){
				return true;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}


	/**
	 * Search business database to find if input username exists
	 * @param input retrieved from clientModel
	 * @return true if username exists in the business database
	 */
	public boolean searchBusinessUserName(String input){
		try{
	    	String user = System.getProperty("user.name");
	        String url = "jdbc:sqlite:/Users/" + user + "/" + "AppointmentBookingSystem.db";

			//connect to appointment booking system in the database
			Connection c = DriverManager.getConnection(url);
			//create a query for MySQL search
			final String userCheck = "select * from Business where username = '" + input + "'";
			//create a statement
			final Statement stmt = c.createStatement();
			//get all tuples from business table that matches the input
			final ResultSet result = stmt.executeQuery(userCheck);


			if(result.next()){
				return true;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}



	/**
	 * Match user's input username with data in customerinfo to find if the username is correct.
	 */
	public boolean searchCustomerUserName(String input){
		try{
	    	String user = System.getProperty("user.name");
	        String url = "jdbc:sqlite:/Users/" + user + "/" + "AppointmentBookingSystem.db";

			//connect to appointment booking system in the database
			Connection c = DriverManager.getConnection(url);
			//create a query for MySQL search
			final String userCheck = "select * from Customerinfo where username = '" + input + "'";
			//create a statement
			final Statement stmt = c.createStatement();
			//get all tuples from business table that matches the input
			final ResultSet result = stmt.executeQuery(userCheck);

			if(result.next()){
				return true;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}


	/**
	 * Insert username, password, firstname, lastname,
	 *  address and contact number into customerinfo database
	 */
    public boolean insertIntoCustomer(String firstname, String lastname,
			String address, String contactNumber, String username, String password){
        Connection c = null;
        Statement stmt = null;
       	String user = System.getProperty("user.name");
        String url = "jdbc:sqlite:/Users/" + user + "/" + "AppointmentBookingSystem.db";

        try {
          c = DriverManager.getConnection(url);
          c.setAutoCommit(false);

          stmt = c.createStatement();
          String sql = "INSERT INTO Customerinfo (first_name,last_name,address,contact_number,username,password) " +
                       "VALUES ('"+ firstname +"', '"+ lastname +"','"+ address +"', '"+ contactNumber +"', '"+ username +"','"+ password +"' );";
          stmt.executeUpdate(sql);

          stmt.close();
          c.commit();
          c.close();
          return true;
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        return false;
    }


    /**
     * Get business name for a given username in the business table.
     * @param username received from cilentModel, pass to the database.
     * @return business name for the given username.
     */
	public String getBusinessName(String username){
		try{
	    	String user = System.getProperty("user.name");
	        String url = "jdbc:sqlite:/Users/" + user + "/" + "AppointmentBookingSystem.db";
	        String businessName;

			//connect to appointment booking system in the database
			Connection c = DriverManager.getConnection(url);
			//create a query for SQLite search
			final String getBusiness = "select business_name from Business where username = '" + username + "'";
			//create a statement
			final Statement stmt = c.createStatement();
			//get all tuples from business table that matches the input
			final ResultSet result = stmt.executeQuery(getBusiness);

			if(result.next()){
				businessName = result.getString("business_name");
				return businessName;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return null;
	}


    /**
     * Get name for a given username in the customer table.
     * @param username received from cilentModel, pass to the database.
     * @return name for the given username.
     */
	public String getCustomerName(String username){
		try{
	    	String user = System.getProperty("user.name");
	        String url = "jdbc:sqlite:/Users/" + user + "/" + "AppointmentBookingSystem.db";
	        String firstname;
	        String lastname;
			//connect to appointment booking system in the database
			Connection c = DriverManager.getConnection(url);
			//create a query for SQLite search
			final String getName = "select * from Customerinfo where username = '" + username + "'";
			//create a statement
			final Statement stmt = c.createStatement();
			//get all tuples from business table that matches the input
			final ResultSet result = stmt.executeQuery(getName);
			if(result.next()){
				firstname = result.getString("first_name");
				lastname = result.getString("last_name");
				return firstname + " " + lastname;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return null;
	}


    /**
     * Get predefined business hours for a given business in the Business table.
     * @param username received from cilentModel, pass to the database.
     * @return business hours in a week for the given business.
     */
	public String getBusinessHours(String username){
		try{
	    	String user = System.getProperty("user.name");
	        String url = "jdbc:sqlite:/Users/" + user + "/" + "AppointmentBookingSystem.db";
	        String workingHours;

			//connect to appointment booking system in the database
			Connection c = DriverManager.getConnection(url);
			//create a query for SQLite search
			final String get = "select working_hours from Business where username = '" + username + "'";
			//create a statement
			final Statement stmt = c.createStatement();
			//get all tuples from business table that matches the input
			final ResultSet result = stmt.executeQuery(get);
			if(result.next()){
				workingHours = result.getString("working_hours");
				return workingHours;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return null;
	}


    /**
     * create new table EmployeeList
     */
    public void createEmployeeListTable(){
        Connection c = null;
        Statement stmt = null;
    	String username = System.getProperty("user.name");
        String url = "jdbc:sqlite:/Users/" + username + "/" + "AppointmentBookingSystem.db";

        try {
          c = DriverManager.getConnection(url);
          stmt = c.createStatement();
          String sql = "CREATE TABLE EmployeeList " +
                       "(firstname	        TEXT," +
                       " lastname			TEXT, " +
                       " owner_username     TEXT	NOT NULL, " +
                       " email			    TEXT 		 PRIMARY KEY     NOT NULL, " +
                       " contact_number     TEXT, " +
                       " Monday        		TEXT, " +
                       " Tuesday       		TEXT, " +
                       " Wednesday        	TEXT, " +
                       " Thursday        	TEXT, " +
                       " Friday        		TEXT, " +
                       " Saturday        	TEXT, " +
                       " Sunday         	TEXT, "	+
                       "FOREIGN KEY(owner_username)	REFERENCES Business(username))";
          stmt.executeUpdate(sql);
          stmt.close();
          c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    	view.add("DatabaseManager", "EmployeeList table has been created.");
    }


    /**
     * create new table BusinessHours, store each business owner's business date and time.
     */
    public void createBusinessHoursTable(){
        Connection c = null;
        Statement stmt = null;
    	String username = System.getProperty("user.name");
        String url = "jdbc:sqlite:/Users/" + username + "/" + "AppointmentBookingSystem.db";

        try {
          c = DriverManager.getConnection(url);
          stmt = c.createStatement();
          String sql = "CREATE TABLE BusinessHours " +
                       "(business_date	    TEXT	PRIMARY KEY	NOT NULL," +
                       " owner_username     TEXT	NOT NULL, " +
                       " open_time     		TEXT	NOT NULL, " +
                       " closing_time       TEXT	NOT NULL, " +
                       "FOREIGN KEY(owner_username)	REFERENCES Business(username))";
          stmt.executeUpdate(sql);
          stmt.close();
          c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    	view.add("DatabaseManager", "BusinessHours table has been created.");
    }
}
