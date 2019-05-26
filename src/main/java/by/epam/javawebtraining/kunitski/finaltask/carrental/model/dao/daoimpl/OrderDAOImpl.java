package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daoimpl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ConnectionPoolException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOStringConstant;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.OrderDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.Order;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.CarClassType;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderDAOImpl implements OrderDAO {

	private static final Logger LOG = LogManager.getLogger(OrderDAOImpl.class.getName());
	private static final String ORDER_STATUS_APPROVED = "approved";

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
			"WHERE o.user_id= ? ORDER BY o.order_id;";

	private static final String FIND_ORDER_BY_CAR_ID_QUERY = "SELECT o.order_id, u.user_id, o.rent_start_date, " +
			"o.rent_end_date, o.damage_price, o.status, o.total_bill " +
			"FROM car_rental.order AS o " +
			"INNER JOIN car_rental.user AS u ON u.user_id = o.user_id " +
			"WHERE o.car_id = ? ORDER BY o.order_id;";

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

	private static final String UPDATE_DISCOUNT_COEFFICIENT_QUERY = "UPDATE car_rental.discount_coefficient AS d " +
			"SET d.coefficient = ?  WHERE d.discount_coefficient_id = ?;";


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
				car.setImage(Base64.encode(rs.getBytes(14)));

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
				car.setImage(Base64.encode(rs.getBytes(6)));

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

	@Override
	public double takeDiscountCoefficient(int countRentDays) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_TAKE_DISCOUNT_COEFFICIENT_STARTS_MSG);

		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;
		ResultSet rs = null;
		double discountCoefficient = 0;

		try {

			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(TAKE_DISCOUNT_COEFFICIENT_QUERY);
			ps.setInt(1, countRentDays);
			ps.setInt(2, countRentDays);

			rs = ps.executeQuery();

			if (rs.next()) {
				discountCoefficient = rs.getDouble(1);
			}

			return discountCoefficient;

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
	}

	@Override
	public void updateDiscountCoefficient(int discountID, double value) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_TAKE_DISCOUNT_COEFFICIENT_STARTS_MSG);

		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;
		try {

			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(UPDATE_DISCOUNT_COEFFICIENT_QUERY);
			ps.setDouble(1, value);
			ps.setInt(2, discountID);
			ps.executeUpdate();

		} catch (ConnectionPoolException | SQLException ex) {
			throw new DAOException(DAOStringConstant.DAO_TAKE_DISCOUNT_COEFFICIENT_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps);
				}
				LOG.debug(DAOStringConstant.DAO_TAKE_DISCOUNT_COEFFICIENT_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_TAKE_DISCOUNT_COEFFICIENT_CLOSE_CON_ERROR_MSG, ex);
			}
		}
	}

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

	public static void main(String[] args) throws DAOException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			connectionPool.initConnectionPool();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

		OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();

			User user = new User();
			user.setUserID(1);
			Car car = new Car();
			car.setCarID(1);
			orderDAO.addOrder(new Order(0, user, car, "2019-05-25",
					"2019-05-28", 0, "undefined", " ", 250));
//			List<Integer> listUsedCarIDs = orderDAO.findUsedCarsID(new Order(7, user, car, format.parse("2019-05-02"),
//					format.parse("2019-05-08") , 0, "undefined", " ", 250));
//			System.out.println(listUsedCarIDs.toString());
//

//		List<Order> orderList = orderDAO.findOrdersByUserId(1);
//		System.out.println(orderList.toString());
//		List<Order> orderListByCarID = orderDAO.findOrdersByCarId(10);
//		System.out.println(orderListByCarID.toString());
//		List<Order> listAllOrders = orderDAO.takeAllOrders(10, 0);
//		System.out.println(listAllOrders.toString());
		System.out.println(orderDAO.takeAdminOrderByOrderId(2));
//		orderDAO.updateStatusWithReason("rejected", 2, "customer has such much accidents");
//		orderDAO.updateStatusWithoutReason("closed", 10);
//		orderDAO.updateDamagePriceByOrderId(1, 80);
//		System.out.println(orderDAO.countAllOrders());
//		System.out.println(orderDAO.findOrderByOrderId(2));
//		System.out.println(orderDAO.takeDiscountCoefficient(14));
//	orderDAO.updateDiscountCoefficient(3, 0.6);
	}
}

