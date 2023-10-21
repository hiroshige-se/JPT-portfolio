package friends;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import bean.UserFriends;
import dao.UserDAO;
import dao.UserFriendsDAO;
import tool.Action;

public class AuthorizeFriendsAction extends Action {
	public String execute (HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int user_id = user.getUser_id();
		String user_name = user.getUser_name();
		int friends_id = Integer.parseInt(request.getParameter("friends_id"));				//	検索ユーザごとのidをURLクエリに付加して(friend_idとして)取得
		String friends_name = request.getParameter("friends_name");

		UserFriendsDAO uf_dao = new UserFriendsDAO();
		uf_dao.authorizeFriends(user_id, friends_id);
		uf_dao.addFriends(user_id, user_name, friends_id, friends_name);
		List<UserFriends> appliedFriends = uf_dao.appliedFriends(user_id);
		List<UserFriends> friends = uf_dao.friendsList(user_id);

		if (friends != null) {							//	ユーザが存在する場合の処理
			UserDAO u_dao = new UserDAO();
			user.setNumber_of_friends(u_dao.countFriends(user_id));
			session.setAttribute("user", user);
			session.setAttribute("appliedFriends", appliedFriends);
			session.setAttribute("friends", friends);
			return "../view/friends/friends_list.jsp";
		}
		return "../view/friends/make_friend_error.jsp";					//	ユーザが存在しない場合の処理
	}
}