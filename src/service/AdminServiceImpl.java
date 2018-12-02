package impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dao.AdminDao;
import domain.Category;
import domain.Order;
import domain.Product;
import service.AdminService;

public class AdminServiceImpl implements AdminService {
	public List<Category> findAllCategory() throws SQLException {
		AdminDao dao=new AdminDao();
		 List<Category> list=null;
		 list=dao.findAllCategory();
		 return list;
	}

	public boolean addProduct(Product product) throws SQLException {
		AdminDao dao=new AdminDao();
		return	dao.addProduct(product);
		
	}

	public List<Order> findAllOrders() throws SQLException {
		AdminDao dao=new AdminDao();
		return dao.findAllOrders();
		
	}

	public List<Product> findAllProducts() throws SQLException {
		AdminDao dao=new AdminDao();
		return dao.findAllProducts();
		
	}

	public void delProduct(String pid) throws SQLException {
		AdminDao dao=new AdminDao();
		dao.delProduct(pid);
		
		
	}

	public List<Map<String,Object>> findOrderByOid(String oid) throws SQLException {
		AdminDao dao=new AdminDao();
		List<Map<String,Object>> listItems=dao.findOrderByOid(oid);
		return listItems;
	}
}
