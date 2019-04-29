package com.revature.services;

import com.revature.beans.User;
import com.revature.handler.DAOHandler;
import com.revature.util.ScannerUtil;

public class UserService {
	
	
	/**
	 * Handles creation workflow for User
	 * bean
	 */
	public User createUser() {
		
		System.out.println("Please enter user's first name: ");
		String firstName = ScannerUtil.getLine();
		
		System.out.println("Please enter user's last name: ");
		String lastName = ScannerUtil.getLine();
		
		System.out.println("Please enter user's email address: " );
		String email = ScannerUtil.getLine();
		
		System.out.println("Please enter user's desired username: ");
		String userName = ScannerUtil.getLine();
		
		System.out.println("Please enter user's password: " );
		String password = ScannerUtil.getLine();
		// Validate all this data
		
		User user = new User(userName, firstName, lastName, email,password);
			
		System.out.println(DAOHandler.addUser(user));
		return user;
	}

	public User loginUser() {
		System.out.println("Username: ");
		String userName = ScannerUtil.getLine();
		System.out.println("Password: ");
		String password = ScannerUtil.getLine();
		User u = DAOHandler.checkCred(userName,password);
		return u;
	}
	
	
}
