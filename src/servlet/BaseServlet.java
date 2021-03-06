package servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BaseServlet
 */
@SuppressWarnings("all")
public class BaseServlet extends HttpServlet {
	//目的是为了解决使用繁杂的if判断语句，去调用不同方法的问题。 
	//获得当前类，去执行与method同名的方法。（通过反射获得method，调用invoke）
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		//1.获得请求的method的名称
		String method=req.getParameter("method");
		//2.获得当前Base被访问对象的字节码对象
		Class clazz=this.getClass();//this代表当前对象.productServlet.class
		//3.获得当前字节码对象指定的方法
		try {
			Method methodName=clazz.getMethod(method, HttpServletRequest.class,HttpServletResponse.class);
		//4.执行相应的功能方法
			methodName.invoke(this,req, res);
			clazz.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
