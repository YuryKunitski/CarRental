package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The command to log out the user
 */
public class LogOutCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(LogOutCommand.class.getName());

	private static final String EXECUTE_STARTS_MSG = "LogOutCommand : execute : starts";
	private static final String EXECUTE_ENDS_MSG = "LogOutCommand : execute : ends";

	private static final String PROCESS_REQUEST_PARAM = "processRequest";

	private static final String FORWARD_VALUE = "forward";

	@Override
	public String execute(HttpServletRequest request) {

		LOG.debug(EXECUTE_STARTS_MSG);

		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
		}
		request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

		LOG.debug(EXECUTE_ENDS_MSG);

		return PageName.INDEX_PAGE;
	}
}
