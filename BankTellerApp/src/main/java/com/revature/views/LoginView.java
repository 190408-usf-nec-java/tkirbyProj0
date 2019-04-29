package com.revature.views;

import com.revature.beans.User;
import com.revature.services.UserService;

public class LoginView implements View {
	UserService userService = new UserService();
	@Override
	public View printOptions() {
		User u = this.userService.loginUser();
		if(u == null)
		{
			System.out.println("login failed");
			return new MainMenu();
		}
		return new UserView(u);
	}

}
