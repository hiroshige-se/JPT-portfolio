package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Part;

import bean.Post;

public class PostDAO extends DAO{
	//	友達のページにてイメージを取得するメソッド
	public List<Post> showFriendsPageImages(int friends_id, String friends_name) throws Exception {
		List<Post> showFriendsPageImages = new ArrayList<>();
		Connection con = getConnection();
		PreparedStatement st = null;

		try {
			st = con.prepareStatement("SELECT * FROM biz_post WHERE user_id = ? ORDER BY post_id DESC");
			st.setInt(1, friends_id);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Post post = new Post();
				post.setUser_id(rs.getInt("user_id"));
				post.setUser_name(friends_name);
				post.setPost_id(rs.getInt("post_id"));
				post.setImage_name(rs.getString("image_name"));
				post.setGood_count(rs.getInt("good_count"));
				showFriendsPageImages.add(post);
			}
		} catch(Exception e) {
			con.rollback();
			e.printStackTrace();
		}
		st.close();
		con.close();
		return showFriendsPageImages;
	}

	//	いいね！を実行するメソッド
	public void goodAdd(int post_id) throws Exception {
		int goodAdd = 0;								//	DAO内で処理する「いいね！」の初期化
		Connection con = getConnection();

		PreparedStatement st = con.prepareStatement("SELECT good_count FROM biz_post WHERE post_id = ?");		//	「いいね！」対象imageの検索SQL
		st.setInt(1, post_id);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			int oldGood = rs.getInt("good_count");		//	現在「いいね！」されている数の取得
			goodAdd = oldGood + 1;						//	「いいね！」を1つ増やす
		}

		try {
			st = con.prepareStatement("UPDATE biz_post SET good_count = ? WHERE post_id = ?");		//	1つ増やした「いいね！」をDBへ反映するSQL
			st.setInt(1, goodAdd);		//	1つ増やした「いいね！」
			st.setInt(2, post_id);		//	「いいね！」対象のimage
			st.executeUpdate();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		}
		st.close();
		con.close();
	}

	//	超いいね！を実行するメソッド
	public void veryGoodAdd(int post_id) throws Exception {
		int veryGoodAdd = 0;								//	DAO内で処理する「いいね！」の初期化
		Connection con = getConnection();

		PreparedStatement st = con.prepareStatement("SELECT good_count FROM biz_post WHERE post_id = ?");		//	「いいね！」対象imageの検索SQL
		st.setInt(1, post_id);
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			int oldGood = rs.getInt("good_count");		//	現在「いいね！」されている数の取得
			veryGoodAdd = (int)(oldGood + Math.floor(100 * Math.random()));						//	「いいね！」を1つ増やす
		}

		try {
			st = con.prepareStatement("UPDATE biz_post SET good_count = ? WHERE post_id = ?");		//	ランダムに増やした「いいね！」をDBへ反映するSQL
			st.setInt(1, veryGoodAdd);		//	1つ増やした「いいね！」
			st.setInt(2, post_id);		//	「いいね！」対象のimage
			st.executeUpdate();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		}
		st.close();
		con.close();
	}

	//	投稿画像のDB書き込みメソッド
	public void upload (Post post, Part part) throws Exception {
		Connection con = getConnection();
		con.setAutoCommit(false);		//	transaction処理のためのfalse設定
		PreparedStatement st = null;

		try {
			st = con.prepareStatement("INSERT INTO biz_post VALUES (?, null, ?, ?, null) ");
			st.setInt(1, post.getUser_id());				//	ユーザid
			st.setString(2, post.getImage_name());	//	投稿画像のタイトル
			st.setInt(3, post.getGood_count());		//	「いいね！」の初期値（0）
			st.executeUpdate();
			//	bean.Upload.post_idは投稿画像のinsert後に決まるため、以下の処理を実施
			st = con.prepareStatement("SELECT MAX(post_id) FROM biz_post");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				post.setPost_id(rs.getInt("MAX(post_id)"));
			}
			uploader(post, part);		//	画像を指定フォルダへ保存するメソッド
		} catch (Exception e){
			con.rollback();
			e.printStackTrace();
		}
		con.commit();
		con.setAutoCommit(true);		//	transaction処理のためのtrue設定
		st.close();
		con.close();
	}

	//	投稿された画像を指定したフォルダへ保管するメソッド（ファイル名はidとpost_idを使用）
	public void uploader(Post post, Part part) throws ServletException, IOException {
        part.write("C:\\pleiades\\workspace_endo\\Bizgram\\imgsrc/p" + post.getUser_id() + "_" +  post.getPost_id() +  ".jpg");
    }

	//	投稿画像の一覧表示メソッド
	public List<Post> showImageList(int user_id) throws Exception {
		List<Post> list = new ArrayList<>();
		Connection con = getConnection();

		PreparedStatement st = con.prepareStatement("SELECT * FROM biz_post WHERE user_id LIKE ? ORDER BY post_id DESC");		//	一覧表示対象ユーザのid検索SQL
		st.setInt(1, user_id);									//	対象ユーザidのセット
		ResultSet rs = st.executeQuery();
		while (rs.next()) {
			Post post = new Post();		//	bean.PostにDBから取得した値をセット
			post.setUser_id(rs.getInt("user_id"));																				//	ユーザid
			post.setPost_id(rs.getInt("post_id"));																	//	投稿画像id
			post.setImage_name(rs.getString("image_name"));															//	投稿画像のタイトル
			post.setGood_count(rs.getInt("good_count"));																//	「いいね！」
			list.add(post);
		}
		st.close();
		con.close();
		return list;
	}

	//	いいねをしてくれたユーザーをDBへ書き込むメソッド
	public void postSpecifyDoGood(int user_id, int post_id, int friends_id) throws Exception {
		Connection con = getConnection();
		PreparedStatement st = null;
		String friends_name = null;

		try {
			st = con.prepareStatement("SELECT friends_name FROM biz_user_friends WHERE user_id = ? AND friends_id = ?");
			st.setInt(1, friends_id);									//	対象ユーザidのセット
			st.setInt(2, user_id);									//	対象ユーザidのセット
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				friends_name = rs.getString("friends_name");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			st = con.prepareStatement("DELETE FROM biz_post_specify_do_good WHERE user_id = ? AND post_id = ? AND friends_id = ?");
			st.setInt(1, user_id);
			st.setInt(2, post_id);
			st.setInt(3, friends_id);
			st.executeUpdate();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		}

		try {
			st = con.prepareStatement("INSERT INTO biz_post_specify_do_good VALUES (?, ?, ?, ?, null)");
			st.setInt(1, user_id);
			st.setInt(2, post_id);
			st.setInt(3, friends_id);
			st.setString(4, friends_name);
			st.executeUpdate();
		} catch (Exception e) {
			con.rollback();
			e.printStackTrace();
		}
		st.close();
		con.close();
	}
}