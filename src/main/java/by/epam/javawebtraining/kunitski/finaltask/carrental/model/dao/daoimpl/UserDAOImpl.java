package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daoimpl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ConnectionPoolException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOStringConstant;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.UserDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.ValidatorUniqueUser;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.RoleType;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

	private static final Logger LOG = LogManager.getLogger(UserDAOImpl.class.getName());

	private static final String SQL_ADD_USER = "INSERT INTO car_rental.user (login, password, role_id, name, surname," +
			" phone, email, passport_id) VALUES (?,?,?,?,?,?,?,?);";

	private static final String SQL_FIND_USER = "SELECT user_id, login, password, role_id, name, surname, phone, email," +
			" passport_id FROM user WHERE user.login=? AND user.password=?;";

	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT login FROM user WHERE login=?;";

	private static final String SQL_FIND_USER_BY_PASSPORT = "SELECT login FROM user WHERE passport_id=?;";

	private static final String SQL_FIND_USER_BY_EMAIL = "SELECT login FROM user WHERE email=?;";

	private static final String SQL_REMOVE_USER_BY_ID = "DELETE FROM user WHERE user_id = ?;";

	private final static String SQL_UPDATE_USER_BY_ID = "UPDATE car_rental.user set login = ?, password = ?, name = ?," +
			" surname= ?, phone= ?, email= ?, passport_id=?  WHERE user_id = ?;";

	private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM user WHERE user_id=?;";

	private static final String TAKE_ALL_USERS_QUERY = "SELECT user_id, login, role_id, surname, name, email, phone" +
			" FROM user ORDER BY (user_id) DESC LIMIT ? OFFSET ?;";

	private static final String COUNT_ALL_USERS_QUERY = "SELECT COUNT(user_id) FROM car_rental.user;";

	/**
	 * Register user.
	 * @param user
	 * @throws DAOException
	 */
	@Override
	public void register(User user) throws DAOException {
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
			ps.setInt(3, user.getRoleType().getOrdinal());
			ps.setString(4, user.getName());
			ps.setString(5, user.getSurName());
			ps.setString(6, user.getPhone());
			ps.setString(7, user.getEmail());
			ps.setString(8, user.getPassportID());
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
		LOG.debug(DAOStringConstant.DAO_REGISTARATION_ENDS_MSG);
	}

	/**
	 * Authorize user.
	 *
	 * @param login
	 * @param password
	 * @return
	 * @throws DAOException
	 */
	@Override
	public User authorize(String login, String password) throws DAOException {

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
				user.setRoleType(RoleType.getValue(rs.getInt(4)));
				user.setName(rs.getString(5));
				user.setSurName(rs.getString(6));
				user.setPhone(rs.getString(7));
				user.setEmail(rs.getString(8));
				user.setPassportID(rs.getString(9));
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

	/**
	 * Find user by ID
	 *
	 * @param userID user's id
	 * @return found user
	 * @throws DAOException error find user by id
	 */
	@Override
	public User findUserById(int userID) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_FIND_USER_BY_ID_STARTS_MSG);

		Connection connection = null;
		ConnectionPool connectionPooldb = ConnectionPool.getInstance();
		ResultSet rs = null;
		PreparedStatement ps = null;
		User user = null;

		try {

			connection = connectionPooldb.takeConnection();
			ps = connection.prepareStatement(FIND_USER_BY_ID_QUERY);
			ps.setInt(1, userID);
			rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setUserID(rs.getInt(1));
				user.setLogin(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setRoleType(RoleType.getValue(rs.getInt(4)));
				user.setName(rs.getString(5));
				user.setSurName(rs.getString(6));
				user.setPhone(rs.getString(7));
				user.setEmail(rs.getString(8));
				user.setPassportID(rs.getString(9));
			}

			return user;

		} catch (SQLException | ConnectionPoolException ex) {
			throw new DAOException(DAOStringConstant.DAO_FIND_USER_BY_ID_ERROR, ex);
		} finally {
			try {
				if (connectionPooldb != null) {
					connectionPooldb.closeConnection(connection, ps, rs);
				}
				LOG.debug(DAOStringConstant.DAO_FIND_USER_BY_ID_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_FIND_USER_BY_ID_CLOSE_CON_ERROR, ex);
			}
		}
	}

	/**
	 * Check for similar login, e-mail, password in the database
	 *
	 * @param login      user's login
	 * @param email      user's e-mail
	 * @param passportID user's passportID
	 * @return object of class ValidatorUniqueUser, which contains information about uniqueness
	 * inserted data
	 * @throws DAOException error checking the presence of such a login, email, password in the database
	 */
	@Override
	public ValidatorUniqueUser findUser(String login, String email, String passportID) throws DAOException {

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
			ps.setString(1, passportID);
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

	/**
	 * Updating user
	 *
	 * @param newUser
	 * @throws DAOException error getting all users
	 */
	@Override
	public void updateUser(User newUser) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_UPDATE_USER_BY_ID_STARTS_MSG);

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = connectionPool.takeConnection();
			connection.setAutoCommit(false);

			ps = connection.prepareStatement(SQL_UPDATE_USER_BY_ID);
			ps.setString(1, newUser.getLogin());
			ps.setString(2, newUser.getPassword());
			ps.setString(3, newUser.getName());
			ps.setString(4, newUser.getSurName());
			ps.setString(5, newUser.getPhone());
			ps.setString(6, newUser.getEmail());
			ps.setString(7, newUser.getPassportID());
			ps.setInt(8, newUser.getUserID());

			ps.executeUpdate();
			connection.commit();

		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(DAOStringConstant.DAO_FIND_USER_ERROR_MSG, e);

		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps);
				}
				LOG.debug(DAOStringConstant.DAO_UPDATE_USER_BY_ID_ENDS_MSG);

			} catch (ConnectionPoolException e) {
				throw new DAOException(DAOStringConstant.DAO_UPDATE_USER_BY_ID_CON_ERROR_MSG, e);
			}
		}

	}

	/**
	 * Remove user account by id.
	 *
	 * @param userID
	 * @return true/false
	 * @throws DAOException
	 */
	@Override
	public boolean removeUserByID(int userID) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_REMOVE_USER_BY_ID_END_MSG);

		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Connection connection = null;
		PreparedStatement ps = null;
		PreparedStatement removeProfileDataPS = null;

		boolean result = false;
		try {
			connection = connectionPool.takeConnection();
			connection.setAutoCommit(false);

			ps = connection.prepareStatement(SQL_REMOVE_USER_BY_ID);
			ps.setInt(1, userID);
			ps.executeUpdate();
			connection.commit();

			result = true;

		} catch (ConnectionPoolException | SQLException e) {
			throw new DAOException(DAOStringConstant.DAO_REMOVE_USER_BY_ID_ERROR_MSG, e);
		} finally {

			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps);
				}

				LOG.debug(DAOStringConstant.DAO_REMOVE_USER_BY_ID_END_MSG);

			} catch (ConnectionPoolException e) {
				throw new DAOException(DAOStringConstant.DAO_REMOVE_USER_BY_ID_CON_ERROR_MSG, e);
			}
		}

		return result;
	}

	/**
	 * Take all users.
	 *
	 * @param startPage number of users to the current user
	 * @param amountUsersOnPage amount users on the page
	 * @return List of all users
	 * @throws DAOException
	 */
	@Override
	public List<User> takeAllUsers(int startPage, int amountUsersOnPage) throws DAOException {

		LOG.debug(DAOStringConstant.DAO_TAKE_ALL_USERS_STARTS_MSG);

		List<User> users = new ArrayList<>();
		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			connection = connectionPool.takeConnection();
			ps = connection.prepareStatement(TAKE_ALL_USERS_QUERY);
			ps.setInt(1, amountUsersOnPage);
			ps.setInt(2, startPage);
			rs = ps.executeQuery();

			while (rs.next()) {
				User user = new User();

				user.setUserID(rs.getInt(1));
				user.setLogin(rs.getString(2));
				user.setRoleType(RoleType.getValue(rs.getInt(3)));
				user.setSurName(rs.getString(4));
				user.setName(rs.getString(5));
				user.setEmail(rs.getString(6));
				user.setPhone(rs.getString(7));

				users.add(user);
			}

			return users;

		} catch (SQLException | ConnectionPoolException ex) {
			throw new DAOException(DAOStringConstant.DAO_TAKE_ALL_USERS_ERROR, ex);
		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, ps, rs);
				}

				LOG.debug(DAOStringConstant.DAO_TAKE_ALL_USERS_ENDS_MSG);

			} catch (ConnectionPoolException ex) {
				throw new DAOException(DAOStringConstant.DAO_TAKE_ALL_USERS_CLOSE_CON_ERROR_MSG, ex);
			}
		}
	}

	/**
	 * Calculate count of all users.
	 *
	 * @return Number of all users
	 * @throws DAOException
	 */
	@Override
	public int countAllUsers() throws DAOException {

		LOG.debug(DAOStringConstant.DAO_COUNT_ALL_USERS_STARTS_MSG);

		int ordersAmount = 0;
		Connection connection = null;
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		Statement st = null;
		ResultSet rs = null;

		try {

			connection = connectionPool.takeConnection();
			st = connection.createStatement();
			rs = st.executeQuery(COUNT_ALL_USERS_QUERY);

			while (rs.next()) {
				ordersAmount = rs.getInt(1);
			}

			return ordersAmount;

		} catch (SQLException | ConnectionPoolException ex) {
			throw new DAOException(ex);

		} finally {
			try {
				if (connectionPool != null) {
					connectionPool.closeConnection(connection, st, rs);
				}
				LOG.debug(DAOStringConstant.DAO_COUNT_ALL_USERS_ENDS_MSG);
			} catch (ConnectionPoolException ex) {
				throw new DAOException(ex);
			}
		}
	}

}
