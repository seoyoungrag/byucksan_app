/**
 * 
 */
package com.sds.acube.app.env.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.anyframe.util.StringUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.OrgUtil;
import com.sds.acube.app.common.util.Transform;
import com.sds.acube.app.common.vo.AddressVO;
import com.sds.acube.app.common.vo.DepartmentVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.env.service.IEnvDocNumRuleService;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.util.OptionUtil;
import com.sds.acube.app.env.vo.EmptyInfoVO;
import com.sds.acube.app.env.vo.FormEnvVO;
import com.sds.acube.app.env.vo.LineGroupVO;
import com.sds.acube.app.env.vo.LineUserVO;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.env.vo.PubViewGroupVO;
import com.sds.acube.app.env.vo.PubViewUserVO;
import com.sds.acube.app.env.vo.RecvGroupVO;
import com.sds.acube.app.env.vo.RecvUserVO;
import com.sds.acube.app.env.vo.SenderTitleVO;
import com.sds.acube.app.env.vo.ShareDocDeptVO;
import com.sds.acube.app.idir.ldaporg.client.LDAPOrganizations;
import com.sds.acube.app.idir.org.orginfo.OrgImage;
import com.sds.acube.app.login.security.EnDecode;
import com.sds.acube.app.login.security.seed.SeedBean;
import com.sds.acube.app.login.vo.UserProfileVO;

/**
 * Class Name  : EnvOptionController.java <br> Description : 결재옵션 조회 및 수정 <br> Modification Information <br> <br> 수 정  일 : 2011. 3. 22. <br> 수 정  자 : 신경훈  <br> 수정내용 :  <br>
 * @author   신경훈 
 * @since  2011. 3. 22.
 * @version  1.0 
 * @see  com.sds.acube.app.env.controller.EnvOptionController.java
 */

@SuppressWarnings("serial")
@Controller("envOptionController")
@RequestMapping("/app/env/*.do")
public class EnvOptionController extends BaseController {
    
    @Inject
    @Named("messageSource")
    MessageSource messageSource;
    
    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;   

    /**
	 */
    @Inject
    @Named("envDocNumRuleService")
    private IEnvDocNumRuleService envDocNumRuleService;
    
    /**
	 */
    @Autowired
    private IOrgService orgService;
    
    /**
	 */
    @Autowired
    private IEnvUserService envUserService;
    
    private final ObjectMapper mapper = new ObjectMapper();
    
    /**
	 */
    private OptionUtil optionUtil = new OptionUtil();
    

