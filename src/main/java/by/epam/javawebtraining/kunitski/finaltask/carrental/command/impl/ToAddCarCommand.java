package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.CarService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The command for add car
 */
public class ToAddCarCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(ToAddCarCommand.class.getName());

	private static final String EXECUTE_STARTS_MSG = "ToAddCarCommand : execute : starts";
	private static final String EXECUTE_ENDS_MSG = "ToAddCarCommand : execute : ends";

	private static final String PROCESS_REQUEST_PARAM = "processRequest";

	private static final String FORWARD_VALUE = "forward";

	@Override
	public String execute(HttpServletRequest request) {

		LOG.debug(EXECUTE_STARTS_MSG);
		request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
		LOG.debug(EXECUTE_ENDS_MSG);

		return PageName.ADD_CAR;

	}
}
