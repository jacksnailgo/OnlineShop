package dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import domain.User;
import util.DataSourceUtils;

public class ForwardDao {

	public User findUser(String username, String password) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from user where username=? and password =?";
		System.out.println(password);
		User user= (User) runner.query(sql, new BeanHandler<User>(User.class),username,password);
		if(user!=null)
		System.out.println("dao层查询到的user"+user.getUsername());
		else System.out.println("dao层没有查询到user");
		return user;
	}

}
