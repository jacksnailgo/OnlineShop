package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dao.AdminDao;
import domain.Category;
import domain.Order;
import domain.OrderItem;
import domain.Product;

public interface AdminService {

	public List<Category> findAllCategory() throws SQLException;

	public boolean addProduct(Product product) throws SQLException;

	public List<Order> findAllOrders() throws SQLException;

	public List<Product> findAllProducts() throws SQLException;

	public void delProduct(String pid) throws SQLException;

	public List<Map<String,Object>> findOrderByOid(String oid) throws SQLException;

}
