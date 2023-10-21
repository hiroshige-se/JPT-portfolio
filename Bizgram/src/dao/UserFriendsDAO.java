package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.User;
import bean.UserFriends;

public class UserFriendsDAO extends DAO{
	//	ユーザの友達をデータベース情報から取得する
	public List<UserFriends> friendsList (int id) throws Exception {
		List<UserFriends> friends = new ArrayList<>();
		Connection con = getConnection();
		PreparedStatement st = null;

		try {
			st = con.prepareStatement("SELECT * FROM biz_user_friends WHERE user_id = ?");
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				UserFriends u_friend = new UserFriends();
				u_friend.setUser_id(rs.getInt("user_id"));
				u_friend.setFriends_id(rs.getInt("friends_id"));
				u_friend.setFriends_name(rs.getString("friends_name"));
				friends.add(u_friend);
			}
		} catch(Exception e) {
			con.rollback();
			e.printStackTrace();
		}
		st.close();
		con.close();
		return friends;
	}

	//	友達検索において、入力に基づくユーザが存在するかチェック
	public List<User> searchFriends (int user_id, String friend_name) throws Exception {
		List<User> friend = new ArrayList<>();
		Connection con = getConnection();
		PreparedStatement st = null;

		try {
			st = con.prepareStatement("SELECT * FROM biz_post JOIN biz_user ON biz_post.user_id = biz_user.user_id WHERE biz_user.user_name LIKE ? GROUP BY biz_post.user_id ORDER BY last_update DESC");
			st.setString(1, "%" + friend_name + "%");
			ResultSet rs = st.executeQuery();
			st = con.prepareStatement("SELECT * FROM biz_user_friends WHERE user_id = ?");
			st.setInt(1, user_id);
			ResultSet friend_rs = st.executeQuery();
			st = con.prepareStatement("SELECT * FROM biz_apply_friends WHERE user_id = ?");
			st.setInt(1, user_id);
			ResultSet apply_rs = st.executeQuery();
			while (rs.next()) {
				User u_friend = new User();
				if (user_id == rs.getInt("user_id")) continue;

				int friendIdentify = 0;
				while (friend_rs.next()) {
					if (friend_rs.getInt("friends_id") == rs.getInt("user_id")) {
						friendIdentify = 1;
					}
				}
				friend_rs.beforeFirst();
				if (friendIdentify == 1) {
					continue;
				}

				int applyIdentify = 0;
				while (apply_rs.next()) {
					if (apply_rs.getInt("friends_id") == rs.getInt("user_id")) {
						applyIdentify = 1;
					}
				}
				apply_rs.beforeFirst();
				if (applyIdentify == 1) {
					continue;
				}

				u_friend.setUser_id(rs.getInt("user_id"));
				u_friend.setUser_name(rs.getString("user_name"));
				u_friend.setLast_update(rs.getString("last_update").substring(0, 16));
				friend.add(u_friend);
			}
		} catch(Exception e) {
			con.rollback();
			e.printStackTrace();
		}
		st.close();
		con.close();
		return friend;
	}

	//	友達申請を行うメソッド
	public UserFriends applyFriends(int user_id, String user_name, int friends_id, String friends_name) throws Exception {
		UserFriends applyFriends = null;
		Connection con = getConnection();
		PreparedStatement st = null;

		try {
			st = con.prepareStatement("INSERT INTO biz_apply_friends VALUES (null, ?, ?, ?, ?, ?, null, ?)");		//	友達承認のDB登録処理
			st.setInt(1, user_id);
			st.setString(2, user_name);
			st.setInt(3, friends_id);
			st.setString(4, friends_name);
			st.setInt(5, 1);
			st.setInt(6, 0);
			st.executeUpdate();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		}

		st = con.prepareStatement("SELECT * FROM biz_apply_friends WHERE user_id = ?");
		st.setInt(1, user_id);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			applyFriends = new UserFriends();
			applyFriends.setApply_number(rs.getInt("apply_number"));
			applyFriends.setUser_id(rs.getInt("user_id"));
			applyFriends.setUser_name(rs.getString("user_name"));
			applyFriends.setFriends_id(rs.getInt("friends_id"));
			applyFriends.setFriends_name(rs.getString("friends_name"));
			applyFriends.setApply_status(rs.getInt("apply_status"));
			applyFriends.setLast_update(rs.getString("last_update"));
		}
		st.close();
		con.close();
		return applyFriends;		//	idとfriend_idを返却
	}

	//	友達申請された数を取得するメソッド
	public List<UserFriends> appliedFriends(int user_id) throws Exception {
		List<UserFriends> appliedFriendsList = new ArrayList<>();;
		UserFriends appliedFriends = null;
		Connection con = getConnection();
		PreparedStatement st = null;

		st = con.prepareStatement("SELECT * FROM biz_apply_friends WHERE friends_id = ? AND apply_status = 1");
		st.setInt(1, user_id);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			appliedFriends = new UserFriends();
			appliedFriends.setApply_number(rs.getInt("apply_number"));
			appliedFriends.setUser_id(rs.getInt("user_id"));
			appliedFriends.setUser_name(rs.getString("user_name"));
			appliedFriends.setFriends_id(rs.getInt("friends_id"));
			appliedFriends.setFriends_name(rs.getString("friends_name"));
			appliedFriends.setApply_status(rs.getInt("apply_status"));
			appliedFriends.setLast_update(rs.getString("last_update"));
			appliedFriendsList.add(appliedFriends);
		}
		st.close();
		con.close();
		return appliedFriendsList;
	}

	//	友達申請を許可し、申請テーブルを書き換えるメソッド
	public void authorizeFriends(int user_id, int friends_id) throws Exception {
		Connection con = getConnection();
		PreparedStatement st = null;

		try {
			st = con.prepareStatement("UPDATE biz_apply_friends SET apply_status = 0 WHERE user_id = ? AND friends_id = ?");		//	友達承認のDB登録処理
			st.setInt(1, friends_id);
			st.setInt(2, user_id);
			st.executeUpdate();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		}
		st.close();
		con.close();
	}

	//	友達申請を拒否し、申請テーブルを書き換えるメソッド
	public void rejectFriends(int user_id, int friends_id) throws Exception {
		Connection con = getConnection();
		PreparedStatement st = null;

		try {
			st = con.prepareStatement("UPDATE biz_apply_friends SET apply_status = 2, reject_counter = 1 WHERE user_id = ? AND friends_id = ? AND apply_status = 1");
			st.setInt(1, friends_id);
			st.setInt(2, user_id);
			st.executeUpdate();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		}
		st.close();
		con.close();
	}

//	新たに友達を追加するメソッド
	public void addFriends(int user_id, String user_name, int friends_id, String friends_name) throws Exception {
		Connection con = getConnection();
		PreparedStatement st = null;

		try {
			st = con.prepareStatement("INSERT INTO biz_user_friends VALUES (?, ?, ?, ?, null)");		//	友達承認のDB登録処理
			st.setInt(1, user_id);
			st.setString(2, user_name);
			st.setInt(3, friends_id);
			st.setString(4, friends_name);
			st.executeUpdate();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		}
		try {
			st = con.prepareStatement("INSERT INTO biz_user_friends VALUES (?, ?, ?, ?, null)");		//	友達承認のDB登録処理
			st.setInt(1, friends_id);
			st.setString(2, friends_name);
			st.setInt(3, user_id);
			st.setString(4, user_name);
			st.executeUpdate();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		}
		st.close();
		con.close();
	}
}