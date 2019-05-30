package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.Order;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.OrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The command receives a list of orders for the user.
 */
public class ViewOrdersUserCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(ViewOrdersUserCommand.class.getName());

	private static final String EXECUTE_STARTS_MSG = "ViewOrdersUserCommand : execute : starts";
	private static final String EXECUTE_ENDS_MSG = "ViewOrdersUserCommand : execute : ends";

	private static final int DEFAULT_PAGE_NUMBER = 1;
	private static final String FORWARD_VALUE = "forward";

	private static final String USER_PARAM = "user";
	private static final String TOTAL_BILL_PARAM = "totalBill";
	private static final String NO_ORDERS_PARAM = "noOrders";
	private static final String ORDERS_PARAM = "orders";
	private static final String PAGE_NUMBER_PARAM = "pageNumber";
	private static final String PROCESS_REQUEST = "processRequest";


	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		LOG.debug(EXECUTE_STARTS_MSG);

		int amountPages = 0;
		int pageNumber = DEFAULT_PAGE_NUMBER;

		if (request.getParameter(PAGE_NUMBER_PARAM) != null) {
			pageNumber = Integer.parseInt(request.getParameter(PAGE_NUMBER_PARAM));
		}

		User user = (User) request.getSession(true).getAttribute(USER_PARAM);
		OrderService service = ServiceFactory.getInstance().getOrderService();
		List<Order> orders = null;

		try {
			if (user != null) {
				orders = service.findOrdersByUserId(user.getUserID());
				for (Order order: orders){
					order.setTotalBill(order.getTotalBill());
				}
				if (orders.size() == 0) {
					request.setAttribute(NO_ORDERS_PARAM, true);
				}

				request.getSession().setAttribute(ORDERS_PARAM, orders);
			}

			request.setAttribute(PROCESS_REQUEST, FORWARD_VALUE);

			LOG.debug(EXECUTE_ENDS_MSG);

			return PageName.USER_ORDERS;

		} catch (ServiceException ex) {
			throw new CommandException(ex);
		}
	}
}
