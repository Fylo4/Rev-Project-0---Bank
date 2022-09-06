package com.revature.test;

import org.junit.jupiter.api.Test;

import com.revature.models.Employee;
import com.revature.repository.EmployeeDao;

public class EmployeeDaoTest {

	@Test
	void getEmployeeTest() {
		Employee emp = EmployeeDao.getEmployee(3);
		assert(emp.getEmployeeID() == 2);
	}
	
	@Test
	void getEmployeeFailTest() {
		Employee emp = EmployeeDao.getEmployee(-1);
		assert(emp == null);
	}
}
