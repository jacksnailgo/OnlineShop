package servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import domain.User;
import service.UserService;
import util.CommonUtils;


/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// 获得表单数据
		Map<String, String[]> parameterMap = request.getParameterMap();
		User user = new User();
		
		//自己指定一个类型转换器，将(String转换成date)
		ConvertUtils.register(	new Converter() {

			@Override
			public Object convert(Class clazz, Object value) {
				//将String转成Date
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
				Date parse=null;
				try {
					parse= sdf.parse(value.toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				return parse;
			}
			
		}, Date.class);
		
		// private String uid;
		user.setUid(CommonUtils.getUUID());
		// private String telephone;
		user.setTelephone(null);
//		private int state;	//账户是否激活
		user.setState(0);
//		private String code;  //激活码
		user.setCode(CommonUtils.getUUID());
		try {
			//映射封装
			BeanUtils.populate(user, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//将User传递给下一层
		UserService service=new UserService();
		boolean isRegistSuccess=false;
		try {
			
			
			 isRegistSuccess=service.register(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//注册是否成功，注册成功，跳转登陆页面，失败则提示
		if(isRegistSuccess) {
			response.sendRedirect(request.getContextPath()+"/RegisterSuccess.jsp");
		}else {
			response.sendRedirect(request.getContextPath()+"/RegisterFail.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
