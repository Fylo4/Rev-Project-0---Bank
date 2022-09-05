package com.revature.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.revature.models.User;

public class UserDao {
	private static final String url = "jdbc:postgresql://p0-bank-database.cijrbngpbjo5.us-east-2.rds.amazonaws.com:5432/postgres";
	private static final String un = "postgres";
	private static final String pass = "postgres";
	
	
	public static User getUser(String username, String password) {
		User user = null;
		
		try(Connection connection = DriverManager.getConnection(url, un, pass);
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
	
	public static User createUser(User newUser) {
		/*if(newUser != null) {
			userData.add(newUser);
		}
		return newUser;*/
		return null;
	}
	public static User updateUser(User updatedUser) {
		return null;
	}
	public static boolean deleteUser(User deletedUser) {
		return false;
	}
	public static boolean usernameAvailable(String username) {
		/*for(User u : userData) {
			if(u.getUsername().equals(username)) {
				return false;
			}
		}*/
		return true;
	}
	public static int getNextUserID() {
		int highest_id = 0;
		/*for(User u : userData) {
			if(u.getUserID() > highest_id) {
				highest_id = u.getUserID();
			}
		}*/
		return highest_id + 1;
	}
}
