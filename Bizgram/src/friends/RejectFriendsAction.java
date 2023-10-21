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

public class RejectFriendsAction extends Action {
	public String execute (HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int user_id = user.getUser_id();
		int friends_id = Integer.parseInt(request.getParameter("friends_id"));				//	検索ユーザごとのidをURLクエリに付加して(friend_idとして)取得

		UserFriendsDAO uf_dao = new UserFriendsDAO();
		uf_dao.rejectFriends(user_id, friends_id);
		List<UserFriends> appliedFriends = uf_dao.appliedFriends(user_id);
		List<UserFriends> friends = uf_dao.friendsList(user_id);

		UserDAO u_dao = new UserDAO();
		user.setNumber_of_friends(u_dao.countFriends(user_id));
		session.setAttribute("user", user);
		session.setAttribute("appliedFriends", appliedFriends);
		session.setAttribute("friends", friends);
		if (appliedFriends.size() != 0) return "../view/user_mypage/information/applied_friends.jsp";
		return "../view/friends/friends_list.jsp";
	}
}