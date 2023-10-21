package showImages;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Post;
import bean.User;
import dao.PostDAO;
import tool.Action;

public class ShowImagesWhenLoginAction extends Action {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int user_id = user.getUser_id();

		PostDAO dao = new PostDAO();		//	画像一覧を取得するDAO
		List<Post> list = dao.showImageList(user_id);

		session.setAttribute("list", list);
		return "../view/user_mypage/mypage.jsp";		//	画像一覧ページへ
	}
}
