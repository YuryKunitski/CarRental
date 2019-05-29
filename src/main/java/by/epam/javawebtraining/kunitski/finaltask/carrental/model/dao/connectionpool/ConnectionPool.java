package by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.connectionpool;

import by.epam.javawebtraining.kunitski.finaltask.carrental.exception.ConnectionPoolException;
import by.epam.javawebtraining.kunitski.finaltask.carrental.model.dao.DAOStringConstant;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

public class ConnectionPool {

	private static final Logger LOG = LogManager.getLogger(ConnectionPool.class);
	private DBResourceManager dbResourceManager = DBResourceManager.getInstance();
	private static ConnectionPool connectionPool;

	private BlockingQueue<Connection> connectionQueue;
	private BlockingQueue<Connection> usedConnectionQueue;
	private String driverName;
	private String url;
	private String user;
	private String password;
	private int poolSize;


	private ConnectionPool() {

		driverName = DBResourceManager.DB_DRIVER;
		url = DBResourceManager.DB_URL;
		user = DBResourceManager.DB_USER;
		password = DBResourceManager.DB_PASSWORD;
		try {
			poolSize = Integer.parseInt(DBResourceManager.DB_POOLSIZE);
		} catch (NumberFormatException e) {
			LOG.error(DAOStringConstant.NUMB_FORM_EXC, e);
		}
	}

	public static ConnectionPool getInstance() {
		if (null == connectionPool) {
			connectionPool = new ConnectionPool();
		}
		return connectionPool;
	}

	/*
	 * Initialization of connection pool.
	 */
	public void initConnectionPool() throws ConnectionPoolException {

		LOG.debug(DAOStringConstant.INIT_STARTS_MSG);

		connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
		usedConnectionQueue = new ArrayBlockingQueue<Connection>(poolSize);

		try {
			Class.forName(driverName);
			for (int indexPool = 0; indexPool < poolSize; indexPool++) {
				Connection connection = DriverManager.getConnection(url, user, password);
				PooledConnection pooledConnection = new PooledConnection(connection);
				connectionQueue.add(pooledConnection);

			}
		} catch (SQLException e) {
			LOG.error(DAOStringConstant.CON_POOL_EXC);
			throw new ConnectionPoolException(DAOStringConstant.CON_POOL_EXC, e);

		} catch (ClassNotFoundException e) {
			LOG.error(DAOStringConstant.CLASS_NOT_FOUND_EXC);
			throw new ConnectionPoolException(DAOStringConstant.CLASS_NOT_FOUND_EXC, e);
		}

		LOG.debug(DAOStringConstant.INIT_ENDS_MSG);

	}

	/*
	 * Take the connection from pool.
	 */
	public Connection takeConnection() throws ConnectionPoolException {

		LOG.debug(DAOStringConstant.TAKE_CONNECTION_STARTS_MSG);

		Connection connection = null;

		try {
			connection = connectionQueue.take();
			usedConnectionQueue.add(connection);


		} catch (InterruptedException e) {
			LOG.error(DAOStringConstant.TAKE_CON_EXC);
			throw new ConnectionPoolException(DAOStringConstant.TAKE_CON_EXC, e);
		}

		LOG.debug(DAOStringConstant.TAKE_CONNECTION_ENDS_MSG);
		return connection;

	}

	/*
	 * Destroy connection pool.
	 */
	public void dispose() throws ConnectionPoolException {

		LOG.debug(DAOStringConstant.DISPOSE_STARTS_MSG);

		try {
			closeConnectionsQueue(usedConnectionQueue);
			closeConnectionsQueue(connectionQueue);
		} catch (SQLException e) {
			LOG.error(DAOStringConstant.DISPOSE_CON_EXC);
			throw new ConnectionPoolException(DAOStringConstant.DISPOSE_CON_EXC, e);
		}

		LOG.debug(DAOStringConstant.DISPOSE_ENDS_MSG);
	}

