/**
 * 
 */
package com.sds.acube.app.mobile.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.vo.DocNumVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.approval.service.IApprovalService;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppLineVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.env.service.IEnvDocNumRuleService;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.idir.org.user.UserImage;
import com.sds.acube.app.mobile.service.IMobileAppService;
import com.sds.acube.app.mobile.vo.MobileAppActionVO;
import com.sds.acube.app.mobile.vo.MobileAppResultVO;
import com.sds.acube.app.mobile.vo.MobileQueueVO;
import com.sds.acube.app.mobile.ws.client.esbservice.AppActionVO;
import com.sds.acube.app.mobile.ws.client.esbservice.AppAttachVO;
import com.sds.acube.app.mobile.ws.client.esbservice.AppDetailVO;
import com.sds.acube.app.mobile.ws.client.esbservice.AppListVOs;
import com.sds.acube.app.mobile.ws.client.esbservice.AppMenuVOs;
import com.sds.acube.app.mobile.ws.client.esbservice.AppReqVO;
import com.sds.acube.app.mobile.ws.client.esbservice.AppResultVO;
import com.sds.acube.app.mobile.ws.client.esbservice.IEsbAppService;
import com.sds.acube.app.ws.client.bind.ArrayOfFieldVO;
import com.sds.acube.app.ws.client.bind.FieldVO;
import com.sds.acube.app.ws.client.bind.IBindApproverInfoWCF;
import com.sds.acube.app.ws.client.bind.ResVO;
import com.sds.acube.app.ws.client.bind.StorVO;

/** 
 *  Class Name  : MobileAppService.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2012. 6. 18. <br>
 *  수 정  자 : pjd  <br>
 *  수정내용 :  <br>
 * 
 *  @author  pjd 
 *  @since 2012. 6. 18.
 *  @version 1.0 
 *  @see  com.sds.acube.app.mobile.service.impl.MobileAppService.java
 */

@SuppressWarnings("serial")
@Service("mobileAppService")
public class MobileAppService extends BaseService implements IMobileAppService {
	
    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;

    /**
	 */
    @Inject
    @Named("approvalService")
    private IApprovalService approvalService;

    /**
	 */
    @Inject
    @Named("appComService")
    private IAppComService appComService;

    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;

    @Inject
    @Named("messageSource")
    private MessageSource messageSource;
    
    @Inject
    @Named("envDocNumRuleService")
    private IEnvDocNumRuleService envDocNumRuleService;

    /**
	 */
    @Inject
    @Named("orgService")
    private IOrgService orgService;
    
    /**
	 * webservice client  
	 */
	@Resource(name = "wsClientEsbAppService")
	private IEsbAppService mobileEsbAppService;
	
	/**
	 * webservice client  
	 */
	@Resource(name = "wsClientMobileBindService")
	private IBindApproverInfoWCF bindApproverInfoWCF;
	
	/**
	 * 함별 건수
	 */
	public AppMenuVOs getListMobileBox(AppReqVO reqVo)throws Exception {
		AppMenuVOs menuVo = new AppMenuVOs();
		menuVo = mobileEsbAppService.listMobileBox(reqVo);
		return menuVo;
	}
	
	/**
	 * 함별 목록
	 */
	public AppListVOs getListMobileAppDoc(AppReqVO reqVo) throws Exception {
		AppListVOs listVo = new AppListVOs();
		listVo = mobileEsbAppService.listAppDoc(reqVo);
		return listVo;
	}

	/**
	 * 결재상세내역
	 */
        public AppDetailVO getSelectDocInfoMobile(AppReqVO reqVo) throws Exception {
        	AppDetailVO detailVo = new AppDetailVO();
        	detailVo = mobileEsbAppService.selectDocInfoMobile(reqVo);
        	
        	return detailVo;
        }
        
	/**
	 * 결재처리 요청
	 */
	public AppResultVO processAppMobile(AppActionVO actionVo) throws Exception {
		AppResultVO resultVo = new AppResultVO(); 
		resultVo = mobileEsbAppService.processDoc(actionVo);
		
		return resultVo;
	}

