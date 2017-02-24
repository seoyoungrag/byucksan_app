/**
 * 
 */
package com.sds.acube.app.common.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.anyframe.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sds.acube.app.bind.BindConstants;
import com.sds.acube.app.bind.service.BindDocumentService;
import com.sds.acube.app.bind.service.BindService;
import com.sds.acube.app.bind.vo.BindDocVO;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.service.impl.OrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.BindUtil;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.EscapeUtil;
import com.sds.acube.app.common.util.ExcelUtil;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.memo.service.IMemoService;
import com.sds.acube.app.memo.vo.MemoVO;
import com.sds.acube.app.notice.service.INoticeService;
import com.sds.acube.app.notice.vo.NoticeVO;


/**
 * Class Name : CommonController.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 5. 25. <br> 수 정 자 : Timothy <br> 수정내용 : <br>
 * @author  Timothy
 * @since  2011. 5. 25.
 * @version  1.0
 * @see  com.sds.acube.app.common.controller.CommonController.java
 */
@SuppressWarnings("serial")
@Controller("commonController")
@RequestMapping("/app/common/excel/*.do")
public class CommonController extends BaseController implements BindConstants {

    @Inject
    @Named("messageSource")
    private MessageSource m;

    /**
	 */
    @Inject
    @Named("bindService")
    private BindService bindService;

    /**
	 */
    @Inject
    @Named("bindDocumentService")
    private BindDocumentService bindDocumentService;

    /**
	 */
    @Inject
    @Named("orgService")
    private OrgService orgService;
    
    /**
	 */
    @Inject
    @Named("noticeService")
    private INoticeService noticeService;
    
    /**
	 */
    @Inject
    @Named("memoService")
    private IMemoService memoService;
    


    @SuppressWarnings("unchecked")
    @RequestMapping("/app/common/excel/exportExcel.do")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

	HttpSession session = request.getSession();
	Locale langType = (Locale) session.getAttribute("LANG_TYPE");
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사
	// 아이디
	String lobCode = "LOB003";

	String titles = m.getMessage("common.excel.column." + lobCode.toLowerCase(), null, langType);
	String[] titleList = titles.split(",");
	String sheetName = m.getMessage("common.excel.title." + lobCode.toLowerCase(), null, langType);

	ArrayList dataList = new ArrayList();

	// Map으로 Excel 생성
	// for (int loop = 0; loop < 5; loop++) {
	// Map<String, String> map = new HashMap<String, String>();
	// for (int pos = 0; pos < 3; pos++) {
	// map.put("key" + loop + pos, "value" + loop + pos);
	// }
	// dataList.add(map);
	// }

	// ArrayList 로 Excel 생성
	for (int loop = 0; loop < 5; loop++) {
	    ArrayList list = new ArrayList();
	    for (int pos = 0; pos < 3; pos++) {
		list.add("key" + loop + pos);
	    }
	    dataList.add(list);
	}

	// VO로 Excel 생성
	// for (int loop = 0; loop < 5; loop++) {
	// AppLineVO appLineVO = new AppLineVO();
	// appLineVO.setDocId(GuidUtil.getGUID());
	// appLineVO.setCompId(compId);
	// dataList.add(appLineVO);
	// }

