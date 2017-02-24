package com.sds.acube.app.idir.org.db.pool;

/**
 * DBConnectionVerifier.java 2007-06-21 현재 생성된 Connection이 유효한지 검사하는 모듈
 * @author  kkang
 * @version  1.0.0.0  Copyright 2001 Samsung SDS Co., Ltd. All rights reserved.
 */
public class DBConnectionVerifier extends Thread {

	public DBConnectionVerifier(String module, DBConnectionPool pool, int interval) {
		
		super(module + "_verify");
		
		this.module = module;
		this.interval = interval;
		this.pool = pool;
	}
	
	/**
	 * Thread 실행 메서드.
	 */
	public void run() {
		
		try {			
			// connection 정보가 확인되었으면 메세지를 받는 함수
			while (!Thread.currentThread().isInterrupted()) {
			
				pool.verifyPools();
				
				sleep(interval);
			}
		} catch (InterruptedException e) {
		} catch (Exception e) {
		} finally {
		}
	}
		
	/**
	 */
	private DBConnectionPool pool;
	private String module;
	private int interval;
}
