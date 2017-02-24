package com.sds.acube.app.bind;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.MessageSource;

import com.sds.acube.app.bind.vo.BindUnitVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.service.impl.OrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.BindUtil;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.vo.PeriodVO;


/**
 * Class Name : BindBaseController.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 4. 8. <br> 수 정 자 : I-ON <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 4. 8.
 * @version  1.0
 * @see  com.sds.acube.app.bind.BindBaseController.java
 */

public class BindBaseController extends BaseController implements BindConstants {

    private static final long serialVersionUID = -2949302139834216940L;

    @Inject
    @Named("messageSource")
    protected MessageSource m;

    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    protected IEnvOptionAPIService envOptionAPIService;

    private static Map<String, String> searchOptions = new LinkedHashMap<String, String>();
    private static Map<String, String> documentOptions = new LinkedHashMap<String, String>();

    static {
	searchOptions.put(BIND_NAME, "bind.obj.name");
	searchOptions.put(UNIT_NAME, "bind.obj.unit.name");
	searchOptions.put(DOC_TYPE, "bind.obj.type");
	searchOptions.put(EXPIRE_YEAR, "bind.obj.expire.year");

	documentOptions.put("title", "bind.obj.doc.title");
	documentOptions.put("docNumber", "bind.obj.doc.num");
	documentOptions.put("sendName", "bind.obj.send.recv");
    }


    protected String getCompId(HttpServletRequest request) {
	return request.getSession().getAttribute(COMP_ID_CODE).toString();
    }


    protected String getCompName(HttpServletRequest request) {
	return request.getSession().getAttribute(COMP_NAME_CODE).toString();
    }


    protected String getUserId(HttpServletRequest request) {
	return request.getSession().getAttribute(USER_ID_CODE).toString();
    }


    protected String getUserName(HttpServletRequest request) {
	return request.getSession().getAttribute(USER_NAME_CODE).toString();
    }


    protected String getLoginId(HttpServletRequest request) {
	return request.getSession().getAttribute(LOGIN_ID_CODE).toString();
    }


    protected String getDeptId(HttpServletRequest request) {
	return request.getSession().getAttribute(DEPT_ID_CODE).toString();
    }

    protected String getEmployeeId(HttpServletRequest request) {
    	return request.getSession().getAttribute(EMPLOYEE_ID).toString();
    }

    public String getDeptName(HttpServletRequest request) {
	return request.getSession().getAttribute(DEPT_NAME_CODE).toString();
    }


    // 대리처리과 때문에 추가 2011.08.02 jHuh
    public String setProxyDeptId(HttpServletRequest request) {
	return CommonUtil.nullTrim((String) request.getSession().getAttribute(PROXY_DOC_HANDLE_DEPT_CODE)).toString();
    }


    public String getRoleCode(HttpServletRequest request) {
	return request.getSession().getAttribute("ROLE_CODES").toString();
    }


    public String getPos(HttpServletRequest request) {
	return request.getSession().getAttribute("DISPLAY_POSITION").toString(); // 사용자직위
    }


    public String getIPAddress(HttpServletRequest request) {
	String address = CommonUtil.nullTrim(request.getHeader("WL-Proxy-Client-IP"));
	if (address.length() == 0) {
	    address = CommonUtil.nullTrim(request.getHeader("Proxy-Client-IP"));
	}
	if (address.length() == 0) {
	    address = CommonUtil.nullTrim(request.getRemoteAddr());
	}
	return address;
    }


    protected String getCurrentTime() {
	SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
	return format.format(Calendar.getInstance().getTime());
    }


    protected int getCurrentYear() {
	return Calendar.getInstance().get(Calendar.YEAR);
    }


    protected String getMessage(HttpServletRequest request, String key) {
	return m.getMessage(key, null, this.getLocale(request));
    }


    protected String getMessage(String key, Locale locale) {
	return m.getMessage(key, null, locale);
    }


