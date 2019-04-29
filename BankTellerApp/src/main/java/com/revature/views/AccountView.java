package com.revature.views;

import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.services.AccountService;
import com.revature.util.ScannerUtil;

public class AccountView extends UserView implements View {

	Account a;
	
	AccountService aS = new AccountService();
	public AccountView(Account a,User u) {
		super(u);
		this.a = a;
	}


	@Override
	public View printOptions() {
		// TODO
		System.out.println("Account name: " + a.getName() + " Current balance: $" + (a.getBalance()/100.0));
		System.out.println("1. Deposit");
		System.out.println("2. Withdraw");
		System.out.println("3. Add Owner");
		System.out.println("4. Transfer Funds");
		System.out.println("5. Delete Account");
		System.out.println("6. Go Back");
		System.out.println("0. Quit");
	
		int selection = ScannerUtil.getNumericChoice(6);
		
		switch(selection) {
		case 1: aS.deposit(a);
				return this;
		case 2: aS.withdraw(a);
				return this;
		case 3: aS.addOwner(a);
				return this;
		case 4: aS.transferMoney(a);
				return this;
		case 5: aS.deleteAcc(a);
				return new UserView(user);
		case 6: return new UserView(user);
		default: return null;
		}
		//Add/remove funds
		//add owner
		//go back
		//switch on choices
		//run service command based on choice
		// go back makes new UserAccountsView(u)
	}

}