	/**
         * 모바일 첨부파일
         */
        public AppAttachVO getAttachFile(AppReqVO reqVo) throws Exception {
	    	AppAttachVO attachVo = new AppAttachVO();
	    	attachVo = mobileEsbAppService.getAttachFile(reqVo);
            	return attachVo;
        }
	
	
	
    	/**
         * 모바일 첨부파일바인딩
         */
        public void setApproverInfo() throws Exception {

        	//JaxWsProxyFactoryBean factory = null;
        	//factory = new JaxWsProxyFactoryBean();
        	//factory.setServiceClass(BindApproverInfoWSSoap.class);
        	//factory.setAddress("http://70.7.32.231/ep/app/mobile/ws/bind/bindApproverInfoWS.asmx?WSDL");
        	//factory.setBindingId(javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING);

        	// ---------------------
        	// 서비스 인터페이스
        	// ---------------------
        	//bindApproverInfoWSSoap = (BindApproverInfoWSSoap) factory.create();
        	
        	
			File file = new File("D:\\temp\\sign.jpg");
			FileInputStream fis = null;
			byte[] image = null;

			try {
				fis = new FileInputStream(file);
				image = new byte[fis.available()];        
				fis.read(image, 0, fis.available());
			} catch(IOException e) {
				throw e;
			} finally {
				if(fis != null) fis.close();
			}
	    	
			String fileId = "b9b6c112a49c78049cd470ded1df5c37";
			int fileType = 1;
			
			StorVO storVO = new StorVO();
			storVO.setStorIp("70.7.32.236");
			storVO.setPort(7082);
			storVO.setVolumeName("HDVOLUME01");
			storVO.setVolumeId(101);
			storVO.setJstorApiType("ndisc");
			storVO.setStorFileType(2);
			
			FieldVO fieldVO1 = new FieldVO();
			fieldVO1.setField("검토1");
			fieldVO1.setSignType(1);
			fieldVO1.setSign("최총무");
			
			FieldVO fieldVO2 = new FieldVO();
			fieldVO2.setField("검토2");
			fieldVO2.setSignType(2);
			fieldVO2.setSign("588214ba1aef11e1d812000d9dfe2e1f_2.jpg");
			fieldVO2.setImage(image);
			//fieldVO2.setSignType(1);
			//fieldVO2.setSign("TEST");

			ArrayOfFieldVO signVOs = new ArrayOfFieldVO();
			signVOs.getFieldVO().add(fieldVO1);
			signVOs.getFieldVO().add(fieldVO2);
	    	
			ResVO resVO = bindApproverInfoWCF.setApproverInfo(fileId, fileType, storVO, signVOs);
			System.out.println("setApproverInfo.result=" + "[bind : " + resVO.isBind() + "], [fileid : " + resVO.getFileId() + "], [errorMessage : " + resVO.getErrorMessage() + "]");
        }
	
