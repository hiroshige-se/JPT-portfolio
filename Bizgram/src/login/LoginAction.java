package login;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.User;
import bean.UserFriends;
import dao.UserDAO;
import dao.UserFriendsDAO;
import tool.Action;

public class LoginAction extends Action {
	public String execute (HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		UserDAO dao = new UserDAO();
		User user = dao.login(username, password);		//	入力情報を基に、ユーザが存在するかをチェック

		if (user != null) {							//	ユーザが存在する場合の処理
			int user_id = user.getUser_id();
			UserFriendsDAO uf_dao = new UserFriendsDAO();
			List<UserFriends> friends = uf_dao.friendsList(user_id);
			List<UserFriends> appliedFriends = uf_dao.appliedFriends(user_id);
			session.setAttribute("user", user);
			session.setAttribute("friends", friends);
			session.setAttribute("appliedFriends", appliedFriends);
			return "../view/login/login-to-mypage.jsp";
		}
		return "../view/login/login-error.jsp";					//	ユーザが存在しない場合の処理
	}
}