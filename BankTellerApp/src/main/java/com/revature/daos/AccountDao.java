package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.util.ConnectionUtil;

public class AccountDao {
	
	public void changeBalance(Account a, double change)
	{
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "Update Accounts set balance = balance + ? where aid = ? returning balance";
			PreparedStatement ps = conn.prepareStatement(sql);
			int pennies = (int) Math.round(change*100);
			ps.setInt(1, pennies);
			ps.setInt(2,a.getAid());

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int newBalance = rs.getInt("balance");
				a.setBalance(newBalance);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<Account> userAccounts(User u)
	{
		
		List<Account> accs = new LinkedList<Account>();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "Select acc from ownership where own = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,u.getId());
			ResultSet rs = ps.executeQuery();
			ResultSet rs2;
			while(rs.next())
			{
				int accID = rs.getInt("acc");
				sql = "Select * from accounts where aid = ?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, accID);
				rs2 = ps.executeQuery();
				if(rs2.next())
				{
					int aid = rs2.getInt("aid");
					String accname = rs2.getString("accname");
					int bal = rs2.getInt("balance");
					Account a = new Account(accname,bal*1.0);
					a.setAid(aid);
					accs.add(a);
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accs;
	}
	
	public Account grabAccountFull(int id) throws SQLException {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select aid,accname,balance from accounts where aid = ?";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int aid = rs.getInt("aid");
				String accN = rs.getString("accname");
				int balance = rs.getInt("balance");
				Account a = new Account(accN, balance);
				a.setAid(aid);
				return a;
				
			}
		}
		return null;
	}

	public void addOwner(User u, Account a) {
		if(u == null)
		{
			return;
		}
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql2 = "INSERT INTO ownership (own,acc)" + " VALUES (?,?) returning own";
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setInt(1, u.getId());
			ps2.setInt(2, a.getAid());
			ps2.executeQuery();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public void removeAccount(Account a) {
		if(a == null)
		{
			return;
		}
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql1 = "Delete from ownership where acc = ?";
			String sql2 = "Delete from accounts where aid = ?";
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps1.setInt(1, a.getAid());
			ps2.setInt(1, a.getAid());
			ps1.executeUpdate();
			ps2.executeUpdate();
		}
 catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
