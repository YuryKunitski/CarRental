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
import java.util.List;

public class ViewClassCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(ViewClassCommand.class.getName());

	private static final int AMOUNT_CARS_ON_PAGE = 9;
	private static final int DEFAULT_PAGE_NUMBER = 1;

	private static final String EXECUTE_STARTS = "ViewClassCommand : execute : starts";
	private static final String EXECUTE_ENDS = "ViewClassCommand : execute : ends";

	private static final String PAGE_NUBMER_PARAM = "pageNumber";
	private static final String CAR_CLASS_PARAM = "carClass";
	private static final String AMOUNT_PAGES_PARAM = "amountPages";
	private static final String ALL_CARS_PARAM = "allCars";
	private static final String COMMAND_PARAM = "command";
	private static final String COMMAND_VALUE = "view-class";
	private static final String ALL_CLASSES_PARAM = "allClasses";
	private static final String PROCESS_REQUEST = "processRequest";

	private static final String FORWARD_VALUE = "forward";


	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		LOG.debug(EXECUTE_STARTS);

		int amountPages = 0;
		int pageNumber = DEFAULT_PAGE_NUMBER;

		if (request.getParameter(PAGE_NUBMER_PARAM) != null) {
			pageNumber = Integer.parseInt(request.getParameter(PAGE_NUBMER_PARAM));
		}

		CarClassType classType = CarClassType.valueOf(request.getParameter(CAR_CLASS_PARAM));
		CarService service = ServiceFactory.getInstance().getCarService();
		List<Car> cars = null;
		List<CarClassType> carClassTypes = null;

		try {
			cars = service.takeCarsByClass(classType, pageNumber, AMOUNT_CARS_ON_PAGE);
			carClassTypes = service.takeCarClass();
			amountPages = service.countPageAmountClassCars(classType, AMOUNT_CARS_ON_PAGE);

			request.getSession().setAttribute(ALL_CLASSES_PARAM, carClassTypes);
			request.getSession().setAttribute(ALL_CARS_PARAM, cars);

			request.setAttribute(AMOUNT_PAGES_PARAM, amountPages);
			request.setAttribute(COMMAND_PARAM, COMMAND_VALUE);
			request.setAttribute(CAR_CLASS_PARAM, classType);
			request.setAttribute(PAGE_NUBMER_PARAM, pageNumber);
			request.setAttribute(PROCESS_REQUEST, FORWARD_VALUE);

			LOG.debug(EXECUTE_ENDS);

			return PageName.ALL_CARS;

		} catch (ServiceException ex) {
			throw new CommandException(ex);
		}
	}
}
