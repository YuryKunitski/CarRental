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

public class ViewOrderAdminCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(ViewOrderAdminCommand.class.getName());

	private static final String EXECUTE_STARTS = "ViewOrderAdminCommand : execute : starts";
	private static final String EXECUTE_ENDS = "ViewOrderAdminCommand : execute : ends";

	private static final String SELECTED_ORDER_ID_PARAM = "selectedOrderId";
	private static final String SELECTED_ORDER_PARAM = "selectedOrder";
	private static final String ORDER_ID_PARAM = "orderId";
	private static final String PROCESS_REQUEST_PARAM = "processRequest";

	private static final String FORWARD_VALUE = "forward";

	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		LOG.debug(EXECUTE_STARTS);

		int orderId = Integer.parseInt(request.getParameter(SELECTED_ORDER_ID_PARAM));
		request.setAttribute(ORDER_ID_PARAM, orderId);
		OrderService service = ServiceFactory.getInstance().getOrderService();
		Order order = null;

		try {
			order = service.takeAdminOrderByOrderId(orderId);

			request.setAttribute(SELECTED_ORDER_PARAM, order);
			request.setAttribute(SELECTED_ORDER_ID_PARAM, orderId);
			request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

			LOG.debug(EXECUTE_ENDS);

			return PageName.ADMIN_ORDER;
		} catch (ServiceException ex) {
			throw new CommandException(ex);
		}
	}
}
