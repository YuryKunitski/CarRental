package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daoimpl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ConnectionPoolException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.OrderDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.Order;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderDAOImplTest {

	private static ConnectionPool connectionPool;
	private static OrderDAO orderDAO;

	@BeforeClass
	public static void init() throws ConnectionPoolException {
		connectionPool = ConnectionPool.getInstance();
		connectionPool.initConnectionPool();
		orderDAO = DAOFactory.getInstance().getOrderDAO();
	}

	@AfterClass
	public static void dispose() throws ConnectionPoolException {
		connectionPool.dispose();
	}

	@Test(expected = DAOException.class)
	public void addOrder() throws DAOException {
		Order order = new Order();
		User user = new User();
		Car car = new Car();

		order.setUser(user);
		order.setCar(car);

		orderDAO.addOrder(order);
	}

	@Test
	public void findOrderByOrderId() throws DAOException {

		Order order = orderDAO.findOrderByOrderId(26);

		String expectRentStartDate = "2019-05-22";
		String expectRentEndDate = "2019-05-24";
		String expectStatus = "closed";
		double expectTotalBill = 240;

		assertEquals(expectRentStartDate, order.getRentalStartDate());
		assertEquals(expectRentEndDate, order.getRentalEndDate());
		assertEquals(expectStatus, order.getStatus());
		assertEquals(expectTotalBill, order.getTotalBill(), 0.00000001);
	}

}