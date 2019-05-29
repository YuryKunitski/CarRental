package by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceimpl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.UserDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.ValidatorUniqueUser;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.RoleType;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceConstant;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

/*
 * Class for creating some logical actions with an entity User
 */
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LogManager.getLogger(UserServiceImpl.class.getName());

	private final UserDAO USER_DAO = DAOFactory.getInstance().getUserDAO();


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
			User user = USER_DAO.authorize(login, password);
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
			ValidatorUniqueUser validatorUniqueUser = USER_DAO.findUser(login, email, passportID);
			if (validatorUniqueUser.isUniqueLogin() && validatorUniqueUser.isUniqueEmail()
					&& validatorUniqueUser.isUniquePassportID()) {
				USER_DAO.register(user);
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
			User user = USER_DAO.findUserById(userID);
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
			USER_DAO.updateUser(newUser);
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
			USER_DAO.removeUserByID(userID);
			result = true;

			LOG.debug(ServiceConstant.UPDATE_USER_ENDS_MSG);
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
		return result;
	}

	/**
	 * Take list of all users from DAO
	 * @return list of all users
	 * @throws ServiceException error take list of all users
	 */
	@Override
	public List<User> takeAllUsers(int pageNumber, int amountUsersOnPage) throws ServiceException {

		LOG.debug(ServiceConstant.TAKE_ALL_USERS_START_MSG);

		int startPage = userToStartPage(pageNumber, amountUsersOnPage);

		try {
			List<User> users = USER_DAO.takeAllUsers(startPage, amountUsersOnPage);

			LOG.debug(ServiceConstant.TAKE_ALL_USERS_END_MSG);

			return users;

		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Calculate count of pages of all users
	 * @return list of all users
	 * @throws ServiceException error take list of all users
	 */
	@Override
	public int countPageAmountAllUsers(int amountUsersOnPage) throws ServiceException {

		LOG.debug(ServiceConstant.COUNT_PAGE_AMOUNT_ALL_USERS_START_MSG);

		int pageAmount = 0;
		int usersAmount = 0;

		try {
			usersAmount = USER_DAO.countAllUsers();
			if (usersAmount % amountUsersOnPage != 0) {
				pageAmount = (usersAmount / amountUsersOnPage) + 1;
			} else {
				pageAmount = (usersAmount / amountUsersOnPage);
			}

			LOG.debug(ServiceConstant.COUNT_PAGE_AMOUNT_ALL_USERS_END_MSG);

			return pageAmount;

		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Calculate number of users before current page
	 * @param pageNumber   number of page
	 * @param usersOnPage count of order on page
	 * @return count of order to start page
	 */
	private int userToStartPage(int pageNumber, int usersOnPage) {

		LOG.debug(ServiceConstant.USER_TO_START_PAGE_STARTS_MSG);
		LOG.debug(ServiceConstant.USER_TO_START_PAGE_ENDS_MSG);

		return ((pageNumber * usersOnPage) - usersOnPage);
	}
}
