package dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.taglibs.standard.tag.common.sql.DataSourceUtil;

import domain.User;
import util.DataSourceUtils;

public class UserDao {

	public int register(User user) throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into user values(?,?,?,?,?,?,?,?,?,?)";
		int row=runner.update(sql, user.getUid(),user.getUsername(),user.getPassword(),user.getEmail(),user.getTelephone(),
				user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()	);
		return row;
	}

}
