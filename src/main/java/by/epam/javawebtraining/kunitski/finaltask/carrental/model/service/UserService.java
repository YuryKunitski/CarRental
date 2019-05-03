package by.epam.javawebtraining.kunitski.finaltask.carrental.model.service;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ConnectionPoolException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.interfacedao.UserDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.ValidatorUniqueUser;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.RoleType;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;
import com.google.protobuf.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/*
 * Class for creating some logical actions with an entity User
 */
public class UserService {

	private static final Logger LOG = LogManager.getLogger(ConnectionPool.class.getName());
	private static final UserService instance = new UserService();

	private final UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

	private UserService() {

	}

	public static UserService getInstance() {
		return instance;
	}

	/**
	 * Data transfer to DAO for user authorization
	 *
	 * @param login    user's login
	 * @param password user's password
	 * @return authorized user
	 * @throws ServiceException error authorization user
	 */
	public User login(String login, String password) throws ServiceException {

		LOG.debug(ServiceConstant.LOGIN_START_MSG);

		try {
			User user = userDAO.authorize(login, password);
			LOG.debug(ServiceConstant.LOGIN_END_MSG);
			return user;

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Data transfer to DAO for user registration
	 *
	 * @param login      user's login
	 * @param password   user's password
	 * @param name       user's name
	 * @param surname    user's surname
	 * @param phone      user's phone
	 * @param email      user's e-mail
	 * @param passportID user's passportID
	 * @return object of class ValidatorUniqueUser, which contains information about the reason for refusal
	 * to register a user or successful registration
	 * @throws ServiceException error registration user
	 */
	public ValidatorUniqueUser register(String login, String password, RoleType roleType, String name, String surname,
	                                    String phone, String email, String passportID) throws ServiceException {

		LOG.debug(ServiceConstant.REGISTER_START_MSG);

		User user = new User(login, password, roleType, name, surname, phone, email, passportID);

		try {
			ValidatorUniqueUser validatorUniqueUser = userDAO.findUser(login, email, passportID);
			if (validatorUniqueUser.isUniqueLogin() && validatorUniqueUser.isUniqueEmail()
					&& validatorUniqueUser.isUniquePassportID()) {
				userDAO.register(user);
				LOG.debug(ServiceConstant.REGISTER_END_MSG);
				return validatorUniqueUser;
			} else {
				LOG.debug(ServiceConstant.REGISTER_END_MSG);
				return validatorUniqueUser;
			}
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Data transfer to DAO for find user by id
	 *
	 * @param userID user's id
	 * @return authorized user
	 * @throws ServiceException error find user by id
	 */
	public User findUserById(int userID) throws ServiceException {
		LOG.debug(ServiceConstant.TAKE_USER_BY_ID_START_MSG);
		try {
			User user = userDAO.findUserById(userID);
			LOG.debug(ServiceConstant.TAKE_USER_BY_ID_END_MSG);
			return user;
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Data transfer to DAO for update user
	 *
	 * @param newUser new users
	 * @throws ServiceException error update user
	 */
	public void updateUser(User newUser) throws ServiceException {
		LOG.debug(ServiceConstant.UPDATE_USER_START_MSG);
		try {
			userDAO.updateUser(newUser);
			LOG.debug(ServiceConstant.UPDATE_USER_ENDS_MSG);
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Data transfer to DAO for remove user by id
	 *
	 * @param userID user's id
	 * @throws ServiceException error remove user by id
	 */
	public boolean removeUserByID(int userID) throws ServiceException {

		LOG.debug(ServiceConstant.UPDATE_USER_START_MSG);
		boolean result = false;

		try {
			userDAO.removeUserByID(userID);
			result = true;

			LOG.debug(ServiceConstant.UPDATE_USER_ENDS_MSG);
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
		return result;
	}

	public static void main(String[] args) {

		try {
			ConnectionPool.getInstance().initConnectionPool();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}

		UserService userService = UserService.getInstance();
		try {
			userService.updateUser(new User(18,"Yutta", "102030", RoleType.CUSTOMER, "Yury",
					"Kunitski", "+37529 1111111", "yyy@gmail.com", "кн123456789"));
//			System.out.println(userService.findUserById(18));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

}
