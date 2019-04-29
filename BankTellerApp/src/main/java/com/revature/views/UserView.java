package com.revature.views;

import com.revature.beans.User;
import com.revature.services.UserService;
import com.revature.util.ScannerUtil;

public class UserView implements View {

	UserService userService = new UserService();
	User user = null;
	public UserView(User u) {
		super();
		this.user = u;
	}
	@Override
	public View printOptions() {
		//TODO
		//Check accounts
		//change details
		//Add/remove acc
		System.out.println("Hello " + user.getFirstName() + "!");
		System.out.println("1. View Accounts");
		System.out.println("2. New Account");
		System.out.println("0. Quit");
	
		int selection = ScannerUtil.getNumericChoice(2);
		
		switch(selection) {
		case 1: return new UserAccountsView(user);
		case 2: return new AddAccountView(user);
		default: return null;
		}
	}

}
