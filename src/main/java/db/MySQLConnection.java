package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import entity.Item;
import entity.Item.ItemBuilder;


public class MySQLConnection {

	private Connection conn;

	public MySQLConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			conn = DriverManager.getConnection(MySQLDBUtil.URL);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setFavoriteItems(String userId, Item item) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return;
		}
		//assure that the item is existed in our database.
		saveItem(item);
		//we shouldn't use %s instead we use ?. Because if someone use "1111; DELETE FROM history" as userID
		//then the MySql will consider it should delete the whole history table.
		String sql = "INSERT INTO history (user_id, item_id) VALUES (?, ?)";
		try {
//			Statement statement = conn.createStatement();
//			String templeate = "INSERT INTO history (user_id, item_id) VALUES(%s,%s)";
//			String sql = String.format(templeate, userId, item.getItemId());
//			statement.executeUpdate(sql);
			
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			statement.setString(2, item.getItemId());
			statement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void unsetFavoriteItems(String userId, String itemId) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return;
		}
		String sql = "DELETE FROM history WHERE user_id = ? AND item_id = ?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			statement.setString(2, itemId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void saveItem(Item item) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return;
		}
		//"IGNORE" will check the item is or not existed in the table.
		// Because I set primary key, if we use insert without IGNORE and insert same item, database will throw an exception.
		// Database will jump the insert operation if we use IGNORE, instead of exception.
		String sql = "INSERT IGNORE INTO items VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, item.getItemId());
			statement.setString(2, item.getName());
			statement.setString(3, item.getAddress());
			statement.setString(4, item.getImageUrl());
			statement.setString(5, item.getUrl());
			statement.executeUpdate();
			
			sql = "INSERT IGNORE INTO keywords VALUES (?, ?)";
                    statement = conn.prepareStatement(sql);
			statement.setString(1, item.getItemId());
			for (String keyword : item.getKeywords()) {
				statement.setString(2, keyword);
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
