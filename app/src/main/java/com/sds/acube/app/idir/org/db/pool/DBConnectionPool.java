package com.sds.acube.app.idir.org.db.pool;

import com.sds.acube.app.idir.common.vo.ConnectionParam;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Properties;
import java.util.ResourceBundle;
import java.io.*;


/**
 * DBConnectionPool class is a Singleton that provides access to a connection  pool defined in a Property file.  A client gets access to the single instance through the static getInstance() method and can then check-out and check-in connections from a pool. When the client shuts down it should call the release() method to close all open connections and do other clean up.
 * @author  Gefion software, MyungHee Jung, Jack
 * @version  $Revision: 1.2 $  Last Revised $Date: 2009/12/10 05:29:48 $
 */
public class DBConnectionPool {

	/**
	 * Return the single instance, 
	 * creating one if it's the first time this method is called.
	 *
	 * @return the single instance of DBConnectionPool 
	 */
	public static synchronized DBConnectionPool getInstance(ConnectionParam[] connectionParams) throws Exception {

		try {
			if (instance == null) {	
				// 추가 정보 셋팅
				setAdditionalProperties();
				
				instance = new DBConnectionPool(connectionParams);
				DBException.exceptionCount = 0;
				
				instance.initializeVerification();
			} else {
				instance.loadDrivers(connectionParams);
				instance.createPools(connectionParams);
			}
			clients++;
		} catch (DBException dbex) {
			if (DBException.exceptionCount >= MAX_FAILURE_COUNT) {
// TODO 커넥션풀에 대한 Reset 기능 재고 : release() 시 사용중인 커넥션은 해제 대상에서 누락됨
/*
				if (instance != null) {
					try {
						instance.release();
					} catch (DBException ignore) { } 
					
					instance = null;
				}
				DBException.exceptionCount = 0;
*/
			}
			throw dbex;
		} catch (Exception e) {
			throw e;
		}

		return instance;
	}
	
	/**
	 * Properties정보를 읽어서 Connection Pool을 만들기 위한 정보를 만드는 함수.
	 */
	private static void setAdditionalProperties() throws Exception
	{
		ResourceBundle bundle = ResourceBundle.getBundle(CONNECTION_PARAM_PROPERTIES);
		
		String propPoolName = bundle.getString(PROP_ORGANIZATION_CONNECTION_POOL_NAME);
		if ((propPoolName == null) || (propPoolName.length() == 0)) 
		{
			throw new Exception("Empty pool Name");	
		}
		poolName = propPoolName;
		
		String propLoginTimeout = bundle.getString(PROP_ORGANIZATION_CONNECTION_LOGIN_TIMEOUT);
		if ((propLoginTimeout == null) || (propLoginTimeout.length() == 0)) 
		{
			throw new Exception("Empty login time out");	
		}
		loginTimeout = Integer.parseInt(propLoginTimeout);
		
		String propInitialCapacity = bundle.getString(PROP_ORGANIZATION_CONNECTION_INITIAL_CAPACITY);
		if ((propInitialCapacity == null) || (propInitialCapacity.length() == 0)) 
		{
			throw new Exception("Empty initial capacity");	
		}
		initialCapacity = Integer.parseInt(propInitialCapacity);
		
		String propMaxCapacity = bundle.getString(PROP_ORGANIZATION_CONNECTION_MAX_CAPACITY);
		if ((propMaxCapacity == null) || (propMaxCapacity.length() == 0)) 
		{
			throw new Exception("Empty max capacity");	
		}
		maxCapacity = Integer.parseInt(propMaxCapacity);
		
		String propCapacityIncrement = bundle.getString(PROP_ORGANIZATION_CONNECTION_CAPACITY_INCREMENT);	
		if ((propCapacityIncrement == null) || (propCapacityIncrement.length() == 0)) 
		{
			throw new Exception("Empty capacity increment");	
		}
		capacityIncrement = Integer.parseInt(propCapacityIncrement);
		
		String propIsVerify = bundle.getString(PROP_ORGANIZATION_CONNECTION_IS_VERIFY);
		if ((propIsVerify == null) || (propIsVerify.length() == 0)) 
		{
			throw new Exception("Empty is verify");	
		}
		isVerify = Boolean.parseBoolean(propIsVerify);
		
		String propVerificationInterval = bundle.getString(PROP_ORGANIZATION_CONNECTION_VERIFICATION_INTERVAL);
		if ((propVerificationInterval == null) || (propVerificationInterval.length() == 0)) 
		{
			throw new Exception("Empty verification interval");	
		}
		verificationInterval = Integer.parseInt(propVerificationInterval);
	}

