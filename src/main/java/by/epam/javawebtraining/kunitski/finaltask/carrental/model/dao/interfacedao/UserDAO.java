package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.interfacedao;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.ValidatorUniqueUser;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;

import java.util.List;

public interface UserDAO {

	/**
	 * Register user.
	 *
	 * @throws DAOException
	 */
	void registration(User user) throws DAOException;

	/**
	 * Authorize user.
	 *
	 * @param login
	 * @param password
	 * @return
	 * @throws DAOException
	 */
	User authorization(String login, String password) throws DAOException;

	/**
	 * Проверка наличия таких логина, e-mail, пароля в базе данных
	 * @param login логин пользователя
	 * @param email e-mail пользователя
	 * @param passport серия и номер пасспорта пользователя
	 * @return объект класса ValidatorUniqueUser, хранящий в себе информацию, об уникальности
	 * вставляемых данных
	 * @throws DAOException ошибка при проверке наличия таких логина,
	 * e-mail, пароля в бд
	 */
	public ValidatorUniqueUser findUser(String login, String email, String passport) throws DAOException;

	/**
	 * Получение всех пользователей
	 * @return список всех пользователей
	 * @throws DAOException ошибка при получении всех пользователей
	 */
	public List<User> takeAllUsers(int startPage, int amountUsersOnPage) throws DAOException;

	/**
	 * Remove user account by id.
	 *
	 * @param idUser
	 * @return
	 * @throws DAOException
	 */
	boolean removeUserByID(int idUser) throws DAOException;

}
