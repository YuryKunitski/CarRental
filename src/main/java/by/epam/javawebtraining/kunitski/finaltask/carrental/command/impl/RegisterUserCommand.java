package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.ValidatorUniqueUser;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.RoleType;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.Validator;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The command to register a user
 */
public class RegisterUserCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(RegisterUserCommand.class.getName());

	private static final String EXECUTE_STARTS_MSG = "RegisterUserCommand : execute : starts";
	private static final String EXECUTE_ENDS_MSG = "RegisterUserCommand : execute : ends";
	private static final String VALID_PARAMETERS_STARTS_MSG = "RegisterUserCommand : validParameters : starts";
	private static final String VALID_PARAMETERS_ENDS_MSG = "RegisterUserCommand : validParameters : ends";

	private static final String LOGIN_PARAMETER = "login";
	private static final String PASSWORD_PARAMETER = "password";
	private static final String CONFIRM_PASSWORD_PARAMETER = "confirm-password";
	private static final String E_MAIL = "e-mail";
	private static final String NAME = "name";
	private static final String SURNAME = "surname";
	private static final String PHONE = "phone";
	private static final String PASSPORT_ID = "passportID";
	private static final String LOGIN_VALID = "validLogin";
	private static final String PASSWORD_VALID = "validPassword";
	private static final String CONFIRM_PASSWORD_VALID = "validConfirmPassword";
	private static final String CONFIRMATION_PASSWORD = "confirmationPassword";
	private static final String E_MAIL_VALID = "validEmail";
	private static final String NAME_VALID = "validName";
	private static final String SURNAME_VALID = "validSurname";
	private static final String PHONE_VALID = "validPhone";
	private static final String PASSPORT_ID_VALID = "validPassportID";
	private static final String UNIQUE_LOGIN = "uniqueLogin";
	private static final String UNIQUE_EMAIL = "uniqueEmail";
	private static final String UNIQUE_PASSPORT_ID = "uniquePassportID";
	private static final String SUCCESSFUL_REGISTER = "successfulRegister";
	private static final String PROCESS_REQUEST_PARAM = "processRequest";

	private static final String FORWARD_VALUE = "forward";
	private static final String REDIRECT_VALUE = "redirect";

	private static final RoleType CUSTOMER_ROLE = RoleType.CUSTOMER;

	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		LOG.debug(EXECUTE_STARTS_MSG);

		if (validParameters(request)) {
			request.getSession(true).setAttribute(SUCCESSFUL_REGISTER, true);
			request.setAttribute(PROCESS_REQUEST_PARAM, REDIRECT_VALUE);

			LOG.debug(EXECUTE_ENDS_MSG);

			return PageName.INDEX_PAGE;
		} else {
			request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

			LOG.debug(EXECUTE_ENDS_MSG);

			return PageName.REGISTRATION_PAGE;
		}
	}

	/**
	 * Validation input data to registration user
	 * @param request
	 * @return
	 * @throws CommandException
	 */
	private boolean validParameters(HttpServletRequest request) throws CommandException {

		LOG.debug(VALID_PARAMETERS_STARTS_MSG);

		String login = request.getParameter(LOGIN_PARAMETER);
		String password = request.getParameter(PASSWORD_PARAMETER);
		String confirmPassword = request.getParameter(CONFIRM_PASSWORD_PARAMETER);
		String email = request.getParameter(E_MAIL);
		String name = request.getParameter(NAME);
		String surname = request.getParameter(SURNAME);
		String phone = request.getParameter(PHONE);
		String passportID = request.getParameter(PASSPORT_ID);


		boolean uniqueLogin = false;
		boolean uniqueEmail = false;
		boolean uniquePassport = false;

		int countFailedValidations = 0;

		Validator validator = Validator.getInstance();

		request.setAttribute(LOGIN_VALID, true);
		request.setAttribute(PASSWORD_VALID, true);
		request.setAttribute(CONFIRM_PASSWORD_VALID, true);
		request.setAttribute(CONFIRMATION_PASSWORD, true);
		request.setAttribute(E_MAIL_VALID, true);
		request.setAttribute(NAME_VALID, true);
		request.setAttribute(SURNAME_VALID, true);
		request.setAttribute(PHONE_VALID, true);
		request.setAttribute(PASSPORT_ID_VALID, true);


		if (!validator.validateLogin(login)) {
			request.setAttribute(LOGIN_VALID, false);
			countFailedValidations++;
		}
		if (!validator.validatePassword(password)) {
			request.setAttribute(PASSWORD_VALID, false);
			countFailedValidations++;
		}
		if (!validator.validatePassword(confirmPassword)) {
			request.setAttribute(CONFIRM_PASSWORD_VALID, false);
			countFailedValidations++;
		}
		if (!validator.validateConfirmationPassword(password, confirmPassword)) {
			request.setAttribute(CONFIRMATION_PASSWORD, false);
			countFailedValidations++;
		}
		if (!validator.validateEmail(email)) {
			request.setAttribute(E_MAIL_VALID, false);
			countFailedValidations++;
		}
		if (!validator.validateName(name)) {
			request.setAttribute(NAME_VALID, false);
			countFailedValidations++;
		}
		if (!validator.validateName(surname)) {
			request.setAttribute(SURNAME_VALID, false);
			countFailedValidations++;
		}

		if (!validator.validatePhone(phone)) {
			request.setAttribute(PHONE_VALID, false);
			countFailedValidations++;
		}
		if (!validator.validatePassport(passportID)) {
			request.setAttribute(PASSPORT_ID_VALID, false);
			countFailedValidations++;
		}

		if (countFailedValidations > 0) {
			LOG.debug(VALID_PARAMETERS_ENDS_MSG);
			return false;

		} else {
			try {
				UserService service = ServiceFactory.getInstance().getUserService();
				ValidatorUniqueUser validatorUniqueUser = service.register(login, password, CUSTOMER_ROLE ,name,
						surname, phone, email, passportID);

				uniqueLogin = validatorUniqueUser.isUniqueLogin();
				uniqueEmail = validatorUniqueUser.isUniqueEmail();
				uniquePassport = validatorUniqueUser.isUniquePassportID();

				request.setAttribute(UNIQUE_LOGIN, uniqueLogin);
				request.setAttribute(UNIQUE_EMAIL, uniqueEmail);
				request.setAttribute(UNIQUE_PASSPORT_ID, uniquePassport);

				if (!(uniqueLogin && uniqueEmail && uniquePassport)) {
					LOG.debug(VALID_PARAMETERS_ENDS_MSG);
					return false;
				}

				LOG.debug(VALID_PARAMETERS_ENDS_MSG);

				return true;
			} catch (ServiceException ex) {
				throw new CommandException(ex);
			}
		}
	}
}
