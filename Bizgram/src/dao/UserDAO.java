package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.User;

public class UserDAO extends DAO {
	public User login (String username, String password) throws Exception {
		User user = null;		//	ユーザが存在しない場合はnullを返すための初期化
		Connection con = getConnection();
		PreparedStatement st = null;

		try {
			st = con.prepareStatement("SELECT user_id, user_name FROM biz_user WHERE user_name=? AND password=?");
			st.setString(1, username);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setUser_id(rs.getInt("user_id"));
				user.setUser_name(rs.getString("user_name"));
			}
		} catch(Exception e) {
			con.rollback();
			e.printStackTrace();
		}
		st.close();
		con.close();

		if(user != null) {
			int friends = countFriends(user.getUser_id());
			user.setNumber_of_friends(friends);
		}
		return user;
	}

	//	友達の数を取得するメソッド
	public int countFriends(int id) throws Exception {
		int countFriends = 0;
		Connection con = getConnection();
		PreparedStatement st = null;
		st = con.prepareStatement("SELECT COUNT(user_id) FROM biz_user_friends WHERE user_id=?");
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			countFriends = rs.getInt("COUNT(user_id)");
		}
		st.close();
		con.close();
		return countFriends;
	}
}