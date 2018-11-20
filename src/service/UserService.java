package service;

import java.sql.SQLException;

import dao.UserDao;
import domain.User;

public class UserService {

	public boolean register(User user) throws SQLException {
		UserDao dao=new UserDao();
		int row=dao.register(user);
		if(row>0)return true;
		return false;
	}

}
