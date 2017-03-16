package database;

import java.sql.*;
import java.util.ArrayList;

import model.ClientModel;

public class DatabaseManager {

	public DatabaseManager(){}

	/**
	 * Match user's input username with data in business to find if the username is correct.
	 */
	public Boolean searchBusinessUserName(String input){
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
	public Boolean searchBusinessPassword(String input){
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

}
