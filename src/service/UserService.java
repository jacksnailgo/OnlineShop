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

	public void changeState(String activeCode) {
		UserDao dao=new UserDao();
		try {
			dao.changeState(activeCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public int checkUsername(String username) throws SQLException {
		UserDao dao=new UserDao();
	int res= dao.checkUsername(username);

	return res;
	}

}
