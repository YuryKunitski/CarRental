package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface;

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
	 * Find cars id that are used for a certain period of time.
	 * @param order заказ
	 * @return список id автомобилей
	 * @throws DAOException ошибка при получении списка id автомобилей
	 */
	public List<Integer> findUsedCarsID(Order order) throws DAOException;


	/**
	 * Find orders by user id
	 *
	 * @param userId
	 * @return list of orders
	 * @throws DAOException exception getting orders list
	 */
	List<Order> findOrdersByUserId(int userId) throws DAOException;

	/**
	 * Find orders by car id
	 *
	 * @param carId
	 * @return list of orders
	 * @throws DAOException exception getting orders list
	 */
	List<Order> findOrdersByCarId(int carId) throws DAOException;


	/**
	 * Getting all orders
	 *
	 * @return list of all orders
	 * @throws DAOException exception getting orders list
	 */
	List<Order> takeAllOrders(int toStartPage, int ordersOnPage) throws DAOException;

	/**
	 * Find an order by order_id for administrator
	 * @param orderId order's id
	 * @return finding order
	 * @throws DAOException exception finding order
	 */
	public Order takeAdminOrderByOrderId(int orderId) throws DAOException;


	/**
	 * Find order by order id
	 *
	 * @param orderId
	 * @return sought order
	 * @throws DAOException exception find order
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
	 * @param orderId id changing order
	 * @throws DAOException exception changing the status
	 */
	void updateStatusWithoutReason(String status, int orderId) throws DAOException;

	/**
	 * Changing damage price
	 * @param orderId order's id
	 * @param damagePrice damage price
	 * @throws DAOException exception changing damage price
	 */
	public void updateDamagePriceByOrderId(int orderId, double damagePrice) throws DAOException;

	/**
	 * Taking discount coefficient
	 * @param countRentDays count rent days
	 * @throws DAOException taking discount coefficient
	 */
	public double takeDiscountCoefficient(int countRentDays) throws DAOException;

	/**
	 * Taking all discount coefficients
	 * @return list of all discount coefficient
	 * @throws DAOException changing discount coefficient
	 */
	public List<Double> takeAllDiscountCoefficients () throws DAOException;


	/**
	 * Take all count of orders
	 *
	 * @throws DAOException exception take all count of orders
	 */
	int countAllOrders() throws DAOException;

	/**
	 * Find Status By Car Id
	 *
	 * @throws DAOException exception take all count of orders
	 */
	public List<String> findStatusByCarId(int carID) throws DAOException;

}
