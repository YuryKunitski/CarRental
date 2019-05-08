package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daoimpl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ConnectionPoolException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOStringConstant;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.CarDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.CarClassType;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CarDAOImpl implements CarDAO {

	private static final Logger LOG = LogManager.getLogger(CarDAOImpl.class);
	private static final String ORDER_STATUS_APPROVED = "approved";

	private static final String INSERT_CAR_QUERY = "INSERT INTO car (car_model_id, car_class_id, year_issue," +
			" price_per_day, image) VALUES ((SELECT car_model.car_model_id FROM car_model WHERE model = ?),?,?,?,?);";

	private static final String TAKE_CAR_BY_ID_QUERY = "SELECT car.car_id, car_model.model, car_class.class, car.year_issue," +
			" car.price_per_day, car.image FROM car_rental.car " +
			"INNER JOIN car_model ON car_model.car_model_id = car.car_model_id " +
			"INNER JOIN car_class ON car_class.car_class_id = car.car_class_id WHERE car_id=?;";

	private static final String TAKE_ALL_CARS_QUERY = "SELECT car.car_id, car_model.model, car_class.class," +
			" car.year_issue, car.price_per_day, car.image FROM car_rental.car " +
			"INNER JOIN car_model ON car_model.car_model_id = car.car_model_id " +
			"INNER JOIN car_class ON car_class.car_class_id = car.car_class_id ORDER BY car_id DESC LIMIT ? OFFSET ?;";

	private static final String DELETE_CAR_BY_ID = "DELETE FROM car WHERE car_id=?;";

	private static final String TAKE_UNUSED_CARS_QUERY = "SELECT car.car_id, car_model.model, car_class.class, " +
			"car.year_issue, car.price_per_day, car.image FROM car_rental.car " +
			"INNER JOIN car_model ON car_model.car_model_id = car.car_model_id " +
			"INNER JOIN car_class ON car_class.car_class_id = car.car_class_id " +
			"WHERE car_id NOT IN (SELECT car_id FROM car_rental.order AS o " +
			"WHERE( o.status = ? AND (o.rent_start_date BETWEEN ? AND ?) OR (o.rent_end_date BETWEEN ? AND ?))) " +
			"ORDER BY car_id DESC LIMIT ? OFFSET ?;";

	private static final String TAKE_UNUSED_CARS_BY_CLASS_QUERY = "SELECT car.car_id, car_model.model, car_class.class, " +
			"car.year_issue, car.price_per_day, car.image FROM car_rental.car " +
			"INNER JOIN car_model ON car_model.car_model_id = car.car_model_id " +
			"INNER JOIN car_class ON car_class.car_class_id = car.car_class_id " +
			"WHERE car_id NOT IN (SELECT car_id FROM car_rental.order AS o " +
			"WHERE( o.status = ? AND (o.rent_start_date BETWEEN ? AND ?) OR (o.rent_end_date BETWEEN ? AND ?))) " +
			"AND car_class.class = ? ORDER BY car_id DESC LIMIT ? OFFSET ?;";

	private static final String TAKE_CARS_BY_CLASS_QUERY = "SELECT car.car_id, car_model.model, car_class.class," +
			" car.year_issue, car.price_per_day, car.image FROM car_rental.car " +
			"INNER JOIN car_model ON car_model.car_model_id = car.car_model_id " +
			"INNER JOIN car_class ON car_class.car_class_id = car.car_class_id " +
			"WHERE car_class.class = ? ORDER BY car_id DESC LIMIT ? OFFSET ?;";

	private static final String COUNT_ALL_CARS_QUERY = "SELECT COUNT(car_id) FROM car;";


	@Override
	public void insertCar(Car car) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_INSERT_CAR_STARTS_MSG);
		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;
		try {
			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(INSERT_CAR_QUERY);
			ps.setString(1, car.getCarModel());
			ps.setInt(2, car.getCarClassType().getOrdinal());
			ps.setString(3, car.getYearIssue());
			ps.setDouble(4, car.getPricePerDay());
			ps.setBlob(5, new ByteArrayInputStream(Base64.decode(car.getImage())));
			ps.executeUpdate();
		} catch (Base64DecodingException | SQLException | ConnectionPoolException ex) {
			throw new DAOException(DAOStringConstant.DAO_INSERT_CAR_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps);
				}
				LOG.debug(DAOStringConstant.DAO_INSERT_CAR_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_INSERT_CAR_CLOSE_CON_ERROR_MSG, ex);
			}
		}
	}

	@Override
	public Car takeCarById(int id) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_TAKE_CAR_BY_ID_STARTS_MSG);

		Car car = new Car();
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(TAKE_CAR_BY_ID_QUERY);
			ps.setInt(1, id);
			rs = ps.executeQuery();

			while (rs.next()) {
				car.setCarID(rs.getInt(1));
				car.setCarModel(rs.getString(2));
				car.setCarClassType(CarClassType.valueOf(rs.getString(3).toUpperCase()));
				car.setYearIssue(rs.getString(4));
				car.setPricePerDay(rs.getDouble(5));
				car.setImage(Base64.encode(rs.getBytes(6)));

			}
			return car;
		} catch (SQLException | ConnectionPoolException ex) {
			throw new DAOException(DAOStringConstant.DAO_TAKE_CAR_BY_ID_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps, rs);
				}
				LOG.debug(DAOStringConstant.DAO_TAKE_CAR_BY_ID_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_TAKE_CAR_BY_ID_CLOSE_CON_ERROR_MSG, ex);
			}
		}

	}

	@Override
	public List<Car> takeAllCars(int toStartPage, int carsOnPage) throws DAOException {
		LOG.debug(DAOStringConstant.DAO_TAKE_ALL_CARS_STARTS_MSG);
		Connection connection = null;
		ConnectionPool connectionPooldb = ConnectionPool.getInstance();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			connection = connectionPooldb.takeConnection();
			ps = connection.prepareStatement(TAKE_ALL_CARS_QUERY);
			ps.setInt(1, toStartPage);
			ps.setInt(2, carsOnPage);
			rs = ps.executeQuery();
			List<Car> cars = new ArrayList<>();
			while (rs.next()) {
				Car car = new Car();
				car.setCarID(rs.getInt(1));
				car.setCarModel(rs.getString(2));
				car.setCarClassType(CarClassType.valueOf(rs.getString(3).toUpperCase()));
				car.setYearIssue(rs.getString(4));
				car.setPricePerDay(rs.getDouble(5));
				car.setImage(Base64.encode(rs.getBytes(6)));
				cars.add(car);
			}
			return cars;

		} catch (ConnectionPoolException | SQLException ex) {
			throw new DAOException(DAOStringConstant.DAO_TAKE_ALL_CARS_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPooldb != null) {
					connectionPooldb.closeConnection(connection, ps, rs);
				}
				LOG.debug(DAOStringConstant.DAO_TAKE_ALL_CARS_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_TAKE_ALL_CARS_CLOSE_CON_ERROR_MSG, ex);
			}
		}
	}

	@Override
	public void deleteCarById(int carId) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_DELETE_CAR_BY_ID_STARTS_MSG);

		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;

		try {
			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(DELETE_CAR_BY_ID);
			ps.setInt(1, carId);
			ps.executeUpdate();
		} catch (SQLException | ConnectionPoolException ex) {
			throw new DAOException(DAOStringConstant.DAO_DELETE_CAR_BY_ID_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps);
				}
				LOG.debug(DAOStringConstant.DAO_DELETE_CAR_BY_ID_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_DELETE_CAR_BY_ID_CLOSE_CON_ERROR_MSG, ex);
			}
		}
	}

	@Override
	public List<Car> takeUnusedCars(Date supposedDateFrom, Date supposedDateTo, int startPage, int carsOnPage)
			throws DAOException {

		LOG.debug(DAOStringConstant.DAO_TAKE_UNUSED_CARS_STARTS_MSG);

		List<Car> cars = new ArrayList<>();
		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {

			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(TAKE_UNUSED_CARS_QUERY);

			ps.setString(1, ORDER_STATUS_APPROVED);
			ps.setDate(2, supposedDateFrom);
			ps.setDate(3, supposedDateTo);
			ps.setDate(4, supposedDateFrom);
			ps.setDate(5, supposedDateTo);
			ps.setInt(6, startPage);
			ps.setInt(7, carsOnPage);
			rs = ps.executeQuery();

			while (rs.next()) {
				Car car = new Car();
				car.setCarID(rs.getInt(1));
				car.setCarModel(rs.getString(2));
				car.setCarClassType(CarClassType.valueOf(rs.getString(3).toUpperCase()));
				car.setYearIssue(rs.getString(4));
				car.setPricePerDay(rs.getDouble(5));
				car.setImage(Base64.encode(rs.getBytes(6)));

				cars.add(car);
			}
			return cars;
		} catch (ConnectionPoolException | SQLException ex) {
			throw new DAOException(DAOStringConstant.DAO_TAKE_UNUSED_CARS_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps, rs);
				}
				LOG.debug(DAOStringConstant.DAO_TAKE_UNUSED_CARS_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_TAKE_UNUSED_CARS_CLOSE_CON_ERROR_MSG, ex);
			}
		}
	}

	@Override
	public List<Car> takeUnUsedCarsByClass(Date supposedDateFrom, Date supposedDateTo, CarClassType carClassType,
	                                       int startPage, int carsOnPage) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_TAKE_UNUSED_CARS_BY_CLASS_STARTS_MSG);

		List<Car> cars = new ArrayList<>();
		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {

			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(TAKE_UNUSED_CARS_BY_CLASS_QUERY);

			ps.setString(1, ORDER_STATUS_APPROVED);
			ps.setDate(2, supposedDateFrom);
			ps.setDate(3, supposedDateTo);
			ps.setDate(4, supposedDateFrom);
			ps.setDate(5, supposedDateTo);
			ps.setString(6, carClassType.toString());
			ps.setInt(7, startPage);
			ps.setInt(8, carsOnPage);
			rs = ps.executeQuery();

			while (rs.next()) {
				Car car = new Car();
				car.setCarID(rs.getInt(1));
				car.setCarModel(rs.getString(2));
				car.setCarClassType(CarClassType.valueOf(rs.getString(3).toUpperCase()));
				car.setYearIssue(rs.getString(4));
				car.setPricePerDay(rs.getDouble(5));
				car.setImage(Base64.encode(rs.getBytes(6)));

				cars.add(car);
			}
			return cars;
		} catch (ConnectionPoolException | SQLException ex) {
			throw new DAOException(DAOStringConstant.DAO_TAKE_UNUSED_CARS_BY_CLASS_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps, rs);
				}
				LOG.debug(DAOStringConstant.DAO_TAKE_UNUSED_CARS_BY_CLASS_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_TAKE_UNUSED_CARS_BY_CLASS_CLOSE_CON_ERROR_MSG, ex);
			}
		}
	}

	@Override
	public List<Car> takeCarsByClass(CarClassType carClassType, int toStartPage, int carsOnPage) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_TAKE_CARS_BY_CLASS_STARTS_MSG);

		List<Car> cars = new ArrayList<>();
		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(TAKE_CARS_BY_CLASS_QUERY);
			ps.setString(1, carClassType.toString());
			ps.setInt(2, toStartPage);
			ps.setInt(3, carsOnPage);
			rs = ps.executeQuery();

			while (rs.next()) {
				Car car = new Car();
				car.setCarID(rs.getInt(1));
				car.setCarModel(rs.getString(2));
				car.setCarClassType(CarClassType.valueOf(rs.getString(3).toUpperCase()));
				car.setYearIssue(rs.getString(4));
				car.setPricePerDay(rs.getDouble(5));
				car.setImage(Base64.encode(rs.getBytes(6)));

				cars.add(car);
			}
			return cars;
		} catch (ConnectionPoolException | SQLException ex) {
			throw new DAOException(DAOStringConstant.DAO_TAKE_CARS_BY_CLASS_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps, rs);
				}
				LOG.debug(DAOStringConstant.DAO_TAKE_CARS_BY_CLASS_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_TAKE_CARS_BY_CLASS_CLOSE_CON_ERROR_MSG, ex);
			}
		}
	}

	@Override
	public int countAllCars() throws DAOException {

		LOG.debug(DAOStringConstant.DAO_COUNT_ALL_CARS_STARTS_MSG);

		int carsAmount = 0;
		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Statement st = null;
		ResultSet rs = null;

		try {

			connection = connectionPool.takeConnection();
			st = connection.createStatement();
			rs = st.executeQuery(COUNT_ALL_CARS_QUERY);
			while (rs.next()) {
				carsAmount = rs.getInt(1);
			}
			return carsAmount;

		} catch (SQLException | ConnectionPoolException ex) {
			throw new DAOException(DAOStringConstant.DAO_COUNT_ALL_CARS_ERROR_MSG, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, st, rs);
				}
				LOG.debug(DAOStringConstant.DAO_COUNT_ALL_CARS_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_COUNT_ALL_CARS_CLOSE_CON_ERROR_MSG, ex);
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

		CarDAO carDAO = DAOFactory.getInstance().getCarDAO();
//		carDAO.insertCar(new Car(21,"ford_focus",CarClassType.ECONOM,"2008", 75.5, ""));
		System.out.println(carDAO.takeCarById(15));
		List<Car> carList = carDAO.takeAllCars(20, 0);
		System.out.println("All cars - " + carList.toString());
//		carDAO.deleteCarById(41);
		List<Car> unusedCars = null;
		try {
			unusedCars = carDAO.takeUnUsedCarsByClass(new  java.sql.Date(format.parse( "2019-06-10").getTime()),
		new java.sql.Date(format.parse( "2019-06-16").getTime()), CarClassType.ECONOM,20,0);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("Unused cars by econom class - " + unusedCars.toString());
//		List<Car> listCarByClass = carDAO.takeCarsByClass(CarClassType.BUSINESS, 20, 0);
//		System.out.println(listCarByClass.toString());
//		System.out.println(carDAO.takeCarById(5));
	}
}
