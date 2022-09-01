package com.revature.models;

public class Account {
	private int accountID;
	private String type; //checking, savings
	private double balance;
	private String name; //Name of the account, e.g. "checking-1"
	private String state; //open, closed, pending
	
	//Constructor
	public Account(int accountID, String type, double balance, String name, String state) {
		super();
		this.accountID = accountID;
		this.type = type;
		this.balance = balance;
		this.name = name;
		this.state = state;
	}

	
	//Getters and setters
	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
