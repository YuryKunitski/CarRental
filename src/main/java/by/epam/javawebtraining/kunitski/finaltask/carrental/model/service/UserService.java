package by.epam.javawebtraining.kunitski.finaltask.carrental.model.service;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.impldao.UserDAOimpl;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.interfacedao.UserDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.ValidatorUniqueUser;
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
	 * Передача данных на DAO для авторизации пользователя
	 *
	 * @param login    логин польщователя
	 * @param password пароль пользователя
	 * @return если пользователь не null, то он может авторизиоваться
	 * @throws ServiceException ошибка при авторизации пользователя
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
	 * Передача параметров на DAO для регистрации пользователя
	 *
	 * @param login        логин пользователя
	 * @param password хэш-значение пароля
	 * @param name     фамилия пользователя
	 * @param surname    имя пользователя
	 * @param phone        телефон пользователя
	 * @param email        e-mail пользователя
	 * @param passportID     паспорт пользователя private static final String REGISTER_START_MSG
	 * @return объект класса ValidatorUniqueUser, который несет в себе информацию о причине отказа
	 * в регистрации пользователя или успешной регистрации
	 * @throws ServiceException ошибка при регистрации пользователя
	 */
	public ValidatorUniqueUser register(String login, String password, String name, String surname, String phone,
	                                    String email, String passportID) throws ServiceException {
		LOG.debug(ServiceConstant.REGISTER_START_MSG);
		User user = new User(login, password, name, surname, phone, email, passportID);
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
