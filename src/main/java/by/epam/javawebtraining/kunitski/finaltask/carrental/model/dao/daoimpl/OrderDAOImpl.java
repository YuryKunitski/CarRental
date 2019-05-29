package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daoimpl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ConnectionPoolException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOStringConstant;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.OrderDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.Order;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.CarClassType;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

	private static final Logger LOG = LogManager.getLogger(OrderDAOImpl.class.getName());
	private static final String ORDER_STATUS_APPROVED = "approved";
	private static final double MAX_DISCOUNT_COEFFICIENT = 0.5;
	private static final int COUNT_DAYS_FOR_MAX_DISCOUNT = 30;

	private static final String ADD_ORDER_QUERY = "INSERT INTO car_rental.order (user_id, car_id, rent_start_date," +
			" rent_end_date, damage_price, status, info, total_bill) VALUES (?,?,?,?,?,?,?,?);";

	private static final String TAKE_USED_CARS_ID_QUERY = "SELECT car_id FROM car_rental.order AS o " +
			"WHERE o.status= ? AND ((o.rent_start_date BETWEEN ? AND ?) OR (o.rent_end_date BETWEEN ? AND ?));";

	private static final String FIND_ORDER_BY_USER_ID_QUERY = "SELECT o.order_id, m.model, c.class," +
			" o.rent_start_date, o.rent_end_date, o.damage_price, o.status, o.total_bill " +
			"FROM car_rental.order AS o " +
			"INNER JOIN car ON car.car_id = o.car_id " +
			"INNER JOIN car_model AS m ON m.car_model_id = car.car_model_id " +
			"INNER JOIN car_class AS c ON c.car_class_id = car.car_class_id " +
			"WHERE o.user_id= ? ORDER BY o.order_id DESC;";

	private static final String FIND_ORDER_BY_CAR_ID_QUERY = "SELECT o.order_id, u.user_id, o.rent_start_date, " +
			"o.rent_end_date, o.damage_price, o.status, o.total_bill " +
			"FROM car_rental.order AS o " +
			"INNER JOIN car_rental.user AS u ON u.user_id = o.user_id " +
			"WHERE o.car_id = ? ORDER BY o.order_id DESC;";

	private static final String FIND_BY_ORDER_ID_QUERY = "SELECT o.order_id, cm.model, cc.class, car.year_issue, " +
			"car.price_per_day, car.image, o.rent_start_date, o.rent_end_date, o.damage_price, o.status, o.info, " +
			"o.total_bill FROM car_rental.order AS o " +
			"INNER JOIN car ON car.car_id= o.car_id " +
			"INNER JOIN car_model AS cm ON car.car_model_id = cm.car_model_id " +
			"INNER JOIN car_class AS cc ON car.car_class_id = cc.car_class_id " +
			"WHERE o.order_id=?;";

	private static final String TAKE_ALL_ORDERS_QUERY = "SELECT o.order_id, u.login, u.name, u.surname, cm.model," +
			" o.rent_start_date, o.rent_end_date, cc.class, o.status, o.damage_price, o.total_bill " +
			"FROM car_rental.order AS o " +
			"INNER JOIN car ON o.car_id= car.car_id " +
			"INNER JOIN car_rental.user AS u ON o.user_id = u.user_id " +
			"INNER JOIN car_model AS cm ON cm.car_model_id = car.car_model_id " +
			"INNER JOIN car_class AS cc ON cc.car_class_id = car.car_class_id " +
			"ORDER BY o.order_id DESC LIMIT ? OFFSET ?;";

	private static final String TAKE_ADMIN_ORDER_BY_ORDER_ID_QUERY = "SELECT o.order_id, u.user_id, u.login, u.name, u.surname, " +
			"u.phone, u.email, u.passport_id, car.car_id, cm.model, cc.class, car.year_issue, car.price_per_day, " +
			"car.image, o.rent_start_date, o.rent_end_date, o.damage_price, o.status, o.info, o.total_bill " +
			"FROM car_rental.order AS o " +
			"INNER JOIN car ON o.car_id= car.car_id " +
			"INNER JOIN car_rental.user AS u ON o.user_id= u.user_id " +
			"INNER JOIN car_model AS cm ON cm.car_model_id = car.car_model_id " +
			"INNER JOIN car_class AS cc ON cc.car_class_id= car.car_class_id " +
			"WHERE o.order_id = ?;";

	private static final String UPDATE_STATUS_INFO_BY_ORDER_ID = "UPDATE car_rental.order SET status=?, info=? " +
			"WHERE order_id=?;";

	private static final String UPDATE_STATUS_BY_ORDER_ID = "UPDATE car_rental.order SET status=? " +
			"WHERE order_id=?;";

	private static final String UPDATE_DAMAGE_PRICE_BY_ORDER_ID = "UPDATE car_rental.order SET damage_price=? " +
			"WHERE order_id=?;";

	private static final String COUNT_ALL_ORDERS = "SELECT COUNT(order_id) FROM car_rental.order;";

	private static final String TAKE_DISCOUNT_COEFFICIENT_QUERY = "SELECT d.coefficient " +
			"FROM car_rental.discount_coefficient AS d WHERE d.days_from <= ? AND d.days_to >= ?;";

	private static final String TAKE_ALL_DISCOUNT_COEFFICIENT_QUERY = "SELECT coefficient FROM discount_coefficient;";

	private static final String FIND_ORDER_STATUS_BY_CAR_ID_QUERY = "SELECT status FROM car_rental.order" +
			" WHERE car_id = ? ;";

	/**
	 * Add order into database
	 *
	 * @param order adding order
	 * @throws DAOException exception adding order into data base
	 */
	@Override
	public void addOrder(Order order) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_ADD_ORDER_STARTS_MSG);

		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;

		try {

			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(ADD_ORDER_QUERY);

			ps.setInt(1, order.getUser().getUserID());
			ps.setInt(2, order.getCar().getCarID());
			ps.setString(3, order.getRentalStartDate());
			ps.setString(4, order.getRentalEndDate());
			ps.setDouble(5, order.getDamagePrice());
			ps.setString(6, order.getStatus());
			ps.setString(7, order.getInfo());
			ps.setDouble(8, order.getTotalBill());

			ps.executeUpdate();
		} catch (ConnectionPoolException | SQLException ex) {
			throw new DAOException(DAOStringConstant.DAO_ADD_ORDER_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps);
				}
				LOG.debug(DAOStringConstant.DAO_ADD_ORDER_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_ADD_ORDER_CLOSE_CON_ERROR_MSG, ex);
			}
		}
	}

	/**
	 * Find cars id that are used for a certain period of time.
	 * @param order заказ
	 * @return список id автомобилей
	 * @throws DAOException ошибка при получении списка id автомобилей
	 */
	@Override
	public List<Integer> findUsedCarsID(Order order) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_FIND_USED_CARS_ID_STARTS_MSG);

		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Integer> carIds = new ArrayList<>();

		try {
			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(TAKE_USED_CARS_ID_QUERY);
			ps.setString(1, ORDER_STATUS_APPROVED);
			ps.setString(2, order.getRentalStartDate());
			ps.setString(3, order.getRentalEndDate());
			ps.setString(4, order.getRentalStartDate());
			ps.setString(5, order.getRentalEndDate());

			rs = ps.executeQuery();
			while (rs.next()) {
				carIds.add(rs.getInt(1));
			}

			return carIds;

		} catch (ConnectionPoolException | SQLException ex) {
			throw new DAOException(DAOStringConstant.DAO_FIND_USED_CARS_ID_ERROR_MSG, ex);

		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps, rs);
				}

				LOG.debug(DAOStringConstant.DAO_FIND_USED_CARS_ID_ENDS_MSG);

			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_FIND_USED_CARS_ID_CLOSE_CON_ERROR_MSG, ex);
			}
		}
	}

	/**
	 * Find orders by user id
	 *
	 * @param userId
	 * @return list of orders
	 * @throws DAOException exception getting orders list
	 */
	@Override
	public List<Order> findOrdersByUserId(int userId) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_FIND_ORDERS_BY_USER_ID_STARTS_MSG);

		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Order> orders = new ArrayList<>();

		try {

			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(FIND_ORDER_BY_USER_ID_QUERY);
			ps.setInt(1, userId);

			rs = ps.executeQuery();
			while (rs.next()) {
				Order order = new Order();
				Car car = new Car();

				order.setOrderID(rs.getInt(1));
				car.setCarModel(rs.getString(2));
				car.setCarClassType(CarClassType.valueOf(rs.getString(3).toUpperCase()));
				order.setRentalStartDate(rs.getString(4));
				order.setRentalEndDate(rs.getString(5));
				order.setDamagePrice(rs.getDouble(6));
				order.setStatus(rs.getString(7));
				order.setTotalBill(rs.getDouble(8));

				order.setCar(car);
				orders.add(order);
			}
			return orders;
		} catch (ConnectionPoolException | SQLException ex) {
			throw new DAOException(DAOStringConstant.DAO_FIND_ORDERS_BY_USER_ID_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps, rs);
				}
				LOG.debug(DAOStringConstant.DAO_FIND_ORDERS_BY_USER_ID_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_FIND_ORDERS_BY_USER_ID_CLOSE_CON_ERROR_MSG, ex);
			}
		}
	}

	/**
	 * Find orders by car id
	 *
	 * @param carId
	 * @return list of orders
	 * @throws DAOException exception getting orders list
	 */
	@Override
	public List<Order> findOrdersByCarId(int carId) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_FIND_ORDERS_BY_USER_ID_STARTS_MSG);

		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Order> orders = new ArrayList<>();


		try {

			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(FIND_ORDER_BY_CAR_ID_QUERY);
			ps.setInt(1, carId);

			rs = ps.executeQuery();
			while (rs.next()) {
				Order order = new Order();
				User user = new User();

				user.setUserID(rs.getInt(2));

				order.setOrderID(rs.getInt(1));
				order.setRentalStartDate(rs.getString(3));
				order.setRentalEndDate(rs.getString(4));
				order.setDamagePrice(rs.getDouble(5));
				order.setStatus(rs.getString(6));
				order.setTotalBill(rs.getDouble(7));

				order.setUser(user);
				orders.add(order);
			}
			return orders;
		} catch (ConnectionPoolException | SQLException ex) {
			throw new DAOException(DAOStringConstant.DAO_FIND_ORDERS_BY_USER_ID_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps, rs);
				}
				LOG.debug(DAOStringConstant.DAO_FIND_ORDERS_BY_USER_ID_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_FIND_ORDERS_BY_USER_ID_CLOSE_CON_ERROR_MSG, ex);
			}
		}
	}

	/**
	 * Getting all orders
	 *
	 * @return list of all orders
	 * @throws DAOException exception getting orders list
	 */
	@Override
	public List<Order> takeAllOrders(int toStartPage, int ordersOnPage) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_TAKE_ALL_ORDERS_STARTS_MSG);

		List<Order> orders = new ArrayList<>();
		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Statement st = null;
		ResultSet rs = null;

		try {
			connection = connectionPool.takeConnection();
			PreparedStatement ps = connection.prepareStatement(TAKE_ALL_ORDERS_QUERY);
			ps.setInt(1, ordersOnPage);
			ps.setInt(2, toStartPage);
			rs = ps.executeQuery();

			while (rs.next()) {
				Order order = new Order();
				Car car = new Car();
				User user = new User();
				order.setOrderID(rs.getInt(1));
				user.setLogin(rs.getString(2));
				user.setName(rs.getString(3));
				user.setSurName(rs.getString(4));
				car.setCarModel(rs.getString(5));

				order.setRentalStartDate(rs.getString(6));
				order.setRentalEndDate(rs.getString(7));

				car.setCarClassType(CarClassType.valueOf(rs.getString(8).toUpperCase()));

				order.setStatus(rs.getString(9));
				order.setDamagePrice(rs.getDouble(10));
				order.setTotalBill(rs.getDouble(11));

				order.setCar(car);
				order.setUser(user);
				orders.add(order);
			}
			return orders;
		} catch (ConnectionPoolException | SQLException ex) {
			throw new DAOException(DAOStringConstant.DAO_TAKE_ALL_ORDERS_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, st, rs);
				}
				LOG.debug(DAOStringConstant.DAO_TAKE_ALL_ORDERS_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_TAKE_ALL_ORDERS_CLOSE_CON_ERROR_MSG, ex);
			}
		}
	}

	/**
	 * Find an order by order_id for administrator
	 * @param orderId order's id
	 * @return finding order
	 * @throws DAOException exception finding order
	 */
	@Override
	public Order takeAdminOrderByOrderId(int orderId) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_TAKE_ADMIN_ORDER_BY_ORDER_ID_STARTS_MSG);

		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(TAKE_ADMIN_ORDER_BY_ORDER_ID_QUERY);
			ps.setInt(1, orderId);

			rs = ps.executeQuery();
			Order order = new Order();
			Car car = new Car();
			User user = new User(); //u2 - car9
			if (rs.next()) {
				order.setOrderID(rs.getInt(1));

				user.setUserID(rs.getInt(2));
				user.setLogin(rs.getString(3));
				user.setName(rs.getString(4));
				user.setSurName(rs.getString(5));
				user.setPhone(rs.getString(6));
				user.setEmail(rs.getString(7));
				user.setPassportID(rs.getString(8));

				car.setCarID(rs.getInt(9));
				car.setCarModel(rs.getString(10));
				car.setCarClassType(CarClassType.valueOf(rs.getString(11).toUpperCase()));
				car.setYearIssue(rs.getString(12));
				car.setPricePerDay(rs.getDouble(13));
				car.setImage(rs.getString(14));

				order.setRentalStartDate(rs.getString(15));
				order.setRentalEndDate(rs.getString(16));
				order.setDamagePrice(rs.getDouble(17));
				order.setStatus(rs.getString(18));
				order.setInfo(rs.getString(19));
				order.setTotalBill(rs.getDouble(20));

				order.setCar(car);
				order.setUser(user);
			}
			return order;
		} catch (ConnectionPoolException | SQLException ex) {
			throw new DAOException(DAOStringConstant.DAO_TAKE_ADMIN_ORDER_BY_ORDER_ID_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps, rs);
				}
				LOG.debug(DAOStringConstant.DAO_TAKE_ADMIN_ORDER_BY_ORDER_ID_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_TAKE_ADMIN_ORDER_BY_ORDER_ID_CLOSE_CON_ERROR_MSG, ex);
			}
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
	public Order findOrderByOrderId(int orderId) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_FIND_ORDER_BY_ID_STARTS_MSG);

		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(FIND_BY_ORDER_ID_QUERY);
			ps.setInt(1, orderId);
			rs = ps.executeQuery();
			Order order = new Order();
			if (rs.next()) {
				Car car = new Car();

				order.setOrderID(rs.getInt(1));

				car.setCarModel(rs.getString(2));
				car.setCarClassType(CarClassType.valueOf(rs.getString(3).toUpperCase()));
				car.setYearIssue(rs.getString(4));
				car.setPricePerDay(rs.getDouble(5));
				car.setImage(rs.getString(6));

				order.setRentalStartDate(rs.getString(7));
				order.setRentalEndDate(rs.getString(8));
				order.setDamagePrice(rs.getDouble(9));
				order.setStatus(rs.getString(10));
				order.setInfo(rs.getString(11));
				order.setTotalBill(rs.getDouble(12));

				order.setCar(car);
			}
			return order;
		} catch (ConnectionPoolException | SQLException ex) {
			throw new DAOException(DAOStringConstant.DAO_FIND_ORDER_BY_ID_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps, rs);
				}
				LOG.debug(DAOStringConstant.DAO_FIND_ORDER_BY_ID_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_FIND_ORDER_BY_ID_CLOE_CON_ERROR_MSG, ex);
			}
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
	public void updateStatusWithReason(String status, int orderId, String orderInfo) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_UPDATE_STATUS_INFO_STARTS_MSG);

		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;
		try {

			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(UPDATE_STATUS_INFO_BY_ORDER_ID);
			ps.setString(1, status);
			ps.setString(2, orderInfo);
			ps.setInt(3, orderId);
			ps.executeUpdate();
		} catch (ConnectionPoolException | SQLException ex) {
			throw new DAOException(DAOStringConstant.DAO_UPDATE_STATUS_INFO_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps);
				}
				LOG.debug(DAOStringConstant.DAO_UPDATE_STATUS_INFO_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_UPDATE_STATUS_INFO_CLOSE_CON_ERROR_MSG, ex);
			}
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
	public void updateStatusWithoutReason(String status, int orderId) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_UPDATE_STATUS_INFO_STARTS_MSG);

		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;
		try {

			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(UPDATE_STATUS_BY_ORDER_ID);
			ps.setString(1, status);
			ps.setInt(2, orderId);
			ps.executeUpdate();
		} catch (ConnectionPoolException | SQLException ex) {
			throw new DAOException(DAOStringConstant.DAO_UPDATE_STATUS_INFO_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps);
				}
				LOG.debug(DAOStringConstant.DAO_UPDATE_STATUS_INFO_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_UPDATE_STATUS_INFO_CLOSE_CON_ERROR_MSG, ex);
			}
		}
	}

	/**
	 * Changing damage price
	 * @param orderId order's id
	 * @param damagePrice damage price
	 * @throws DAOException exception changing damage price
	 */
	@Override
	public void updateDamagePriceByOrderId(int orderId, double damagePrice) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_UPDATE_DAMAGE_PRICE_STARTS_MSG);

		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;
		try {

			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(UPDATE_DAMAGE_PRICE_BY_ORDER_ID);
			ps.setDouble(1, damagePrice);
			ps.setInt(2, orderId);
			ps.executeUpdate();
		} catch (ConnectionPoolException | SQLException ex) {
			throw new DAOException(DAOStringConstant.DAO_UPDATE_DAMAGE_PRICE_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps);
				}
				LOG.debug(DAOStringConstant.DAO_UPDATE_DAMAGE_PRICE_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_UPDATE_DAMAGE_PRICE_CLOSE_CON_ERROR_MSG, ex);
			}
		}
	}

	/**
	 * Taking discount coefficient
	 * @param countRentDays count rent days
	 * @throws DAOException taking discount coefficient
	 */
	@Override
	public double takeDiscountCoefficient(int countRentDays) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_TAKE_DISCOUNT_COEFFICIENT_STARTS_MSG);

		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;
		ResultSet rs = null;
		double discountCoefficient = 0;

		if (countRentDays <= COUNT_DAYS_FOR_MAX_DISCOUNT) {
			try {
				connection = connectionPool.takeConnection();
				ps = connection.prepareStatement(TAKE_DISCOUNT_COEFFICIENT_QUERY);
				ps.setInt(1, countRentDays);
				ps.setInt(2, countRentDays);

				rs = ps.executeQuery();

				if (rs.next()) {
					discountCoefficient = rs.getDouble(1);
				}

			} catch (ConnectionPoolException | SQLException ex) {
				throw new DAOException(DAOStringConstant.DAO_TAKE_DISCOUNT_COEFFICIENT_ERROR_MSG, ex);

			} finally {
				try {
					if (connectionPool != null) {
						connectionPool.closeConnection(connection, ps, rs);
					}

					LOG.debug(DAOStringConstant.DAO_TAKE_DISCOUNT_COEFFICIENT_ENDS_MSG);

				} catch (ConnectionPoolException ex) {
					throw new DAOException(DAOStringConstant.DAO_TAKE_DISCOUNT_COEFFICIENT_CLOSE_CON_ERROR_MSG, ex);
				}
			}
		} else {
			discountCoefficient = MAX_DISCOUNT_COEFFICIENT;
		}
		return discountCoefficient;
	}

	/**
	 * Taking all discount coefficients
	 * @return list of all discount coefficient
	 * @throws DAOException changing discount coefficient
	 */
	@Override
	public List<Double> takeAllDiscountCoefficients () throws DAOException {

		LOG.debug(DAOStringConstant.DAO_TAKE_ALL_DISCOUNT_COEFFICIENT_STARTS_MSG);

		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Double> allDiscountCoefficients = new ArrayList<>();

		try {

			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(TAKE_ALL_DISCOUNT_COEFFICIENT_QUERY);
			rs = ps.executeQuery();

			while (rs.next()){
				allDiscountCoefficients.add(rs.getDouble(1));
			}

			return allDiscountCoefficients;

		} catch (ConnectionPoolException | SQLException ex) {
			throw new DAOException(DAOStringConstant.DAO_TAKE_ALL_DISCOUNT_COEFFICIENT_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps);
				}
				LOG.debug(DAOStringConstant.DAO_TAKE_ALL_DISCOUNT_COEFFICIENT_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_TAKE_ALL_DISCOUNT_COEFFICIENT_CLOSE_CON_ERROR, ex);
			}
		}
	}

	/**
	 * Take all count of orders
	 *
	 * @throws DAOException exception take all count of orders
	 */
	@Override
	public int countAllOrders() throws DAOException {

		LOG.debug(DAOStringConstant.DAO_COUNT_ALL_ORDERS_STARTS_MSG);

		int ordersAmount = 0;
		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Statement st = null;
		ResultSet rs = null;
		try {

			connection = connectionPool.takeConnection();
			st = connection.createStatement();
			rs = st.executeQuery(COUNT_ALL_ORDERS);
			while (rs.next()) {
				ordersAmount = rs.getInt(1);
			}
			return ordersAmount;
		} catch (SQLException | ConnectionPoolException ex) {
			throw new DAOException(DAOStringConstant.DAO_COUNT_ALL_ORDERS_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, st, rs);
				}
				LOG.debug(DAOStringConstant.DAO_COUNT_ALL_ORDERS_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_COUNT_ALL_ORDERS_CLOSE_CON_ERROR_MSG, ex);
			}
		}
	}

	/**
	 * Find Status By Car Id
	 *
	 * @throws DAOException exception take all count of orders
	 */
	@Override
	public List<String> findStatusByCarId(int carID) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_FIND_STATUS_BY_CAR_ID_STARTS_MSG);

		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(FIND_ORDER_STATUS_BY_CAR_ID_QUERY);
			ps.setInt(1, carID);
			rs = ps.executeQuery();
			List<String> orderStatuses = new ArrayList<>();

			while (rs.next()) {
				orderStatuses.add(rs.getString(1));
			}

			LOG.debug(DAOStringConstant.DAO_FIND_STATUS_BY_CAR_ID_ENDS_MSG);

			return orderStatuses;

		} catch (SQLException | ConnectionPoolException ex) {
			throw new DAOException(DAOStringConstant.DAO_FIND_STATUS_BY_CAR_ID_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps, rs);
				}
				LOG.debug(DAOStringConstant.DAO_COUNT_ALL_ORDERS_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_FIND_STATUS_BY_CAR_ID_CLOSE_CON_ERROR, ex);
			}
		}
	}
}