	/**
	 * Return the single instance. 
	 * @return  the single instance of DBConnectionPool
	 */
	public static synchronized DBConnectionPool getInstance() {

		return instance;
	}
	
	//***************************************************************************
	// Private Constructor
	//***************************************************************************

	/**
	 * A private constructor since this is a Singleton
	 */
	private DBConnectionPool(ConnectionParam[] connectionParams) throws DBException {

		loadDrivers(connectionParams);
		createPools(connectionParams);
	}

	//***************************************************************************
	// Public Methods
	//***************************************************************************

	/**
	 * 커넥션 풀의 초기 용량으로 커넥션을 생성
	 */
	public void initializePools() throws DBException {
		
		Enumeration allPools = poolList.elements();
		while (allPools.hasMoreElements()) {
			Pool pool = (Pool) allPools.nextElement();
			pool.initializePool();
		}
	}

	/**
	 * Return an open connection for the connection pool.
	 * If no one is available, and the max number of connections has not been 
	 * reached, a new connection is created.
	 *
	 * @return the connection or null
	 */
	public Connection getConnection(String name) throws DBException {

		Connection conn = null;
		try {
			Pool pool = (Pool) poolList.get(name);
			if (pool != null) {
				conn = pool.getConnection();
				if (conn != null) {
					// ConnectionFactory를 통해 얻어진 Connection의 close()를 호출할 때,
					// DBConnectionPool.freeConnection(name, conn)이 호출되어지도록 override한 클래스를 반환
					//return new ConnectionImpl(conn, name);
					// Connection Pool을 사용 하지 않으므로 override제거(JDK버전별 관리문제)
					return conn;
				} else {
					return null;
				}
			} else {
				throw new DBException(DBException.DBPOOLNOTFOUND, name);
			}
		} catch (DBException dbe) {
// TODO 커넥션풀에 대한 Reset 기능 재고 : release() 시 사용중인 커넥션은 해제 대상에서 누락됨
/*
			if (DBException.exceptionCount >= MAX_FAILURE_COUNT) {
				clients = 1;
				release();
				instance = null;
				DBException.exceptionCount = 0;
			}
*/				
			throw dbe;
		}
	}
	
	/**
	 * Return an open connection for the connection pool.
	 * If no one is available, and the max number of connections has not been 
	 * reached, a new connection is created.
	 *
	 * @return the connection or null
	 */
	public Connection getDefaultConnection() throws DBException {
		
		return getConnection(poolName);
	}

	/**
	 * Return a connection to the named pool.
	 *
	 * @param name the pool name as defined in the properties file
	 * @param conn the Connection
	 */
	public void freeConnection(String name, Connection conn) {

		try {
			Pool pool = (Pool) poolList.get(name);
			if (pool != null)
				pool.freeConnection(conn);
		} catch (DBException dbex) { }
	}

	/** 
	 * Closes all open connections and deregisters driver.
	 */
	public synchronized void release() throws DBException {
		
		releaseVerification();

		if (--clients != 0) {
			System.out.println("[DBConnectionPool.release] Ramained clients : " + clients);
			return;
		}

		if (poolList != null) {
			Enumeration allPools = poolList.elements();
			while (allPools.hasMoreElements()) {
				Pool pool = (Pool) allPools.nextElement();
				pool.release();
//				poolList.remove(pool);
				pool = null;
			}
		}

		System.out.println("[DBConnectionPool.release] Deregister drivers");
		Enumeration allDrivers = drivers.elements();
		while (allDrivers.hasMoreElements()) {
			Driver driver = (Driver) allDrivers.nextElement();
			try {
				DriverManager.deregisterDriver(driver);
			} catch (Exception ex) {
				throw new DBException(DBException.DRIVEROFFERROR, driver.getClass().getName());
			}
		}
	}

