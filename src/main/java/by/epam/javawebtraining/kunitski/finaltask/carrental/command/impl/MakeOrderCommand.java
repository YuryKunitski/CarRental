package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.Validator;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.OrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The command for ordering by the user
 */
public class MakeOrderCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(MakeOrderCommand.class.getName());

	private static final String EXECUTE_STARTS_MSG = "MakeOrderCommand : execute : starts";
	private static final String EXECUTE_ENDS_MSG = "MakeOrderCommand : execute : ends";

	private static final String USER_PARAM = "user";
	private static final String SELECTED_CAR_PARAM_ID = "selectedCarId";
	private static final String SUPPOSED_DATE_FROM_PARAM = "supposedDateFrom";
	private static final String SUPPOSED_DATE_TO_PARAM = "supposedDateTo";
	private static final String INV_DATE_PARAM = "invalidDate";
	private static final String ADD_ORDER_FAILED_PARAM = "addOrderFailed";
	private static final String ORDER_SUCCESSFUL_MADE = "orderSuccessfulMade";
	private static final String PROCESS_REQUEST_PARAM = "processRequest";

	private static final String FORWARD_VALUE = "forward";
	private static final String REDIRECT_VALUE = "redirect";

	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		LOG.debug(EXECUTE_STARTS_MSG);

		User user = (User) request.getSession(true).getAttribute(USER_PARAM);
		int userId = user.getUserID();
		int carId = (Integer) request.getSession(true).getAttribute(SELECTED_CAR_PARAM_ID);
		String supposedDateFrom = request.getParameter(SUPPOSED_DATE_FROM_PARAM) ;
		String supposedDateTo = request.getParameter(SUPPOSED_DATE_TO_PARAM) ;
		OrderService service = ServiceFactory.getInstance().getOrderService();
		Validator validator = Validator.getInstance();

		try {
			if (!validator.validateDate(supposedDateFrom, supposedDateTo)) {

				request.setAttribute(INV_DATE_PARAM, true);
				request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

				LOG.debug(EXECUTE_ENDS_MSG);

				return PageName.MAKE_ORDER;
			}


			boolean add = service.addOrder(userId, carId, supposedDateFrom, supposedDateTo);

			if (!add) {
				request.setAttribute(ADD_ORDER_FAILED_PARAM, true);
				request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

				LOG.debug(EXECUTE_ENDS_MSG);

				return PageName.MAKE_ORDER;
			}

			request.getSession().setAttribute(ORDER_SUCCESSFUL_MADE, true);
			request.setAttribute(PROCESS_REQUEST_PARAM, REDIRECT_VALUE);

			LOG.debug(EXECUTE_ENDS_MSG);

			return PageName.USER_SUCCESS;

		} catch (ServiceException ex) {
			throw new CommandException(ex);
		}
	}
}
