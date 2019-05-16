package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.CarClassType;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.CarService;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The command to authorize the user
 */
public class LoginUserCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(LoginUserCommand.class.getName());

	private static final String EXECUTE_STARTS_MSG = "LoginUserCommand : execute : starts";
	private static final String EXECUTE_ENDS_MSG = "LoginUserCommand : execute : ends";

	private static final String PARAM_LOGIN = "login";
	private static final String PARAM_PASSWORD = "password";
	private static final String PARAM_LOGIN_FAILED = "loginFailed";
	private static final String PARAM_USER = "user";
	private static final String PARAM_ROLE = "role";
	private static final String PARAM_PAGE_NAME = "page-name";
	private static final String PARAM_PAGE_NUMBER = "pageNumber";
	private static final String ALL_CARS_PARAM = "allCars";
	private static final String ALL_CLASSES_PARAM = "allClasses";
	private static final String AMOUNT_PAGES_PARAM = "amountPages";
	private static final String PROCESS_REQUEST_PARAM = "processRequest";

	private static final String INDEX_VALUE = "index";
	private static final String ALL_CARS_VALUE = "all-cars";
	private static final String VIEW_CAR_VALUE = "view-car";
	private static final String FORWARD_VALUE = "forward";

	private static final int AMOUNT_CARS_ON_PAGE = 9;
	private static final int DEFAULT_PAGE_NUMBER = 1;

	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		LOG.debug(EXECUTE_STARTS_MSG);

		String login = request.getParameter(PARAM_LOGIN);
		String password = request.getParameter(PARAM_PASSWORD);
		UserService service = ServiceFactory.getInstance().getUserService();

		try {
			User user = service.login(login, password);

			if (user != null) {
				request.getSession(true).setAttribute(PARAM_USER, user);
				request.getSession(true).setAttribute(PARAM_ROLE, user.getRoleType());

				if (request.getParameter(PARAM_PAGE_NAME).equals(INDEX_VALUE)) {
					request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

					LOG.debug(EXECUTE_ENDS_MSG);

					return PageName.INDEX_PAGE;

				} else if (request.getParameter(PARAM_PAGE_NAME).equals(ALL_CARS_VALUE)) {

					int pageNumber = DEFAULT_PAGE_NUMBER;

					if (request.getParameter(PARAM_PAGE_NUMBER) != null) {
						pageNumber = Integer.parseInt(request.getParameter(PARAM_PAGE_NUMBER));
					}

					CarService carService = ServiceFactory.getInstance().getCarService();
					List<Car> cars = carService.takeAllCars(pageNumber, AMOUNT_CARS_ON_PAGE);

					List<CarClassType> carClasses = carService.takeCarClass();

					int amountPages = carService.countPageAmountAllCars(AMOUNT_CARS_ON_PAGE);

					request.setAttribute(AMOUNT_PAGES_PARAM, amountPages);
					request.getSession().setAttribute(ALL_CARS_PARAM, cars);

					request.getSession().setAttribute(ALL_CLASSES_PARAM, carClasses);
					request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

					LOG.debug(EXECUTE_ENDS_MSG);

					return PageName.ALL_CARS;

				} else if (request.getParameter(PARAM_PAGE_NAME).equals(VIEW_CAR_VALUE)) {
					request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

					LOG.debug(EXECUTE_ENDS_MSG);

					return PageName.VIEW_CAR;

				} else {
					request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

					LOG.debug(EXECUTE_ENDS_MSG);

					return PageName.ERROR_PAGE;
				}
			} else {
				request.setAttribute(PARAM_LOGIN_FAILED, true);

				if (request.getParameter(PARAM_PAGE_NAME).equals(INDEX_VALUE)) {
					request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

					LOG.debug(EXECUTE_ENDS_MSG);

					return PageName.INDEX_PAGE;
				} else if (request.getParameter(PARAM_PAGE_NAME).equals(ALL_CARS_VALUE)) {
					request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

					LOG.debug(EXECUTE_ENDS_MSG);

					return PageName.ALL_CARS;

				} else if (request.getParameter(PARAM_PAGE_NAME).equals(VIEW_CAR_VALUE)) {
					request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

					LOG.debug(EXECUTE_ENDS_MSG);

					return PageName.VIEW_CAR;

				} else {
					request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

					LOG.debug(EXECUTE_ENDS_MSG);

					return PageName.ERROR_PAGE;
				}
			}
		} catch (ServiceException ex) {
			throw new CommandException(ex);
		}
	}
}