	/**
	 * 커넥션 풀 정보 출력
	 * @param logger 커넥션 풀 정보를 출력할 Logger 개체
	 */
	public void printPools() throws DBException {
		
		Enumeration e = poolList.elements();
		while (e.hasMoreElements()) {
			Pool pool = (Pool) e.nextElement();
			pool.printPool();
		}
	}

	/**
	 * 커넥션 풀 정보 반환
	 * @return 커넥션 풀 정보를 담고 있는 StringBuffer 개체
	 */
	public StringBuffer monitorPools() throws DBException {

		StringBuffer sBuf = new StringBuffer();
		Enumeration e = poolList.elements();
		while (e.hasMoreElements()) {
			Pool pool = (Pool) e.nextElement();
			sBuf.append(pool.monitorPool());
		}
		
		return sBuf;
	}
	
	/**
	 * 커넥션이 유효한지 검사하는 함수
	 */
	public void verifyPools() throws DBException {
		
		System.out.println("[ACUBE IDIR] *** Verify pools start.");
		Enumeration allPools = poolList.elements();
		while (allPools.hasMoreElements()) {
			Pool pool = (Pool) allPools.nextElement();
			pool.verifyPool();
		}
		System.out.println("[ACUBE IDIR] *** Verify pools end.");
	}

	//***************************************************************************
	// Private Methods
	//***************************************************************************

	/**
	 * Load and register all JDBC drivers. 
	 * This is done by the DBConnectionPool, as opposed to the Pool,
	 * since many poolList may share the same driver.
	 */
	private void loadDrivers(ConnectionParam[] connectionParams) throws DBException {

		if (drivers == null)
			drivers = new Hashtable();

		for (int i = 0; i < connectionParams.length; i++) {
			String className = connectionParams[i].getClassName();
			try {
				if (!drivers.containsKey(className)) {
					Driver driver = (Driver) Class.forName(className).newInstance();
					DriverManager.registerDriver(driver);
					drivers.put(className, driver);
					System.out.println("[DBConnectionPool.loadDrivers] Register dirver - " + className);
				}
			} catch (Exception ex) {
				System.out.println("[DBConnectionPool.loadDrivers] Can't Register JDBC Driver : " + className);
				throw new DBException(DBException.DRIVERONERROR, className);
			}
		}
	}

	/**
	 * Create instances of Pool based on the DBInformation array.
	 * A Pool can be defined with the following properties:
	 *
	 * db.pool.name				A Database pool name
	 * db.url					A Database url
	 * db.user					A Database user (optional)
	 * db.password				A Database user password (if user specified)
	 * db.initial.capacity		The initial number of connections
	 * db.max.capacity			The maximal number of connections
	 * db.capacity.increment	The incremental number of connections
	 */
	private void createPools(ConnectionParam[] connectionParams) throws DBException {

		if (poolList == null)
			poolList = new Hashtable(5);

		for (int i = 0; i < connectionParams.length; i++) {
			String poolName = this.poolName;
			if (!poolList.containsKey(poolName)) {
				Pool pool = new Pool(connectionParams[i], poolName, loginTimeout, initialCapacity,
				                     maxCapacity, capacityIncrement);
				poolList.put(poolName, pool);
				System.out.println("[DBConnectionPool.createPools] Create pool - " + poolName);
			}
		}
	}
	
	/**
	 * Verification Thread를 띄우는 부분 
	 */
	private void initializeVerification() {
		
		try {
			if (!isVerify)
				return;
				
			if (verificationInterval <= 0)
				return;
			
			if (verifier == null) {
				verifier = new DBConnectionVerifier("ACUBEIDIR", instance, verificationInterval);
				verifier.start();
				System.out.println("[ACUBE IDIR] Verification thread is started.");
			}
		} catch (Exception e) {
			System.out.println("[ACUBE IDIR] Fail to initialize verification ." + e.getMessage());
		}
	}
	
	/**
	 * Verification Thread를 종료하는 부분 
	 */
	private void releaseVerification() {
		
		if (verifier != null) {
			verifier.interrupt();
		}
	}
	
	//***************************************************************************
	// Constants
	//***************************************************************************
	
