package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao;

import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daoimpl.CarDAOImpl;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daoimpl.OrderDAOImpl;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daoimpl.UserDAOImpl;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.CarDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.OrderDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.UserDAO;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DAOFactory {
	private static final Logger LOG = LogManager.getLogger(DAOFactory.class.getName());
	private static final DAOFactory instance = new DAOFactory();


	private static final UserDAO USER_DAO = new UserDAOImpl();
	private static final CarDAO CAR_DAO = new CarDAOImpl();
	private static final OrderDAO ORDER_DAO = new OrderDAOImpl();

	private DAOFactory() {
		LOG.debug(DAOStringConstant.INIT_DAO_FACTORY_MSG);
	}

	public static DAOFactory getInstance() {
		return instance;
	}

	public UserDAO getUserDAO() {
		return USER_DAO;
	}

	public CarDAO getCarDAO() {
		return CAR_DAO;
	}

	public OrderDAO getOrderDAO() {
		return ORDER_DAO;
	}
}
