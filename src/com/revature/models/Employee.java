package com.revature.models;

public class Employee extends User{
	protected int EmployeeID;
	protected int UserID;
	protected boolean IsAdmin = false;
	

	public Employee(int userID, String firstname, String lastname, String username, String password, int sSN) {
		super(userID, firstname, lastname, username, password, sSN);
		// TODO Auto-generated constructor stub
	}
	
}
