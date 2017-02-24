package com.sds.acube.app.list.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.service.IEnvUserService;
import com.sds.acube.app.env.vo.AuditDeptVO;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.list.service.IListBoxService;
import com.sds.acube.app.list.service.IListCompleteService;
/**
 * Class Name  : ListBoxService.java <br> Description : 왼쪽 메뉴 함 리스트 서비스  <br> Modification Information <br> <br> 수 정  일 : <br> 수 정  자 : <br> 수정내용 : <br>
 * @author   김경훈 
 * @since  2011. 3. 22.
 * @version  1.0 
 * @see  com.sds.acube.app.list.service.impl.ListBoxService.java
 */
@Service("listBoxService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class ListBoxService extends BaseService implements IListBoxService{       
    
    private static final long serialVersionUID = 1L;
    
    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService; 
    
    /**
	 */
    @Inject
    @Named("envUserService")
    private IEnvUserService envUserService;
    
    
    
    /**
	 */
    @Inject
    @Named("orgService")
    private IOrgService orgService;
    
    /**
	 */
    @Inject
    @Named("listCompleteService")
    private IListCompleteService listCompleteService; 
      
    @SuppressWarnings("unchecked")
    public Map<String, String[]> list(String compId, String optBoxGroupId, String optRegistGroupId, String roleCodes, String deptId, String userId, String langType) throws Exception {
	List<OptionVO> result		= null;
	List<OptionVO> resultReg	= null;
	Map<String,String[]> VoMap	= new HashMap<String,String[]>();
	
	int voSize	= 0;
	int voRegSize	= 0;
	boolean isExamDept = false;
	boolean isProgressDept = false;
	boolean isDocDept = false;
	boolean isExamAuditDept = false;
	boolean isAuditDept = false;
	String isExamDeptYn = "N";
	String isProgressDeptYn = "N";
	String isDocDeptYn = "N";
	String isExamAuditDeptYn = "N";
	String isOfficerYn = "N";
	String isAuditDeptYn = "N";
	String isOfficerDeptAddYn = "N";
	String isAuditDeptAddYn = "N";
	String isAssistantChargerYn = "N";
	
	
	String roleId11Yn	= "N";
	String roleId12Yn	= "N";
	String roleId15Yn	= "N";
	String roleId16Yn	= "N";
	String roleId22Yn	= "N";
	String roleId23Yn	= "N";
	String roleId24Yn	= "N";
	String roleId25Yn 	= "N";
	String roleId30Yn	= "N";
	String roleId31Yn	= "N";
	String roleId32Yn	= "N";	
	
	String roleId11 = AppConfig.getProperty("role_doccharger", "", "role"); // 처리과 문서담당자
	String roleId12 = AppConfig.getProperty("role_cordoccharger", "", "role"); // 문서과 문서담당자
	String roleId15 = AppConfig.getProperty("role_sealadmin", "", "role"); // 기관날인관리자
	String roleId16 = AppConfig.getProperty("role_signatoryadmin", "", "role"); // 부서날인관리자
//	String roleId22 = AppConfig.getProperty("role_creditassessor", "", "role"); // 여신심사자코드
	String roleId23 = AppConfig.getProperty("role_ceopostassignadmin", "", "role"); // 담당부서지정자코드(임원)
	String roleId24 = AppConfig.getProperty("role_auditpostassignadmin", "", "role"); // 담당부서지정자코드(감사)
	String roleId25 = AppConfig.getProperty("role_dailyinpectreader", "", "role"); // 일상감사일지 조회자
	String roleId30 = AppConfig.getProperty("role_officecoordinationreader", "", "role"); // 부서협조열람자
	String roleId31 = AppConfig.getProperty("role_ceo", "", "role"); // CEO
	String roleId32 = AppConfig.getProperty("role_officer", "", "role"); // 임원
	
	
	
	String opt347 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT347", "OPT347", "OPT")); //감사열람함 열람자 지정
	
	AuditDeptVO auditVO = new AuditDeptVO();

	auditVO.setCompId(compId);
	auditVO.setAuditorId(userId);
	auditVO.setAuditorType("A");

	int totalAuditDept = listCompleteService.getAuditDeptCnt(auditVO);

	if(totalAuditDept > 0) {
	    isExamAuditDeptYn = "Y";	    
	}
	
	if("N".equals(isExamAuditDeptYn) && "Y".equals(opt347)) {
	    isExamAuditDept 	= orgService.selectIsOrgRole(deptId, AppConfig.getProperty("role_auditdept", "O006", "role"));
	    if(isExamAuditDept){
		isExamAuditDeptYn = "Y";
	    }
	}
	
	isExamDept 	= orgService.selectIsOrgRole(deptId, AppConfig.getProperty("role_auditdept", "O006", "role"));
	if(isExamDept){
	    isExamDeptYn = "Y";
	}
	
	isDocDept = orgService.selectIsOrgRole(deptId, AppConfig.getProperty("role_odcd", "O005", "role"));
	if(isDocDept){
	    isDocDeptYn = "Y";
	}
	isProgressDept = orgService.selectIsOrgRole(deptId, AppConfig.getProperty("role_process", "O004", "role"));
	if(isProgressDept){
	    isProgressDeptYn = "Y";
	}
	
	String optAppDisplay = appCode.getProperty("OPT334", "OPT334", "OPT");
	String optEnfDisplay = appCode.getProperty("OPT335", "OPT335", "OPT");
	
	if(roleCodes.indexOf(roleId11) != -1){
	    roleId11Yn = "Y"; 
	}
	if(roleCodes.indexOf(roleId12) != -1){
	    roleId12Yn = "Y"; 
	}
	if(roleCodes.indexOf(roleId15) != -1){
	    roleId15Yn = "Y"; 
	}
	if(roleCodes.indexOf(roleId16) != -1){
	    roleId16Yn = "Y"; 
	}
	
	/*
	// 본인을 대결자로 지정된 사람 중 날인관리자인 사람을 찾는다.
	List<UserVO> userInfo = orgService.selectMandators(userId);

	int infoSize = 0;
	infoSize = userInfo.size();

	if(infoSize > 0) {

	    for(int j=0; j < infoSize; j++) {
		String infoUserRoles = userInfo.get(j).getRoleCodes();

		if(infoUserRoles.indexOf(roleId16) != -1 && infoUserRoles.indexOf(roleId15) == -1){

		    roleId16Yn = "Y";

		}else if(infoUserRoles.indexOf(roleId15) != -1 ){

		    roleId15Yn = "Y";
		    
		}
	    }
	}
	//날인 대결 정보 끝	 
	*/

// 여신심사자 주석 처리
//	if(roleCodes.indexOf(roleId22) != -1){
//	    roleId22Yn = "Y"; 
//	}

	if(roleCodes.indexOf(roleId32) != -1){
	    roleId32Yn = "Y"; 
	}
	
	AuditDeptVO officerVO = new AuditDeptVO();

	officerVO.setCompId(compId);
	officerVO.setAuditorId(userId);
	officerVO.setAuditorType("O");

	List<AuditDeptVO> resultOffierDepts = listCompleteService.getListAuditDept(officerVO);

	int totalOffierDept = resultOffierDepts.size();

	if("Y".equals(roleId32Yn) && totalOffierDept > 0) {
	    isOfficerYn = "Y";
	}
	
	String receiveAuthYn = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT341", "OPT341", "OPT"));
	
	//감사담당부서 지정권한 체크(감사과 + 2013)
	isAuditDept 	= orgService.selectIsOrgRole(deptId, AppConfig.getProperty("role_auditdept", "O006", "role"));
	if(roleCodes.indexOf(roleId23) != -1){
	    roleId23Yn = "Y"; 
	}
	if(roleCodes.indexOf(roleId24) != -1){
	    roleId24Yn = "Y"; 
	}
	if("Y".equals(roleId23Yn)) {
	    isOfficerDeptAddYn = "Y";
	}
	if("Y".equals(roleId24Yn)) {
	    isAuditDeptAddYn = "Y";
	}
	
	if(isAuditDept){
	    isAuditDeptYn = "Y";
	}
	
	if(roleCodes.indexOf(roleId31) != -1){
	    roleId31Yn = "Y"; 
	}
	
	// 양식 등록 사용 여부
	Map opt354Map = envOptionAPIService.selectOptionTextMap(compId, appCode.getProperty("OPT354", "OPT354", "OPT"));
	String envFormRegistYn = "N";	
	
	if(roleCodes.indexOf(roleId11) != -1 && "Y".equals(opt354Map.get("I2"))){
	    envFormRegistYn = "Y";
	}else if(roleCodes.indexOf(roleId12) != -1 && "Y".equals(opt354Map.get("I1"))){
	    envFormRegistYn = "Y";
	}
	// 양식 등록 사용 여부 끝
	
	if(roleCodes.indexOf(roleId25) != -1){
	    roleId25Yn = "Y"; 
	}
	
	String opt367 = appCode.getProperty("OPT367", "OPT367", "OPT"); // 서명인 날인 승인 사용 여부
	opt367 = envOptionAPIService.selectOptionValue(compId,opt367);
	
	if(roleCodes.indexOf(roleId30) != -1){
	    roleId30Yn = "Y"; 
	}
	
	// 협조 담당자인지 체크
	boolean chkAssistanCharger = envUserService.isAuditor(compId, userId, "C");
	if(chkAssistanCharger){
	    isAssistantChargerYn = "Y";
	}
	
	try {	 
	    // 다국어 추가
	    result	  =  envOptionAPIService.selectOptionGroupListResource(compId, optBoxGroupId, langType);
	    resultReg =  envOptionAPIService.selectOptionGroupListResource(compId, optRegistGroupId, langType);
	    // result	 =  envOptionAPIService.selectOptionGroupList(compId, optBoxGroupId);
	    // resultReg =  envOptionAPIService.selectOptionGroupList(compId, optRegistGroupId);
		
	    String optAppDisplayYn = envOptionAPIService.selectOptionValue(compId, optAppDisplay);
	    String optEnfDisplayYn = envOptionAPIService.selectOptionValue(compId, optEnfDisplay);
	    
	    voSize	= result.size();
	    voRegSize	= resultReg.size();    
	    
	    String[] putResult		= new String[3];
	    String[] putResultReg	= new String[3];
	    String[] putResultRole	= new String[1];
	    
	    OptionVO optVO	= null; 
	    OptionVO optRegVO	= null; 
	    
	    putResultRole = new String[1];
	    putResultRole[0] = roleId11Yn;
	    VoMap.put(roleId11,putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = roleId12Yn;
	    VoMap.put(roleId12,putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = roleId15Yn;
	    VoMap.put(roleId15,putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = roleId16Yn;
	    VoMap.put(roleId16,putResultRole);

// 여신심사자 주석 처리	    
//	    putResultRole = new String[1];
//	    putResultRole[0] = roleId22Yn;
//	    VoMap.put(roleId22,putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = roleId25Yn;
	    VoMap.put(roleId25,putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = roleId32Yn;
	    VoMap.put(roleId32,putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = roleId31Yn;
	    VoMap.put(roleId31,putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = roleId30Yn;
	    VoMap.put(roleId30,putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = optAppDisplayYn;
	    VoMap.put(optAppDisplay,putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = optEnfDisplayYn;
	    VoMap.put(optEnfDisplay,putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = isExamDeptYn;
	    VoMap.put("isExamDept",putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = isDocDeptYn;
	    VoMap.put("isDocDept",putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = isProgressDeptYn;
	    VoMap.put("isProgressDept",putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = receiveAuthYn;
	    VoMap.put("receiveAuthYn",putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = isExamAuditDeptYn;
	    VoMap.put("isExamAuditDeptYn",putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = isOfficerYn;
	    VoMap.put("isOfficerYn",putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = isAuditDeptYn;
	    VoMap.put("isAuditDeptYn",putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = isOfficerDeptAddYn;
	    VoMap.put("isOfficerDeptAddYn",putResultRole);

	    putResultRole = new String[1];
	    putResultRole[0] = isAuditDeptAddYn;
	    VoMap.put("isAuditDeptAddYn",putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = envFormRegistYn;
	    VoMap.put("envFormRegistYn",putResultRole);	    
	    
	    putResultRole = new String[1];
	    putResultRole[0] = opt367;
	    VoMap.put("opt367",putResultRole);
	    
	    putResultRole = new String[1];
	    putResultRole[0] = isAssistantChargerYn;
	    VoMap.put("isAssistantChargerYn",putResultRole);
	    
	    
	    
	    
	    if(voSize > 0){
		for(int i=0; i < voSize; i++){	
		    
		    optVO = new OptionVO();
		    optVO = result.get(i);
		    
		    putResult		= new String[3];
		    
		    putResult[0] = optVO.getOptionId();
		    putResult[1] = optVO.getUseYn();
		    putResult[2] = optVO.getOptionValue();
		    
		    
		    VoMap.put(putResult[0], putResult);
		}
	    }
	    
	    if(voRegSize > 0){
		for(int k=0; k < voRegSize; k++){	
		    
		    optRegVO = new OptionVO();
		    optRegVO = resultReg.get(k);
		    
		    putResultReg	= new String[3];
		    
		    putResultReg[0] = optRegVO.getOptionId();
		    putResultReg[1] = optRegVO.getUseYn();
		    putResultReg[2] = optRegVO.getOptionValue();		    
		    
		    VoMap.put(putResultReg[0], putResultReg);
		}
	    }
	    
	   
	    
	    
	} catch (Exception e) {
	    logger.error(e.getMessage());
	}
	return  VoMap;
    }

}
