package servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.User;
import service.ForwardService;


/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		HttpSession session=request.getSession();
		User u=null;
		ForwardService service=new ForwardService();
		try {
			u=service.findUser(username,password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(u!=null) {
			System.out.println(u.getUsername());
			//登陆成功，跳转到页面
			String autoLogin=request.getParameter("autoLogin");
			//判断用户是否勾选登陆
		if(autoLogin!=null) {
			//对中文进行编码
			 username=URLEncoder.encode(username, "UTF-8");
			//这里相当于增强的功能，如果存储了cookie，以后可以自动登陆
			Cookie cookie_username=new Cookie("cookie_username", username);
			Cookie cookie_password=new Cookie("cookie_password", password);
			//设置Cookie持久化事件1小时
			//设置携带路径
			cookie_username.setPath(request.getContextPath());
			cookie_password.setPath(request.getContextPath());
			//response发送Cookie
				response.addCookie(cookie_username);
				response.addCookie(cookie_password);	
			}
			
			//将登陆的信息存在session中
			session.setAttribute("user", u);
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}else {
			//登陆失败，停留在本页面，同时说明用户名或密码错误
			request.setAttribute("info", "用户名或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
