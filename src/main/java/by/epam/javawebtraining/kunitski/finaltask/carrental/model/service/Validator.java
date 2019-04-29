package by.epam.javawebtraining.kunitski.finaltask.carrental.model.service;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Валидатор данных.
 * <p>
 * <p>
 * Валидатор данных. Валидирует такие данные, введенные пользователем, как
 * логин, пароль, имя, паспортные данные, адрес пользователя, номер телефона, e-mail,
 * марка и модель автомобиля, его номер, год впуска автомобиля, информация о заказе, дату
 * аренды автомобиля.
 * </p>
 */
public class Validator {
	private static final Validator instance = new Validator();

	private static final int LOGIN_LENGTH = 20;
	private static final int EMAIL_LENGTH = 60;
	private static final int NAME_LENGTH = 25;
	private static final int PHONE_LENGTH = 30;
	private static final int PASSPORT_LENGTH = 15;
	private static final int ADDRESS_LENGTH = 70;
	private static final int PLACE_LENGTH = 45;
	private static final int DATE_LENGTH = 16;
	private static final int CAR_INFO_LENGTH = 140;

	private static final String PATTERN_LOGIN = "[A-Za-z0-9_]+";
	private static final String PATTERN_PASSWORD = "\\S+";
	private static final String PATTERN_EMAIL = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}$";
	private static final String PATTERN_NAME = "[A-Za-zА-Яа-я-]+";
	private static final String PATTERN_PHONE = "[0-9-+\\s()]+";
	private static final String PATTERN_PASSPORT = "[A-Z0-9]+";
	private static final String PATTERN_DAMAGE_PRICE = "(^[0-9]+\\.([0-9][0-9]|[0-9])$)|(^[0-9]+$)";
	private static final String PATTERN_ORDER_INFO = "^\\S[^<>]*";
	private static final String PATTERN_CAR_YEAR = "[1-3][0-9]{3}";
//	private static final String PATTERN_CAR_GOV_NUMBER = "[0-9]{4}[A-CEHIKMOPTX]{2}-[0-7]";
//	private static final String PATTERN_CAR_VIN_CODE = "[0-9A-Z]{17}";
	private static final String PATTERN_DATE = "[2][0-9][0-9][0-9]\\-[0-1][0-9]\\-[0-3][0-9].+";
//	private static final String PATTERN_ADDRESS = "[^<>]*";
	private static final String PATTERN_CAR_INFO = "[^<>]*";
