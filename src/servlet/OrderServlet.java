package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Cart;
import domain.CartItem;
import domain.Order;
import domain.OrderItem;
import domain.User;
import service.ProductService;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获得数据
		//获得登陆用户
		User loginUser =(User) request.getSession().getAttribute("user");
		if(loginUser==null) {
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return ;
		}
		
		//2.封装数据,传递给service层
		Order order=new Order();
//		private String oid;//订单号
		order.setOid(UUID.randomUUID().toString());
//		private Date ordertime;//下单时间
		order.setOrdertime(new Date());
//		private double total;//订单总金额
		Cart cart=(Cart)request.getSession().getAttribute("cart");//这一步是为了获得购物车中的总金额
		double total=cart.getTotal();
		order.setTotal(total);
//		private int state;  //订单状态，1，2，3，4     1代表已付款   0代表未付
		order.setState(0);
//		private String address;		//收货地址
		order.setAddress(null);
//		private String name;//收货人
		order.setName(null);
//		private String telephone;
		order.setTelephone(null);
//		private User user;//该订单属于哪个用户
		order.setUser(loginUser);
//		该订单中有多少购物项
		//获得购物车中购物项
		Map<String,CartItem> map=cart.getCartItems();
		//图的遍历
		for(Map.Entry<String, CartItem> entry:map.entrySet()) {
			//将cartItem放入OrderItem中
			//创建新的订单项
			OrderItem item=new OrderItem(); 
			item.setItemid(UUID.randomUUID().toString());
			item.setCount(entry.getValue().getBuyNum());
			item.setSubtotal(entry.getValue().getSubtotal());
			item.setProduct(entry.getValue().getProduct());
			item.setOrder(order);
			//将该订单项添加到集合中
			order.getOrderItems().add(item);
		}
//order对象封装完毕
		//3.传递数据到Service层
		ProductService service=new ProductService();
		try {
			service.submitOrder(order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

}
