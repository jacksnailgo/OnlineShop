package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.google.gson.Gson;

import domain.Cart;
import domain.CartItem;
import domain.Category;
import domain.PageBean;
import domain.Product;
import service.ProductService;

/**
 * 该类中没有service方法，会去执行父类的service方法，此时this指代的都是ProductServlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	/*protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String methodName = request.getParameter("method");
		if ("categoryList".equals(methodName)) {
			categoryList(request, response);
		} else if ("index".equals(methodName)) {
			productListInfo(request, response);
		} else if ("productInfo".equals(methodName)) {
			productInfo(request, response);
		} else if ("productListInfo".equals(methodName)) {
			productListInfo(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}*/

	// 模块中的功能是根据方法区分的
	// 显示商品类别的功能
	public void categoryList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ProductService service = new ProductService();
		List<Category> category = service.findAllCategory();
		// 将数据转成JSON格式
		Gson gson = new Gson();
		String json = gson.toJson(category);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
	}

	// 显示首页的功能
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.准备热门商品List<Product>
		ProductService service = new ProductService();
		List<Product> hotProductList = service.findHotProducts();

		// 2.准备最新商品
		List<Product> newProductList = service.findNewProducts();
		// 3.准备分类数据
		List<Category> category = service.findAllCategory();

		request.setAttribute("hotProductList", hotProductList);
		request.setAttribute("newProductList", newProductList);
		// request.setAttribute("category", category);
		System.out.println("最新商品：" + newProductList);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	// 显示商品的详细信息
	public void productInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 3新增返回按钮
		// 获得当前页和当前商品类别
		String currentPage = request.getParameter("currentPage");
		String cid = request.getParameter("cid");

		String pid = request.getParameter("pid");
		Product product = null;
		ProductService service = new ProductService();
		try {
			product = service.findProductByPid(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("product", product);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("cid", cid);
		// 获得客户端携带的Cookie--获得名字是Pids的cookie
		String pids = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("pids".equals(cookie.getName())) {
					pids = cookie.getValue(); // "3-1-2"
					String[] str = pids.split("-");
					List<String> asList = Arrays.asList(str);
					LinkedList<String> list = new LinkedList<String>(asList);
					if (list.contains(pid)) {
						list.remove(pid);
					}
					list.addFirst(pid);
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < list.size() && i < 7; i++) {
						sb.append(list.get(i));
						sb.append("-");
					}
					pids = sb.substring(0, sb.length() - 1);

				}
				Cookie cookie_pids = new Cookie("pids", pids);
				response.addCookie(cookie_pids);
			}
		}

		// 4.转发之前，创建cookie，存储Pid
		request.getRequestDispatcher("product_info.jsp").forward(request, response);

	}

	// 根据商品的类别获得商品的列表
	public void productListInfo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.获得cid
		String cid = request.getParameter("cid");
		// 将pageBean的2个数据通过web层来获取
		String currentPageStr = request.getParameter("currentPage");
		if (currentPageStr == null)
			currentPageStr = "1";
		int currentPage = Integer.parseInt(currentPageStr);
		int currentCount = 6;

		ProductService service = new ProductService();
		PageBean pageBean = null;
		pageBean = service.findProductByCid(cid, currentPage, currentCount);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);

		// 定义一个记录历史商品信息的集合
		List<Product> historyProduct = new ArrayList<Product>();

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("pids".equals(cookie.getName())) {
					String pids = cookie.getValue();
					String[] spilt = pids.split("-");
					for (String pid : spilt) {
						Product pro = null;
						try {
							pro = service.findProductByPid(pid);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						historyProduct.add(pro);
					}
				}
			}
		}

		/*
		 * //定义一个记录历史商品信息的集合 List<Product> historyProduct = new ArrayList<Product>();
		 * //获得客户端携带名字叫pids的cookie Cookie[] cookies = request.getCookies();
		 * if(cookies!=null){ for(Cookie cookie:cookies){
		 * if("pids".equals(cookie.getName())){ String pids = cookie.getValue();//3-2-1
		 * String[] split = pids.split("-"); for(String pid : split){ Product pro=null;
		 * try { pro = service.findProductByPid(pid); } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } historyProduct.add(pro); }
		 * } } }
		 * 
		 * //将历史记录的集合放到域中
		 */

		// 将历史记录的集合放入域中

		request.setAttribute("historyProduct", historyProduct);

		request.getRequestDispatcher("/product_list.jsp").forward(request, response);

	}

	public void addProductToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	public void delProductToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String pid=request.getParameter("pid");
		//购物车是放在session中
		HttpSession session=request.getSession();
		Cart cart=(Cart) session.getAttribute("cart");
		if(cart!=null) {
			Map<String,CartItem> map=cart.getCartItems();
			cart.setTotal(cart.getTotal()-map.get(pid).getSubtotal());
			map.remove(pid);
			cart.setCartItems(map);
		}
		
		session.setAttribute("cart", cart);
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
		
	}
	public void delAllProductToCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session=request.getSession();
		session.removeAttribute("cart");
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}
}
