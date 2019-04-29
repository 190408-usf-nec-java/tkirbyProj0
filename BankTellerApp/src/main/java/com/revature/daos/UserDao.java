package com.revature.daos;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.google.common.hash.Hashing;
import com.revature.beans.Account;
import com.revature.beans.User;
import com.revature.util.ConnectionUtil;

public class UserDao {
	
	private String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
	
	private String hash(String password,String salt)
	{
		String sha256hex = Hashing.sha256()
				  .hashString(password+salt, StandardCharsets.UTF_8)
				  .toString();
		return sha256hex;
	}

	public void saveUser(User user) throws SQLException {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO users (username,hashedpassword,first_name,last_name,email,salt)"
					+ " VALUES (?,?,?,?,?,?) RETURNING uid";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setString(5, user.getEmail());
			String salt = getSaltString();
			ps.setString(6, salt);
			ps.setString(2, hash(user.getPassword(),salt));
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				int id = rs.getInt("uid");
				user.setId(id);
			}
		}
	}
	
	
	public void saveAccount(User user,Account acc) {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO accounts (accname,balance)"
					+ " VALUES (?,?) RETURNING aid";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, acc.getName());
			ps.setDouble(2, acc.getBalance());
			ResultSet rs = ps.executeQuery();
			if(rs.next())
			{
				int aid = rs.getInt("aid");
				acc.setAid(aid);
			}
			String sql2 = "INSERT INTO ownership (own,acc)"
					+ " VALUES (?,?) returning own";
			PreparedStatement ps2 = conn.prepareStatement(sql2);
			ps2.setInt(1, user.getId());
			ps2.setInt(2, acc.getAid());
			ps2.executeQuery();
			//TODO
			//connect aid to uid
			//add to ownership table
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public User loginUser(String uname,String password) {
		User user = null;

		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT hashedpassword, salt FROM users WHERE username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, uname);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String hashedpassword = rs.getString("hashedpassword");
				String salt = rs.getString("salt");
				if(hash(password, salt).equals(hashedpassword))
				{
					return grabUserFull(uname);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public User grabUserFull(String uname) throws SQLException {
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "select uid,username,first_name,last_name,email from users where username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, uname);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int uid = rs.getInt("uid");
				String userN = rs.getString("username");
				String first = rs.getString("first_name");
				String last = rs.getString("last_name");
				String email = rs.getString("email");
				User u = new User(userN, first, last, email, "");
				u.setId(uid);
				return u;
				
			}
		}
		return null;
	}

	

}
