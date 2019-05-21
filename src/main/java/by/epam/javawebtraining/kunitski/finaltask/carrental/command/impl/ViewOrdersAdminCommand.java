package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.Order;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.OrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ViewOrdersAdminCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(ViewOrdersAdminCommand.class.getName());

	private static final String EXECUTE_STARTS_MSG = "ViewOrdersAdminCommand : execute : starts";
	private static final String EXECUTE_ENDS_MSG = "ViewOrdersAdminCommand : execute : ends";

	private static final String PAGE_NUMBER_PARAM = "pageNumber";
	private static final String AMOUNT_PAGES_PARAM = "amountPages";
	private static final String NO_ORDERS_PARAM = "noOrders";
	private static final String ORDERS_PARAM = "orders";
	private static final String PROCESS_REQUEST_PARAM = "processRequest";

	private static final int AMOUNT_ORDERS_ON_PAGE = 4;
	private static final int DEFAULT_PAGE_NUMBER = 1;
	private static final String FORWARD_VALUE = "forward";

	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		LOG.debug(EXECUTE_STARTS_MSG);

		int amountPages = 0;
		int pageNumber = DEFAULT_PAGE_NUMBER;
		OrderService service = ServiceFactory.getInstance().getOrderService();

		if (request.getParameter(PAGE_NUMBER_PARAM) != null) {
			pageNumber = Integer.parseInt(request.getParameter(PAGE_NUMBER_PARAM));
		}
		List<Order> orders = null;
		try {
			orders = service.takeAllOrders(pageNumber, AMOUNT_ORDERS_ON_PAGE);

			if (orders.size() == 0) {

				request.setAttribute(NO_ORDERS_PARAM, true);
			}

			amountPages = service.countPageAmountAllOrders(AMOUNT_ORDERS_ON_PAGE);

			request.setAttribute(AMOUNT_PAGES_PARAM, amountPages);
			request.setAttribute(PAGE_NUMBER_PARAM, pageNumber);
			request.getSession(true).setAttribute(ORDERS_PARAM, orders);
			request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

			LOG.debug(EXECUTE_ENDS_MSG);

			return PageName.ADMIN_ORDERS;

		} catch (ServiceException ex) {
			throw new CommandException(ex);
		}
	}
}
