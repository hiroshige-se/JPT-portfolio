package showImages;

import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class ImageShowsNewAction extends Action {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String imgsrcPass = "C:\\pleiades\\workspace_endo\\Bizgram\\imgsrc/p";		//	画像保管元のpath
		response.setContentType("image/jpg");

		try {
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			int post_id = Integer.parseInt(request.getParameter("post_id"));				//	投稿画像ごとのpost_idをURLクエリに付加して取得
			FileInputStream fis = new FileInputStream(imgsrcPass + user_id + "_" + post_id + ".jpg");		//	保管元の呼び込み
			int data;
			ServletOutputStream sos = response.getOutputStream();

			while ((data = fis.read()) != -1) {
				sos.write((byte) data);		//	JSPへ書き出し
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;		//	画像一覧ページへ
	}
}
