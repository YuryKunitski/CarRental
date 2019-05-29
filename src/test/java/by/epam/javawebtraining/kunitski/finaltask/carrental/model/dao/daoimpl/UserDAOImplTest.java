package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daoimpl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ConnectionPoolException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.UserDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.RoleType;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserDAOImplTest {

	private static ConnectionPool connectionPool;
	private static UserDAO userDAO;

	@BeforeClass
	public static void init() throws ConnectionPoolException {
		connectionPool = ConnectionPool.getInstance();
		connectionPool.initConnectionPool();
		userDAO = DAOFactory.getInstance().getUserDAO();
	}

	@AfterClass
	public static void dispose() throws ConnectionPoolException {
		connectionPool.dispose();
	}

	@Test(expected = DAOException.class)
	public void register() throws Exception {
		User user = new User();
		user.setLogin("Vasil");
		user.setPassword("1111");
		user.setRoleType(RoleType.CUSTOMER);
		user.setSurName("Vasiliev");
		user.setName("Vasili");
		user.setPhone("+37529 9999999");
		user.setEmail("Vasili@mail.ru");

		userDAO.register(user);
	}

	@Test
	public void authorize() throws DAOException {
		String login = "marusia";
		String password = "102030";

		String expectName = "Маруся";
		String expectSurName = "Марусина";
		String expectEmail = "supermarusia@gmail.com";
		String expectPassportID = "MR123456789";

		User user = userDAO.authorize(login, password);
		assertEquals(expectName, user.getName());
		assertEquals(expectSurName, user.getSurName());
		assertEquals(expectEmail, user.getEmail());
		assertEquals(expectPassportID, user.getPassportID());
	}
}