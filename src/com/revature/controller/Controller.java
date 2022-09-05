package com.revature.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.revature.models.Account;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.repository.AccountDao;
import com.revature.repository.AccountHolderDao;
import com.revature.repository.UserDao;

public class Controller {
	private static User loggedUser = null;
	private static Employee loggedEmployee = null;
	private static Scanner scanner = new Scanner(System.in);
	private static final DecimalFormat df = new DecimalFormat("0.00"); //For showing balances
	
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
	private static double getDouble(double min, double max) {
		double ret = min-1;
		while(ret < min || ret > max) {
			try {
				ret = scanner.nextDouble();
			} catch(NoSuchElementException e) {
			}
			if(ret < min || ret > max) {
				System.out.println("Input not recognized, please try again");
			}
			scanner.nextLine();
		}
		return ret;
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
		
		loggedUser = UserDao.getUser(username, password);
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
			if(UserDao.usernameAvailable(uname)) {
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
		User newUser = UserDao.createUser(new User(UserDao.getNextUserID(), fname, lname, uname, pass, ssn));
		if(newUser == null) {
			System.out.println("Something went wrong, please try again");
			mainMenu();
		}
		else {
			System.out.println("Profile successfully created!");
			login();
		}
	}

	private static void employeeMenu() {
		System.out.println("Employee Menu");
		System.out.println("Please select from the following options:");
		System.out.println("1) Your customer menu");
		System.out.println("2) Manage users");
		System.out.println("3) Manage pending users/accounts");
		System.out.println("4) Log out");
		if(loggedEmployee.isAdmin()) {
			System.out.println("5) Actions on all accounts");
		}
		int selection = getInt(1, loggedEmployee.isAdmin()?4:3);
		switch(selection) {
		case 1:
			userMenu();
			break;
		case 2:
			manageUsers();
			break;
		case 3:
			managePending();
			break;
		case 4:
			logout();
			break;
		case 5:
			allAccounts();
			break;
		}
	}
	private static void manageUsers() {
		
	}
	private static void managePending() {
		
	}
	private static void allAccounts() {
		
	}


	private static void userMenu() {
		boolean isEmployee = (loggedEmployee != null && loggedEmployee.getUserID() == loggedUser.getUserID());
		System.out.println(loggedUser.getFirstname());
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
			break;
		case 2:
			showAccountDetails();
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
		System.out.println("Displaying all balances:");
		double total = 0;
		for(Account a : AccountHolderDao.getAccountsByUser(loggedUser.getUserID())) {
			System.out.println("Name: "+a.getName() + " Balance: $"+df.format(a.getBalance()));
			total += a.getBalance();
		}
		System.out.println("Total balance: $"+df.format(total));
		userMenu();
	}
	private static void showAccountDetails() {
		System.out.println("Displaying all account details:");
		for(Account a : AccountHolderDao.getAccountsByUser(loggedUser.getUserID())) {
			System.out.println("Name: "+a.getName() + " Type: "+a.getType()+" Balance: $"+df.format(a.getBalance())+" State: "+a.getState());
		}
		userMenu();
	}
	private static void deposit() {
		List<Account> allAccounts = AccountHolderDao.getAccountsByUser(loggedUser.getUserID());
		System.out.println("Deposit");
		System.out.println("Select which account to deposit into:");
		int index = 1;
		for(Account a : allAccounts) {
			System.out.println(index+") "+a.getName()+" - $"+a.getBalance());
			index ++;
		}
		int selection = getInt(1, index-1);
		System.out.println("How much money to deposit? ");
		double amount = getDouble(0.01, Double.MAX_VALUE);
		allAccounts.get(selection-1).changeBalance(amount);
		System.out.println("Deposit successful");
		userMenu();
	}
	private static void withdraw() {
		List<Account> allAccounts = AccountHolderDao.getAccountsByUser(loggedUser.getUserID());
		System.out.println("Withdraw");
		System.out.println("Select which account to withdraw from:");
		int index = 1;
		for(Account a : allAccounts) {
			System.out.println(index+") "+a.getName()+" - $"+df.format(a.getBalance()));
			index ++;
		}
		int selection = getInt(1, index-1);
		System.out.println("How much money to withdraw? ");
		double amount = getDouble(0.01, allAccounts.get(selection-1).getBalance());
		allAccounts.get(selection-1).changeBalance(-amount);
		System.out.println("Withdraw successful");
		userMenu();
	}
	private static void transfer() {
		List<Account> allAccounts = AccountHolderDao.getAccountsByUser(loggedUser.getUserID());
		System.out.println("Transfer");
		System.out.println("Accounts:");
		int index = 1;
		for(Account a : allAccounts) {
			System.out.println(index+") "+a.getName()+" - $"+df.format(a.getBalance()));
			index ++;
		}
		System.out.println("Move money from account #: ");
		int acc_from = getInt(1, index-1);
		System.out.println("To account #: ");
		int acc_to = getInt(1, index-1);
		System.out.println("Amount of money to transfer: ");
		double amount = getDouble(0.01, allAccounts.get(acc_from-1).getBalance());
		allAccounts.get(acc_from-1).changeBalance(-amount);
		allAccounts.get(acc_to-11).changeBalance(amount);
		System.out.println("Transfer successful");
		userMenu();
	}
	private static void manageAccounts() {
		System.out.println("Please select from the following options:");
		System.out.println("1) Open new account");
		System.out.println("2) Join joint account");
		System.out.println("3) Rename account");
		System.out.println("4) Delete account");
		int selection = getInt(1, 4);
		switch(selection) {
		case 1:
			openAccount();
			break;
		case 2:
			joinAccount();
			break;
		case 3:
			renameAccount();
			break;
		case 4:
			deleteAccount();
			break;
		}
	}
	private static void openAccount() {
		
	}
	private static void joinAccount(){
		
	}
	private static void renameAccount() {
		List<Account> allAccounts = AccountHolderDao.getAccountsByUser(loggedUser.getUserID());
		System.out.println("Rename Account");
		System.out.println("Select which account to delete:");
		System.out.println("0) Exit without renaming");
		int index = 1;
		for(Account a : allAccounts) {
			System.out.println(index+") "+a.getName()+" - $"+a.getBalance());
			index ++;
		}
		int selection = getInt(0, index-1);
		if(selection == 0) {
			userMenu();
		}else {
			System.out.println("Rename to: ");
			String newName = scanner.nextLine();
			AccountDao.renameAccount(allAccounts.get(selection-1).getAccountID(), newName);
		}
	}
	private static void deleteAccount() {
		List<Account> allAccounts = AccountHolderDao.getAccountsByUser(loggedUser.getUserID());
		System.out.println("Delete Account");
		System.out.println("Select which account to delete:");
		System.out.println("0) Exit without deleting");
		int index = 1;
		for(Account a : allAccounts) {
			System.out.println(index+") "+a.getName()+" - $"+a.getBalance());
			index ++;
		}
		int selection = getInt(0, index-1);
		if(selection == 0) {
			userMenu();
		}
		else {
			System.out.println("Type 'delete' (without quotes) to confirm");
			String input = scanner.nextLine();
			if(input.equals("delete")) {
				if(AccountDao.deleteAccount(allAccounts.get(selection-1).getAccountID())) {
					System.out.println("Account deleted successfully");
				}
				else {
					System.out.println("Failed to delete the account");
				}
			}
			else {
				System.out.println("Deletion cancelled");
			}
		}
		userMenu();
	}
	private static void logout() {
		loggedUser = null;
		loggedEmployee = null;
		mainMenu();
	}
	public static void main(String[] args) {
		mainMenu();
	}
}
