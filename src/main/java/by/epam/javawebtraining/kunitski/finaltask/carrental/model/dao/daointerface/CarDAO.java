package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.CarClassType;

import java.sql.Date;
import java.util.List;

public interface CarDAO {

	/**
	 * Inserting a car into the database
	 *
	 * @param car which insert into database
	 * @throws DAOException
	 */
	void insertCar(Car car) throws DAOException;

	/*
	 *Take car by id
	 *
	 * @param id car's id
	 * @return car
	 * @throws DAOException  error take car by id
	 */
	Car takeCarById(int id) throws DAOException;

	/**
	 * Get all cars
	 *
	 * @param startPage number of page
	 * @param toStartPage count cars on page
	 * @return list of all cars
	 * @throws DAOException error get all cars
	 */
	List<Car> takeAllCars(int startPage, int toStartPage) throws DAOException;


	/**
	 * Delete a car by id
	 *
	 * @param carId car's id
	 * @throws DAOException delete a car by id
	 */
	void deleteCarById(int carId) throws DAOException;

	/**
	 * Getting a list of free cars on some free time interval
	 *
	 * @param supposedDateFrom date and time of car rental by user from
	 * @param supposedDateTo   date and time of car rental by user to
	 * @return list of free cars
	 * @throws DAOException
	 */
	List<Car> takeUnusedCars(String supposedDateFrom, String supposedDateTo,
	                         int startPage, int carsOnPage) throws DAOException;

	/**
	 * Getting a list of free cars by class on some free time interval by class
	 *
	 * @param supposedDateFrom date and time of car rental by user from
	 * @param supposedDateTo   date and time of car rental by user to
	 * @return list of free cars
	 * @throws DAOException
	 */
	List<Car> takeUnUsedCarsByClass(String supposedDateFrom, String supposedDateTo, CarClassType carClassType,
	                                int startPage, int carsOnPage) throws DAOException;

	/**
	 * Getting all cars of certain class
	 *
	 * @param carClassType car class
	 * @return list of cars of certain class
	 * @throws DAOException error getting all cars of certain class
	 */
	List<Car> takeCarsByClass(CarClassType carClassType, int startPage, int toStartPage) throws DAOException;

	/**
	 * Getting count of all cars
	 *
	 * @return count of all cars
	 * @throws DAOException
	 */
	public int countAllCars() throws DAOException;

	/**
	 * Getting count of all car classes
	 *
	 * @return count of all car classes
	 * @throws DAOException
	 */
	public int countAllClassCars(CarClassType carClassType) throws DAOException;

	/**
	 * Getting count of all unused cars
	 *
	 * @return count of all unused cars
	 * @throws DAOException
	 */
	public int countUnusedCars(String dateFrom, String dateTo) throws DAOException;

	/**
	 * Getting count of all unused cars by classes
	 *
	 * @return count of all unused cars by classes
	 * @throws DAOException
	 */
	public int countUnusedClassCars(CarClassType carClassType, String dateFrom, String dateTo) throws DAOException;


	/**
	 * Getting all car classes
	 *
	 * @return list of car classes
	 * @throws DAOException
	 */
	public List<CarClassType> takeCarClass() throws DAOException;
}
