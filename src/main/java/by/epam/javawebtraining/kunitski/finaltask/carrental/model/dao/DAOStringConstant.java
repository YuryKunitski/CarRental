package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao;

public class DAOStringConstant {

	/**
	 * Строковые константы для ConnectionPool
	 */
	public static final String NUMB_FORM_EXC = "NumberFormatException in ConnectionPool";
	public static final String INIT_STARTS_MSG =  "ConnectionPool : initConnectionPool : starts";
	public static final String INIT_ENDS_MSG =  "ConnectionPool : initConnectionPool : ends";
	public static final String CON_POOL_EXC = "Error in connection pool. ConnectionPoolException";
	public static final String CLASS_NOT_FOUND_EXC =  "Can't find database driver class. ConnectionPool";
	public static final String TAKE_CONNECTION_STARTS_MSG =   "ConnectionPool : takeConnection : starts";
	public static final String TAKE_CONNECTION_ENDS_MSG =  "ConnectionPool : takeConnection : ends";
	public static final String TAKE_CON_EXC =  "Error connection to the data source. ConnectionPool";
	public static final String DISPOSE_STARTS_MSG =  "ConnectionPool : dispose : starts";
	public static final String DISPOSE_ENDS_MSG =  "ConnectionPool : dispose : ends";
	public static final String DISPOSE_CON_EXC =  "SQL Exception in disposeConnectionQueue. ConnectionPool";
	public static final String CLOSE_CONNECTION_MSG =  "ConnectionPool : closeConnection";
	public static final String CLOSE_CON_EXC =   "SQL Exception in closeConnection. ConnectionPool";
	public static final String CLOSE_CONNECTION_QUERY_STARTS_MSG = "ConnectionPool : closeConnectionsQueue : starts";
	public static final String CLOSE_CONNECTION_QUERY_ENDS_MSG = "ConnectionPool : closeConnectionsQueue : ends";
	public static final String IS_CLOSED_EXC = "Attempting to close closed connection. ConnectionPool";
	public static final String REMOVE_EXC = "Error deleting connection from the used connections pool. ConnectionPool";
	public static final String OFFER_EXC = "Error allocation connection in the pool. ConnectionPool";

	/**
	 * Строковые константы для CarDAOdb
	 */
	public static final String DAO_TAKE_MARKS_STARTS_MSG = "CarDAOdb : takeMarks : starts";
	public static final String DAO_TAKE_MODELS_STARTS_MSG = "CarDAOdb : takeModels : starts";
	public static final String DAO_TAKE_CAR_TYPES_STARTS_MSG = "CarDAOdb : takeCarTypes : starts";
	public static final String DAO_TAKE_CARS_BY_CLASS_STARTS_MSG = "CarDAOdb : takeCarsByClass : starts";
	public static final String DAO_TAKE_UNUSED_CARS_STARTS_MSG = "CarDAOdb : takeUnusedCars : starts";
	public static final String DAO_TAKE_UNUSED_CARS_BY_CLASS_STARTS_MSG = "CarDAOdb : takeUnusedCarsByClass : starts";
	public static final String DAO_TAKE_ALL_CARS_STARTS_MSG = "CarDAOdb : takeAllCars : starts";
	public static final String DAO_INSERT_CAR_STARTS_MSG = "CarDAOdb : insertCar : starts";
	public static final String DAO_FIND_CAR_BY_VIN_NUMBER_STARTS_MSG = "CarDAOdb : findCarByGovNumberVin : starts";
	public static final String DAO_DELETE_CAR_BY_ID_STARTS_MSG = "CarDAOdb : deleteCarById : starts";
	public static final String DAO_COUNT_ALL_CARS_STARTS_MSG = "CarDAOdb : countAllCars : starts";
	public static final String DAO_COUNT_ALL_TYPE_CARS_STARTS_MSG = "CarDAOdb : countAllCars : starts";
	public static final String DAO_COUNT_UNUSED_TYPE_CARS_STARTS_MSG = "CarDAOdb : countUnusedTypeCars : starts";
	public static final String DAO_TAKE_CAR_BY_ID_STARTS_MSG = "CarDAOdb : takeCarById : starts";
	public static final String DAO_TAKE_MARKS_ENDS_MSG = "CarDAOdb : takeMarks : ends";
	public static final String DAO_TAKE_MODELS_ENDS_MSG = "CarDAOdb : takeModels : ends";
	public static final String DAO_TAKE_CAR_TYPES_ENDS_MSG = "CarDAOdb : takeCarTypes : ends";
	public static final String DAO_TAKE_CARS_BY_CLASS_ENDS_MSG = "CarDAOdb : takeCarsByClass : ends";
	public static final String DAO_TAKE_UNUSED_CARS_ENDS_MSG = "CarDAOdb : takeUnusedCars : ends";
	public static final String DAO_TAKE_UNUSED_CARS_BY_CLASS_ENDS_MSG = "CarDAOdb : takeUnusedCarsByClass : ends";
	public static final String DAO_TAKE_ALL_CARS_ENDS_MSG = "CarDAOdb : takeAllCars : ends";
	public static final String DAO_INSERT_CAR_ENDS_MSG = "CarDAOdb : insertCar : ends";
	public static final String DAO_FIND_CAR_BY_VIN_NUMBER_ENDS_MSG = "CarDAOdb : findCarByGovNumberVin : ends";
	public static final String DAO_DELETE_CAR_BY_ID_ENDS_MSG = "CarDAOdb : deleteCarById : ends";
	public static final String DAO_COUNT_ALL_CARS_ENDS_MSG = "CarDAOdb : countAllCars : ends";
	public static final String DAO_COUNT_ALL_TYPE_CARS_ENDS_MSG = "CarDAOdb : countAllCars : ends";
	public static final String DAO_COUNT_UNUSED_TYPE_CARS_ENDS_MSG = "CarDAOdb : countUnusedTypeCars : ends";
	public static final String DAO_TAKE_CAR_BY_ID_ENDS_MSG = "CarDAOdb : takeCarById : ends";