    protected Locale getLocale(HttpServletRequest request) {
	Locale locale = (Locale) request.getSession().getAttribute(LANG_TYPE_CODE);
	if (locale == null)
	    locale = new Locale(KO);
	return locale;
    }


    public Map<String, String> getRetentionPeriod(Locale locale) {
	Map<String, String> result = new LinkedHashMap<String, String>();

	Map<String, String> props = appCode.getProperties("DRY");
	Iterator<String> it = props.keySet().iterator();
	while (it.hasNext()) {
	    String key = it.next();
	    String msg = key;
	    try {
		msg = getMessage("bind.code." + key, locale);
	    } catch (Exception e) {
	    }

	    result.put(key, msg);
	}

	return result;
    }


    public String getLastRetentionPeriod() {
	return RETENTION_PERIODS.DRY008.name();
    }


    public Map<String, String> getDocumentType(Locale locale) {
	Map<String, String> result = new LinkedHashMap<String, String>();

	DOCUMENT_TYPES[] values = DOCUMENT_TYPES.values();
	for (DOCUMENT_TYPES value : values) {
	    result.put(value.name(), getMessage("bind.code." + value, locale));
	}

	return result;
    }


    public Map<String, String> getSearchOptions(Locale locale) {
	Map<String, String> result = new LinkedHashMap<String, String>();
	Iterator<String> it = searchOptions.keySet().iterator();
	while (it.hasNext()) {
	    String key = it.next();
	    String value = searchOptions.get(key);

	    result.put(key, m.getMessage(value, null, locale));
	}
	return result;
    }


    public Map<String, String> getDocumentOptions(Locale locale) {
	Map<String, String> result = new LinkedHashMap<String, String>();
	Iterator<String> it = documentOptions.keySet().iterator();
	while (it.hasNext()) {
	    String key = it.next();
	    String value = documentOptions.get(key);

	    result.put(key, m.getMessage(value, null, locale));
	}
	return result;
    }


    public List<String> getSearchList(String compId) {
	String unitDate = AppConfig.getProperty("unitdate", "2002-01-01 00:00:00", "etc");
	int startYear = Integer.parseInt(unitDate.substring(0, 4));
	int currentYear = Calendar.getInstance().get(Calendar.YEAR);

	List<String> list = new ArrayList<String>();

	for (int i = currentYear; i >= startYear; i--) {
	    list.add(String.valueOf(i));
	}

	return list;
    }


    public String getSearchOption(String compId) throws Exception {
    String periodType = "";
	PeriodVO vo = envOptionAPIService.getCurrentPeriod(compId);
	
	if(vo != null) {
		periodType = vo.getPeriodType(); 
	}
	
	return periodType; // Y:연도, P:회기
    }


    public String[] getCompList() {
	String[] company = AppConfig.getArray("compid", null, "companyinfo");
	if (company != null) {
	    Arrays.sort(company);
	}
	
	return company;
    }


    public Map<String, String> getSearchYear(String compId, String lastYear, Locale locale) throws Exception {
	return getSearchYear(compId, Integer.parseInt(lastYear), locale);
    }


    public Map<String, String> getSearchYear(String compId, int lastYear, Locale locale) throws Exception {
	Map<String, String> options = new LinkedHashMap<String, String>();
	String code = YEAR_SESSION.equals(this.getSearchOption(compId)) ? "bind.obj.year.option" : "bind.obj.period.option";
	String currentYear = envOptionAPIService.getCurrentPeriodStr(compId);
	int current = 2015;
	if(currentYear.isEmpty()==false)
		current = Integer.parseInt(currentYear);
	if (lastYear > current) {
	    for (int i = lastYear; current < i; i--) {
		options.put(String.valueOf(i), m.getMessage(code, new String[] { String.valueOf(i) }, locale));
	    }
	}

	List<PeriodVO> vos = envOptionAPIService.listPeriod(compId);
	for (PeriodVO vo : vos) {
	    String id = vo.getPeriodId();
	    options.put(id, m.getMessage(code, new String[] { id }, locale));
	}

	return options;
    }


