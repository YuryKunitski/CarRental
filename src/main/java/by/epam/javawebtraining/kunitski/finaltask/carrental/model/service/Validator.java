package by.epam.javawebtraining.kunitski.finaltask.carrental.model.service;

import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.CarClassType;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Data validator
 * <p>
 * Validates such data entered by users, such as username, password, name, passport data, phone number,
 * e-mail, car model, car intake year, car rental date.
 * </p>
 */
public class Validator {
	private static final Validator instance = new Validator();

	private static final int LOGIN_LENGTH = 20;
	private static final int EMAIL_LENGTH = 60;
	private static final int NAME_LENGTH = 25;
	private static final int PHONE_LENGTH = 30;
	private static final int PASSPORT_LENGTH = 15;


	private static final String PATTERN_LOGIN = "[A-Za-z0-9_]+";
	private static final String PATTERN_PASSWORD = "\\S+";
	private static final String PATTERN_EMAIL = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$";
	private static final String PATTERN_NAME = "[A-Za-zА-Яа-я-]+";
	private static final String PATTERN_PHONE = "[0-9-+\\s()]+";
	private static final String PATTERN_PASSPORT = "[A-Z0-9]+";
	private static final String PATTERN_DATE = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])";
	private static final String PATTERN_PRICE = "(^[0-9]+\\.([0-9][0-9]|[0-9])$)|(^[0-9]+$)";
	private static final String PATTERN_ORDER_INFO = "^\\S[^<>]*";

	private static final String PATTERN_CAR_YEAR = "[1-3][0-9]{3}";
	private static final String PATTERN_CAR_MODEL = "[A-Za-z0-9_]+";

	private Validator() {

	}

	public static Validator getInstance() {
		return instance;
	}

	/**
	 * Validation login
	 *
	 * @param login user's login
	 * @return true - login is correct, false is incorrect
	 */
	public boolean validateLogin(String login) {

		boolean result = false;

		if (login != null && !login.isEmpty()) {
			Pattern pattern = Pattern.compile(PATTERN_LOGIN);
			Matcher matcher = pattern.matcher(login);
			boolean match = matcher.matches();

			result = (login.length() <= LOGIN_LENGTH) && match;
		}

		return result;
	}

	/**
	 * Validation password
	 *
	 * @param password user's password
	 * @return true - password is correct, false is incorrect
	 */
	public boolean validatePassword(String password) {

		boolean result = false;

		if (password != null && !password.isEmpty()) {
			Pattern pattern = Pattern.compile(PATTERN_PASSWORD);
			Matcher matcher = pattern.matcher(password);
			boolean match = matcher.matches();

			result = match;
		}
		return result;
	}

	/**
	 * Verifying password confirmation
	 *
	 * @param password        user's password
	 * @param confirmPassword password confirmation
	 * @return true - password is correct, false is incorrect
	 */
	public boolean validateConfirmationPassword(String password, String confirmPassword) {

		boolean result = false;

		if (password != null && confirmPassword != null) {

			result = password.equals(confirmPassword);
		}
		return result;
	}

	/**
	 * Validation user's email
	 *
	 * @param email user's e-mail
	 * @return true - email is correct, false is incorrect
	 */
	public boolean validateEmail(String email) {

		boolean result = false;

		if (email != null && !email.isEmpty()) {
			Pattern pattern = Pattern.compile(PATTERN_EMAIL);
			Matcher matcher = pattern.matcher(email);
			boolean match = matcher.matches();

			result = (email.length() <= EMAIL_LENGTH) && match;
		}
		return result;
	}

	/**
	 * Validation name or surname
	 *
	 * @param name user's name
	 * @return true - name is correct, false is incorrect
	 */
	public boolean validateName(String name) {

		boolean result = false;

		if (name != null && !name.isEmpty()) {
			Pattern pattern = Pattern.compile(PATTERN_NAME);
			Matcher matcher = pattern.matcher(name);
			boolean match = matcher.matches();

			result = (name.length() <= NAME_LENGTH) && match;
		}
		return result;
	}

	/**
	 * Validation user's phone
	 *
	 * @param phone user's phone
	 * @return true - phone is correct, false is incorrect
	 */
	public boolean validatePhone(String phone) {

		boolean result = false;

		if (phone != null && !phone.isEmpty()) {
			Pattern pattern = Pattern.compile(PATTERN_PHONE);
			Matcher matcher = pattern.matcher(phone);
			boolean match = matcher.matches();

			result = (phone.length() <= PHONE_LENGTH) && match;
		}
		return result;
	}

	/**
	 * Validation passportID
	 *
	 * @param passportID user's passportID
	 * @return true - passportID is correct, false is incorrect
	 */
	public boolean validatePassport(String passportID) {

		boolean result = false;

		if (passportID != null && !passportID.isEmpty()) {
			Pattern pattern = Pattern.compile(PATTERN_PASSPORT);
			Matcher matcher = pattern.matcher(passportID);
			boolean match = matcher.matches();

			result = (passportID.length() <= PASSPORT_LENGTH) && match;
		}
		return result;
	}


	/**
	 * Validation rental dates
	 *
	 * @param firstDate  first date
	 * @param secondDate second date
	 * @return true - the first date is before the second, and the current date is before the first
	 */
	public boolean validateDate(String firstDate, String secondDate) {


		if (firstDate == null || secondDate == null) {
			return false;
		}

		if (firstDate.isEmpty() || secondDate.isEmpty()) {
			return false;
		}

		Pattern pattern = Pattern.compile(PATTERN_DATE);
		Matcher matcher1 = pattern.matcher(firstDate);
		Matcher matcher2 = pattern.matcher(secondDate);
		boolean match1 = matcher1.matches();
		boolean match2 = matcher2.matches();

		if (!match1) {
			return false;
		}

		if (!match2) {
			return false;
		}

		LocalDate date1 = LocalDate.parse(firstDate);
		LocalDate date2 = LocalDate.parse(secondDate);
		LocalDate dateNow = LocalDate.now();

		return (date1.isBefore(date2) && dateNow.isBefore(date1));
	}

	/**
	 * Validation the start date when user take car
	 *
	 * @param rentStartDate date when user take car
	 * @return true - current date is before the rentStartDate
	 */
	public boolean validateStartAndEndDate(Date rentStartDate) {

		boolean result = false;

		if (rentStartDate != null) {

			LocalDateTime localDateTime = rentStartDate.toLocalDate().atStartOfDay();
			LocalDateTime dateNow = LocalDateTime.now();

			result = dateNow.isBefore(localDateTime);
		}
		return result;
	}


	/**
	 * Validation damage price
	 *
	 * @param damagePrice damage price
	 * @return true - price is correct, false - price is not correct
	 */
	public boolean validateDamagePrice(Double damagePrice) {

		boolean result = false;

		if (damagePrice != null) {
			Pattern pattern = Pattern.compile(PATTERN_PRICE);
			Matcher matcher = pattern.matcher(damagePrice.toString());

			result = matcher.matches();
		}

		return result;
	}

	/**
	 * Validation the order info
	 *
	 * @param orderInfo order info
	 * @return true - order info is correct, false - order info is not correct
	 */
	public boolean validateOrderInfo(String orderInfo) {

		boolean result = false;

		if (orderInfo != null) {

			Pattern pattern = Pattern.compile(PATTERN_ORDER_INFO);
			Matcher matcher = pattern.matcher(orderInfo);

			result = matcher.matches();
		}

		return result;
	}

	/**
	 * Validation the year issue of car
	 *
	 * @param year issue of car
	 * @return true - issue of car is correct, false - issue of car is not correct
	 */
	public boolean validateCarYear(String year) {

		boolean result = false;

		if (year != null && (year.length() == 4)) {


			Pattern pattern = Pattern.compile(PATTERN_CAR_YEAR);
			Matcher matcher = pattern.matcher(year);

			result = matcher.matches();
		}

		return result;
	}


	/**
	 * Validation model of car
	 *
	 * @param model model of car
	 * @return true - model of car is correct, false - model of car is not correct
	 */
	public boolean validateCarModel(String model) {

		boolean result = false;

		if (model != null && !model.isEmpty()) {

			Pattern pattern = Pattern.compile(PATTERN_CAR_MODEL);
			Matcher matcher = pattern.matcher(model);

			result =  matcher.matches();
		}

		return result;
	}


	/**
	 * Validation the car class
	 *
	 * @param carClass issue of car
	 * @return true - issue of car is correct, false - issue of car is not correct
	 */
	public boolean validateCarClass(String carClass) {

		boolean result = false;
		CarClassType[] classTypes = CarClassType.values();

		if (carClass != null) {

			for (CarClassType c : classTypes) {

				if (carClass.equalsIgnoreCase(c.toString())) {
					result = true;
				}
			}
		}

		return result;
	}

	/**
	 * Validation the car image
	 *
	 * @param imageName issue of car
	 * @return true - image of car is correct, false - image of car is not correct
	 */
	public boolean validateCarImage(String imageName) {

		boolean result = false;

		if (imageName != null && !imageName.isEmpty()){
			result = true;
		}

		return result;
	}

}