    /** 
     *      
     * <pre> 
     *  요청유형 사용여부 조회
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectOptionProcess.do") 
    public ModelAndView selectOptionProcess(HttpServletRequest req) throws Exception {
	
		HttpSession session = req.getSession();	
	
		ModelAndView mav = new ModelAndView("EnvController.admin.selectOptionProcess");	
		
		// 다국어 추가
		String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();
		mav.addObject("VOLists", envOptionAPIService.selectOptionGroupListResourceOrderBySequence((String)session.getAttribute("COMP_ID"), "OPTG000", langType));		
		// mav.addObject("VOLists", envOptionAPIService.selectOptionGroupListOrderBySequence((String)session.getAttribute("COMP_ID"), "OPTG000"));

		mav.addObject("VOMap300", envOptionAPIService.selectOptionGroupMap((String)session.getAttribute("COMP_ID"), "OPTG300"));
		mav.addObject("VOMap400", envOptionAPIService.selectOptionGroupMap((String)session.getAttribute("COMP_ID"), "OPTG400"));
		mav.addObject("map417", envOptionAPIService.selectOptionTextMap((String)session.getAttribute("COMP_ID"), "OPT417")); // 직인 및 서명인 날인 신청 사용.
		mav.addObject("map428", envOptionAPIService.selectOptionTextMap((String)session.getAttribute("COMP_ID"), "OPT428")); // 문서편집기 선택 사용
		
		return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  요청유형 사용여부 수정
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/updateOptionProcess.do") 
    public ModelAndView updateOptionProcess(HttpServletRequest req) throws Exception 
    {
		HttpSession session = req.getSession();
		String compId = (String)session.getAttribute("COMP_ID");
		
		// [OPTG000] grouping BEGIN. edited by bonggon.choi. 2012.03.27.
		List<OptionVO> OPTG000VOList = new ArrayList<OptionVO>();
		int OPTG000ListSize = Integer.parseInt(req.getParameter("voSize"));
		OptionVO vo = null;
		
		Enumeration<String> OPTG000Enum = req.getParameterNames();
		List<String> OPTG000List = new ArrayList<String>();
		
		while(OPTG000Enum.hasMoreElements())
		{
			String tmp = OPTG000Enum.nextElement();
			if (tmp.endsWith(".Text"))
				OPTG000List.add(tmp);
		}
		
		for (int i=0; i < OPTG000ListSize; i++) {
		    vo = new OptionVO();
		    vo.setCompId(compId);
		    String optionId = OPTG000List.get(i);
		    String optionValue = req.getParameter(optionId);		// [optionId include ".Text"]    
		    optionId = optionId.substring(0, 6);					// [remove ".Text"]
		    
		    vo.setOptionId(optionId);
		    vo.setOptionValue(optionValue);
		    
		    if ("OPT001".equals(optionId) || "OPT002".equals(optionId) || "OPT014".equals(optionId) || "OPT020".equals(optionId)) { // 기안, 검토, 결재, 담당
		    	vo.setUseYn("Y");
		    } else {
		    	String useYn = "";
		    	// 협조(기안), 협조(검토), 협조(결재), 감사(기안), 감사(검토), 감사(결재), 합의(기안), 합의(검토), 합의(결재)
		    	if ("OPT006".equals(optionId) || "OPT007".equals(optionId) || "OPT008".equals(optionId) 
						|| "OPT011".equals(optionId) || "OPT012".equals(optionId) || "OPT013".equals(optionId)
						|| "OPT056".equals(optionId) || "OPT057".equals(optionId) || "OPT058".equals(optionId) ) { 
				    useYn = req.getParameter(optionId+"Val");
				} else {
				    useYn = optionUtil.checkboxValue(req.getParameter(optionId));
				}
				vo.setUseYn(useYn);
		    }
		    
		    OPTG000VOList.add(vo);
		}
		// [OPTG000] grouping END.
		
		// 기본 결재옵션
		envOptionAPIService.updateOptionList(OPTG000VOList);	
		
		// 결재유형 관련 세부 옵션
		envOptionAPIService.updateOptionUseYn(compId, "OPT350", optionUtil.checkboxValue(req.getParameter("OPT350"))); // 일괄기안 사용여부
		envOptionAPIService.updateOptionUseYn(compId, "OPT325", optionUtil.checkboxValue(req.getParameter("OPT325"))); // 이중결재 사용여부
		envOptionAPIService.updateOptionUseYn(compId, "OPT378", optionUtil.checkboxValue(req.getParameter("OPT378"))); // 처리과만 결재요청유형 선택
		envOptionAPIService.updateOptionUseYn(compId, "OPT320", optionUtil.checkboxValue(req.getParameter("OPT320"))); // CEO 결재문서 감사
		
		// 심사기능 관련 세부 옵션
		envOptionAPIService.updateOptionUseYn(compId, "OPT345", optionUtil.checkboxValue(req.getParameter("OPT345"))); // 심사기능 사용
		envOptionAPIService.updateOptionUseYn(compId, "OPT415", optionUtil.checkboxValue(req.getParameter("OPT415"))); // 기안/발송담당자 발송 사용
		envOptionAPIService.updateOptionUseYn(compId, "OPT373", optionUtil.checkboxValue(req.getParameter("OPT373"))); // 직인날인신청 사용
		envOptionAPIService.updateOptionUseYn(compId, "OPT367", optionUtil.checkboxValue(req.getParameter("OPT367"))); // 서명인날인신청 사용
		envOptionAPIService.updateOptionUseYn(compId, "OPT416", optionUtil.checkboxValue(req.getParameter("OPT416"))); // 기안/발송담당자 날인 허용
		
		// 생략인 사용
		envOptionAPIService.updateOptionValue(compId, "OPT417", req.getParameter("OPT417optionValue"));
		
		// 감사표시
		envOptionAPIService.updateOptionUseYn(compId, "OPT304", req.getParameter("OPT304"));
		
		// 문서발송
		envOptionAPIService.updateOptionUseYn(compId, "OPT351", optionUtil.checkboxValue(req.getParameter("OPT351"))); // 수신자 재발송
		envOptionAPIService.updateOptionUseYn(compId, "OPT315", optionUtil.checkboxValue(req.getParameter("OPT315"))); // 수신자 추가발송
		envOptionAPIService.updateOptionUseYn(compId, "OPT349", optionUtil.checkboxValue(req.getParameter("OPT349"))); // 기본발신명의(부서명+장) 사용
		envOptionAPIService.updateOptionUseYn(compId, "OPT327", optionUtil.checkboxValue(req.getParameter("OPT327"))); // 상위부서 발신명의 사용
		envOptionAPIService.updateOptionUseYn(compId, "OPT364", optionUtil.checkboxValue(req.getParameter("OPT364"))); // 부서발신명의 관리기능 사용
		
		// 문서배부
		envOptionAPIService.updateOptionUseYn(compId, "OPT427", optionUtil.checkboxValue(req.getParameter("OPT427"))); // 다중문서배부 사용
		
		// 문서편집기 선택 
		envOptionAPIService.updateOptionValue(compId, "OPT428", req.getParameter("OPT428optionValue"));
		
		// 문서접수
		envOptionAPIService.updateOptionUseYn(compId, "OPT341", req.getParameter("OPT341"));
		
		// 이송/경유
		envOptionAPIService.updateOptionUseYn(compId, "OPT401", optionUtil.checkboxValue(req.getParameter("OPT401"))); // 접수전 이송
		envOptionAPIService.updateOptionUseYn(compId, "OPT402", optionUtil.checkboxValue(req.getParameter("OPT402"))); // 접수후 이송
		envOptionAPIService.updateOptionUseYn(compId, "OPT403", optionUtil.checkboxValue(req.getParameter("OPT403"))); // 접수후 경유

		envOptionAPIService.optionReload(compId);
		
		ModelAndView mav = new ModelAndView("EnvController.procResultOption");
		mav.addObject("msg", "env.option.msg.success.save");
		mav.addObject("url", "/app/env/admin/selectOptionProcess.do");
		return mav;
    }
    
   
    /** 
     * 
     * <pre> 
     *  함 사용여부 조회
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectOptionBox.do") 
    public ModelAndView selectOptionBox(HttpServletRequest req) throws Exception {
	
	HttpSession session = req.getSession();

	ModelAndView mav = new ModelAndView("EnvController.admin.selectOptionBox");	
	
	// 다국어 추가
	String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();
	List<OptionVO> optg100 = envOptionAPIService.selectOptionGroupListResource((String)session.getAttribute("COMP_ID"), "OPTG100", langType); 
	// List<OptionVO> optg100 = envOptionAPIService.selectOptionGroupList((String)session.getAttribute("COMP_ID"), "OPTG100");
	
	Iterator it = optg100.iterator();
	
	while (it.hasNext()) {
		OptionVO toBeRemoved = (OptionVO)it.next();
		if ( toBeRemoved.getOptionId().equals("OPT114")) //optg100.remove("OPT114"); // 감사열람함	(구:감사부서열람함) edited by emptyColor. 2012.04.25.
			it.remove();
		else if ( toBeRemoved.getOptionId().equals("OPT120")) //optg100.remove("OPT120"); // 여신문서함
			it.remove();
		else if ( toBeRemoved.getOptionId().equals("OPT188")) //optg100.remove("OPT188"); // 부서별 발송대기문서 현황(관리자) 
			it.remove();
		else if ( toBeRemoved.getOptionId().equals("OPT189")) //optg100.remove("OPT189"); // 부서별 접수대기문서 현황(관리자) 
			it.remove();
		else if ( toBeRemoved.getOptionId().equals("OPT190")) //optg100.remove("OPT190"); // 개인별 미처리문서 현황(관리자) 
			it.remove();
	}
	
	mav.addObject("VOLists", optg100);
	mav.addObject("VOMap", envOptionAPIService.selectOptionGroupMap((String)session.getAttribute("COMP_ID"), "OPTG300"));
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  함 사용여부 수정
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/updateOptionBox.do") 
    public ModelAndView updateOptionBox(HttpServletRequest req) throws Exception {
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	
	List<OptionVO> voList = new ArrayList<OptionVO>();
	
	  // 다국어 추가
	String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();
	List<OptionVO> optg100 = envOptionAPIService.selectOptionGroupListResource((String)session.getAttribute("COMP_ID"), "OPTG100", langType); 
	//List<OptionVO> optg100 = envOptionAPIService.selectOptionGroupList((String)session.getAttribute("COMP_ID"), "OPTG100"); 
	
	int listSize = optg100.size();
	
	for (int i=1; i<=listSize; i++) {
	    OptionVO vo = new OptionVO();
	    vo.setCompId(compId);
	    String optionId = "OPT1";
	    optionId = optionId + String.format("%02d", i);
	    vo.setOptionId(optionId);
	    vo.setUseYn(optionUtil.checkboxValue(req.getParameter(optionId)));
	    vo.setOptionValue(CommonUtil.nullTrim(req.getParameter(optionId+"Value")));
	    if ( optionId.equals("OPT114") ) continue; // edited by emptyColor. 2012.04.25. 감사열람함 이동: 감사옵션 메뉴.
	    voList.add(vo);
	}
	
	envOptionAPIService.updateOptionList(voList);
	envOptionAPIService.updateOptionUseYn(compId, "OPT302", optionUtil.checkboxValue(req.getParameter("OPT302"))); // 임시저장함 상신 후 삭제
	envOptionAPIService.updateOptionUseYn(compId, "OPT339", req.getParameter("OPT339")); // 임원결재함 열람범위
	envOptionAPIService.updateOptionUseYn(compId, "OPT365", req.getParameter("OPT365")); // 공람함 기본검색조건
	envOptionAPIService.updateOptionUseYn(compId, "OPT383", req.getParameter("OPT383")); // 결재 진행문서를 결재 전 사용자가 조회할 수 있는 여부
	
	envOptionAPIService.optionReload(compId);
	
	ModelAndView mav = new ModelAndView("EnvController.procResultOption");
	mav.addObject("msg", "env.option.msg.success.save");
	mav.addObject("url", "/app/env/admin/selectOptionBox.do");
	return mav;
    }
    
    
    /** 
     * 
     * <pre> 
     *  대장 사용여부 조회
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectOptionRegister.do") 
    public ModelAndView selectOptionRegister(HttpServletRequest req) throws Exception 
    {
		HttpSession session = req.getSession();
	
		ModelAndView mav = new ModelAndView("EnvController.admin.selectOptionRegister");	
		
		// 다국어 추가
		String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();		
		List<OptionVO> optg200 = envOptionAPIService.selectOptionGroupListResource((String)session.getAttribute("COMP_ID"), "OPTG200", langType);
		// List<OptionVO> optg200 = envOptionAPIService.selectOptionGroupList((String)session.getAttribute("COMP_ID"), "OPTG200");
		
		HashMap<String, OptionVO> optg300 = envOptionAPIService.selectOptionGroupMap((String)session.getAttribute("COMP_ID"), "OPTG300");
		
		Iterator it = optg200.iterator();
		
		while (it.hasNext()) {
			OptionVO toBeRemoved = (OptionVO)it.next();
			if ( toBeRemoved.getOptionId().equals("OPT206") || toBeRemoved.getOptionId().equals("OPT207") || toBeRemoved.getOptionId().equals("OPT208"))
				it.remove();
		}
		
		//optg200.remove("OPT206"); // 감사문서함	(구:일일감사대장) edited by emptyColor. 2012.04.25.
		//optg200.remove("OPT207"); // 일상감사대장 (구:일상감사일지)
		//optg200.remove("OPT208"); // 감사직인날인대장
		
		mav.addObject("VOLists", optg200);
		mav.addObject("VOMap", optg300);
		mav.addObject("map382", envOptionAPIService.selectOptionTextMap((String)session.getAttribute("COMP_ID"), "OPT382")); // 문서등록대장 열람옵션
		return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  대장 사용여부 수정
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/updateOptionRegister.do") 
    public ModelAndView updateOptionRegister(HttpServletRequest req) throws Exception {
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	List<OptionVO> voList = new ArrayList<OptionVO>();
	
	  // 다국어 추가
	String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();
	List<OptionVO> optg200 = envOptionAPIService.selectOptionGroupListResource((String)session.getAttribute("COMP_ID"), "OPTG200", langType);
	// List<OptionVO> optg200 = envOptionAPIService.selectOptionGroupList((String)session.getAttribute("COMP_ID"), "OPTG200"); 
	
	int listSize = optg200.size();
	
	for (int i=1; i<=listSize; i++) {
	    OptionVO vo = new OptionVO();
	    vo.setCompId(compId);
	    String optionId = "OPT2";
	    optionId = optionId + String.format("%02d", i);
	    vo.setOptionId(optionId);
	    vo.setUseYn(optionUtil.checkboxValue(req.getParameter(optionId)));
	    vo.setOptionValue(CommonUtil.nullTrim(req.getParameter(optionId+"Value")));
	    if ( optionId.equals("OPT206") || optionId.equals("OPT207") || optionId.equals("OPT208") ) continue; // edited by emptyColor. 2012.04.25. 감사옵션으로 이동.
	    voList.add(vo);
	}
	envOptionAPIService.updateOptionList(voList);	
	envOptionAPIService.updateOptionUseYn(compId, "OPT338", optionUtil.checkboxValue(req.getParameter("OPT338"))); // 연도/회기별 문서목록 조회
	envOptionAPIService.updateOptionUseYn(compId, "OPT368", optionUtil.checkboxValue(req.getParameter("OPT368"))); // 등록대장 생산문서 간편조회 사용
	envOptionAPIService.updateOptionUseYn(compId, "OPT369", optionUtil.checkboxValue(req.getParameter("OPT369"))); // 등록대장 접수문서 간편조회 사용
	envOptionAPIService.updateOptionValue(compId, "OPT382", optionUtil.nullToDefault(req.getParameter("OPT382optionValue"), "I1:N^I2:N")); //문서등록대장 열람옵션
	envOptionAPIService.optionReload(compId);

	ModelAndView mav = new ModelAndView("EnvController.procResultOption");
	mav.addObject("msg", "env.option.msg.success.save");
	mav.addObject("url", "/app/env/admin/selectOptionRegister.do");
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  일상감사-감사대장 사용여부 조회
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectOptionInspection.do") 
    public ModelAndView selectOptionInspection(HttpServletRequest req) throws Exception {
    	
    	HttpSession session = req.getSession();
    	
    	ModelAndView mav = new ModelAndView("EnvController.admin.selectOptionInspection");	

		// 다국어 추가
		String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();		
    	mav.addObject("VOMap100", envOptionAPIService.selectOptionGroupMapResource((String)session.getAttribute("COMP_ID"), "OPTG100", langType));
    	mav.addObject("VOMap200", envOptionAPIService.selectOptionGroupMapResource((String)session.getAttribute("COMP_ID"), "OPTG200", langType));
		// mav.addObject("VOMap100", envOptionAPIService.selectOptionGroupMap((String)session.getAttribute("COMP_ID"), "OPTG100"));
    	// mav.addObject("VOMap200", envOptionAPIService.selectOptionGroupMap((String)session.getAttribute("COMP_ID"), "OPTG200"));
		
    	mav.addObject("VOMap300", envOptionAPIService.selectOptionGroupMap((String)session.getAttribute("COMP_ID"), "OPTG300"));
    	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  일상감사-감사대장 사용여부 수정
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/updateOptionInspection.do") 
    public ModelAndView updateOptionInspection(HttpServletRequest req) throws Exception {
    	
    	HttpSession session = req.getSession();
    	String compId = (String)session.getAttribute("COMP_ID");
    	
    	List<OptionVO> voList = new ArrayList<OptionVO>();
    	
    	OptionVO vo = null;    	
    	String optionId = "";
    	
	    vo = new OptionVO();
	    vo.setCompId(compId);
	    optionId = "OPT207"; // 일상감사대장
	    vo.setOptionId(optionId);
	    vo.setUseYn(optionUtil.checkboxValue(req.getParameter(optionId)));
	    vo.setOptionValue(CommonUtil.nullTrim(req.getParameter(optionId+"Value")));
	    voList.add(vo);
	    
	    vo = new OptionVO();
	    vo.setCompId(compId);
	    optionId = "OPT206"; // 감사문서함
	    vo.setOptionId(optionId);
	    vo.setUseYn(optionUtil.checkboxValue(req.getParameter(optionId)));
	    vo.setOptionValue(CommonUtil.nullTrim(req.getParameter(optionId+"Value")));
	    voList.add(vo);
	    
	    vo = new OptionVO();
	    vo.setCompId(compId);
	    optionId = "OPT114"; // 감사열람함
	    vo.setOptionId(optionId);
	    vo.setUseYn(optionUtil.checkboxValue(req.getParameter(optionId)));
	    vo.setOptionValue(CommonUtil.nullTrim(req.getParameter(optionId+"Value")));
	    voList.add(vo);
	    
	    vo = new OptionVO();
	    vo.setCompId(compId);
	    optionId = "OPT208"; // 감사직인날인대장
	    vo.setOptionId(optionId);
	    vo.setUseYn(optionUtil.checkboxValue(req.getParameter(optionId)));
	    vo.setOptionValue(CommonUtil.nullTrim(req.getParameter(optionId+"Value")));
	    voList.add(vo);
    	    
    	envOptionAPIService.updateOptionList(voList);	
    	envOptionAPIService.updateOptionUseYn(compId, "OPT346", optionUtil.checkboxValue(req.getParameter("OPT346"))); // 일상감사 대상 사용
    	envOptionAPIService.updateOptionUseYn(compId, "OPT312", optionUtil.checkboxValue(req.getParameter("OPT312"))); // 감사부서 열람 사용
    	
    	envOptionAPIService.updateOptionUseYn(compId, "OPT342", req.getParameter("OPT342")); // 감사부서 열람대상문서 범위
    	
    	envOptionAPIService.updateOptionUseYn(compId, "OPT375", optionUtil.checkboxValue(req.getParameter("OPT375"))); // 접수문서
    	envOptionAPIService.updateOptionUseYn(compId, "OPT377", optionUtil.checkboxValue(req.getParameter("OPT377"))); // 비전자문서
    	
    	envOptionAPIService.updateOptionUseYn(compId, "OPT347", optionUtil.checkboxValue(req.getParameter("OPT347"))); // 감사부서 열람함 열람대상
    	
		envOptionAPIService.updateOptionUseYn(compId, "OPT379", optionUtil.checkboxValue(req.getParameter("OPT379"))); // 감사부서에서만 감사업무수행여부	
		envOptionAPIService.updateOptionUseYn(compId, "OPT380", optionUtil.checkboxValue(req.getParameter("OPT380"))); // 감사대상문서,감사문서 별도지정여부
		envOptionAPIService.updateOptionUseYn(compId, "OPT381", optionUtil.checkboxValue(req.getParameter("OPT381"))); // 사후감사기능 사용여부
	    	
		envOptionAPIService.optionReload(compId);
    	
    	ModelAndView mav = new ModelAndView("EnvController.procResultOption");
    	mav.addObject("msg", "env.option.msg.success.save");
    	mav.addObject("url", "/app/env/admin/selectOptionInspection.do");
    	return mav;
    }
    
    
    /** 
     * 
     * <pre> 
     *  공람/열람/문서수신대상 사용여부 조회
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectOptionReadShow.do") 
    public ModelAndView selectOptionReadShow(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession();
	
		ModelAndView mav = new ModelAndView("EnvController.admin.selectOptionReadShow");
		
		// 다국어 추가
		String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();	
		mav.addObject("VOMap", envOptionAPIService.selectOptionGroupMapResource((String)session.getAttribute("COMP_ID"), "OPTG300", langType));
		// mav.addObject("VOMap", envOptionAPIService.selectOptionGroupMap((String)session.getAttribute("COMP_ID"), "OPTG300"));
		
		return mav;
    }    
    
    /** 
     * 
     * <pre> 
     *  공람/열람/문서수신대상 사용여부 수정
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/updateOptionReadShow.do") 
    public ModelAndView updateOptionReadShow(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession();
		String compId = (String)session.getAttribute("COMP_ID");
		
		envOptionAPIService.updateOption(compId, "OPT334", optionUtil.checkboxValue(req.getParameter("OPT334")), CommonUtil.nullTrim(req.getParameter("OPT334value"))); // 생산문서 공람사용
		envOptionAPIService.updateOption(compId, "OPT335", optionUtil.checkboxValue(req.getParameter("OPT335")), CommonUtil.nullTrim(req.getParameter("OPT335value"))); // 접수문서 공람사용
		envOptionAPIService.updateOptionUseYn(compId, "OPT372", optionUtil.checkboxValue(req.getParameter("OPT372"))); // 공람문서함에서 공람자 추가 사용
		envOptionAPIService.updateOptionUseYn(compId, "OPT366", optionUtil.checkboxValue(req.getParameter("OPT366"))); // 공람자목록 분리표시
		envOptionAPIService.updateOptionUseYn(compId, "OPT314", req.getParameter("OPT314")); // 생산문서 열람범위
		envOptionAPIService.updateOptionUseYn(compId, "OPT361", req.getParameter("OPT361")); // 접수문서 열람범위
		envOptionAPIService.updateOptionUseYn(compId, "OPT316", req.getParameter("OPT316")); // 공람게시
		
		// 다국어때문에 아래 공람게시 필드란(OPTION_VALUE)을 저장할 필요가 없으므로 주석처리해서 저장하지 못하게 한다.
		// envOptionAPIService.updateOptionValue(compId, "OPT316", CommonUtil.nullTrim(req.getParameter("OPT316Value")));
		
		envOptionAPIService.optionReload(compId);
	
		ModelAndView mav = new ModelAndView("EnvController.procResultOption");
		
		mav.addObject("msg", "env.option.msg.success.save");
		mav.addObject("url", "/app/env/admin/selectOptionReadShow.do");
		
		return mav;
    }
    
    
    /** 
     * 
     * <pre> 
     *  캠페인/로고/심볼 사용여부 조회
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admincharge/selectOptionCLS.do") 
    public ModelAndView selectOptionCLS(HttpServletRequest req) throws Exception {
		HttpSession session = req.getSession();
		String compId = (String)session.getAttribute("COMP_ID");
		// 심볼,로고 등은 기관별로 관리한다.   // jth8172 2012 신결재 TF
		UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
		String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
		if ("".equals(institutionId)) {
			institutionId = userProfileVO.getCompId();
		}	
		String registerDeptId = institutionId;
	
		String fet001 = appCode.getProperty("FET001", "FET001", "FET");
		String fet002 = appCode.getProperty("FET002", "FET002", "FET");
		String fet003 = appCode.getProperty("FET003", "FET003", "FET");
		String fet004 = appCode.getProperty("FET004", "FET004", "FET");
	    
		ModelAndView mav = new ModelAndView("EnvController.admin.selectOptionCLS");
		
		mav.addObject("VOMap", envOptionAPIService.selectOptionGroupMap(compId, "OPTG300"));
		mav.addObject("VOLogoList", envOptionAPIService.selectFormEnvList(compId, registerDeptId, fet001));// 로고
		mav.addObject("VOSymbolList", envOptionAPIService.selectFormEnvList(compId, registerDeptId, fet002));// 심볼
		
		// 다국어 추가
		String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();
		mav.addObject("VOCampaignUpList", envOptionAPIService.selectFormEnvListResource(compId, registerDeptId, fet003, langType));// 상부캠페인
		mav.addObject("VOCampaignDownList", envOptionAPIService.selectFormEnvListResource(compId, registerDeptId, fet004, langType));// 하부캠페인
		// mav.addObject("VOCampaignUpList", envOptionAPIService.selectFormEnvList(compId, registerDeptId, fet003));
		// mav.addObject("VOCampaignDownList", envOptionAPIService.selectFormEnvList(compId, registerDeptId, fet004));
		
		envOptionAPIService.DonwnloadFormEnvFile(compId);
		
		return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  캠페인/로고/심볼 사용여부 수정
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admincharge/updateOptionCLS.do") 
    public ModelAndView updateOptionCLS(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	
	envOptionAPIService.updateOptionUseYn(compId, "OPT323", optionUtil.checkboxValue(req.getParameter("OPT323")));
	envOptionAPIService.updateOptionUseYn(compId, "OPT324", optionUtil.checkboxValue(req.getParameter("OPT324")));
	envOptionAPIService.updateOptionUseYn(compId, "OPT328", optionUtil.checkboxValue(req.getParameter("OPT328")));
	envOptionAPIService.updateOptionUseYn(compId, "OPT329", optionUtil.checkboxValue(req.getParameter("OPT329")));
	envOptionAPIService.optionReload(compId);

	ModelAndView mav = new ModelAndView("EnvController.procResultOption");
	mav.addObject("msg", "env.option.msg.success.save");
	mav.addObject("url", "/app/env/admincharge/selectOptionCLS.do");
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  캠페인/로고/심볼 기본설정 수정
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admincharge/updateFormEnvDefaultSet.do") 
    public ModelAndView updateFormEnvDefaultSet(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	// 심볼,로고 등은 기관별로 관리한다.   // jth8172 2012 신결재 TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
	if ("".equals(institutionId)) {
		institutionId = userProfileVO.getCompId();
	}	
	String registerDeptId = institutionId;
	
	envOptionAPIService.updateFormEnvDefaultSet(compId, registerDeptId, req.getParameter("envType"), req.getParameter("formEnvId"), req.getParameter("optionValue"));
	envOptionAPIService.optionReload(compId);
	
	ModelAndView mav = new ModelAndView("EnvController.procResultOption");
	mav.addObject("msg", "env.option.msg.success.setDefault");
	mav.addObject("url", "/app/env/admincharge/selectOptionCLS.do");
	return mav;	
    }
    
    /** 
     * 
     * <pre> 
     *  캠페인/로고/심볼 삭제
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admincharge/deleteFormEnv.do") 
    public ModelAndView deleteFormEnv(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	
	envOptionAPIService.deleteFormEnv((String)session.getAttribute("COMP_ID"), req.getParameter("formEnvId"));
    
	ModelAndView mav = new ModelAndView("EnvController.procResultOption");
	mav.addObject("msg", "env.option.msg.success.delete");
	mav.addObject("url", "/app/env/admincharge/selectOptionCLS.do");
	return mav;
    }    
    
    /** 
     * 
     * <pre> 
     *  로고/심볼 이미지
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/selectOptionComImg.do") 
    public ModelAndView selectOptionComImg(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	FormEnvVO vo = new FormEnvVO();
	vo = envOptionAPIService.selectFormEnvVO(compId, req.getParameter("formEnvId"));	
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");	
	
	StorFileVO fileVO = new StorFileVO();
	fileVO.setDisplayname(vo.getRemark());
	fileVO.setFilepath(uploadTemp + "/" + compId + "/" + vo.getRemark());
	fileVO.setType(vo.getRemark().substring(vo.getRemark().lastIndexOf(".")+1).toLowerCase());
	
	ModelAndView mav = new ModelAndView("EnvController.admin.selectOptionComImg");
	mav.addObject("fileVO", fileVO);
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  로고/심볼 이미지 미리보기
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/imgPreView.do") 
    public ModelAndView imgPreView(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String fileName = req.getParameter("fileName");	
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");	
	
	StorFileVO fileVO = new StorFileVO();
	fileVO.setDisplayname(fileName);
	fileVO.setFilepath(uploadTemp + "/" + compId + "/" + fileName);
	fileVO.setType(fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase());
	
	ModelAndView mav = new ModelAndView("EnvController.admin.imgPreView");
	mav.addObject("fileVO", fileVO);
	return mav;
    }    
    
    /** 
     * 
     * <pre> 
     *  캠페인/로고/심볼 등록 팝업
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admincharge/insertFormEnv.do") 
    public ModelAndView insertFormEnv(HttpServletRequest req) throws Exception {
	ModelAndView mav = new ModelAndView("EnvController.admin.insertFormEnv");	
	return mav;
    }
    

    /** 
     * 
     * <pre> 
     *  캠페인/로고/심볼 선택 팝업
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/selectLogoSymbol.do") 
    public ModelAndView selectLogoSymbol(HttpServletRequest req) throws Exception {
	//  jth8172 2012 신결재 TF
	ModelAndView mav = new ModelAndView("EnvController.selectLogoSymbol");
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	// 심볼,로고 등은 기관별로 관리한다.   // jth8172 2012 신결재 TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
	if ("".equals(institutionId)) {
		institutionId = userProfileVO.getCompId();
	}	

	// 사용자가 속한 조직 상위의 기관목록을 가져온다. jth8172 신결재 TF
	List<OrganizationVO> upperOrgs = orgService.selectUserOrganizationListByOrgId(compId, institutionId);
    int nUpperOrgs = upperOrgs.size();
    for (int nLoop = nUpperOrgs - 1; nLoop >= 0; nLoop--) {
		OrganizationVO tempVO = upperOrgs.get(nLoop);
		if (!tempVO.getIsInstitution() && (compId).equals(upperOrgs.get(nLoop).getCompanyID())) {
		    upperOrgs.remove(nLoop);
	    }
    }
	mav.addObject( "upperOrgs", upperOrgs );
	
	//해당 부서의 로고 심볼 목록
	String registerDeptId =  CommonUtil.nullTrim(req.getParameter("registerDeptId"));	
	
	if("".equals(registerDeptId)) {
		registerDeptId = institutionId;
	}	
	String fet001 = appCode.getProperty("FET001", "FET001", "FET");
	String fet002 = appCode.getProperty("FET002", "FET002", "FET");
	
	mav.addObject("VOLogoList", envOptionAPIService.selectFormEnvList(compId, registerDeptId, fet001));// 로고
	mav.addObject("VOSymbolList", envOptionAPIService.selectFormEnvList(compId, registerDeptId, fet002));// 심볼
	envOptionAPIService.DonwnloadFormEnvFile(compId);
	
	
	return mav;
    }
    
    
    /** 
     * 
     * <pre> 
     *  캠페인 등록
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admincharge/insertCampaign.do") 
    public void insertCampaign(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	JSONObject result = new JSONObject();
    	
		HttpSession session = req.getSession();
		// 심볼,로고 등은 기관별로 관리한다.   // jth8172 2012 신결재 TF
		UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
		String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
		if ("".equals(institutionId)) {
			institutionId = userProfileVO.getCompId();
		}	
		String registerDeptId = institutionId;
		
		String formEnvId = GuidUtil.getGUID();
		try {
		    
		    FormEnvVO vo = new FormEnvVO();
		    vo.setCompId((String)session.getAttribute("COMP_ID"));
		    vo.setFormEnvId(formEnvId);
		    vo.setEnvType(req.getParameter("envType"));
		    vo.setEnvInfo(req.getParameter(req.getParameter("envType")+"EnvInfo"));
		    vo.setDefaultYn(req.getParameter("campDefaultYn"));
		    vo.setRegisterId((String)session.getAttribute("USER_ID"));
		    vo.setRegisterDeptId(registerDeptId);
		    vo.setRegisterName((String)session.getAttribute("USER_NAME"));
		    vo.setRegistDate(DateUtil.getCurrentDate());
		    envOptionAPIService.insertFormEnvCamp(vo);
		   
		    result.put("success", true);
			result.put("formEnvId", formEnvId);
			
		} catch (Exception e) {	 
			result.put("success", false);
			
		    logger.error(e.getMessage());
		}	
		res.setContentType("application/x-json; charset=utf-8");
		res.getWriter().write(result.toString());		
    }
    
    /** 
     * 
     * <pre> 
     *  로고/심볼 등록
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admincharge/insertLogoSymbol.do") 
    public ModelAndView insertLogoSymbol(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();
	// 심볼,로고 등은 기관별로 관리한다.   // jth8172 2012 신결재 TF
	UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
	String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
	if ("".equals(institutionId)) {
		institutionId = userProfileVO.getCompId();
	}	
	String registerDeptId = institutionId;	
	try {
	    
	    FormEnvVO vo = new FormEnvVO();
	    vo.setCompId((String)session.getAttribute("COMP_ID"));
	    vo.setFormEnvId(GuidUtil.getGUID());
	    vo.setEnvType(req.getParameter("envType"));
	    vo.setDefaultYn(req.getParameter("LSDefaultYn"));
	    vo.setRemark(req.getParameter("fileName"));
	    vo.setEnvName(req.getParameter("fileDisplayName"));
	    vo.setRegisterId((String)session.getAttribute("USER_ID"));
	    vo.setRegisterDeptId(registerDeptId);
	    vo.setRegisterName((String)session.getAttribute("USER_NAME"));
	    vo.setRegistDate(DateUtil.getCurrentDate());
	    envOptionAPIService.insertFormEnvLS(vo);
	    mapper.writeValue(res.getOutputStream(), "success");
	    
	} catch (Exception e) {	    
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	    logger.error(e.getMessage());
	}
	return null;
    }
    
    
    /** 
     * 
     * <pre> 
     *  기타 결재옵션 조회
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectOptionEtc.do") 
    public ModelAndView selectOptionEtc(HttpServletRequest req) throws Exception 
    {
		HttpSession session = req.getSession();
		String compId = (String)session.getAttribute("COMP_ID");
		
		String opt340 = envOptionAPIService.selectOptionText(compId, "OPT340");
		String[] attr = opt340.split("\\^");
		
		// edited by bonggon.choi. 2012.03.27. [add VOMap400]
		ModelAndView mav = new ModelAndView("EnvController.admin.selectOptionEtc");	
		mav.addObject("VOMap300", envOptionAPIService.selectOptionGroupMap((String)session.getAttribute("COMP_ID"), "OPTG300"));
		mav.addObject("VOMap400", envOptionAPIService.selectOptionGroupMap((String)session.getAttribute("COMP_ID"), "OPTG400"));
		mav.addObject("map410", envOptionAPIService.selectOptionTextMap((String)session.getAttribute("COMP_ID"), "OPT410"));
		mav.addObject("attrPos", attr);
		return mav;
    }
    
    
    /** 
     * 
     * <pre> 
     *  기타 결재옵션 조회
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectLDAP.do") 
    public ModelAndView selectLDAP(HttpServletRequest req) throws Exception 
    {
    	HttpSession session = req.getSession();
    	String compId = (String)session.getAttribute("COMP_ID");
    	
    	// edited by bonggon.choi. 2012.03.27. [add VOMap400]
    	ModelAndView mav = new ModelAndView("EnvController.admin.testLDAP");	
    	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  기타 결재옵션 사용여부 수정
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/updateOptionEtc.do") 
    public ModelAndView updateOptionEtc(HttpServletRequest req) throws Exception 
    {
		HttpSession session = req.getSession();
		String compId = (String)session.getAttribute("COMP_ID");
		
		envOptionAPIService.updateOptionUseYn(compId, "OPT353", optionUtil.checkboxValue(req.getParameter("OPT353"))); // 개인 결재경로그룹 사용
		envOptionAPIService.updateOptionUseYn(compId, "OPT352", optionUtil.checkboxValue(req.getParameter("OPT352"))); // 부서장을 기본 결재자로 지정(생산문서)
		envOptionAPIService.updateOptionUseYn(compId, "OPT362", optionUtil.checkboxValue(req.getParameter("OPT362"))); // 부서장을 기본 선람자로 지정(접수문서)
		
		envOptionAPIService.updateOptionUseYn(compId, "OPT411", req.getParameter("OPT411")); // 문서보안 암호설정
		
		envOptionAPIService.updateOptionValue(compId, "OPT410", CommonUtil.nullTrim(req.getParameter("OPT410optionValue"))); // 수신자 지정
		
		envOptionAPIService.updateOptionUseYn(compId, "OPT405", req.getParameter("OPT405")); // 수신자 표시 방법 - 부서명
		envOptionAPIService.updateOptionUseYn(compId, "OPT407", req.getParameter("OPT407")); // 수신자 표시 방법 - 부서장 직위
		envOptionAPIService.updateOptionUseYn(compId, "OPT406", req.getParameter("OPT406")); // 수신자 표시 방법 - 수신자 기호
		
		envOptionAPIService.updateOptionUseYn(compId, "OPT313", req.getParameter("OPT313")); // 부재처리
		
		envOptionAPIService.updateOptionUseYn(compId, "OPT331", req.getParameter("OPT331")); // 기본 조회기간 설정
		
		envOptionAPIService.updateOptionValue(compId, "OPT424", req.getParameter("OPT424")); // 한 페이지당 목록건수
		envOptionAPIService.updateOptionValue(compId, "OPT426", req.getParameter("OPT426")); // 한 페이지당 최대목록건수
		
		envOptionAPIService.updateOptionValue(compId, "OPT340", CommonUtil.nullTrim(req.getParameter("posPriority"))); // 직위표시 우선순위
		envOptionAPIService.updateOptionValue(compId, "OPT418", CommonUtil.nullTrim(req.getParameter("saveRuleLine"))); // 문서번호 생성규칙
		
		envOptionAPIService.updateOptionUseYn(compId, "OPT421", req.getParameter("OPT421")); // 결재진행문서 회수기능 설정
		
		/* 기타 결재옵션 */
		envOptionAPIService.updateOptionUseYn(compId, "OPT321", optionUtil.checkboxValue(req.getParameter("OPT321"))); // 관련문서 사용
		envOptionAPIService.updateOptionUseYn(compId, "OPT357", optionUtil.checkboxValue(req.getParameter("OPT357"))); // 결재 처리 후 문서 자동닫기
		envOptionAPIService.updateOptionUseYn(compId, "OPT359", optionUtil.checkboxValue(req.getParameter("OPT359"))); // 비전자문서 첨부 필수		
		envOptionAPIService.updateOptionUseYn(compId, "OPT363", optionUtil.checkboxValue(req.getParameter("OPT363"))); // 문서등록취소기능 사용
		envOptionAPIService.updateOptionUseYn(compId, "OPT371", optionUtil.checkboxValue(req.getParameter("OPT371"))); // 접수절차 간소화 사용
		envOptionAPIService.updateOptionUseYn(compId, "OPT404", optionUtil.checkboxValue(req.getParameter("OPT404"))); // 비공개 사유 입력
		envOptionAPIService.updateOptionUseYn(compId, "OPT412", optionUtil.checkboxValue(req.getParameter("OPT412"))); // 반려된 문서를 재기안시 등록대장에 등록
		envOptionAPIService.updateOptionUseYn(compId, "OPT419", optionUtil.checkboxValue(req.getParameter("OPT419"))); // 서명이미지 사용
		envOptionAPIService.updateOptionUseYn(compId, "OPT420", optionUtil.checkboxValue(req.getParameter("OPT420"))); // 비전자문서 접수 프로세스 사용
		envOptionAPIService.updateOptionUseYn(compId, "OPT422", optionUtil.checkboxValue(req.getParameter("OPT422"))); // 문서분류체계 사용.
		envOptionAPIService.updateOptionUseYn(compId, "OPT423", optionUtil.checkboxValue(req.getParameter("OPT423"))); // 편철 사용.
		envOptionAPIService.updateOption(compId, "OPT330", optionUtil.checkboxValue(req.getParameter("OPT330"))
				, optionUtil.nullToDefault(CommonUtil.nullTrim(req.getParameter("OPT330optionValue")), "0")); // 첨부사이즈 제한
		envOptionAPIService.optionReload(compId);
	
