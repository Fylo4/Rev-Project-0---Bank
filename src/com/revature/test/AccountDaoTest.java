package com.revature.test;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.revature.models.Account;
import com.revature.repository.AccountDao;

public class AccountDaoTest {
	
	@Test
	void accountDaoTest() {
		//Create the account
		boolean accountCreation = AccountDao.createAccount(new Account(0, "checking", 10, "test", "pending"), 1);
		assert(accountCreation);
		
		//Check for pending accounts
		List<Account> pendingAccounts = AccountDao.getPendingAccounts();
		boolean foundTestAccount = false;
		Account testAccount = null;
		for(Account a : pendingAccounts) {
			if(a.getName().equals("test")) {
				foundTestAccount = true;
				testAccount = a;
			}
		}
		assert(foundTestAccount);
		
		//Modify the account
		testAccount.setName("test2");
		boolean updated = AccountDao.updateAccount(testAccount.getAccountID(), testAccount);
		assert(updated);
		
		//Add to all accounts
		boolean deposited = AccountDao.depositAll(10);
		assert(deposited);
		
		//Find the account data again
		pendingAccounts = AccountDao.getPendingAccounts();
		testAccount = null;
		for(Account a : pendingAccounts) {
			if(a.getName().equals("test2")) {
				testAccount = a;
			}
		}
		//If this passes, then the rename also worked
		assert(testAccount != null);
		
		//Ensure deposit worked'
		assert(testAccount.getBalance() == 20);
		
		//Delete the account
		boolean accountDeletion = AccountDao.deleteAccount(testAccount.getAccountID());
		assert(accountDeletion);
	}
}
