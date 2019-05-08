package by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.ValidatorUniqueUser;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.RoleType;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;

public interface UserService {

	/**
	 * Data transfer to DAO for user authorization
	 *
	 * @param login    user's login
	 * @param password user's password
	 * @return authorized user
	 * @throws ServiceException error authorization user
	 */
	 User login(String login, String password) throws ServiceException;

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
	 ValidatorUniqueUser register(String login, String password, RoleType roleType, String name, String surname,
	                                    String phone, String email, String passportID) throws ServiceException;

	/**
	 * Data transfer to DAO for find user by id
	 *
	 * @param userID user's id
	 * @return authorized user
	 * @throws ServiceException error find user by id
	 */
	 User findUserById(int userID) throws ServiceException;

	/**
	 * Data transfer to DAO for update user
	 *
	 * @param newUser new users
	 * @throws ServiceException error update user
	 */
	 void updateUser(User newUser) throws ServiceException;

	/**
	 * Data transfer to DAO for remove user by id
	 *
	 * @param userID user's id
	 * @throws ServiceException error remove user by id
	 */
	 boolean removeUserByID(int userID) throws ServiceException;


}
