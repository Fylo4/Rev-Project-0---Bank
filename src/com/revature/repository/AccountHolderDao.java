package com.revature.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountHolder;

public class AccountHolderDao {
	private static final String url = "jdbc:postgresql://p0-bank-database.cijrbngpbjo5.us-east-2.rds.amazonaws.com:5432/postgres";
	private static final String username = "postgres";
	private static final String password = "postgres";
	
	public static List<AccountHolder> accountHolderData;
	
	static{
		accountHolderData = new LinkedList<>();
		accountHolderData.add(new AccountHolder(0, 0)); //Account, user
		accountHolderData.add(new AccountHolder(1, 1));
		accountHolderData.add(new AccountHolder(2, 1));
		accountHolderData.add(new AccountHolder(3, 1));
	}
	
	/*public static List<Account> getAccountsByUser(int userID) {
		List<Account> ret = new LinkedList<Account>();
		for(AccountHolder a : accountHolderData) {
			if(a.getUserID() == userID){
				ret.add(AccountDao.getAccountById(a.getAccountID()));
			}
		}
		return ret;
	}*/
	public static List<Account> getAccountsByUser(int userID){
		
		List<Account> ret = new LinkedList<>();
		
		try(Connection connection = DriverManager.getConnection(url, username, password);
				Statement statement = connection.createStatement();
			    ResultSet set = statement.executeQuery("SELECT * FROM accountholders INNER JOIN accounts ON accountholders.accountid = accounts.accountid WHERE userid = "+userID+";");
			){
				while(set.next()) {
					ret.add(new Account(set.getInt(1), set.getString(4), set.getDouble(5), set.getString(6), set.getString(7)));
				}
			} catch (SQLException e1) {
		}
		return ret;
	}
}
