package com.sds.acube.app.idir.org.ws;

import java.util.ResourceBundle;

import com.sds.acube.app.idir.common.vo.ConnectionParam;

/**
 * BaseService.java 2009-03-31 웹서비스 호출 관련
 * @author  kkang
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */

public class BaseService {
	
	/**
	 * Constructor
	 */
	public BaseService() {
		
		loadConnectionParam();
	}

	/**
	 * 웹 서비스 호출 관련 테스트 메서드.
	 * @param inputMessage 입력된 메시지
	 * @return String
	 */
	public String getTestMessage(String inputMessage) {
		
		return "[Input Message] " + inputMessage;
	}
	
	/**
	 * Connection 정보 관련 메서드
	 * @param licenseKey
	 * @return String
	 */
	public String getConnectionInfo(String licenseKey) {
		
		StringBuffer connectionInfo = new StringBuffer();
		
		connectionInfo.append("[Method] " + connectionParam.getMethod() + "\n\r");
		connectionInfo.append("[Class Name] " + connectionParam.getClassName() + "\n\r");
		connectionInfo.append("[URL] " + connectionParam.getURL() + "\n\r");
		connectionInfo.append("[DS Name] " + connectionParam.getDSName() + "\n\r");
		connectionInfo.append("[DB Type] " + connectionParam.getDBType() + "\n\r");
		connectionInfo.append("[WAS Type] " + connectionParam.getWASType() + "\n\r");
		
		return connectionInfo.toString();
	}
	
	/**
	 * LinkedList를 Value Object의 Array로 만드는 부분.
	 * @param linkedList Value Object의 LinkedList
	 * @return Object[]
	 */
/*	protected Object[] toArray(LinkedList linkedList) {
		
		if ((linkedList == null) || (linkedList.size() == 0)) 
			return (new Object[0]);
		
		Object[] objects = new Object[linkedList.size()];
		
		for (int i = 0; i < linkedList.size(); i++) 
			objects[i] = linkedList.get(i);
		
		return objects;
	}
	*/
	/**
	 * Connection Parameter 정보를 읽어오는 부분.
	 */
	protected void loadConnectionParam() {
		
		connectionParam = new ConnectionParam();
		
		connectionParam.setMethod(Integer.parseInt(bundle.getString(propConnectionMethod)));
		connectionParam.setClassName(bundle.getString(propConnectionClassName));
		connectionParam.setURL(bundle.getString(propConnectionUrl));
		connectionParam.setDSName(bundle.getString(propConnectionDSName));
		connectionParam.setDBType(Integer.parseInt(bundle.getString(propConnectionDBType)));
		connectionParam.setWASType(Integer.parseInt(bundle.getString(propConnectionWASType)));
	}
	
	/**
	 */
	protected ConnectionParam connectionParam;
	
	private static ResourceBundle bundle = ResourceBundle.getBundle("com.sds.acube.app.idir.org.service.OrgServiceEnv");
	
	
	private static String propConnectionMethod = "ORGANIZATION_CONNECTION_METHOD";
	private static String propConnectionClassName = "ORGANIZATION_CONNECTION_CLASSNAME";
	private static String propConnectionUrl = "ORGANIZATION_CONNECTION_URL";
	private static String propConnectionDSName = "ORGANIZATION_CONNECTION_DSNAME";
	private static String propConnectionDBType = "ORGANIZATION_CONNECTION_DB_TYPE";
	private static String propConnectionWASType = "ORGANIZATION_CONNECTION_WAS_TYPE";
}
