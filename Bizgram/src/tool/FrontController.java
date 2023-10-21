package tool;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"*.action"})
@MultipartConfig(maxFileSize=1048576)
public class FrontController extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String path = request.getServletPath().substring(1);
			String name = path.replace(".a", "A").replace('/', '.');
			Action action = (Action)Class.forName(name).newInstance();
			String url = action.execute(request, response);
			if(url != null) {
				request.getRequestDispatcher(url).forward(request, response);
			}
		} catch (Exception e) {
			PrintWriter out = response.getWriter();
			e.printStackTrace(out);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
