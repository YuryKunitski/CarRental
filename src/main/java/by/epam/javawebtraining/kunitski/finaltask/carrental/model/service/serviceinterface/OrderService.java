package by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.Order;

import java.util.List;

public interface OrderService {

	/**
	 * Sent data to DAO for input an order
	 * @param userId user's id, who made an order
	 * @param carId ordered car id
	 * @param rentalStartDate supposed rental start date
	 * @param rentalEndDate supposed rental end date
	 * @return true - order added, false - order doesn't added
	 * @throws ServiceException sent data to DAO for input an order
	 */

	 boolean addOrder(int userId, int carId, String rentalStartDate, String rentalEndDate) throws ServiceException;

	/**
	 * Data transfer to the DAO to find for an order by the user's id who made the order
	 * @param userId user's id
	 * @return list of orders made by a specific user
	 * @throws ServiceException find for an order by the user's id
	 */
	 List<Order> findOrdersByUserId(int userId) throws ServiceException;

	/**
	 * Find order by order id
	 *
	 * @param orderId
	 * @return sought order
	 * @throws DAOException exception find order
	 */
	 Order findOrderByOrderId(int orderId) throws ServiceException;

	/**
	 * Getting all orders
	 *
	 * @return list of all orders
	 * @throws DAOException exception getting orders list
	 */
	 List<Order> takeAllOrders(int pageNumber, int ordersOnPage) throws ServiceException;

	/**
	 * Find an order by order_id for administrator
	 *
	 * @param orderId order's id
	 * @return finding order
	 * @throws DAOException exception finding order
	 */
	 Order takeAdminOrderByOrderId(int orderId) throws ServiceException;

	/**
	 * Changing the status of the order, without the reason for the change
	 *
	 * @param status  new status
	 * @param orderId id changing order
	 * @throws DAOException exception changing the status
	 */
	 void updateStatusWithOutReason(String status, int orderId) throws ServiceException;

	/**
	 * Changing the status of the order, indicating the reason for the change
	 *
	 * @param status    new status
	 * @param orderId   order id
	 * @param orderInfo reason of changing the status
	 * @throws DAOException exception changing the status
	 */
	 void updateStatusById(String status, int orderId, String orderInfo) throws ServiceException;

	/**
	 * Changing damage price
	 *
	 * @param orderId order's id
	 * @param damagePrice damage price
	 * @throws DAOException exception changing damage price
	 */
	 void updateDamagePriceByOrderId(int orderId, double damagePrice) throws ServiceException;

	/**
	 * Count pages all orders
	 *
	 * @param amountOrdersOnPage amount orders on page
	 * @throws DAOException exception count pages all orders
	 */
	public int countPageAmountAllOrders(int amountOrdersOnPage) throws ServiceException;

	/**
	 * Check all statuses of car by car ID
	 *
	 * @param carId car id
	 * @throws DAOException exception is may delete
	 * @return true - if car may delete/else - false
	 */
	public boolean isMayDelete(int carId) throws ServiceException;

	/**
	 * Taking all discount
	 *
	 * @return list of all discount
	 * @throws ServiceException exception taking all discount
	 */
	public List<Double> takeAllDiscountCoefficients() throws ServiceException;



}
