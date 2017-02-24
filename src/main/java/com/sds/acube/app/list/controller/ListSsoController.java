/**
 * 
 */
package com.sds.acube.app.list.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.common.controller.BaseController;

/** 
 *  Class Name  : ListSsoController.java <br>
 *  Description :  보안토큰 및 인증서 관리 <br>
 *  Modification Information <br>
 *  
 *  수 정  일 : 2015. 7. 21. <br>
 *  수 정  자 : SNOTE  <br>
 *  수정내용 :  <br>
 * 
 *  @author  S 
 *  @since 2015. 7. 21.
 *  @version 1.0 
 *  @see  com.sds.acube.app.list.controller.ListSsoController.java
 */

@Controller("ListSsoController")
@RequestMapping("/app/sso/security/*.do")
public class ListSsoController extends BaseController{
	
    /**
     * <pre> 
     *  보안토큰 및 인증서 관리 선택시 매뉴얼 표시 화면으로 이동한다.
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/SecuTokenManual.do")
	public ModelAndView getSecuTokenManual(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 목록 타이틀
		String listTitle = "보안토큰 및 인증서 관리";
		
		ModelAndView mav = new ModelAndView("ListSsoController.getSecuTokenManual");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
    /**
     * <pre> 
     *  보안토큰 정보 목록을 조회한다. - 보안토큰정보
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/ListSecuTokenInfo.do")
	public ModelAndView ListSecuTokenInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 목록 타이틀
		String listTitle = "보안토큰정보";
		
		ModelAndView mav = new ModelAndView("ListSsoController.listSecuTokenInfo");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
    /**
     * <pre> 
     *  보안토큰 정보 목록 - 보안토큰 등록 버튼 선택
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/secuTokenReg.do")
	public ModelAndView setSecuTokenReg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 타이틀
		String listTitle = "보안토큰 등록";
		
		ModelAndView mav = new ModelAndView("ListSsoController.setSecuTokenReg");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
	
	/**
     * <pre> 
     *  보안토큰 정보 목록 - 폐기 버튼 선택
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/discardToken.do")
	public ModelAndView setDiscardToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 타이틀
		String listTitle = "보안토큰 폐기";
		
		ModelAndView mav = new ModelAndView("ListSsoController.setDiscardToken");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
	/**
     * <pre> 
     *  보안토큰 정보 목록 - 상세정보보기 버튼 선택(보안토큰 조회)
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/detailTokenInfo.do")
	public ModelAndView getDetailTokenInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 타이틀
		String listTitle = "보안토큰 조회";
		
		ModelAndView mav = new ModelAndView("ListSsoController.getDetailTokenInfo");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
	/**
     * <pre> 
     *  보안토큰 정보 목록 - 검색 버튼 선택 (보안토큰 검색)
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/secuTokenSearch.do")
	public ModelAndView getSecuTokenSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 타이틀
		String listTitle = "보안토큰 검색";
		
		ModelAndView mav = new ModelAndView("ListSsoController.getSecuTokenSearch");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
    /**
     * <pre> 
     *  사원명으로 인증서 검색 후 인증서를 변경 또는 삭제한다. - 인증서 폐기
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/secuTokenDiscard.do")
	public ModelAndView getSecuTokenDiscard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 목록 타이틀
		String listTitle = "인증서 폐기";
		
		ModelAndView mav = new ModelAndView("ListSsoController.getSecuTokenDiscard");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
    /**
     * <pre> 
     *  사용자 확인 후 인증서(보안토큰) 신규발급한다. - 인증서 신규발급
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/certNewIssue.do")
	public ModelAndView getCertNewIssue(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 목록 타이틀
		String listTitle = "인증서 신규발급";
		
		ModelAndView mav = new ModelAndView("ListSsoController.getCertNewIssue");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
    /**
     * <pre> 
     *  보안토큰 비밀번호 변경 - 인증서 비밀번호 변경
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/certPwdChange.do")
	public ModelAndView setCertPwdChange(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 목록 타이틀
		String listTitle = "인증서 비밀번호 변경";
		
		ModelAndView mav = new ModelAndView("ListSsoController.setCertPwdChange");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
    /**
     * <pre> 
     *  보안토큰 신청 - 보안토큰 발급 신청 
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/tokenIssueReq.do")
	public ModelAndView setTokenIssueReq(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 목록 타이틀
		String listTitle = "발급신청";
		
		ModelAndView mav = new ModelAndView("ListSsoController.setTokenIssueReq");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
    /**
     * <pre> 
     *  보안토큰 신청 - 나의 신청내역
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/ListUserReq.do")
	public ModelAndView getListUserReq(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 목록 타이틀
		String listTitle = "나의 신청내역";
		
		ModelAndView mav = new ModelAndView("ListSsoController.getListUserReq");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
    /**
     * <pre> 
     *  보안토큰 부가기능 - 캐시설정
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/tokenCacheSetting.do")
	public ModelAndView setTokenCacheSetting(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 목록 타이틀
		String listTitle = "캐시설정";
		
		ModelAndView mav = new ModelAndView("ListSsoController.setTokenCacheSetting");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
    /**
     * <pre> 
     *  보안토큰 부가기능 - 인증서 조회
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/certificationCheck.do")
	public ModelAndView getCertificationCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 목록 타이틀
		String listTitle = "인증서 조회";
		
		ModelAndView mav = new ModelAndView("ListSsoController.getCertificationCheck");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
    /**
     * <pre> 
     *  보안토큰 관리자 - 관리대장
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/ListTokenManage.do")
	public ModelAndView getListTokenmanage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 목록 타이틀
		String listTitle = "관리대장";
		
		ModelAndView mav = new ModelAndView("ListSsoController.getListTokenmanage");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
	/**
     * <pre> 
     *  보안토큰 관리자 - 관리대장 (정보수정)
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/modifyTokenInfo.do")
	public ModelAndView setModifyTokenInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 타이틀
		String listTitle = "보안토큰 정보수정";
		
		ModelAndView mav = new ModelAndView("ListSsoController.setModifyTokenInfo");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
	
	/**
     * <pre> 
     *  보안토큰 관리자 - 관리대장 (정보수정) - 취급자 선택 - 취급자검색 팝업
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/tokenHandlerSearch.do")
	public ModelAndView getTokenHandlerSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 타이틀
		String listTitle = "취급자검색";
		
		ModelAndView mav = new ModelAndView("ListSsoController.getTokenHandlerSearch");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
    /**
     * <pre> 
     *  보안토큰 관리자 - 발급 및 취소승인
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/ListTokenIssueDecision.do")
	public ModelAndView getListTokenIssueDecision(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 목록 타이틀
		String listTitle = "발급 및 취소승인";
		
		ModelAndView mav = new ModelAndView("ListSsoController.getListTokenIssueDecision");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
    /**
     * <pre> 
     *  보안토큰 관리자 - 보안토큰수량관리
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/ListTokenNumManage.do")
	public ModelAndView getListTokenNumManage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 목록 타이틀
		String listTitle = "보안토큰수량관리";
		
		ModelAndView mav = new ModelAndView("ListSsoController.getListTokenNumManage");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
	
	/**
     * <pre> 
     *  보안토큰 관리자 - 보안토큰수량관리 - 지역본부 배부 버튼 선택시 팝업 호출(배부처리)
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/tokenIssueProcess.do")
	public ModelAndView setTokenIssueProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 타이틀
		String listTitle = "지역본부 배부";
		
		
		ModelAndView mav = new ModelAndView("ListSsoController.setTokenIssueProcess");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
	/**
     * <pre> 
     *  보안토큰 관리자 - 보안토큰수량관리 - 입고 버튼 선택시 팝업 호출(입고)
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/tokenStore.do")
	public ModelAndView setTokenStore(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 타이틀
		String listTitle = "입고";
		
		ModelAndView mav = new ModelAndView("ListSsoController.setTokenStore");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
	
    /**
     * <pre> 
     *  보안토큰 관리자 - 보안토큰 관리권한
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/ListTokenManageAuth.do")
	public ModelAndView getListTokenManageAuth(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 목록 타이틀
		String listTitle = "보안토큰 관리권한";
		
		ModelAndView mav = new ModelAndView("ListSsoController.getListTokenManageAuth");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
	/**
     * <pre> 
     *  보안토큰 관리자 - 보안토큰 관리권한 - 등록버튼(등록팝업 호출)
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/tokenAuthReg.do")
	public ModelAndView setTokenAuthReg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 타이틀
		String listTitle = "보안토큰관리권한 등록";
		
		ModelAndView mav = new ModelAndView("ListSsoController.setTokenAuthReg");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
	
	/**
     * <pre> 
     *  보안토큰 관리자 - 보안토큰 관리권한 - 검색버튼(보안토큰관리권한 검색)
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/TokenAuthSearch.do")
	public ModelAndView getTokenAuthSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 타이틀
		String listTitle = "보안토큰관리권한 검색";
		
		ModelAndView mav = new ModelAndView("ListSsoController.getTokenAuthSearch");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
	
	
    /**
     * <pre> 
     *  보안토큰 관리자 - 발급대장
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/ListTokenIssue.do")
	public ModelAndView getListTokenIssue(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 목록 타이틀
		String listTitle = "발급대장";
		
		ModelAndView mav = new ModelAndView("ListSsoController.getListTokenIssue");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
	
	
	/**
     * <pre> 
     *  보안토큰 관리자 - 발급대장 - 검색
     * </pre>
     * 
     * @param
     * @return
     * @throws Exception
     * @see
     */
	@RequestMapping("/app/sso/security/tokenIssueSearch.do")
	public ModelAndView getTokenIssueSearch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 타이틀
		String listTitle = "발급대장 검색";
		
		ModelAndView mav = new ModelAndView("ListSsoController.getTokenIssueSearch");
		mav.addObject("listTitle", listTitle);
		return mav;
	}
}
