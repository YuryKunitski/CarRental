package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.Order;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.Validator;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.OrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class UpdateDamagePrice implements Command {

	private static final Logger LOG = LogManager.getLogger(UpdateDamagePrice.class.getName());

	private static final String EXECUTE_STARTS = "UpdateDamagePrice : execute : starts";
	private static final String EXECUTE_ENDS = "UpdateDamagePrice : execute : ends";

	private static final String ORDER_ID_PARAM = "selectedOrderId";
	private static final String SELECTED_ORDER_PARAM = "selectedOrder";
	private static final String ORDER_STATUS_PARAM = "statusOrder";
	private static final String ORDER_INFO_PARAM = "order-info";
	private static final String INVALID_INFO = "invalidInfo";
	private static final String DAMAGE_PRICE_PARAM = "damage-price";

	private static final String SUCCESSFUL_UPDATED_DMG_PRICE_PARAM = "dmgPriceUpdated";
	private static final String PROCESS_REQUEST = "processRequest";
	private static final String FROM_DATE_PARAM = "fromDate";

	private static final String APPROVED_VALUE = "paid";
	private static final String FORWARD_VALUE = "forward";
	private static final String REDIRECT_VALUE = "redirect";

	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		LOG.debug(EXECUTE_STARTS);

		int orderId = Integer.parseInt(request.getParameter(ORDER_ID_PARAM));
		double damagePrice = Double.parseDouble(request.getParameter(DAMAGE_PRICE_PARAM));
		String status = request.getParameter(ORDER_STATUS_PARAM);
		OrderService service = ServiceFactory.getInstance().getOrderService();
		Validator validator = Validator.getInstance();

		try {
			if (status.equals(APPROVED_VALUE) && validator.validateDamagePrice(damagePrice)) {

				service.updateDamagePriceByOrderId(orderId, damagePrice);

				request.setAttribute(PROCESS_REQUEST, REDIRECT_VALUE);
				request.setAttribute(INVALID_INFO, false);

				request.getSession().setAttribute(SUCCESSFUL_UPDATED_DMG_PRICE_PARAM, true);
				request.getSession().setAttribute(FROM_DATE_PARAM, true);
				request.getSession().setAttribute(ORDER_ID_PARAM, orderId);

				LOG.debug(EXECUTE_ENDS);

				return PageName.ADMIN_SUCCESS;

			} else {

				request.setAttribute(ORDER_ID_PARAM, orderId);

				Order order = service.takeAdminOrderByOrderId(orderId);

				request.setAttribute(SELECTED_ORDER_PARAM, order);
				request.setAttribute(PROCESS_REQUEST, FORWARD_VALUE);
				request.setAttribute(INVALID_INFO, true);

				LOG.debug(EXECUTE_ENDS);

				return PageName.ADMIN_ORDER;
			}
		} catch (ServiceException ex) {
			throw new CommandException(ex);
		}

	}
}
