package com.revature.models;

public class User {
	protected int userID;
	protected String firstname;
	protected String lastname;
	protected String username;
	protected String password;
	protected int SSN;
	
	//Constructor	
	public User(int userID, String firstname, String lastname, String username, String password, int SSN) {
		super();
		this.userID = userID;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.SSN = SSN;
	}
	
	//Getters and setters
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getSSN() {
		return SSN;
	}
	public void setSSN(int SSN) {
		this.SSN = SSN;
	}
	
}
