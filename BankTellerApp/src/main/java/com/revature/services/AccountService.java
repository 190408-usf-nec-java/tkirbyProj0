package com.revature.services;

import java.util.List;
import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.handler.DAOHandler;
import com.revature.util.ScannerUtil;

public class AccountService {
	
	
	/**
	 * Handles creation workflow for User
	 * bean
	 */
	public Account createAccount() {
		
		System.out.println("Please enter Account name: ");
		String accName = ScannerUtil.getLine();
		double startBal = 0;
		
		// Validate all this data
		
		Account a = new Account(accName, startBal);
		
		return a;
	}

	public void deposit(Account a)
	{
		boolean done = false;
		while (!done) {
			System.out.println("How much would you like to deposit? \nYou have $" + (a.getBalance()/100.0) + " in your account");
			String deposit = ScannerUtil.getLine();
			double toDep = 0;
			try {
				toDep = Double.parseDouble(deposit);
				if(toDep < 0)
				{
					System.out.println("negative deposit assuming 0");
					toDep = 0;
				}
				done = true;
			} catch (NumberFormatException e) {
				System.out.println("invalid input try again");
			}
			DAOHandler.deposit(a, Math.abs(toDep));
		}
	}
	
	public void withdraw(Account a) {
		boolean done = false;
		while (!done) {
			System.out.println("How much would you like to withdraw? \nYou have $" + (a.getBalance()/100.0) + " in your account");
			String deposit = ScannerUtil.getLine();
			double toDep = 0;
			try {
				toDep = Double.parseDouble(deposit);
				if(toDep*100 > a.getBalance())
				{
					System.out.println("Overdraw assuming full withdraw");
					toDep = a.getBalance()/100.0;
				}
				if(toDep < 0)
				{
					System.out.println("negative withdraw assuming cancellation");
					toDep = 0;
				}
				done = true;
			} catch (NumberFormatException e) {
				System.out.println("invalid input");
			}
			DAOHandler.deposit(a, Math.abs(toDep) * -1.0);
		}
	}
	
	public void addOwner(Account a)
	{
		System.out.println("Username of the desired additional owner?");
		String newOwner = ScannerUtil.getLine();
		DAOHandler.addOwner(newOwner,a);
	}
	
	public List<Account> grabAccounts(User u){
		return DAOHandler.readAcc(u);
	}

	public void transferMoney(Account a) {
		boolean done = false;
		while (!done) {
			System.out.println("How much would you like to transfer? \nYou have $" + (a.getBalance()/100.0) + " in your account");
			String deposit = ScannerUtil.getLine();
			double toDep = 0;
			try {
				toDep = Double.parseDouble(deposit);
				if(toDep*100 > a.getBalance())
				{
					System.out.println("Overdraw assuming full transfer");
					toDep = a.getBalance()/100.0;
				}
				if(toDep < 0)
				{
					System.out.println("negative transfer assuming cancellation");
					return;
				}
				done = true;
			} catch (NumberFormatException e) {
				System.out.println("invalid input");
			}
			System.out.println("where would you like to transfer (use Account id)");
			String target = ScannerUtil.getLine();
			Account b = null;
			int targetid;
			try {
				targetid = Integer.parseInt(target);
				b = DAOHandler.getAccount(targetid);
			} catch (NumberFormatException e) {
				System.out.println("invalid input");
			} catch (NullPointerException e)
			{
				System.out.println("account does not exist");
				return;
			}
			DAOHandler.deposit(a, Math.abs(toDep) * -1.0);
			DAOHandler.deposit(b, Math.abs(toDep));
		}
		
	}

	public void deleteAcc(Account a) {
		if(a.getBalance()!= 0)
		{
			System.out.println("need a balance of 0 to delete");
			return;
		}
		DAOHandler.removeAccount(a.getAid());
		// TODO Auto-generated method stub
		
	}
	
	
}
