package friends;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Post;
import dao.PostDAO;
import dao.UserDAO;
import tool.Action;

public class ShowFriendsPageAction extends Action {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		int friends_id = Integer.parseInt(request.getParameter("friends_id"));
		String friends_name = request.getParameter("friends_name");

		if (friends_id != 0) {
			PostDAO p_dao = new PostDAO();
			List<Post> friendsPage = p_dao.showFriendsPageImages(friends_id, friends_name);
			UserDAO u_dao = new UserDAO();
			int friendsCountFriends = u_dao.countFriends(friends_id);

			request.setAttribute("friends_id", friends_id);
			request.setAttribute("friends_name", friends_name);
			request.setAttribute("friendsCountFriends", friendsCountFriends);
			session.setAttribute("friendsPage", friendsPage);
		}
		return "../view/friends/friends_mypage.jsp";		//	画像一覧ページへ
	}
}