	public static final String SYSTEM_PROPERTY_BPM = "bpm";
	public static final int MAX_FAILURE_COUNT = 5;
	public static final long DEFAULT_TIMEOUT = 3000;
	
	public static final String CONNECTION_PARAM_PROPERTIES = "OrgConnection";
	
	public static final String PROP_ORGANIZATION_CONNECTION_POOL_NAME = "ORGANIZATION_CONNECTION_POOL_NAME";
	public static final String PROP_ORGANIZATION_CONNECTION_LOGIN_TIMEOUT = "ORGANIZATION_CONNECTION_LOGIN_TIMEOUT";
	public static final String PROP_ORGANIZATION_CONNECTION_INITIAL_CAPACITY = "ORGANIZATION_CONNECTION_INITIAL_CAPACITY";
	public static final String PROP_ORGANIZATION_CONNECTION_MAX_CAPACITY = "ORGANIZATION_CONNECTION_MAX_CAPACITY";
	public static final String PROP_ORGANIZATION_CONNECTION_CAPACITY_INCREMENT = "ORGANIZATION_CONNECTION_CAPACITY_INCREMENT";
	public static final String PROP_ORGANIZATION_CONNECTION_IS_VERIFY = "ORGANIZATION_CONNECTION_IS_VERIFY";
	public static final String PROP_ORGANIZATION_CONNECTION_VERIFICATION_INTERVAL = "ORGANIZATION_CONNECTION_VERIFICATION_INTERVAL";

	//***************************************************************************
	// Variables
	//***************************************************************************
	
	private static String poolName;
	private static int loginTimeout;
	private static int initialCapacity;
	private static int maxCapacity;
	private static int capacityIncrement;
	private static boolean isVerify;
	private static int verificationInterval;
	
	/**
	 */
	private static DBConnectionPool instance;
	/**
	 */
	private static DBConnectionVerifier verifier;
	private static Hashtable poolList;
	private static Hashtable drivers;
	private static int clients = 0;
	
	// private ConnectionParam[] connectionParams;

	//***************************************************************************
	// Inner Class
	//***************************************************************************

	/**
	 * This inner class represents a connection pool. 
	 * It creates new connections on demand, up to a max number if specified.
	 * It also makes sure a connection is still open before it is
	 * returned to a client.
	 */
	class Pool {

		/**
		 * Create new connection pool.
		 *
		 * @param connectionParam Connection 설정을 위한 Parameter 개체
		 * @param poolName Connection Pool Name
		 * @param loginTimeout Login Time out 시간 
		 * @param initialCapacity 초기 용량 
		 * @param maxCapacity 최대 용량 
		 * @param capacityIncrement 용량 증가
		 */
		protected Pool(ConnectionParam connectionParam,
					   String poolName,
					   int loginTimeout,
					   int initialCapacity,
					   int maxCapacity,
					   int capacityIncrement) throws DBException {

			this.uRL = connectionParam.getURL();
			this.poolName = poolName;
			this.user = connectionParam.getUser();
			this.password = connectionParam.getPassword();
			this.loginTimeout = loginTimeout;
			this.initialCapacity = initialCapacity;
			this.maxCapacity = maxCapacity;
			this.capacityIncrement = capacityIncrement;
			this.connectionsHigh = 0;
			this.waitSecondsHigh = 0;
			this.connectionsTotal = 0;
			this.connections = 0;

			freeConnections = new Vector();
		}

		/**
		 * 커넥션 풀의 초기 용량으로 커넥션을 생성
		 */
		protected synchronized void initializePool() throws DBException {
			
			System.out.println("[Pool.initializePool][" + poolName + "] Initialize pool...");
			while ((connectionsTotal < initialCapacity) && (connectionsTotal < maxCapacity)) {
				// 새로운 Connection 생성
				Connection conn = newConnection();
				if (conn != null)
					freeConnections.addElement(conn);		// Pool에 추가
			}
		}

