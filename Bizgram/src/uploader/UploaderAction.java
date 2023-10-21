package uploader;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import bean.Post;
import bean.User;
import dao.PostDAO;
import tool.Action;

public class UploaderAction extends Action {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		int user_id = user.getUser_id();
		String image_name = request.getParameter("image_name");		//	画像タイトルの取得
		Part part = request.getPart("file");						//	画像ファイルの取得処理

		if(part.getSize() == 0) {		//	画像ファイルが空の場合の処理
			return "../view/uploader/upload-error.jsp";
		}

		Post post = new Post();		//	Uploadに必要なbean.Uploadのセット
		post.setUser_id(user_id);
		post.setImage_name(image_name);
		post.setGood_count(0);

		PostDAO dao = new PostDAO();
		dao.upload(post, part);		//	DBへの情報登録と保存フォルダへの書き込み処理

		//	画像一覧を取得するDAO
		List<Post> list = dao.showImageList(user_id);
		session.setAttribute("list", list);
		return "../view/user_mypage/mypage.jsp";
	}
}