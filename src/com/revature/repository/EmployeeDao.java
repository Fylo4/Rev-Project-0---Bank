package com.revature.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.models.Employee;
import com.revature.services.SQLServices;

public class EmployeeDao {
	public static Employee getEmployee(int userID) {
		String command = "SELECT * FROM employees INNER JOIN users ON employees.userid = users.userid WHERE employees.userid = "+userID+";";
		
		try(Connection connection = DriverManager.getConnection(SQLServices.url, SQLServices.username, SQLServices.password);
		ResultSet set = connection.createStatement().executeQuery(command);){
			if(set != null && set.next()) {
				return new Employee(set.getInt(2), set.getString(5), set.getString(6), set.getString(7), set.getString(8), set.getInt(9), set.getInt(1), set.getBoolean(3));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
		return null;
	}
}
