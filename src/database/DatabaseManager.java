package database;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

	private ArrayList<String> username =  new ArrayList<String>();
	private ArrayList<String> password =  new ArrayList<String>();

	public DatabaseManager(ArrayList<String> username, ArrayList<String> password){
		this.username = username;
		this.password = password;
	}
	/**
	 * Match user's input username with data in business to find if the username is correct.
	 */
	public void retrieveBusinessUserName(){
		try{
			//connect to appointment booking system in the database
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS","root","root");
			//create a statement
			Statement statement = connection.createStatement();
			//get all tuples from business table
			ResultSet result = statement.executeQuery("select * from business");
			//
			while(result.next()){
				username.add(result.getString("username"));
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
	}

	/**
	 * Match user's input password with data in business to find if the password is correct.
	 */
	public void retrieveBusinessPassword(){
		try{
			//connect to appointment booking system in the database
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ABS","root","root");
			//create a statement
			Statement statement = connection.createStatement();
			//get all tuples from business table
			ResultSet result = statement.executeQuery("select * from business");
			//
			while(result.next()){
				password.add(result.getString("password"));
			}
		}
		catch (Exception exp){
			exp.printStackTrace();
		}
	}

}
