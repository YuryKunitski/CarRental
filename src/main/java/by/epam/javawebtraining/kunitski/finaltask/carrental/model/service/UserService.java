package by.epam.javawebtraining.kunitski.finaltask.carrental.model.service;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.impldao.UserDAOimpl;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.interfacedao.UserDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.ValidatorUniqueUser;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.RoleType;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;
import com.google.protobuf.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class UserService {

	private static final Logger LOG = LogManager.getLogger(ConnectionPool.class.getName());
	private static final UserService instance = new UserService();

	private final UserDAO userDAO = new UserDAOimpl();

	private UserService() {

	}

	public static UserService getInstance() {
		return instance;
	}

	/**
	 *
	 * Data transfer to DAO for user authorization
	 *
	 * @param login   user's login
	 * @param password user's password
	 * @return authorized user
	 * @throws ServiceException error authorization user
	 */
	public User login(String login, String password) throws ServiceException {

		LOG.debug(ServiceConstant.LOGIN_START_MSG);

		try {
			User user = userDAO.authorization(login, password);
			LOG.debug(ServiceConstant.LOGIN_END_MSG);
			return user;

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Data transfer to DAO for user registration
	 *
	 * @param login        user's login
	 * @param password user's password
	 * @param name     user's name
	 * @param surname    user's surname
	 * @param phone        user's phone
	 * @param email        user's e-mail
	 * @param passportID    user's passportID
	 * @return object of class ValidatorUniqueUser, which carries information about the reason for refusal
	 * to register a user or successful registration
	 * @throws ServiceException error registration user
	 */
	public ValidatorUniqueUser register(String login, String password, RoleType roleType, String name, String surname, String phone,
	                                    String email, String passportID) throws ServiceException {
		LOG.debug(ServiceConstant.REGISTER_START_MSG);
		User user = new User(login, password, roleType, name, surname, phone, email, passportID);
		try {
			ValidatorUniqueUser validatorUniqueUser = userDAO.findUser(login, email, passportID);
			if (validatorUniqueUser.isUniqueLogin() && validatorUniqueUser.isUniqueEmail() && validatorUniqueUser.isUniquePassportID()) {
				userDAO.registration(user);
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

}
