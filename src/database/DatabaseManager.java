package database;

import java.sql.*;
import java.util.ArrayList;

import model.ClientModel;

public class DatabaseManager {

	public DatabaseManager(){}

	/**
	 * This method will create a database in /Users/me/ using SQLite.
	 * @param fileName will be retrieved from clientModel, default will be AppointmentBookingSystem.
	 */
    public void createNewDatabase(String fileName) {
    	String username = System.getProperty("user.name");
        String url = "jdbc:sqlite:/Users/" + username + "/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println(fileName + " database has been created." + "\n");
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

    }

	/**
	 * Search business database to find if username and password exist and they are in the business name.
	 */
	public boolean searchBusiness(String username, String password){
		try{
			//connect to appointment booking system in the database
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS?autoReconnect=true&useSSL=false","root","root");
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
			//connect to appointment booking system in the database
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS?autoReconnect=true&useSSL=false","root","root");
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
			//connect to appointment booking system in the database
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS?autoReconnect=true&useSSL=false","root","root");
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
			//connect to appointment booking system in the database
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS?autoReconnect=true&useSSL=false","root","root");
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
	 * This function will insert username into customerinfo database
	 */
	public boolean insertIntoCustomer(String input, String input2){
		try{
			//connect to appointment booking system in the database
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS?autoReconnect=true&useSSL=false","root","root");
			//create a query for MySQL search
			final String insertData = "insert into customerinfo values ('" + input + "','" + input2 + "')";
			//create a statement
			final Statement ps = connection.createStatement();
			//get all tuples from business table that matches the input
			ps.executeUpdate(insertData);
			System.out.println("Your customer account has been successfully created!");
			return true;

		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}
}