    public Map<String, String> getSearchYear(String compId, Locale locale) throws Exception {
	return getSearchYear(compId, envOptionAPIService.getCurrentPeriodStr(compId), locale);
    }


    public String getStartDate(String periodId, String compId) throws Exception {
	PeriodVO vo = envOptionAPIService.getPeriod(compId, periodId);
	if ("P".equals(vo.getPeriodType())) {
	    String startDate = StringUtils.replace(vo.getStartDate(), "-", "");
	    return startDate.substring(0, 8) + "000000";
	} else {
	    String unitDate = AppConfig.getProperty("unitdate", "2002-01-01 00:00:00", "etc");
	    return BindUtil.getStartDate(unitDate, periodId, compId);
	}
    }


    public String getEndDate(String periodId, String compId) throws Exception {
	PeriodVO vo = envOptionAPIService.getPeriod(compId, periodId);
	if ("P".equals(vo.getPeriodType())) {
	    String endDate = StringUtils.replace(vo.getEndDate(), "-", "");
	    return endDate.substring(0, 8) + "235959";
	} else {
	    String unitDate = AppConfig.getProperty("unitdate", "2002-01-01 00:00:00", "etc");
	    return BindUtil.getEndDate(unitDate, periodId, compId);
	}
    }


    protected List<JSONObject> jsonTree(List<BindUnitVO> list, boolean useBindCount) throws JSONException, ParseException {
	List<JSONObject> result = new ArrayList<JSONObject>();

	String simpleDateFormat = AppConfig.getProperty("date_format", "yyyy/MM/dd", "date");
	String dateFormat = AppConfig.getProperty("format", "yyyy-MM-dd HH:mm:ss", "date");

	for (int i = 0; i < list.size(); i++) {
	    BindUnitVO vo = list.get(i);

	    JSONObject row = new JSONObject();
	    row.put(ID, vo.getUnitId());

	    row.put(TEXT, vo.getUnitName() + (useBindCount ? (vo.getBindCount() == 0 ? "" : " (" + vo.getBindCount() + ")") : ""));
	    row.put(UNIT_ID, vo.getUnitId());
	    row.put(UNIT_NAME, vo.getUnitName());
	    row.put(ICON_CLS, FOLDER.equals(vo.getUnitType()) ? FOLDER : ICON_UNIT);
	    row.put(SERIAL_NUMBER, vo.getSerialNumber());
	    row.put(RETENTION_PERIOD, vo.getRetentionPeriod());
	    row.put(DESCRIPTION, vo.getDescription());
	    row.put(UNIT_TYPE, vo.getUnitType());
	    row.put(CALC, FastDateFormat.getInstance(simpleDateFormat).format(
		    new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.KOREA).parse(vo.getApplied())));
	    row.put(APPLIED, vo.getApplied().substring(0, 8));
	    row.put(CREATED, FastDateFormat.getInstance(dateFormat).format(
		    new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.KOREA).parse(vo.getCreated())));
	    row.put(MODIFIED, FastDateFormat.getInstance(dateFormat).format(
		    new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.KOREA).parse(vo.getModified())));
	    row.put(LEAF, !FOLDER.equals(vo.getUnitType()));
	    row.put(EXPANDED, vo.getChildCount() == 0 ? true : false);

	    result.add(row);
	}

	return result;
    }


    public String getDeptName(OrgService orgService, String deptId) throws Exception {
	OrganizationVO vo = orgService.selectOrganization(deptId);
	return vo.getOrgName();
    }


    public Map<String, String> getSearchMonth(Locale locale) {
	Map<String, String> options = new LinkedHashMap<String, String>();

	for (int i = 1; i <= 12; i++) {
	    options.put((i < 10 ? "0" : "") + i, m.getMessage("bind.obj.month", new Integer[] { i }, locale));
	}
	return options;
    }
}
