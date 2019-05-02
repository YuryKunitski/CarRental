package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.impldao;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.interfacedao.OrderDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.Order;

import java.util.List;

public class OrderDAOImpl implements OrderDAO {
	@Override
	public void addOrder(Order order) throws DAOException {

	}

	@Override
	public List<Order> findOrdersByUserId(int userId, int toStartPage, int ordersOnPage) throws DAOException {
		return null;
	}

	@Override
	public List<Order> findOrdersByCarId(int carId, int toStartPage, int ordersOnPage) throws DAOException {
		return null;
	}

	@Override
	public List<Order> takeAllOrders(int toStartPage, int carsOnPage) throws DAOException {
		return null;
	}

	@Override
	public Order findOrderByOrderId(int orderId) throws DAOException {
		return null;
	}

	@Override
	public void updateStatusWithReason(String status, int orderId, String orderInfo) throws DAOException {

	}

	@Override
	public void updateStatusWithoutReason(String status, int orderId) throws DAOException {

	}

	@Override
	public int countAllOrders() throws DAOException {
		return 0;
	}
}
