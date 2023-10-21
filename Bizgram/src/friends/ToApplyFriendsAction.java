package friends;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import bean.UserFriends;
import dao.UserFriendsDAO;
import tool.Action;

public class ToApplyFriendsAction extends Action {
	public String execute (HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int user_id = user.getUser_id();
		String user_name = user.getUser_name();
		int friends_id = Integer.parseInt(request.getParameter("friends_id"));				//	検索ユーザごとのidをURLクエリに付加して(friend_idとして)取得
		String friends_name = request.getParameter("friends_name");

		UserFriendsDAO uf_dao = new UserFriendsDAO();
		UserFriends apply_friends = uf_dao.applyFriends(user_id, user_name, friends_id, friends_name);		//	入力情報を基に、友達の追加処理

		if (apply_friends != null) {							//	ユーザが存在する場合の処理
			session.setAttribute("apply_friends", apply_friends);
			return "../view/friends/make_friend.jsp";
		}
		return "../../view/friends/make_friend_error.jsp";					//	ユーザが存在しない場合の処理
	}
}