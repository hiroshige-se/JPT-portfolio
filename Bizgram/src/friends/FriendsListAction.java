package friends;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import bean.UserFriends;
import dao.UserFriendsDAO;
import tool.Action;

public class FriendsListAction extends Action {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int id = user.getUser_id();

		UserFriendsDAO uf_dao = new UserFriendsDAO();
		List<UserFriends> friends = uf_dao.friendsList(id);

		request.setAttribute("friends", friends);
		return "../view/friends/friends_list.jsp";		//	画像一覧ページへ
	}
}
