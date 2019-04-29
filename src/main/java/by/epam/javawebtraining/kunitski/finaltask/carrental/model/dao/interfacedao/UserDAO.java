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
	 * Check for such logins, e-mail, password in the database
	 * @param login user's login
	 * @param email user's e-mail
	 * @param passportID user's passportID
	 * @return object of class ValidatorUniqueUser, which stores information about uniqueness
	 * inserted data
	 * @throws DAOException error checking the presence of such a login, email, password in the database
	 */
	public ValidatorUniqueUser findUser(String login, String email, String passportID) throws DAOException;

	/**
	 * Getting all users
	 * @return list of all users
	 * @throws DAOException error getting all users
	 */
	public List<User> takeAllUsers(int startPage, int amountUsersOnPage) throws DAOException;

	/**
	 * Remove user account by id.
	 *
	 * @param idUser
	 * @return true/false
	 * @throws DAOException
	 */
	boolean removeUserByID(int idUser) throws DAOException;

}
