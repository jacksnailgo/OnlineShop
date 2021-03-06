package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Category;
import domain.Product;
import service.ProductService;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.准备热门商品List<Product>
		ProductService service=new ProductService();
		List<Product> hotProductList=service.findHotProducts();
		
		//2.准备最新商品
		List<Product> newProductList=service.findNewProducts();
		//3.准备分类数据
		List<Category> category=service.findAllCategory();
		
		
		request.setAttribute("hotProductList", hotProductList);
		request.setAttribute("newProductList", newProductList);
		//request.setAttribute("category", category);
		System.out.println("最新："+newProductList);
		System.out.println("目录："+category.isEmpty());
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
