package com.revature.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.revature.models.User;
import com.revature.services.SQLServices;

public class UserDao {
	public static User getUser(String username, String password) {
		User user = null;
		
		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);
			Statement statement = connection.createStatement();
		    ResultSet set = statement.executeQuery("SELECT * FROM users WHERE username = '"+username+"';");
		){
			if(set != null && set.next()) {
				user = new User(
					set.getInt(1),
					set.getString(2),
					set.getString(3),
					set.getString(4),
					set.getString(5),
					set.getInt(6));
				if(!user.getPassword().equals(password)) {
					user = null;
				}
			}
		} catch (SQLException e1) {
			return null;
		}
		return user;
	}
	
	public static boolean createUser(User newUser) {
		String command = "INSERT INTO users(firstname, lastname, username, password, ssn) VALUES ('"+newUser.getFirstname()+"', '"+newUser.getLastname()+"', '"+newUser.getUsername()+"', '"+newUser.getPassword()+"', "+newUser.getSSN()+");";
		//System.out.println(command);

		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);){
			connection.createStatement().execute(command);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		
		return true;
	}
	public static boolean updateUser(int userID, User updatedUser) {
		String command = "UPDATE users SET firstname = '" + updatedUser.getFirstname() + "', lastname = '" + updatedUser.getLastname() + "', username = '"+updatedUser.getUsername()+"', password = '"+updatedUser.getPassword()+"', SSN = "+updatedUser.getSSN()+" WHERE userID = "+userID+";";

		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);){
			connection.createStatement().execute(command);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		
		return true;
	}
	public static boolean deleteUser(User deletedUser) {
		String command = "DELETE FROM users WHERE userID = "+deletedUser.getUserID()+";";

		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);){
			connection.createStatement().execute(command);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		
		return true;
	}
	public static boolean usernameAvailable(String username) {
		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);
				Statement statement = connection.createStatement();
			    ResultSet set = statement.executeQuery("SELECT * FROM users WHERE username = '"+username+"';");
			){
				if(set != null && set.next()) {
					return false;
				}
			} catch (SQLException e1) {
				return false;
			}
		return true;
	}
	public static User findByName(String fname, String lname) {
		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);
			Statement statement = connection.createStatement();
		    ResultSet set = statement.executeQuery("SELECT * FROM users WHERE firstname = '"+fname+"' AND lastname = '"+lname+"';");
		){
			if(set != null && set.next()) {
				return new User(set.getInt(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5), set.getInt(6));
			}
		} catch (SQLException e1) {
		}
		return null;
	}
	public static User findByID(int id) {
		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);
			Statement statement = connection.createStatement();
		    ResultSet set = statement.executeQuery("SELECT * FROM users WHERE userid = "+id+";");
		){
			if(set != null && set.next()) {
				return new User(set.getInt(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5), set.getInt(6));
			}
		} catch (SQLException e1) {
		}
		return null;
	}
}