	/*
	 * Close Connection and Statement
	 */
	public void closeConnection(Connection con, Statement st) throws ConnectionPoolException {
		LOG.debug(DAOStringConstant.CLOSE_CONNECTION_MSG);
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException ex) {
			LOG.error(DAOStringConstant.CLOSE_CON_EXC);
			throw new ConnectionPoolException(ex);
		}
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException ex) {
			LOG.error(DAOStringConstant.CLOSE_CON_EXC);
			throw new ConnectionPoolException(ex);
		}
	}

	/*
	 * Close Connection, Statement and ResultSet
	 */
	public void closeConnection(Connection con, Statement st, ResultSet rs) throws ConnectionPoolException {

		LOG.debug(DAOStringConstant.CLOSE_CONNECTION_MSG);

		closeConnection(con, st);

		try {
			if (rs != null)
				rs.close();
		} catch (SQLException ex) {
			LOG.error(DAOStringConstant.CLOSE_CON_EXC);
			throw new ConnectionPoolException(ex);
		}

	}


	private void closeConnectionsQueue(BlockingQueue<Connection> queue) throws SQLException {

		LOG.debug(DAOStringConstant.CLOSE_CONNECTION_QUERY_STARTS_MSG);

		Connection connection = null;

		while ((connection = queue.poll()) != null) {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			((PooledConnection) connection).reallyClose();
		}

		LOG.debug(DAOStringConstant.CLOSE_CONNECTION_QUERY_ENDS_MSG);
	}

	private class PooledConnection implements Connection {

		private Connection connection;

		public PooledConnection(Connection connection) {
			this.connection = connection;
		}

		public void reallyClose() throws SQLException {
			connection.close();
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return connection.isWrapperFor(iface);
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return connection.unwrap(iface);
		}

		@Override
		public void abort(Executor executor) throws SQLException {
			connection.abort(executor);
		}

		@Override
		public void clearWarnings() throws SQLException {
			connection.clearWarnings();

		}

		@Override
		public void close() throws SQLException {

			if (connection.isClosed()) {
				LOG.error(DAOStringConstant.IS_CLOSED_EXC);
				throw new SQLException(DAOStringConstant.IS_CLOSED_EXC);
			}
			if (connection.isReadOnly()) {
				connection.setReadOnly(false);
			}
			if (!usedConnectionQueue.remove(this)) {
				LOG.error(DAOStringConstant.REMOVE_EXC);
				throw new SQLException(DAOStringConstant.REMOVE_EXC);
			}
			if (!connectionQueue.offer(this)) {
				LOG.error(DAOStringConstant.OFFER_EXC);
				throw new SQLException(DAOStringConstant.OFFER_EXC);
			}
		}

		@Override
		public void commit() throws SQLException {
			connection.commit();

		}

		@Override
		public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
			return connection.createArrayOf(typeName, elements);
		}

		@Override
		public Clob createClob() throws SQLException {
			return connection.createClob();
		}

		@Override
		public Blob createBlob() throws SQLException {
			return connection.createBlob();
		}

		@Override
		public NClob createNClob() throws SQLException {
			return connection.createNClob();
		}

		@Override
		public SQLXML createSQLXML() throws SQLException {
			return connection.createSQLXML();
		}

		@Override
		public Statement createStatement() throws SQLException {
			return connection.createStatement();
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency);
		}

		@Override
		public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
				throws SQLException {
			return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
			return connection.createStruct(typeName, attributes);
		}

		@Override
		public boolean getAutoCommit() throws SQLException {
			return connection.getAutoCommit();
		}

		@Override
		public String getCatalog() throws SQLException {
			return connection.getCatalog();
		}

		@Override
		public String getClientInfo(String name) throws SQLException {
			return connection.getClientInfo(name);
		}

		@Override
		public Properties getClientInfo() throws SQLException {
			return connection.getClientInfo();
		}

		@Override
		public int getHoldability() throws SQLException {
			return connection.getHoldability();
		}

		@Override
		public DatabaseMetaData getMetaData() throws SQLException {
			return connection.getMetaData();
		}

		@Override
		public int getNetworkTimeout() throws SQLException {
			return connection.getNetworkTimeout();
		}

		@Override
		public String getSchema() throws SQLException {
			return connection.getSchema();
		}

		@Override
		public int getTransactionIsolation() throws SQLException {
			return connection.getTransactionIsolation();
		}

		@Override
		public Map<String, Class<?>> getTypeMap() throws SQLException {
			return connection.getTypeMap();
		}

		@Override
		public SQLWarning getWarnings() throws SQLException {
			return connection.getWarnings();
		}

		@Override
		public boolean isClosed() throws SQLException {
			return connection.isClosed();
		}

		@Override
		public boolean isReadOnly() throws SQLException {
			return connection.isReadOnly();
		}

		@Override
		public boolean isValid(int timeout) throws SQLException {
			return connection.isValid(timeout);
		}

		@Override
		public String nativeSQL(String sql) throws SQLException {
			return connection.nativeSQL(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql) throws SQLException {
			return connection.prepareCall(sql);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
		                                     int resultSetHoldability) throws SQLException {
			return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public PreparedStatement prepareStatement(String sql) throws SQLException {
			return connection.prepareStatement(sql);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
			return connection.prepareStatement(sql, autoGeneratedKeys);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
			return connection.prepareStatement(sql, columnIndexes);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
			return connection.prepareStatement(sql, columnNames);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
				throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
		}

		@Override
		public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
		                                          int resultSetHoldability) throws SQLException {
			return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
		}

		@Override
		public void releaseSavepoint(Savepoint savepoint) throws SQLException {
			connection.releaseSavepoint(savepoint);
		}

		@Override
		public void rollback() throws SQLException {
			connection.rollback();
		}

		@Override
		public void rollback(Savepoint savepoint) throws SQLException {
			connection.rollback(savepoint);
		}

		@Override
		public void setAutoCommit(boolean autoCommit) throws SQLException {
			connection.setAutoCommit(autoCommit);
		}

		@Override
		public void setCatalog(String catalog) throws SQLException {
			connection.setCatalog(catalog);
		}

		@Override
		public void setClientInfo(Properties properties) throws SQLClientInfoException {
			connection.setClientInfo(properties);
		}

		@Override
		public void setClientInfo(String name, String value) throws SQLClientInfoException {
			connection.setClientInfo(name, value);
		}

		@Override
		public void setHoldability(int holdability) throws SQLException {
			connection.setHoldability(holdability);
		}

		@Override
		public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
			connection.setNetworkTimeout(executor, milliseconds);
		}

		@Override
		public void setReadOnly(boolean readOnly) throws SQLException {
			connection.setReadOnly(readOnly);
		}

		@Override
		public Savepoint setSavepoint() throws SQLException {
			return connection.setSavepoint();
		}

		@Override
		public Savepoint setSavepoint(String name) throws SQLException {
			return connection.setSavepoint(name);
		}

		@Override
		public void setSchema(String schema) throws SQLException {
			connection.setSchema(schema);
		}

		@Override
		public void setTransactionIsolation(int level) throws SQLException {
			connection.setTransactionIsolation(level);
		}

		@Override
		public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
			connection.setTypeMap(map);
		}
	}

}