        public void processMobileApp(MobileQueueVO mobileQueueVO) throws Exception {

        	AppLineVO appLineVO = new AppLineVO();
        	appLineVO.setDocId(mobileQueueVO.getDocId());
        	List<AppLineVO> appLineVOList = selectMobileAppLine(appLineVO);

        	String processorId 			= mobileQueueVO.getProcessorId();
        	String docId 				= mobileQueueVO.getDocId();
        	String compId 				= appLineVOList.get(0).getCompId();
        	
        	String fieldDateFormat	 	= AppConfig.getProperty("date_format", "", "date");
        	String currentDate 			= DateUtil.getCurrentDate();
        	String approvalDate			= DateUtil.getCurrentDate(fieldDateFormat);
        	String fieldDate			= "";
        	
        	if ((currentDate.length() == 10) || (currentDate.length() == 19))
        		fieldDate =  currentDate.substring(0,4) + "." + currentDate.substring(5,7) + "." + currentDate.substring(8,10) + ".";
        	else if (currentDate.length() == 14)
        		fieldDate =  currentDate.substring(0,4) + "." + currentDate.substring(4,6) + "." + currentDate.substring(6,8) + ".";

        	String fileId = selectMobileFileId(compId, docId);
        	int bodyFileType = 1;	//HWP

        	String[] appLineInfo = getAppLineInfo(compId, processorId, appLineVOList);
        	String fieldName = appLineInfo[0];
        	String lastAppLineYN = appLineInfo[1];
        	String processorName = appLineInfo[2];
        	String processorPos = appLineInfo[3];
        	String processorDeptName = appLineInfo[4];
        	
        	//HWP, MS-Word 작성기 필드구분
        	String type 		= mobileQueueVO.getFileType();
        	String prefixPos	= "*";
        	String prefixDate 	= "@";
        	String prefixName 	= "#";
        	
        	if(!"".equals(type) && type != null) {
        		if(type.indexOf("doc") > 0) {
                	prefixPos	= "P_";
                	prefixDate 	= "D_";
                	prefixName 	= "N_"; 
                	
                	bodyFileType = 2;	//MS-Word
        		}
        	}

        	int serialNumber = 0;

        	//서명 필드
        	FieldVO fieldVO1 = new FieldVO();
        	fieldVO1.setField(fieldName);
        	fieldVO1.setSignType(1);
        	fieldVO1.setSign(processorName);

        	String personalSign = mobileQueueVO.getSignYn();
		    
		    if ("Y".equals(personalSign)) {
			    UserImage userImage = orgService.selectUserImage(processorId);
	        	
	        	if(userImage != null && (!"".equals(userImage.getStampType()) || !"".equals(userImage.getSignType()))) {
	        		int stampOrSign = userImage.getStampOrSign();

	        		if(stampOrSign < 2) {
	        			String fileType = null;
	        			
	        			if(stampOrSign == 0) {
	        				fileType = userImage.getStampType();
	        			}
	        			else {
	        				fileType = userImage.getSignType();
	        			}
	        			
	        			String fileName = GuidUtil.getGUID();
	        			
	        			if(fileType != null && !"".equals(fileType)) {
	        				fileName += "." + fileType;
	        			}
	        			
	        			fieldVO1.setSignType(2);
	        			fieldVO1.setSign(fileName);
	        			fieldVO1.setUserUid(processorId);
	        		}

	        	}
		    }

        	//직위,직급 필드
        	FieldVO fieldVO2 = new FieldVO();
        	fieldVO2.setField(prefixPos + fieldName);
        	fieldVO2.setSignType(1);
        	fieldVO2.setSign(processorPos);

        	ArrayOfFieldVO signVOs = new ArrayOfFieldVO();
        	signVOs.getFieldVO().add(fieldVO1);
        	signVOs.getFieldVO().add(fieldVO2);

        	if("Y".equals(lastAppLineYN)) {
        		String fieldEnforceNumber = messageSource.getMessage("hwpconst.form.enforcenumber" , null , Locale.getDefault()); //시행번호
        		String fieldApproval = messageSource.getMessage("hwpconst.field.approval" , null , Locale.getDefault()); //결재
        		String fieldApprovalDept = messageSource.getMessage("hwpconst.field.approvaldept" , null , Locale.getDefault()); //결재부서
        		String fieldApprovalDate = messageSource.getMessage("hwpconst.field.approvaldate" , null , Locale.getDefault()); //결재일자
        		
        		/**
        		String fieldApproval = messageSource.getMessage("hwpconst.form.assistance" , null , Locale.getDefault()); //결재
        		String fieldApprovalDept = messageSource.getMessage("hwpconst.form.consider" , null , Locale.getDefault()); //결재부서
        		String fieldApprovalDate = messageSource.getMessage("hwpconst.form.auditor" , null , Locale.getDefault()); //결재일자
        		**/

        		// 결재일자 필드
        		FieldVO fieldVO3 = new FieldVO();
        		fieldVO3.setField(prefixDate + fieldName);
        		fieldVO3.setSignType(1);
        		fieldVO3.setSign(fieldDate);
        		
        		signVOs.getFieldVO().add(fieldVO3);

        		// 시행번호 필드
        		AppDocVO appDocVO = approvalService.selectAppDoc(compId, mobileQueueVO.getDocId());
        		String deptCategory = appDocVO.getDeptCategory();
        		String drafterDeptId = appDocVO.getDrafterDeptId();
        		OrganizationVO org = orgService.selectOrganization(drafterDeptId);			    			    
        		String proxyDeptId = org.getProxyDocHandleDeptCode();
        		appDocVO.getDrafterDeptId();
        		if (proxyDeptId != null && !"".equals(proxyDeptId)) {
        			OrganizationVO proxyDept = orgService.selectOrganization(proxyDeptId);
        			if (proxyDept != null) {
        				deptCategory = envDocNumRuleService.getDocNum(proxyDeptId, compId, CommonUtil.nullTrim(appDocVO.getClassNumber()));	//문서번호 옵션화-생성규칙 형식에 맞게 문서번호 표현
        				drafterDeptId = proxyDeptId;
        			}
        		}

        		String regi = appCode.getProperty("REGI", "REGI", "NCT");
        		DocNumVO docNumVO = new DocNumVO();
        		docNumVO.setCompId(appDocVO.getCompId());
        		docNumVO.setDeptId(drafterDeptId);
        		docNumVO.setNumPeriod(envOptionAPIService.getCurrentPeriodStr(compId));
        		docNumVO.setNumType(regi);
        		int num = appComService.selectDocNum(docNumVO);
        		if(num > 0) appComService.updateDocNum(docNumVO);

        		serialNumber = num;

        		FieldVO fieldVO4 = new FieldVO();
        		fieldVO4.setField(fieldEnforceNumber);
        		fieldVO4.setSignType(1);
        		fieldVO4.setSign(deptCategory + "-" + serialNumber);

        		signVOs.getFieldVO().add(fieldVO4);

        		// 결재부서 필드
        		FieldVO fieldVO5 = new FieldVO();
        		fieldVO5.setField(fieldApprovalDept);
        		fieldVO5.setSignType(1);
        		fieldVO5.setSign(processorDeptName);

        		// 결재자직위 필드
        		FieldVO fieldVO6 = new FieldVO();
        		fieldVO6.setField(prefixPos + fieldApproval);
        		fieldVO6.setSignType(1);
        		fieldVO6.setSign(processorPos);

        		// 결재자이름 필드
        		FieldVO fieldVO7 = new FieldVO();
        		fieldVO7.setField(prefixName + fieldApproval);
        		fieldVO7.setSignType(1);
        		fieldVO7.setSign(processorName);

        		// 결재일자
        		FieldVO fieldVO8 = new FieldVO();
        		fieldVO8.setField(fieldApprovalDate);
        		fieldVO8.setSignType(1);
        		fieldVO8.setSign(approvalDate);

        		signVOs.getFieldVO().add(fieldVO5);
        		signVOs.getFieldVO().add(fieldVO6);
        		signVOs.getFieldVO().add(fieldVO7);
        		signVOs.getFieldVO().add(fieldVO8);

        	}

        	String storSvr = AppConfig.getProperty("stor_svr", "", "attach");
        	int storPort = Integer.parseInt(AppConfig.getProperty("stor_port", "7082", "attach"));
        	String jstorApiType = AppConfig.getProperty("jstor_api_type", "ndisc", "attach");
        	int storVol = Integer.parseInt(AppConfig.getProperty("stor_vol", "101", "attach"));
        	String jstorSvrType = AppConfig.getProperty("jstor_svr_type", "unix", "attach");

        	StorVO storVO = new StorVO();
        	storVO.setStorIp(storSvr);
        	storVO.setPort(storPort);
        	//storVO.setVolumeName("HDVOLUME01");
        	storVO.setVolumeId(storVol);
        	storVO.setJstorApiType(jstorApiType);
        	storVO.setStorFileType(2);
        	storVO.setJstorSvrType(jstorSvrType);
        	
        	ResVO resVO = new ResVO();
        			
        	if(!"".equals(type) && type != null) {
        		if("html".equals(type)) {
        			resVO.setBind(true);
        			resVO.setFileId(fileId);
        		}else {
        			resVO = bindApproverInfoWCF.setApproverInfo(fileId, bodyFileType, storVO, signVOs);
        		}
        	}
        	
        	System.out.println("setApproverInfo.result=" + "[bind : " + resVO.isBind() + "], [fileid : " + resVO.getFileId() + "], [errorMessage : " + resVO.getErrorMessage() + "]");

        	if(resVO.isBind()) {
        		MobileAppActionVO mobileAppActionVO = new MobileAppActionVO();
        		mobileAppActionVO.setActioncode(mobileQueueVO.getProcessType());
        		mobileAppActionVO.setAppopinion(mobileQueueVO.getProcessOpinion());
        		mobileAppActionVO.setSignYn(personalSign);
        		mobileAppActionVO.setDocid(docId);
        		mobileAppActionVO.setOrgcode(compId);
        		mobileAppActionVO.setReqdate(currentDate);
        		mobileAppActionVO.setUserid(processorId);
        		mobileAppActionVO.setSerialnumber(serialNumber);
        		MobileAppResultVO mobileAppResultVO = approvalService.processMobileApp(mobileAppActionVO);

        		if("success".equals(mobileAppResultVO.getRespose_code())) {
        			FileVO fileVO = new FileVO();
        			fileVO.setDocId(docId);
        			fileVO.setFileId(resVO.getFileId());;
        			int result = updateBodyFileInfo(fileVO);
        			if(result > 0) {
        				result = deleteMobileQueue(mobileQueueVO);
        				if(result > 0) {
        				}
        			} else {
        				updateRetryCountMobileQueue(mobileQueueVO);
        			}
        		} else {
    				updateRetryCountMobileQueue(mobileQueueVO);
        		}
            	logger.printVO(mobileAppResultVO);
        	} else {
				updateRetryCountMobileQueue(mobileQueueVO);
        	}
        }
        
