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

   	String username = System.getProperty("user.name");
    String path = "jdbc:sqlite:/Users/" + username + "/" + "AppointmentBookingSystem.db";
    Connection c = null;
    Statement stmt = null;

	public DatabaseManager(){}

	ViewController view = new ViewController();

	/**
	 * delete existing database in local files, the path is /Users/'username'/AppointmentBookingSystem.db
	 */
	public void deleteDatabase(){
        String url = "/Users/" + username + "/" + "AppointmentBookingSystem.db";
        File f = new File(url);
    	f.delete();
    	view.add("DatabaseManager", "The existing database has been deleted.");
	}


	/**
	 * Establish connection to the database, the connection will be only created once.
	 */
	public void setConnection(){
		try{
			c = DriverManager.getConnection(path);
			stmt = c.createStatement();
			view.add("DatabaseManager", "The connection to the database has been established.");
		}
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

	/**
	 * This method will create a database in /Users/me/ using SQLite.
	 * @param fileName will be retrieved from clientModel, default will be AppointmentBookingSystem.
	 */
    public void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:/Users/" + username + "/" + fileName;

        try ( Connection con = DriverManager.getConnection(url)) {
            if (con != null) {
            	view.add("DatabaseManager", fileName + " database has been created.");
            	con.close();
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
        try {
          String sql = "CREATE TABLE Business " +
                       "(business_name	        TEXT	NOT NULL," +
                       " business_owner_name	TEXT, " +
                       " address            	TEXT, " +
                       " phone        			TEXT, " +
                       " business_hours        	TEXT, " +
                       " username        		TEXT     PRIMARY KEY     NOT NULL, " +
                       " password         		TEXT     NOT NULL)";
          stmt.executeUpdate(sql);
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
        try {
          c.setAutoCommit(false);
          String sql = "INSERT INTO Business (business_name,business_owner_name,address,phone,business_hours,username,password) " +
                       "VALUES ('Da Guido Melbourne la Pasta', 'Williams','130 Lygon St, Carlton, Victoria 3053', '+61 3 8528 4547', '11:00 - 9:00 Monday to Sunday', 'daguido','daguido' );";
          stmt.executeUpdate(sql);

          sql = "INSERT INTO Business (business_name,business_owner_name,address,phone,business_hours,username,password) " +
                "VALUES ('TONI&GUY Georges', 'Tom', '195 Little Collins St, Melbourne, Victoria 3000', '(03) 9654 9444', '9:00 - 20:00 Monday to Saturday', 'toniguy', 'toniguy');";
          stmt.executeUpdate(sql);

          sql = "INSERT INTO Business (business_name,business_owner_name,address,phone,business_hours,username,password) " +
                "VALUES ('System Testing Account',null,null,null, '9:00 - 6:00 Monday to Friday', 'owner','owner');";
          stmt.executeUpdate(sql);
          c.commit();

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
        try {
          String sql = "CREATE TABLE Customerinfo " +
                       "(first_name	        TEXT     NOT NULL," +
                       " last_name			TEXT     NOT NULL, " +
                       " address            TEXT, " +
                       " contact_number     TEXT, " +
                       " username        	TEXT     PRIMARY KEY     NOT NULL, " +
                       " password         	TEXT     NOT NULL)";
          stmt.executeUpdate(sql);

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
        try {
          c.setAutoCommit(false);
          String sql = "INSERT INTO Customerinfo (first_name,last_name,address,contact_number,username,password) " +
                       "VALUES ('David', 'Beckham','London', '0123 456 789', 'david','david' );";
          stmt.executeUpdate(sql);

          sql = "INSERT INTO Customerinfo (first_name,last_name,address,contact_number,username,password) " +
                "VALUES ('Bruce', 'Wayne', 'New York City', '0345 689 910', 'bruce', 'bruce');";
          stmt.executeUpdate(sql);

          sql = "INSERT INTO Customerinfo (first_name,last_name,address,contact_number,username,password) " +
                "VALUES ('" + username + "','" + username + "',null,null,'customer','customer');";
          stmt.executeUpdate(sql);
          c.commit();
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
			final String business = "select * from Business where username = '" + username + "' and password = '" + password + "'";
			ResultSet result = stmt.executeQuery(business);
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
	 *
	 * @return true if username exists in the customerinfo database and password matches username in the same row
	 */
	public boolean searchCustomer(String username, String password){
		try{
			final String customer = "select * from Customerinfo where username = '" + username + "' and password = '" + password + "'";
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
			final String userCheck = "select * from Business where username = '" + input + "'";
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
			final String userCheck = "select * from Customerinfo where username = '" + input + "'";
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
        try {
          c.setAutoCommit(false);
          String sql = "INSERT INTO Customerinfo (first_name,last_name,address,contact_number,username,password) " +
                       "VALUES ('"+ firstname +"', '"+ lastname +"','"+ address +"', '"+ contactNumber +"', '"+ username +"','"+ password +"' );";
          stmt.executeUpdate(sql);
          c.commit();
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
	        String businessName;
			//create a query for SQLite search
			final String getBusiness = "select business_name from Business where username = '" + username + "'";
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
	        String firstname;
	        String lastname;
			//create a query for SQLite search
			final String getName = "select * from Customerinfo where username = '" + username + "'";
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
	        String workingHours;
			final String get = "select working_hours from Business where username = '" + username + "'";
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
    public void createEmployeeTable(){
        try {
          String sql = "CREATE TABLE Employee " +
                       "(firstname	        TEXT	NOT NULL," +
                       " lastname			TEXT	NOT NULL, " +
                       " owner_username     TEXT	NOT NULL, " +
                       " email			    TEXT 		 PRIMARY KEY     NOT NULL, " +
                       " contact_number     TEXT, " +
                       "FOREIGN KEY(owner_username)	REFERENCES Business(username))";
          stmt.executeUpdate(sql);
      	  view.add("DatabaseManager", "Employee table has been created.");
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }


    /**
     * set new employee in database
     */
    public void setEmployee(String firstname, String lastname, String owner_username, String email, String contact_number){
        try {
          String sql = "INSERT INTO Employee (first_name,last_name,owner_username,email,contact_number) " +
                  "VALUES ('"+ firstname +"', '"+ lastname +"','"+ owner_username +"', '"+ email +"', '"+ contact_number +"');";
          stmt.executeUpdate(sql);
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    	view.add("DatabaseManager", "A new employee has been inserted into Employee.");
    }


    /**
     * create new table BusinessTime, store each business owner's business date and time.
     */
    public void createBusinessTimeTable(){
        try {
          String sql = "CREATE TABLE BusinessTime " +
                       "(business_date	    TEXT	PRIMARY KEY	NOT NULL," +
                       " owner_username     TEXT	NOT NULL, " +
                       " open_time     		TEXT	NOT NULL, " +
                       " closing_time       TEXT	NOT NULL, " +
                       "FOREIGN KEY(owner_username)	REFERENCES Business(username))";
          stmt.executeUpdate(sql);
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    	view.add("DatabaseManager", "BusinessTime table has been created.");
    }


    /**
     * set business time for a business owner.
     */
    public void setBusinessTime(String business_date, String owner_username, String open_time, String closing_time){
        try {
          c.setAutoCommit(false);
          String sql = "INSERT INTO BusinessTime (business_date,owner_username,open_time,closing_time) " +
                  "VALUES ('"+ business_date +"', '"+ owner_username +"','"+ open_time +"', '"+ closing_time +"');";
          stmt.executeUpdate(sql);
          c.commit();
      	  view.add("DatabaseManager", "New business time has been inserted into BusinessTime.");
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }


    /**
     * create new table WorkingTime, store each employee's working time in a week, user day as primary key, email as foreign key.
     */
    public void createWorkingTimeTable(){
        try {
          String sql = "CREATE TABLE WorkingTime " +
                       "(day	 	 	TEXT	PRIMARY KEY	NOT NULL," +
                       " time	     	TEXT	NOT NULL, " +
                       " employee_email	TEXT	NOT NULL, " +
                       "FOREIGN KEY(employee_email)	REFERENCES Employee(email))";
          stmt.executeUpdate(sql);
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    	view.add("DatabaseManager", "WorkingTime table has been created.");
    }


    /**
     * set working time for an employee.
     */
    public void setWorkingTime(String day, String time, String employee_email){
        try {
          String sql = "INSERT INTO WorkingTime (day,time,employee_email) " +
                  "VALUES ('"+ day +"', '"+ time +"','"+ employee_email +"');";
          stmt.executeUpdate(sql);
        } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    	view.add("DatabaseManager", "New working time has been inserted into WorkingTime.");
    }
}
