package com.revature.repository;

import java.util.LinkedList;
import java.util.List;

import com.revature.models.Account;

public class AccountDao {
	public static List<Account> accountData;
	
	static{
		accountData = new LinkedList<Account>();
		accountData.add(new Account(0, "checking", 10.99, "u1 check", "open"));
		accountData.add(new Account(1, "checking", 10.99, "u2 check", "open"));
		accountData.add(new Account(2, "savings", 10.99, "u2 save", "open"));
		accountData.add(new Account(3, "savings", 10.99, "u2 retirement", "open"));
	}
	
	public static Account getAccountById(int id){
		for(Account a : accountData) {
			if(a.getAccountID() == id) {
				return a;
			}
		}
		return null;
	}
}
