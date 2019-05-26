package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.user.User;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The command gets user info
 */
public class ViewUserCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(ViewUserCommand.class.getName());

	private static final String EXECUTE_STARTS = "ViewUserCommand : execute : starts";
	private static final String EXECUTE_ENDS = "ViewUserCommand : execute : ends";

	private static final String SELECTED_USER_ID_PARAM = "selectedUserId";
	private static final String SELECTED_USER_PARAM = "selectedUser";
	private static final String PROCESS_REQUEST = "processRequest";

	private static final String FORWARD_VALUE = "forward";

	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		LOG.debug(EXECUTE_STARTS);

		int userId = Integer.parseInt(request.getParameter(SELECTED_USER_ID_PARAM));
		UserService service = ServiceFactory.getInstance().getUserService();

		try {
			User user = service.findUserById(userId);

			request.setAttribute(SELECTED_USER_PARAM, user);
			request.setAttribute(PROCESS_REQUEST, FORWARD_VALUE);

			LOG.debug(EXECUTE_ENDS);

			return PageName.USER;

		} catch (ServiceException ex) {
			throw new CommandException(ex);
		}
	}
}
