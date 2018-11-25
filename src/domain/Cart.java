package domain;

import java.util.HashMap;
import java.util.Map;

public class Cart {
	@Override
	public String toString() {
		return "Cart [cartItems=" + cartItems + ", total=" + total + "]";
	}
	//原本设计为List，但是在业务中删除购物项，list在删除过程比较繁琐
	//<商品ID，购物车项>
	private Map<String,CartItem> cartItems=new HashMap();
	//总金额
	private double 	total;
	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
}
