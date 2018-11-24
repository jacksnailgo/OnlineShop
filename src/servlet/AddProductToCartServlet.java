package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Cart;
import domain.CartItem;
import domain.Product;
import service.ProductService;

/**
 * Servlet implementation class AddProductToCartServlet
 */
public class AddProductToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("进入方法加入购物车");
			// 获得放入购物车的商品pid
			String pid = request.getParameter("pid");
			int buyNum = Integer.parseInt(request.getParameter("buyNum"));
			// 获得product对象
			ProductService service = new ProductService();
			Product product = null;
			try {
				product = service.findProductByPid(pid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 计算小计
			
			double subTotal = product.getShop_price() * buyNum;
			// 封装cartItem
			CartItem cartItem = new CartItem();
			cartItem.setBuyNum(buyNum);
			cartItem.setProduct(product);
			cartItem.setSubtotal(subTotal);
			// 获得购物车,1个用户只有一个购物车,判断session中是否存在购物车
			HttpSession session = (HttpSession) request.getSession();	
			Cart cart = (Cart) session.getAttribute("cart");
			if (cart == null) {
				cart = new Cart();
			}
			//将购物项放到车中---key是pid
			//先判断购物车中是否已将包含此购物项了 ----- 判断key是否已经存在
			//如果购物车中已经存在该商品----将现在买的数量与原有的数量进行相加操作
			Map<String,CartItem> cartItems=cart.getCartItems();
			double newSubTotal=0.0;
			if(cartItems.containsKey(pid)) {
				CartItem items=cartItems.get(pid);
				int oldNum=items.getBuyNum();
				oldNum+=buyNum;
				items.setBuyNum(oldNum);
				cartItems.put(pid, items);
				cart.setCartItems(cartItems);
			}else {
				cart.getCartItems().put(pid, cartItem);
			}
					
			//计算总计金额
			double total=cart.getTotal()+subTotal;
			cart.setTotal(total);
			System.out.println(cart);
			//将购物项放到车中--key是pid
			//我们在cart中已经生成一个map，直接将cartItem放入购物车中。
			//cart.getCartItems().put(pid, cartItem);
			session.setAttribute("cart", cart);
			
			//直接跳转到购物车页面
			//使用转发，因为使用的是session，在整个web中都保存着session。而Cookie要写代码获取
		//request.getRequestDispatcher("/cart.jsp").forward(request, response);
			response.sendRedirect(request.getContextPath()+"/cart.jsp");

		}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
