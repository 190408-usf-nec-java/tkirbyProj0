package com.revature.views;

import com.revature.beans.User;
import com.revature.services.UserService;

public class NewUserView implements View {
	UserService userservice = new UserService();
	@Override
	public View printOptions() {
		User u = this.userservice.createUser();
		if(u.getId() == -1)
		{
			return new MainMenu();
		}
		else
		{
			return new UserView(u);
		}
		
	}

}
