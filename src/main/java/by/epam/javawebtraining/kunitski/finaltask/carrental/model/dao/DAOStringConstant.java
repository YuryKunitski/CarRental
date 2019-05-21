package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao;

public class DAOStringConstant {

	/**
	 * String constants for ConnectionPool
	 */
	public static final String NUMB_FORM_EXC = "NumberFormatException in ConnectionPool";
	public static final String INIT_STARTS_MSG = "ConnectionPool : initConnectionPool : starts";
	public static final String INIT_ENDS_MSG = "ConnectionPool : initConnectionPool : ends";
	public static final String CON_POOL_EXC = "Error in connection pool. ConnectionPoolException";
	public static final String CLASS_NOT_FOUND_EXC = "Can't find database driver class. ConnectionPool";
	public static final String TAKE_CONNECTION_STARTS_MSG = "ConnectionPool : takeConnection : starts";
	public static final String TAKE_CONNECTION_ENDS_MSG = "ConnectionPool : takeConnection : ends";
	public static final String TAKE_CON_EXC = "Error connection to the data source. ConnectionPool";
	public static final String DISPOSE_STARTS_MSG = "ConnectionPool : dispose : starts";
	public static final String DISPOSE_ENDS_MSG = "ConnectionPool : dispose : ends";
	public static final String DISPOSE_CON_EXC = "SQL Exception in disposeConnectionQueue. ConnectionPool";
	public static final String CLOSE_CONNECTION_MSG = "ConnectionPool : closeConnection";
	public static final String CLOSE_CON_EXC = "SQL Exception in closeConnection. ConnectionPool";
	public static final String CLOSE_CONNECTION_QUERY_STARTS_MSG = "ConnectionPool : closeConnectionsQueue : starts";
	public static final String CLOSE_CONNECTION_QUERY_ENDS_MSG = "ConnectionPool : closeConnectionsQueue : ends";
	public static final String IS_CLOSED_EXC = "Attempting to close closed connection. ConnectionPool";
	public static final String REMOVE_EXC = "Error deleting connection from the used connections pool. ConnectionPool";
	public static final String OFFER_EXC = "Error allocation connection in the pool. ConnectionPool";

	/**
	 * String constants for CarDAOdb
	 */
	public static final String DAO_TAKE_CARS_BY_CLASS_STARTS_MSG = "CarDAOdb : takeCarsByClass : starts";
	public static final String DAO_TAKE_UNUSED_CARS_STARTS_MSG = "CarDAOdb : takeUnusedCars : starts";
	public static final String DAO_TAKE_UNUSED_CARS_BY_CLASS_STARTS_MSG = "CarDAOdb : takeUnusedCarsByClass : starts";
	public static final String DAO_TAKE_ALL_CARS_STARTS_MSG = "CarDAOdb : takeAllCars : starts";
	public static final String DAO_INSERT_CAR_STARTS_MSG = "CarDAOdb : insertCar : starts";
	public static final String DAO_DELETE_CAR_BY_ID_STARTS_MSG = "CarDAOdb : deleteCarById : starts";
	public static final String DAO_COUNT_ALL_CARS_STARTS_MSG = "CarDAOdb : countAllCars : starts";
	public static final String DAO_TAKE_CAR_BY_ID_STARTS_MSG = "CarDAOdb : takeCarById : starts";
	public static final String DAO_TAKE_CARS_BY_CLASS_ENDS_MSG = "CarDAOdb : takeCarsByClass : ends";
	public static final String DAO_TAKE_UNUSED_CARS_ENDS_MSG = "CarDAOdb : takeUnusedCars : ends";
	public static final String DAO_TAKE_UNUSED_CARS_BY_CLASS_ENDS_MSG = "CarDAOdb : takeUnusedCarsByClass : ends";
	public static final String DAO_TAKE_ALL_CARS_ENDS_MSG = "CarDAOdb : takeAllCars : ends";
	public static final String DAO_INSERT_CAR_ENDS_MSG = "CarDAOdb : insertCar : ends";
	public static final String DAO_DELETE_CAR_BY_ID_ENDS_MSG = "CarDAOdb : deleteCarById : ends";
	public static final String DAO_COUNT_ALL_CARS_ENDS_MSG = "CarDAOdb : countAllCars : ends";
	public static final String DAO_TAKE_CAR_BY_ID_ENDS_MSG = "CarDAOdb : takeCarById : ends";
	public static final String DAO_TAKE_CAR_CLASS_STARTS_MSG = "CarDAOdb : takeCarClass : starts";
	public static final String DAO_TAKE_CAR_CLASS_ENDS_MSG = "CarDAOdb : takeCarClass : ends";
	public static final String DAO_COUNT_ALL_CLASS_CARS_STARTS_MSG = "CarDAOdb : countAllTypeCars : starts";
	public static final String DAO_COUNT_ALL_CLASS_CARS_ENDS_MSG = "CarDAOdb : countAllTypeCars : ends";
	public static final String DAO_COUNT_UNUSED_CLASS_CARS_STARTS_MSG = "CarDAOdb : countUnusedClassCars : starts";
	public static final String DAO_COUNT_UNUSED_CLASS_CARS_ENDS_MSG = "CarDAOdb : countUnusedClassCars : ends";
	public static final String DAO_COUNT_UNUSED_CARS_STARTS_MSG = "CarDAOdb : countUnusedCars : starts";
	public static final String DAO_COUNT_UNUSED_CARS_ENDS_MSG = "CarDAOdb : countUnusedCars : ends";


