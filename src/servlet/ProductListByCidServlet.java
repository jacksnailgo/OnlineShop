package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import domain.PageBean;
import domain.Product;
import service.ProductService;

/**
 * Servlet implementation class ProductListByCidServlet
 */
public class ProductListByCidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获得cid
		String cid=request.getParameter("cid");
		//将pageBean的2个数据通过web层来获取
		String currentPageStr=request.getParameter("currentPage");
		if(currentPageStr==null)currentPageStr="1";
		int currentPage=Integer.parseInt(currentPageStr);
		int currentCount=6;
		
		ProductService service=new ProductService();
		PageBean pageBean=null;
		pageBean=service.findProductByCid(cid,currentPage,currentCount);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("cid", cid);
		
		//定义一个记录历史商品信息的集合
		List<Product> historyProduct=new ArrayList<Product>();
		
		Cookie[] cookies=request.getCookies();
		if(cookies!=null) {
			for(Cookie cookie:cookies) {
				if("pids".equals(cookie.getName())) {
					String pids=cookie.getValue();
					String[] spilt=pids.split("-");
					for(String pid:spilt) {
						Product pro=null;
						try {
							pro=service.findProductByPid(pid);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						historyProduct.add(pro);		
					}
				}
			}
		}
		
		//将历史记录的集合放入域中
		
		request.setAttribute("historyProduct", historyProduct);
		
		
		
		
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
