package com.revature.models;

public class AccountHolder {
	private int accountID;
	private int userID;
	
	
	public AccountHolder(int accountID, int userID) {
		super();
		this.accountID = accountID;
		this.userID = userID;
	}
	
	
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
}
