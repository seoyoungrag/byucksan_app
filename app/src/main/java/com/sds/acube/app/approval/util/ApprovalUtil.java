package com.sds.acube.app.approval.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.PubReaderVO;
import com.sds.acube.app.appcom.vo.SendInfoVO;
import com.sds.acube.app.approval.vo.AppLineHisVO;
import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.approval.vo.AppRecvVO;
import com.sds.acube.app.approval.vo.CustomerVO;
import com.sds.acube.app.approval.vo.RelatedDocVO;
import com.sds.acube.app.approval.vo.RelatedRuleVO;
import com.sds.acube.app.common.util.AppCode;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.MemoryUtil;
import com.sds.acube.app.common.util.Transform;
import com.sds.acube.app.enforce.vo.EnfLineVO;

/** 
 *  Class Name  : ApprovalUtil.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2011. 4. 5. <br>
 *  수 정  자 : Timothy  <br>
 *  수정내용 :  <br>
 * 
 *  @author  Timothy 
 *  @since 2011. 4. 5.
 *  @version 1.0 
 *  @see  com.sds.acube.app.approval.util.ApprovalUtil.java
 */

public class ApprovalUtil {
    
    private static Log logger = LogFactory.getLog(ApprovalUtil.class);

	    
    // 현재 결재자
    public static AppLineVO getCurrentApprover(List<AppLineVO> list, String userId) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 찬성
	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 반대

