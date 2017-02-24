/**
 * 
 */
package com.sds.acube.app.common.util;

import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.common.vo.BizProcVO;
import com.sds.acube.app.exchange.service.IExchangeService;

/** 
 *  Class Name  : HttpSendThread.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2015. 6. 5. <br>
 *  수 정  자 : kjk  <br>
 *  수정내용 :  <br>
 * 
 *  @author  kjk 
 *  @since 2015. 6. 5.
 *  @version 1.0 
 *  @see  com.sds.acube.app.common.util.HttpSendThread.java
 */

public class HttpSendThread implements Runnable{
	private BizProcVO bizProcVO;
	private AppDocVO appDocVO;
	private IExchangeService exchangeService;
	
	public HttpSendThread(IExchangeService exchangeService, BizProcVO bizProcVO, AppDocVO appDocVO){
		this.bizProcVO = bizProcVO;
		this.appDocVO = appDocVO;
		this.exchangeService = exchangeService;
	}
	
	@Override
	public void run(){
		try{
			exchangeService.directAckSend(bizProcVO, appDocVO);
		}catch(InterruptedException e){
		    e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

}
