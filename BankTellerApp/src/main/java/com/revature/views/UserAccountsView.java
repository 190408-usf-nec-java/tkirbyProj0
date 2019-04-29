package com.revature.views;

import java.util.List;
import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.services.AccountService;
import com.revature.util.ScannerUtil;

public class UserAccountsView extends UserView implements View {

	AccountService as = new AccountService();

	public UserAccountsView(User u) {
		super(u);
	}

	@Override
	public View printOptions() {
		// TODO
		// get RS of accounts
		List<Account> accs = as.grabAccounts(user);
		int index = 1;
		for (Account a : accs) {
			System.out.println(index + ": " + a.toString());
			index++;
		}
		System.out.println("0: go back\n anything else quit");
		int selection = ScannerUtil.getNumericChoice(2);
		if(selection == 0)
		{
			return new UserView(user);
		}
		else if (selection <= accs.size())
		{
			return new AccountView(accs.get(selection-1),user);
		}
		else
		{
			return null;
		}
		// for each entry print them starting from 1
		// switch on it and if 0 return userview(u)
		// else return AccountView(accountselected)\
	}

}
