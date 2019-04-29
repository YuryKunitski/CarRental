package by.epam.javawebtraining.kunitski.finaltask.carrental.webapp.listener;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ConnectionPoolException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool.ConnectionPool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ProjectServletContextListener implements ServletContextListener {

	private static final Logger LOG = LogManager.getLogger(ConnectionPool.class.getName());
	private static final String DESTROY_MSG = "ServletContextListener : Connection Pool has been destroyed";
	private static final String INIT_MSG = "ServletContextListener : Connection Pool has been initialized";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			pool.dispose();
			LOG.debug(DESTROY_MSG);
		} catch (ConnectionPoolException ex) {
			LOG.error(ex);
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			ConnectionPool pool = ConnectionPool.getInstance();
			pool.initConnectionPool();
			LOG.debug(INIT_MSG);
		} catch (ConnectionPoolException ex) {
			LOG.error(ex);
			throw new RuntimeException(ex);
		}
	}
}
