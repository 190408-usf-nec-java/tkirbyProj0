package com.revature.views;

import com.revature.util.ScannerUtil;

public class MainMenu implements View {

	@Override
	public View printOptions() {
		System.out.println("1. Returning User");
		System.out.println("2. New User");
		System.out.println("0. Quit");
	
		int selection = ScannerUtil.getNumericChoice(2);
		
		switch(selection) {
		case 1: return new LoginView();
		case 2: return new NewUserView();
		default: return null;
		}
		
	}

}
