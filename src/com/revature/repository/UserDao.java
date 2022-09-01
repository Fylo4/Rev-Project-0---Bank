package com.revature.repository;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.revature.models.User;

public class UserDao {
	public static List<User> userData;

	static{
		userData = new LinkedList<User>();
		userData.add(new User(0, "first1", "last1", "user1", "pass1", 100000001));
		userData.add(new User(1, "first2", "last2", "user2", "pass2", 100000002));
		userData.add(new User(2, "first3", "last3", "user3", "pass3", 100000003));
	}
	
	
	/*public static User getUser(String username, String password) {
		
		User user = null;
		final String sql = "SELECT * FROM users WHERE username = '"+username+"';";
		
		try(Connection connection = DriverManager.getConnection(
				"jdbc:postgresql://p0-bank-database.cijrbngpbjo5.us-east-2.rds.amazonaws.com:5432/postgres", 
				"postgres", "postgres");
			Statement statement = connection.createStatement();)
		{
			ResultSet set = statement.executeQuery(sql);
			if(set.next()) {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}*/
	
	public static User createUser(User newUser) {
		if(newUser != null) {
			userData.add(newUser);
		}
		return newUser;
	}
	public static User getUser(String username, String password) {
		for(User u : userData) {
			if(u.getUsername().equals(username) && u.getPassword().equals(password)) {
				return u;
			}
		}
		return null;
	}
	public static User updateUser(User updatedUser) {
		return null;
	}
	public static boolean deleteUser(User deletedUser) {
		return false;
	}
	public static boolean usernameAvailable(String username) {
		for(User u : userData) {
			if(u.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}
	public static int getNextUserID() {
		int highest_id = 0;
		for(User u : userData) {
			if(u.getUserID() > highest_id) {
				highest_id = u.getUserID();
			}
		}
		return highest_id + 1;
	}
}