        public List<MobileQueueVO> selectMobileQueue() throws Exception {

        	MobileQueueVO mobileQueueVO = new MobileQueueVO();
        	return commonDAO.getList("mobile.selectMobileQueue", mobileQueueVO);
        }
        
        // 모바일 결재 스케줄러에서 파일 타입 ('.doc', 'html', '.hwp')을 추가한 정보를 조회한다.
        public List<MobileQueueVO> selectMobileQueueFileType() throws Exception {

        	MobileQueueVO mobileQueueVO = new MobileQueueVO();
        	return commonDAO.getList("mobile.selectMobileQueueFileType", mobileQueueVO);
        }        

        private int deleteMobileQueue(MobileQueueVO mobileQueueVO) throws Exception {
    		return commonDAO.delete("mobile.deleteMobileQueue", mobileQueueVO);
    	}

    	public int updateRetryCountMobileQueue(MobileQueueVO mobileQueueVO) throws Exception {
    		return this.commonDAO.modify("mobile.updateRetryCountMobileQueue", mobileQueueVO);
    	}

    	public int updateBodyFileInfo( FileVO fileVO) throws Exception {
    		return this.commonDAO.modify("mobile.updateBodyFileInfo", fileVO);
    	}

        private String selectMobileFileId(String compId, String docId) throws Exception {

    	    Map<String, String> map = new HashMap<String, String>();
    	    map.put("compId", compId);
    	    map.put("docId", docId);
        	
    		String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // 본문(HWP)
    		map.put("fileType", "'" + aft001 + "'");

    		List<FileVO> fileVOs = commonDAO.getListMap("appcom.listBody", map);
    		if (fileVOs.size() > 0) {
    		    return fileVOs.get(0).getFileId();
    		} else {
    		    return null;
    		}
        }
        
