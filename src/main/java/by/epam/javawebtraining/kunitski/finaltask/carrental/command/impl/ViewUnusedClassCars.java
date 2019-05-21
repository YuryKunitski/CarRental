package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.CarClassType;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.Validator;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.CarService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ViewUnusedClassCars implements Command {

	private static final Logger LOG = LogManager.getLogger(ViewUnusedClassCars.class.getName());

	private static final String EXECUTE_STARTS = "ViewUnusedClassCars : execute : starts";
	private static final String EXECUTE_ENDS = "ViewUnusedClassCars : execute : ends";

	private static final int AMOUNT_CARS_ON_PAGE = 9;
	private static final int DEFAULT_PAGE_NUMBER = 1;

	private static final String PAGE_NUMBER_PARAM = "pageNumber";
	private static final String CAR_CLASS_PARAM = "carClass";
	private static final String INVALID_ClASS_PARAM = "invalidClass";
	private static final String SUPPOSED_DATE_FROM_PARAM = "supposedDateFrom";
	private static final String SUPPOSED_DATE_TO_PARAM = "supposedDateTo";
	private static final String INVALID_DATE_PARAM = "invalidDate";
	private static final String NO_CARS_PARAM = "noCars";
	private static final String AMOUNT_PAGES_PARAM = "amountPages";
	private static final String ALL_CARS_PARAM = "allCars";
	private static final String COMMAND_PARAM = "command";
	private static final String PROCESS_REQUEST_PARAM = "processRequest";

	private static final String VIEW_CLASS_UNUSED_VALUE = "view-class-unused";
	private static final String FORWARD_VALUE = "forward";


	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		LOG.debug(EXECUTE_STARTS);

		int amountPages = 0;
		int pageNumber = DEFAULT_PAGE_NUMBER;

		if (request.getParameter(PAGE_NUMBER_PARAM) != null) {
			pageNumber = Integer.parseInt(request.getParameter(PAGE_NUMBER_PARAM));
		}

		Validator validator = Validator.getInstance();
		CarService service = ServiceFactory.getInstance().getCarService();
		CarClassType carClass = null;

		if (request.getParameter(CAR_CLASS_PARAM) != null) {
			carClass = CarClassType.valueOf(request.getParameter(CAR_CLASS_PARAM));
		} else {
			request.setAttribute(INVALID_ClASS_PARAM, true);
		}
		String supposedDateFrom = null;
		String supposedDateTo = null;

		if (request.getParameter(SUPPOSED_DATE_FROM_PARAM) != null) {
			supposedDateFrom = request.getParameter(SUPPOSED_DATE_FROM_PARAM);
			supposedDateTo = request.getParameter(SUPPOSED_DATE_TO_PARAM);

			request.getSession().setAttribute(SUPPOSED_DATE_FROM_PARAM, supposedDateFrom);
			request.getSession().setAttribute(SUPPOSED_DATE_TO_PARAM, supposedDateTo);

		} else {
			supposedDateFrom = (String) request.getSession().getAttribute(SUPPOSED_DATE_FROM_PARAM);
			supposedDateTo = (String) request.getSession().getAttribute(SUPPOSED_DATE_TO_PARAM);
		}

		if (!validator.validateDate(supposedDateFrom, supposedDateTo)) {
			request.setAttribute(INVALID_DATE_PARAM, true);
			request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
			request.setAttribute(NO_CARS_PARAM, true);

			LOG.debug(EXECUTE_ENDS);
			return PageName.ALL_CARS;
		}

		List<Car> cars = null;
		try {
			if (carClass != null) {
				cars = service.takeUnysedCarsByClassAndDate(carClass, supposedDateFrom, supposedDateTo, pageNumber,
						AMOUNT_CARS_ON_PAGE);
			} else {
				cars = service.takeUnusedCarsByDate(supposedDateFrom, supposedDateTo, pageNumber, AMOUNT_CARS_ON_PAGE);
			}

			if (cars.size() == 0) {
				request.setAttribute(NO_CARS_PARAM, true);
			}

			if (carClass != null) {
				amountPages = service.countPageAmountUnusedClassCars(supposedDateFrom, supposedDateTo, carClass,
						AMOUNT_CARS_ON_PAGE);
			} else {
				amountPages = service.countPageAmountUnusedCars(supposedDateFrom, supposedDateTo,
						AMOUNT_CARS_ON_PAGE);
			}

			request.getSession().setAttribute(ALL_CARS_PARAM, cars);

			request.setAttribute(AMOUNT_PAGES_PARAM, amountPages);
			request.setAttribute(COMMAND_PARAM, VIEW_CLASS_UNUSED_VALUE);
			request.setAttribute(CAR_CLASS_PARAM, carClass);
			request.setAttribute(PAGE_NUMBER_PARAM, pageNumber);
			request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

			LOG.debug(EXECUTE_ENDS);

			return PageName.ALL_CARS;

		} catch (ServiceException ex) {
			throw new CommandException(ex);
		}

	}
}
