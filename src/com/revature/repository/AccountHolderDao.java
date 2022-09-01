package com.revature.repository;

import java.util.List;

import com.revature.models.AccountHolder;

public class AccountHolderDao {
	List<AccountHolder> accountHolderData;
	
	public AccountHolderDao() {
		accountHolderData.add(new AccountHolder(0, 0)); //Account, user
		accountHolderData.add(new AccountHolder(1, 1));
		accountHolderData.add(new AccountHolder(2, 1));
		accountHolderData.add(new AccountHolder(3, 1));
	}
}
