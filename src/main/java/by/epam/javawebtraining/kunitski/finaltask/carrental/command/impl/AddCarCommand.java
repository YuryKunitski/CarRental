package by.epam.javawebtraining.kunitski.finaltask.carrental.command.impl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.command.Command;
import by.epam.javawebtraining.kunitski.finaltask.carrental.command.PageName;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.CommandException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.CarClassType;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.Validator;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.CarService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * The command add car in "Car Rental"
 */
public class AddCarCommand implements Command {

	private static final Logger LOG = LogManager.getLogger(AddCarCommand.class.getName());

	private static final String EXECUTE_STARTS_MSG = "AddCarCommand : execute : starts";
	private static final String EXECUTE_ENDS_MSG = "AddCarCommand : execute : ends";

	private static final String CAR_MODEL_PARAM = "carModel";
	private static final String CAR_YEAR_PARAM = "carYear";
	private static final String CAR_CLASS_PARAM = "carClass";
	private static final String CAR_PRICE_PARAM = "carPrice";
	private static final String IMAGE_PARAM = "image";
	private static final String CAR_SUCCESSFUL_ADDED_PARAM = "carSuccessfulAdded";
	private static final String CAR_MODEL_VALID = "validCarModel";
	private static final String CAR_CLASS_VALID = "validCarClass";
	private static final String CAR_YEAR_VALID = "validCarYear";
	private static final String CAR_PRICE_VALID = "validCarPrice";
	private static final String CAR_IMAGE_VALID = "validCarImage";
	private static final String VALID_PARAMETERS_STARTS_MSG = "AddCarCommand : validParameters : starts";
	private static final String VALID_PARAMETERS_ENDS_MSG = "AddCarCommand : validParameters : ends";

	private static final String PROCESS_REQUEST_PARAM = "processRequest";

	private static final String FORWARD_VALUE = "forward";
	private static final String REDIRECT_VALUE = "redirect";

	@Override
	public String execute(HttpServletRequest request) throws CommandException {

		LOG.debug(EXECUTE_STARTS_MSG);

		if (validParameters(request)) {

			request.getSession().setAttribute(CAR_SUCCESSFUL_ADDED_PARAM, true);
			request.setAttribute(PROCESS_REQUEST_PARAM, REDIRECT_VALUE);

			LOG.debug(EXECUTE_ENDS_MSG);

			return PageName.ADMIN_SUCCESS;

		} else {
			request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);

			LOG.debug(EXECUTE_ENDS_MSG);

			return PageName.ADD_CAR;
		}
	}

	/**
	 * Validation input data to add car
	 *
	 * @param request
	 * @return
	 * @throws CommandException
	 */
	private boolean validParameters(HttpServletRequest request) throws CommandException {

		LOG.debug(VALID_PARAMETERS_STARTS_MSG);

		Part image = null;
		String imageName = null;

		String carModel = request.getParameter(CAR_MODEL_PARAM);
		String carYear = request.getParameter(CAR_YEAR_PARAM);
		String carClass = request.getParameter(CAR_CLASS_PARAM);
		double carPrice = Double.parseDouble(request.getParameter(CAR_PRICE_PARAM));

		try {
			image = request.getPart(IMAGE_PARAM);
			imageName = image.getSubmittedFileName();

		} catch (ServletException | IOException ex) {
			throw new CommandException(ex);
		}

		Validator validator = Validator.getInstance();
		int countFailedValidations = 0;

		if (!validator.validateCarModel(carModel)) {
			request.setAttribute(CAR_MODEL_VALID, false);
			countFailedValidations++;
		}
		if (!validator.validateCarYear(carYear)) {
			request.setAttribute(CAR_YEAR_VALID, false);
			countFailedValidations++;
		}
		if (!validator.validateCarClass(carClass)) {
			request.setAttribute(CAR_CLASS_VALID, false);
			countFailedValidations++;
		}
		if (!validator.validateDamagePrice(carPrice)) {
			request.setAttribute(CAR_PRICE_VALID, false);
			countFailedValidations++;
		}

		if (!validator.validateCarImage(imageName)) {
			request.setAttribute(CAR_IMAGE_VALID, false);
			countFailedValidations++;
		}

		if (countFailedValidations > 0) {
			LOG.debug(VALID_PARAMETERS_ENDS_MSG);
			return false;

		} else {
			try {
				CarService service = ServiceFactory.getInstance().getCarService();
				Car car = new Car();

				String realpath = request.getServletContext().getRealPath("");
				File uploads = new File(realpath);
				File file = new File(uploads, imageName);

				try (InputStream input = image.getInputStream()) {
					Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					throw new CommandException(e);
				}

				car.setCarModel(carModel);
				car.setCarClassType(CarClassType.valueOf(carClass.toUpperCase()));
				car.setYearIssue(carYear);
				car.setPricePerDay(carPrice);
				car.setImage(imageName);
				service.insertCar(car);

				return true;

			} catch (ServiceException ex) {
				throw new CommandException(ex);
			}
		}

	}
}
