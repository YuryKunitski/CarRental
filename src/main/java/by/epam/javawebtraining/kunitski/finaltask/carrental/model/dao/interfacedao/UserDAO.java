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
	void register(User user) throws DAOException;

	/**
	 * Authorize user.
	 *
	 * @param login
	 * @param password
	 * @return
	 * @throws DAOException
	 */
	User authorize(String login, String password) throws DAOException;

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
	public ValidatorUniqueUser findUser(String login, String email, String passportID) throws DAOException;

	/**
	 * Find user by ID
	 *
	 * @param userId user's id
	 * @return found user
	 * @throws DAOException error find user by id
	 */
	User findUserById(int userId) throws DAOException;

	/**
	 * Updating user
	 *
	 * @param newUser
	 * @throws DAOException error getting all users
	 */
	public void updateUser(User newUser) throws DAOException;


	/**
	 * Remove user account by id.
	 *
	 * @param idUser
	 * @return true/false
	 * @throws DAOException
	 */
	boolean removeUserByID(int idUser) throws DAOException;

}