//	private static final String PATTERN_PLACES = "[^<>]*";

	private Validator() {

	}

	public static Validator getInstance() {
		return instance;
	}

	/**
	 * Валидация логина.
	 *
	 * @param login логин пользователя
	 * @return true - логин корректен, false - некорректен
	 */
	public boolean validateLogin(String login) {
		Pattern pattern = Pattern.compile(PATTERN_LOGIN);
		Matcher matcher = pattern.matcher(login);
		boolean match = matcher.matches();
		if ((login.length() <= LOGIN_LENGTH) && match && !login.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Валидация пароля.
	 *
	 * @param password пароль пользователя
	 * @return true - пароль корректен, false - некорректен
	 */
	public boolean validatePassword(String password) {
		Pattern pattern = Pattern.compile(PATTERN_PASSWORD);
		Matcher matcher = pattern.matcher(password);
		boolean match = matcher.matches();
		if (match && !password.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Проверка подтверждения пароля.
	 *
	 * @param password        пароль пользователя
	 * @param confirmPassword подтверждение пароля
	 * @return true - пароль подтвержден, false - не подтвержден
	 */
	public boolean validateConfirmationPassword(String password, String confirmPassword) {
		if (password.equals(confirmPassword)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Валидация e-mail пользователя.
	 *
	 * @param email e-mail пользователя
	 * @return true - e-mail корректен, не корректен
	 */
	public boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile(PATTERN_EMAIL);
		Matcher matcher = pattern.matcher(email);
		boolean match = matcher.matches();
		if ((email.length() <= EMAIL_LENGTH) && match && !email.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Валидация фамилии пользователя
	 *
	 * @param lastName фамилия пользвателя
	 * @return true - фамилия корректна, false - не корректна
	 */
	public boolean validateLastName(String lastName) {
		Pattern pattern = Pattern.compile(PATTERN_NAME);
		Matcher matcher = pattern.matcher(lastName);
		boolean match = matcher.matches();
		if ((lastName.length() <= NAME_LENGTH) && match && !lastName.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Валидация имени пользователя
	 *
	 * @param firstName имя пользователя
	 * @return true - имя корректно, false - не корректно
	 */
	public boolean validateFirstName(String firstName) {
		Pattern pattern = Pattern.compile(PATTERN_NAME);
		Matcher matcher = pattern.matcher(firstName);
		boolean match = matcher.matches();
		if ((firstName.length() <= NAME_LENGTH) && match && !firstName.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Валидация отчества пользователя
	 *
	 * @param middleName отчество пользователя
	 * @return true - отчество корректно, false - не корректно
	 */
	public boolean validateMiddleName(String middleName) {
		Pattern pattern = Pattern.compile(PATTERN_NAME);
		Matcher matcher = pattern.matcher(middleName);
		boolean match = matcher.matches();
		if ((middleName.length() <= NAME_LENGTH) && match && !middleName.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Валидация телефона пользователя.
	 *
	 * @param phone телефон пользователя
	 * @return true - телефон корректен, false - не корректен
	 */
	public boolean validatePhone(String phone) {
		Pattern pattern = Pattern.compile(PATTERN_PHONE);
		Matcher matcher = pattern.matcher(phone);
		boolean match = matcher.matches();
		if ((phone.length() <= PHONE_LENGTH) && match && !phone.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Валидация серии и номера паспорта пользователя.
	 *
	 * @param passport серия и номер паспорта пользователя
	 * @return true - серия и номер корректны, false - не корректны
	 */
	public boolean validatePassport(String passport) {
		Pattern pattern = Pattern.compile(PATTERN_PASSPORT);
		Matcher matcher = pattern.matcher(passport);
		boolean match = matcher.matches();
		if ((passport.length() <= PASSPORT_LENGTH) && match && !passport.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Валидация адреса пользователя.
	 *
	 * @param address адрес пользователя
	 * @return true - адрес корректен, false - не корректен
//	 */
//	public boolean validateAddress(String address) {
//		Pattern pattern = Pattern.compile(PATTERN_ADDRESS);
//		if (address != null) {
//			if (address.length() <= ADDRESS_LENGTH) {
//				Matcher matcher = pattern.matcher(address);
//				boolean match = matcher.matches();
//				return match;
//			}
//		}
//		return true;
//	}

	/**
	 * Валидация даты и времени.
	 *
	 * @param firstDate  первая дата
	 * @param secondDate вторая дата
	 * @return true - первая дата предшествует второй, а текущая дата предшествует - первой,
	 * false - наоборот
	 */
	public boolean validateDate(String firstDate, String secondDate) {
		if (firstDate.equals("null null") || (secondDate.equals("null null"))) {
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
		String tFirstDate = firstDate.replace(" ", "T");
		String tSecondDate = secondDate.replace(" ", "T");
		LocalDateTime date1 = LocalDateTime.parse(tFirstDate);
		LocalDateTime date2 = LocalDateTime.parse(tSecondDate);
		LocalDateTime dateNow = LocalDateTime.now();
		if (date1.isBefore(date2) && dateNow.isBefore(date1)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Валидация времени и даты, когда машина была пользователем в пользование.
	 *
	 * @param date дата и время взятия пользоателем автомобиля
	 * @return true - дата и время не предшествуют реальному времени, false - наоборот
	 */
	public boolean validateRealDateFrom(String date) {
		if (date.length() != DATE_LENGTH) {
			return false;
		}
		String tDate = date.replace(" ", "T");
		LocalDateTime parsedDate = LocalDateTime.parse(tDate);
		LocalDateTime dateNow = LocalDateTime.now();
		if (dateNow.isBefore(parsedDate)) {
			return true;
		} else {
			return false;
		}
	}

//	/**
//	 * Валидация места доставки или возврата автомобиля.
//	 *
//	 * @param place место доставки или возврата автомобиля
//	 * @return true - место доставки доставки или воврата корректно,
//	 * false - место доставки или возврата не корректно
//	 */
//	public boolean validatePlace(String place) {
//		if (place == null) {
//			return false;
//		}
//		Pattern pattern = Pattern.compile(PATTERN_PLACES);
//		Matcher matcher = pattern.matcher(place);
//		boolean match = matcher.matches();
//		if (place.length() <= PLACE_LENGTH && !place.isEmpty() && match) {
//			return true;
//		} else {
//			return false;
//		}
//	}

	/**
	 * Валидация цены
	 *
	 * @param damagePrice цена
	 * @return true - цена корректна, false - цена не корректна
	 */
	public boolean validateDamagePrice(Double damagePrice) {
		if (damagePrice >= 1000000) {
			return false;
		}
		Pattern pattern = Pattern.compile(PATTERN_DAMAGE_PRICE);
		Matcher matcher = pattern.matcher(damagePrice.toString());
		boolean match = matcher.matches();
		if (match) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Валидация причины смены статуса заказа
	 *
	 * @param orderInfo причина смены статуса заказа
	 * @return true - причина корректна, false - не корректна
	 */
	public boolean validateOrderInfo(String orderInfo) {
		if (orderInfo.isEmpty() && !(orderInfo.length() <= 140)) {
			return false;
		}
		Pattern pattern = Pattern.compile(PATTERN_ORDER_INFO);
		Matcher matcher = pattern.matcher(orderInfo);
		boolean match = matcher.matches();
		if (!match) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Валидация года выпуска автомобиля
	 *
	 * @param year год выпуска автомобиля
	 * @return true - год выпуска корректен, false - не корректен
	 */
	public boolean validateCarYear(String year) {
		if (year.isEmpty() && !(year.length() == 4)) {
			return false;
		}
		Pattern pattern = Pattern.compile(PATTERN_CAR_YEAR);
		Matcher matcher = pattern.matcher(year);
		boolean match = matcher.matches();
		if (!match) {
			return false;
		} else {
			return true;
		}
	}




	/**
	 * Валидация марки автомобиля
	 *
	 * @param mark марка автомобиля
	 * @return true - марка корректна, false - не корректна
	 */
	public boolean validateMark(String mark) {
		if (mark.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Валидация модели автомобиля
	 *
	 * @param model модель автомобиля
	 * @return true - модель корректна, false - не корректна
	 */
	public boolean validateModel(String model) {
		if (model == null) {
			return false;
		} else if (model.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public boolean validateCarInfo(String info) {
		Pattern pattern = Pattern.compile(PATTERN_CAR_INFO);
		if (info != null) {
			if (info.length() <= CAR_INFO_LENGTH) {
				Matcher matcher = pattern.matcher(info);
				boolean match = matcher.matches();
				return match;
			}
		}
		return true;
	}

}
