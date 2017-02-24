/*
 * @(#) LoginController.java 2010. 5. 25.
 * Copyright (c) 2010 Samsung SDS Co., Ltd. All Rights Reserved.
 */
package com.sds.acube.app.login.controller;
import java.util.List;


/**
    /**
    /**

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/app/login/loginProcess.do")
	logger.debug("loginProcessByPortlet called...");
	//LoginVO loginVO = new LoginVO();
	LoginVO loginVO = (LoginVO)Transform.transformToDmo("com.sds.acube.app.login.vo.LoginVO", request);
	String userIp = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
	if(!AppConfig.getBooleanProperty("use_https", true)) {
	    // Seed Decode
	    SeedBean.setRoundKey(request);
	    loginVO.setLoginId(SeedBean.doDecode(request, loginVO.getLoginId()));
	    loginVO.setPassword(SeedBean.doDecode(request, loginVO.getPassword()));
	}
	if((AppConfig.getProperty("systemUser", "", "systemOperation")).equals(loginVO.getLoginId())) {
	UserProfileVO userProfile = loginService.loginProcess(loginVO);
	HttpSession session = request.getSession();
	ModelAndView mav = new ModelAndView();
	} else if(userProfile.getLoginResult() == ConstantList.LOGIN_FAIL_NO_ID) {		
	return mav;
    }
    private void setUserSession(HttpSession session, UserProfileVO userProfileVO, String userIp) throws Exception {
	HttpSession session = request.getSession();
	UserProfileVO userProfile = (UserProfileVO) session.getAttribute("userProfile");
	if(userProfile!=null) {
	    logger.info(userProfile.getLoginId()+" logout..");
	    String dhu006 = appCode.getProperty("DHU006", "DHU006", "DHU"); // 로그아웃
	return mav;
    }
}