		/**
		 * Check out a connection from the pool. 
		 * If no free connection is available, a new connection is created 
		 * unless the max number of connections has been reached. 
		 * If a free connection has been closed by the database, 
		 * it's removed from the pool and this method is called again recursively.
		 * 
		 * If no connection is available and the max number has been reached, 
		 * this method waits the specified time for one to be checked in.
		 *
		 * @param timeout The timeout value in milliseconds
		 */
		protected synchronized Connection getConnection() throws DBException {

			// Pool로부터 Connection을 얻음
			Connection conn = getUsedConnection();
			if (conn != null) {
				// 총 연결 수 증가
				connections++;
				// 연결 상한보다 총 연결 수가 많아진 경우 업데이트
				if (connections > connectionsHigh) {
					connectionsHigh = connections;
					System.out.println("[Pool.getConnection][" + poolName + "] Connections High : " + connectionsHigh);
				}

				return conn;
			} else {
				System.out.println("[Pool.getConnection][" + poolName + "] Waiting connection...");
				long startTime = new Date().getTime();
				while (true) {
					try {
						System.out.println("[Pool.getConnection][" + poolName + "] Login Timeout : " + loginTimeout);
						if (loginTimeout >= 0)
							this.wait(loginTimeout);
					} catch (InterruptedException e) { }
					
					conn = getUsedConnection();
					if (conn != null) {
						connections++;
						if (connections > connectionsHigh) {
							connectionsHigh = connections;
							System.out.println("[Pool.getConnection][" + poolName + "] Connections High : " + connectionsHigh);
						}

						// 연결 대기 시간 
						int elapsed = (int) ((new Date().getTime() - startTime) / 1000);
						// 대기 시간 상한보다 연결 대기 시간이 길면 업데이트
						if (elapsed > waitSecondsHigh) {
							waitSecondsHigh = elapsed;
							System.out.println("[Pool.getConnection][" + poolName + "] Wait Seconds High : " + waitSecondsHigh);
						}

						return conn;
					} else if ((new Date().getTime() - startTime) >= loginTimeout) {		// Timeout 경과
						waitSecondsHigh = (int) (loginTimeout / 1000);
						System.out.println("[Pool.getConnection][" + poolName + "] Wait Seconds High : " + waitSecondsHigh);
						throw new DBException(DBException.CONNECTIONFAIL, (DBException.exceptionCount + 1) + ". " + "Timeout has expired");
					}
				}
			}
		}

		/**
		 * Checks in a connection to the pool. 
		 * Notify other Threads that may be waiting for a connection.
		 *
		 * @param con The connection to check in
		 */
		protected synchronized void freeConnection(Connection conn) throws DBException {

			// Put the connection at the end of the Vector
			if (conn != null) {
				freeConnections.addElement(conn);
				if (connections > 0) {
					connections--;
				}
			}

			// getConnection()의 wait()에 알림
			notify();
		}	
				
		/**
		 * Closes all available connections.
		 */
		protected synchronized void release() {

			Enumeration allConnections = freeConnections.elements();
			while (allConnections.hasMoreElements()) {
				Connection conn = (Connection) allConnections.nextElement();
				try {
					conn.close();
				} catch (SQLException sqle) {
				} finally {
					conn = null;
				}
				connectionsTotal--;
				System.out.println("[Pool.release][" + poolName + "] ** Release physical connection");
			}
			System.out.println("[Pool.release][" + poolName + "] Close all connections");
			System.out.println("[Pool.release][" + poolName + "] Connections Total : " + connectionsTotal);

			freeConnections.removeAllElements();
			freeConnections = null;
		}
		
		/**
		 *	모든 free Connection 이 유효한지 Connection 테스트
		 */
		protected synchronized boolean verifyPool() {
			
			Enumeration allConnections = freeConnections.elements();
			int count = 0;
			int invalidCount = 0;
			
			System.out.println("[ACUBEIDIR][" + poolName + "] ** Verify physical connection ");
			while (allConnections.hasMoreElements()) {
				Connection conn = (Connection) allConnections.nextElement();
				count++;
				if (!isValid(conn)) {
					invalidCount++;
				}
			}
			System.out.println("[ACUBEIDIR][" + poolName + "] ** Verification Result : Total [" + count + "] / Invalid [" + invalidCount + "]");
			
			if (invalidCount > 0)
				return false;
			else 
				return true;
		}

