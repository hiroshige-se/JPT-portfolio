package pushGood;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Post;
import bean.User;
import dao.PostDAO;
import tool.Action;

public class PushGoodButtonAction extends Action {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String goodTargetIdentifier = request.getParameter("friends_id");

		int user_id;
		int friends_id = 0;
		if (goodTargetIdentifier != null) {
			user_id = Integer.parseInt(goodTargetIdentifier);
			friends_id = user.getUser_id();
		} else {
			user_id = user.getUser_id();
		}

		int post_id = 0;
		String goodMyselfIdentifier = request.getParameter("goodMyself");
		String goodIdentifier = request.getParameter("good");
		if (goodMyselfIdentifier != null) {
			post_id = Integer.parseInt(goodMyselfIdentifier);							//	投稿画像ごとのpost_idをURLクエリに付加して取得
		} else if (goodIdentifier != null) {
			post_id = Integer.parseInt(goodIdentifier);
		}

		int verygood_post_id = 0;
		String veryGoodIdentifier = request.getParameter("veryGood");
		if (veryGoodIdentifier != null) {
			verygood_post_id = Integer.parseInt(veryGoodIdentifier);				//	投稿画像ごとのpost_idをURLクエリに付加して取得
		}

		PostDAO dao = new PostDAO();
		if (post_id != 0) {
			dao.goodAdd(post_id);		//	「いいね！」を増やすメソッド
			if (goodTargetIdentifier != null) dao.postSpecifyDoGood(user_id, post_id, friends_id);
		} else {
			dao.veryGoodAdd(verygood_post_id);
			if (goodTargetIdentifier != null) dao.postSpecifyDoGood(user_id, verygood_post_id, friends_id);
		}

		List<Post> list = dao.showImageList(user_id);		//	画像の一覧表示メソッド
		session.setAttribute("list", list);

		String goodForFriendsPost = request.getParameter("good_for_friends_post");
		if (goodForFriendsPost != null) {
			int intGoodForFriendsPost = Integer.parseInt(request.getParameter("good_for_friends_post"));
			if (intGoodForFriendsPost == 1) return "../friends/ShowFriendsPage.action";
		}
		return "../view/user_mypage/mypage.jsp";
	}
}