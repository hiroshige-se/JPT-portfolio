package friends;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import dao.UserFriendsDAO;
import tool.Action;

public class SearchFriendsAction extends Action {
	public String execute (HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String friends_name = request.getParameter("friends_name");
		User user = (User)session.getAttribute("user");
		int userId = user.getUser_id();

		UserFriendsDAO dao = new UserFriendsDAO();
		List<User> searchFriends = dao.searchFriends(userId, friends_name);		//	入力情報を基に、検索されたユーザが存在するかをチェック

		if (searchFriends.size() != 0) {							//	ユーザが存在する場合の処理
			request.setAttribute("searchFriends", searchFriends);
			return "../view/friends/result_search_friend.jsp";
		}
		return "../view/friends/result_search_friend_error.jsp";					//	ユーザが存在しない場合の処理
	}
}
