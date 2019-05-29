package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daoimpl;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ConnectionPoolException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.DAOException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.CarDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.entity.car.Car;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class CarDAOImplTest {

	private static ConnectionPool connectionPool;
	private static CarDAO carDAO;

	@BeforeClass
	public static void init() throws ConnectionPoolException {
		connectionPool = ConnectionPool.getInstance();
		connectionPool.initConnectionPool();
		carDAO = DAOFactory.getInstance().getCarDAO();
	}

	@AfterClass
	public static void dispose() throws ConnectionPoolException {
		connectionPool.dispose();
	}

	@Test(expected = DAOException.class)
	public void insertCar() throws DAOException {
		Car car = new Car();

		carDAO.insertCar(car);
	}

	@Test
	public void takeCarById() throws DAOException {
		Car car = carDAO.takeCarById(15);
		String expectModel = "mazda_6";
		String expectYear = "2018";
		double expectPrice = 120.0;

		assertEquals(expectModel, car.getCarModel());
		assertEquals(expectYear, car.getYearIssue());
		assertEquals(expectPrice, car.getPricePerDay(), 0.0000001);
	}
}