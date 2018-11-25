package domain;

public class OrderItem {
	/*`itemid` varchar(32) NOT NULL,
	  `count` int(11) DEFAULT NULL,
	  `subtotal` double DEFAULT NULL,
	  `pid` varchar(32) DEFAULT NULL,
	  `oid` varchar(32) DEFAULT NULL,
	  PRIMARY KEY (`itemid`),
	  KEY `fk_0001` (`pid`),
	  KEY `fk_0002` (`oid`),*/
	private String itemid;//订单项的id
	private int count;//订单内商品购买的数量
	private double subtotal;//订单项的小计
	/*private String pid;
	不能这样描述，根据面向对象
	*/
	private Product product;  //订单项内的商品
	private Order order;			//订单项对应的订单id
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
}
