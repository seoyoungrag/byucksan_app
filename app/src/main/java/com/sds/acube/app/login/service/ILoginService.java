package com.sds.acube.app.login.service;

import com.sds.acube.app.login.vo.LoginHistoryVO;
import com.sds.acube.app.login.vo.LoginVO;


public interface ILoginService {
	public UserProfileVO loginProcess(LoginVO param) throws Exception;