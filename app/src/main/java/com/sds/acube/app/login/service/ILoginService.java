package com.sds.acube.app.login.service;

import com.sds.acube.app.login.vo.LoginHistoryVO;import com.sds.acube.app.login.vo.UserProfileVO;
import com.sds.acube.app.login.vo.LoginVO;


public interface ILoginService {
	public UserProfileVO loginProcess(LoginVO param) throws Exception;	public UserProfileVO loginProcessByUserId(String userId) throws Exception;	public int insertLoginHistory(LoginHistoryVO vo) throws Exception;		public int removeAccessHistory() throws Exception;}
