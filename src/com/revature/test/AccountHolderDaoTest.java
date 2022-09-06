package com.revature.test;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.revature.models.Account;
import com.revature.repository.AccountHolderDao;

public class AccountHolderDaoTest {

	@Test
	void getAccountsTest() {
		List<Account> aliceAccounts = AccountHolderDao.getAccountsByUser(1);
		assert(aliceAccounts.get(0).getName().equals("Alice's Checking"));
	}
	
	@Test
	void getAccountsFailTest() {
		List<Account> blankList = AccountHolderDao.getAccountsByUser(-1);
		assert(blankList != null);
		assert(blankList.size() == 0);
	}
}