        private List<AppLineVO> selectMobileAppLine(AppLineVO appLineVO) throws Exception {
        	return commonDAO.getList("mobile.selectAppLine", appLineVO);
        }

        private String[] getAppLineInfo(String compId, String processorId, List<AppLineVO> appLineVOList) throws Exception {


        	String art010 = appCode.getProperty("ART010", "ART010", "ART"); // 기안
        	String art020 = appCode.getProperty("ART020", "ART020", "ART"); // 검토
        	String art021 = appCode.getProperty("ART021", "ART021", "ART"); // 검토(주관부서)
        	String art030 = appCode.getProperty("ART030", "ART030", "ART"); // 협조
        	String art031 = appCode.getProperty("ART031", "ART031", "ART"); // 병렬협조
        	String art032 = appCode.getProperty("ART032", "ART032", "ART"); // 부서협조
        	String art033 = appCode.getProperty("ART033", "ART033", "ART"); // 협조(기안)
        	String art034 = appCode.getProperty("ART034", "ART034", "ART"); // 협조(검토)
        	String art035 = appCode.getProperty("ART035", "ART035", "ART"); // 협조(결재)

        	String art130 = appCode.getProperty("ART130", "ART130", "ART"); // 합의 // jth8172 2012 신결재 TF
        	String art131 = appCode.getProperty("ART131", "ART131", "ART"); // 병렬합의 // jth8172 2012 신결재 TF
        	String art132 = appCode.getProperty("ART132", "ART132", "ART"); // 부서합의 // jth8172 2012 신결재 TF
        	String art133 = appCode.getProperty("ART133", "ART133", "ART"); // 합의(기안) // jth8172 2012 신결재 TF
        	String art134 = appCode.getProperty("ART134", "ART134", "ART"); // 합의(검토) // jth8172 2012 신결재 TF
        	String art135 = appCode.getProperty("ART135", "ART135", "ART"); // 합의(결재) // jth8172 2012 신결재 TF

        	String art040 = appCode.getProperty("ART040", "ART040", "ART"); // 감사
        	String art041 = appCode.getProperty("ART041", "ART041", "ART"); // 부서감사
        	String art042 = appCode.getProperty("ART042", "ART042", "ART"); // 감사(기안)
        	String art043 = appCode.getProperty("ART043", "ART043", "ART"); // 감사(검토)
        	String art044 = appCode.getProperty("ART044", "ART044", "ART"); // 감사(결재)
        	String art045 = appCode.getProperty("ART045", "ART045", "ART"); // 일상감사
        	String art046 = appCode.getProperty("ART046", "ART046", "ART"); // 준법감시
        	String art047 = appCode.getProperty("ART047", "ART047", "ART"); // 감사위원
        	String art050 = appCode.getProperty("ART050", "ART050", "ART"); // 결재 
        	String art051 = appCode.getProperty("ART051", "ART051", "ART"); // 전결
        	String art052 = appCode.getProperty("ART052", "ART052", "ART"); // 대결
        	String art053 = appCode.getProperty("ART053", "ART053", "ART"); // 1인전결
        	String art054 = appCode.getProperty("ART054", "ART054", "ART"); // 후열
        	String art055 = appCode.getProperty("ART055", "ART055", "ART"); // 통보 // jth8172 2012 신결재 TF
        	String art060 = appCode.getProperty("ART060", "ART060", "ART"); // 선람
        	String art070 = appCode.getProperty("ART070", "ART070", "ART"); // 담당	

        	String apt001 = appCode.getProperty("APT001", "APT001", "APT"); // 승인
        	String apt002 = appCode.getProperty("APT002", "APT002", "APT"); // 반려
        	String apt003 = appCode.getProperty("APT003", "APT003", "APT"); // 대기
        	String apt004 = appCode.getProperty("APT004", "APT004", "APT"); // 보류
        	
        	String assistantlinetype = appCode.getProperty("OPT303", "OPT303", "OPT"); // 부서협조 - 1 : 최종협조자, 2 : 모든협조자
        	assistantlinetype = envOptionAPIService.selectOptionValue(compId, assistantlinetype);
        	String auditlinetype = appCode.getProperty("OPT304", "OPT304", "OPT"); // 감사표시 - 1 : 결재라인, 2 : 협조라인, 3 : 감사라인	
        	auditlinetype = envOptionAPIService.selectOptionValue(compId, auditlinetype);

        	String approverCode = art010 + "|" + art020 + "|" + art021 + "|" + art050 + "|" + art051 + "|" + art052 + "|" + art053;
        	if (auditlinetype == "1") {
        		approverCode += "|" + art040 + "|" + art041 + "|" + art044 + "|" + art045 + "|" + art046 + "|" + art047;
        	}
        	String assistantCode = "";
        	if (assistantlinetype == "1") { 
        		assistantCode += art030 + "|" + art031 + "|" + art032 + "|" + art035 + "|" + art130 + "|" + art131 + "|" + art132 + "|" + art135;
        	} else {
        		assistantCode += art030 + "|" + art031 + "|" + art032 + "|" + art033 + "|" + art034 + "|" + art035 + "|" + art130 + "|" + art131 + "|" + art132 + "|" + art133 + "|" + art134 + "|" + art135;
        	}
        	if (auditlinetype == "2") {
        		assistantCode += "|" + art040 + "|" + art041 + "|" + art044 + "|" + art045 + "|" + art046 + "|" + art047;
        	}

        	String auditorCode = "";
        	if (auditlinetype == "3") {
        		auditorCode = art040 + "|" + art041 + "|" + art044 + "|" + art045 + "|" + art046 + "|" + art047;
        	}

			int approverIndex = 0;
        	int assistantIndex = 0;
        	int auditorIndex = 0;
        	String fieldName = "";
        	
        	String fieldConsider = messageSource.getMessage("hwpconst.form.consider" , null , Locale.getDefault());
        	String fieldAssistance = messageSource.getMessage("hwpconst.form.assistance" , null , Locale.getDefault());
        	String fieldAuditor = messageSource.getMessage("hwpconst.form.auditor" , null , Locale.getDefault());
        	
        	int lineIndex = 0;
        	for(AppLineVO appLineVO : appLineVOList) {
        		if(approverCode.indexOf(appLineVO.getAskType()) != -1) {
        			approverIndex++;
        			fieldName = fieldConsider + approverIndex;
        		} else if(assistantCode.indexOf(appLineVO.getAskType()) != -1) {
        			assistantIndex++;
        			fieldName = fieldAssistance + assistantIndex;
        		} else if(auditorCode.indexOf(appLineVO.getAskType()) != -1) {
        			auditorIndex++;
        			fieldName = fieldAuditor + auditorIndex;
        		}
        		if((processorId.equals(appLineVO.getApproverId()) || processorId.equals(appLineVO.getRepresentativeId()))
        				&& (apt003.equals(appLineVO.getProcType()) || apt004.equals(appLineVO.getProcType()) || "".equals(appLineVO.getProcType()))) {
        			break;
        		}
        		lineIndex++;
        	}
        	
        	String lastAppLineYN = "N";
        	int linesize = appLineVOList.size();
        	for (int loop = linesize - 1; loop >= 0; loop--) {
        		AppLineVO appLineVO = appLineVOList.get(loop);
        		if (!art054.equals(appLineVO.getAskType()) && !art055.equals(appLineVO.getAskType())) {
        			if(loop == lineIndex) {
        				lastAppLineYN = "Y";
        			}
        			break;
        		}
        	}

        	String[] result = new String[5];
        	result[0] = fieldName;
        	result[1] = lastAppLineYN;
        	result[2] = appLineVOList.get(lineIndex).getApproverName();
        	result[3] = appLineVOList.get(lineIndex).getApproverPos();
        	result[4] = appLineVOList.get(lineIndex).getApproverDeptName();
        	return result;
        }

        private AppLineVO getLastApprover(List<AppLineVO> appLineVOList) throws Exception {

        	String art054 = appCode.getProperty("ART054", "ART054", "ART"); // 후열
        	String art055 = appCode.getProperty("ART055", "ART055", "ART"); // 통보 // jth8172 2012 신결재 TF

        	AppLineVO lastAppLineVO = null;
        	int linesize = appLineVOList.size();
        	for (int loop = linesize - 1; loop >= 0; loop--) {
        		AppLineVO appLineVO = appLineVOList.get(loop);
        		if (!art054.equals(appLineVO.getAskType()) && !art055.equals(appLineVO.getAskType())) {
        			lastAppLineVO = appLineVO;
        		}
        	}
        	return lastAppLineVO;
        }

    	/**
    	 * 모바일 결재처리(QUEUE 생성)
    	 */
    	public MobileAppResultVO processMobileApproval(MobileAppActionVO mobileActionVo) throws Exception {
    		MobileAppResultVO resultVo = new MobileAppResultVO(); 
    		resultVo = mobileEsbAppService.processMobileApproval(mobileActionVo);
    		
    		return resultVo;
    	}

}
