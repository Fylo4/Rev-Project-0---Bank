package com.revature.repository;

import java.util.LinkedList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountHolder;

public class AccountDao {
	public static List<Account> accountData;
	
	static{
		accountData = new LinkedList<Account>();
		accountData.add(new Account(0, "checking", 10.99, "u1 check", "open"));
		accountData.add(new Account(1, "checking", 10.99, "u2 check", "open"));
		accountData.add(new Account(2, "savings", 10.99, "u2 save", "open"));
		accountData.add(new Account(3, "savings", 10.99, "u2 retirement", "open"));
	}
	
	public static boolean createAccount(Account account) {
		return true;
	}
	
	public static Account getAccountById(int id){
		for(Account a : accountData) {
			if(a.getAccountID() == id) {
				return a;
			}
		}
		return null;
	}
	
	public static boolean deleteAccount(int accountID) {
		for(int a = 0; a < accountData.size(); a ++) {
			if(accountData.get(a).getAccountID() == accountID) {
				accountData.remove(a);
				//Delete all accountHolders that correspond to this account
				for(int b = 0; b < AccountHolderDao.accountHolderData.size(); b ++) {
					AccountHolder ah = AccountHolderDao.accountHolderData.get(b);
					if(ah.getAccountID() == accountID) {
						AccountHolderDao.accountHolderData.remove(b);
					}
				}
				return true;
			}
		}
		return false;
	}
	public static boolean updateAccount(int accountID, Account newAccount) {
		//Update all information on this record to match newAccount
		return true;
	}
	public static boolean renameAccount(int accountID, String newName) {
		for(Account a : accountData) {
			if(a.getAccountID() == accountID) {
				a.setName(newName);
				return true;
			}
		}
		return false;
	}
	public static List<Account> getPendingAccounts(){
		return null;
	}
	public static void depositAll(double amount) {
		
	}
}