		/**
		 * 커넥션 풀 정보 출력
		 * @param logger 커넥션 풀 정보를 출력할 Logger 개체
		 */
		protected void printPool() {

			System.out.println("URL : " + uRL);
			System.out.println("Pool Name : " + poolName);
			System.out.println("User : " + user);
			System.out.println("Password : " + password);
			System.out.println("Login Timeout : " + loginTimeout);
			System.out.println("Initial Capacity : " + initialCapacity);
			System.out.println("Max Capacity : " + maxCapacity);
			System.out.println("Capacity Increment : " + capacityIncrement);
			System.out.println("Connections High : " + connectionsHigh);
			System.out.println("Wait Seconds High : " + waitSecondsHigh);
			System.out.println("Connections Total : " + connectionsTotal);
			System.out.println("Connections : " + connections);
			System.out.println("Free Connections : " + freeConnections.size());
		}

		/**
		 * 커넥션 풀 정보 반환
		 */
		protected StringBuffer monitorPool() {

			StringBuffer sBuf = new StringBuffer();
			sBuf.append("URL : " + uRL + "||");
			sBuf.append("Pool Name : " + poolName + "||");
			sBuf.append("User : " + user + "||");
			sBuf.append("Password : " + password + "||");
			sBuf.append("Login Timeout : " + loginTimeout + "||");
			sBuf.append("Initial Capacity : " + initialCapacity + "||");
			sBuf.append("Max Capacity : " + maxCapacity + "||");
			sBuf.append("Capacity Increment : " + capacityIncrement + "||");
			sBuf.append("Connections High : " + connectionsHigh + "||");
			sBuf.append("Wait Seconds High : " + waitSecondsHigh + "||");
			sBuf.append("Connections Total : " + connectionsTotal + "||");
			sBuf.append("Connections : " + connections + "||");
			sBuf.append("Free Connections : " + freeConnections.size());
			
			return sBuf;
		}

		/**
		 * Creates a new connection, using a userid and password if specified.
		 */
		private Connection newConnection() throws DBException {

			Connection conn = null;
			try {
				if (user == null)
					conn = DriverManager.getConnection(uRL);
				else
					conn = DriverManager.getConnection(uRL, user, password);

				if (conn != null) {
					// 현재 용량 증가
					connectionsTotal++;
					System.out.println("[Pool.newConnection][" + poolName + "] ** Create physical connection");
					System.out.println("[Pool.newConnection][" + poolName + "] Connections Total : " + connectionsTotal);

					return conn;
				} else {
					System.out.println("[Pool.newConnection][" + poolName + "] !* Fail to Create physical connection");
					return null;
				}
			} catch (Exception ex) {
				throw new DBException(DBException.CONNECTIONFAIL, (DBException.exceptionCount + 1) + ". " + ex.getMessage());
			}
		}
		
		/**
		 * Checks out a connection from the pool. 
		 * If no free connection is available, a new connection is created 
		 * unless the max number of connections has been reached. 
		 * If a free connection has been closed by the database, 
		 * it's removed from the pool and this method is called again recursively.
		 */
		private Connection getUsedConnection() throws DBException {

			Connection conn = null;
			if (freeConnections.size() > 0) {		// Pool에 Connection이 남아있는 경우
				// Pick the first Connection in the Vector to get round-robin usage
				conn = (Connection) freeConnections.firstElement();
				freeConnections.removeElementAt(0);

				try {
					if (conn.isClosed()) {
						System.out.println("[Pool.getUsedConnection][" + poolName + "] Pooled connection has closed");
						
						// 유효하지 않은 커넥션이므로 풀에서 제거
						conn = null;
						connectionsTotal--;
						System.out.println("[Pool.getUsedConnection][" + poolName + "] ** Release physical connection");
						System.out.println("[Pool.getUsedConnection][" + poolName + "] Connections Total : " + connectionsTotal);
						
						// Try again recursively
						conn = getUsedConnection();
					} else if (!isValid(conn)) {
						System.out.println("[Pool.getUsedConnection][" + poolName + "] Pooled connection is invalid");
						
						// 유효하지 않은 커넥션이므로 풀에서 제거
						conn = null;
						connectionsTotal--;
						System.out.println("[Pool.getUsedConnection][" + poolName + "] ** Release physical connection");
						System.out.println("[Pool.getUsedConnection][" + poolName + "] Connections Total : " + connectionsTotal);
						
						// Try again recursively
						conn = getUsedConnection();
					}
				} catch (Exception ex) {				
					System.out.println("[Pool.getUsedConnection][" + poolName + "] Exception occured");
					
					// 유효하지 않은 커넥션이므로 풀에서 제거
					if (conn != null) {
						conn = null;
						connectionsTotal--;
						System.out.println("[Pool.getUsedConnection][" + poolName + "] ** Release physical connection");
						System.out.println("[Pool.getUsedConnection][" + poolName + "] Connections Total : " + connectionsTotal);
					}

					// Try again recursively
					conn = getUsedConnection();
				}
			} else if (connectionsTotal < maxCapacity) {		// 현재 용량이 최대 용량보다 작으면 확장
				System.out.println("[Pool.getUsedConnection][" + poolName + "] Increase connection");
				// 커넥션 풀 용량 확장
				increaseConnection();

				// Try again recursively
				conn = getUsedConnection();
			}

			return conn;
		}

