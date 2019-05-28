package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.CarService;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.OrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class ViewCarCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(ViewCarCommand.class.getName());

	private static final String EXECUTE_STARTS = "ViewCarCommand : execute : starts";
	private static final String EXECUTE_ENDS = "ViewCarCommand : execute : ends";
//	private static final Double MAX_DISCOUNT_COEFFICIENT = 0.5;

	private static final String SELECTED_CAR_ID_PARAM = "selectedCarId";
	private static final String SELECTED_CAR_PARAM = "selectedCar";
	private static final String SELECTED_CAR_CLASS_PARAM = "carClassType";
	private static final String PROCESS_REQUEST = "processRequest";
	private static final String CAR_PRICES_PARAM = "prices";

	private static final String FORWARD_VALUE = "forward";

	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		LOG.debug(EXECUTE_STARTS);

		int id = Integer.valueOf(request.getParameter(SELECTED_CAR_ID_PARAM));
		CarService carService = ServiceFactory.getInstance().getCarService();
		OrderService orderService = ServiceFactory.getInstance().getOrderService();
		Car car = null;
		List<Double> listCarPrices = new ArrayList<>();

		try {
			car = carService.takeCarById(id);

			List<Double> listAllDiscounts = orderService.takeAllDiscountCoefficients();
			if (listAllDiscounts != null){
				for (Double discount : listAllDiscounts){
					listCarPrices.add(car.getPricePerDay() * discount);
				}
//				listAllDiscounts.add(MAX_DISCOUNT_COEFFICIENT);
			}

			request.getSession().setAttribute(SELECTED_CAR_ID_PARAM, id);
			request.getSession().setAttribute(SELECTED_CAR_PARAM, car);
			request.getSession().setAttribute(SELECTED_CAR_CLASS_PARAM, car.getCarClassType());
			request.getSession().setAttribute(CAR_PRICES_PARAM, listCarPrices);

			request.setAttribute(PROCESS_REQUEST, FORWARD_VALUE);

			LOG.debug(EXECUTE_ENDS);

			return PageName.VIEW_CAR;

		} catch (ServiceException ex) {
			throw new CommandException(ex);
		}
	}
}
