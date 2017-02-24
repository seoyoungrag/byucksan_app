package com.sds.acube.app.idir.org.db.pool;

import java.sql.SQLException;

/**
 * DBException class encapsulates the exception raised when connecting Database.
 * 
 * @see java.sql.SQLException
 * 
 * @author  MyungHee Jung
 * @version $Revision: 1.1 $
 * Last Revised $Date: 2009/03/25 01:56:26 $
 */

public class DBException extends SQLException {
	
	public static String _selfDescription() {
		return (" - " + _version + " - " + _lastUpdate);
	}
	
	//***************************************************************************
	// Constructor
	//***************************************************************************

	public DBException() {
		super();
		exceptionCount++;
	}

	/**
	 * Construct a DBException of a given type.
	 *
	 * @param type the type of the exception
	 */
	public DBException(int type) {
		super(msg[type]);
		exceptionCount++;
	}

	/**
	 * Construct a DBException from a given exception.
	 *
	 * @param ex the original exception
	 */
	public DBException(Exception ex) {
		super(msg[0] + ex.getMessage());
		exceptionCount++;
	}
	
	public DBException(String exMsg) {
		super(exMsg);
		exceptionCount++;
	}

	/**
	 * Construct a DBException of a given type and message.
	 *
	 * @param type the type of the exception
	 * @param str the additional exception message
	 */
	public DBException(int type, String str) {
		super(msg[type] + str);
		exceptionCount++;
	}
	
	//***************************************************************************
	// Constants
	//***************************************************************************

	public static final int SQLERROR = 0;
	public static final int DBNOTFOUND = 1;
	public static final int DRIVERNOTFOUND = 2;
	public static final int DBPOOLNOTFOUND = 3;
	public static final int DRIVERONERROR = 4;
	public static final int DRIVEROFFERROR = 5;
	public static final int CONNECTIONFAIL = 6;
	public static final int DBPOOLINIT = 7;
	
	/** Default Exception Messages */
	public static final String msg[] = {
			"General SQLException : ",
			"Database Not Found : ",
			"JDBC Driver Not Found ",
			"DB Pool Not Found : ",
			"Can't Register JDBC Driver : ",
			"Can't Deregister JDBC Driver : ",
			"Connection Failure : ",
			"DB Pool Initialization Failure : " 
	};

	//***************************************************************************
	// Variables
	//***************************************************************************
	
	public static int exceptionCount = 0;
	
	///////////////////////////////////////////////////////////////////////////
	private final static String _version = "$Revision: 1.1 $";
	private final static String _lastUpdate = "$Date: 2009/03/25 01:56:26 $";
	///////////////////////////////////////////////////////////////////////////
}