	public static final String DAO_TAKE_CARS_BY_CLASS_ERROR_MSG = "CarDAOdb : takeCarsByClass : ERROR";
	public static final String DAO_TAKE_CARS_BY_CLASS_CLOSE_CON_ERROR_MSG = "CarDAOdb : takeCarsByCLass : close connection error";
	public static final String DAO_TAKE_UNUSED_CARS_ERROR_MSG = "CarDAOdb : takeUnusedCars : ERROR";
	public static final String DAO_TAKE_UNUSED_CARS_CLOSE_CON_ERROR_MSG = "CarDAOdb : takeUnusedCars : close connection error";
	public static final String DAO_TAKE_UNUSED_CARS_BY_CLASS_ERROR_MSG = "CarDAOdb : takeUnusedCarsByClass : ERROR";
	public static final String DAO_TAKE_UNUSED_CARS_BY_CLASS_CLOSE_CON_ERROR_MSG = "CarDAOdb : takeUnusedCarsByClass : close connection error";
	public static final String DAO_TAKE_ALL_CARS_ERROR_MSG = "CarDAOdb : takeAllCars : ERROR";
	public static final String DAO_TAKE_ALL_CARS_CLOSE_CON_ERROR_MSG = "CarDAOdb : takeAllCars : close connection error";
	public static final String DAO_INSERT_CAR_ERROR_MSG = "CarDAOdb : insertCar : ERROR";
	public static final String DAO_INSERT_CAR_CLOSE_CON_ERROR_MSG = "CarDAOdb : insertCar : close connection error";
	public static final String DAO_DELETE_CAR_BY_ID_ERROR_MSG = "CarDAOdb : deleteCarById : ERROR";
	public static final String DAO_DELETE_CAR_BY_ID_CLOSE_CON_ERROR_MSG = "CarDAOdb : deleteCarById : close connection error";
	public static final String DAO_COUNT_ALL_CARS_ERROR_MSG = "CarDAOdb : countAllCars : ERROR";
	public static final String DAO_COUNT_ALL_CARS_CLOSE_CON_ERROR_MSG = "CarDAOdb : countAllCars : close connection error";
	public static final String DAO_TAKE_CAR_BY_ID_ERROR_MSG = "CarDAOdb : TakeCarById : ERROR";
	public static final String DAO_TAKE_CAR_BY_ID_CLOSE_CON_ERROR_MSG = "CarDAOdb : TakeCarById : close connection error";
	public static final String DAO_TAKE_CAR_CLASS_ERROR_MSG = "CarDAOdb : takeCarClass : ERROR";
	public static final String DAO_TAKE_CAR_CLASS_CLOSE_CON_ERROR_MSG = "CarDAOdb : takeCarClass : close connection error";
	public static final String DAO_COUNT_ALL_CLASS_CARS_ERROR_MSG = "CarDAOdb : countAllTypeCars : ERROR";
	public static final String DAO_COUNT_ALL_CLASS_CARS_CLOSE_CON_ERROR_MSG = "CarDAOdb : countAllTypeCars : close connection error";
	public static final String DAO_COUNT_UNUSED_CLASS_CARS_ERROR_MSG = "CarDAOdb : countUnusedClassCars : ERROR";
	public static final String DAO_COUNT_UNUSED_CLASS_CARS_CLOSE_CON_ERROR_MSG = "CarDAOdb : countUnusedClassCars : close connection error";
	public static final String DAO_COUNT_UNUSED_CARS_ERROR_MSG = "CarDAOdb : countUnusedCars : ERROR";
	public static final String DAO_COUNT_UNUSED_CARS_CLOSE_CON_ERROR_MSG = "CarDAOdb : countUnusedCars : close connection error";


