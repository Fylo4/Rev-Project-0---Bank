package com.revature.controller;

import java.util.NoSuchElementException;
import java.util.Scanner;

import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.repository.UserDao;

public class Controller {
	private static User loggedUser = null;
	private static Employee loggedEmployee = null;
	private static Scanner scanner = new Scanner(System.in);
	private static UserDao userDao = new UserDao();
	
	private static int getInt(int min, int max) {
		int choice = min-1;
		while(choice < min || choice > max) {
			try {
				choice = scanner.nextInt();
			} catch(NoSuchElementException e) {
			}
			if(choice < min || choice > max) {
				 System.out.println("Input not recognized, please try agin");
			}
			scanner.nextLine();
		}
		return choice;
	}
	
	
	private static void mainMenu() {
		System.out.println("BankCo Banking Application");
		System.out.println("Please select from the following options:");
		System.out.println("1) Log in");
		System.out.println("2) Create profile");
		int choice = getInt(1, 2);
		switch(choice) {
		case 1:
			login();
			break;
		case 2:
			create();
			break;
		}
	}
	private static void login() {
		System.out.println("Login");
		System.out.println("Username: ");
		String username = scanner.nextLine();
		System.out.println("Password: ");
		String password = scanner.nextLine();
		
		loggedUser = userDao.getUser(username, password);
		if(loggedUser != null) {
			loggedEmployee = null; //getEmployee()
			if(loggedEmployee != null) {
				employeeMenu();
			}
			else {
				userMenu();
			}
		}
		else {
			System.out.println("Username or password incorrect");
			login();
		}
	}
	private static void create() {
		String fname, lname, uname, pass;
		System.out.println("Create a new profile");
		System.out.println("Enter your first name: ");
		fname = scanner.nextLine();
		System.out.println("Enter your last name: ");
		lname = scanner.nextLine();
		while(true){
			System.out.println("Profile username: ");
			uname = scanner.nextLine();
			if(userDao.usernameAvailable(uname)) {
				break;
			}
			else {
				System.out.println("Username taken, please enter another username");
			}
		}
		System.out.println("Profile password: ");
		pass = scanner.nextLine();
		System.out.println("SSN: ");
		int ssn = getInt(0, 999_99_9999);
		User newUser = userDao.createUser(new User(userDao.getNextUserID(), fname, lname, uname, pass, ssn));
		if(newUser == null) {
			System.out.println("Something went wrong, please try again");
			create();
		}
		else {
			System.out.println("Profile successfully created!");
			login();
		}
	}
	private static void employeeMenu() {
		
	}
	private static void userMenu() {
		boolean isEmployee = (loggedEmployee != null && loggedEmployee.getUserID() == loggedUser.getUserID());
		System.out.println("Please select from the following options:");
		System.out.println("1) See balances");
		System.out.println("2) See account details");
		System.out.println("3) Deposit");
		System.out.println("4) Withdraw");
		System.out.println("5) Transfer funds");
		System.out.println("6) Manage accounts");
		System.out.println("7) Log out");
		if(isEmployee) {
			System.out.println("8) Return to employee menu");
		}
		int selection = getInt(1, isEmployee?8:7);
		switch(selection) {
		case 1:
			showBalances();
			userMenu();
			break;
		case 2:
			showAccountDetails();
			userMenu();
			break;
		case 3:
			deposit();
			break;
		case 4:
			withdraw();
			break;
		case 5:
			transfer();
			break;
		case 6:
			manageAccounts();
			break;
		case 7:
			logout();
			break;
		case 8:
			employeeMenu();
			break;
		}
		
	}
	private static void showBalances() {
		
	}
	private static void showAccountDetails() {
		
	}
	private static void deposit() {
		
	}
	private static void withdraw() {
		
	}
	private static void transfer() {
		
	}
	private static void manageAccounts() {
		
	}
	private static void logout() {
		
	}
	public static void main(String[] args) {
		mainMenu();
	}
}