	public static final String DAO_TAKE_MARKS_ERROR_MSG = "CarDAOdb : takeMarks : ERROR";
	public static final String DAO_TAKE_MARKS_CLOSE_CON_ERROR_MSG = "CarDAOdb : takeMarks : close connection error";
	public static final String DAO_TAKE_MODELS_ERROR_MSG = "CarDAOdb : takeModels : ERROR";
	public static final String DAO_TAKE_MODELS_CLOSE_CON_ERROR_MSG = "CarDAOdb : takeModels : close connection error";
	public static final String DAO_TAKE_CAR_TYPES_ERROR_MSG = "CarDAOdb : takeCarTypes : ERROR";
	public static final String DAO_TAKE_CAR_TYPES_CLOSE_CON_ERROR_MSG = "CarDAOdb : takeCarTypes : close connection error";
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
	public static final String DAO_FIND_CAR_BY_GOV_NUMBER_VIN_ERROR_MSG = "CarDAOdb : findCarByGovNumberVin : ERROR";
	public static final String DAO_FIND_CAR_BY_GOV_NUMBER_VIN_CLOSE_CON_ERROR_MSG = "CarDAOdb : findCarByGovNumberVin : close connection error";
	public static final String DAO_DELETE_CAR_BY_ID_ERROR_MSG = "CarDAOdb : deleteCarById : ERROR";
	public static final String DAO_DELETE_CAR_BY_ID_CLOSE_CON_ERROR_MSG = "CarDAOdb : deleteCarById : close connection error";
	public static final String DAO_COUNT_ALL_CARS_ERROR_MSG = "CarDAOdb : countAllCars : ERROR";
	public static final String DAO_COUNT_ALL_CARS_CLOSE_CON_ERROR_MSG = "CarDAOdb : countAllCars : close connection error";
	public static final String DAO_COUNT_ALL_TYPE_CARS_ERROR_MSG = "CarDAOdb : countAllTypeCars : ERROR";
	public static final String DAO_COUNT_ALL_TYPE_CARS_CLOSE_CON_ERROR_MSG = "CarDAOdb : countAllTypeCars : close connection error";
	public static final String DAO_COUNT_UNUSED_TYPE_CARS_ERROR_MSG = "carDAOdb : countUnusedTypeCars : ERROR";
	public static final String DAO_COUNT_UNUSED_TYPE_CARS_CLOSE_CON_ERROR_MSG = "carDAOdb : countUnusedTypeCars : close connection error";
	public static final String DAO_TAKE_CAR_BY_ID_ERROR_MSG = "CarDAOdb : TakeCarById : ERROR";
	public static final String DAO_TAKE_CAR_BY_ID_CLOSE_CON_ERROR_MSG = "CarDAOdb : TakeCarById : close connection error";