	/**
	 * String constants for OrderDAOdb
	 */
	public static final String DAO_ADD_ORDER_STARTS_MSG = "OrderDAOdb : addOrder : starts";
	public static final String DAO_ADD_ORDER_ENDS_MSG = "OrderDAOdb : addOrder : ends";
	public static final String DAO_FIND_USED_CARS_ID_STARTS_MSG = "OrderDAOdb : findUsedCarsID : starts";
	public static final String DAO_FIND_USED_CARS_ID_ENDS_MSG = "OrderDAOdb : findUsedCarsID : ends";
	public static final String DAO_FIND_ORDERS_BY_USER_ID_STARTS_MSG = "OrderDAOdb : findOrdersByUserId : starts";
	public static final String DAO_FIND_ORDERS_BY_USER_ID_ENDS_MSG = "OrderDAOdb : findOrdersByUserId : ends";
	public static final String DAO_FIND_ORDER_BY_ID_STARTS_MSG = "OrderDAOdb : findOrderByOrderId : starts";
	public static final String DAO_FIND_ORDER_BY_ID_ENDS_MSG = "OrderDAOdb : findOrderByOrderId : ends";
	public static final String DAO_TAKE_ALL_ORDERS_STARTS_MSG = "OrderDAOdb : takeAllOrders : starts";
	public static final String DAO_TAKE_ALL_ORDERS_ENDS_MSG = "OrderDAOdb : takeAllOrders : ends";
	public static final String DAO_TAKE_ADMIN_ORDER_BY_ORDER_ID_STARTS_MSG = "OrderDAOdb : takeAdminOrderByOrderId : starts";
	public static final String DAO_TAKE_ADMIN_ORDER_BY_ORDER_ID_ENDS_MSG = "OrderDAOdb : takeAdminOrderByOrderId  : ends";
	public static final String DAO_UPDATE_STATUS_INFO_STARTS_MSG = "OrderDAOdb : updateStatusInfo : starts";
	public static final String DAO_UPDATE_STATUS_INFO_ENDS_MSG = "OrderDAOdb : updateStatusInfo : ends";
	public static final String DAO_UPDATE_DAMAGE_PRICE_STARTS_MSG = "OrderDAOdb : updateDamagePriceByOrderId : starts";
	public static final String DAO_UPDATE_DAMAGE_PRICE_ENDS_MSG = "OrderDAOdb : updateDamagePriceByOrderId : ends";
	public static final String DAO_TAKE_DISCOUNT_COEFFICIENT_STARTS_MSG = "OrderDAOdb : takeDiscountCoefficient : starts";
	public static final String DAO_TAKE_DISCOUNT_COEFFICIENT_ENDS_MSG = "OrderDAOdb : takeDiscountCoefficient : ends";
	public static final String DAO_COUNT_ALL_ORDERS_STARTS_MSG = "OrderDAOdb : countAllOrders : stats";
	public static final String DAO_COUNT_ALL_ORDERS_ENDS_MSG = "OrderDAOdb : countAllOrders : ends";

