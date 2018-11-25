package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import domain.Category;
import domain.Order;
import domain.OrderItem;
import domain.Product;
import util.DataSourceUtils;

public class ProductDao {

	public List<Product> findHotProducts() throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where is_hot=1 limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class),0,9);
		
	}

	public List<Product> findNewProducts() throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product order by pdate ";
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
		
	}

	public List<Category> findAllCategory() throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from category  ";
		
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}

	public int getCount(String cid) throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select count(*) from product  where cid=?";
		Long count=(Long) runner.query(sql, new ScalarHandler(),cid);
		return count.intValue();
	}
//根据商品的分类进行分页查询商品
	public List<Product> findProductsByCategory(String cid, int index, int currentCount) throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where cid=? limit ?,?";
		List<Product> products=	runner.query(sql, new BeanListHandler<Product>(Product.class),cid,index,currentCount);
		return products;
	}

	public Product findProductByPid(String pid) throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from product where pid=?";
		return runner.query(sql, new BeanHandler<>(Product.class),pid);
		
	}
	//向order插入数据
	public void addOrders(Order order) throws SQLException {
		//手动控制事务，conn需要传递进入QueryRunner
		QueryRunner runner =new QueryRunner();
		String sql="insert into orders values(?,?,?,?,?,?,?,?) ";
		Connection conn=DataSourceUtils.getConnection();
		runner.update(conn, sql, order.getOid(),order.getOrdertime(),order.getTotal(),order.getState()
				,order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid());
		
		
	}

	public void addOrderItem(Order order) throws SQLException {
		QueryRunner runner =new QueryRunner();
		String sql="insert into orderItem values(?,?,?,?,?) ";
		Connection conn=DataSourceUtils.getConnection();
	List<OrderItem> list=	order.getOrderItems();
	for(OrderItem item:list) {
		runner.update(conn,sql,item.getItemid(),item.getCount(),item.getSubtotal(),item.getProduct().getPid(),item.getOrder().getOid());
	}
	}

}
