package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;
/*
*
 */

public class DBResourceManager {

	private static final Logger LOG = LogManager.getLogger(ConnectionPool.class.getName());
	private static DBResourceManager dbResourceManager;
	private Properties properties;
	public static String DB_DRIVER;
	public static String DB_URL;
	public static String DB_USER;
	public static String DB_PASSWORD;
	public static String DB_POOLSIZE;

	{
		properties = new Properties();
		try {
			properties.load(getClass().getResourceAsStream("/database.properties"));
		} catch (IOException e) {
			LOG.error("Resource Connection Error" + e);
		}

	}

	private DBResourceManager() {

		DB_DRIVER = properties.getProperty("db_driver");
		DB_URL = properties.getProperty("db_url");
		DB_USER = properties.getProperty("db_user");
		DB_PASSWORD = properties.getProperty("db_password");
		DB_POOLSIZE = properties.getProperty("db_poolsize");
	}

	public static DBResourceManager getInstance() {
		if (null == dbResourceManager) {
			dbResourceManager = new DBResourceManager();
		}
		return dbResourceManager;
	}

}
