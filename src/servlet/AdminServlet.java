package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import domain.Category;
import domain.Order;
import domain.OrderItem;
import domain.Product;
import impl.AdminServiceImpl;
import service.AdminService;
import util.BeanFactory;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;
	public void findOrderByOid(HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String oid=request.getParameter("oid");
		//AdminService service=new AdminService();
		//解耦和方式编码---web和Service的耦合
		AdminService service=new AdminServiceImpl();
	//AdminService service=	(AdminService) BeanFactory.getBean("adminService");这里失败了
		//多条数据，每条数据由商品图片，价格，数量，小计，我们把她封装成一个list
		List<Map<String,Object>> listOrderItems=null;
		try {
		 listOrderItems= service.findOrderByOid(oid);
		} catch (SQLException e) {		
			e.printStackTrace();
		}
		Gson gson=new Gson();
		String json=gson.toJson(listOrderItems);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
		
		
	}
	//后台点击删除商品
	 void delProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String listProducts=request.getParameter("listProducts");
		String pid=request.getParameter("pid");
		System.out.println(pid);
		AdminService service=new AdminServiceImpl();
		try {
			service.delProduct(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("listProducts", listProducts);
		request.getRequestDispatcher("admin?method=findProduct").forward(request, response);
		
	}
			//后台显示所有的商品信息
	public void findProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminService service=new AdminServiceImpl();
		List<Product> listProducts=null;
		try {
			listProducts=service.findAllProducts();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		request.setAttribute("listProducts", listProducts);
	
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
		
	}
	//获得所有订单
	public void findOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AdminService service=new AdminServiceImpl();
		List<Order> listOrders=null;
		try {
			listOrders =service.findAllOrders();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("listOrders", listOrders);
		request.getRequestDispatcher("/admin/order/list.jsp").forward(request, response);
		
	}
	public void findAllCategory(HttpServletRequest request, HttpServletResponse response) {
		//提交一个集合List<category> ，并转换成JSON字符串
		AdminService service=new AdminServiceImpl();
		List<Category> listCategory=null;
		try {
			listCategory=service.findAllCategory();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gson gson=new Gson();
		String json=gson.toJson(listCategory);
		System.out.println(json);
		response.setContentType("text/json;charset=UTF-8");
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
