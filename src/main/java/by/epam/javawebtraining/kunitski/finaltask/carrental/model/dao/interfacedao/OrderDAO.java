package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.interfacedao;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.Order;

import java.util.List;

public interface OrderDAO {
	/**
	 * Add order into database
	 *
	 * @param order adding order
	 * @throws DAOException exception adding order into data base
	 */
	void addOrder(Order order) throws DAOException;

	/**
	 * Find orders by user id
	 *
	 * @param userId
	 * @return list of orders
	 * @throws DAOException exception getting orders list
	 */
	List<Order> findOrdersByUserId(int userId, int toStartPage, int ordersOnPage) throws DAOException;

	/**
	 * Find orders by car id
	 *
	 * @param carId
	 * @return list of orders
	 * @throws DAOException exception getting orders list
	 */
	List<Order> findOrdersByCarId(int carId, int toStartPage, int ordersOnPage) throws DAOException;


	/**
	 * Getting all orders
	 *
	 * @return list of all orders
	 * @throws DAOException exception getting orders list
	 */
	List<Order> takeAllOrders(int toStartPage, int carsOnPage) throws DAOException;

	/**
	 * Find order by order id
	 *
	 * @param orderId
	 * @return sought order
	 * @throws DAOException exception sought order
	 */
	Order findOrderByOrderId(int orderId) throws DAOException;

	/**
	 * Changing the status of the order, indicating the reason for the change
	 *
	 * @param status    new status
	 * @param orderId   order id
	 * @param orderInfo reason of changing the status
	 * @throws DAOException exception changing the status
	 */
	void updateStatusWithReason(String status, int orderId, String orderInfo) throws DAOException;

	/**
	 * Changing the status of the order, without the reason for the change
	 *
	 * @param status  new status
	 * @param orderId id изменяемого заказа
	 * @throws DAOException exception changing the status
	 */
	void updateStatusWithoutReason(String status, int orderId) throws DAOException;

	/**
	 * Take all count of orders
	 *
	 * @throws DAOException exception take all count of orders
	 */
	int countAllOrders() throws DAOException;

}
