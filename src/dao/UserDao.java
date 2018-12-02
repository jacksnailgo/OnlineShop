package dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import domain.User;
import util.DataSourceUtils;

public class UserDao {

	public int register(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		int row = runner.update(sql, user.getUid(), user.getUsername(), user.getPassword(), user.getName(),
				user.getEmail(), user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(),
				user.getCode());
		return row;
	}

	public void changeState(String activeCode) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="update user set state=1 where code=? ";
		runner.update(sql,activeCode);
	}

	public int checkUsername(String username) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select count(*) from user where username=? ";
		Long res=(Long) runner.query(sql, new ScalarHandler(),username);
		System.out.println("dao查询结果"+res);
		return res.intValue();
	}

}
