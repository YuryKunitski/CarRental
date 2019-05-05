package by.epam.javawebtraining.kunitski.finaltask.carrental.model.service;

import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOFactory;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOStringConstant;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daoimpl.CarDAOImpl;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daoimpl.OrderDAOImpl;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daoimpl.UserDAOImpl;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.CarDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.OrderDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.daointerface.UserDAO;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceimpl.CarServiceImpl;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceimpl.OrderServiceImpl;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceimpl.UserServiceImpl;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.CarService;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.OrderService;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.service.serviceinterface.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ServiceFactory {

	private static final Logger LOG = LogManager.getLogger(ServiceFactory.class.getName());
	private static final ServiceFactory instance = new ServiceFactory();


	private static final UserService USER_SERVICE = new UserServiceImpl();
	private static final CarService CAR_SERVICE = new CarServiceImpl();
	private static final OrderService ORDER_SERVICE = new OrderServiceImpl();

	private ServiceFactory() {
		LOG.debug(ServiceConstant.INIT_SERVICE_FACTORY_MSG);
	}

	public static ServiceFactory getInstance() {
		return instance;
	}

	public UserService getUserService() {
		return USER_SERVICE;
	}

	public CarService getCarService() {
		return CAR_SERVICE;
	}

	public OrderService getOrderService() {
		return ORDER_SERVICE;
	}
}