	ExcelUtil excelUtil = new ExcelUtil();
	excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, lobCode);
    }


    @SuppressWarnings("unchecked")
    @RequestMapping("/app/common/excel/bind.do")
    public void bind(HttpServletRequest request, HttpServletResponse response) throws Exception {
	HttpSession session = request.getSession();
	Locale langType = (Locale) session.getAttribute(LANG_TYPE_CODE);
	String compId = (String) session.getAttribute(COMP_ID_CODE); // 사용자 소속
	String roleCode = (String) session.getAttribute("ROLE_CODES");

	String searchYear = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_YEAR), String.valueOf(DateUtil.getCurrentYear()));
	String searchType = request.getParameter(SEARCH_TYPE);
	String searchValue = request.getParameter(SEARCH_VALUE);
	if (StringUtils.isNotEmpty(searchValue) && searchValue.startsWith("%")) {
	    searchValue = URLDecoder.decode(searchValue, UTF8);
	}
	String docType = request.getParameter(DOC_TYPE);
	String deptId = request.getParameter(DEPT_ID);

	String adminRoleCode = AppConfig.getProperty("role_appadmin", "", "role");

	if (roleCode.indexOf(adminRoleCode) > -1) {
	    if (StringUtils.isNotEmpty(deptId) && deptId.length() <= 3) {
		deptId = null;
	    } else if (StringUtils.isEmpty(deptId)) {
		deptId = null;
	    }
	} else {
	    if (StringUtils.isEmpty(deptId)) {
		deptId = (String) session.getAttribute(DEPT_ID_CODE);
	    }
	}

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(CREATE_YEAR, searchYear);
	param.put(PAGE_NO, "1");
	param.put(LIST_NUM, "10000000");
	param.put(SCREEN_COUNT, "1");
	if (DOC_TYPE.equals(searchType)) {
	    param.put(DOC_TYPE, docType);
	} else {
	    param.put(searchType, searchValue);
	}

	List<BindVO> binds = bindService.getList(param);

	ArrayList dataList = new ArrayList();

	// 번호,단위업무명,편철명,건수,생산년도,문서형태,보존기간,비고
	for (int i = 0; i < binds.size(); i++) {
	    BindVO vo = binds.get(i);

	    String arrange = vo.getArrange();
	    String binding = vo.getBinding();
	    String remark = "";
	    if (BindConstants.Y.equals(binding)) {
		remark = m.getMessage("bind.obj.confirmation", null, langType);
	    } else if (BindConstants.Y.equals(arrange)) {
		remark = m.getMessage("bind.obj.ok", null, langType);
	    }

	    if (BindConstants.SEND_TYPES.DST002.name().equals(vo.getSendType())) {
		if (StringUtils.isNotEmpty(remark)) {
		    remark = m.getMessage("bind.code.DST002", null, langType) + " / " + remark;
		} else {
		    remark = m.getMessage("bind.code.DST002", null, langType);
		}
	    } else if (BindConstants.SEND_TYPES.DST003.name().equals(vo.getSendType())) {
		if (StringUtils.isNotEmpty(remark)) {
		    remark = m.getMessage("bind.code.DST003", null, langType) + " / " + remark;
		} else {
		    remark = m.getMessage("bind.code.DST003", null, langType);
		}
	    } else if (BindConstants.SEND_TYPES.DST004.name().equals(vo.getSendType())) {
		if (StringUtils.isNotEmpty(remark)) {
		    remark = m.getMessage("bind.code.DST004", null, langType) + " / " + remark;
		} else {
		    remark = m.getMessage("bind.code.DST004", null, langType);
		}
	    }

	    ArrayList list = new ArrayList();
	    list.add(String.valueOf(i + 1));
	    list.add(vo.getUnitName());
	    list.add(vo.getBindName());
	    list.add(String.valueOf(vo.getDocCount()));
	    list.add(m.getMessage("bind.code." + vo.getDocType(), null, langType));
	    list.add(m.getMessage("bind.code." + vo.getRetentionPeriod(), null, langType));
	    list.add(remark);

	    dataList.add(list);
	}

	String lobCode = "LOB101";
	String titles = m.getMessage("common.excel.column." + lobCode.toLowerCase(), null, langType);
	String[] titleList = titles.split(",");
	String sheetName = m.getMessage("common.excel.title." + lobCode.toLowerCase(), null, langType);

	ExcelUtil excelUtil = new ExcelUtil();
	excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, lobCode);
    }


    @SuppressWarnings("unchecked")
    @RequestMapping("/app/common/excel/bindDocument.do")
    public void bindDocument(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String bindId = request.getParameter(BIND_ID);
	String searchType = request.getParameter(SEARCH_TYPE);
	String searchValue = request.getParameter(SEARCH_VALUE);

	HttpSession session = request.getSession();
	Locale langType = (Locale) session.getAttribute(LANG_TYPE_CODE);
	String compId = (String) session.getAttribute(COMP_ID_CODE); // 사용자 소속
	String deptId = (String) session.getAttribute(DEPT_ID_CODE);

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(BIND_ID, bindId);
	param.put(PAGE_NO, String.valueOf(1));
	param.put(LIST_NUM, String.valueOf(10000000));
	param.put(SCREEN_COUNT, DEFAULT_COUNT);

	if (StringUtils.isNotEmpty(searchType) && StringUtils.isNotEmpty(searchValue)) {
	    if (DEFAULT_SEARCH_TYPE.equals(searchType)) {
		searchValue = EMPTY;
	    } else if (DOC_NUMBER.equals(searchType)) {
		String[] values = searchValue.split("-");
		if (values.length > 0) {
		    param.put(DEPT_CATEGORY, values[0]);
		}
		if (values.length > 1) {
		    param.put(SERIAL_NUMBER, values[1]);
		}
		if (values.length > 2) {
		    param.put(SUBSERIAL_NUMBER, values[2]);
		}
	    } else {
		param.put(searchType, searchValue);
	    }
	}

	List<BindDocVO> binds = bindDocumentService.getDocumentList(param);

	ArrayList dataList = new ArrayList();

	String DPI001 = appCode.getProperty("DPI001", "DPI001", "DPI");
	String DPI002 = appCode.getProperty("DPI002", "DPI002", "DPI");

	for (int i = 0; i < binds.size(); i++) {
	    BindDocVO vo = binds.get(i);
	    String electronDocYn = m.getMessage(BindConstants.Y.equals(vo.getElectronDocYn()) ? "bind.obj.doc.electronic"
		    : "bind.obj.doc.none.electronic", null, langType);

	    String displayName = "";
	    if (DPI001.equals(vo.getDocType())) {
		String[] receivers = vo.getReceivers().split(",");

		for (String receiver : receivers) {
		    displayName += ", " + receiver;
		}

		if (displayName.startsWith(", ")) {
		    displayName = displayName.substring(2);
		}

	    } else if (DPI002.equals(vo.getDocType())) {
		displayName = vo.getSenderName();
	    }

	    ArrayList list = new ArrayList();
	    list.add(String.valueOf(vo.getIdx()));
	    list.add(m.getMessage("bind.code." + vo.getDocType(), null, langType));
	    list.add(vo.getTitle());
	    list.add(vo.getDocNumber());
	    list.add(EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(vo.getApprovalDate())));
	    list.add(displayName);
	    list.add(electronDocYn);

	    dataList.add(list);
	}

	String lobCode = "LOB102";
	String titles = m.getMessage("common.excel.column." + lobCode.toLowerCase(), null, langType);
	String[] titleList = titles.split(",");
	String sheetName = m.getMessage("common.excel.title." + lobCode.toLowerCase(), null, langType);

	ExcelUtil excelUtil = new ExcelUtil();
	excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, lobCode);
    }


    @SuppressWarnings("unchecked")
    @RequestMapping("/app/common/excel/nonBindDocument.do")
    public void nonBindDocument(HttpServletRequest request, HttpServletResponse response) throws Exception {
	HttpSession session = request.getSession();
	Locale langType = (Locale) session.getAttribute(LANG_TYPE_CODE);
	String compId = (String) session.getAttribute(COMP_ID_CODE); // 사용자 소속
	String deptId = StringUtils.defaultIfEmpty(request.getParameter(DEPT_ID), (String) session.getAttribute(DEPT_ID_CODE));

	String searchYear = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_YEAR), DateUtil.getCurrentYear());
	String searchType = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_TYPE), EMPTY);
	String searchValue = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_VALUE), EMPTY);

	String unitDate = AppConfig.getProperty("unitdate", "", "etc");

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(PAGE_NO, String.valueOf(1));
	param.put(LIST_NUM, String.valueOf(1000000));
	param.put(SCREEN_COUNT, DEFAULT_COUNT);
	param.put("startdate", BindUtil.getStartDate(unitDate, searchYear, compId));
	param.put("enddate", BindUtil.getEndDate(unitDate, searchYear, compId));

	if (StringUtils.isNotEmpty(searchType) && StringUtils.isNotEmpty(searchValue)) {
	    if (DEFAULT_SEARCH_TYPE.equals(searchType)) {
		searchValue = EMPTY;
	    } else if (DOC_NUMBER.equals(searchType)) {
		String[] values = searchValue.split("-");
		if (values.length > 0) {
		    param.put(DEPT_CATEGORY, values[0]);
		}
		if (values.length > 1) {
		    param.put(SERIAL_NUMBER, values[1]);
		}
		if (values.length > 2) {
		    param.put(SUBSERIAL_NUMBER, values[2]);
		}
	    } else {
		param.put(searchType, searchValue);
	    }
	}

	List<BindDocVO> binds = bindDocumentService.getNonBindList(param);

	ArrayList dataList = new ArrayList();

	for (int i = 0; i < binds.size(); i++) {
	    BindDocVO vo = binds.get(i);
	    String electronDocYn = m.getMessage(BindConstants.Y.equals(vo.getElectronDocYn()) ? "bind.obj.doc.electronic"
		    : "bind.obj.doc.none.electronic", null, langType);

	    ArrayList list = new ArrayList();
	    list.add(String.valueOf(vo.getIdx()));
	    list.add(m.getMessage("bind.code." + vo.getDocType(), null, langType));
	    list.add(vo.getTitle());
	    list.add(vo.getDocNumber());
	    list.add(EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(vo.getApprovalDate())));
	    list.add(vo.getSenderName());
	    list.add(electronDocYn);

	    dataList.add(list);
	}

	String lobCode = "LOB102";
	String titles = m.getMessage("common.excel.column." + lobCode.toLowerCase(), null, langType);
	String[] titleList = titles.split(",");
	String sheetName = m.getMessage("common.excel.title." + lobCode.toLowerCase(), null, langType);

	ExcelUtil excelUtil = new ExcelUtil();
	excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, lobCode);
    }


    @SuppressWarnings("unchecked")
    @RequestMapping("/app/common/excel/transferDocument.do")
    public void transferDocument(HttpServletRequest request, HttpServletResponse response) throws Exception {
	String bindId = request.getParameter(ORG_BIND_ID);
	String searchType = request.getParameter(SEARCH_TYPE);
	String searchValue = request.getParameter(SEARCH_VALUE);

	HttpSession session = request.getSession();
	Locale langType = (Locale) session.getAttribute(LANG_TYPE_CODE);
	String compId = (String) session.getAttribute(COMP_ID_CODE); // 사용자 소속
	String deptId = StringUtils.defaultIfEmpty(request.getParameter(DEPT_ID), (String) session.getAttribute(DEPT_ID_CODE));

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(BIND_ID, bindId);
	param.put(PAGE_NO, String.valueOf(1));
	param.put(LIST_NUM, String.valueOf(1000000));
	param.put(SCREEN_COUNT, DEFAULT_COUNT);

	if (StringUtils.isNotEmpty(searchType) && StringUtils.isNotEmpty(searchValue)) {
	    if (DEFAULT_SEARCH_TYPE.equals(searchType)) {
		searchValue = EMPTY;
	    } else if (DOC_NUMBER.equals(searchType)) {
		String[] values = searchValue.split("-");
		if (values.length > 0) {
		    param.put(DEPT_CATEGORY, values[0]);
		}
		if (values.length > 1) {
		    param.put(SERIAL_NUMBER, values[1]);
		}
		if (values.length > 2) {
		    param.put(SUBSERIAL_NUMBER, values[2]);
		}
	    } else {
		param.put(searchType, searchValue);
	    }
	}

	List<BindDocVO> binds = bindDocumentService.getTransferList(param);

	ArrayList dataList = new ArrayList();

	String DPI001 = appCode.getProperty("DPI001", "DPI001", "DPI");
	String DPI002 = appCode.getProperty("DPI002", "DPI002", "DPI");

	for (int i = 0; i < binds.size(); i++) {
	    BindDocVO vo = binds.get(i);
	    String electronDocYn = m.getMessage(BindConstants.Y.equals(vo.getElectronDocYn()) ? "bind.obj.doc.electronic"
		    : "bind.obj.doc.none.electronic", null, langType);

	    String displayName = "";
	    if (DPI001.equals(vo.getDocType())) {
		String[] receivers = vo.getReceivers().split(",");

		for (String receiver : receivers) {
		    displayName += ", " + receiver;
		}

		if (displayName.startsWith(", ")) {
		    displayName = displayName.substring(2);
		}

	    } else if (DPI002.equals(vo.getDocType())) {
		displayName = vo.getSenderName();
	    }

	    ArrayList list = new ArrayList();
	    list.add(String.valueOf(i + 1));
	    list.add(m.getMessage("bind.code." + vo.getDocType(), null, langType));
	    list.add(vo.getTitle());
	    list.add(vo.getDocNumber());
	    list.add(EscapeUtil.escapeDate(DateUtil.getFormattedShortDate(vo.getApprovalDate())));
	    list.add(displayName);
	    list.add(electronDocYn);

	    dataList.add(list);
	}

	String lobCode = "LOB102";
	String titles = m.getMessage("common.excel.column." + lobCode.toLowerCase(), null, langType);
	String[] titleList = titles.split(",");
	String sheetName = m.getMessage("common.excel.title." + lobCode.toLowerCase(), null, langType);

	ExcelUtil excelUtil = new ExcelUtil();
	excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, lobCode);
    }


    @SuppressWarnings("unchecked")
    @RequestMapping("/app/common/excel/sharedBind.do")
    public void sharedBind(HttpServletRequest request, HttpServletResponse response) throws Exception {
	HttpSession session = request.getSession();
	Locale langType = (Locale) session.getAttribute(LANG_TYPE_CODE);
	String compId = (String) session.getAttribute(COMP_ID_CODE); // 사용자 소속

	String searchYear = StringUtils.defaultIfEmpty(request.getParameter(SEARCH_YEAR), String.valueOf(DateUtil.getCurrentYear()));
	String searchType = request.getParameter(SEARCH_TYPE);
	String searchValue = request.getParameter(SEARCH_VALUE);
	if (StringUtils.isNotEmpty(searchValue) && searchValue.startsWith("%")) {
	    searchValue = URLDecoder.decode(searchValue, UTF8);
	}
	String docType = request.getParameter(DOC_TYPE);
	String deptId = request.getParameter(DEPT_ID);

	Map<String, String> param = new HashMap<String, String>();
	param.put(COMP_ID, compId);
	param.put(DEPT_ID, deptId);
	param.put(CREATE_YEAR, searchYear);
	param.put(PAGE_NO, "1");
	param.put(LIST_NUM, "1000000");
	param.put(SCREEN_COUNT, "1");

	if (DOC_TYPE.equals(searchType)) {
	    param.put(DOC_TYPE, docType);
	} else {
	    param.put(searchType, searchValue);
	}

	List<BindVO> binds = bindService.getSharedList(param);

	ArrayList dataList = new ArrayList();

	// 번호,단위업무명,편철명,건수,생산년도,문서형태,보존기간,비고
	for (int i = 0; i < binds.size(); i++) {
	    BindVO vo = binds.get(i);

	    OrganizationVO org = orgService.selectOrganization(vo.getDeptId());
	    String deptName = org.getOrgName();
	    String arrange = vo.getArrange();
	    String binding = vo.getBinding();

	    String remark = "";
	    if (BindConstants.Y.equals(binding)) {
		remark = m.getMessage("bind.obj.confirmation", null, langType);
	    } else if (BindConstants.Y.equals(arrange)) {
		remark = m.getMessage("bind.obj.ok", null, langType);
	    }

	    if (BindConstants.SEND_TYPES.DST002.name().equals(vo.getSendType())) {
		if (StringUtils.isNotEmpty(remark)) {
		    remark = m.getMessage("bind.code." + vo.getSendType(), null, langType) + " / " + remark;
		} else {
		    remark = m.getMessage("bind.code." + vo.getSendType(), null, langType);
		}
	    } else if (BindConstants.SEND_TYPES.DST003.name().equals(vo.getSendType())) {
		if (StringUtils.isNotEmpty(remark)) {
		    remark = m.getMessage("bind.code." + vo.getSendType(), null, langType) + " / " + remark;
		} else {
		    remark = m.getMessage("bind.code." + vo.getSendType(), null, langType);
		}
	    }

	    ArrayList list = new ArrayList();
	    list.add(String.valueOf(i + 1));
	    list.add(deptName);
	    list.add(vo.getUnitName());
	    list.add(vo.getBindName());
	    list.add(String.valueOf(vo.getDocCount()));
	    list.add(m.getMessage("bind.code." + vo.getDocType(), null, langType));
	    list.add(m.getMessage("bind.code." + vo.getRetentionPeriod(), null, langType));
	    list.add(remark);

	    dataList.add(list);
	}

	String lobCode = "LOB103";
	String titles = m.getMessage("common.excel.column." + lobCode.toLowerCase(), null, langType);
	String[] titleList = titles.split(",");
	String sheetName = m.getMessage("common.excel.title." + lobCode.toLowerCase(), null, langType);

	ExcelUtil excelUtil = new ExcelUtil();
	excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, lobCode);
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/common/excel/notice.do")
    public void notice(HttpServletRequest request, HttpServletResponse response) throws Exception {
	HttpSession session = request.getSession();
	Locale langType = (Locale) session.getAttribute(LANG_TYPE_CODE);
	String compId = (String) session.getAttribute(COMP_ID_CODE); // 사용자 소속
	String roleCode = (String) session.getAttribute("ROLE_CODES");

	String searchValue = request.getParameter(SEARCH_VALUE);
	if (StringUtils.isNotEmpty(searchValue) && searchValue.startsWith("%")) {
	    searchValue = URLDecoder.decode(searchValue, UTF8);
	}

	NoticeVO noticeVO = new NoticeVO();
	noticeVO.setSearchValue(searchValue);
	Page noticePage = this.noticeService.getList(noticeVO, 1);

	List<NoticeVO> notices = (List<NoticeVO>)noticePage.getList();

	ArrayList dataList = new ArrayList();

	// 번호,단위업무명,편철명,건수,생산년도,문서형태,보존기간,비고
	for (int i = 0; i < notices.size(); i++) {
		NoticeVO vo = notices.get(i);

	    String classCode = vo.getClassCode();
	    
	    if(classCode.equals("03")){
			classCode = "일반 공지";
		}else if(classCode.equals("04")){
			classCode = "관리소 공지";
		}else if(classCode.equals("93")){
			classCode = "국외 출장 공지";
		}else if(classCode.equals("99")){
			classCode = "주요 보도";
		}

	    ArrayList list = new ArrayList();
	    list.add(String.valueOf(i + 1));
	    list.add(vo.getReportNo());
	    list.add(classCode);
	    list.add(vo.getSubjectTitle());
	    list.add(vo.getContentsEtc());
	    list.add(vo.getInputDate());
	    list.add("관리자");
	    dataList.add(list);
	}

	String lobCode = "LOB104";
	String titles = m.getMessage("common.excel.column." + lobCode.toLowerCase(), null, langType);
	String[] titleList = titles.split(",");
	String sheetName = m.getMessage("common.excel.title." + lobCode.toLowerCase(), null, langType);

	ExcelUtil excelUtil = new ExcelUtil();
	excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, lobCode);
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping("/app/common/excel/memo.do")
    public void memo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Locale langType = (Locale) session.getAttribute(LANG_TYPE_CODE);
		String compId = (String) session.getAttribute(COMP_ID_CODE); // 사용자 소속
		
		Map<String, String> param = new HashMap<String, String>();
		
		String searchType = request.getParameter(SEARCH_TYPE);
		param.put("searchType", searchType);
		
		String searchValue = request.getParameter(SEARCH_VALUE);
		if (StringUtils.isNotEmpty(searchValue)) {
		    searchValue = URLDecoder.decode(searchValue, UTF8);
		    param.put("searchValue", searchValue);
		}
		
		String userId = (String) session.getAttribute(USER_ID_CODE);
		String pageType = request.getParameter("pageType")==null?"receive":request.getParameter("pageType");
		
		if(pageType.equals("receive")){
			param.put("receiverId", userId);
		}else if(pageType.equals("send")){
			param.put("senderId", userId);
		}
		
		Page memoPage = this.memoService.list(param, 1, 1000000); 
		
		List<MemoVO> memos = (List<MemoVO>)memoPage.getList();
	
		ArrayList dataList = new ArrayList();
	
		// 번호,단위업무명,편철명,건수,생산년도,문서형태,보존기간,비고
		for (int i = 0; i < memos.size(); i++) {
			MemoVO vo = memos.get(i);
	
		    ArrayList list = new ArrayList();
		    list.add(String.valueOf(i + 1));
		    list.add(vo.getSenderId());
		    list.add(vo.getSenderName());
		    list.add(vo.getReceiverId());
		    list.add(vo.getReceiverName());
		    list.add(vo.getTitle());
		    list.add(vo.getCreateDate());
		    list.add(CommonUtil.nullTrim(vo.getReadDate()).equals("")?"읽지않음":"읽음");
		    list.add(String.valueOf(vo.getAttachCount()));
		    dataList.add(list);
		}
	
		String lobCode = "LOB105";
		String titles = m.getMessage("common.excel.column." + lobCode.toLowerCase(), null, langType);
		String[] titleList = titles.split(",");
		String sheetName = m.getMessage("common.excel.title." + lobCode.toLowerCase(), null, langType);
	
		ExcelUtil excelUtil = new ExcelUtil();
		excelUtil.exportListData(request, response, dataList, titleList, sheetName, compId, lobCode);
    }
}
