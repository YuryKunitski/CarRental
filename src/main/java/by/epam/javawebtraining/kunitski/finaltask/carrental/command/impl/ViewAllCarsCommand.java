package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.CarClassType;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.CarService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  Command to get all the cars
 */
public class ViewAllCarsCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(ViewAllCarsCommand.class.getName());

	private static  final int AMOUNT_CARS_ON_PAGE = 9;

	private static final String EXECUTE_STARTS = "ViewAllCarsCommand : execute : starts";
	private static final String EXECUTE_ENDS = "ViewAllCarsCommand : execute : ends";

	private static final String PAGE_NUMBER_PARAM = "pageNumber";
	private static final String AMOUNT_PAGES_PARAM = "amountPages";
	private static final String ALL_CARS_PARAM = "allCars";
	private static final String ALL_CLASSES_PARAM = "allClasses";
	private static final String NO_CARS_PARAM = "noCars";
	private static final String PROCESS_REQUEST_PARAM = "processRequest";

	private static final String FORWARD_VALUE = "forward";
	private static final int DEFAULT_PAGE_NUMBER = 1;

	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		LOG.debug(EXECUTE_STARTS);

		int amountPages = 0;
		int pageNumber = DEFAULT_PAGE_NUMBER;
		if (request.getParameter(PAGE_NUMBER_PARAM) != null) {
			pageNumber = Integer.parseInt(request.getParameter(PAGE_NUMBER_PARAM));
		}
		List<Car> cars = null;
		List<CarClassType> carClasses = null;
		CarService service = ServiceFactory.getInstance().getCarService();

		try {
			cars = service.takeAllCars(pageNumber, AMOUNT_CARS_ON_PAGE);
			Set<Car> setCars = new HashSet<>(cars);
			if (cars.size() == 0) {
				request.setAttribute(NO_CARS_PARAM, true);
			}
			carClasses = service.takeCarClass();
			amountPages = service.countPageAmountAllCars(AMOUNT_CARS_ON_PAGE);

			request.setAttribute(AMOUNT_PAGES_PARAM, amountPages);

			request.getSession().setAttribute(ALL_CARS_PARAM, setCars);
			request.getSession().setAttribute(ALL_CLASSES_PARAM, carClasses);

			request.setAttribute(PAGE_NUMBER_PARAM, pageNumber);
			request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

			LOG.debug(EXECUTE_ENDS);

			return PageName.ALL_CARS;

		} catch (ServiceException ex) {
			throw new CommandException(ex);
		}
	}
}
