package com.revature.repository;

import java.util.LinkedList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountHolder;

public class AccountHolderDao {
	public static List<AccountHolder> accountHolderData;
	
	static{
		accountHolderData = new LinkedList<>();
		accountHolderData.add(new AccountHolder(0, 0)); //Account, user
		accountHolderData.add(new AccountHolder(1, 1));
		accountHolderData.add(new AccountHolder(2, 1));
		accountHolderData.add(new AccountHolder(3, 1));
	}
	
	public static List<Account> getAccountsByUser(int userID) {
		List<Account> ret = new LinkedList<Account>();
		for(AccountHolder a : accountHolderData) {
			if(a.getUserID() == userID){
				ret.add(AccountDao.getAccountById(a.getAccountID()));
			}
		}
		return ret;
	}
}
