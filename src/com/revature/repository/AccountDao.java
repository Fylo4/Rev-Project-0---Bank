package com.revature.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.revature.models.Account;
import com.revature.services.SQLServices;

public class AccountDao {
	public static boolean createAccount(Account account, int userID) {
		String command = "INSERT INTO accounts (type, balance, name, state) VALUES ('"+account.getType()+"', "+account.getBalance()+", '"+account.getName()+"', '"+account.getState()+"');";

		int current_account_id = -1;
		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);){
			connection.createStatement().execute(command);

		    ResultSet set = connection.createStatement().executeQuery("select currval(pg_get_serial_sequence('accounts', 'accountid'));");
			if(set.next()) {
				current_account_id = set.getInt(1);
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		
		//Make sure we got an account number
		if(current_account_id < 0) {
			return false;
		}
		
		//Now add the bridge between account and user
		command = "INSERT INTO accountHolders VALUES ("+current_account_id+", "+userID+");";
		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);){
			connection.createStatement().execute(command);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static boolean deleteAccount(int accountID) {
		//First delete any account bridges that lead to this
		String command = "DELETE FROM accountHolders WHERE accountID = "+accountID+";";
		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);){
			connection.createStatement().execute(command);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		
		//Then delete the account itself
		command = "DELETE FROM accounts WHERE accountID = "+accountID+";";
		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);){
			connection.createStatement().execute(command);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		
		return true;
	}
	public static boolean updateAccount(int accountID, Account newAccount) {
		String command = "UPDATE accounts SET type='"+newAccount.getType()+"', balance="+newAccount.getBalance()+", name='"+newAccount.getName()+"', state='"+newAccount.getState()+"' WHERE accountID = "+accountID+";";

		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);){
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

		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);
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

		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);){
			connection.createStatement().execute(command);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}
}