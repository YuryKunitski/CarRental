package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.CarService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ViewCarCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(ViewCarCommand.class.getName());

	private static final String EXECUTE_STARTS = "ViewCarCommand : execute : starts";
	private static final String EXECUTE_ENDS = "ViewCarCommand : execute : ends";

	private static final String SELECTED_CAR_ID_PARAM = "selectedCarId";
	private static final String SELECTED_CAR_PARAM = "selectedCar";
	private static final String PROCESS_REQUEST = "processRequest";

	private static final String FORWARD_VALUE = "forward";

	@Override
	public String execute(HttpServletRequest request) throws CommandException {
		LOG.debug(EXECUTE_STARTS);
		int id = Integer.valueOf(request.getParameter(SELECTED_CAR_ID_PARAM));
		CarService service = ServiceFactory.getInstance().getCarService();
		Car car = null;

		try {
			car = service.takeCarById(id);

			request.getSession().setAttribute(SELECTED_CAR_ID_PARAM, id);
			request.getSession().setAttribute(SELECTED_CAR_PARAM, car);
			request.setAttribute(PROCESS_REQUEST, FORWARD_VALUE);

			LOG.debug(EXECUTE_ENDS);

			return PageName.VIEW_CAR;

		} catch (ServiceException ex) {
			throw new CommandException(ex);
		}
	}
}
