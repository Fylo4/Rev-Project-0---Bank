package com.revature.repository;

import java.util.LinkedList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;

public class AccountDao {
	public List<Account> accountData;
	
	public AccountDao(){
		accountData = new LinkedList<Account>();
		accountData.add(new Account(0, "checking", 10.99, "u1 check", "open"));
		accountData.add(new Account(1, "checking", 10.99, "u2 check", "open"));
		accountData.add(new Account(2, "savings", 10.99, "u2 save", "open"));
		accountData.add(new Account(3, "savings", 10.99, "u2 retirement", "open"));
	}
}
