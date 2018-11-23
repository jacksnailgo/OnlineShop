package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Product;
import service.ProductService;

/**
 * Servlet implementation class ProductInfoServlet
 */
public class ProductInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//3新增返回按钮
		//获得当前页和当前商品类别
		String currentPage=request.getParameter("currentPage");
		String cid=request.getParameter("cid"); 
		
		String pid=request.getParameter("pid");
		Product product=null;
		ProductService service=new ProductService();
		try {
			product=service.findProductByPid(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("product", product);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("cid", cid);
		//获得客户端携带的Cookie--获得名字是Pids的cookie
	String pids="";
	Cookie[] cookies=request.getCookies();
	if(cookies!=null) {
		for(Cookie cookie:cookies) {
			if("pids".equals(cookie.getName())) {
				 pids=cookie.getValue();  //"3-1-2"
				String [] str=pids.split("-");
				List<String> asList=Arrays.asList(str);
				LinkedList<String> list=new LinkedList<String>(asList);
				if(list.contains(pid)) {
					list.remove(pid);
				}
				list.addFirst(pid);
				StringBuffer sb=new StringBuffer();
				for(int i=0;i<list.size()&&i<7;i++) {
					sb.append(list.get(i));
					sb.append("-");
				}
				pids=sb.substring(0,sb.length()-1);
				
			}
			Cookie cookie_pids=new Cookie("pids",pids);
			response.addCookie(cookie_pids);
		}
	}
		
		//4.转发之前，创建cookie，存储Pid
		request.getRequestDispatcher("product_info.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

