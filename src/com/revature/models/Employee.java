package com.revature.models;

public class Employee extends User{
	private int employeeID;
	private boolean isAdmin = false;
	

	public Employee(int userID, String firstname, String lastname, String username, String password, int SSN, int eID, boolean isAdmin) {
		super(userID, firstname, lastname, username, password, SSN);
		this.employeeID = eID;
		this.isAdmin = isAdmin;
	}


	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}
