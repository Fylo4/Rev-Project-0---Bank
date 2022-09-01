package com.revature.controller;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.revature.models.Employee;
import com.revature.models.User;

public class Controller {
	private static User loggedUser = null;
	private static Employee loggedEmployee = null;
	private static Scanner scanner = new Scanner(System.in);
	
	private static int getInt(int min, int max) {
		int choice = min-1;
		while(choice < min || choice > max) {
			try {
				choice = scanner.nextInt();
			} catch(NoSuchElementException e) {
			}
			if(choice < min || choice > max) {
				 System.out.println("Please select one of the above options");
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
		System.out.println("Username: ");
		String username = scanner.nextLine();
		System.out.println("Password: ");
		String password = scanner.nextLine();
		
		loggedUser = null; //getUser() or smth
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
		System.out.println("Create a new profile");
		System.out.println("Enter your first name: ");
		System.out.println("Enter your last name: ");
		System.out.println("Profile username: ");
		System.out.println("Profile password: ");
	}
	private static void employeeMenu() {
		
	}
	private static void userMenu() {
		
	}
	public static void main(String[] args) {
		mainMenu();
	}
}