		/**
		 * 용량 증가분 만큼의 커넥션을 새로 생성
		 */
		private void increaseConnection() throws DBException {

			System.out.println("[Pool.getUsedConnection][" + poolName + "] Capacity Increment : " + capacityIncrement);
			System.out.println("[Pool.getUsedConnection][" + poolName + "] Connections Total : " + connectionsTotal);
			System.out.println("[Pool.getUsedConnection][" + poolName + "] Max Capacity : " + maxCapacity);

			for (int i = 0; i < capacityIncrement; i++) {
				if (connectionsTotal < maxCapacity) {
					Connection conn = newConnection();
					if (conn != null)
						freeConnections.addElement(conn);
				} else {
					break;
				}
			}
		}

		/**
		 * 현재 Connection이 유효한 Connection인지 테스트
		 * @param connection
		 * @return
		 */
		private boolean isValid(Connection connection) {
			
			String testLevel = System.getProperty(SYSTEM_PROPERTY_CONNECTION_POOL_TEST_LEVEL);
			System.out.println("[Pool.getUsedConnection][" + poolName + "] " + SYSTEM_PROPERTY_CONNECTION_POOL_TEST_LEVEL + " : " + testLevel);
			if ((testLevel != null) && testLevel.equalsIgnoreCase(CONNECTION_POOL_TEST_LEVEL_HIGH)) {
				Statement statement = null;
				ResultSet resultSet = null;
				try {
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					resultSet = statement.executeQuery(CONNECTION_POOL_TEST_QUERY);
					return true;
				} catch (SQLException sqle) {
					System.out.println("[Pool.getUsedConnection][" + poolName + "] " + sqle);
					return false;
				} finally {
					try {
						if (resultSet != null)
							resultSet.close();
					} catch (SQLException ignore) { } 
					try {
						if (statement != null)
							statement.close();
					} catch (SQLException ignore) { } 
				}
			} else {
				try {
					connection.setAutoCommit(true);
					return true;
				} catch (SQLException sqle) {
					System.out.println("[Pool.getUsedConnection][" + poolName + "] " + sqle);
					return false;
				}
			}
		}

		static final String SYSTEM_PROPERTY_CONNECTION_POOL_TEST_LEVEL = "com.sds.acube.app.idir.org.ConnectionPoolTestLevel";
		static final String CONNECTION_POOL_TEST_LEVEL_HIGH = "high";
		static final String CONNECTION_POOL_TEST_QUERY = "SELECT 1 FROM DUAL";

		protected String poolName;				// Pool Name
		protected long loginTimeout;
		protected int initialCapacity;			// 초기 용량
		protected int maxCapacity;				// 최대 용량
		protected int capacityIncrement;			// 용량 증가분
		protected String uRL;
		protected String user;
		protected String password;
		protected int connectionsHigh;			// 연결 상한
		protected int waitSecondsHigh;			// 대기 시간 상한 (초)
		protected int connectionsTotal;			// 현재 용량
		protected int connections;				// 총 연결 수
		private Vector freeConnections;			// Pool
	}
}

