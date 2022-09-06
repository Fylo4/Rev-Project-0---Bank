package com.revature.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.revature.models.Account;
import com.revature.models.AccountHolder;
import com.revature.models.Employee;
import com.revature.models.User;
import com.revature.repository.AccountDao;
import com.revature.repository.AccountHolderDao;
import com.revature.repository.EmployeeDao;
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
		System.out.println("3) Quit application");
		int choice = getInt(1, 3);
		switch(choice) {
		case 1:
			login();
			break;
		case 2:
			create();
			break;
		//3 does nothing, which ends the program
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
			loggedEmployee = EmployeeDao.getEmployee(loggedUser.getUserID());
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
		boolean successful = UserDao.createUser(new User(0, fname, lname, uname, pass, ssn));
		if(!successful) {
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
		System.out.println("3) Manage pending accounts");
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
			pendingAccounts();
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
		System.out.println("Manage users");
		System.out.println("1) Find users by name");
		System.out.println("2) Find users by ID");
		System.out.println("3) Cancel");
		int selection = getInt(1, 3);
		switch(selection) {
		case 1:
			manageUsersByName();
			break;
		case 2:
			manageUsersByID();
			break;
		case 3:
			employeeMenu();
			break;
		}
		
	}
	private static void manageUsersByName() {
		System.out.println("First name: ");
		String fname = scanner.nextLine();
		System.out.println("Last name: ");
		String lname = scanner.nextLine();
		User user = UserDao.findByName(fname, lname);
		if(user == null) {
			System.out.println("No users found by that name");
			manageUsers();
		}
		else {
			manageUsers2(user);
		}
	}
	private static void manageUsersByID() {
		System.out.println("User ID: ");
		int id = scanner.nextInt();
		User user = UserDao.findByID(id);
		if(user == null) {
			System.out.println("No users found with that ID");
			manageUsers();
		}
		else {
			manageUsers2(user);
		}
	}
	private static void manageUsers2(User user) {
		System.out.println("1) View accounts");
		System.out.println("2) View profile info");
		System.out.println("3) Return");
		if(loggedEmployee.isAdmin()) {
			System.out.println("4) Change account details");
			System.out.println("5) Change profile details");
		}

		int selection = getInt(1, loggedEmployee.isAdmin()?5:3);
		switch(selection) {
		case 1:
			viewUserAccounts(user);
			break;
		case 2:
			viewUserProfile(user);
			break;
		case 3:
			manageUsers();
			break;
		case 4:
			changeUserAccount(user);
			break;
		case 5:
			changeUserProfile(user);
			break;
		}
	}
	private static void viewUserAccounts(User user) {
		System.out.println("Displaying all account details:");
		boolean accountsFound = false;
		for(Account a : AccountHolderDao.getAccountsByUser(user.getUserID())) {
			System.out.println("Name: "+a.getName() + " Type: "+a.getType()+" Balance: $"+df.format(a.getBalance())+" State: "+a.getState());
			accountsFound = true;
		}
		if(!accountsFound) {
			System.out.println("No accounts found in this profile");
		}
		manageUsers2(user);
	}
	private static void viewUserProfile(User user) {
		System.out.println("UserID: "+user.getUserID());
		System.out.println("Name: "+user.getFirstname()+" "+user.getLastname());
		System.out.println("Username: "+user.getUsername());
		System.out.println("Show sensitive information? (y/n) ");
		String answer = scanner.nextLine();
		if(answer.equals("y") || answer.equals("Y") || answer.equals("yes") || answer.equals("Yes")) {
			System.out.println("Password: "+user.getPassword());
			System.out.println("SSN: "+user.getSSN());
		}
		manageUsers2(user);
	}
	private static void changeUserAccount(User user) {
		//Pick the account to modify
		System.out.println("Select an account to modify:");
		List<Account> myAccounts = AccountHolderDao.getAccountsByUser(user.getUserID());

		for(int a = 0; a < myAccounts.size(); a ++) {
			System.out.println((a+1)+") "+myAccounts.get(a).getName());
		}
		if(myAccounts.size() == 0) {
			System.out.println("No accounts found in this profile");
		}
		int selection1 = getInt(1, myAccounts.size());
		Account selectedAccount = myAccounts.get(selection1-1);
		
		//Pick the modification
		System.out.println("How do you want to modify this account?");
		System.out.println("1) Change type");
		System.out.println("2) Change balance");
		System.out.println("3) Change name");
		System.out.println("4) Change state");
		System.out.println("5) Delete");
		int selection2 = getInt(1, 5);
		
		//Make the modification
		switch(selection2) {
		case 1:
			System.out.println("1) Set to checking");
			System.out.println("2) Set to savings");
			int newType = getInt(1, 2);
			selectedAccount.setType((newType==1)?"checking":"savings");
			AccountDao.updateAccount(selectedAccount.getAccountID(), selectedAccount);
			break;
		case 2:
			System.out.println("New balance: ");
			double newBal = getDouble(0, Double.MAX_VALUE);
			selectedAccount.setBalance(newBal);
			AccountDao.updateAccount(selectedAccount.getAccountID(), selectedAccount);
			break;
		case 3:
			System.out.println("New name: ");
			String newName = scanner.nextLine();
			selectedAccount.setName(newName);
			AccountDao.updateAccount(selectedAccount.getAccountID(), selectedAccount);
			break;
		case 4:
			System.out.println("1) Set to open");
			System.out.println("2) Set to closed");
			int newState = getInt(1, 2);
			selectedAccount.setState((newState == 1)?"open":"closed");
			AccountDao.updateAccount(selectedAccount.getAccountID(), selectedAccount);
			break;
		case 5:
			System.out.println("Type 'delete' to verify deletion: ");
			String input = scanner.nextLine();
			if(input.equals("delete")) {
				AccountDao.deleteAccount(selectedAccount.getAccountID());
			}
			break;
		}
		manageUsers2(user);
	}
	private static void changeUserProfile(User user) {
		System.out.println("How do you want to modify this profile?");
		System.out.println("1) Change name");
		System.out.println("2) Change username");
		System.out.println("3) Change password");
		System.out.println("4) Change SSN");
		System.out.println("5) Delete");
		int selection = getInt(1, 5);
		
		switch(selection) {
		case 1:
			System.out.println("New first name: ");
			String fname = scanner.nextLine();
			System.out.println("New last name: ");
			String lname = scanner.nextLine();
			user.setFirstname(fname);
			user.setLastname(lname);
			UserDao.updateUser(user.getUserID(), user);
			break;
		case 2:
			System.out.println("New username: ");
			String uname = scanner.nextLine();
			user.setUsername(uname);
			UserDao.updateUser(user.getUserID(), user);
			break;
		case 3:
			System.out.println("New password: ");
			String password = scanner.nextLine();
			user.setPassword(password);
			UserDao.updateUser(user.getUserID(), user);
			break;
		case 4:
			System.out.println("New SSN: ");
			int ssn = getInt(0, 999_99_9999);
			user.setSSN(ssn);
			UserDao.updateUser(user.getUserID(), user);
			break;
		case 5:
			System.out.println("Type 'delete' to confirm deletion: ");
			String input = scanner.nextLine();
			if(input.equals("delete")) {
				UserDao.deleteUser(user);
			}
			break;
		}
	}
	private static void managePending() {
		//Open or close pending accounts and accountHolders
		//Actually I don't need to manage pending holders, so this menu never shows up
		System.out.println("1) Pending new accounts");
		System.out.println("2) Pending joint account connections");
		System.out.println("3) Return");
		int selection = getInt(1, 2);
		switch(selection) {
		case 1:
			pendingAccounts();
			break;
		case 2:
			//pendingHolders();
			break;
		case 3:
			employeeMenu();
			break;
		}
	}
	private static void pendingAccounts() {
		List<Account> pending = AccountDao.getPendingAccounts();
		if(pending != null) {
			System.out.println("0) Return");
			for(int a = 0; a < pending.size(); a ++) {
				System.out.println((a+1)+") Account #"+pending.get(a).getAccountID()+" - "+pending.get(a).getName());
			}
			int selection = getInt(0, pending.size());
			if(selection == 0) {
				employeeMenu();
			}
			else {
				System.out.println("1) Set to open");
				System.out.println("2) Set to closed");
				int selection2 = getInt(1, 2);
				pending.get(selection-1).setState((selection2==1)?"open":"closed");
				AccountDao.updateAccount(pending.get(selection-1).getAccountID(), pending.get(selection-1));
			}
			pendingAccounts();
		}
		else {
			System.out.println("An error occurred while searching for pending accounts");
			employeeMenu();
		}
	}
	private static void allAccounts() {
		System.out.println("1) Deposit to all accounts");
		System.out.println("2) Withdraw from all accounts");
		int selection = getInt(1, 2);
		System.out.println("Amount: ");
		double amount = getDouble(0, Double.MAX_VALUE);
		if(selection == 2)
			amount *= -1;
		AccountDao.depositAll(amount);
		employeeMenu();
	}


	private static void userMenu() {
		boolean isEmployee = (loggedEmployee != null && loggedEmployee.getUserID() == loggedUser.getUserID());
		System.out.println("Hello "+loggedUser.getFirstname()+" "+loggedUser.getLastname());
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
		boolean accountsFound = false;
		for(Account a : AccountHolderDao.getAccountsByUser(loggedUser.getUserID())) {
			System.out.println("Name: "+a.getName() + " Balance: $"+df.format(a.getBalance()));
			total += a.getBalance();
			accountsFound = true;
		}
		if(accountsFound) {
			System.out.println("Total balance: $"+df.format(total));
		}else {
			System.out.println("No accounts found on this profile");
		}
		userMenu();
	}
	private static void showAccountDetails() {
		System.out.println("Displaying all account details:");
		boolean accountsFound = false;
		for(Account a : AccountHolderDao.getAccountsByUser(loggedUser.getUserID())) {
			System.out.println("Name: "+a.getName() + " Type: "+a.getType()+" Balance: $"+df.format(a.getBalance())+" State: "+a.getState());
			accountsFound = true;
		}
		if(!accountsFound) {
			System.out.println("No accounts found in this profile");
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
		System.out.println("Select which account to rename:");
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
