package com.revature.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.revature.repository.UserDao;
import com.revature.models.User;

class UserDaoTest {

	@Test
	void createAccessRemoveTest() {
		boolean createSuccessful = UserDao.createUser(new User(0, "Test1F", "Test1L", "Test1U", "Test1P", 0));
		assert(createSuccessful);
		
		User createdUser = UserDao.getUser("Test1U", "Test1P");
		assertEquals("Test1F", createdUser.getFirstname());
		
		boolean deleteSuccessful = UserDao.deleteUser(createdUser);
		assert(deleteSuccessful);
		
		User user2 = UserDao.getUser("Test1U", "Test1P");
		assertEquals(user2, null);
	}
	
	@Test
	void updateUserTest() {
		User newUser = new User(0, "Test2F", "Test2L", "Test2U", "Test2P", 1);
		boolean createSuccessful = UserDao.createUser(newUser);
		assert(createSuccessful);
		
		User user2 = UserDao.getUser("Test2U", "Test2P");

		user2.setFirstname("New2F");
		user2.setLastname("New2L");
		user2.setUsername("New2U");
		user2.setPassword("New2P");
		user2.setSSN(111);
		boolean setSuccessful = UserDao.updateUser(user2.getUserID(), user2);
		assert(setSuccessful);
		
		User user3 = UserDao.getUser("New2U", "New2P");
		assertEquals(user3.getFirstname(), "New2F");
		assertEquals(user3.getLastname(), "New2L");
		assertEquals(user3.getSSN(), 111);
		
		assert(UserDao.deleteUser(user3));
	}

}