	/**
	 * Строковые константы для OrderDAOdb
	 */
	public static final String DAO_ADD_ORDER_STARTS_MSG = "OrderDAOdb : addOrder : starts";
	public static final String DAO_ADD_ORDER_ENDS_MSG = "OrderDAOdb : addOrder : ends";
	public static final String DAO_TAKE_ORDER_BY_ORDER_STARTS_MSG = "OrderDAOdb : takeOrderByOrder : starts";
	public static final String DAO_TAKE_ORDER_BY_ORDER_ENDS_MSG = "OrderDAOdb : takeOrderByOrder : ends";
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
	public static final String DAO_UPDATE_REAL_TIME_FROM_STARTS_MSG = "OrderDAOdb : updateRealTimeFrom : starts";
	public static final String DAO_UPDATE_REAL_TIME_FROM_ENDS_MSG = "OrderDAOdb : updateRealTimeFrom : ends";
	public static final String DAO_UPDATE_REAL_TIME_TO_STARTS_MSG = "OrderDAOdb : updateRealTimeTo : starts";
	public static final String DAO_UPDATE_REAL_TIME_TO_ENDS_MSG = "OrderDAOdb : updateRealTimeTo : ends";
	public static final String DAO_COUNT_USER_ORDERS_STARTS_MSG = "OrderDAOdb : countUserOrders : starts";
	public static final String DAO_COUNT_USER_ORDERS_ENDS_MSG = "OrderDAOdb : countUserOrders : ends";
	public static final String DAO_COUNT_ALL_ORDERS_STARTS_MSG = "OrderDAOdb : countAllOrders : stats";
	public static final String DAO_COUNT_ALL_ORDERS_ENDS_MSG = "OrderDAOdb : countAllOrders : ends";
	public static final String DAO_FIND_STATUS_BY_CAR_ID_STARTS_MSG = "OrderDAOdb : findStatusByCarId : starts";
	public static final String DAO_FIND_STATUS_BY_CAR_ID_ENDS_MSG = "OrderDAOdb : findStatusByCarId : ends";

	public static final String DAO_ADD_ORDER_ERROR_MSG = "OrderDAOdb : addOrder : ERROR";
	public static final String DAO_ADD_ORDER_CLOSE_CON_ERROR_MSG = "OrderDAOdb : addOrder : close connection error";
	public static final String DAO_TAKE_ORDER_BY_ORDER_ERROR_MSG = "OrderDAOdb : takeOrderByOrder : ERROR";
	public static final String DAO_TAKE_ORDER_BY_ORDER_CLOSE_CON_ERROR_MSG = "OrderDAOdb : takeOrderByOrder : close connection error";
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
	public static final String DAO_UPDATE_REAL_TIME_FROM_ERROR_MSG = "OrderDAOdb : updateRealTimeFrom : ERROR";
	public static final String DAO_UPDATE_REAL_TIME_FROM_CLOSE_CON_ERROR_MSG = "OrderDAOdb : updateRealTimeFrom : close connection error";
	public static final String DAO_UPDATE_REAL_TIME_TO_ERROR_MSG = "OrderDAOdb : updateRealTimeTo : ERROR";
	public static final String DAO_UPDATE_REAL_TIME_TO_CLOSE_CON_ERROR_MSG = "OrderDAOdb : updateRealTimeTo : close connection error";
	public static final String DAO_COUNT_USER_ORDERS_ERROR_MSG = "OrderDAOdb : countUserOrders : ERROR";
	public static final String DAO_COUNT_USER_ORDERS_CLOSE_CON_ERROR_MSG = "OrderDAOdb : countUserOrders : close connection error";
	public static final String DAO_COUNT_ALL_ORDERS_ERROR_MSG = "OrderDAOdb : countAllOrders : ERROR";
	public static final String DAO_COUNT_ALL_ORDERS_CLOSE_CON_ERROR_MSG = "OrderDAOdb : countAllOrders : close connection error";
	public static final String DAO_FIND_STATUS_BY_CAR_ID_ERROR_MSG = "OrderDAOdb : findStatusByCarId : ERROR";
	public static final String DAO_FIND_STATUS_BY_CAR_ID_CLOSE_CON_ERROR = "orderDAOdb : findStatusByCarId : close connection error";

	/**
	 * Строковые константы для UserDAOdb
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
	public static final String DAO_TAKE_ALL_USERS_ERROR = "UserDAOdb : takeAllUsers : ERROR";
	public static final String DAO_TAKE_ALL_USERS_CLOSE_CON_ERROR_MSG = "UserDAOdb : takeAllUsers : close connection error";
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
	public static final String DAO_TAKE_ALL_USERS_STARTS_MSG = "UserDAOdb : takeAllUsers : starts";
	public static final String DAO_TAKE_ALL_USERS_ENDS_MSG = "UserDAOdb : takeAllUsers : ends";
	public static final String DAO_FIND_USER_BY_ID_STARTS_MSG = "UserDAOdb : findUserById : starts";
	public static final String DAO_FIND_USER_BY_ID_ENDS_MSG = "UserDAOdb : findUserById : ends";
	public static final String DAO_COUNT_ALL_USERS_STARTS_MSG = "UserDAOdb : countAllUsers : starts";
	public static final String DAO_COUNT_ALL_USERS_ENDS_MSG = "UserDAOdb : countAllUsers : ends";

	public static final String INIT_DAO_FACTORY_MSG = "DAOFactory : init DAO factory";


}
