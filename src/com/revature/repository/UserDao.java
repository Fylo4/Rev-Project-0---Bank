package com.revature.repository;

import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;

import com.revature.models.User;

public class UserDao {
	public List<User> userData;

	UserDao(){
		userData = new LinkedList<User>();
		userData.add(new User(0, "first1", "last1", "user1", "pass1", 100000001));
		userData.add(new User(1, "first2", "last2", "user2", "pass2", 100000002));
		userData.add(new User(2, "first3", "last3", "user3", "pass3", 100000003));
	}
	
	public User createUser(User newUser) {
		return null;
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
}
