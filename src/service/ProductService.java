package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dao.ProductDao;
import domain.Category;
import domain.Order;
import domain.OrderItem;
import domain.PageBean;
import domain.Product;
import util.DataSourceUtils;

public class ProductService {
	
	
//获得最热商品
	public List<Product> findHotProducts() {
		ProductDao dao=new ProductDao();
		List<Product> HotProducts=null;
		try {
			HotProducts= dao.findHotProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return HotProducts;
	}
//获得最新商品
	public List<Product> findNewProducts() {
		ProductDao dao=new ProductDao();
		List<Product> newProducts=null;
		try {
			dao.findNewProducts();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return newProducts;
	}
	public List<Category> findAllCategory() {
		ProductDao dao=new ProductDao();
		List<Category> listCategory=null;
		try {
			listCategory=dao.findAllCategory();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listCategory;
	}
	public PageBean<Product> findProductByCid(String cid,int currentPage,int currentCount ) {
		PageBean<Product> pageBean=new PageBean<Product>();
		
		
		//1.封装当前页
		pageBean.setCurrentPage(currentPage);
		//2.封装每页显示条数
		
		pageBean.setCurrentCount(currentCount);	
		//3.封装总条数
		ProductDao dao=new ProductDao();
		int totalCount=0;
		try {
			totalCount=dao.getCount(cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageBean.setTotalCount(totalCount);
		//4.封装总页数
		int totalPage=(int) Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		//5.当前页显示数据
		List<Product> listProducts=null;
		int index=(currentPage-1)*currentCount;
		try {
			listProducts=dao.findProductsByCategory(cid,index,currentCount);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageBean.setList(listProducts);
		System.out.println(pageBean);

		//pageBean封装好了，返回Web层
		return pageBean;
		
	}
	public Product findProductByPid(String pid) throws SQLException {
		ProductDao dao=new ProductDao();
		
		return dao.findProductByPid(pid);
	}
	public void submitOrder(Order order) throws SQLException {
		ProductDao dao=new ProductDao();
		
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();
			//2.调用dao存储Order表数据的方法
			dao.addOrders(order);
			//3.调用dao存储orderItem表数据的方法（多次提交）
			dao.addOrderItem(order);
			
		} catch (Exception e) {
			DataSourceUtils.rollback();
			e.printStackTrace();
		}finally {
			DataSourceUtils.commitAndRelease();
		}
		
	}
	public void updateOrder(Order order) throws SQLException {
		ProductDao dao=new ProductDao();
		dao.updateOrder(order);
		
	}
	public void changeState(String r6_Order) {
		ProductDao dao=new ProductDao();
		try {
			dao.changeState(r6_Order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<Order> findAllOrders(String uid) throws SQLException {
		ProductDao dao=new ProductDao();
		return dao.findAllOrders(uid);
	}
	public List<Map<String,Object>> findAllOrderItemByOid(String oid) throws SQLException {
		ProductDao dao=new ProductDao();
		return dao.findAllOrderItemByOid(oid);
	}

}
