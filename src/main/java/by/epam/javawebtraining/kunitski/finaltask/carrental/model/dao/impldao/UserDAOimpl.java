package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.impldao;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ConnectionPoolException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOStringConstant;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.interfacedao.UserDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.ValidatorUniqueUser;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOimpl implements UserDAO {

	private static final String SQL_ADD_USER = "INSERT INTO car_rental.user (`login`, `password`, `name`, `surname`, `phone`, `email`, `passport_id`) VALUES (?,?,?,?,?,?,?);";
	private static final String SQL_FIND_USER = "SELECT user_id, login, password, name, surname, phone, email, passport_id FROM user WHERE user.login=? AND user.password=?;";
	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT login FROM user WHERE login=?;";
	private static final String SQL_FIND_USER_BY_PASSPORT = "SELECT login FROM user WHERE passport=?;";
	private static final String SQL_FIND_USER_BY_EMAIL = "SELECT login FROM user WHERE email=?";
//	private static final String TAKE_ALL_USERS_QUERY =  "SELECT userID, login, type, lastName, firstName, email, phone FROM users GROUP BY (userID) DESC LIMIT ?,?;";
//	private static final String COUNT_ALL_USERS_QUERY = "SELECT COUNT(user_id) FROM user";


//	private static final String SQL_NAME = "name";
//	private static final String SQL_SURNAME = "surname";
//	private static final String SQL_PHONE = "phone";
//	private static final String SQL_EMAIL = "email";
//	private static final String SQL_PASSPORT_ID = "passport_id";
//	private static final String SQL_USER_ID = "user_id";

	private static final Logger LOG = LogManager.getLogger(UserDAOimpl.class.getName());

	@Override
	public void registration(User user) throws DAOException {
		LOG.debug(DAOStringConstant.DAO_REGISTRATION_STARTS_MSG);

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement ps = null;

		try {

			connection = connectionPool.takeConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(SQL_ADD_USER);

			ps.setString(1, user.getLogin());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getSurName());
			ps.setString(5, user.getPhone());
			ps.setString(6, user.getEmail());
			ps.setString(7, user.getPassportID());
			ps.executeUpdate();
			connection.commit();

		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(DAOStringConstant.DAO_REGISTRATION_ERROR_MSG, e);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps);
				}
			} catch (ConnectionPoolException e) {
				throw new DAOException(DAOStringConstant.DAO_REGISTRATION_CLOSE_CON_ERROR_MSG, e);
			}
		}
	}

	@Override
	public User authorization(String login, String password) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_AUTORIZATION_STARTS_MSG);

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;

		try {
			connectionPool = ConnectionPool.getInstance();
			connection = connectionPool.takeConnection();

			ps = connection.prepareStatement(SQL_FIND_USER);
			ps.setString(1, login);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUserID(rs.getInt(1));
				user.setLogin(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setName(rs.getString(4));
				user.setSurName(rs.getString(5));
				user.setPhone(rs.getString(6));
				user.setEmail(rs.getString(7));
				user.setPassportID(rs.getString(8));
			}

		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(DAOStringConstant.DAO_AUTHORIZATION_ERROR_MSG, e);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps, rs);
				}
				LOG.debug(DAOStringConstant.DAO_AUTORIZATION_ENDS_MSG);
			} catch (ConnectionPoolException e) {
				throw new DAOException(DAOStringConstant.DAO_AUTHORIZATION_CLOSE_CON_ERROR_MSG, e);
			}
		}
		return user;
	}

	@Override
	public ValidatorUniqueUser findUser(String login, String email, String passport) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_FIND_USER_STARTS_MSG);

		ConnectionPool connectionPooldb = ConnectionPool.getInstance();
		ValidatorUniqueUser validatorUniqueUser = new ValidatorUniqueUser();
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {

			connection = connectionPooldb.takeConnection();
			ps = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
			ps.setString(1, login);
			rs = ps.executeQuery();

			if (rs.next()) {
				validatorUniqueUser.setUniqueLogin(false);
			} else {
				validatorUniqueUser.setUniqueLogin(true);
			}

			ps = connection.prepareStatement(SQL_FIND_USER_BY_PASSPORT);
			ps.setString(1, passport);
			rs = ps.executeQuery();

			if (rs.next()) {
				validatorUniqueUser.setUniqueEmail(false);
			} else {
				validatorUniqueUser.setUniqueEmail(true);
			}

			ps = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL);
			ps.setString(1, email);
			rs = ps.executeQuery();

			if (rs.next()) {
				validatorUniqueUser.setUniquePassportID(false);
			} else {
				validatorUniqueUser.setUniquePassportID(true);
			}

		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(DAOStringConstant.DAO_FIND_USER_ERROR_MSG, e);

		} finally {
			try {
				if (connectionPooldb != null) {
					connectionPooldb.closeConnection(connection, ps, rs);
				}
				LOG.debug(DAOStringConstant.DAO_FIND_USER_ENDS_MSG);

			} catch (ConnectionPoolException e) {
				throw new DAOException(DAOStringConstant.DAO_FIND_USER_CLOSE_CON_ERROR_MSG, e);
			}
		}
		return validatorUniqueUser;
	}

//
//	private Customer getCustomer(ResultSet rs) throws SQLException {
//		Customer customer = new Customer();
//
//		if (!rs.next()) {
//			return null;
//		}
//
//		customer.setName(rs.getString(SQL_NAME));
//		customer.setSurName(rs.getString(SQL_SURNAME));
//		customer.setPhone(rs.getString(SQL_PHONE));
//		customer.setEmail(rs.getString(SQL_EMAIL));
//		customer.setPassportID(rs.getString(SQL_PASSPORT_ID));
//		customer.setDriveExperience(rs.getInt(SQL_DRIVE_EXPERIENCE));
//		customer.setUserID(rs.getInt(SQL_USER_ID));
//
//		return customer;
//	}


	@Override
	public List<User> takeAllUsers(int startPage, int amountUsersOnPage) throws DAOException {
		return null;
	}

	@Override
	public boolean removeUserByID(int idUser) throws DAOException {
		return false;
	}

	public static void main(String[] args) throws DAOException {

		UserDAO userDAO = new UserDAOimpl();
		System.out.println(userDAO.authorization("trol", "1"));
	}


}