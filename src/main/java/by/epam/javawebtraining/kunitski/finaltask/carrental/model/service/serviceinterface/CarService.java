package by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ServiceException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.CarClassType;

import java.util.List;

public interface CarService {

	/**
	 * Inserting a car into the database
	 *
	 * @param car which insert into database
	 * @throws ServiceException
	 */
	 void insertCar(Car car) throws ServiceException;

	/**
	 * Delete a car by id
	 *
	 * @param carId car's id
	 * @throws ServiceException delete a car by id
	 */
	 void deleteCar(int carId) throws ServiceException;

	/*
	 *Take car by id
	 *
	 * @param id car's id
	 * @return car
	 * @throws ServiceException  error take car by id
	 */
	 Car takeCarById(int id) throws ServiceException;

	/**
	 * Getting all cars of certain class
	 *
	 * @param classType car class
	 * @return list of cars of certain class
	 * @throws ServiceException error getting all cars of certain class
	 */
	 List<Car> takeCarsByClass(CarClassType classType, int pageNumber, int carsOnPage) throws ServiceException;

	/**
	 * Getting a list of all unused cars on some free time interval by class
	 *
	 * @param dateFrom date and time of car rental by user from
	 * @param dateTo   date and time of car rental by user to
	 * @return list of free cars
	 * @throws ServiceException
	 */
	 List<Car> takeUnusedCarsByDate(String dateFrom, String dateTo,
	                                              int pageNumber, int carsOnPage) throws ServiceException;

	/**
	 * Getting a list of unused cars by class on some free time interval by class
	 *
	 * @param dateFrom date and time of car rental by user from
	 * @param dateTo   date and time of car rental by user to
	 * @return list of free cars
	 * @throws ServiceException
	 */
	 List<Car> takeUnysedCarsByClassAndDate(CarClassType classType, String dateFrom, String dateTo,
	                                        int pageNumber, int carsOnPage) throws ServiceException;

	/**
	 * Get all cars
	 *
	 * @param pageNumber number of page
	 * @param carsOnPage count cars on page
	 * @return list of all cars
	 * @throws ServiceException error get all cars
	 */
	 List<Car> takeAllCars(int pageNumber, int carsOnPage) throws ServiceException;

	/**
	 * Getting all car classes
	 *
	 * @return list of car classes
	 * @throws ServiceException
	 */
	public List<CarClassType> takeCarClass() throws ServiceException;

	/**
	 * Counting pages of amount all cars
	 *
	 * @param amountCarsOnPage count cars on page
	 * @return page amount
	 * @throws ServiceException counting pages of amount all cars
	 */
	int countPageAmountAllCars(int amountCarsOnPage) throws ServiceException;

	/**
	 * Counting pages of amount all car classes
	 *
	 * @param amountCarsOnPage count cars on page
	 * @return page amount
	 * @throws ServiceException counting pages of amount all car classes
	 */
	public int countPageAmountClassCars(CarClassType carClassType, int amountCarsOnPage) throws ServiceException;

	/**
	 * Counting pages of amount unused cars
	 *
	 * @param amountCarsOnPage count cars on page
	 * @return page amount
	 * @throws ServiceException counting pages of amount unused cars
	 */
	public int countPageAmountUnusedCars(String dateFrom, String dateTo, int amountCarsOnPage) throws ServiceException;

	/**
	 * Counting pages of amount unused class of cars
	 *
	 * @param amountCarsOnPage count cars on page
	 * @return page amount
	 * @throws ServiceException counting pages of amount unused class of cars
	 */
	public int countPageAmountUnusedClassCars(String dateFrom, String dateTo, CarClassType carClassType, int amountCarsOnPage) throws ServiceException;


}