	public static final String DAO_ADD_ORDER_ERROR_MSG = "OrderDAOdb : addOrder : ERROR";
	public static final String DAO_ADD_ORDER_CLOSE_CON_ERROR_MSG = "OrderDAOdb : addOrder : close connection error";
	public static final String DAO_FIND_USED_CARS_ID_ERROR_MSG = "OrderDAOdb : findUsedCarsID : ERROR";
	public static final String DAO_FIND_USED_CARS_ID_CLOSE_CON_ERROR_MSG = "OrderDAOdb : findUsedCarsID : close connection error";
	public static final String DAO_FIND_ORDERS_BY_USER_ID_ERROR_MSG = "OrderDAOdb : findOrdersByUserId : ERROR";
	public static final String DAO_FIND_ORDERS_BY_USER_ID_CLOSE_CON_ERROR_MSG = "OrderDAOdb : findOrdersByUserId : close connection error";
	public static final String DAO_FIND_ORDER_BY_ID_ERROR_MSG = "OrderDAOdb : findOrderByOrderId : ERROR";
	public static final String DAO_FIND_ORDER_BY_ID_CLOE_CON_ERROR_MSG = "OrderDAOdb : findOrderByOrderId : close connection error";
	public static final String DAO_TAKE_ALL_ORDERS_ERROR_MSG = "OrderDAOdb : takeAllOrders : ERROR";
	public static final String DAO_TAKE_ALL_ORDERS_CLOSE_CON_ERROR_MSG = "OrderDAOdb : takeAllOrders : close connection error";
	public static final String DAO_TAKE_ADMIN_ORDER_BY_ORDER_ID_ERROR_MSG = "OrderDAOdb : takeAdminOrderByOrderId : ERROR";
	public static final String DAO_TAKE_ADMIN_ORDER_BY_ORDER_ID_CLOSE_CON_ERROR_MSG = "OrderDAOdb : takeAdminOrderByOrderId  : close connection error";
	public static final String DAO_UPDATE_STATUS_INFO_ERROR_MSG = "OrderDAOdb : updateStatusInfo : ERROR";
	public static final String DAO_UPDATE_STATUS_INFO_CLOSE_CON_ERROR_MSG = "OrderDAOdb : updateStatusInfo : close connection error";
	public static final String DAO_UPDATE_DAMAGE_PRICE_ERROR_MSG = "OrderDAOdb : updateDamagePriceByOrderId : ERROR";
	public static final String DAO_UPDATE_DAMAGE_PRICE_CLOSE_CON_ERROR_MSG = "OrderDAOdb : updateDamagePriceByOrderId : close connection error";
	public static final String DAO_TAKE_DISCOUNT_COEFFICIENT_ERROR_MSG = "OrderDAOdb : takeDiscountCoefficient : ERROR";
	public static final String DAO_TAKE_DISCOUNT_COEFFICIENT_CLOSE_CON_ERROR_MSG = "OrderDAOdb : takeDiscountCoefficient : close connection error";
	public static final String DAO_COUNT_ALL_ORDERS_ERROR_MSG = "OrderDAOdb : countAllOrders : ERROR";
	public static final String DAO_COUNT_ALL_ORDERS_CLOSE_CON_ERROR_MSG = "OrderDAOdb : countAllOrders : close connection error";

	/**
	 * String constants for UserDAOdb
	 */
	public static final String DAO_AUTHORIZATION_ERROR_MSG = "UserDAOdb : authorization : ERROR";
	public static final String DAO_AUTHORIZATION_CLOSE_CON_ERROR_MSG = "UserDAOdb : authorization : close connection error";
	public static final String DAO_REGISTRATION_ERROR_MSG = "UserDAOdb : registaration : ERROR";
	public static final String DAO_REGISTRATION_CLOSE_CON_ERROR_MSG = "UserDAOdb : registration : close connection error";
	public static final String DAO_FIND_USER_ERROR_MSG = "UserDAOdb : findUser : ERROR";
	public static final String DAO_FIND_USER_CLOSE_CON_ERROR_MSG = "UserDAOdb : findUser : close connection error";
	public static final String DAO_REMOVE_USER_BY_ID_ERROR_MSG = "UserDAOdb : removeUserByID : ERROR";
	public static final String DAO_REMOVE_USER_BY_ID_CON_ERROR_MSG = "UserDAOdb : removeUserByID : close connection error";
	public static final String DAO_UPDATE_USER_BY_ID_CON_ERROR_MSG = "UserDAOdb : updateUserByID : close connection error";
	public static final String DAO_FIND_USER_BY_ID_ERROR = "UserDAOdb : findUserById : ERROR";
	public static final String DAO_FIND_USER_BY_ID_CLOSE_CON_ERROR = "UserDAOdb : findUserById : close connection error";

	public static final String DAO_FIND_USER_STARTS_MSG = "UserDAOdb : findUser : starts";
	public static final String DAO_FIND_USER_ENDS_MSG = "UserDAOdb : findUser : ends";
	public static final String DAO_AUTORIZATION_STARTS_MSG = "UserDAOdb : authorization : starts";
	public static final String DAO_AUTORIZATION_ENDS_MSG = "UserDAOdb : authorization : ends";
	public static final String DAO_REGISTRATION_STARTS_MSG = "UserDAO : registrate user : starts";
	public static final String DAO_REGISTARATION_ENDS_MSG = "UserDAO : registrate user : ends";
	public static final String DAO_REMOVE_USER_BY_ID_END_MSG = "UserDAO : removeUserByID : ends";
	public static final String DAO_UPDATE_USER_BY_ID_STARTS_MSG = "UserDAOdb : updateUserByID : starts";
	public static final String DAO_UPDATE_USER_BY_ID_ENDS_MSG = "UserDAOdb : updateUserByID : ends";
	public static final String DAO_FIND_USER_BY_ID_STARTS_MSG = "UserDAOdb : findUserById : starts";
	public static final String DAO_FIND_USER_BY_ID_ENDS_MSG = "UserDAOdb : findUserById : ends";

	public static final String INIT_DAO_FACTORY_MSG = "DAOFactory : init DAO factory";


}
