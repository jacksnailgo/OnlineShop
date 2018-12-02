package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import domain.Category;
import domain.Order;
import domain.OrderItem;
import domain.Product;
import util.DataSourceUtils;

public class AdminDao {

	public List<Category> findAllCategory() throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from category  ";
		
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}

	public boolean addProduct(Product product) throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into product values(?,?,?,?,?,?,?,?,?,?)";
		int row=runner.update(sql,product.getPid(),product.getPname(),product.getMarket_price(),
				product.getShop_price(),product.getPimage(),product.getPdate(),
				product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCategory().getCid());
		System.out.println("是否成功执行"+row);
		if(row>0)return true;
		return false;
	}
	/*private String pid;
	private String pname;
	private double market_price;
	private double shop_price;
	private String pimage;
	private Date pdate;
	private int  is_hot;
	private String pdesc;
	private int pflag;
	private Category category;*/

	public List<Order> findAllOrders() throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select *from orders";
		return runner.query(sql, new BeanListHandler<Order>(Order.class));
	}

	public List<Product> findAllProducts() throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select *from product";
		return runner.query(sql, new BeanListHandler<Product>(Product.class));

	}

	public void delProduct(String pid) throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql="delete from product where pid=?";
		runner.update(sql,pid);
		System.out.println("delete:"+pid);
		
	}

	public List<Map<String,Object>> findOrderByOid(String oid) throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select p.pimage,p.pname,p.shop_price,i.count,i.subtotal "+
				" from orderitem i,product p "+
				" where i.pid=p.pid and i.oid=? ";
		return runner.query(sql, new MapListHandler(),oid);
	}
}
