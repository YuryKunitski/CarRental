package by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceimpl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ConnectionPoolException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.CarDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.CarClassType;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceConstant;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.ServiceFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.CarService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CarServiceImpl implements CarService {

	private static final Logger LOG = LogManager.getLogger(CarServiceImpl.class.getName());
	private final CarDAO CAR_DAO = DAOFactory.getInstance().getCarDAO();

	/**
	 * Inserting a car into the database
	 *
	 * @param car which insert into database
	 * @throws ServiceException
	 */
	@Override
	public void insertCar(Car car) throws ServiceException {
		LOG.debug(ServiceConstant.SERVICE_INSERT_CAR_STARTS_MSG);
		try {
			CAR_DAO.insertCar(car);
			LOG.debug(ServiceConstant.SERVICE_INSERT_CAR_ENDS_MSG);
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Delete a car by id
	 *
	 * @param carId car's id
	 * @throws ServiceException delete a car by id
	 */
	@Override
	public void deleteCar(int carId) throws ServiceException {
		LOG.debug(ServiceConstant.SERVICE_DELETE_CAR_STARTS_MSG);
		try {
			CAR_DAO.deleteCarById(carId);
			LOG.debug(ServiceConstant.SERVICE_DELETE_CAR_ENDS_MSG);
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/*
	 *Take car by id
	 *
	 * @param id car's id
	 * @return car
	 * @throws ServiceException  error take car by id
	 */
	@Override
	public Car takeCarById(int id) throws ServiceException {
		LOG.debug(ServiceConstant.SERVICE_TAKE_CAR_BY_ID_STARTS_MSG);
		Car car = null;
		try {
			car = CAR_DAO.takeCarById(id);
			LOG.debug(ServiceConstant.SERVICE_TAKE_CAR_BY_ID_ENDS_MSG);
			return car;
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Getting all cars of certain class
	 *
	 * @param classType car class
	 * @return list of cars of certain class
	 * @throws ServiceException error getting all cars of certain class
	 */
	@Override
	public List<Car> takeCarsByClass(CarClassType classType, int pageNumber, int carsOnPage) throws ServiceException {
		LOG.debug(ServiceConstant.SERVICE_TAKE_CARS_BY_CLASS_START_MSG);
		int startPage = carToStartPage(pageNumber, carsOnPage);
		List<Car> cars = null;
		try {
			cars = CAR_DAO.takeCarsByClass(classType, startPage, carsOnPage);
			LOG.debug(ServiceConstant.SERVICE_TAKE_CARS_BY_ClASS_END_MSG);
			return cars;
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Getting a list of all unused cars on some free time interval by class
	 *
	 * @param dateFrom date and time of car rental by user from
	 * @param dateTo   date and time of car rental by user to
	 * @return list of free cars
	 * @throws ServiceException
	 */
	@Override
	public List<Car> takeUnusedCarsByDate(Date dateFrom, Date dateTo,
	                                              int pageNumber, int carsOnPage) throws ServiceException {
		LOG.debug(ServiceConstant.SERVICE_TAKE_CARS_BY_TYPE_AND_DATE_START_MSG);
		int startPage = carToStartPage(pageNumber, carsOnPage);
		try {
			List<Car> cars = CAR_DAO.takeUnusedCars(dateFrom, dateTo, startPage, carsOnPage);
			LOG.debug(ServiceConstant.SERVICE_TAKE_CARS_BY_TYPE_AND_DATE_END_MSG);
			return cars;
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Getting a list of unused cars by class on some free time interval by class
	 *
	 * @param dateFrom date and time of car rental by user from
	 * @param dateTo   date and time of car rental by user to
	 * @return list of free cars
	 * @throws ServiceException
	 */
	@Override
	public List<Car> takeUnysedCarsByClassAndDate(CarClassType classType, String dateFrom, String dateTo,
	                                        int pageNumber, int carsOnPage) throws ServiceException {
		LOG.debug(ServiceConstant.SERVICE_TAKE_CARS_BY_TYPE_AND_DATE_START_MSG);
		int startPage = carToStartPage(pageNumber, carsOnPage);
		try {
			List<Car> cars = CAR_DAO.takeUnUsedCarsByClass(dateFrom, dateTo, classType, startPage, carsOnPage);

			LOG.debug(ServiceConstant.SERVICE_TAKE_CARS_BY_TYPE_AND_DATE_END_MSG);

			return cars;
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Get all cars
	 *
	 * @param pageNumber number of page
	 * @param carsOnPage count cars on page
	 * @return list of all cars
	 * @throws ServiceException error get all cars
	 */
	@Override
	public List<Car> takeAllCars(int pageNumber, int carsOnPage) throws ServiceException {

		LOG.debug(ServiceConstant.SERVICE_TAKE_ALL_CARS_STARTS_MSG);

		int startPage = carToStartPage(pageNumber, carsOnPage);

		try {
			List<Car> cars = CAR_DAO.takeAllCars(startPage, carsOnPage);

			LOG.debug(ServiceConstant.SERVICE_TAKE_ALL_CARS_ENDS_MSG);

			return cars;
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Getting all car classes
	 *
	 * @return list of car classes
	 * @throws ServiceException
	 */
	@Override
	public List<CarClassType> takeCarClass() throws ServiceException {

		LOG.debug(ServiceConstant.SERVICE_TAKE_CAR_CLASS_STARTS_MSG);

		try {
			List<CarClassType> carClasses = CAR_DAO.takeCarClass();

			LOG.debug(ServiceConstant.SERVICE_TAKE_CAR_CLASS_ENDS_MSG);

			return carClasses;

		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Getting all the free cars of a certain type in a certain time period
	 *
	 * @param carClassType     car class
	 * @param dateFrom date and time of car rental by user from
	 * @param dateTo   date and time of car rental by user to
	 * @return all the free cars of a certain type in a certain time period
	 * @throws ServiceException error getting all the free cars of a certain type in a certain time period
	 */
	public List<Car> takeCarsByClassAndDate(CarClassType carClassType, String dateFrom, String dateTo,
	                                        int pageNumber, int carsOnPage) throws ServiceException {
		LOG.debug(ServiceConstant.SERVICE_TAKE_CARS_BY_TYPE_AND_DATE_START_MSG);
		int startPage = carToStartPage(pageNumber, carsOnPage);
		try {
			List<Car> cars = CAR_DAO.takeUnUsedCarsByClass(dateFrom, dateTo, carClassType, startPage, carsOnPage);
			LOG.debug(ServiceConstant.SERVICE_TAKE_CARS_BY_TYPE_AND_DATE_END_MSG);
			return cars;
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}


	/**
	 * Counting pages of amount all cars
	 *
	 * @param amountCarsOnPage count cars on page
	 * @return page amount
	 * @throws ServiceException counting pages of amount all cars
	 */
	@Override
	public int countPageAmountAllCars(int amountCarsOnPage) throws ServiceException {

		LOG.debug(ServiceConstant.SERVICE_COUNT_PAGE_AMOUNT_ALL_CARS_STARTS_MSG);

		int pageAmount = 0;

		try {
			int carsAmount = CAR_DAO.countAllCars();

			if (carsAmount%amountCarsOnPage != 0) {
				pageAmount = (carsAmount / amountCarsOnPage) + 1;

			} else {
				pageAmount = (carsAmount / amountCarsOnPage);
			}
			LOG.debug(ServiceConstant.SERVICE_COUNT_PAGE_AMOUNT_ALL_CARS_ENDS_MSG);

			return pageAmount;

		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Counting pages of amount all classes of cars
	 *
	 * @param amountCarsOnPage count cars on page
	 * @return page amount
	 * @throws ServiceException counting pages of amount all classes of cars
	 */
	@Override
	public int countPageAmountClassCars(CarClassType carClassType, int amountCarsOnPage) throws ServiceException {

		LOG.debug(ServiceConstant.SERVICE_COUNT_PAGE_AMOUNT_TYPE_STARTS_MSG);

		int pageAmount = 0;
		int carsAmount = 0;
		try {
			carsAmount = CAR_DAO.countAllClassCars(carClassType);
			if (carsAmount%amountCarsOnPage != 0) {
				pageAmount = (carsAmount / amountCarsOnPage) + 1;
			} else {
				pageAmount = (carsAmount / amountCarsOnPage);
			}
			LOG.debug(ServiceConstant.SERVICE_COUNT_PAGE_AMOUNT_TYPE_ENDS_MSG);

			return pageAmount;
		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Counting pages of amount unused class of cars
	 *
	 * @param amountCarsOnPage count cars on page
	 * @return page amount
	 * @throws ServiceException counting pages of amount unused class of cars
	 */
	@Override
	public int countPageAmountUnusedClassCars(String dateFrom, String dateTo, CarClassType carClassType, int amountCarsOnPage,
	                                          int pageNumber) throws ServiceException {

		LOG.debug(ServiceConstant.SERVICE_COUNT_PAGE_AMOUNT_UNUSED_CARS_MSG_START);

		int pageAmount = 0;
		int carsAmount = 0;
		try {
			carsAmount = CAR_DAO.takeUnUsedCarsByClass(dateFrom, dateTo, carClassType, pageNumber, amountCarsOnPage).size();

			if (carsAmount%amountCarsOnPage != 0) {
				pageAmount = (carsAmount / amountCarsOnPage) + 1;
			} else {
				pageAmount = (carsAmount / amountCarsOnPage);
			}
			LOG.debug(ServiceConstant.SERVICE_COUNT_PAGE_AMOUNT_UNUSED_CARS_MSG_END);

			return pageAmount;

		} catch (DAOException ex) {
			throw new ServiceException(ex);
		}
	}

	/**
	 * Working with pages
	 *
	 * @param pageNumber number of page
	 * @param carsOnPage count of car on page
	 * @return count of car to start page
	 */
	private int carToStartPage(int pageNumber, int carsOnPage) {
		LOG.debug(ServiceConstant.SERVICE_CAR_TO_START_PAGE_STARTS_MSG);
		LOG.debug(ServiceConstant.SERVICE_CAR_TO_START_PAGE_ENDS_MSG);

		return ((pageNumber * carsOnPage) - carsOnPage);
	}



	public static void main(String[] args) {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			connectionPool.initConnectionPool();
		} catch (ConnectionPoolException e) {
			e.printStackTrace();
		}
//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		CarService carService = ServiceFactory.getInstance().getCarService();
//
		try {
		List<Car> carList = carService.takeAllCars(1,9);
			System.out.println(carList.size());
//			System.out.println(carService.takeUnysedCarsByClassAndDate(CarClassType.ECONOM, new  Date(format.parse( "2019-06-10").getTime()),
//					new Date(format.parse( "2019-06-16").getTime()), 15, 1 ).toString());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
//		catch (ParseException e) {
//			e.printStackTrace();
//		}
	}

}
