package by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceimpl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.UserDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.ValidatorUniqueUser;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.RoleType;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceConstant;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.UserService;
import com.google.protobuf.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/*
 * Class for creating some logical actions with an entity User
 */
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class.getName());

	private final UserDAO userDAO = DAOFactory.getInstance().getUserDAO();



	/**
	 * Data transfer to DAO for user authorization
	 *
	 * @param login    user's login
	 * @param password user's password
	 * @return authorized user
	 * @throws ServiceException error authorization user
	 */
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
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

}