		ModelAndView mav = new ModelAndView("EnvController.procResultOption");
		mav.addObject("msg", "env.option.msg.success.save");
		mav.addObject("url", "/app/env/admin/selectOptionEtc.do");
		return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  비활성화 결재옵션 조회
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectOptionDisabled.do") 
    public ModelAndView selectOptionDisabled(HttpServletRequest req) throws Exception 
    {
    	HttpSession session = req.getSession();
    	
    	// edited by bonggon.choi. 2012.03.27. [add VOMap400]
    	ModelAndView mav = new ModelAndView("EnvController.admin.selectOptionDisabled");	
    	
    	// 다국어 추가
		String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();	
		mav.addObject("VOMap000", envOptionAPIService.selectOptionGroupMapResource((String)session.getAttribute("COMP_ID"), "OPTG000", langType));
    	// mav.addObject("VOMap000", envOptionAPIService.selectOptionGroupMap((String)session.getAttribute("COMP_ID"), "OPTG000"));
    	
		mav.addObject("VOMap300", envOptionAPIService.selectOptionGroupMap((String)session.getAttribute("COMP_ID"), "OPTG300"));
    	mav.addObject("VOMap400", envOptionAPIService.selectOptionGroupMap((String)session.getAttribute("COMP_ID"), "OPTG400"));
    	mav.addObject("map354", envOptionAPIService.selectOptionTextMap((String)session.getAttribute("COMP_ID"), "OPT354"));
    	
    	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  비활성화 결재옵션 사용여부 수정
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/updateOptionDisabled.do") //::
    public ModelAndView updateOptionDisabled(HttpServletRequest req) throws Exception 
    {
    	HttpSession session = req.getSession();
    	String compId = (String)session.getAttribute("COMP_ID");
    	
    	// [OPTG000] grouping BEGIN. edited by bonggon.choi. 2012.05.22.
		List<OptionVO> OPTG000VOList = new ArrayList<OptionVO>();
		int OPTG000ListSize = 0;
		OptionVO vo = null;
		
		Enumeration<String> OPTG000Enum = req.getParameterNames();
		List<String> OPTG000List = new ArrayList<String>();
		
		while(OPTG000Enum.hasMoreElements())
		{
			String tmp = OPTG000Enum.nextElement();
			if (tmp.endsWith(".Text"))
			{
				OPTG000List.add(tmp);
				OPTG000ListSize++;
			}
		}
		
		for (int i=0; i < OPTG000ListSize; i++) {
		    vo = new OptionVO();
		    vo.setCompId(compId);
		    String optionId = OPTG000List.get(i);
		    String optionValue = req.getParameter(optionId);		// [optionId include ".Text"]    
		    optionId = optionId.substring(0, 6);					// [remove ".Text"]
		    
		    vo.setOptionId(optionId);
		    vo.setOptionValue(optionValue);
			vo.setUseYn(optionUtil.checkboxValue(req.getParameter(optionId)));
		    
		    OPTG000VOList.add(vo);
		}
		// [OPTG000] grouping END.
		
		envOptionAPIService.updateOptionList(OPTG000VOList);	
    	
    	envOptionAPIService.updateOptionUseYn(compId, "OPT337", optionUtil.checkboxValue(req.getParameter("OPT337"))); // 결재 후 부서협조 사용
    	envOptionAPIService.updateOptionUseYn(compId, "OPT360", optionUtil.checkboxValue(req.getParameter("OPT360"))); // 결재 후 병렬협조 사용
    	envOptionAPIService.updateOptionUseYn(compId, "OPT336", optionUtil.checkboxValue(req.getParameter("OPT336"))); // 결재 후 협조 사용
    	
    	envOptionAPIService.updateOptionUseYn(compId, "OPT303", req.getParameter("OPT303")); // 부서협조 표시
    	
    	String opt304 = req.getParameter("OPT304");
    	
    	if ( opt304 != null )
    	{
    		envOptionAPIService.updateOptionUseYn(compId, "OPT304", opt304); // 감사 표시
    	}
    	
    	envOptionAPIService.updateOptionUseYn(compId, "OPT333", req.getParameter("OPT333")); // 수신 지정
    	envOptionAPIService.updateOptionUseYn(compId, "OPT358", req.getParameter("OPT358")); // 채번
    	
    	envOptionAPIService.updateOptionUseYn(compId, "OPT310", optionUtil.checkboxValue(req.getParameter("OPT310"))); // 하위번호 사용
    	envOptionAPIService.updateOptionUseYn(compId, "OPT311", req.getParameter("OPT310value"));
    	
    	envOptionAPIService.updateOptionUseYn(compId, "OPT305", req.getParameter("OPT305")); // 반려문서결재
    	envOptionAPIService.updateOptionValue(compId, "OPT354", CommonUtil.nullTrim(req.getParameter("OPT354optionValue"))); // 부서별 양식등록 사용
    	
    	envOptionAPIService.updateOptionUseYn(compId, "OPT307", optionUtil.checkboxValue(req.getParameter("OPT307"))); // 그룹내(계열사) 자동발송
    	envOptionAPIService.updateOptionUseYn(compId, "OPT308", optionUtil.checkboxValue(req.getParameter("OPT308"))); // 서명인 그룹내(계열사)발송
    	envOptionAPIService.updateOptionUseYn(compId, "OPT355", optionUtil.checkboxValue(req.getParameter("OPT355"))); // 채번문서 담당접수 사용불가
    	envOptionAPIService.updateOptionUseYn(compId, "OPT356", optionUtil.checkboxValue(req.getParameter("OPT356"))); // 미채번문서 접수경로 사용불가
    	
    	//envOptionAPIService.updateOptionUseYn(compId, "OPT321", optionUtil.checkboxValue(req.getParameter("OPT321"))); // 관련문서 사용
    	envOptionAPIService.updateOptionUseYn(compId, "OPT370", optionUtil.checkboxValue(req.getParameter("OPT370"))); // 관련문서 필터링 사용여부
    	envOptionAPIService.updateOptionUseYn(compId, "OPT319", optionUtil.checkboxValue(req.getParameter("OPT319"))); // DRM 사용여부
    	envOptionAPIService.updateOptionUseYn(compId, "OPT343", optionUtil.checkboxValue(req.getParameter("OPT343"))); // 모바일 사용
    	
    	envOptionAPIService.updateOptionUseYn(compId, "OPT413", optionUtil.checkboxValue(req.getParameter("OPT413"))); // 대내문서 자동발송 사용여부
    	envOptionAPIService.updateOptionUseYn(compId, "OPT414", req.getParameter("OPT413value"));
    	
    	envOptionAPIService.updateOptionUseYn(compId, "OPT322", req.getParameter("OPT322")); // PDF파일 저장권한
    	
    	envOptionAPIService.optionReload(compId);
    	
    	ModelAndView mav = new ModelAndView("EnvController.procResultOption");
    	mav.addObject("msg", "env.option.msg.success.save");
    	mav.addObject("url", "/app/env/admin/selectOptionDisabled.do");
    	return mav;
    }
    
    
    /** 
     * 
     * <pre> 
     *  알림설정/결재시인증 사용여부 조회
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectOptionNoticeCert.do") 
    public ModelAndView selectOptionNoticeCert(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	HashMap<String, String> map = new HashMap<String, String>();
	HashMap<String, String> map2 = new HashMap<String, String>();
	map = envOptionAPIService.selectOptionTextMap(compId, "OPT317");	
	map.put("OPT301", envOptionAPIService.selectOptionValue(compId, "OPT301"));
	map2 = envOptionAPIService.selectOptionTextMap(compId, "OPT332");

	ModelAndView mav = new ModelAndView("EnvController.admin.selectOptionNoticeCert");	
	mav.addObject("VOMap", map);
	mav.addObject("VOMap2", map2);
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  알림설정/결재시인증 사용여부 수정
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/updateOptionNoticeCert.do") 
    public ModelAndView updateOptionNoticeCert(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	
	envOptionAPIService.updateOptionValue(compId, "OPT317", req.getParameter("OPT317optionValue"));
	envOptionAPIService.updateOptionUseYn(compId, "OPT301", req.getParameter("OPT301"));
	envOptionAPIService.updateOptionValue(compId, "OPT332", req.getParameter("OPT332optionValue"));
	envOptionAPIService.optionReload(compId);

	ModelAndView mav = new ModelAndView("EnvController.procResultOption");
	mav.addObject("msg", "env.option.msg.success.save");
	mav.addObject("url", "/app/env/admin/selectOptionNoticeCert.do");
	return mav;
    }
    
    
    /** 
     * 
     * <pre> 
     *  가지번호/채번 사용여부 조회
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     *//*
    @RequestMapping("/app/env/admin/selectOptionDocNumber.do") 
    public ModelAndView selectOptionDocNumber(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	HashMap<String, String> map = new HashMap<String, String>();
	map.put("OPT318_value", envOptionAPIService.selectOptionText(compId, "OPT318"));
	map.put("OPT318_useYn", envOptionAPIService.selectOptionValue(compId, "OPT318"));
	map.put("OPT310", envOptionAPIService.selectOptionValue(compId, "OPT310"));
	map.put("OPT311", envOptionAPIService.selectOptionValue(compId, "OPT311"));
	ListUtil listUtil = new ListUtil();
	String term = listUtil.returnCurRegist(compId, envOptionAPIService.selectOptionValue(compId, "OPT318"), "", "");
	map.put("term", term);
	
	ModelAndView mav = new ModelAndView("EnvController.admin.selectOptionDocNumber");
	mav.addObject("VOMap", map);
	return mav;
    }*/
    
    /** 
     * 
     * <pre> 
     *  가지번호/채번 사용여부 수정
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     *//*
    @RequestMapping("/app/env/admin/updateOptionDocNumber.do") 
    public ModelAndView updateOptionDocNumber(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	
	envOptionAPIService.updateOptionUseYn(compId, "OPT310", optionUtil.checkboxValue(req.getParameter("OPT310")));
	envOptionAPIService.updateOptionUseYn(compId, "OPT311", req.getParameter("OPT311"));
	envOptionAPIService.updateOptionUseYn(compId, "OPT318", req.getParameter("OPT318"));
	envOptionAPIService.updateOptionValue(compId, "OPT318", req.getParameter("startDateId"));

	ModelAndView mav = new ModelAndView("EnvController.procResultOption");
	mav.addObject("msg", "env.option.msg.success.save");
	mav.addObject("url", "/app/env/admin/selectOptionDocNumber.do");
	return mav;
    }*/
    
    
    /** 
     * 
     * <pre> 
     *  기타 일반옵션 조회
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectOptionGenEtc.do") 
    public ModelAndView selectOptionGenEtc(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String opt340 = envOptionAPIService.selectOptionText(compId, "OPT340");
	String[] attr = opt340.split("\\^");

	ModelAndView mav = new ModelAndView("EnvController.admin.selectOptionGenEtc");	
	mav.addObject("VOMap", envOptionAPIService.selectOptionGroupMap((String)session.getAttribute("COMP_ID"), "OPTG300"));
	mav.addObject("attrPos", attr);
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  기타 일반옵션 사용여부 수정
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/updateOptionGenEtc.do") 
    public ModelAndView updateOptionGenEtc(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");	
	
	envOptionAPIService.updateOptionUseYn(compId, "OPT321", optionUtil.checkboxValue(req.getParameter("OPT321"))); // 관련문서 사용
	envOptionAPIService.updateOptionUseYn(compId, "OPT370", optionUtil.checkboxValue(req.getParameter("OPT370"))); // 관련문서 필터링 사용
	envOptionAPIService.updateOptionUseYn(compId, "OPT344", optionUtil.checkboxValue(req.getParameter("OPT344"))); // 관련규정 사용
	envOptionAPIService.updateOptionUseYn(compId, "OPT348", optionUtil.checkboxValue(req.getParameter("OPT348"))); // 거래처 사용
	envOptionAPIService.updateOptionUseYn(compId, "OPT319", optionUtil.checkboxValue(req.getParameter("OPT319"))); // DRM 사용
	envOptionAPIService.updateOptionUseYn(compId, "OPT343", optionUtil.checkboxValue(req.getParameter("OPT343"))); // 모바일 사용
	envOptionAPIService.updateOption(compId, "OPT330", optionUtil.checkboxValue(req.getParameter("OPT330")), optionUtil.nullToDefault(CommonUtil.nullTrim(req.getParameter("OPT330optionValue")), "0")); // 첨부사이즈 제한
	envOptionAPIService.updateOptionUseYn(compId, "OPT322", req.getParameter("OPT322")); // PDF파일 저장권한
	envOptionAPIService.updateOptionUseYn(compId, "OPT331", req.getParameter("OPT331")); // 기본 조회기간
	envOptionAPIService.updateOptionValue(compId, "OPT340", CommonUtil.nullTrim(req.getParameter("posPriority"))); // 직위표시 우선순위
	envOptionAPIService.optionReload(compId);

	ModelAndView mav = new ModelAndView("EnvController.procResultOption");
	mav.addObject("msg", "env.option.msg.success.save");
	mav.addObject("url", "/app/env/admin/selectOptionGenEtc.do");
	return mav;
    }
    
    
    /** 
     * 
     * <pre> 
     *  발신명의 목록 조회(처리과/문서과 문서책임자)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/charge/selectEnvSenderTitle.do")
    public ModelAndView selectEnvSenderTitleCharge(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String deptId = (String)session.getAttribute("DEPT_ID");
	String roleCodes = (String)session.getAttribute("ROLE_CODES");
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); // 부서 문서관리책임자
	String roleId12 = AppConfig.getProperty("role_cordoccharger", "", "role"); // 기관 문서관리책임자
	
	String groupType = req.getParameter("groupType");
	logger.debug(">>>>>>>>>>>>>> groupType : "+groupType);
	
	if (groupType == null) {
		if (roleCodes.indexOf(roleId11)>=0) {
		    groupType = "GUT003";
		} else if (roleCodes.indexOf(roleId12)>=0) {
		    groupType = "GUT002";
		}		
	}
	
	if ("GUT002".equals(groupType)) {
		UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
		String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
		if ("".equals(institutionId)) {
			institutionId = userProfileVO.getCompId();
		}
		deptId = institutionId;
	}
	
	logger.debug(">>>>>>>>>>>>>> groupType : "+groupType);
	
	ModelAndView mav = new ModelAndView("EnvController.charge.selectEnvSenderTitle");
	
	// 다국어 추가
	String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();
	mav.addObject("voList", envOptionAPIService.selectSenderTitleListResource(compId, deptId, groupType, langType));
	//mav.addObject("voList", envOptionAPIService.selectSenderTitleList(compId, deptId, groupType));
	
	//mav.addObject("checkList", envOptionAPIService.selectSenderTitleAllList(session));
	mav.addObject("mode", "charge");
	mav.addObject("groupType", groupType);
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  발신명의 목록 조회(회사관리자)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectEnvSenderTitle.do")
	    public ModelAndView selectEnvSenderTitleAdmin(HttpServletRequest req) throws Exception {
		
    	String GUT001 = appCode.getProperty("GUT001","GUT001", "GUT"); // 회사
		
		HttpSession session = req.getSession();
		String compId = (String)session.getAttribute("COMP_ID");
		
		String groupType = GUT001;
	
		ModelAndView mav = new ModelAndView("EnvController.charge.selectEnvSenderTitle");
		
		// 다국어 추가
		String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();
		mav.addObject("voList", envOptionAPIService.selectSenderTitleListResource(compId, compId, groupType, langType));
		//mav.addObject("voList", envOptionAPIService.selectSenderTitleList(compId, compId, groupType));

		mav.addObject("mode", "admin");
		mav.addObject("groupType", groupType);
	
		return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  발신명의 목록 조회(문서과/처리과 문서책임자) - Ajax
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/charge/selectEnvSenderTitleAjax.do")
    public ModelAndView selectEnvSenderTitleChargeAjax(HttpServletRequest req, HttpServletResponse res) throws Exception {

	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String deptId = (req.getParameter("deptId") == null ? "" : req.getParameter("deptId"));
	String groupType = req.getParameter("groupType");	

	try {
	    if (compId == null) {
		throw new NullPointerException("compId");
	    }
	    if (groupType == null) {
		throw new NullPointerException("groupType");
	    }
	    
		// 다국어 추가
		String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();
		List<SenderTitleVO> voList = envOptionAPIService.selectSenderTitleListResource(compId, deptId, groupType, langType);
	    // List<SenderTitleVO> voList = envOptionAPIService.selectSenderTitleList(compId, deptId, groupType);
	    
	    mapper.writeValue(res.getOutputStream(), voList);
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }

    /** 
     * 
     * <pre> 
     *  발신명의 목록 조회(회사관리자) - Ajax
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectEnvSenderTitleAjax.do")
    public ModelAndView selectEnvSenderTitleAdminAjax(HttpServletRequest req, HttpServletResponse res) throws Exception {

	String GUT001 = appCode.getProperty("GUT001","GUT001", "GUT"); // 회사
 
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String deptId = (req.getParameter("deptId") == null ? "" : req.getParameter("deptId"));
	String groupType = req.getParameter("groupType");	
	
	if (GUT001.equals(groupType)) {
	    deptId = compId;
	}

	try {
	    if (compId == null) {
		throw new NullPointerException("compId");
	    }
	    if (groupType == null) {
		throw new NullPointerException("groupType");
	    }
	    
	    // 다국어 추가	
		String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();	    
		List<SenderTitleVO> voList = envOptionAPIService.selectSenderTitleListResource(compId, deptId, groupType, langType);
		// List<SenderTitleVO> voList = envOptionAPIService.selectSenderTitleList(compId, deptId, groupType);
		
	    mapper.writeValue(res.getOutputStream(), voList);
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  발신명의 등록
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admincharge/insertEnvSenderTitle.do") 
    public ModelAndView insertSenderTitle(HttpServletRequest req) throws Exception {
	
    	String GUT001 = appCode.getProperty("GUT001","GUT001", "GUT"); // 회사
    	String GUT002 = appCode.getProperty("GUT002","GUT002", "GUT"); // 기관
    	String GUT003 = appCode.getProperty("GUT003","GUT003", "GUT"); // 부서 
	
		HttpSession session = req.getSession();
		String mode = req.getParameter("mode");
		String compId = (String)session.getAttribute("COMP_ID");
		String deptId = (req.getParameter("deptId") == null ? "" : req.getParameter("deptId"));
		String groupType = req.getParameter("groupType");
		if ("admin".equals(mode)) {
			if (GUT001.equals(groupType)) {
			    deptId = compId;
			}		
		} else if ("charge".equals(mode)) {
			if (GUT002.equals(groupType)) {
				UserProfileVO userProfileVO = (UserProfileVO) session.getAttribute("userProfile");
				String institutionId = CommonUtil.nullTrim((String) userProfileVO.getInstitution());
				if ("".equals(institutionId)) {
					institutionId = userProfileVO.getCompId();
				}
				deptId = institutionId;
			}		
		}
		
		int result = 0;	
		SenderTitleVO vo = new SenderTitleVO();
		
		String senderTitleId = GuidUtil.getGUID();
		vo.setSenderTitleId(senderTitleId);
		vo.setCompId(compId);		
		vo.setSenderTitle(CommonUtil.nullTrim(req.getParameter("senderTitle")));
		vo.setDefaultYn(optionUtil.checkboxValue(req.getParameter("defaultYn")));
		vo.setDeptId(deptId);
		vo.setGroupType(groupType);
		String returnUrl = "/app/env/"+mode+"/selectEnvSenderTitle.do?groupType="+groupType;
		String returnMsg = "";
		
		try {
		    result = envOptionAPIService.insertSenderTitle(vo);
		} catch (Exception e) {
		    result = -1;
		}
		if (result == 1) {
		    returnMsg = "env.option.msg.success.register";
		} else if (result == 0) {
		    returnMsg = "env.group.msg.sendertitle.duplication";
		} else {
		    returnMsg = "env.option.msg.fail.register";
		}
		
		ModelAndView mav = new ModelAndView("EnvController.procResultOption");
		
		mav.addObject("msg", returnMsg);
		mav.addObject("url", returnUrl);
		
		// 다국어때문에 추가
		mav.addObject("conditionValue", senderTitleId);
		
		return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  발신명의 기본설정 수정
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admincharge/updateEnvSenderTitleDefaultSet.do") 
    public ModelAndView updateEnvSenderTitleDefaultSet(HttpServletRequest req) throws Exception {
	String GUT001 = appCode.getProperty("GUT001","GUT001", "GUT"); // 회사
 
	HttpSession session = req.getSession();
	String mode = req.getParameter("mode");	
	String compId = (String)session.getAttribute("COMP_ID");
	//String deptId = (String)session.getAttribute("DEPT_ID");
	String deptId = (req.getParameter("deptId") == null ? "" : req.getParameter("deptId"));
	String senderTitleId = req.getParameter("senderTitleId");
	boolean result = true;
	String returnMsg = "";
	
	String groupType = req.getParameter("groupType");
	String returnUrl = "/app/env/"+mode+"/selectEnvSenderTitle.do?groupType="+groupType;
	
	/*
	if ("admin".equals(mode)) {
	    groupType = "GUT001";
	    deptId = compId;
	}
	*/
	
	if (GUT001.equals(groupType)) {
	    deptId = compId;
	}
	
	try {
	    result = envOptionAPIService.updateSenderTitleDefaultSet(compId, deptId, senderTitleId, groupType);
	    if (result) {
		returnMsg = "env.option.msg.success.setDefault";
	    } else {
		returnMsg = "env.option.msg.fail.setDefault";
	    }
	} catch(Exception e) {
	    returnMsg = "env.option.msg.fail.setDefault";
	}
	
	
	ModelAndView mav = new ModelAndView("EnvController.procResultOption");
	mav.addObject("msg", returnMsg);
	mav.addObject("url", returnUrl);
	return mav;	
    }
    
