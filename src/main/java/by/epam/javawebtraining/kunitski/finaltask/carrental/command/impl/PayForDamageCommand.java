package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.OrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command to pay for damage to the car by the user
 */
public class PayForDamageCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(PayCommand.class.getName());

	private static final String EXECUTE_STARTS_MSG = "PayForDamageCommand : execute : starts";
	private static final String EXECUTE_ENDS_MSG = "PayForDamageCommand : execute : ends";

	private static final String SELECTED_ORDER_ID_PARAM = "selectedOrderId";
	private static final String SUCCESSFUL_PAYMENT_PARAM = "successfulPaymentForDmg";
	private static final String PROCESS_REQUEST_PARAM = "processRequest";

	private static final String STATUS_VALUE = "closed";
	private static final String REDIRECT_VALUE = "redirect";

	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		LOG.debug(EXECUTE_STARTS_MSG);

		int orderId = (Integer) request.getSession().getAttribute(SELECTED_ORDER_ID_PARAM);
		OrderService service = ServiceFactory.getInstance().getOrderService();

		try {
			service.updateStatusWithOutReason(STATUS_VALUE, orderId);

			request.getSession().setAttribute(SUCCESSFUL_PAYMENT_PARAM, true);
			request.setAttribute(PROCESS_REQUEST_PARAM, REDIRECT_VALUE);

			LOG.debug(EXECUTE_ENDS_MSG);

			return PageName.USER_SUCCESS;

		} catch (ServiceException ex) {
			throw new CommandException(ex);
		}
	}
}