	int listsize = list.size();
	for (int loop = 0; loop < listsize; loop++) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if ( (userId.equals(appLineVO.getApproverId()) || userId.equals(appLineVO.getRepresentativeId()) ) 
		    && ( apt003.equals(appLineVO.getProcType()) || apt004.equals(appLineVO.getProcType())
//		     	 || apt051.equals(appLineVO.getProcType()) || apt052.equals(appLineVO.getProcType()) 대결자가 합의자인 경우 3번 결재하는 문제 방지
		     	 ) ) {
		return appLineVO;
	    }
	}
	
	return null;
    }
    
    // 현재 결재자
    public static AppLineVO getCurrentApprover(List<AppLineVO> list) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 찬성
	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 반대

	int listsize = list.size();
	for (int loop = 0; loop < listsize; loop++) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if (apt003.equals(appLineVO.getProcType()) || apt004.equals(appLineVO.getProcType())
//	     || apt051.equals(appLineVO.getProcType()) || apt052.equals(appLineVO.getProcType()) 대결자가 합의자인 경우 3번 결재하는 문제 방지
	     ) {
		return appLineVO;
	    }
	}
	
	return null;
    }

    // 현재 처리자(접수문서)
    public static EnfLineVO getCurrentProcessor(List<EnfLineVO> list) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류

	int listsize = list.size();
	for (int loop = 0; loop < listsize; loop++) {
	    EnfLineVO enfLineVO = (EnfLineVO) list.get(loop);
	    if (apt003.equals(enfLineVO.getProcType()) || apt004.equals(enfLineVO.getProcType())) {
		return enfLineVO;
	    }
	}
	
	return null;
    }    
    
    // 현재 부서
    public static AppLineVO getCurrentDept(List<AppLineVO> list, String deptId) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	String apt051 = appCode.getProperty("APT051", "APT051", "APT"); // 찬성
	String apt052 = appCode.getProperty("APT052", "APT052", "APT"); // 반대
	String art032 = appCode.getProperty("ART032", "ART032", "ART"); // 부서협조
	String art132 = appCode.getProperty("ART132", "ART132", "ART"); // 부서합의
	String art041 = appCode.getProperty("ART041", "ART041", "ART"); // 부서감사

	int listsize = list.size();
	for (int loop = 0; loop < listsize; loop++) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if (deptId.equals(appLineVO.getApproverDeptId())
		    && ((art032.equals(appLineVO.getAskType()) ||art132.equals(appLineVO.getAskType()) || art041.equals(appLineVO.getAskType())) || (appLineVO.getLineNum() == 2 && "".equals(appLineVO.getApproverId())))
		    && (apt003.equals(appLineVO.getProcType()) || apt004.equals(appLineVO.getProcType())
	    		|| apt051.equals(appLineVO.getProcType()) || apt052.equals(appLineVO.getProcType()))) {
		return appLineVO;
	    }
	}
	
	return null;
    }
    
    // 현재사용자
    public static AppLineVO getCurrentUser(List<AppLineVO> list, String userId, String procType) {
	int listsize = list.size();
	for (int loop = listsize - 1; loop >= 0; loop--) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if ((userId.equals(appLineVO.getApproverId()) || userId.equals(appLineVO.getRepresentativeId())) 
		    && procType.equals(appLineVO.getProcType())) {
		return appLineVO;
	    }
	}
	
	return null;
    }
    
    /**
     * 현재사용자의 결재 유형에 따른 결재 의견 여부
     */
    public static boolean getCurrentUserOpinionChk(List<AppLineVO> list, String userId, String procType, String confirmAskTypes) {
	boolean result = false;
	int listsize = list.size();
	for (int loop = listsize - 1; loop >= 0; loop--) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if ((userId.equals(appLineVO.getApproverId()) || userId.equals(appLineVO.getRepresentativeId())) 
		    && procType.equals(appLineVO.getProcType()) && confirmAskTypes.indexOf(appLineVO.getAskType()) != -1 
		    && !"".equals(CommonUtil.nullTrim(appLineVO.getProcOpinion()))) {
		result = true;
	    }
	}
	
	return result;
    }

    // 결재하지 않은 다음 결재자
    public static AppLineVO getNextApprover(List<AppLineVO> list) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	String art054 = appCode.getProperty("ART054", "ART054", "ART"); // 후열
	String art055 = appCode.getProperty("ART055", "ART055", "ART"); // 통보 // jth8172 2012 신결재 TF

	int listsize = list.size();
	for (int loop = 0; loop < listsize; loop++) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if ( (!art054.equals(appLineVO.getAskType()) && !art055.equals(appLineVO.getAskType()) ) // jth8172 2012 신결재 TF
		    && (apt004.equals(appLineVO.getProcType()) || ("9999-12-31 23:59:59".equals(appLineVO.getProcessDate()) && ("".equals(appLineVO.getProcType()) || apt003.equals(appLineVO.getProcType()))))) {
		return appLineVO;
	    }
	}
	
	return null;
    }
    
    // 결재하지 않은 다음 결재자들
    public static List<AppLineVO> getNextApprovers(List<AppLineVO> list) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
	String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의
	String art054 = appCode.getProperty("ART054", "ART054", "ART"); // 후열
	String art055 = appCode.getProperty("ART055", "ART055", "ART"); // 통보 // jth8172 2012 신결재 TF

	List<AppLineVO> appLineVOs  = new ArrayList<AppLineVO>();
	int listsize = list.size();
	for (int loop = 0; loop < listsize; loop++) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if ((!art054.equals(appLineVO.getAskType()) && !art055.equals(appLineVO.getAskType()) )  // jth8172 2012 신결재 TF
		    && (apt004.equals(appLineVO.getProcType()) || ("9999-12-31 23:59:59".equals(appLineVO.getProcessDate()) && ("".equals(appLineVO.getProcType()) || apt003.equals(appLineVO.getProcType()))))) {
		if (art031.equals(appLineVO.getAskType()) || art131.equals(appLineVO.getAskType())) {
		    appLineVOs = getLineOrderApprover(list, appLineVO.getLineNum(), appLineVO.getLineOrder(), false);
		} else {
		    appLineVOs.add(appLineVO);
		}
		break;
	    }
	}
	
	return appLineVOs;
    }

    // 접수문서의 결재하지 않은 다음 결재자
    public static EnfLineVO getNextEnfApprover(List<EnfLineVO> list) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기

	int listsize = list.size();
	for (int loop = 0; loop < listsize; loop++) {
	    EnfLineVO enfLineVO = (EnfLineVO) list.get(loop);
	    if ("9999-12-31 23:59:59".equals(enfLineVO.getProcessDate()) && ("".equals(enfLineVO.getProcType()) || apt003.equals(enfLineVO.getProcType()))) {
		return enfLineVO;
	    }
	}
	
	return null;
    }

    // 결재하지 않은 동일 결재순서자들
    public static List<AppLineVO> getLineOrderApprover(List<AppLineVO> list, int lineNum, int lineOrder, boolean processed) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류

	List<AppLineVO> appLineVOs  = new ArrayList<AppLineVO>();
	int listsize = list.size();
	for (int loop = 0; loop < listsize; loop++) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if (appLineVO.getLineNum() == lineNum && appLineVO.getLineOrder() == lineOrder 
		    && (processed || (apt004.equals(appLineVO.getProcType()) || ("9999-12-31 23:59:59".equals(appLineVO.getProcessDate()) && ("".equals(appLineVO.getProcType()) || apt003.equals(appLineVO.getProcType())))))) {
		appLineVOs.add(appLineVO);
	    }
	}
	
	return appLineVOs;
    }
    
    // 다음 결재자(결재여부 상관없음)
    public static AppLineVO getNextApprover(List<AppLineVO> list, AppLineVO currentUser) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String art054 = appCode.getProperty("ART054", "ART054", "ART"); // 후열
	String art055 = appCode.getProperty("ART055", "ART055", "ART"); // 통보 // jth8172 2012 신결재 TF

	int listsize = list.size();
	for (int loop = 0; loop < listsize; loop++) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if (currentUser.getLineNum() <= appLineVO.getLineNum() && currentUser.getLineOrder() < appLineVO.getLineOrder()
		    && ( !art054.equals(appLineVO.getAskType()) &&  !art055.equals(appLineVO.getAskType()) )) { // jth8172 2012 신결재 TF
		return appLineVO;
	    }
	}
	
	return null;
    }
    
    // 다음 결재자들(결재여부 상관없음)
    public static List<AppLineVO> getNextApprovers(List<AppLineVO> list, AppLineVO currentUser) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
	String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의
	String art054 = appCode.getProperty("ART054", "ART054", "ART"); // 후열
	String art055 = appCode.getProperty("ART055", "ART055", "ART"); // 통보 // jth8172 2012 신결재 TF

	List<AppLineVO> appLineVOs  = new ArrayList<AppLineVO>();
	int listsize = list.size();
	for (int loop = 0; loop < listsize; loop++) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if (currentUser.getLineNum() <= appLineVO.getLineNum() && currentUser.getLineOrder() < appLineVO.getLineOrder() 
		    && ( !art054.equals(appLineVO.getAskType()) && !art055.equals(appLineVO.getAskType())) ) { // jth8172 2012 신결재 TF 
		if (art031.equals(appLineVO.getAskType()) || art131.equals(appLineVO.getAskType())) {
		    appLineVOs = getLineOrderApprover(list, appLineVO.getLineNum(), appLineVO.getLineOrder(), true);
		} else {
		    appLineVOs.add(appLineVO);
		}
		break;
	    }
	}
	
	return appLineVOs;
    }
    
    /**
     * 이전 승인자들
     */
    public static List<AppLineVO> getPrevApprovers(List<AppLineVO> list, AppLineVO currentUser){
	AppCode appCode = MemoryUtil.getCodeInstance();
	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
	List<AppLineVO> appLineVOs  = new ArrayList<AppLineVO>();
	int listsize = list.size();

	for (int loop = 0; loop < listsize; loop++) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if (currentUser.getLineNum() >= appLineVO.getLineNum() && currentUser.getLineOrder() > appLineVO.getLineOrder() 
		    && apt001.equals(appLineVO.getProcType())) {
		
		appLineVOs.add(appLineVO);		
	    }

	}
	
	return appLineVOs;
    }
    
    // 기안자
    public static AppLineVO getDrafter(List<AppLineVO> list) {
	int listsize = list.size();
	if (listsize > 0) {
	    return (AppLineVO) list.get(0);
	} else {
	    return null;
	}
    }

    
    // 접수문서 최초 선람/담당자
    public static EnfLineVO getFirstProcessor(List<EnfLineVO> list) {
	int listsize = list.size();
	if (listsize > 0) {
	    return (EnfLineVO) list.get(0);
	} else {
	    return null;
	}
    }

    // 접수문서 최종 담당자
    public static EnfLineVO getLastEnfApprover(List<EnfLineVO> list) {
	int listsize = list.size();
	EnfLineVO lastEnfLineVO = (EnfLineVO) list.get(listsize - 1);
	for (int loop = 0; loop < listsize; loop++) {
	    EnfLineVO enfLineVO = (EnfLineVO) list.get(loop);
	    if (enfLineVO.getLineOrder() > lastEnfLineVO.getLineOrder()) {
		lastEnfLineVO = enfLineVO;
	    }
	}
	
	return lastEnfLineVO;
    }   
    
    // 최종 결재자
    public static AppLineVO getLastApprover(List<AppLineVO> list) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String art054 = appCode.getProperty("ART054", "ART054", "ART"); // 후열
	String art055 = appCode.getProperty("ART055", "ART055", "ART"); // 통보 // jth8172 2012 신결재 TF

	int listsize = list.size();
	for (int loop = listsize - 1; loop >= 0; loop--) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if (!art054.equals(appLineVO.getAskType()) && !art055.equals(appLineVO.getAskType())) { // jth8172 2012 신결재 TF
		return appLineVO;
	    }
	}

	return null;
    }
    
 
    
    // 라인별 최종 결재자
    public static AppLineVO getLastApprover(List<AppLineVO> list, int lineNum) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String art054 = appCode.getProperty("ART054", "ART054", "ART"); // 후열
	String art055 = appCode.getProperty("ART055", "ART055", "ART"); // 통보  // jth8172 2012 신결재 TF

	int listsize = list.size();
	for (int loop = listsize - 1; loop >= 0; loop--) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if (appLineVO.getLineNum() == lineNum && (!art054.equals(appLineVO.getAskType()) && !art055.equals(appLineVO.getAskType()) ) ) {  // jth8172 2012 신결재 TF
		return appLineVO;
	    }
	}

	return null;
    }
    
    // 최종 감사결재자
    public static AppLineVO getLastAuditApprover(List<AppLineVO> list) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String art040 = appCode.getProperty("ART040", "ART040", "ART"); // 감사
	String art044 = appCode.getProperty("ART044", "ART044", "ART"); // 감사(결재)

	int listsize = list.size();
	for (int loop = listsize - 1; loop >= 0; loop--) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if (art040.equals(appLineVO.getAskType()) || art044.equals(appLineVO.getAskType())) {
		return appLineVO;
	    }
	}

	return null;
    }

    // 최종 감사결재자
    public static AppLineVO getLastAuditor(List<AppLineVO> list) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String art045 = appCode.getProperty("ART045", "ART045", "ART"); // 일상감사
	String art046 = appCode.getProperty("ART046", "ART046", "ART"); // 준법감시
	String art047 = appCode.getProperty("ART047", "ART047", "ART"); // 감사위원

	int listsize = list.size();
	for (int loop = listsize - 1; loop >= 0; loop--) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if (art045.equals(appLineVO.getAskType()) || art046.equals(appLineVO.getAskType()) || art047.equals(appLineVO.getAskType())) {
		return appLineVO;
	    }
	}

	return null;
    }

    // 반려 처리자
    public static AppLineVO getReturnApprover(List<AppLineVO> list) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려

	int listsize = list.size();
	for (int loop = listsize - 1; loop >= 0; loop--) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if (apt002.equals(appLineVO.getProcType())) {
		return appLineVO;
	    }
	}

	return null;
    }
    
    // 후열자
    public static List<AppLineVO> getAfterApprover(List<AppLineVO> list) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String art054 = appCode.getProperty("ART054", "ART054", "ART"); // 후열

	List<AppLineVO> afterVOs = new ArrayList<AppLineVO>();

	int listsize = list.size();
	for (int loop = 0; loop < listsize; loop++) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if (art054.equals(appLineVO.getAskType())) {
		afterVOs.add(appLineVO);
	    }
	}
	
	return afterVOs;
    }
    
	// 통보자  // jth8172 2012 신결재 TF
    public static List<AppLineVO> getInformApprover(List<AppLineVO> list) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String art055 = appCode.getProperty("ART055", "ART055", "ART"); // 통보

	List<AppLineVO> informVOs = new ArrayList<AppLineVO>();

	int listsize = list.size();
	for (int loop = 0; loop < listsize; loop++) {
	    AppLineVO appLineVO = (AppLineVO) list.get(loop);
	    if (art055.equals(appLineVO.getAskType())) {
		informVOs.add(appLineVO);
	    }
	}

	
	return informVOs;
    }
            
    // 동일한 결재라인여부(결재자) - 결재유형 구분
    public static boolean isSameApprover(AppLineVO source, AppLineVO target) {
	if (source.getLineNum() == target.getLineNum() && source.getLineOrder() == target.getLineOrder() && source.getLineSubOrder() == target.getLineSubOrder() 
		&& (source.getApproverId()).equals(target.getApproverId()) && (source.getApproverDeptId()).equals(target.getApproverDeptId()) 
		&& (source.getRepresentativeId()).equals(target.getRepresentativeId()) && (source.getRepresentativeDeptId()).equals(target.getRepresentativeDeptId())
		&& (source.getAskType()).equals(target.getAskType()) && (source.getApproverRole()).equals(target.getApproverRole())) {
	    return true;
	}

	return false;
    }
    
    // 접수문서 동일한 결재라인여부(결재자) - 결재유형 구분
    public static boolean isSameEnfApprover(EnfLineVO source, EnfLineVO target) {
	if (source.getLineOrder() == target.getLineOrder() && (source.getProcessorId()).equals(target.getProcessorId()) && (source.getProcessorDeptId()).equals(target.getProcessorDeptId()) 
		&& (source.getRepresentativeId()).equals(target.getRepresentativeId()) && (source.getRepresentativeDeptId()).equals(target.getRepresentativeDeptId())
		&& (source.getAskType()).equals(target.getAskType())) {
	    return true;
	}

	return false;
    }

    // 동일한 결재자여부(결재자) - 결재유형 상관없음
    public static boolean isSameApprovalUser(AppLineVO source, AppLineVO target) {
	if (source.getLineNum() == target.getLineNum() && source.getLineOrder() == target.getLineOrder() && source.getLineSubOrder() == target.getLineSubOrder() 
		&& (source.getApproverId()).equals(target.getApproverId()) && (source.getApproverDeptId()).equals(target.getApproverDeptId())
		&& (source.getRepresentativeId()).equals(target.getRepresentativeId()) && (source.getRepresentativeDeptId()).equals(target.getRepresentativeDeptId())) {
	    return true;
	}
	
	return false;
    }

    // 동일한 결재라인여부
    public static boolean isSameApprover(List<AppLineVO> sourceList, AppLineVO target) {
	int sourceCount = sourceList.size();
	for (int loop = 0; loop < sourceCount; loop++) {
	    AppLineVO source = sourceList.get(loop);
	    if (source.getLineNum() == target.getLineNum() && source.getLineOrder() == target.getLineOrder() && source.getLineSubOrder() == target.getLineSubOrder() 
		    && (source.getApproverId()).equals(target.getApproverId()) && (source.getApproverDeptId()).equals(target.getApproverDeptId())
		    && (source.getRepresentativeId()).equals(target.getRepresentativeId()) && (source.getRepresentativeDeptId()).equals(target.getRepresentativeDeptId())
		    && (source.getAskType()).equals(target.getAskType()) && (source.getApproverRole()).equals(target.getApproverRole())) {
		return true;
	    }
	}
	
	return false;
    }

    // 현재 보고경로에서 보류처리 사용자 수
    public static int getCurrentHoldOffCount(List<AppLineVO> appLineVOs, List<AppLineVO> currentLineVOs, AppLineVO currentUser) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류

	int count = 0;
	int lineNum = currentUser.getLineNum();
	int lineOrder = currentUser.getLineOrder();
	
	int currentlinesize = currentLineVOs.size();
	int applinesize = appLineVOs.size();
	int linesize = (currentlinesize > applinesize) ? applinesize : currentlinesize;
	for (int loop = 0; loop < linesize; loop++) {
	    AppLineVO appLineVO = appLineVOs.get(loop);
	    AppLineVO currentLineVO = currentLineVOs.get(loop);
	    if (appLineVO.getLineNum() == lineNum && appLineVO.getLineOrder() == lineOrder
		&& (appLineVO.getAskType()).equals(currentLineVO.getAskType())
		&& (appLineVO.getApproverId()).equals(currentLineVO.getApproverId())
		&& (appLineVO.getApproverDeptId()).equals(currentLineVO.getApproverDeptId())) {
		if (!(currentLineVO.getProcessDate()).equals(appLineVO.getProcessDate())) {
		    try {
	                Transform.duplicateDMO(currentLineVO, appLineVO);
                    } catch (Exception e) {
	                logger.error(e.getMessage());
                    }
		}
	    }
	    if (appLineVO.getLineNum() == lineNum && appLineVO.getLineOrder() == lineOrder && apt004.equals(appLineVO.getProcType())) {
		count++;
	    }
	}
	
	return count;
    }
        
    // 이중결재 주관부서 사용자
    public static AppLineVO getExecDeptUser(List<AppLineVO> appLineVOs) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토	
	String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)	ART021은 이중결재 부서 추가일 경우 사용한다.
	String art053 = appCode.getProperty("ART053", "ART053", "ART"); // 1인전결

	int linesize = appLineVOs.size();
	for (int loop = 0; loop < linesize; loop++) {
	    AppLineVO appLineVO = appLineVOs.get(loop);
	    if (appLineVO.getLineNum() == 2 && (art010.equals(appLineVO.getAskType())|| art020.equals(appLineVO.getAskType()) || art021.equals(appLineVO.getAskType()) || art053.equals(appLineVO.getAskType()))) {
		return appLineVO;
	    }
	}
	
	return null;
    }

    // 보고경로 이력정보    
    public static List<AppLineHisVO> getAppLineHis(List<AppLineVO> appLineVOs, String hisId, String registDate) {
	int lineCount = appLineVOs.size();
	for (int loop = 0; loop < lineCount; loop++) {
	    AppLineVO appLineVO = (AppLineVO) appLineVOs.get(loop);
	    appLineVO.setRegistDate(registDate);
	}
	
	return getAppLineHis(appLineVOs, hisId);
    }    
    
    // 보고경로 이력정보    
    public static List<AppLineHisVO> getAppLineHis(List<AppLineVO> appLineVOs, String hisId) {
	List<AppLineHisVO> appLineHisVOs = new ArrayList<AppLineHisVO>();
	int lineCount = appLineVOs.size();
	for (int loop = 0; loop < lineCount; loop++) {
	    AppLineVO appLineVO = (AppLineVO) appLineVOs.get(loop);    
	    AppLineHisVO appLineHisVO = new AppLineHisVO();
	    try {
	        Transform.transformVO(appLineVO, appLineHisVO);
             } catch (Exception e) {
	        logger.error(e.getMessage());
            }
	    appLineHisVO.setLineHisId(hisId);
	    
	    appLineHisVOs.add(appLineHisVO);
	}

	return appLineHisVOs;
    }

    // 첨부갯수
    public static int getAttachCount(List<FileVO> list) {
		AppCode appCode = MemoryUtil.getCodeInstance();
		String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
		String aft010 = appCode.getProperty("AFT010", "AFT010", "AFT"); // 연계첨부
	
		int attachcount = 0;
		int listsize = list.size();
		for (int loop = 0; loop < listsize; loop++) {
		    FileVO fileVO = (FileVO) list.get(loop);
		    if (aft004.equals(fileVO.getFileType()) || aft010.equals(fileVO.getFileType())) {
		    	attachcount++;
		    }
		}
		
		return attachcount;
    }

    // 첨부파일
    public static List<FileVO> getAttachFile(List<FileVO> list, String fileType) {
		AppCode appCode = MemoryUtil.getCodeInstance();
		String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
		String aft010 = appCode.getProperty("AFT010", "AFT010", "AFT"); // 연계첨부
	
		List<FileVO> attachVOs = new ArrayList<FileVO>();
		int fileCount = list.size();
		for (int loop = 0; loop < fileCount; loop++) {
		    FileVO fileVO = (FileVO) list.get(loop);
		    if ("ALL".equals(fileType)) {
				if (aft004.equals(fileVO.getFileType()) || aft010.equals(fileVO.getFileType())) {
				    attachVOs.add(fileVO);
				}
		    } else {
				if (fileType.equals(fileVO.getFileType())) {
				    attachVOs.add(fileVO);
				}
		    }
		}
		return attachVOs;
    }
    
    public static List<FileHisVO> getAttachFileHis(List<FileHisVO> list, String fileType) {
	AppCode appCode = MemoryUtil.getCodeInstance();
	String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
	String aft010 = appCode.getProperty("AFT010", "AFT010", "AFT"); // 연계첨부

	List<FileHisVO> attachVOs = new ArrayList<FileHisVO>();
	int fileCount = list.size();
	for (int loop = 0; loop < fileCount; loop++) {
	    FileHisVO fileHisVO = (FileHisVO) list.get(loop);
	    if ("ALL".equals(fileType)) {
		if (aft004.equals(fileHisVO.getFileType()) || aft010.equals(fileHisVO.getFileType())) {
		    attachVOs.add(fileHisVO);
		}
	    } else {
		if (fileType.equals(fileHisVO.getFileType())) {
		    attachVOs.add(fileHisVO);
		}
	    }
	}
	
	return attachVOs;
    }
    
    public static List<FileVO> copyFileId(List<FileVO> sourceList, List<FileVO> targetList) {
	int sourceCount = sourceList.size();
	int targetCount = targetList.size();
	
	for (int loop = 0; loop < sourceCount; loop++) {
	    FileVO source = sourceList.get(loop);
	    for (int pos = 0; pos < targetCount; pos++) {
		FileVO target = (FileVO) targetList.get(pos);
		if (source.getFileName().equals(target.getFileName())) {
		    target.setFileId(source.getFileId());
		    break;
		}
	    }
	}
	
	return targetList;
    }
    
    public static List<FileHisVO> getFileHis(List<FileVO> fileVOs, String hisId) {
	List<FileHisVO> fileHisVOs = new ArrayList<FileHisVO>();
	
	int filesize = fileVOs.size();
	for (int loop = 0; loop < filesize; loop++) {
	    FileVO fileVO = (FileVO) fileVOs.get(loop);
	    FileHisVO fileHisVO = new FileHisVO();
	    try {
	        Transform.transformVO(fileVO, fileHisVO);
            } catch (Exception e) {
	        logger.error(e.getMessage());
            }
	    fileHisVO.setFileHisId(hisId);

	    fileHisVOs.add(fileHisVO);
	}
	
	return fileHisVOs;
    }
    
    // return 동일 - same, 순서변경 - order, 변경 - change
    public static String compareFile(List<FileVO> fileVOs, FileVO targetVO, boolean setId) {
	int filesize = fileVOs.size();
	for (int loop = 0; loop < filesize; loop++) {
	    FileVO fileVO = (FileVO) fileVOs.get(loop);
	    if ((targetVO.getFileType()).equals(fileVO.getFileType()) && (targetVO.getFileName()).equals(fileVO.getFileName()) 
	    	&& targetVO.getFileSize() == fileVO.getFileSize()) {
		if (setId) {
		    targetVO.setFileId(fileVO.getFileId());
		}
		if (targetVO.getFileOrder() == fileVO.getFileOrder() && (fileVO.getDisplayName()).equals(targetVO.getDisplayName())) {
		    return "same";
		} else {
		    return "order";
		}
	    }
	}
	
	return "change";
    }

    public static boolean compareSendInfo(SendInfoVO prevVO, SendInfoVO nextVO) {
	if ((prevVO.getDocType()).equals(nextVO.getDocType()) && (prevVO.getSendOrgName()).equals(nextVO.getSendOrgName())
		&& (prevVO.getSenderTitle()).equals(nextVO.getSenderTitle()) && (prevVO.getHeaderCamp()).equals(nextVO.getHeaderCamp())
		&& (prevVO.getFooterCamp()).equals(nextVO.getFooterCamp()) && (prevVO.getPostNumber()).equals(nextVO.getPostNumber())
		&& (prevVO.getAddress()).equals(nextVO.getAddress()) && (prevVO.getTelephone()).equals(nextVO.getTelephone())
		&& (prevVO.getFax()).equals(nextVO.getFax()) && (prevVO.getVia()).equals(nextVO.getVia())
		&& (prevVO.getSealType()).equals(nextVO.getSealType()) && (prevVO.getHomepage()).equals(nextVO.getHomepage())
		&& (prevVO.getEmail()).equals(nextVO.getEmail()) && (prevVO.getReceivers()).equals(nextVO.getReceivers())
		&& (prevVO.getDisplayNameYn()).equals(nextVO.getDisplayNameYn())) {
	    return true;
	} else {
	    return false;
	}
    }
    
    // 수신자 동일여부 비교
    public static List<AppRecvVO> compareAppRecv(List<AppRecvVO> sourceList, List<AppRecvVO> targetList) {
	List<AppRecvVO> newVOs = new ArrayList<AppRecvVO>();
	boolean compareflag = false;
	int sourceCount = sourceList.size();
	int targetCount = targetList.size();
	for (int loop = 0; loop < sourceCount; loop++) {
	    AppRecvVO sourceVO = sourceList.get(loop);
	    for (int pos = 0; pos < targetCount; pos++) {
		AppRecvVO targetVO = targetList.get(pos);
		if (sourceVO.getReceiverOrder() == targetVO.getReceiverOrder() && (sourceVO.getReceiverType()).equals(targetVO.getReceiverType())
			&& (sourceVO.getEnfType()).equals(targetVO.getEnfType()) && (sourceVO.getRecvDeptId()).equals(targetVO.getRecvDeptId())
			&& (sourceVO.getEnfType()).equals(targetVO.getEnfType()) && (sourceVO.getRecvDeptId()).equals(targetVO.getRecvDeptId())
			&& (sourceVO.getRefDeptId()).equals(targetVO.getRefDeptId()) && (sourceVO.getRecvUserId()).equals(targetVO.getRecvUserId())
			&& (sourceVO.getRecvDeptName()).equals(targetVO.getRecvDeptName()) && (sourceVO.getRefDeptName()).equals(targetVO.getRefDeptName())
			&& (sourceVO.getRecvUserName()).equals(targetVO.getRecvUserName()) && (sourceVO.getPostNumber()).equals(targetVO.getPostNumber())
			&& (sourceVO.getAddress()).equals(targetVO.getAddress()) && (sourceVO.getTelephone()).equals(targetVO.getTelephone())
			&& (sourceVO.getFax()).equals(targetVO.getFax()) && (sourceVO.getRecvSymbol()).equals(targetVO.getRecvSymbol())
			&& (sourceVO.getRefSymbol()).equals(targetVO.getRefSymbol()) && (sourceVO.getRecvChiefName()).equals(targetVO.getRecvChiefName())
			&& (sourceVO.getRefChiefName()).equals(targetVO.getRefChiefName())  ) {
		    compareflag = true;
		    break;
		}
	    }
	    if (compareflag) {
		compareflag = false;
	    } else {
		newVOs.add(sourceVO);		
	    }
	}
	
	return newVOs;
    }

    
    // 관련문서 동일여부 비교
    public static List<RelatedDocVO> compareRelatedDoc(List<RelatedDocVO> sourceList, List<RelatedDocVO> targetList) {
	List<RelatedDocVO> newVOs = new ArrayList<RelatedDocVO>();
	boolean compareflag = false;
	int sourceCount = sourceList.size();
	int targetCount = targetList.size();
	for (int loop = 0; loop < sourceCount; loop++) {
	    RelatedDocVO sourceVO = sourceList.get(loop);
	    for (int pos = 0; pos < targetCount; pos++) {
		RelatedDocVO targetVO = targetList.get(pos);
		if ((sourceVO.getOriginDocId()).equals(targetVO.getOriginDocId())) {
		    compareflag = true;
		    break;
		}
	    }
	    if (compareflag) {
		compareflag = false;
	    } else {
		newVOs.add(sourceVO);		
	    }
	}
	
	return newVOs;
    }

    
    // 관련규정 동일여부 비교
    public static List<RelatedRuleVO> compareRelatedRule(List<RelatedRuleVO> sourceList, List<RelatedRuleVO> targetList) {
	List<RelatedRuleVO> newVOs = new ArrayList<RelatedRuleVO>();
	boolean compareflag = false;
	int sourceCount = sourceList.size();
	int targetCount = targetList.size();
	for (int loop = 0; loop < sourceCount; loop++) {
	    RelatedRuleVO sourceVO = sourceList.get(loop);
	    for (int pos = 0; pos < targetCount; pos++) {
		RelatedRuleVO targetVO = targetList.get(pos);
		if ((sourceVO.getRuleId()).equals(targetVO.getRuleId())) {
		    compareflag = true;
		    break;
		}
	    }
	    if (compareflag) {
		compareflag = false;
	    } else {
		newVOs.add(sourceVO);		
	    }
	}
	
	return newVOs;
    }

    
    // 거래처 동일여부 비교
    public static List<CustomerVO> compareCustomer(List<CustomerVO> sourceList, List<CustomerVO> targetList) {
	List<CustomerVO> newVOs = new ArrayList<CustomerVO>();
	boolean compareflag = false;
	int sourceCount = sourceList.size();
	int targetCount = targetList.size();
	for (int loop = 0; loop < sourceCount; loop++) {
	    CustomerVO sourceVO = sourceList.get(loop);
	    for (int pos = 0; pos < targetCount; pos++) {
		CustomerVO targetVO = targetList.get(pos);
		if ((sourceVO.getCustomerId()).equals(targetVO.getCustomerId())) {
		    compareflag = true;
		    break;
		}
	    }
	    if (compareflag) {
		compareflag = false;
	    } else {
		newVOs.add(sourceVO);		
	    }
	}
	
	return newVOs;
    }

    // 공람자 동일여부 비교
    public static List<PubReaderVO> comparePubReader(List<PubReaderVO> sourceList, List<PubReaderVO> targetList) {
	List<PubReaderVO> newVOs = new ArrayList<PubReaderVO>();
	boolean compareflag = false;
	int sourceCount = sourceList.size();
	int targetCount = targetList.size();
	for (int loop = 0; loop < sourceCount; loop++) {
	    PubReaderVO sourceVO = sourceList.get(loop);
	    for (int pos = 0; pos < targetCount; pos++) {
		PubReaderVO targetVO = targetList.get(pos);
		if ((sourceVO.getPubReaderId()).equals(targetVO.getPubReaderId())) {
		    compareflag = true;
		    break;
		}
	    }
	    if (compareflag) {
		compareflag = false;
	    } else {
		newVOs.add(sourceVO);		
	    }
	}
	
	return newVOs;
    }
}