    /** 
     * 
     * <pre> 
     *  발신명의 삭제
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admincharge/deleteEnvSenderTitle.do") 
    public ModelAndView deleteEnvSenderTitle(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	//String deptId = (String)session.getAttribute("DEPT_ID");
	String mode = req.getParameter("mode");
	String groupType = req.getParameter("groupType");
	int result = 0;
	String returnMsg = "";
	
	/*
	if ("admin".equals(mode)) {
	    deptId = compId;
	}
	if ("GUT001".equals(groupType)) {
	    deptId = compId;
	}
	*/
	String returnUrl = "/app/env/"+mode+"/selectEnvSenderTitle.do?groupType="+groupType;	
	result = envOptionAPIService.deleteSenderTitle(compId, req.getParameter("senderTitleId"));
	
	if (result > 0) {
	    returnMsg = "env.option.msg.success.delete";
	} else {
	    returnMsg = "env.option.msg.fail.delete";
	}
    
	ModelAndView mav = new ModelAndView("EnvController.procResultOption");
	mav.addObject("msg", returnMsg);
	mav.addObject("url", returnUrl);
	return mav;
    } 
    
    
    /** 
     * 
     * <pre> 
     *  결재경로그룹 목록을 가져온다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/listEnvLineGroup.do")
    public ModelAndView selectEnvLineGroup(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String registerId = (String)session.getAttribute("USER_ID");
	String registerDeptId = (String)session.getAttribute("DEPT_ID");
	String groupType = (req.getParameter("groupType") == null ? "GUT004" : req.getParameter("groupType"));
	String opt353 = appCode.getProperty("OPT353","OPT353", "OPT"); // 기본 결재경로그룹 사용여부
	String opt353UseYn = envOptionAPIService.selectOptionValue(compId, opt353);
	
	ModelAndView mav = new ModelAndView("EnvController.listEnvLineGroup");
	mav.addObject("gList", envOptionAPIService.listAppLineGroup(compId, registerId, registerDeptId, groupType));
	mav.addObject("opt353UseYn", opt353UseYn);
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  결재경로를 가져온다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/listEnvAppLine.do")
    public ModelAndView selectEnvAppLine(HttpServletRequest req, HttpServletResponse res) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String lineGroupId = req.getParameter("lineGroupId");
	
	try {
	    if (lineGroupId == null) {
		throw new NullPointerException("lineGroupId");
	    }
	    List<LineUserVO> voList = envOptionAPIService.listAppLine(compId, lineGroupId);
	    mapper.writeValue(res.getOutputStream(), envOptionAPIService.checkOrgAppLine(voList));
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  결재경로를 삭제한다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/deleteEnvLineGroup.do")
    public ModelAndView deleteEnvAppLineGroup(HttpServletRequest req, HttpServletResponse res) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String lineGroupId = req.getParameter("lineGroupId");
	
	try {
	    if (lineGroupId == null) {
		throw new NullPointerException("lineGroupId");
	    }
	    envOptionAPIService.deleteAppLineGroup(compId, lineGroupId);
	    mapper.writeValue(res.getOutputStream(), "success");
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  결재경로그룹 관리에서 결재경로 등록화면을 호출한다.(팝업화면)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/envAppLine.do") 
    public ModelAndView envAppLine(HttpServletRequest req) throws Exception {
	
	//IOrgService orgService = new OrgService();
	
	HttpSession session = req.getSession();
	String userId = req.getParameter("userid");
	String compId = req.getParameter("compid");
	String treeType = req.getParameter("treetype");
	
	
	if (compId == null) {
	    userId = (String) session.getAttribute("USER_ID");
	    compId = (String) session.getAttribute("COMP_ID");
	}
	
	treeType = (treeType == null ? "3" : treeType);
	
	//Departments departments =  orgService.selectOrgTree(userId, Integer.valueOf(treeType).intValue());
	//List<Department> results = departments.getDepartmentList();
	List<DepartmentVO> results = orgService.selectOrgTreeList(userId, Integer.parseInt(treeType));
	
	//옵션값을 옵션 코드(ID)값을 키로 하여 맵을 생성한다. 
	// 다국어 추가
	HashMap<String, OptionVO> mapOptions = envOptionAPIService.selectOptionGroupMapResource(compId, "OPTG000", AppConfig.getCurrentLangType());
	// HashMap<String, OptionVO> mapOptions = envOptionAPIService.selectOptionGroupMap(compId, "OPTG000");
	
	mapOptions.put(appCode.getProperty("OPT325","OPT325", "OPT"), envOptionAPIService.selectOption(compId, appCode.getProperty("OPT325","OPT325", "OPT"))); //이중결재 사용여부
	mapOptions.put(appCode.getProperty("OPT320","OPT320", "OPT"), envOptionAPIService.selectOption(compId, appCode.getProperty("OPT320","OPT320", "OPT"))); //CEO 결재문서 감사필수 여부
	mapOptions.put(appCode.getProperty("OPT321","OPT321", "OPT"), envOptionAPIService.selectOption(compId, appCode.getProperty("OPT321","OPT321", "OPT"))); //일상감사
	mapOptions.put(appCode.getProperty("OPT322","OPT322", "OPT"), envOptionAPIService.selectOption(compId, appCode.getProperty("OPT322","OPT322", "OPT"))); //준법감시
	mapOptions.put(appCode.getProperty("OPT323","OPT323", "OPT"), envOptionAPIService.selectOption(compId, appCode.getProperty("OPT323","OPT323", "OPT"))); //감사위원

	mapOptions.put("OPT336", envOptionAPIService.selectOption(compId, appCode.getProperty("OPT336","OPT336", "OPT"))); //결재 후 협조 사용여부
	mapOptions.put("OPT337", envOptionAPIService.selectOption(compId, appCode.getProperty("OPT337","OPT337", "OPT"))); //결재 후 부서협조 사용여부
	mapOptions.put("OPT360", envOptionAPIService.selectOption(compId, appCode.getProperty("OPT360","OPT360", "OPT"))); //결재 후 병렬협조 사용
	
	ModelAndView mav = new ModelAndView("EnvController.envAppLine");
	mav.addObject("results", results);
	mav.addObject("options", mapOptions);
	
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  결재경로그룹 관리에서 담당자지정 등록화면을 호출한다.(팝업화면)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/envAppPreReader.do") 
    public ModelAndView envAppPreReader(HttpServletRequest req) throws Exception {
	
	//IOrgService orgService = new OrgService();
	
	HttpSession session = req.getSession();
	String userId = req.getParameter("userid");
	String compId = req.getParameter("compid");
	String treeType = req.getParameter("treetype");

	
	if (compId == null) {
	    userId = (String) session.getAttribute("USER_ID");
	    compId = (String) session.getAttribute("COMP_ID");
	}
	
	treeType = (treeType == null ? "3" : treeType);
	
	List<DepartmentVO> results = orgService.selectOrgTreeList(userId, Integer.parseInt(treeType));
	
	//옵션값을 옵션 코드(ID)값을 키로 하여 맵을 생성한다. 
	// 다국어 추가
	HashMap<String, OptionVO> mapOptions = envOptionAPIService.selectOptionGroupMapResource(compId, "OPTG000", AppConfig.getCurrentLangType());
	// HashMap<String, OptionVO> mapOptions = envOptionAPIService.selectOptionGroupMap(compId, "OPTG000");
		
	ModelAndView mav = new ModelAndView("EnvController.envAppPreReader");
	mav.addObject("results", results);
	mav.addObject("options", mapOptions);
	
	return mav;
    }
    
    /**
     * 
     * <pre> 
     *  결재경로그룹을 등록한다.
     * </pre>
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/insertLineGroup.do") 
    public ModelAndView insertLineGroup(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String registerId = (String)session.getAttribute("USER_ID");
	String registerName = (String)session.getAttribute("USER_NAME");
	String registerDeptId = (String)session.getAttribute("DEPT_ID");
	String registerDeptName = (String)session.getAttribute("DEPT_NAME");	
	String groupType = req.getParameter("groupType");
	String registDate = DateUtil.getCurrentDate();
	String usingType = req.getParameter("usingType");
	String lineGroupName = CommonUtil.nullTrim(req.getParameter("paramLineGroupName"));
	String lineGroupId = GuidUtil.getGUID();
	String lineGroupInfo = req.getParameter("paramLineGroupInfo");
	
	try {
	    if (lineGroupName == null) {
		throw new NullPointerException("lineGroupName");
	    } else if (lineGroupInfo == null) {
		throw new NullPointerException("lineGroupInfo");
	    } else if (usingType == null) {
		throw new NullPointerException("usingType");
	    }
	    // set LineGroupVO
	    LineGroupVO gvo = new LineGroupVO();	    
	    gvo.setCompId(compId);
	    gvo.setLineGroupId(lineGroupId);
	    gvo.setLineGroupName(lineGroupName);
	    gvo.setRegisterId(registerId);
	    gvo.setRegisterName(registerName);
	    gvo.setRegisterDeptId(registerDeptId);
	    gvo.setRegisterDeptName(registerDeptName);
	    gvo.setRegistDate(registDate);
	    gvo.setUsingType(usingType);
	    gvo.setGroupType(groupType);
	    gvo.setDefaultYn("N");
	    
	    // set LineUserVO
	    LineUserVO uvo = null;
	    List<LineUserVO> uvoList = new ArrayList<LineUserVO>();
	    String[] attrLines = lineGroupInfo.split(String.valueOf((char) 4));
	    int attrLength = attrLines.length;
	    for (int i=0; i<attrLength; i++) {
		uvo = new LineUserVO();
		String[] attrLine = attrLines[i].split(String.valueOf((char) 2));
		uvo.setCompId(compId);
		uvo.setLineGroupId(lineGroupId);
		uvo.setLineOrder(Integer.parseInt(attrLine[0]));
		uvo.setLineSubOrder(Integer.parseInt(attrLine[1]));
		uvo.setApproverId(attrLine[2]);
		uvo.setApproverName(attrLine[3]);
		uvo.setApproverPos(attrLine[4]);
		uvo.setApproverDeptId(attrLine[5]);
		uvo.setApproverDeptName(attrLine[6]);
		uvo.setAskType(attrLine[7]);
		uvoList.add(uvo);
	    }
	    
	    envOptionAPIService.insertAppLineGroup(gvo, uvoList);
	    mapper.writeValue(res.getOutputStream(), "success");
	} catch (Exception e) {	    
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	    logger.error(e.getMessage());
	}	
	return null;
    }
    
    
    /**
     * 
     * <pre> 
     *  결재경로그룹을 수정한다.
     * </pre>
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/updateEnvLineGroup.do") 
    public ModelAndView updateLineGroup(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String lineGroupName = CommonUtil.nullTrim(req.getParameter("paramLineGroupName"));
	String lineGroupInfo = req.getParameter("paramLineGroupInfo");
	String lineGroupId = req.getParameter("lineGroupId");
	
	try {
	    if (lineGroupName == null) {
		throw new NullPointerException("lineGroupName");
	    } else if (lineGroupInfo == null) {
		throw new NullPointerException("lineGroupInfo");
	    }
	    // set LineGroupVO
	    LineGroupVO gvo = new LineGroupVO();	    
	    gvo.setCompId(compId);
	    gvo.setLineGroupId(lineGroupId);
	    gvo.setLineGroupName(lineGroupName);
	    
	    // set LineUserVO
	    LineUserVO uvo = null;
	    List<LineUserVO> uvoList = new ArrayList<LineUserVO>();
	    String[] attrLines = lineGroupInfo.split(String.valueOf((char) 4));
	    int attrLength = attrLines.length;
	    for (int i=0; i<attrLength; i++) {
		uvo = new LineUserVO();
		String[] attrLine = attrLines[i].split(String.valueOf((char) 2));
		uvo.setCompId(compId);
		uvo.setLineGroupId(lineGroupId);
		uvo.setLineOrder(Integer.parseInt(attrLine[0]));
		uvo.setLineSubOrder(Integer.parseInt(attrLine[1]));
		uvo.setApproverId(attrLine[2]);
		uvo.setApproverName(attrLine[3]);
		uvo.setApproverPos(attrLine[4]);
		uvo.setApproverDeptId(attrLine[5]);
		uvo.setApproverDeptName(attrLine[6]);
		uvo.setAskType(attrLine[7]);
		uvoList.add(uvo);
	    }
	    
	    envOptionAPIService.updateAppLineGroup(gvo, uvoList);
	    mapper.writeValue(res.getOutputStream(), "success");
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	    logger.error(e.getMessage());
	}	
	return null;
    } 
    
    /** 
     * 
     * <pre> 
     *  기본 결재경로그룹을 설정한다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/updateEnvDefaultAppLine.do")
    public ModelAndView updateEnvDefaultAppLine(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String registerId = (String)session.getAttribute("USER_ID");
	String lineGroupId = req.getParameter("lineGroupId");
	String usingType = req.getParameter("usingType");
	String groupType = req.getParameter("groupType");
	
	try {
	    if (lineGroupId == null) {
		throw new NullPointerException("lineGroupId");
	    }
	    if (usingType == null) {
		throw new NullPointerException("usingType");
	    }
	    if (groupType == null) {
		throw new NullPointerException("groupType");
	    }
	    envOptionAPIService.updateDefaultAppLineSet(compId, usingType, registerId, lineGroupId, groupType);
	    mapper.writeValue(res.getOutputStream(), "success");
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  기본 결재경로그룹을 설정한다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/cancelEnvDefaultAppLine.do")
    public ModelAndView cancelEnvDefaultAppLine(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String lineGroupId = req.getParameter("lineGroupId");
	
	try {
	    if (lineGroupId == null) {
		throw new NullPointerException("lineGroupId");
	    }
	    envOptionAPIService.cancelAppLineDefaultYn(compId, lineGroupId);
	    mapper.writeValue(res.getOutputStream(), "success");
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
    
    /** 
     * 
     * <pre> 
     *  수신자그룹 목록을 가져온다.(처리과/문서과 문서책임자)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/charge/listEnvRecvGroup.do")
    public ModelAndView listEnvRecvGroupCharge(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String roleCodes = (String)session.getAttribute("ROLE_CODES");
	String registerDeptId = (String)session.getAttribute("DEPT_ID");
	String registerId = (String)session.getAttribute("USER_ID");
	String groupType = (req.getParameter("groupType") == null ? "GUT004" : req.getParameter("groupType"));
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
	String roleId12 = AppConfig.getProperty("role_cordoccharger", "", "role"); // 문서과 문서담당자
	logger.debug(">>>>>>>>>>>>>> groupType : "+groupType);
	
 

	ModelAndView mav = new ModelAndView("EnvController.listEnvRecvGroup");
	if ("GUT001".equals(groupType) && roleCodes.indexOf(roleId12)>=0) {
	    mav.addObject("gList", envOptionAPIService.listRecvGroup(compId, groupType));
	} else if ("GUT003".equals(groupType) && roleCodes.indexOf(roleId11)>=0) {
	    mav.addObject("gList", envOptionAPIService.listRecvGroup(compId, registerDeptId, groupType));
    	} else {
	    mav.addObject("gList", envOptionAPIService.listRecvGroup(compId, registerId, groupType));
	}
 
	mav.addObject("mode", "charge");
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  수신자그룹 목록을 가져온다.(회사관리자)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/listEnvRecvGroup.do")
    public ModelAndView listEnvRecvGroupAdmin(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
 
	String groupType = "GUT001";

	ModelAndView mav = new ModelAndView("EnvController.listEnvRecvGroup");
	mav.addObject("gList", envOptionAPIService.listRecvGroup(compId, groupType));
	mav.addObject("mode", "admin");
 
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  수신그룹 등록화면을 가져온다.(팝업)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
	@RequestMapping("/app/env/insertEnvRecvPop.do")
    public ModelAndView insertEnvRecvPop(HttpServletRequest req) throws Exception {
	
	HttpSession session = req.getSession();
	String userId = req.getParameter("userid");
	String compId = req.getParameter("compid");
	
	if (compId == null) {
	    userId = (String) session.getAttribute("USER_ID");
	    compId = (String) session.getAttribute("COMP_ID");
	}

	ModelAndView mav = new ModelAndView("EnvController.insertEnvRecvPop");
	
	List<DepartmentVO> result = orgService.selectOrgTreeList(userId, 3);
	List<DepartmentVO> result2 = orgService.selectOrgTreeList(userId, 1);
	DepartmentVO companyVO = result.get(0);
	String orgId = companyVO.getOrgID();
	
	HashMap<String, String> map410 = envOptionAPIService.selectOptionTextMap((String)session.getAttribute("COMP_ID"), "OPT410");
	Iterator keySetIter = map410.keySet().iterator();
	String tabI1 = "", tabI2 = "", tabI3 = "", tabI4 = "";
	
	while(keySetIter.hasNext())
	{
		String key = (String)keySetIter.next();
		if ( key.equals("I1") ) tabI1 = map410.get(key);
		if ( key.equals("I2") ) tabI2 = map410.get(key);
		if ( key.equals("I3") ) tabI3 = map410.get(key);
		if ( key.equals("I4") ) tabI4 = map410.get(key);
	}
	
	List<OrganizationVO> resultSymbol = null;
	LDAPOrganizations LDAPOrgs = null;
	
	if ( tabI3.equals("Y") ) {
		int prefixLength = Integer.parseInt(AppConfig.getProperty("prefixLength", "1", "recvSymbol"));
		resultSymbol = orgService.selectRootIndexByAddrSymPrefix(orgId, prefixLength);
	}
	
	if ( tabI4.equals("Y") ) {
		LDAPOrgs = orgService.getSubLDAPOrg("ROOT");
	}
	
	String opt333 = envOptionAPIService.selectOptionValue(compId, "OPT333"); //수신자 부서 원사용여부 1:부서 2 : 부서원 사용
	mav.addObject("result",result); //대내 수신처
	mav.addObject("result2",result2); //대외수신처
	mav.addObject("resultSymbol",resultSymbol); //수신자기호
	mav.addObject("LDAPOrgs",LDAPOrgs); //행정기관
	mav.addObject("opt333", opt333);
	return mav;
    }
    
    
    /**
     * 
     * <pre> 
     *  수신자그룹을 등록한다.
     * </pre>
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/insertRecvGroup.do") 
    public ModelAndView insertRecvGroup(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String registerId = (String)session.getAttribute("USER_ID");
	String registerName = (String)session.getAttribute("USER_NAME");
	String registerDeptId = (String)session.getAttribute("DEPT_ID");
	String registerDeptName = (String)session.getAttribute("DEPT_NAME");	
	String registDate = DateUtil.getCurrentDate();
	String recvGroupName = CommonUtil.nullTrim(req.getParameter("paramRecvGroupName"));
	String recvGroupId = GuidUtil.getGUID();
	String recvGroupInfo = req.getParameter("paramRecvGroupInfo");
	String recvGroupType = req.getParameter("paramGroupType");
	
	logger.debug(">>>>>>>>>>>>>>>> 수신자그룹 등록 recvGroupType : "+recvGroupType);
	try {
	    if (recvGroupName == null) {
		throw new NullPointerException("recvGroupName");
	    } else if (recvGroupInfo == null) {
		throw new NullPointerException("recvGroupInfo");
	    }
	    // set RecvGroupVO
	    RecvGroupVO gvo = new RecvGroupVO();	    
	    gvo.setCompId(compId);
	    gvo.setRecvGroupId(recvGroupId);
	    gvo.setRecvGroupName(recvGroupName);
	    gvo.setRegisterId(registerId);
	    gvo.setRegisterName(registerName);
	    gvo.setRegisterDeptId(registerDeptId);
	    gvo.setRegisterDeptName(registerDeptName);
	    gvo.setRegistDate(registDate);
	    gvo.setGroupType(recvGroupType);
	    
	    // set RecvUserVO
	    RecvUserVO uvo = null;
	    List<RecvUserVO> uvoList = new ArrayList<RecvUserVO>();
	    String[] recvLines = recvGroupInfo.split(String.valueOf((char) 4));
	    int attrLength = recvLines.length;
	    for (int i=0; i<attrLength; i++) {
		uvo = new RecvUserVO();
		String[] attrLine = recvLines[i].split(String.valueOf((char) 2));
		
		uvo.setCompId(compId);
		uvo.setRecvGroupId(recvGroupId);
		uvo.setRecvId(GuidUtil.getGUID());
		uvo.setReceiverOrder(Integer.parseInt(attrLine[0]));
		uvo.setReceiverType(attrLine[1]);
		uvo.setEnfType(attrLine[2]);		
		if ("DET002".equals(attrLine[2]) || "DET003".equals(attrLine[2]) || "DET010".equals(attrLine[2]) || "DET011".equals(attrLine[2])) {
		    uvo.setRecvDeptId(attrLine[3]);
		} else {
		    uvo.setRecvDeptId(attrLine[2]);
		}		
		uvo.setRecvDeptName(attrLine[4]);
		uvo.setRefDeptId(attrLine[5]);
		uvo.setRefDeptName(attrLine[6]);
		uvo.setRecvUserId(attrLine[7]);
		uvo.setRecvUserName(attrLine[8]);
		uvo.setPostNumber(attrLine[9]);
		uvo.setAddress(attrLine[10]);
		uvo.setTelephone(attrLine[11]);
		uvo.setFax(attrLine[12]);
		uvo.setRecvSymbol(attrLine[13]);
		uvo.setRecvCompId(attrLine[14]);

		uvo.setRefSymbol(attrLine[15]); // jth8172 2012 신결재 TF
		uvo.setRecvChiefName(attrLine[16]); // jth8172 2012 신결재 TF
		uvo.setRefChiefName(attrLine[17]); // jth8172 2012 신결재 TF
		
		uvoList.add(uvo);
	    }
	    envOptionAPIService.insertRecvGroup(gvo, uvoList);
	    mapper.writeValue(res.getOutputStream(), "success");
	} catch (Exception e) {	    
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	    logger.error(e.getMessage());
	}
	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  수신자경로를 가져온다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/listEnvRecvLine.do")
    public ModelAndView listEnvAppLine(HttpServletRequest req, HttpServletResponse res) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String recvGroupId = req.getParameter("recvGroupId");
	
	try {
	    if (recvGroupId == null) {
		throw new NullPointerException("recvGroupId");
	    }
	    List<RecvUserVO> uvoList = envOptionAPIService.listRecvLine(compId, recvGroupId);
	    mapper.writeValue(res.getOutputStream(), envOptionAPIService.checkOrgRecv(uvoList));
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
    /**
     * 
     * <pre> 
     *  수신자 그룹을 검색한다.
     * </pre>
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/searchEnvAppLineGroup.do")
    public ModelAndView searchEnvAppLineGroup(HttpServletRequest req, HttpServletResponse res) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String registerId = (String)session.getAttribute("USER_ID");
	String registerDeptId = (String)session.getAttribute("DEPT_ID");
	String searchDept = req.getParameter("searchDept");

	try {


	    List<RecvGroupVO> recvGroups = envOptionAPIService.searchRecvGroupP(compId, registerDeptId, registerId, searchDept);

	    mapper.writeValue(res.getOutputStream(), recvGroups);
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
   
    /** 
     * 
     * <pre> 
     *  수신자그룹을 삭제한다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/deleteEnvRecvGroup.do")
    public ModelAndView deleteEnvRecvGroup(HttpServletRequest req, HttpServletResponse res) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String recvGroupId = req.getParameter("recvGroupId");
	
	try {
	    if (recvGroupId == null) {
		throw new NullPointerException("recvGroupId");
	    }
	    envOptionAPIService.deleteRecvGroup(compId, recvGroupId);
	    mapper.writeValue(res.getOutputStream(), "success");
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
    /**
     * 
     * <pre> 
     *  수신자그룹을 수정한다.
     * </pre>
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/updateEnvRecvGroup.do") 
    public ModelAndView updateEnvRecvGroup(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String recvGroupName = CommonUtil.nullTrim(req.getParameter("paramRecvGroupName"));
	String recvGroupInfo = req.getParameter("paramRecvGroupInfo");
	String recvGroupId = req.getParameter("recvGroupId");
	
	try {
	    if (recvGroupName == null) {
		throw new NullPointerException("recvGroupName");
	    } else if (recvGroupInfo == null) {
		throw new NullPointerException("recvGroupInfo");
	    }
	    // set RecvGroupVO
	    RecvGroupVO gvo = new RecvGroupVO();	    
	    gvo.setCompId(compId);
	    gvo.setRecvGroupId(recvGroupId);
	    gvo.setRecvGroupName(recvGroupName);
	    
	    // set RecvUserVO
	    RecvUserVO uvo = null;
	    List<RecvUserVO> uvoList = new ArrayList<RecvUserVO>();
	    String[] attrLines = recvGroupInfo.split(String.valueOf((char) 4));
	    int attrLength = attrLines.length;
	    for (int i=0; i<attrLength; i++) {
		uvo = new RecvUserVO();
		String[] attrLine = attrLines[i].split(String.valueOf((char) 2));
		uvo.setCompId(compId);
		uvo.setRecvGroupId(recvGroupId);
		uvo.setRecvId(GuidUtil.getGUID());
		uvo.setReceiverOrder(Integer.parseInt(attrLine[0]));
		uvo.setReceiverType(attrLine[1]);
		uvo.setEnfType(attrLine[2]);
		uvo.setRecvDeptId(attrLine[3]);
		uvo.setRecvDeptName(attrLine[4]);
		uvo.setRefDeptId(attrLine[5]);
		uvo.setRefDeptName(attrLine[6]);
		uvo.setRecvUserId(attrLine[7]);
		uvo.setRecvUserName(attrLine[8]);
		uvo.setPostNumber(attrLine[9]);
		uvo.setAddress(attrLine[10]);
		uvo.setTelephone(attrLine[11]);
		uvo.setFax(attrLine[12]);
		uvo.setRecvSymbol(attrLine[13]);
		uvo.setRecvCompId(attrLine[14]);

		uvo.setRefSymbol(attrLine[15]);
		uvo.setRecvChiefName(attrLine[16]);
		uvo.setRefChiefName(attrLine[17]);

		uvoList.add(uvo);
	    }
	    
	    envOptionAPIService.updateRecvGroup(gvo, uvoList);
	    mapper.writeValue(res.getOutputStream(), "success");
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	    logger.error(e.getMessage());
	}	
	return null;
    } 
    
    
    /** 
     * 
     * <pre> 
     *  우편번호 검색 팝업
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/selectZipcodePop.do") 
    public ModelAndView selectZipcodePop(HttpServletRequest req) throws Exception {
	
	ModelAndView mav = new ModelAndView("EnvController.selectZipcodePop");	
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  우편번호를 검색한다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/listZipcode.do")
    public ModelAndView listZipcode(HttpServletRequest req, HttpServletResponse res) throws Exception {

	String searchKey = req.getParameter("searchKey");
	
	try {
	    if (searchKey == null) {
		throw new NullPointerException("recvGroupId");
	    }
	    List<AddressVO> voList = orgService.selectAddressListByDong(searchKey);
	    mapper.writeValue(res.getOutputStream(), voList);
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  부재정보/대결정보를 가져온다.(회사관리자)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/envEmptyInfo.do") 
    public ModelAndView envEmptyInfoAdmin(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String opt313 = appCode.getProperty("OPT313","OPT313", "OPT"); // 부재처리
	String opt313Value = envOptionAPIService.selectOptionValue(compId, opt313);
	//String userId = (String)session.getAttribute("USER_ID");
	//String roleCodes = (String)session.getAttribute("ROLE_CODES");
	
	ModelAndView mav = new ModelAndView("EnvController.envEmptyInfo");
	mav.addObject("emptyReason", envUserService.selectEmptyReasonList(compId));
	mav.addObject("mode", "admin");
	mav.addObject("opt313", opt313Value);
	/*
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	if (roleCodes.indexOf(roleId10)<0) {
	    //mav.addObject("objUserStatus", orgService.selectEmptyStatus(userId));
	    //mav.addObject("objSubstitute", orgService.selectSubstitute(userId));
	    mav.addObject("emptyInfo", envUserService.selectEmptyInfo(userId));	    
	}
	*/
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  부재정보/대결정보를 가져온다.(문서책임자)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/envEmptyInfo.do") 
    public ModelAndView envEmptyInfoCharge(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String userId = (String)session.getAttribute("USER_ID");
	String opt313 = appCode.getProperty("OPT313","OPT313", "OPT"); // 부재처리
	String opt313Value = envOptionAPIService.selectOptionValue(compId, opt313);
	//String roleCodes = (String)session.getAttribute("ROLE_CODES");
	ModelAndView mav = new ModelAndView("EnvController.envEmptyInfo");
	mav.addObject("emptyReason", envUserService.selectEmptyReasonList(compId));
	//String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	//if (roleCodes.indexOf(roleId10)<0) {
	    //mav.addObject("objUserStatus", orgService.selectEmptyStatus(userId));
	    //mav.addObject("objSubstitute", orgService.selectSubstitute(userId));
	    mav.addObject("emptyInfo", envUserService.selectEmptyInfoForAdmin(userId));	    
	//}
	mav.addObject("mode", "charge");
	mav.addObject("opt313", opt313Value);
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  대결정보를 가져온다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/selectEnvProxyStatus.do")
    public ModelAndView selectEnvProxyStatus(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	String userId = req.getParameter("userId");

	try {
	    if (userId == null) {
		throw new NullPointerException("userId");
	    }
	    //mapper.writeValue(res.getOutputStream(), orgService.selectSubstitute(userId));
	    mapper.writeValue(res.getOutputStream(), envUserService.selectEmptyInfoForAdmin(userId));
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  부재정보를 가져온다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/selectEnvEmptyStatus.do")
    public ModelAndView selectEnvEmptyStatus(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	String userId = req.getParameter("userId");

	try {
	    if (userId == null) {
		throw new NullPointerException("userId");
	    }
	    //mapper.writeValue(res.getOutputStream(), orgService.selectEmptyStatus(userId));
	    mapper.writeValue(res.getOutputStream(), envUserService.selectEmptyInfoForAdmin(userId));
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  부서원 목록을 가져온다.(새창)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/envDeptMember.do") 
    public ModelAndView envDeptMember(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String orgId = (String)session.getAttribute("DEPT_ID");
	
	int orgType = 0;
	
	List<UserVO> voList = orgService.selectUserListByOrgId(compId, orgId, orgType);
	UserVO uvo = new UserVO();
	EmptyInfoVO evo = new EmptyInfoVO();
	int voListSize = voList.size();
	for (int i=0; i<voListSize; i++) {
	    uvo = voList.get(i);
	    evo = envUserService.selectEmptyInfoForAdmin(uvo.getUserUID());
	    if (evo.getIsEmpty()) {
		uvo.setExistence(false);
	    } else {
		uvo.setExistence(true);
	    }
	}	
	
	ModelAndView mav = new ModelAndView("EnvController.envDeptMember");
	mav.addObject("listUserVO", voList);
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  부재정보와 대결자 정보를 저장한다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/insertEnvEmptyStatus.do")
    public ModelAndView insertEnvEmptyStatus(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	boolean result = false;
	//String emptyUserName = req.getParameter("emptyUserName");
	String emptyUserId = req.getParameter("emptyUserId");
	//String emptyDeptName = req.getParameter("emptyDeptName");
	//String emptyPosName = req.getParameter("emptyPosName");
	String userStatus = req.getParameter("userStatus");
	String startDate = req.getParameter("paramStartDate");
	String endDate = req.getParameter("paramEndDate");
	
	//String proxyUserName = req.getParameter("proxyUserName");
	String proxyUserId = req.getParameter("proxyUserId");
	//String proxyDeptName = req.getParameter("proxyDeptName");
	//String proxyPosName = req.getParameter("proxyPosName");
	
	//logger.debug(">>>>>>>>>>>>>>"+emptyUserName+", "+emptyUserId+", "+emptyDeptName+", "+emptyPosName+", "+userStatus+", "+startDate+", "+endDate);
	//logger.debug(">>>>>>>>>>>>>>"+proxyUserName+", "+proxyUserId+", "+proxyDeptName+", "+proxyPosName);
	
	//UserStatus userStatusVO = new UserStatus();
	//Substitute substituteVO = new Substitute();
	EmptyInfoVO vo = new EmptyInfoVO();
	
	vo.setUserId(emptyUserId);
	//userStatusVO.setUserStatus(userStatus);
	vo.setIsEmpty(true);
	vo.setEmptyReason(userStatus);
	vo.setEmptyStartDate(startDate);
	vo.setEmptyEndDate(endDate);
	
	logger.debug("[EnvEmptyStatus Modify] >>>>>>>>>> emptyUserId : "+emptyUserId);
	logger.debug("[EnvEmptyStatus Modify] >>>>>>>>>> proxyUserId : "+proxyUserId);
	
	if (proxyUserId != null && !"".equals(proxyUserId)) {
	    logger.debug("[EnvEmptyStatus Modify] >>>>>>>>>> proxyInfo not null");
	    vo.setIsSubstitute(true);
	    vo.setSubstituteId(proxyUserId);
	    vo.setSubstituteStartDate(startDate);
	    vo.setSubstituteEndDate(endDate);
	} else  {
	    logger.debug("[EnvEmptyStatus Modify] >>>>>>>>>> proxyInfo null");
	    vo.setIsSubstitute(false); 
	}		

	try {
	    result = envUserService.insertEmptyInfo(vo);
	    logger.debug("[EnvEmptyStatus Modify] >>>>>>>>>> result : "+result);
	    if (result) {
		mapper.writeValue(res.getOutputStream(), "success");
	    }
	} catch (Exception e) {
	    logger.debug(e);
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }    
    
    /** 
     * 
     * <pre> 
     *  부재정보를 삭제한다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/deleteEnvEmptyStatus.do")
    public ModelAndView deleteEnvEmptyStatus(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	boolean result = false;
	String userId = req.getParameter("emptyUserId");

	try {
	    result = orgService.deleteEmptyStatus(userId);
	    if (result) {
		mapper.writeValue(res.getOutputStream(), "success");
	    }
	} catch (Exception e) {
	    logger.debug(e);
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  코드그룹 목록을 가져온다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/listEnvCodeMng.do") 
    public ModelAndView listEnvCodeMng(HttpServletRequest req) throws Exception {
	
	ModelAndView mav = new ModelAndView("EnvController.admin.listEnvCodeMng");
	mav.addObject("cList", envOptionAPIService.listAppCode("ROOT"));
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  결재코드 목록을 가져온다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/listEnvAppCode.do")
    public ModelAndView listEnvAppCode(HttpServletRequest req, HttpServletResponse res) throws Exception {

	String codeGroupId = req.getParameter("codeGroupId");	
	try {
	    if (codeGroupId == null) {
		throw new NullPointerException("codeGroupId");
	    }
	    mapper.writeValue(res.getOutputStream(), envOptionAPIService.listAppCode(codeGroupId));
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}
	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  결재코드 설명을 수정한다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/updateEnvAppCode.do")
    public ModelAndView updateEnvAppCode(HttpServletRequest req, HttpServletResponse res) throws Exception {

	String codeId = req.getParameter("codeId");
	String discription = req.getParameter("discription");
	int result;
	try {
	    if (codeId == null) {
		throw new NullPointerException("codeId");
	    }
	    if (discription == null) {
		throw new NullPointerException("discription");
	    }
	    result = envOptionAPIService.updateAppCode(codeId, discription);
	    if (result > 0) {
		mapper.writeValue(res.getOutputStream(), "success");
	    }
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}
	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  전자결재 코드 설명 수정화면을 가져온다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/updateAppCode.do") 
    public ModelAndView updateAppCode(HttpServletRequest req) throws Exception {
	
	ModelAndView mav = new ModelAndView("EnvController.admin.updateAppCode");	
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  부서정보를 가져온다.(처리과 문서책임자)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/charge/selectEnvDeptInfo.do") 
    public ModelAndView selectDeptInfo(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String orgId = (String)session.getAttribute("DEPT_ID");
	ModelAndView mav = new ModelAndView("EnvController.charge.selectEnvDeptInfo");	
	mav.addObject("orgVo", orgService.selectOrganization(orgId));
	mav.addObject("mode", "charge");
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  부서정보를 가져온다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectEnvDeptInfoAjax.do") 
    public ModelAndView selectDeptInfoAjax(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	String orgId = req.getParameter("orgId");

	try {
	    if (orgId == null) {
		throw new NullPointerException("orgId");
	    }
	    mapper.writeValue(res.getOutputStream(), orgService.selectOrganization(orgId));
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  부서정보를 가져온다.(회사관리자)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectEnvDeptInfoAdmin.do") 
    public ModelAndView selectDeptInfoAdmin(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String orgId = (String)session.getAttribute("DEPT_ID");
	ModelAndView mav = new ModelAndView("EnvController.charge.selectEnvDeptInfo");	
	mav.addObject("orgVo", orgService.selectOrganization(orgId));
	mav.addObject("mode", "admin");
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  부서정보를 수정한다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/charge/updateEnvDeptInfo.do") 
    public ModelAndView updateDeptInfo(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	boolean result = false;	
	HttpSession session = req.getSession();
	String roleCodes = (String)session.getAttribute("ROLE_CODES");
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	String mode = req.getParameter("mode");
	String orgId = "";
	if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) {
	    orgId = req.getParameter("orgId");
	} else {
	    orgId = (String)session.getAttribute("DEPT_ID");
	}
	String orgAbbrName = req.getParameter("orgAbbrName");
	String addrSymbol = req.getParameter("addrSymbol");
	String chiefPosition = req.getParameter("chiefPosition");
	String zipCode = req.getParameter("zipCode");
	String address = req.getParameter("address");
	String addressDetail = req.getParameter("addressDetail");
	String telephone = req.getParameter("telephone");
	String fax = req.getParameter("fax");
	String email = req.getParameter("email");
	String homepage = req.getParameter("homepage");
	String outgoingName = req.getParameter("outgoingName");
	
	OrganizationVO vo = new OrganizationVO();
	vo.setOrgID(orgId);
	vo.setOrgAbbrName(orgAbbrName);
	vo.setAddrSymbol(addrSymbol);
	vo.setChiefPosition(chiefPosition);
	vo.setZipCode(zipCode);
	vo.setAddress(address);
	vo.setAddressDetail(addressDetail);
	vo.setTelephone(telephone);
	vo.setFax(fax);
	vo.setEmail(email);
	vo.setHomepage(homepage);
	vo.setOutgoingName(outgoingName);
	
	try {	    
	    result = orgService.updateOrganization(vo);
	    if (result) {
		mapper.writeValue(res.getOutputStream(), "success");
	    }
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}
	return null;
    }
    
    
    /** 
     * 
     * <pre> 
     *  개인 사진/서명 정보를 가져온다.(관리자: 조직도에서 임직원 선택시)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectEnvPersonalInfoImg.do") 
    public ModelAndView selectPersonalInfoImg(HttpServletRequest req, HttpServletResponse res) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String userId = req.getParameter("userId");
	HashMap<String, String> mapResult = new HashMap<String, String>();
	
	FileVO picture = envUserService.selectUserImage(compId, userId, 0);
	FileVO sign = envUserService.selectUserImage(compId, userId, 2);
	//String pictureYn = picture.getImageData() == null ? "N" : "Y";
	//String signYn = sign.getImageData() == null ? "N" : "Y";
	String pictureYn = picture.getFilePath() == null ? "N" : "Y";
	String signYn = sign.getFilePath() == null ? "N" : "Y";
	
	mapResult.put("pictureYn", pictureYn);
	mapResult.put("signYn", signYn);
	
	try {
	    if (userId == null) {
		throw new NullPointerException("userId");
	    }
	    mapper.writeValue(res.getOutputStream(), (Object)mapResult);
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}
	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  개인정보를 가져온다.(개인)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/selectEnvPersonalInfo.do") 
    public ModelAndView selectPersonalInfo(HttpServletRequest req, HttpServletResponse res) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String userId = (String)session.getAttribute("USER_ID");
	String OPT301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재시 인증방식 - 1일때 전자결재 비밀번호
	String pwUseYn = envOptionAPIService.selectOptionValue(compId, OPT301);
	if ("1".equals(pwUseYn)) {
	    pwUseYn = "Y";
	} else {
	    pwUseYn = "N"; 
	}
	
	FileVO picture = envUserService.selectUserImage(compId, userId, 0);
	FileVO sign = envUserService.selectUserImage(compId, userId, 2);
	logger.debug("picture : "+picture);
	logger.debug("sign : "+sign);
	//String pictureYn = (picture == null || picture.getImageData() == null) ? "N" : "Y";
	//String signYn = (sign == null || sign.getImageData() == null) ? "N" : "Y";
	String pictureYn = (picture == null || picture.getFilePath() == null) ? "N" : "Y";
	String signYn = (sign == null || sign.getFilePath() == null) ? "N" : "Y";
	
	UserVO userVO = new UserVO();
	userVO = orgService.selectUserByUserId(userId);
	
	//logger.debug(">>>>>>>>>>>>> picture.getFilePath() : "+picture.getFilePath());
	//logger.debug(">>>>>>>>>>>>> sign.getFilePath() : "+sign.getFilePath());
	
	//logger.debug(">>>>>>>>>>>>> picture.getImageData() : "+picture.getImageData());
	//logger.debug(">>>>>>>>>>>>> sign.getImageData() : "+sign.getImageData());
	
	//logger.debug(">>>>>>>>>>>>> pictureYn : "+pictureYn);
	//logger.debug(">>>>>>>>>>>>> signYn : "+signYn);
	
	
	//>>>>>>>>>>>>> 알림 발송 테스트 시작 <<<<<<<<<<<<<<<<<<<<<
	/*
	String[] receiver;// = {"C2002273", "C2007089"};
	if ("004".equals(compId)) {
	    receiver = new String[] {"C2002273", "C2007089"};
	} else {
	    receiver = new String[] {"O9199014", "O9199015"};
	}
	SendMessageVO vo = new SendMessageVO();
	vo.setReceiverId(receiver);
	vo.setCompId(compId);
	vo.setPointCode("I1");
	vo.setDocId("APP0E2C1216B258D131D22CFD64FFFF803D");
	vo.setDocTitle("통합그룹웨어 구축 및 운영방안 협의");
	vo.setUsingType("DPI001");
	vo.setElectronicYn("Y");
	vo.setLoginId("O9199015");
	//sendMessageService.sendMessage(vo);
	String charSet = req.getParameter("charSet");
	String testMsg = messageSource.getMessage("alarm.message.title.I1", null, (Locale)session.getAttribute("LANG_TYPE"));
	if (charSet == null || "".equals(charSet)) {
	    testMsg = "Approval Send SMS Service Test...";
	}
	logger.debug(">>>>>>>>>>>>>>>>>>>>> charSet : "+charSet);
	logger.debug(">>>>>>>>>>>>>>>>>>>>> testMsg : "+testMsg);
	if (charSet != null && !"".equals(charSet)) {
	    testMsg = new String(testMsg.getBytes("UTF-8"), charSet);
	}
	logger.debug("@@@@@@@@@@@@@@@@@@@@@ testMsg : "+testMsg);

	if ("004".equals(compId)) {
	    //vo.setMessageTitle(testMsg);
	    //vo.setMessageContent("test message content...");
	    //vo.setReturnURL("http://10.1.22.105/ep/app/approval/selectAppDoc.do??docId=APP0E2C1216B258D131D22CFD64FFFF803D&lobCode=LOB003");
	    sendMessageService.sendSMS(vo, (Locale)session.getAttribute("LANG_TYPE"));
	} else {
	    sendMessageService.sendMessage(vo, (Locale)session.getAttribute("LANG_TYPE"));
	}*/
	//sendMessageService.sendMessage(vo, (Locale)session.getAttribute("LANG_TYPE"));	
	//sendMessageService.makeATHFile(compId, "I1");
	//logger.debug(">>>>> >>>>> >>>>> >>>>> >>>>> >>>>> >>>>> >>>>>");*/
	//>>>>>>>>>>>>> 알림 발송 테스트 끝 <<<<<<<<<<<<<<<<<<<<<
	
	/*/ ThumbNail Image 테스트
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	
	String test = req.getParameter("n");
	String name = req.getParameter("fname");
	File file = new File(uploadTemp+"/test"+test+".bmp");	
	logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 이미지 : "+uploadTemp+"/test"+test+".bmp");
	BufferedImage bi = ImageIO.read(file);
	File imgThumbNail = new File(uploadTemp+"/imgThumbNail.jpg");
	ImageIO.write(bi, "jpg", imgThumbNail);
	bi.flush();
	

	//optionUtil = new OptionUtil();
	//optionUtil.createThumbNail(uploadTemp+"/test.gif", uploadTemp+"/999/test.gif", 150, 150, 80);
	//optionUtil = new OptionUtil();
	//optionUtil.createThumbNail(uploadTemp+"/Chrysanthemum.jpg", uploadTemp+"/999/"+name+".jpg", 150, 150, 80);
	optionUtil = new OptionUtil();
	optionUtil.createThumbNail(uploadTemp+"/imgThumbNail.jpg", uploadTemp+"/999/"+name+".jpg", 150, 150, 80);*/
	
	
	ModelAndView mav = new ModelAndView("EnvController.selectEnvPersonalInfo");	
	mav.addObject("pwUseYn", pwUseYn);
	mav.addObject("pictureYn", pictureYn);
	mav.addObject("signYn", signYn);
	mav.addObject("mode", "general");
	mav.addObject("userVO", userVO);
	return mav;
    }
    
    /**
     * 
     * <pre> 
     *  전자결재용 팩스와 주소정보를 가져오기 위해 ajax 호출
     * </pre>
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectOfficeApprInfoAjax.do") 
    public ModelAndView selectOfficeApprInfoAjax(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	String userId = StringUtil.null2str(req.getParameter("userId"),"");

	try {
	    if ("".equals(userId)) {
		throw new NullPointerException("userId");
	    }
	    mapper.writeValue(res.getOutputStream(), orgService.selectUserByUserId(userId));
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
    /**
     * 
     * <pre> 
     *  전자결재용 팩스와 주소를 변경한다.
     * </pre>
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/charge/updateUserAppOfficeInfo.do") 
    public ModelAndView updateUserAppOfficeInfo(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	boolean result = false;	
	HttpSession session = req.getSession();
	String userId = "";
	String mode = StringUtil.null2str(req.getParameter("mode"),"");
	
	if("admin".equals(mode)){
	    userId = StringUtil.null2str(req.getParameter("officeInfoUserId"),"");
	}else{
	    userId = (String)session.getAttribute("USER_ID");
	}
	
	
	String officeFax = StringUtil.null2str(req.getParameter("officeFax"), "");
	String officeZipCode = StringUtil.null2str(req.getParameter("officeZipCode"), "");
	String officeAddress = StringUtil.null2str(req.getParameter("officeAddress"), "");
	String officeAddressDetail = StringUtil.null2str(req.getParameter("officeAddressDetail"), "");
	
	UserVO userVO = new UserVO();
	userVO.setUserUID(userId);
	userVO.setOfficeFax(officeFax);
	userVO.setOfficeZipCode(officeZipCode);
	userVO.setOfficeAddr(officeAddress);
	userVO.setOfficeDetailAddr(officeAddressDetail);
	
	try {
	   
	    result = orgService.updateUserAppOfficeInfo(userVO);
	    logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	    logger.debug("result (controller):"+result);
	    logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	    if (result) {
		mapper.writeValue(res.getOutputStream(), "success");
	    }
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}
	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  개인정보를 가져온다.(관리자)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/envPersonalInfoAdmin.do") 
    public ModelAndView selectPersonalInfoAdmin(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String OPT301 = appCode.getProperty("OPT301", "OPT301", "OPT"); // 결재시 인증방식 - 1일때 전자결재 비밀번호
	String pwUseYn = envOptionAPIService.selectOptionValue(compId, OPT301);
	if ("1".equals(pwUseYn)) {
	    pwUseYn = "Y";
	} else {
	    pwUseYn = "N"; 
	}
	
	ModelAndView mav = new ModelAndView("EnvController.selectEnvPersonalInfo");
	mav.addObject("pwUseYn", pwUseYn);
	mav.addObject("mode", "admin");
	//mav.addObject("orgVo", orgService.selectOrganization(orgId));
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  개인정보 이미지(사진, 서명)를 표시한다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/EnvComImage.do") 
    public ModelAndView envComImage(HttpServletRequest req) throws Exception {
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String userId = req.getParameter("userId");
	int nType = Integer.parseInt(req.getParameter("nType"));	
	FileVO file = envUserService.selectUserImage(compId, userId, nType);
	
	ModelAndView mav = new ModelAndView("EnvController.commonImage");
	mav.addObject("fileVO", file);
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  개인정보 - 사진, 서명 이미지 삭제
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/deletePersonalImg.do")
    public ModelAndView deletePersonalImg(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();
	String roleCodes = (String)session.getAttribute("ROLE_CODES");
	String userId = (String)session.getAttribute("USER_ID");
	String mode = req.getParameter("modeImg") != null ? req.getParameter("modeImg") : "general";
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) {
	    userId = req.getParameter("userIdImg");
	}

	//String userId = req.getParameter("userId");
	int nType = Integer.parseInt(req.getParameter("nType"));
	boolean result = false;
	logger.debug(">>>>>>>>>>>>>>>>> userId "+userId);
	logger.debug(">>>>>>>>>>>>>>>>> nType "+nType);
	try {
	    if (userId == null) {
		throw new NullPointerException("userId");
	    }
	    result = envUserService.deleteUserImage(userId, nType);
	    if (result) {
		mapper.writeValue(res.getOutputStream(), "success");
	    }
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}
	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  개인정보 사진, 서명 수정 
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/updatePersonalImg.do")
    public ModelAndView updateEnvImage(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String roleCodes = (String)session.getAttribute("ROLE_CODES");
	boolean result = false;
	String userId = (String)session.getAttribute("USER_ID");
	String mode = req.getParameter("modeImg") != null ? req.getParameter("modeImg") : "general";
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) {
	    userId = req.getParameter("userIdImg");
	}
	String nType = req.getParameter("nType");
	String fileName = req.getParameter("fileName"+nType);
	//String filePath = req.getParameter("LocalFilePath"+nType);
	
	String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
	String filePath = uploadTemp + "/" + compId + "/" + fileName;
	
	logger.debug(">>>>>>>>>>>>> filePath : "+filePath);
	//logger.debug(">>>>>>>>>>>>> fileType : "+filePath.substring(filePath.lastIndexOf(".")+1).toLowerCase());
	
	FileVO fileVO = new FileVO();
	fileVO.setProcessorId(userId);
	fileVO.setFilePath(filePath);
	fileVO.setFileType(filePath.substring(filePath.lastIndexOf(".")+1).toLowerCase());
	
	try {
	    if (nType == null) {
		throw new NullPointerException("nType");
	    }
	    if (filePath == null) {
		throw new NullPointerException("filePath");
	    }
	    result = envUserService.updateUserImage(fileVO, Integer.parseInt(nType));
	    logger.debug("result : "+result);
	    if (result) {
		mapper.writeValue(res.getOutputStream(), "success");
	    }
	} catch (Exception e) {
	    logger.debug("Exception : "+e);
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}
	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  개인정보 - 전자결재 비밀번호를 수정한다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/updateAppPassword.do")
    public ModelAndView updateAppPassword(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();
	String userId = "";
	String roleCodes = (String)session.getAttribute("ROLE_CODES");
	String mode = req.getParameter("mode") != null ? req.getParameter("mode") : "general";
	String roleId10 = AppConfig.getProperty("role_appadmin", "", "role"); // 시스템관리자
	if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) {
	    userId = req.getParameter("userId");
	} else {
	    userId = (String)session.getAttribute("USER_ID");
	}
	String newPw = CommonUtil.nullTrim(req.getParameter("newencryptedpassword"));
	String encryptedPwd = "";
	SeedBean.setRoundKey(req);
	if ("general".equals(mode)) {
	    String beforePw = CommonUtil.nullTrim(req.getParameter("encryptedpassword"));
	    String password = "";
	    if ("".equals(beforePw)) {
		password = "";
	    	encryptedPwd = "";
	    } else {
		password = SeedBean.doDecode(req, beforePw);
		encryptedPwd = EnDecode.EncodeBySType(password);
	    }
	}	
	String newpassword = SeedBean.doDecode(req, newPw);	
	String newencryptedPwd = EnDecode.EncodeBySType(newpassword);
	boolean result = false;
	String resultMsg = "";
	
	try {
	    if ("general".equals(mode)) {
    	    	if (!orgService.compareApprovalPassword(userId, encryptedPwd)) {
    	    	    resultMsg = "nomatching";
    	    	} else {
    	    	    result = orgService.updateApprovalPassword(userId, newencryptedPwd);
    	    	    if (result) {
    		    resultMsg = "success";
    	    	    }
    	    	}
	    }
	    if (roleCodes.indexOf(roleId10)>=0 && "admin".equals(mode)) {
		result = orgService.updateApprovalPassword(userId, newencryptedPwd);
		if (result) {
		    resultMsg = "success";
		}
	    }
	    mapper.writeValue(res.getOutputStream(), resultMsg);
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}
	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  개인정보 - 전자결재 비밀번호를 초기화한다.(사번으로 초기화)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/initializePassword.do")
    public ModelAndView initializePassword(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	//HttpSession session = req.getSession();
	String userId = req.getParameter("userId");	
	String resultMsg = "success";
	UserVO userVO = orgService.selectUserByUserId(userId);
	String loginId = userVO.getUserID(); // 사번
	String newencryptedPwd = EnDecode.EncodeBySType(loginId);	
	
	//logger.debug(">>>>>>>>>>>>>>>>>> loginId : "+loginId);
	//logger.debug(">>>>>>>>>>>>>>>>>> newencryptedPwd : "+newencryptedPwd);
	try {
	    
	    if (orgService.updateApprovalPassword(userId, newencryptedPwd)) {
		resultMsg = "success";
	    }

	    mapper.writeValue(res.getOutputStream(), resultMsg);
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}
	return null;
    }
    
    
    /** 
     * 
     * <pre> 
     *  공람자그룹 목록을 가져온다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/listEnvPubViewGroup.do")
    public ModelAndView listEnvPubViewGroup(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String registerId = (String)session.getAttribute("USER_ID");
	String registerDeptId = (String)session.getAttribute("DEPT_ID");
	String groupType = (req.getParameter("groupType") == null ? "GUT004" : req.getParameter("groupType"));
	ModelAndView mav = new ModelAndView("EnvController.listEnvPubViewGroup");
	mav.addObject("gList", envOptionAPIService.listPubViewGroup(compId, registerId, registerDeptId, groupType));
	return mav;
    }
    
    /** 
     * 
     * <pre> 
     *  공람자그룹 관리에서 공람자 등록화면을 호출한다.(팝업화면)
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/envPubView.do") 
    public ModelAndView envPubView(HttpServletRequest req) throws Exception {
	
	HttpSession session = req.getSession();
	String userId = req.getParameter("userid");
	String compId = req.getParameter("compid");
	
	String usingType = req.getParameter("usingType");
	usingType = (usingType == null?appCode.getProperty("DPI001","DPI001","DPI"):usingType);
	
	if (compId == null) {
	    userId = (String) session.getAttribute("USER_ID");
	    compId = (String) session.getAttribute("COMP_ID");
	}
	
	ModelAndView mav = new ModelAndView("EnvController.envPubView");
	//Departments departments =  orgService.selectOrgTree(userId, 3);
	//List<Department> result = departments.getDepartmentList();
	List<DepartmentVO> result = orgService.selectOrgTreeList(userId, 3);
	
	mav.addObject("result",result); //대내 수신처
	mav.addObject("usingType", usingType);
	return mav;
    }
    
    /**
     * 
     * <pre> 
     *  공람자그룹을 등록한다.
     * </pre>
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/insertPubViewGroup.do") 
    public ModelAndView insertPubViewGroup(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String registerId = (String)session.getAttribute("USER_ID");
	String registerName = (String)session.getAttribute("USER_NAME");
	String registerDeptId = (String)session.getAttribute("DEPT_ID");
	String registerDeptName = (String)session.getAttribute("DEPT_NAME");	
	String registDate = DateUtil.getCurrentDate();
	String pubviewGroupName = CommonUtil.nullTrim(req.getParameter("paramPubviewGroupName"));
	String pubviewGroupId = GuidUtil.getGUID();
	String pubviewGroupInfo = req.getParameter("paramPubviewGroupInfo");
	String pubviewGroupType = req.getParameter("paramGroupType");
	
	logger.debug(">>>>>>>>>>>>>>>> 공람자그룹 등록 pubviewGroupType : "+pubviewGroupType);
	logger.debug(">>>>>>>>>>>>>>>> 공람자그룹 등록 pubviewGroupInfo : "+pubviewGroupInfo);
	
	try {
	    if (pubviewGroupName == null) {
		throw new NullPointerException("pubviewGroupName");
	    } else if (pubviewGroupInfo == null) {
		throw new NullPointerException("pubviewGroupInfo");
	    }
	    // set PubViewGroupVO
	    PubViewGroupVO gvo = new PubViewGroupVO();	    
	    gvo.setCompId(compId);
	    gvo.setPubviewGroupId(pubviewGroupId);
	    gvo.setPubviewGroupName(pubviewGroupName);
	    gvo.setRegisterId(registerId);
	    gvo.setRegisterName(registerName);
	    gvo.setRegisterDeptId(registerDeptId);
	    gvo.setRegisterDeptName(registerDeptName);
	    gvo.setRegistDate(registDate);
	    gvo.setGroupType(pubviewGroupType);
	    
	    // set PubViewUserVO
	    PubViewUserVO uvo = null;
	    List<PubViewUserVO> uvoList = new ArrayList<PubViewUserVO>();
	    String[] pubviewLines = pubviewGroupInfo.split(String.valueOf((char) 4));
	    int attrLength = pubviewLines.length;
	    for (int i=0; i<attrLength; i++) {
		uvo = new PubViewUserVO();
		String[] attrLine = pubviewLines[i].split(String.valueOf((char) 2));
		
		uvo.setCompId(compId);
		uvo.setPubviewGroupId(pubviewGroupId);
		uvo.setPubReaderId(attrLine[0]);
		uvo.setPubReaderName(attrLine[1]);
		uvo.setPubReaderPos(attrLine[2]);
		uvo.setPubReaderDeptId(attrLine[3]);
		uvo.setPubReaderDeptName(attrLine[4]);
		uvo.setPubReaderRole(attrLine[5]);
		uvo.setPubReaderOrder(Integer.parseInt(attrLine[7]));
		uvoList.add(uvo);
	    }
	    envOptionAPIService.insertPubViewGroup(gvo, uvoList);
	    mapper.writeValue(res.getOutputStream(), "success");
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	    logger.error(e.getMessage());
	}
	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  공람자경로를 가져온다.
     * </pre>
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/listEnvPubView.do")
    public ModelAndView listEnvPubView(HttpServletRequest req, HttpServletResponse res) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String pubviewGroupId = req.getParameter("pubviewGroupId");
	
	try {
	    if (pubviewGroupId == null) {
		throw new NullPointerException("pubviewGroupId");
	    }
	    List<PubViewUserVO> uvoList = envOptionAPIService.listPubViewLine(compId, pubviewGroupId);
	    mapper.writeValue(res.getOutputStream(), envOptionAPIService.checkOrgPubView(uvoList));
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
    /** 
     * 
     * <pre> 
     *  공람자그룹을 삭제한다.
     * </pre>
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/deleteEnvPubViewGroup.do")
    public ModelAndView deleteEnvPubViewGroup(HttpServletRequest req, HttpServletResponse res) throws Exception {
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String pubviewGroupId = req.getParameter("pubviewGroupId");
	
	try {
	    if (pubviewGroupId == null) {
		throw new NullPointerException("pubviewGroupId");
	    }
	    envOptionAPIService.deletePubViewGroup(compId, pubviewGroupId);
	    mapper.writeValue(res.getOutputStream(), "success");
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
    /**
     * 
     * <pre> 
     *  공람자그룹을 수정한다.
     * </pre>
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/updateEnvPubViewGroup.do") 
    public ModelAndView updateEnvPubViewGroup(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String pubviewGroupName = CommonUtil.nullTrim(req.getParameter("paramPubviewGroupName"));	
	String pubviewGroupId = req.getParameter("pubviewGroupId");
	String pubviewGroupInfo = req.getParameter("paramPubviewGroupInfo");
	
	try {
	    if (pubviewGroupName == null) {
		throw new NullPointerException("pubviewGroupName");
	    } else if (pubviewGroupInfo == null) {
		throw new NullPointerException("pubviewGroupInfo");
	    }
	    // set PubViewGroupVO
	    PubViewGroupVO gvo = new PubViewGroupVO();	    
	    gvo.setCompId(compId);
	    gvo.setPubviewGroupId(pubviewGroupId);
	    gvo.setPubviewGroupName(pubviewGroupName);
	    
	    // set PubViewUserVO
	    PubViewUserVO uvo = null;
	    List<PubViewUserVO> uvoList = new ArrayList<PubViewUserVO>();
	    String[] attrLines = pubviewGroupInfo.split(String.valueOf((char) 4));
	    int attrLength = attrLines.length;
	    for (int i=0; i<attrLength; i++) {
		uvo = new PubViewUserVO();
		String[] attrLine = attrLines[i].split(String.valueOf((char) 2));
		uvo.setCompId(compId);
		uvo.setPubviewGroupId(pubviewGroupId);
		uvo.setPubReaderId(attrLine[0]);
		uvo.setPubReaderName(attrLine[1]);
		uvo.setPubReaderPos(attrLine[2]);
		uvo.setPubReaderDeptId(attrLine[3]);
		uvo.setPubReaderDeptName(attrLine[4]);
		uvo.setPubReaderRole(attrLine[5]);
		uvo.setPubReaderOrder(Integer.parseInt(attrLine[7]));
		uvoList.add(uvo);
	    }
	    
	    envOptionAPIService.updatePubViewGroup(gvo, uvoList);
	    mapper.writeValue(res.getOutputStream(), "success");
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	    logger.error(e.getMessage());
	}	
	return null;
    } 
    
    
    /** 
     * 
     * <pre> 
     *  회기정보를 조회한다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/selectPeriodInfo.do") 
    public ModelAndView selectPeriodInfo(HttpServletRequest req) throws Exception {	
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String periodType = AppConfig.getProperty("periodType","Y", "etc");
	
	ModelAndView mav = new ModelAndView("EnvController.admin.periodInfo");	
	mav.addObject("voList", envOptionAPIService.listAllPeriod(compId));
	mav.addObject("currentPeriod", envOptionAPIService.getCurrentPeriodStr(compId));
	mav.addObject("latestPeriod", envOptionAPIService.getLatestPeriod(compId, periodType));
	return mav;
    }
    
    /**
     * 
     * <pre> 
     *  회기를 등록한다.
     * </pre>
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/insertPeriod.do") 
    public ModelAndView insertPeriod(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String periodType = AppConfig.getProperty("periodType","Y", "etc");
	
	int result;
	try {
	    result = envOptionAPIService.insertPeriod(compId, periodType);
	    if (result > 0) {
		mapper.writeValue(res.getOutputStream(), "success");
	    } else {
		mapper.writeValue(res.getOutputStream(), "fail");
	    }
		
	} catch (Exception e) {	    
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	    logger.error(e.getMessage());
	}
	return null;
    }
    
    /**
     * 
     * <pre> 
     *  회기를 삭제한다.
     * </pre>
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/deletePeriod.do") 
    public ModelAndView deletePeriod(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String periodId = req.getParameter("periodId");
	
	int result;
	try {
	    result = envOptionAPIService.deletePeriod(compId, periodId);
	    if (result > 0) {
		mapper.writeValue(res.getOutputStream(), "success");
	    } else {
		mapper.writeValue(res.getOutputStream(), "fail");
	    }
		
	} catch (Exception e) {	    
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	    logger.error(e.getMessage());
	}
	return null;
    }
    
    /**
     * 
     * <pre> 
     *  회기를 수정한다.
     * </pre>
     * @param req
     * @param res
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/updatePeriod.do") 
    public ModelAndView updatePeriod(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
	HttpSession session = req.getSession();
	String compId = (String)session.getAttribute("COMP_ID");
	String periodId = req.getParameter("periodId");
	String startDate = "";
	if (req.getParameter("startDate"+periodId) != null) {
	    startDate = req.getParameter("startDate"+periodId) + " 00:00:00";
	}
	String endDate = req.getParameter("endDate"+periodId) + " 23:59:59";
	
	logger.debug(">>>>>>>>>>>>>>>>>> startDate : "+startDate);
	logger.debug(">>>>>>>>>>>>>>>>>> endDate : "+endDate);
	
	int result=0;
	try {
	    result = envOptionAPIService.updatePeriod(compId, periodId, startDate, endDate);
	    if (result > 0) {
		mapper.writeValue(res.getOutputStream(), "success");
	    } else {
		mapper.writeValue(res.getOutputStream(), "fail");
	    }
		
	} catch (Exception e) {	    
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	    logger.error(e.getMessage());
	}
	return null;
    }

    
    /** 
     * 
     * <pre> 
     *  관리자 환경설정 좌측 메뉴 조회
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/MenuOptionAdmin.do") 
    public ModelAndView menuOptionAdmin(HttpServletRequest req) throws Exception {
	HttpSession session = req.getSession();
	
	String compId = StringUtil.null2str((String) session.getAttribute("COMP_ID"), ""); // 사용자 소속 회사 아이디
	
	String opt204Yn = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT204", "OPT204", "OPT"));
	String opt205Yn = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT205", "OPT205", "OPT"));
	String opt207Yn = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT207", "OPT207", "OPT"));
	
	ModelAndView mav = new ModelAndView("EnvController.admin.menuOptionAdmin");	
	mav.addObject("opt204Yn",opt204Yn);
	mav.addObject("opt205Yn",opt205Yn);
	mav.addObject("opt207Yn",opt207Yn);
	
	return mav;
    } 
    
    

    /**
     * <pre> 
     *  조직 이미지 정보 조회 화면
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/selectOrgImageInfo.do")
    public ModelAndView selectOrgImageInfo(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();
	String roleCodes = "^" + (String) session.getAttribute("ROLE_CODES") + "^";
	String role_appadmin = "^" + AppConfig.getProperty("role_appadmin", "", "role") + "^"; // 시스템관리자
	String role_sealadmin = "^" + AppConfig.getProperty("role_sealadmin", "", "role") + "^"; // 기관날인관리자
	String role_signatoryadmin = "^" + AppConfig.getProperty("role_signatoryadmin", "", "role") + "^"; // 부서날인관리자

	String permission = "";

	if (roleCodes.indexOf(role_appadmin) != -1) {
	    permission = "A";
	} else if (roleCodes.indexOf(role_sealadmin) != -1) {
	    permission = "I";
	} else if (roleCodes.indexOf(role_signatoryadmin) != -1) {
	    permission = "D";
	}

	String imageId = req.getParameter("imageId");

	OrgImage orgImage = orgService.selectOrgImage(imageId);


	ModelAndView mav = new ModelAndView("EnvController.selectOrgImage");
	mav.addObject("orgImage", orgImage);
	mav.addObject("permission", permission);

	return mav;
    }

    /**
     * <pre> 
     *  조직 이미지 정보 등록,수정 화면
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/insertOrgImageInfo.do")
    public ModelAndView insertOrgImageInfo(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();
	String roleCodes = "^" + (String) session.getAttribute("ROLE_CODES") + "^";
	String role_appadmin = "^" + AppConfig.getProperty("role_appadmin", "", "role") + "^"; // 시스템관리자
	String role_sealadmin = "^" + AppConfig.getProperty("role_sealadmin", "", "role") + "^"; // 기관날인관리자
	String role_signatoryadmin = "^" + AppConfig.getProperty("role_signatoryadmin", "", "role") + "^"; // 부서날인관리자

	String permission = "";

	if (roleCodes.indexOf(role_appadmin) != -1) {
	    permission = "A";
	} else if (roleCodes.indexOf(role_sealadmin) != -1) {
	    permission = "I";
	} else if (roleCodes.indexOf(role_signatoryadmin) != -1) {
	    permission = "D";
	}
	
	String imageId = req.getParameter("imageId");
	
	OrgImage orgImage = new OrgImage();
	OrganizationVO organizationVO = new OrganizationVO();
	String orgId = null;
	if(imageId != null && !"".equals(imageId)) {
	    orgImage = orgService.selectOrgImage(imageId);
	    orgId = orgImage.getOrgID();
	} else {
	    orgId = req.getParameter("orgId");

	    String imageTypeStr = req.getParameter("imageType");
	    int imageType = 0;
	    if(imageTypeStr != null && !"".equals(imageTypeStr)) {
		imageType = Integer.parseInt(imageTypeStr);
	    }

	    orgImage.setImageType(imageType);
	    orgImage.setSizeWidth(20);
	    orgImage.setSizeHeight(20);
	}
	if(orgId == null || "".equals(orgId)) {
	    orgId = (String) session.getAttribute("DEPT_ID");
	}

	organizationVO = orgService.selectOrganization(orgId);

	ModelAndView mav = new ModelAndView("EnvController.insertOrgImage");
	mav.addObject("orgImage", orgImage);
	mav.addObject("organizationVO", organizationVO);
	mav.addObject("permission", permission);

	return mav;
    }

    /**
     * <pre> 
     *  조직 이미지 정보 등록,수정
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/updateOrgImageInfo.do")
    public ModelAndView updateOrgImageInfo(MultipartHttpServletRequest req, HttpServletResponse res) throws Exception {

	HttpSession session = req.getSession();
	String roleCodes = "^" + (String) session.getAttribute("ROLE_CODES") + "^";
	String role_appadmin = "^" + AppConfig.getProperty("role_appadmin", "", "role") + "^"; // 시스템관리자
	String role_sealadmin = "^" + AppConfig.getProperty("role_sealadmin", "", "role") + "^"; // 기관날인관리자
	String role_signatoryadmin = "^" + AppConfig.getProperty("role_signatoryadmin", "", "role") + "^"; // 부서날인관리자

	String permission = "";

	if (roleCodes.indexOf(role_appadmin) != -1) {
	    permission = "A";
	} else if (roleCodes.indexOf(role_sealadmin) != -1) {
	    permission = "I";
	} else if (roleCodes.indexOf(role_signatoryadmin) != -1) {
	    permission = "D";
	}

	String imageId = req.getParameter("imageID");

	OrgImage orgImage = (OrgImage)Transform.transformToDmo("com.sds.acube.app.idir.org.orginfo.OrgImage", req);
	String disuseYN = req.getParameter("disuseYN");
	if(disuseYN != null && "1".equals(disuseYN)) orgImage.setDisuseYN(true);
	if(!orgImage.getDisuseYN()) {
	    orgImage.setDisuseDate("");
	}

	if(imageId == null || "".equals(imageId)) {
	    orgImage.setImageID(GuidUtil.getGUID());
	    orgImage.setRegistrationDate(DateUtil.getCurrentDate("yyyy-MM-dd"));
	} else {
	    OrgImage orgImagePre = orgService.selectOrgImage(imageId);
	    String regDate = orgImagePre.getRegistrationDate();
	    if(regDate != null && !"".equals(regDate)) {
		orgImage.setRegistrationDate(regDate);
	    }
	    String disuseDate = orgImagePre.getDisuseDate();
	    if(disuseDate != null && !"".equals(disuseDate)) {
		orgImage.setDisuseDate(disuseDate);
	    }
	    if(orgImage.getDisuseYN() && !orgImagePre.getDisuseYN()) {
		orgImage.setDisuseDate(DateUtil.getCurrentDate("yyyy-MM-dd"));
	    }
	    orgImage.setImage(orgImagePre.getImage());
	    orgImage.setImageFileType(orgImagePre.getImageFileType());
	}

	orgImage.setImageType(Integer.parseInt(req.getParameter("imageType")));
	orgImage.setSizeWidth(Integer.parseInt(req.getParameter("sizeWidth")));
	orgImage.setSizeHeight(Integer.parseInt(req.getParameter("sizeHeight")));
	orgImage.setImageOrder(Integer.parseInt(req.getParameter("imageOrder")));

	final Map<String, MultipartFile> files = req.getFileMap();

	//파일정보 얻기 및 저장( Iterator나 List로 변환가능 )
	Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
	MultipartFile file;
	while (itr.hasNext()) {


	    Entry<String, MultipartFile> entry = itr.next();

	    file = entry.getValue(); //파일 얻기
	    String orginFileName = file.getOriginalFilename();
	    long size = file.getSize();
	    if(size > 0) {
		String imageType = orginFileName.substring(orginFileName.lastIndexOf(".") + 1);
		orgImage.setImageFileType(imageType);
		orgImage.setImage(file.getBytes());
	    }
	}

	orgService.updateOrgImage(orgImage);
	ModelAndView mav = new ModelAndView("EnvController.selectOrgImage");
	mav.addObject("orgImage", orgImage);
	mav.addObject("permission", permission);

	return mav;
    }

    /**
     * <pre> 
     *  조직 이미지 목록 화면
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/listOrgImage.do")
    public ModelAndView listOrgImage(HttpServletRequest req) throws Exception {

	HttpSession session = req.getSession();
	String compId = (String) session.getAttribute("COMP_ID");
	String orgId = (String) session.getAttribute("DEPT_ID");

	String roleCodes = "^" + (String) session.getAttribute("ROLE_CODES") + "^";
	String role_appadmin = "^" + AppConfig.getProperty("role_appadmin", "", "role") + "^"; // 시스템관리자
	String role_sealadmin = "^" + AppConfig.getProperty("role_sealadmin", "", "role") + "^"; // 기관날인관리자
	String role_signatoryadmin = "^" + AppConfig.getProperty("role_signatoryadmin", "", "role") + "^"; // 부서날인관리자

	String permission = "";

	if (roleCodes.indexOf(role_appadmin) != -1) {
	    permission = "A";
	} else if (roleCodes.indexOf(role_sealadmin) != -1) {
	    permission = "I";
	} else if (roleCodes.indexOf(role_signatoryadmin) != -1) {
	    permission = "D";
	}

	ModelAndView mav = new ModelAndView("EnvController.listOrgImage");
	
	OrganizationVO institution = orgService.selectHeadOrganizationByRoleCode(compId, orgId, AppConfig.getProperty("role_institution", "O002", "role"));
	mav.addObject("institution", institution);
	mav.addObject("permission", permission);

	return mav;
    }

    /**
     * <pre> 
     *  조직 이미지 목록
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/selectOrgImageList.do")
    public ModelAndView selectOrgImageList(HttpServletRequest req, HttpServletResponse res) throws Exception {
	String orgId = req.getParameter("orgId");
	String imageTypeStr = req.getParameter("imageType");
	int imageType = 0;
	if(imageTypeStr != null && !"".equals(imageTypeStr)) {
	    imageType = Integer.parseInt(imageTypeStr);
	}

	try {
	    List<OrgImage> orgImageList = orgService.selectOrgImageList(orgId, imageType, false);

	    mapper.writeValue(res.getOutputStream(), orgImageList);
	} catch (Exception e) {
	    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
	}

	return null;
    }
    
    /**
     * 
     * <pre> 
     *  Exception 이 발생했을 때 AJax 메시지를 리턴한다.
     * </pre>
     * @param e
     * @return
     * @see  
     *
     */
    private ModelAndView getAjaxExceptionMsg(Exception e) {
	ModelAndView mnv = new ModelAndView();
	logger.error("Exception[" + e.toString() + "]");

	mnv.addObject("_errorCode", "-9999");

	mnv.addObject("_errorMsg", "Unexpected Error. (" + e.getMessage() + ")");
	return mnv;
    }
    
        
    /*
    @RequestMapping("/app/env/optCompBaseUpdate.do")  
    public ModelAndView updateOption(@ModelAttribute("res") OptionVO optionVO) throws Exception {
	logger.debug("[EnvOptionController]updateOption() OPT001 : " + optionVO.getOptionId());
	
	///
	vo.setCompId((String)session.getAttribute("COMP_ID"));
	vo.setOptionId("OPT003");
	vo.setUseYn("N");
	envOptionService.updateOption(vo);	
	
	Enumeration<String> eAttr = req.getParameterNames();
	logger.debug("[EnvOptionController] updateOptionProcess() >>>>> COMP_ID : " + session.getAttribute("COMP_ID"));
	
	while (eAttr.hasMoreElements()) {
	    String aName = (String)eAttr.nextElement();
	    String aValue = req.getParameter(aName);
	    logger.debug(">>>>> " + aName + " : " + aValue);
	}
	///

	envOptionService.selectOption(optionVO);

	ModelAndView mav = new ModelAndView("EnvController.optCompBaseView");	    
	return mav;
    }   
    */ 
    
    /**
     * <pre> 
     *  옵션화된 문서번호 체계 JSON(jd.park, 20120504)
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/getEnvDocNumber.do")
    public ModelAndView getEnvDocNumber(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	HttpSession session = req.getSession();
    	
    	String classNumber 	= CommonUtil.nullTrim(req.getParameter("classNumber"));    	
		String compId 		= (String)session.getAttribute("COMP_ID");
		String deptId 		= (String)session.getAttribute("DEPT_ID");
		
		String deptCategory = envDocNumRuleService.getDocNum(deptId, compId, classNumber);
		
		ModelAndView mav = new ModelAndView("EnvController.envDocNumber");
		mav.addObject("result", deptCategory);
		return mav;    	
    }

    /**
     * <pre> 
     *  문서공유부서관리 목록
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/shareDeptList.do")
    public ModelAndView shareDeptList(HttpServletRequest req) throws Exception {
    	
    	HttpSession session = req.getSession();	
    	String compId = (String)session.getAttribute("COMP_ID");
    	String deptId = (String)session.getAttribute("DEPT_ID");
    	
    	ShareDocDeptVO shareDocDeptVO = new ShareDocDeptVO();
    	
    	shareDocDeptVO.setCompId(compId);

    	List<ShareDocDeptVO> shareDeptList = envOptionAPIService.selectShareDeptList(shareDocDeptVO);
	
    	ModelAndView mav = new ModelAndView("EnvController.ListShareDept");

    	mav.addObject("shareDeptList", shareDeptList);
    	mav.addObject("deptId", deptId);

    	return mav;
    } 
    
    /** 
     *      
     * <pre> 
     *  문서공유부서 추가
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/insertShareDept.do") 
    public void insertShareDept(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
		HttpSession session = req.getSession();	
		
		String compId			= (String)session.getAttribute("COMP_ID");
		String userId			= (String)session.getAttribute("USER_ID");
		String userName			= (String)session.getAttribute("USER_NAME");
		String targetDeptId		= (String)req.getParameter("targetDeptId");
		String targetDeptName	= (String)req.getParameter("targetDeptName");
		String shareDeptId		= (String)req.getParameter("shareDeptId");
		String shareDeptName	= (String)req.getParameter("shareDeptName");
		String bResult			= "success";
	
		try {
			
		    if (compId == null) {
		    	throw new NullPointerException("compId");
		    } else if(targetDeptId == null) {
		    	throw new NullPointerException("targetDeptId");
		    } else if(targetDeptName == null) {
		    	throw new NullPointerException("targetDeptName");
		    } else if(shareDeptId == null) {
		    	throw new NullPointerException("shareDeptId");
		    } else if(shareDeptName == null) {
		    	throw new NullPointerException("shareDeptName");
		    }
		    
		    ShareDocDeptVO shareDocDeptVO = new ShareDocDeptVO();
		    
		    shareDocDeptVO.setCompId(compId);
		    shareDocDeptVO.setTargetDeptId(targetDeptId);
		    shareDocDeptVO.setTargetDeptName(targetDeptName);
		    shareDocDeptVO.setShareDeptId(shareDeptId);
		    shareDocDeptVO.setShareDeptName(shareDeptName);
		    shareDocDeptVO.setRegisterId(userId);
		    shareDocDeptVO.setRegisterName(userName);
		    shareDocDeptVO.setRegistDate(DateUtil.getCurrentDate());
		
		    //문서공유부서 INSERT
		    int result = envOptionAPIService.insertShareDept(shareDocDeptVO);
	
		    if(result != 1) {
		    	bResult = "false";
		    }
		    
		    mapper.writeValue(res.getOutputStream(), bResult);
		    
		} catch (Exception e) {
		    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
		}
    }
    
    /**
     * <pre> 
     *  문서공유부서 목록 AJAX
     * </pre>
     * 
     * @param req
     * @return
     * @throws Exception
     * @see
     */
    @RequestMapping("/app/env/ajaxSelectShareDept.do")
    public void ajaxSelectShareDept(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	
    	HttpSession session 	= req.getSession();	
    	String compId 			= (String)session.getAttribute("COMP_ID");
		String targetDeptId		= (String)req.getParameter("targetDeptId");
		String shareDeptId		= (String)req.getParameter("shareDeptId");
    	
    	ShareDocDeptVO shareDocDeptVO = new ShareDocDeptVO();
    	shareDocDeptVO.setCompId(compId);
    	
    	if(targetDeptId != null && !"".equals(targetDeptId)) {
    		shareDocDeptVO.setTargetDeptId(targetDeptId);
    	}
    	
    	if(shareDeptId != null && !"".equals(shareDeptId)) {
    		shareDocDeptVO.setShareDeptId(shareDeptId);
    	}

    	List<ShareDocDeptVO> shareDeptList = envOptionAPIService.selectShareDeptList(shareDocDeptVO);
	
    	mapper.writeValue(res.getOutputStream(), shareDeptList);
    } 
    
    /** 
     *      
     * <pre> 
     *  문서공유부서 삭제
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/deleteShareDept.do") 
    public void deleteShareDept(HttpServletRequest req, HttpServletResponse res) throws Exception {
	
		HttpSession session = req.getSession();	
		
		String compId			= (String)session.getAttribute("COMP_ID");
		String targetDeptId		= (String)req.getParameter("targetDeptId");
		String shareDeptId		= (String)req.getParameter("shareDeptId");
		String bResult			= "success";
	
		try {
			
		    if (compId == null) {
		    	throw new NullPointerException("COMPID IS NULL");
		    } else if(targetDeptId == null) {
		    	throw new NullPointerException("TARGETID IS NULL");
		    } 
		    
		    ShareDocDeptVO shareDocDeptVO = new ShareDocDeptVO();
		    shareDocDeptVO.setCompId(compId);
		    
	    	if(targetDeptId != null && !"".equals(targetDeptId)) {
	    		shareDocDeptVO.setTargetDeptId(targetDeptId);
	    	}
	    	
	    	if(shareDeptId != null && !"".equals(shareDeptId)) {
	    		shareDocDeptVO.setShareDeptId(shareDeptId);
	    	}
		
		    //문서공유부서 DELETE
		    int result = envOptionAPIService.deleteShareDept(shareDocDeptVO);
	
		    if(result != 1) {
		    	bResult = "false";
		    }
		    
		    mapper.writeValue(res.getOutputStream(), bResult);
	
		} catch (Exception e) {
		    mapper.writeValue(res.getOutputStream(), getAjaxExceptionMsg(e).getModelMap());
		}
    }
    
    /** 
     *      
     * <pre> 
     *  우편번호 일괄 등록. 우정사업본부에서 다운받은 우편번호 zip파일을 그대로 등록한다.
     * </pre>
     * @param req
     * @return
     * @throws Exception
     * @see  
     *
     */
    @RequestMapping("/app/env/admin/insertZipcode.do") 
    public void insertZipcode(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			InputStream is = null; 
			BufferedReader in = null;
			try {
				File directory = new File("C:\\media");
				String filename[] = directory.list();
				
				for (int i = 0; i < filename.length; i++) {
					List<Map<String,String>> zipcodeList = new ArrayList<Map<String,String>>();
					
					//    System.out.print(listFilenames);
					in = new BufferedReader(
							new InputStreamReader(new FileInputStream("C:\\media\\"+filename[i]), "euc-kr"));

					String str;
					String [] strs;
					
					int line = 0;
					while ((str = in.readLine()) != null) {
						Map<String,String> strMap = new HashMap<String, String>();
						line++;
						
						if(line==1){
							continue;
						}
						strs = str.split("\\|");
						strMap.put("ZIPCODE",strs[0]);
						
						String sigungu = strs[3];
						String doromyung = strs[8] +" " + strs[11];
						String detail = strs[15];
						
						// 구가 없는 행정구역 체크
						if(sigungu.indexOf(" ") > -1){
							strMap.put("SIDO",strs[1]+" "+sigungu.split(" ")[0]);
							strMap.put("GUGUN",sigungu.split(" ")[1]);	
						}else{
							strMap.put("SIDO",strs[1]+" "+sigungu);
							strMap.put("GUGUN","");	
						}
						// 동, 읍, 면 
						String dong,eupMyun, li = new String();
						eupMyun = strs[5].equals("")?"":strs[5]+" ";
						dong = strs[17].equals("")?"":strs[17]+" ";
						li = strs[18].equals("")?"":strs[18]+" ";
						//myun = strs[19].equals("")?"":strs[19]+" ";
						
						strMap.put("DONG",dong+eupMyun+li + (doromyung.trim().equals("")?"":"("+doromyung+")") + (detail.equals("")?"":" "+detail) );
						strMap.put("BUNJI",strs[21]);
						strMap.put("SEQ",strs[23]);
						
						zipcodeList.add(strMap);
					}
					
					orgService.insertZipcode(zipcodeList);
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    } finally{
		    	in.close();
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
