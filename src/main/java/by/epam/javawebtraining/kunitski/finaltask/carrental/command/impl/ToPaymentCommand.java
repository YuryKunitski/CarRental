package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The command to go to the order payment page
 */
public class ToPaymentCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(ToPaymentCommand.class.getName());

	private static final String EXECUTE_STARTS_MSG = "ToPaymentCommand : execute : starts";
	private static final String EXECUTE_ENDS_MSG = "ToPaymentCommand : execute : ends";

	private static final String PAYMENT_TYPE_PARAM = "paymentType";
	private static final String PAYMENT_TYPE = "payment-type";

	private static final String PROCESS_REQUEST_PARAM = "processRequest";

	private static final String FORWARD_VALUE = "forward";

	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		LOG.debug(EXECUTE_STARTS_MSG);

		request.getSession().setAttribute(PAYMENT_TYPE_PARAM, request.getParameter(PAYMENT_TYPE));
		request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

		LOG.debug(EXECUTE_ENDS_MSG);

		return PageName.PAYMENT;
	}
}
