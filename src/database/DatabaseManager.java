package database;

import java.io.File;
import java.sql.*;

public class DatabaseManager {

	public DatabaseManager(){}


	/**
	 * delete existing database in local files, the path is /Users/'username'/AppointmentBookingSystem.db
	 */
	public void deleteDatabase(){
       	String username = System.getProperty("user.name");
        String url = "/Users/" + username + "/" + "AppointmentBookingSystem.db";
        File f = new File(url);
    	f.delete();
    	System.out.println("Existing database have been deleted.");
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
                System.out.println(fileName + " database has been created.");
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
          String sql = "CREATE TABLE business " +
                       "(business_name	        TEXT," +
                       " business_owner_name	TEXT, " +
                       " address            	TEXT, " +
                       " phone        			TEXT, " +
                       " username        		TEXT     PRIMARY KEY     NOT NULL, " +
                       " password         		TEXT     NOT NULL)";
          stmt.executeUpdate(sql);
          stmt.close();
          c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        System.out.println("Business table has been created.");
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
          String sql = "INSERT INTO business (business_name,business_owner_name,address,phone,username,password) " +
                       "VALUES ('Da Guido Melbourne la Pasta', 'Williams','130 Lygon St, Carlton, Victoria 3053', '+61 3 8528 4547', 'daguido','daguido' );";
          stmt.executeUpdate(sql);

          sql = "INSERT INTO business (business_name,business_owner_name,address,phone,username,password) " +
                "VALUES ('TONI&GUY Georges', 'Tom', '195 Little Collins St, Melbourne, Victoria 3000', '(03) 9654 9444', 'toniguy', 'toniguy');";
          stmt.executeUpdate(sql);

          sql = "INSERT INTO business (business_name,business_owner_name,address,phone,username,password) " +
                "VALUES (null,null,null,null,'admin','admin');";
          stmt.executeUpdate(sql);

          stmt.close();
          c.commit();
          c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        System.out.println("Business entities have been inserted successfully.");
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
          String sql = "CREATE TABLE customerinfo " +
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
        System.out.println("Customerinfo table has been created.");
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
          String sql = "INSERT INTO customerinfo (first_name,last_name,address,contact_number,username,password) " +
                       "VALUES ('David', 'Beckham','London', '0123 456 789', 'david','david' );";
          stmt.executeUpdate(sql);

          sql = "INSERT INTO customerinfo (first_name,last_name,address,contact_number,username,password) " +
                "VALUES ('Bruce', 'Wayne', 'New York City', '0345 689 910', 'bruce', 'bruce');";
          stmt.executeUpdate(sql);

          sql = "INSERT INTO customerinfo (first_name,last_name,address,contact_number,username,password) " +
                "VALUES ('Test','Test',null,null,'customer','customer');";
          stmt.executeUpdate(sql);

          stmt.close();
          c.commit();
          c.close();
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
        System.out.println("Business entities have been inserted successfully.");
    }


	/**
	 * Search business database to find if username and password exist and they are in the business name.
	 */
	public boolean searchBusiness(String username, String password){
		try{
	    	String user = System.getProperty("user.name");
	        String url = "jdbc:sqlite:/Users/" + user + "/" + "AppointmentBookingSystem.db";

			//connect to appointment booking system in the database
			Connection connection = DriverManager.getConnection(url);
			//create a query searching username and password
			final String business = "select * from business where username = '" + username + "' and password = '" + password + "'";
			//create a statement
			final Statement ps = connection.createStatement();
			//get all tuples from business table that matches the input
			ResultSet result1 = ps.executeQuery(business);
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
			Connection connection = DriverManager.getConnection(url);
			//create a query searching username and password
			final String customer = "select * from customerinfo where username = '" + username + "' and password = '" + password + "'";
			//create a statement
			final Statement ps = connection.createStatement();
			//get all tuples from customer table that matches the input
			ResultSet result = ps.executeQuery(customer);
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
			Connection connection = DriverManager.getConnection(url);
			//create a query for MySQL search
			final String userCheck = "select * from business where username = '" + input + "'";
			//create a statement
			final Statement ps = connection.createStatement();
			//get all tuples from business table that matches the input
			final ResultSet result = ps.executeQuery(userCheck);
			if(result.next()){
				input = result.getString("username");
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
			Connection connection = DriverManager.getConnection(url);
			//create a query for MySQL search
			final String userCheck = "select * from customerinfo where username = '" + input + "'";
			//create a statement
			final Statement ps = connection.createStatement();
			//get all tuples from business table that matches the input
			final ResultSet result = ps.executeQuery(userCheck);
			if(result.next()){
				input = result.getString("username");
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
	public boolean insertIntoCustomer(String username, String password, String firstname, String lastname,
			String address, String contactNumber){
		try{
	    	String user = System.getProperty("user.name");
	        String url = "jdbc:sqlite:/Users/" + user + "/" + "AppointmentBookingSystem.db";

			//connect to appointment booking system in the database
			Connection c = DriverManager.getConnection(url);
			Statement ps = c.createStatement();

			//create a query for MySQL search
	        String sql = "INSERT INTO customerinfo (first_name,last_name,address,contact_number,username,password) " +
	                  "VALUES ('" + firstname + "','" + lastname + "','" + address + "',''" + contactNumber + "'','" + username + "','" + password + "');";

			ps.executeUpdate(sql);
			System.out.println("Your customer account has been successfully created!");
			return true;

		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}
}
