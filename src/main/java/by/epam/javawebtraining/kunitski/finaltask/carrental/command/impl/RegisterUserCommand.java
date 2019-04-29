package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.ValidatorUniqueUser;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.RoleType;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.UserService;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.Validator;
import com.google.protobuf.ServiceException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RegisterUserCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(RegisterUserCommand.class.getName());

	private static final String EXECUTE_STARTS_MSG = "RegisterUserCommand : execute : starts";
	private static final String EXECUTE_ENDS_MSG = "RegisterUserCommand : execute : ends";
	private static final String VALID_PARAMETERS_STARTS_MSG = "RegisterUserCommand : validParameters : starts";
	private static final String VALID_PARAMETERS_ENDS_MSG = "RegisterUserCommand : validParameters : ends";

	private static final String LOGIN_PARAMETER = "login";
	private static final String PASSWORD_PARAMETER = "password";
	private static final String ROLE_ID_PARAMETER = "role_id";
	private static final String CONFIRM_PASSWORD_PARAMETER = "confirm-password";
	private static final String E_MAIL = "email";
	private static final String NAME = "name";
	private static final String SURNAME = "surname";
	//	private static final String MIDDLE_NAME = "middle-name";
	private static final String PHONE = "phone";
	private static final String PASSPORT = "passport";
	//	private static final String ADDRESS = "address";
	private static final String LOGIN_VALID = "validLogin";
	private static final String PASSWORD_VALID = "validPassword";
	private static final String CONFIRM_PASSWORD_VALID = "validConfirmPassword";
	private static final String CONFIRMATION_PASSWORD = "confirmationPassword";
	private static final String E_MAIL_VALID = "validEmail";
	private static final String LAST_NAME_VALID = "validLastName";
	private static final String FIRST_NAME_VALID = "validFirstName";
	private static final String MIDDLE_NAME_VALID = "validMiddleName";
	private static final String PHONE_VALID = "validPhone";
	private static final String PASSPORT_VALID = "validPassport";
	//	private static final String ADDRESS_VALID = "validAddress";
	private static final String UNIQUE_LOGIN = "uniqueLogin";
	private static final String UNIQUE_EMAIL = "uniqueEmail";
	private static final String UNIQUE_PASSPORT = "uniquePassport";
	private static final String SUCCESSFUL_REGISTER = "successfulRegister";
	private static final String PROCESS_REQUEST_PARAM = "processRequest";

	private static final String FORWARD_VALUE = "forward";
	private static final String REDIRECT_VALUE = "redirect";

	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		LOG.debug(EXECUTE_STARTS_MSG);
		if (validParameters(request)) {
			request.getSession(true).setAttribute(SUCCESSFUL_REGISTER, true);
			request.setAttribute(PROCESS_REQUEST_PARAM, REDIRECT_VALUE); //pass to jsp page
			LOG.debug(EXECUTE_ENDS_MSG);
			return PageName.INDEX_PAGE;
		} else {
			request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
			LOG.debug(EXECUTE_ENDS_MSG);
			return PageName.REGISTRATION_PAGE;
		}
	}


	private boolean validParameters(HttpServletRequest request) throws CommandException {
		LOG.debug(VALID_PARAMETERS_STARTS_MSG);
		String login = request.getParameter(LOGIN_PARAMETER);
		String password = request.getParameter(PASSWORD_PARAMETER);
		RoleType roleType = RoleType.valueOf(request.getParameter(ROLE_ID_PARAMETER));
		String confirmPassword = request.getParameter(CONFIRM_PASSWORD_PARAMETER);
		String email = request.getParameter(E_MAIL);
		String name = request.getParameter(NAME);
		String surname = request.getParameter(SURNAME);
//		String middleName = request.getParameter(MIDDLE_NAME);
		String phone = request.getParameter(PHONE);
		String passportID = request.getParameter(PASSPORT);
//		String address = request.getParameter(ADDRESS);

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
		request.setAttribute(LAST_NAME_VALID, true);
		request.setAttribute(FIRST_NAME_VALID, true);
		request.setAttribute(MIDDLE_NAME_VALID, true);
		request.setAttribute(PHONE_VALID, true);
		request.setAttribute(PASSPORT_VALID, true);
//		request.setAttribute(ADDRESS_VALID, true);

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
			request.setAttribute(LAST_NAME_VALID, false);
			countFailedValidations++;
		}
		if (!validator.validateName(surname)) {
			request.setAttribute(FIRST_NAME_VALID, false);
			countFailedValidations++;
		}

		if (!validator.validatePhone(phone)) {
			request.setAttribute(PHONE_VALID, false);
			countFailedValidations++;
		}
		if (!validator.validatePassport(passportID)) {
			request.setAttribute(PASSPORT_VALID, false);
			countFailedValidations++;
		}

		if (countFailedValidations > 0) {
			LOG.debug(VALID_PARAMETERS_ENDS_MSG);
			return false;
		} else {
			try {
				UserService service = UserService.getInstance();
				ValidatorUniqueUser validatorUniqueUser = service.register(login, password, roleType ,name, surname, phone,
						email, passportID);
				uniqueLogin = validatorUniqueUser.isUniqueLogin();
				uniqueEmail = validatorUniqueUser.isUniqueEmail();
				uniquePassport = validatorUniqueUser.isUniquePassportID();
				request.setAttribute(UNIQUE_LOGIN, uniqueLogin);
				request.setAttribute(UNIQUE_EMAIL, uniqueEmail);
				request.setAttribute(UNIQUE_PASSPORT, uniquePassport);
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
