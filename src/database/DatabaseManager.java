package database;

import java.sql.*;
import java.util.ArrayList;

import model.ClientModel;

public class DatabaseManager {

	public DatabaseManager(){}

	/**
	 * Search business database to find if username and password exist and they are in the same row.
	 */
	public boolean searchBusiness(String username, String password){
		try{
			//connect to appointment booking system in the database
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS","root","root");
			//create a query searching username
			final String userCheck = "select * from business where username = '" + username + "'";
			//create a query to find if the password matches username
			final String passwordCheck = "select * from business where password = '" + password + "' and username = '" + username + "'";
			//create a statement
			final Statement ps = connection.createStatement();
			//get all tuples from business table that matches the input
			ResultSet result1 = ps.executeQuery(userCheck);
			ResultSet result2 = ps.executeQuery(passwordCheck);
			if(result1.next() && result2.next()){
				return true;
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
		return false;
	}



	public boolean searchBusinessUserName(String input){
		try{
			//connect to appointment booking system in the database
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS","root","root");
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
	 * Match user's input password with data in business to find if the password is correct.
	 */
	public boolean searchBusinessPassword(String input){
		try{
			//connect to appointment booking system in the database
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS","root","root");
			//create a query for MySQL search
			final String passwordCheck = "select * from business where password = '" + input + "'";
			//create a statement
			final Statement ps = connection.createStatement();
			//get all tuples from business table that matches the input
			final ResultSet result = ps.executeQuery(passwordCheck);
			while(result.next()){
				input = result.getString("password");
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
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS","root","root");
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
	 * Match user's input password with data in customerinfo to find if the password is correct.
	 */
	public boolean searchCustomerPassword(String input){
		try{
			//connect to appointment booking system in the database
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS","root","root");
			//create a query for MySQL search
			final String passwordCheck = "select * from customerinfo where password = '" + input + "'";
			//create a statement
			final Statement ps = connection.createStatement();
			//get all tuples from business table that matches the input
			final ResultSet result = ps.executeQuery(passwordCheck);
			while(result.next()){
				input = result.getString("password");
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
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS","root","root");
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

	/**
	 * The function will insert password into customerinfo database
	 */

}
