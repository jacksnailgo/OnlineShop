package service;

import java.sql.SQLException;

import dao.ForwardDao;
import domain.User;

public class ForwardService {

	public User findUser(String username, String password) throws SQLException {
		ForwardDao dao=new ForwardDao();
		User user=dao.findUser(username,password);
		return user;
	}

}
