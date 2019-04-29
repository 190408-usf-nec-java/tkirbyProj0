package com.revature.views;

import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.handler.DAOHandler;
import com.revature.services.AccountService;

public class AddAccountView extends UserView implements View{
	AccountService acc = new AccountService();
	public AddAccountView(User u) {
		super(u);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View printOptions() {
		// TODO 
		Account a = acc.createAccount();
		DAOHandler.addAccount(user, a);
		// run addaccount from accountservice
		// return AccountView of created account
		return new AccountView(a,user);
	}

}
