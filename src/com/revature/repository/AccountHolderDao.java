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

public class AccountHolderDao {
	
	public static List<Account> getAccountsByUser(int userID){
		List<Account> ret = new LinkedList<>();
		
		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);
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
	public static boolean createHolder(int accountID, int userID) {
		String command = "INSERT INTO accountHolders VALUES ("+accountID+", "+userID+");";

		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);){
			connection.createStatement().execute(command);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		
		return true;
	}
}
