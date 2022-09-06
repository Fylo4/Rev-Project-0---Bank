package com.revature.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.revature.models.Account;

public class AccountDao {
	private static final String url = "jdbc:postgresql://p0-bank-database.cijrbngpbjo5.us-east-2.rds.amazonaws.com:5432/postgres";
	private static final String un = "postgres";
	private static final String pass = "postgres";
	
	public static boolean createAccount(Account account) {
		String command = "INSERT INTO accounts (type, balance, name, state) VALUES ('"+account.getType()+"', "+account.getBalance()+", '"+account.getName()+"', '"+account.getState()+"');";
		
		try(Connection connection = DriverManager.getConnection(url, un, pass);){
			connection.createStatement().execute(command);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean deleteAccount(int accountID) {
		String command = "DELETE FROM accounts WHERE accountID = "+accountID+";";
		
		try(Connection connection = DriverManager.getConnection(url, un, pass);){
			connection.createStatement().execute(command);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		
		return true;
	}
	public static boolean updateAccount(int accountID, Account newAccount) {
		String command = "UPDATE accounts SET type='"+newAccount.getType()+"', balance="+newAccount.getBalance()+", name='"+newAccount.getName()+"', state='"+newAccount.getState()+"' WHERE accountID = "+accountID+";";

		try(Connection connection = DriverManager.getConnection(url, un, pass);){
			connection.createStatement().execute(command);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		
		return true;
	}
	public static List<Account> getPendingAccounts(){
		String command = "SELECT * FROM accounts WHERE state = 'pending'";
		List<Account> ret = new LinkedList<>();
		
		try(Connection connection = DriverManager.getConnection(url, un, pass);
			Statement statement = connection.createStatement();
		    ResultSet set = statement.executeQuery(command);
			){
				while(set.next()) {
					ret.add(new Account(set.getInt(1), set.getString(2), set.getDouble(3), set.getString(4), set.getString(5)));
				}
			} catch (SQLException e1) {
				return null;
			}
		
		return ret;
	}
	public static boolean depositAll(double amount) {
		String command = "UPDATE accounts SET balance = balance + "+amount+";";
		
		try(Connection connection = DriverManager.getConnection(url, un, pass);){
			connection.createStatement().execute(command);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}
}