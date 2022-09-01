package com.revature.repository;

import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import com.revature.models.User;

public class UserDao {
	public List<User> userData;

	public UserDao(){
		userData = new LinkedList<User>();
		userData.add(new User(0, "first1", "last1", "user1", "pass1", 100000001));
		userData.add(new User(1, "first2", "last2", "user2", "pass2", 100000002));
		userData.add(new User(2, "first3", "last3", "user3", "pass3", 100000003));
	}
	
	public User createUser(User newUser) {
		if(newUser != null) {
			userData.add(newUser);
		}
		return newUser;
	}
	public User getUser(String username, String password) {
		for(User u : userData) {
			if(u.getUsername().equals(username) && u.getPassword().equals(password)) {
				return u;
			}
		}
		return null;
	}
	public User updateUser(User updatedUser) {
		return null;
	}
	public boolean deleteUser(User deletedUser) {
		return false;
	}
	public boolean usernameAvailable(String username) {
		for(User u : userData) {
			if(u.getUsername().equals(username)) {
				return false;
			}
		}
		return true;
	}
	public int getNextUserID() {
		int highest_id = 0;
		for(User u : userData) {
			if(u.getUserID() > highest_id) {
				highest_id = u.getUserID();
			}
		}
		return highest_id + 1;
	}
}
