package by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceimpl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ConnectionPoolException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.CarDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.OrderDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.UserDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.Order;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceConstant;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.OrderService;
import com.google.protobuf.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderServiceImpl implements OrderService {

	private static final Logger LOG = LogManager.getLogger(OrderServiceImpl.class.getName());

	private static final OrderDAO ORDER_DAO = DAOFactory.getInstance().getOrderDAO();
	private static final String ORDER_STATUS_DEFOULT = "undefined";

	/**
	 * Sent data to DAO for input an order
	 *
	 * @param userId          user's id, who made an order
	 * @param carId           ordered car id
	 * @param rentalStartDate supposed rental start date
	 * @param rentalEndDate   supposed rental end date
	 * @return true - order added, false - order doesn't added
	 * @throws ServiceException sent data to DAO for input an order
	 */
	@Override
	public boolean addOrder(int userId, int carId, Date rentalStartDate, Date rentalEndDate) throws ServiceException {

		LOG.debug(ServiceConstant.SERVICE_ADD_ORDER_STARTS_MSG);
		UserDAO userDao = DAOFactory.getInstance().getUserDAO();
		CarDAO carDAO = DAOFactory.getInstance().getCarDAO();


		try {
			User user = userDao.findUserById(userId);
			Car car = carDAO.takeCarById(carId);
			int countRentDays = countRentDays(rentalStartDate, rentalEndDate);
			double discCoeff = ORDER_DAO.takeDiscountCoefficient(countRentDays);


			Order order = new Order();
			order.setUser(user);
			order.setCar(car);
			order.setRentalStartDate(rentalStartDate);
			order.setRentalEndDate(rentalEndDate);
			order.setStatus(ORDER_STATUS_DEFOULT);
			order.setTotalBill(calculateTotalBill(car, countRentDays, discCoeff));

			List<Integer> carIds = ORDER_DAO.findUsedCarsID(order);
			boolean carUsed = false;

			for (Integer id : carIds) {
				if (id == carId) {
					carUsed = true;
					break;
				}
			}
			if (!carUsed) {
				ORDER_DAO.addOrder(order);
				LOG.debug(ServiceConstant.SERVICE_ADD_ORDER_ENDS_MSG);
				return true;
			} else {
				LOG.debug(ServiceConstant.SERVICE_ADD_ORDER_ENDS_MSG);
				return false;
			}
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Data transfer to the DAO to find for an order by the user's id who made the order
	 * @param userId user's id
	 * @return list of orders made by a specific user
	 * @throws ServiceException find for an order by the user's id
	 */
	@Override
	public List<Order> findOrdersByUserId(int userId) throws ServiceException {

		LOG.debug(ServiceConstant.SERVICE_FIND_ORSERS_BY_USER_STARTS_MSG);

		List<Order> orders = null;
//		int startPage = orderToStartPage(pageNumber, ordersOnPage);
		try {
			orders = ORDER_DAO.findOrdersByUserId(userId);
			LOG.debug(ServiceConstant.SERVICE_FIND_ORSERS_BY_USER_ENDS_MSG);
			return orders;
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Find order by order id
	 *
	 * @param orderId
	 * @return sought order
	 * @throws DAOException exception find order
	 */
	@Override
	public Order findOrderByOrderId(int orderId) throws ServiceException {
		LOG.debug(ServiceConstant.SERVICE_FIND_ORDER_BY_ID_STARTS_MSG);
		Order order = null;
		try {
			order = ORDER_DAO.findOrderByOrderId(orderId);
			LOG.debug(ServiceConstant.SERVICE_FIND_ORDER_BY_ID_ENDS_MSG);
			return order;
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Getting all orders
	 *
	 * @return list of all orders
	 * @throws DAOException exception getting orders list
	 */
	@Override
	public List<Order> takeAllOrders(int pageNumber, int ordersOnPage) throws ServiceException {
		LOG.debug(ServiceConstant.SERVICE_TAKE_ALL_ORDERS_STARTS_MSG);
		List<Order> orders = null;
		int startPage = orderToStartPage(pageNumber, ordersOnPage);
		try {
			orders = ORDER_DAO.takeAllOrders(startPage, ordersOnPage);
			LOG.debug(ServiceConstant.SERVICE_TAKE_ALL_ORDERS_ENDS_MSG);
			return orders;
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Find an order by order_id for administrator
	 *
	 * @param orderId order's id
	 * @return finding order
	 * @throws DAOException exception finding order
	 */
	@Override
	public Order takeAdminOrderByOrderId(int orderId) throws ServiceException {
		LOG.debug(ServiceConstant.SERVICE_TAKE_ADMIN_ORDER_BY_ID_STARTS_MSG);
		Order order = null;
		try {
			order = ORDER_DAO.takeAdminOrderByOrderId(orderId);
			LOG.debug(ServiceConstant.SERVICE_TAKE_ADMIN_ORDER_BY_ID_ENDS_MSG);
			return order;
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Changing the status of the order, without the reason for the change
	 *
	 * @param status  new status
	 * @param orderId id changing order
	 * @throws DAOException exception changing the status
	 */
	@Override
	public void updateStatusWithOutReason(String status, int orderId) throws ServiceException {
		LOG.debug(ServiceConstant.SERVICE_UPDATE_STATUS_BY_ID_STARTS_MSG);
		try {
			ORDER_DAO.updateStatusWithoutReason(status, orderId);
			LOG.debug(ServiceConstant.SERVICE_UPDATE_STATUS_BY_ID_ENDS_MSG);
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Changing the status of the order, indicating the reason for the change
	 *
	 * @param status    new status
	 * @param orderId   order id
	 * @param orderInfo reason of changing the status
	 * @throws DAOException exception changing the status
	 */
	@Override
	public void updateStatusById(String status, int orderId, String orderInfo) throws ServiceException {
		LOG.debug(ServiceConstant.SERVICE_UPDATE_STATUS_BY_ID_STARTS_MSG);
		try {
			ORDER_DAO.updateStatusWithReason(status, orderId, orderInfo);
			LOG.debug(ServiceConstant.SERVICE_UPDATE_STATUS_BY_ID_ENDS_MSG);
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Changing damage price
	 *
	 * @param orderId order's id
	 * @param damagePrice damage price
	 * @throws DAOException exception changing damage price
	 */
	@Override
	public void updateDamagePriceByOrderId(int orderId, double damagePrice) throws ServiceException {
		LOG.debug(ServiceConstant.SERVICE_UPDATE_DAMAGE_PRICE_BY_ID_STARTS_MSG);
		try {
			ORDER_DAO.updateDamagePriceByOrderId(orderId, damagePrice);
			LOG.debug(ServiceConstant.SERVICE_UPDATE_DAMAGE_PRICE_BY_ID_ENDS_MSG);
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Calculate the count of rent days by dates
	 * @param rentalStartDate start rent days
	 * @param rentalEndDate end rent days
	 * @return count of rent days
	 */
	private int countRentDays(Date rentalStartDate, Date rentalEndDate) {

		return (int) (rentalEndDate.getTime() - rentalStartDate.getTime()) / (24 * 60 * 60 * 1000);
	}

	/**
	 * Calculate the count of rent days by dates
	 * @param car selected car
	 * @param countRentDays count of rent days
	 * @param discountCoefficient coefficient of discount
	 * @return total bill of order
	 */
	private double calculateTotalBill(Car car, int countRentDays, double discountCoefficient) {
		return car.getPricePerDay() * countRentDays * discountCoefficient;
	}

	/**
	 * @param pageNumber number of page
	 * @param ordersOnPage count of order on page
	 * @return count of order to start page
	 */
	private int orderToStartPage(int pageNumber, int ordersOnPage) {
		LOG.debug(ServiceConstant.SERVICE_ORDER_TO_START_PAGE_STARTS_MSG);
		LOG.debug(ServiceConstant.SERVICE_ORDER_TO_START_PAGE_ENDS_MSG);
		return ((pageNumber * ordersOnPage) - ordersOnPage);
	}

	public static void main(String[] args) {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			connectionPool.initConnectionPool();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

		OrderService orderService = ServiceFactory.getInstance().getOrderService();

		try {
			System.out.println(orderService.addOrder(18, 5, format.parse("2019-05-13"),
					format.parse("2019-05-15")));
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}


}