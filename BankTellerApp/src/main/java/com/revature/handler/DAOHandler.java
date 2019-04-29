package com.revature.handler;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.daos.AccountDao;
import com.revature.daos.UserDao;

public class DAOHandler {
	static ReadWriteLock lock = new ReentrantReadWriteLock();
	static Lock writeLock = lock.writeLock();
	static Lock readLock = lock.readLock();
	private static UserDao uDao = new UserDao();
	private static AccountDao aDao = new AccountDao();
	
	public static String addUser(User u)
	{
		String ret = "creation successful";
		try {
			writeLock.lock();
			uDao.saveUser(u);
		}
		catch(Exception e)
		{
			u.setId(-1);
			ret = "creation failed"; 
		}
		finally {
			writeLock.unlock();
		}
		return ret;
	}
	
	public static String addAccount(User u,Account a)
	{
		try {
			writeLock.lock();
			uDao.saveAccount(u, a);
		}
		finally {
			writeLock.unlock();
		}
		return null;
	}
	
	public static String removeAccount(int aid)
	{
		try {
			writeLock.lock();
			Account a =null;
			try {
				a = aDao.grabAccountFull(aid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			aDao.removeAccount(a);
			//if more than 1  ownership remove aid -> u.uid from ownership
			//else deny if aid has money
		}
		finally {
			writeLock.unlock();
		}
		return null;
	}
	
	public static String deposit(Account acc, double deposit)
	{
		try {
			writeLock.lock();
			aDao.changeBalance(acc, deposit);
		}
		finally {
			writeLock.unlock();
		}
		return null;
	}
	
	public static List<Account> readAcc(User u)
	{
		try {
			readLock.lock();
			return aDao.userAccounts(u);
		}
		finally {
			readLock.unlock();
		}
	}
	
	public static User checkCred(String uname, String pword)
	{
		User u = null;
		try {
			readLock.lock();
			u = uDao.loginUser(uname, pword);
		}
		finally {
			readLock.unlock();
		}
		return u;
	}

	public static Account getAccount(int targetid) {
		Account a = null;
		try {
			readLock.lock();
			try {
				a = aDao.grabAccountFull(targetid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		finally {
			readLock.unlock();
		}
		return a;
	}

	public static void addOwner(String newOwner, Account a) {
		try {
			writeLock.lock();
			User u = null;
			try {
				u = uDao.grabUserFull(newOwner);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			aDao.addOwner(u,a);
			
		}
		finally {
			writeLock.unlock();
		}
		
	}

	
	
	
}
