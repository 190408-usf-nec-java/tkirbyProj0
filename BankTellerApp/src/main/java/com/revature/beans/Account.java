package com.revature.beans;

public class Account {
	private int aid;
	private String name;
	private double balance;
	
	public Account(String name, double balance) {
		super();
		this.name = name;
		this.balance = balance;
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "Account [aid=" + aid + ", name=" + name + ", balance=" + (balance/100.0) + "]";
	}
	

}
