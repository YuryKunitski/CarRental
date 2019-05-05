package by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.CarClassType;
import com.google.protobuf.ServiceException;

import java.sql.Date;
import java.util.List;

public interface CarService {

	/**
	 * Inserting a car into the database
	 *
	 * @param car which insert into database
	 * @throws DAOException
	 */
	 void insertCar(Car car) throws ServiceException;

	/**
	 * Delete a car by id
	 *
	 * @param carId car's id
	 * @throws DAOException delete a car by id
	 */
	 void deleteCar(int carId) throws ServiceException;

	/*
	 *Take car by id
	 *
	 * @param id car's id
	 * @return car
	 * @throws DAOException  error take car by id
	 */
	 Car takeCarById(int id) throws ServiceException;

	/**
	 * Getting all cars of certain class
	 *
	 * @param classType car class
	 * @return list of cars of certain class
	 * @throws DAOException error getting all cars of certain class
	 */
	 List<Car> takeCarsByClass(CarClassType classType, int pageNumber, int carsOnPage) throws ServiceException;

	/**
	 * Getting a list of all unused cars on some free time interval by class
	 *
	 * @param dateFrom date and time of car rental by user from
	 * @param dateTo   date and time of car rental by user to
	 * @return list of free cars
	 * @throws DAOException
	 */
	 List<Car> takeUnusedCarsByDate(Date dateFrom, Date dateTo,
	                                              int pageNumber, int carsOnPage) throws ServiceException;

	/**
	 * Getting a list of unused cars by class on some free time interval by class
	 *
	 * @param dateFrom date and time of car rental by user from
	 * @param dateTo   date and time of car rental by user to
	 * @return list of free cars
	 * @throws DAOException
	 */
	 List<Car> takeUnysedCarsByClassAndDate(CarClassType classType, Date dateFrom, Date dateTo,
	                                        int pageNumber, int carsOnPage) throws ServiceException;

	/**
	 * Get all cars
	 *
	 * @param pageNumber number of page
	 * @param carsOnPage count cars on page
	 * @return list of all cars
	 * @throws DAOException error get all cars
	 */
	 List<Car> takeAllCars(int pageNumber, int carsOnPage) throws ServiceException;


}
