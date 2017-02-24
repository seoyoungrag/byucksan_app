package com.sds.acube.app.ws.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebService;
import javax.xml.ws.BindingType;

import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;
import org.springframework.stereotype.Service;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.approval.service.IApprovalService;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.Transform;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.enforce.service.IEnfLineService;
import com.sds.acube.app.enforce.service.IEnforceAppService;
import com.sds.acube.app.enforce.vo.EnfDocVO;
import com.sds.acube.app.enforce.vo.EnfLineVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.vo.OptionVO;
import com.sds.acube.app.etc.vo.PubPostVO;
import com.sds.acube.app.list.service.IListApprovalService;
import com.sds.acube.app.list.service.IListCompleteService;
import com.sds.acube.app.list.service.IListEtcService;
import com.sds.acube.app.list.service.IListReceiveService;
import com.sds.acube.app.list.service.IListRegistService;
import com.sds.acube.app.list.service.IListSendService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.list.vo.SearchVO;
import com.sds.acube.app.login.security.EnDecode;
import com.sds.acube.app.mobile.vo.MobileAppActionVO;
import com.sds.acube.app.mobile.vo.MobileAppResultVO;
import com.sds.acube.app.mobile.vo.MobileQueueVO;
import com.sds.acube.app.ws.service.IEsbAppService;
import com.sds.acube.app.ws.vo.AppActionVO;
import com.sds.acube.app.ws.vo.AppAttachVO;
import com.sds.acube.app.ws.vo.AppDetailVO;
import com.sds.acube.app.ws.vo.AppFileVO;
import com.sds.acube.app.ws.vo.AppFileVOs;
import com.sds.acube.app.ws.vo.AppItemCountVO;
import com.sds.acube.app.ws.vo.AppListVO;
import com.sds.acube.app.ws.vo.AppListVOs;
import com.sds.acube.app.ws.vo.AppMenuVO;
import com.sds.acube.app.ws.vo.AppMenuVOs;
import com.sds.acube.app.ws.vo.AppReqVO;
import com.sds.acube.app.ws.vo.AppResultVO;
import com.sds.acube.app.ws.vo.HeaderVO;

/**
 * Class Name : EsbAppService.java <br> 
 * Description : 연계처리를 위한 웹서비스 구현 클래스 <br> 
 * Modification Information <br> 
 * <br> 
 * 수 정 일 : 2012. 6. 28. <br> 
 * 수 정 자 : 김상태 <br> 
 * 수정내용 : Legacy(ESB연계부분 별도로 제공하여 소스내 제거)<br>
 * 
 * @author  윤동원
 * @since  2011. 5. 23.
 * @version  1.0
 * @see  com.sds.acube.app.ws.service.impl.EsbAppService.java
 */

@SuppressWarnings("serial")
@Service("esbAppService")
@WebService(endpointInterface = "com.sds.acube.app.ws.service.IEsbAppService")
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_MTOM_BINDING)
public class EsbAppService extends BaseService implements IEsbAppService {

    /**
	 */
    @Inject
    @Named("listApprovalService")
    private IListApprovalService listApprovalService;

    /**
	 */
    @Inject
    @Named("listSendService")
    private IListSendService listSendService;

    /**
	 */
    @Inject
    @Named("listReceiveService")
    private IListReceiveService listReceiveService;

    /**
	 */
    @Inject
    @Named("listCompleteService")
    private IListCompleteService listCompleteService;

    /**
	 */
    @Inject
    @Named("listRegistService")
    private IListRegistService listRegistService;

    /**
	 */
    @Inject
    @Named("listEtcService")
    private IListEtcService listEtcService;

    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;

    /**
	 */
    @Inject
    @Named("orgService")
    private IOrgService orgService;
    
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
    @Named("enforceAppService")
    private IEnforceAppService enforceAppService;
    
    /**
	 */
    @Inject
    @Named("enfLineService")
    private IEnfLineService enfLineService;

    /**
	 */
    @Inject
    @Named("attachService")
    private IAttachService attachService;
    
    /**
	 */
    @Inject
    @Named("appComService")
    private IAppComService appComService;

    /**
     * <pre> 
     *  파일가져오기
     * </pre>
     * 
     * @param input
     * @param output
     * @throws IOException
     * @see
     */
    private AppFileVO setAppFile(FileVO fileVO, String mobileYn) throws Exception {
	
	StorFileVO storFileVO = new StorFileVO();
	String filepath = AppConfig.getProperty("upload_temp", null, "attach") + "/" + fileVO.getCompId() + "";// 파일
	String storpath = filepath = filepath + fileVO.getFileName();
	
	AppFileVO appFileVO = new AppFileVO();
	
	appFileVO.setFileName(fileVO.getDisplayName());
	appFileVO.setFileId(fileVO.getFileId());
	
	if("Y".equals(mobileYn)){
	    if (appCode.getProperty("AFT002", "AFT002", "AFT").equals(fileVO.getFileType())
		        || appCode.getProperty("AFT003", "AFT003", "AFT").equals(fileVO.getFileType())) {
		storFileVO.setFilename(fileVO.getFileName());
		storFileVO.setFileid(fileVO.getFileId());
		storFileVO.setFilepath(storpath);

		DrmParamVO drmParamVO = new DrmParamVO();
		drmParamVO.setCompId(fileVO.getCompId());
		drmParamVO.setUserId(fileVO.getProcessorId());

		attachService.downloadAttach(storFileVO, drmParamVO);

		// 결재정보
		File file = new File(storpath);//
		URL fileURL = file.toURI().toURL();

		DataHandler handler = new DataHandler(fileURL);

		appFileVO.setContent(handler);
		appFileVO.setFileType("body");
	    }
	    else if (appCode.getProperty("AFT001", "AFT001", "AFT").equals(fileVO.getFileType())) {
		appFileVO.setFileType("body");
	    }
	}

	
	// 첨부파일
	if (appCode.getProperty("AFT004", "AFT004", "AFT").equals(fileVO.getFileType())
	        || appCode.getProperty("AFT010", "AFT010", "AFT").equals(fileVO.getFileType())) {
	    appFileVO.setFileType("attach");
	}
	    
	return appFileVO;
    }

    
    
    /**
     * <pre> 
     *  파일가져오기
     * </pre>
     * 
     * @param input
     * @param output
     * @throws IOException
     * @see
     */
    private AppFileVO setAllAppFile(FileVO fileVO) throws Exception {
	
	StorFileVO storFileVO = new StorFileVO();
	String filepath = AppConfig.getProperty("upload_temp", null, "attach") + "/" + fileVO.getCompId() + "";// 파일
	String storpath = filepath = filepath + fileVO.getFileName();
	
	AppFileVO appFileVO = new AppFileVO();
	
	appFileVO.setFileName(fileVO.getDisplayName());

	storFileVO.setFilename(fileVO.getFileName());
	storFileVO.setFileid(fileVO.getFileId());
	storFileVO.setFilepath(storpath);

	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(fileVO.getCompId());
	drmParamVO.setUserId(fileVO.getProcessorId());

	attachService.downloadAttach(storFileVO, drmParamVO);

	// 결재정보
	File file = new File(storpath);//
	URL fileURL = file.toURI().toURL();

	DataHandler handler = new DataHandler(fileURL);

	appFileVO.setContent(handler);

	if (appCode.getProperty("AFT001", "AFT001", "AFT").equals(fileVO.getFileType())
	        || appCode.getProperty("AFT002", "AFT002", "AFT").equals(fileVO.getFileType())
	        || appCode.getProperty("AFT003", "AFT003", "AFT").equals(fileVO.getFileType())) {
	    appFileVO.setFileType("body");
	}

	// 첨부파일
	if (appCode.getProperty("AFT004", "AFT004", "AFT").equals(fileVO.getFileType())
	        || appCode.getProperty("AFT010", "AFT010", "AFT").equals(fileVO.getFileType())) {
	    appFileVO.setFileType("attach");
	}

	return appFileVO;
    }
    
    

    /**
     * <pre> 
     *  파일가져오기
     * </pre>
     * 
     * @param input
     * @param output
     * @throws IOException
     * @see
     */
    private AppFileVO setAppAttachFile(FileVO fileVO) throws Exception {

	StorFileVO storFileVO = new StorFileVO();
	String filepath = AppConfig.getProperty("upload_temp", null, "attach") + "/" + fileVO.getCompId() + "";// 파일
	String storpath = filepath = filepath + fileVO.getFileName();
	
	AppFileVO appFileVO = new AppFileVO();
	
	//파일조회
	//todo
	fileVO = this.selectFile(fileVO);
	
	if(fileVO !=null){
	    
	    if(fileVO.getFileId() !=null){
		storFileVO.setFilename(fileVO.getFileName());
		storFileVO.setFileid(fileVO.getFileId());
		storFileVO.setFilepath(storpath);

		DrmParamVO drmParamVO = new DrmParamVO();
		drmParamVO.setCompId(fileVO.getCompId());
		drmParamVO.setUserId(fileVO.getProcessorId());

		attachService.downloadAttach(storFileVO, drmParamVO);



		// 결재정보
		File file = new File(storpath);//
		URL fileURL = file.toURI().toURL();

		DataHandler handler = new DataHandler(fileURL);

		appFileVO.setFileName(fileVO.getDisplayName());
		appFileVO.setContent(handler);
		// 첨부파일
		if (appCode.getProperty("AFT004", "AFT004", "AFT").equals(fileVO.getFileType())
		        || appCode.getProperty("AFT010", "AFT010", "AFT").equals(fileVO.getFileType())) {
		    appFileVO.setFileType("attach");
		}
	    }
	}
	

	return appFileVO;

    }

    /**
     * <pre> 
     *  첨부된 파일의 내용을 읽어온다
     * </pre>
     * 
     * @param fileVO
     * @return
     * @throws IOException
     * @see
     */
    private StringBuffer getContents(AppFileVO fileVO) throws IOException {

	DataHandler dh;
	InputStream is;
	BufferedReader bf;
	StringBuffer sb = null;
	dh = fileVO.getContent();

	is = dh.getInputStream();

	bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));

	String lineTxt = "";
	sb = new StringBuffer();

	while ((lineTxt = bf.readLine()) != null) {

	    sb.append(lineTxt);

	}

	//logger.debug("getContents" + sb.toString());

	return sb;
    }


    /**
     * <pre> 
     *  결재함 리스트 조회
     * </pre>
     * 
     * @param appReqVO
     * @return AppMenuVO
     * @see
     */
    @SuppressWarnings("unchecked")
    public AppMenuVOs listBox(AppReqVO appReqVO) throws Exception {

	AppMenuVOs appMenuVOs = new AppMenuVOs();
	AppMenuVO appMenuVO;
	Map rsltMap = new HashMap();
	List voList = new ArrayList();
	OptionVO optVO;
	String cnt = "";
	try {
		// 다국어 추가
		List optList = (List) envOptionAPIService.selectOptionGroupListResource(appReqVO.getOrgcode(), "OPTG100", AppConfig.getCurrentLangType());
	    //List optList = (List) envOptionAPIService.selectOptionGroupList(appReqVO.getOrgcode(), "OPTG100");

	    int size = optList.size();

	    for (int i = 0; i < size; i++) {
		optVO = new OptionVO();
		optVO = (OptionVO) optList.get(i);
		if (appCode.getProperty("OPT103", "OPT103", "OPT").equals(optVO.getOptionId())
		        || appCode.getProperty("OPT104", "OPT104", "OPT").equals(optVO.getOptionId())
		        || appCode.getProperty("OPT105", "OPT105", "OPT").equals(optVO.getOptionId())
		        || appCode.getProperty("OPT108", "OPT108", "OPT").equals(optVO.getOptionId())
		        || appCode.getProperty("OPT111", "OPT111", "OPT").equals(optVO.getOptionId())
		        || appCode.getProperty("OPT112", "OPT112", "OPT").equals(optVO.getOptionId())
		        || appCode.getProperty("OPT113", "OPT113", "OPT").equals(optVO.getOptionId())) {

		    // if ("Y".equals(optVO.getUseYn())) {
		    appMenuVO = new AppMenuVO();
		    appMenuVO.setMenuid(optVO.getOptionId());
		    appMenuVO.setMenuname(optVO.getOptionValue());
		    // appMenuVO.setOrgcode(optVO.getCompId());

		    // --------------
		    // 건수 조회
		    // --------------

		    appReqVO.setItemid(optVO.getOptionId());

		    // 갯수조회
		    rsltMap = this.getAppDocList(appReqVO, "N", 1, 9999);
		    cnt = (String) rsltMap.get("cnt");
		    appMenuVO.setDoc_count(Integer.parseInt(cnt));
		    appMenuVO.setParent_menuid("ROOT");
		    appMenuVO.setIs_leaf("Y");
		    appMenuVO.setHas_list("Y");
		    voList.add(appMenuVO);
		    // }
		}

	    }

	    // 등록대장
	    appReqVO.setItemid(appCode.getProperty("OPT201", "OPT201", "OPT"));
	    rsltMap = getAppDocList(appReqVO, "N", 1, 9999);
	    cnt = (String) rsltMap.get("cnt");
	    appMenuVO = new AppMenuVO();
	    appMenuVO.setMenuid(appCode.getProperty("OPT201", "OPT201", "OPT"));
	    appMenuVO.setMenuname(envOptionAPIService.selectOptionText(appReqVO.getOrgcode(), appCode
		    .getProperty("OPT201", "OPT201", "OPT")));
	    // appMenuVO.setOrgcode(appReqVO.getOrgcode());
	    appMenuVO.setDoc_count(Integer.parseInt(cnt));
	    voList.add(appMenuVO);

	    // 일일감사대장
	    appReqVO.setItemid(appCode.getProperty("OPT206", "OPT206", "OPT"));
	    rsltMap = getAppDocList(appReqVO, "N", 1, 9999);
	    cnt = (String) rsltMap.get("cnt");
	    appMenuVO = new AppMenuVO();
	    appMenuVO.setMenuid(appCode.getProperty("OPT206", "OPT206", "OPT"));
	    appMenuVO.setMenuname(envOptionAPIService.selectOptionText(appReqVO.getOrgcode(), appCode
		    .getProperty("OPT206", "OPT206", "OPT")));
	    // appMenuVO.setOrgcode(appReqVO.getOrgcode());
	    appMenuVO.setDoc_count(Integer.parseInt(cnt));
	    voList.add(appMenuVO);

	    appMenuVOs.setRespose_code("success");
	    appMenuVOs.setReqtype(appReqVO.getReqtype());
	    appMenuVOs.setReqdate(DateUtil.getCurrentDate());
	    appMenuVOs.setOrgcode(appReqVO.getOrgcode());
	    appMenuVOs.setUserid(appReqVO.getUserid());

	} catch (Exception e) {
	    appMenuVOs.setRespose_code("fail");
	    appMenuVOs.setError_message("결재함목록 조회시 에러");
	}
	appMenuVOs.setAppMenuVOs(voList);

	return appMenuVOs;
    }


    /**
     * <pre> 
     *  결재함 리스트 조회(모바일
     * </pre>
     * 
     * @param appReqVO
     * @return AppMenuVO
     * @see
     */
    @SuppressWarnings("unchecked")
    public AppMenuVOs listMobileBox(AppReqVO appReqVO) throws Exception {

	AppMenuVOs appMenuVOs = new AppMenuVOs();
	AppMenuVO appMenuVO;
	Map rsltMap = new HashMap();
	List voList = new ArrayList();
	OptionVO optVO;
	String cnt = "";
	try {
		// 다국어 추가
		List optList = (List) envOptionAPIService.selectOptionGroupListResource(appReqVO.getOrgcode(), "OPTG100", AppConfig.getCurrentLangType());
	    // List optList = (List) envOptionAPIService.selectOptionGroupList(appReqVO.getOrgcode(), "OPTG100");

	    int size = optList.size();

	    for (int i = 0; i < size; i++) {
			optVO = new OptionVO();
			optVO = (OptionVO) optList.get(i);
			if (appCode.getProperty("OPT103", "OPT103", "OPT").equals(optVO.getOptionId())
			        || appCode.getProperty("OPT104", "OPT104", "OPT").equals(optVO.getOptionId())		        
			        || appCode.getProperty("OPT112", "OPT112", "OPT").equals(optVO.getOptionId())) {
	
			    // if ("Y".equals(optVO.getUseYn())) {
			    appMenuVO = new AppMenuVO();
			    appMenuVO.setMenuid(optVO.getOptionId());
			    appMenuVO.setMenuname(optVO.getOptionValue());
			    // appMenuVO.setOrgcode(optVO.getCompId());
	
			    // --------------
			    // 건수 조회
			    // --------------
	
			    appReqVO.setItemid(optVO.getOptionId());
	
			    // 갯수조회
			    rsltMap = this.getAppDocList(appReqVO, "Y", 1, 9999);
			    cnt = (String) rsltMap.get("cnt");
			    appMenuVO.setDoc_count(Integer.parseInt(cnt));
			    appMenuVO.setParent_menuid("ROOT");
			    appMenuVO.setIs_leaf("Y");
			    appMenuVO.setHas_list("Y");
			    voList.add(appMenuVO);
			    // }
			}
	    }
	    
	    // 등록대장 (jd.park 2012.06.25 추가)
	    appReqVO.setItemid(appCode.getProperty("OPT201", "OPT201", "OPT"));
	    rsltMap = getAppDocList(appReqVO, "Y", 1, 9999);
	    cnt = (String) rsltMap.get("cnt");
	    appMenuVO = new AppMenuVO();
	    appMenuVO.setMenuid(appCode.getProperty("OPT201", "OPT201", "OPT"));
	    appMenuVO.setMenuname(envOptionAPIService.selectOptionText(appReqVO.getOrgcode(), appCode
		    .getProperty("OPT201", "OPT201", "OPT")));
	    // appMenuVO.setOrgcode(appReqVO.getOrgcode());
	    appMenuVO.setDoc_count(Integer.parseInt(cnt));
	    voList.add(appMenuVO);
	    
	    appMenuVOs.setRespose_code("success");
	    appMenuVOs.setReqtype(appReqVO.getReqtype());
	    appMenuVOs.setReqdate(DateUtil.getCurrentDate());
	    appMenuVOs.setOrgcode(appReqVO.getOrgcode());
	    appMenuVOs.setUserid(appReqVO.getUserid());

	} catch (Exception e) {
	    appMenuVOs.setRespose_code("fail");
	    appMenuVOs.setError_message("결재함목록 조회시 에러");
	}
	appMenuVOs.setAppMenuVOs(voList);

	return appMenuVOs;
    }


    /**
     * <pre> 
     *  결재리스트 조회(포탈....)웹서비스
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    public AppListVOs listDoc(AppReqVO appReqVO) throws Exception {

	AppListVOs appListVOs = new AppListVOs();
	Map appMap = new HashMap();
	try {

	    appMap = getAppDocList(appReqVO, "N", 1, appReqVO.getReqcount());
	    appListVOs.setAppListVOs((List) appMap.get("list"));

	} catch (Exception e) {
	    throw new Exception("조회 처리중 에러가 발생하였습니다.");
	}
	return appListVOs;

    }


    /**
     * <pre> 
     *  결재리스트 조회(mobile....)웹서비스
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    public AppListVOs listAppDoc(AppReqVO appReqVO) throws Exception {

	AppListVOs appListVOs = new AppListVOs();
	Map appMap = new HashMap();
	
	


	try {
	   
	    appMap = getAppDocList(appReqVO, "Y", 1, appReqVO.getReqcount());

	    appListVOs.setRespose_code("success");
	    appListVOs.setReqtype(appReqVO.getReqtype());
	    appListVOs.setReqdate(DateUtil.getCurrentDate());
	    appListVOs.setOrgcode(appReqVO.getOrgcode());
	    appListVOs.setUserid(appReqVO.getUserid());

	    appListVOs.setAppListVOs((List) appMap.get("list"));

	} catch (Exception e) {
	    appListVOs.setRespose_code("fail");
	    appListVOs.setError_message("");
	    // throw new Exception("조회 처리중 에러가 발생하였습니다.");
	}
	return appListVOs;

    }


    /**
     * <pre> 
     *  결재리스트 조회 함수
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    private Map getAppDocList(AppReqVO appReqVO, String mobileYn, int pageNo, int pageCount) throws Exception {

	Map<String, Object> rtnMap = new HashMap();
	List volist = new ArrayList();
	Page page = null;
	logger.debug("#############################");
	logger.debug("      결재함 리스트 조회");
	logger.debug("#############################");

	// ---------------------------
	// 함구분에 따른 서비스호출
	// ---------------------------

	// 사용자 조회
	UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());

	if (userVO == null) {

	    throw new Exception("사용자가 존재하지 않습니다.");
	}

	try {

	    if (appReqVO != null) {

		if (appCode.getProperty("OPT103", "OPT103", "OPT").equals(appReqVO.getItemid())) {// 결재대기함

		    // --------------------------
		    // 결재대기함 서비스 호출
		    // --------------------------
		    logger.debug("결재대기 함");

		    page = this.getApprovalWait(appReqVO, mobileYn, pageNo, pageCount);
		    volist = convertAppList((List) page.getList());

		} else if (appCode.getProperty("OPT104", "OPT104", "OPT").equals(appReqVO.getItemid())) {// 진행문서함

		    // --------------------------
		    // 진행문서함 서비스 호출
		    // --------------------------
		    logger.debug("진행문서함 함");

		    page = this.getListProgressDoc(appReqVO, "N", pageNo, pageCount);
		    volist = convertAppList((List) page.getList());

		} else if (appCode.getProperty("OPT110", "OPT110", "OPT").equals(appReqVO.getItemid())) {// 

		    // --------------------------
		    // 결재완료함 서비스 호출
		    // --------------------------
		    logger.debug("결재완료 함");

		    page = this.getListApprovalComplete(appReqVO, "N", pageNo, pageCount);
		    volist = convertAppList((List) page.getList());

		} else if (appCode.getProperty("OPT105", "OPT105", "OPT").equals(appReqVO.getItemid())) {// 발송대기함

		    // --------------------------
		    // 발송대기함 서비스 호출
		    // --------------------------
		    logger.debug("발송대기함 함");

		    page = getListSendWait(appReqVO, pageNo, pageCount);
		    volist = convertAppList((List) page.getList());

		} else if (appCode.getProperty("OPT108", "OPT108", "OPT").equals(appReqVO.getItemid())) {// 접수대기함

		    // --------------------------
		    // 접수대기함 서비스 호출
		    // --------------------------
		    logger.debug("#############################접수대기함 함");

		    page = getListReceiveWait(appReqVO, pageNo, pageCount);
		    volist = convertEnfList((List) page.getList());

		} else if (appCode.getProperty("OPT111", "OPT111", "OPT").equals(appReqVO.getItemid())) {// 접수완료함

		    // --------------------------
		    // 접수완료함 서비스 호출
		    // --------------------------
		    logger.debug("#############################접수완료함 함");

		    page = getListReceiveComplete(appReqVO, mobileYn, pageNo, pageCount);
		    volist = convertEnfList((List) page.getList());

		} else if (appCode.getProperty("OPT112", "OPT112", "OPT").equals(appReqVO.getItemid())) {// 공람함

		    // --------------------------
		    // 공람함 서비스 호출
		    // --------------------------
		    logger.debug("#############################공람함");

		    page = getListDisplay(appReqVO,  mobileYn, pageNo, pageCount);
		    volist = convertAppList((List) page.getList());

		} else if (appCode.getProperty("OPT113", "OPT113", "OPT").equals(appReqVO.getItemid())) {// 후열함

		    // --------------------------
		    // 후열함 서비스 호출
		    // --------------------------
		    logger.debug("#############################후열함");

		    page = getListRear(appReqVO, pageNo, pageCount);
		    volist = convertAppList((List) page.getList());
		// jth8172 2012 신결재 TF
		} else if (appCode.getProperty("OPT124", "OPT124", "OPT").equals(appReqVO.getItemid())) {// 통보함

		    // --------------------------
		    // 통보함 서비스 호출
		    // --------------------------
		    logger.debug("#############################통보함");

		    page = getListInform(appReqVO, pageNo, pageCount);
		    volist = convertAppList((List) page.getList());
		} else if (appCode.getProperty("OPT118", "OPT118", "OPT").equals(appReqVO.getItemid())) {// 회사문서함
		    
		    // --------------------------
		    // 기관문서함 서비스 호출 //회사와 기관 분리 jth8172 2012 신결재 TF
		    // --------------------------
		    logger.debug("#############################기관문서함");
		    
		    page = getListInstitution(appReqVO, pageNo, pageCount);
		    volist = convertAppList((List) page.getList());

		} else if (appCode.getProperty("OPT125", "OPT125", "OPT").equals(appReqVO.getItemid())) {// 회사문서함
		    
		    // --------------------------
		    // 회사문서함 서비스 호출 //회사와 기관 분리 jth8172 2012 신결재 TF
		    // --------------------------
		    logger.debug("#############################회사문서함");
		    
		    page = getListCompany(appReqVO, pageNo, pageCount);
		    volist = convertAppList((List) page.getList());

		} else if (appCode.getProperty("OPT201", "OPT201", "OPT").equals(appReqVO.getItemid())) {// 등록대장

		    // --------------------------
		    // 등록대장 서비스 호출
		    // --------------------------
		    logger.debug("#############################등록대장");
		    page = getListDocRegist(appReqVO, mobileYn, pageNo, pageCount);
		    volist = convertAppList((List) page.getList());

		} else if (appCode.getProperty("OPT206", "OPT206", "OPT").equals(appReqVO.getItemid())) {// 일일감사대장

		    // --------------------------
		    // 일일감사대장 서비스 호출
		    // --------------------------
		    logger.debug("#############################일일감사대장");
		    page = getListInspecOpen(appReqVO, pageNo, pageCount);
		    volist = convertAppList((List) page.getList());
		} else if (appReqVO.getItemid().indexOf(appCode.getProperty("OPT206", "OPT206", "OPT")) != 1) {// 공람게시판

		    // --------------------------
		    // 공람게시판 서비스 호출
		    // --------------------------
		    logger.debug("#############################공람게시판");
		    page = getListDisplayNotice(appReqVO, pageNo, pageCount);
		    volist = convertPubPostList((List) page.getList());
		}
		rtnMap.put("cnt", String.valueOf(page.getTotalCount()));
		rtnMap.put("list", volist);
	    }

	} catch (Exception e) {
	    throw new Exception("조회 처리중 에러가 발생하였습니다.");
	}
	return rtnMap;

    }
    
    /**
     * <pre> 
     *  결재count 조회 함수
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @throws Exception
     * @see
     */
    @SuppressWarnings("unchecked")
    private Map getAppDocCount(AppReqVO appReqVO, String mobileYn, int pageNo, int pageCount) throws Exception {

	Map<String, Object> rtnMap = new HashMap();
	int resultCount = 0;
	logger.debug("#############################");
	logger.debug("      결재함 count 조회");
	logger.debug("#############################");

	// ---------------------------
	// 함구분에 따른 서비스호출
	// ---------------------------

	// 사용자 조회
	UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());

	if (userVO == null) {

	    throw new Exception("사용자가 존재하지 않습니다.");
	}

	try {

	    if (appReqVO != null) {

		if (appCode.getProperty("OPT103", "OPT103", "OPT").equals(appReqVO.getItemid())) {// 결재대기함

		    // --------------------------
		    // 결재대기함 서비스 호출
		    // --------------------------
		    logger.debug("결재대기 함(count)");

		    resultCount = this.getApprovalWaitCount(appReqVO, mobileYn, pageNo, pageCount);

		} else if (appCode.getProperty("OPT104", "OPT104", "OPT").equals(appReqVO.getItemid())) {// 진행문서함

		    // --------------------------
		    // 진행문서함 서비스 호출
		    // --------------------------
		    logger.debug("진행문서함 함(count)");

		    resultCount = this.getListProgressDocCount(appReqVO, "N", pageNo, pageCount);

		} else if (appCode.getProperty("OPT108", "OPT108", "OPT").equals(appReqVO.getItemid())) {// 접수대기함

		    // --------------------------
		    // 접수대기함 서비스 호출
		    // --------------------------
		    logger.debug("#############################접수대기함 함(count)");

		    resultCount = getListReceiveWaitCount(appReqVO, pageNo, pageCount);

		} else if (appCode.getProperty("OPT112", "OPT112", "OPT").equals(appReqVO.getItemid())) {// 공람함

		    // --------------------------
		    // 공람함 서비스 호출
		    // --------------------------
		    logger.debug("#############################공람함(count)");

		    resultCount = getListDisplayCount(appReqVO,  mobileYn, pageNo, pageCount);

		} else if (appCode.getProperty("OPT113", "OPT113", "OPT").equals(appReqVO.getItemid())) {// 후열함

		    // --------------------------
		    // 후열함 서비스 호출
		    // --------------------------
		    logger.debug("#############################후열함(count)");

		    resultCount = getListRearCount(appReqVO, pageNo, pageCount);

		} else if (appCode.getProperty("OPT124", "OPT124", "OPT").equals(appReqVO.getItemid())) {// 통보함

		    // --------------------------
		    // 통보함 서비스 호출
		    // --------------------------
		    logger.debug("#############################통보함(count)");

		    resultCount = getListInformCount(appReqVO, pageNo, pageCount);

		} else if (appCode.getProperty("OPT201", "OPT201", "OPT").equals(appReqVO.getItemid())) {// 등록대장

		    // --------------------------
		    // 등록대장 서비스 호출
		    // --------------------------
		    logger.debug("#############################등록대장(count)");
		    resultCount = getListDocRegistCount(appReqVO, "N", pageNo, pageCount);
		
		}
		rtnMap.put("cnt", String.valueOf(resultCount));
	    }

	} catch (Exception e) {
	    throw new Exception("조회(count) 처리중 에러가 발생하였습니다.");
	}
	return rtnMap;

    }


    /**
     * <pre> 
     *  결재문서 갯수 조회
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    public AppItemCountVO countDoc(AppReqVO appReqVO) throws Exception {
	AppItemCountVO appItemCountVO = new AppItemCountVO();

	try {

	    String cnt = "";
	    Map rsltMap = this.getAppDocList(appReqVO, "N", 1, appReqVO.getReqcount());

	    cnt = (String) rsltMap.get("cnt");
	    appItemCountVO = (AppItemCountVO) Transform.transformVO(appReqVO, appItemCountVO);
	    appItemCountVO.setDoc_count(Integer.parseInt(cnt));
	    appItemCountVO.setRespose_code("success");
	    appItemCountVO.setReqtype("countApprovalItem");
	    appItemCountVO.setReqtype("" + DateUtil.getCurrentDate());
	    appItemCountVO.setRespose_code("success");

	} catch (Exception e) {

	    appItemCountVO.setRespose_code("fail");
	    appItemCountVO.setError_message("결재문서갯수 조회 오류");

	}
	return appItemCountVO;
    }
    
    /**
     * <pre> 
     *  결재문서 갯수 조회(포탈)
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    public AppItemCountVO countPortalDoc(AppReqVO appReqVO) throws Exception {
	AppItemCountVO appItemCountVO = new AppItemCountVO();

	try {

	    String cnt = "";
	    Map rsltMap = this.getAppDocCount(appReqVO, "N", 1, appReqVO.getReqcount());

	    cnt = (String) rsltMap.get("cnt");
	    appItemCountVO = (AppItemCountVO) Transform.transformVO(appReqVO, appItemCountVO);
	    appItemCountVO.setDoc_count(Integer.parseInt(cnt));
	    appItemCountVO.setRespose_code("success");
	    appItemCountVO.setReqtype("countApprovalItem");
	    appItemCountVO.setReqtype("" + DateUtil.getCurrentDate());
	    appItemCountVO.setRespose_code("success");

	} catch (Exception e) {

	    appItemCountVO.setRespose_code("fail");
	    appItemCountVO.setError_message("결재문서갯수 조회 오류");

	}
	return appItemCountVO;
    }


    /**
     * <pre> 
     *  결재상세내용 조회
     * </pre>
     * 
     * @param appReqVO
     * @return AppDetailVO
     * @see
     */
    public AppDetailVO selectDocInfo(AppReqVO appReqVO) throws Exception {

	AppDetailVO appDetailVO = new AppDetailVO();

	try {

	    AppDocVO appdocVO = approvalService.selectAppDoc(appReqVO.getOrgcode(), appReqVO.getItemid());

	    appDetailVO.setReqtype("getApprovalDetail");// 구분
	    appDetailVO.setReqdate(DateUtil.getCurrentDate());// 일자
	    appDetailVO.setOrgcode(appReqVO.getOrgcode());// 회사코드
	    appDetailVO.setUserid(appReqVO.getUserid());// 사용자
	    appDetailVO.setTitle(appdocVO.getTitle());// 문서제목
	    appDetailVO.setDocid(appdocVO.getDocId());// 문서id
	    appDetailVO.setAppline(appdocVO.getAppLine());// 결재라인
	    appDetailVO.setAppstatus(appdocVO.getDocState());// 문서상태
	    appDetailVO.setContent(getAllFileVOs(appdocVO.getFileInfo()));//

	    appDetailVO.setRespose_code("success");

	} catch (Exception e) {

	    appDetailVO.setRespose_code("fail");
	    appDetailVO.setError_message("문서조회 오류");
	}

	return appDetailVO;
    }


    /**
     * <pre> 
     *  결재상세내용 조회(모바일)
     *  본문은 html파일을 조회한다.
     * </pre>
     * 
     * @param appReqVO
     * @return AppDetailVO
     * @see
     */
    public AppDetailVO selectDocInfoMobile(AppReqVO appReqVO) throws Exception {

	AppDetailVO appDetailVO = new AppDetailVO();
	String DPI001 = appCode.getProperty("DPI001", "DPI001", "DPI"); // 생산
	String DPI002 = appCode.getProperty("DPI002", "DPI002", "DPI"); // 접수
	String docType = "APP".equals((appReqVO.getItemid()).substring(0, 3)) ? DPI001 : DPI002;

	try {
	    
	    appDetailVO.setReqtype("getApprovalDetail");// 구분
	    appDetailVO.setReqdate(DateUtil.getCurrentDate());// 일자
	    appDetailVO.setOrgcode(appReqVO.getOrgcode());// 회사코드
	    appDetailVO.setUserid(appReqVO.getUserid());// 사용자
	    if (DPI001.equals(docType)) {
		
		AppDocVO appdocVO = approvalService.selectAppDoc(appReqVO.getOrgcode(), appReqVO.getItemid());	    
		appDetailVO.setTitle(appdocVO.getTitle());// 문서제목
		appDetailVO.setDocid(appdocVO.getDocId());// 문서id
		appDetailVO.setAppline(appdocVO.getAppLine());// 결재라인
		appDetailVO.setAppstatus(appdocVO.getDocState());// 문서상태
		
		appDetailVO.setContent(getFileVOs(appdocVO.getFileInfo(),"Y"));// 문서내용
		appDetailVO.setDocType(appdocVO.getDocType()); // 문서유형
		appDetailVO.setElectronDocYn(appdocVO.getElectronDocYn()); // 전자문서여부
		
	    } else {
		
		EnfDocVO enfdocVO = new EnfDocVO();
		EnfLineVO enflineVO = new EnfLineVO();
		Map<String, String> map = new HashMap<String, String>();
		map.put("docId", appReqVO.getItemid());
		map.put("compId", appReqVO.getOrgcode());
		map.put("tempYn", "N");
		//map.put("fileType", AFT001);
		
		enfdocVO.setCompId(appReqVO.getOrgcode());
		enfdocVO.setDocId(appReqVO.getItemid());
		enfdocVO = enforceAppService.selectEnfDoc(enfdocVO);
		enflineVO.setCompId(appReqVO.getOrgcode());
		enflineVO.setDocId(appReqVO.getItemid());
		
		appDetailVO.setTitle(enfdocVO.getTitle());
		appDetailVO.setDocid(enfdocVO.getDocId());
		appDetailVO.setEnfline((List<EnfLineVO>) enfLineService.getList(enflineVO));
		appDetailVO.setAppstatus(enfdocVO.getDocState());
		//appDetailVO.setContent(getFileVOs(enfdocVO.getFileInfos(),"Y"));
		appDetailVO.setContent(getFileVOs(appComService.listFile(map), "Y"));
		appDetailVO.setDocType(enfdocVO.getDocType());
		appDetailVO.setElectronDocYn(enfdocVO.getElectronDocYn());
	    }

	    appDetailVO.setRespose_code("success");

	} catch (Exception e) {

	    appDetailVO.setRespose_code("fail");
	    appDetailVO.setError_message("문서상세조회 오류");
	}

	return appDetailVO;
    }


    /**
     * 결재첨부파일 조회
     */
    public AppAttachVO getAttachFile(AppReqVO appReqVO) throws Exception {

	AppAttachVO appAttachVO = new AppAttachVO();
	
	try {

	    appAttachVO.setReqtype("getApprovalAttach");// 구분
	    appAttachVO.setReqdate(DateUtil.getCurrentDate());// 일자
	    appAttachVO.setOrgcode(appReqVO.getOrgcode());// 회사코드
	    appAttachVO.setUserid(appReqVO.getUserid());// 사용자
	    appAttachVO.setDocid(appReqVO.getItemid());// 문서id
	    appAttachVO.setFile(getAttachFileVOs(appReqVO));// 첨부파일

	    appAttachVO.setRespose_code("success");

	} catch (Exception e) {

	    appAttachVO.setDocid(appReqVO.getItemid());
	    appAttachVO.setOrgcode(appReqVO.getOrgcode());
	    appAttachVO.setReqdate(DateUtil.getCurrentDate());
	    appAttachVO.setReqtype(appReqVO.getReqtype());
	    appAttachVO.setRespose_code("fail");
	    appAttachVO.setError_message("첨부파일조회오류");
	}

	return appAttachVO;
    }


    /**
     * <pre>
     * 파일가져오기
     * </pre>
     * 
     * @param files
     * @return
     * @throws Exception
     * @see
     */
    private AppFileVOs getFileVOs(List<FileVO> files, String mobileYn) throws Exception {

	AppFileVOs appFileVOs = new AppFileVOs();
	AppFileVO appFileVO;
	FileVO filevo;
	boolean ishtmlFileYn= isHtmlFiles(files);
	
	List<AppFileVO> filelist = new ArrayList<AppFileVO>();
	if (files != null) {
	    for (int i = 0; i < files.size(); i++) {
		filevo = new FileVO();
		filevo = (FileVO) files.get(i);
		appFileVO = new AppFileVO();
		if(ishtmlFileYn){
		    //한글파일
		    if (appCode.getProperty("AFT001", "AFT001", "AFT").equals(filevo.getFileType())){
			continue;
		    }
		    
		}
		appFileVO = setAppFile(filevo,mobileYn);
			
		filelist.add(appFileVO);

	    }
	}
	appFileVOs.setFileVOs(filelist);
	
	return appFileVOs;
    }

   
    
    /**
     * <pre>
     * 파일가져오기
     * </pre>
     * 
     * @param files
     * @return
     * @throws Exception
     * @see
     */
    private AppFileVOs getAllFileVOs(List<FileVO> files) throws Exception {
		AppFileVOs appFileVOs = new AppFileVOs();
		AppFileVO appFileVO;
		FileVO filevo;
		
		List<AppFileVO> filelist = new ArrayList<AppFileVO>();
		if (files != null) {
		    for (int i = 0; i < files.size(); i++) {
			filevo = new FileVO();
			filevo = (FileVO) files.get(i);
			appFileVO = new AppFileVO();
			appFileVO = setAllAppFile(filevo);
			filelist.add(appFileVO);
		    }
		}
		appFileVOs.setFileVOs(filelist);
		
		return appFileVOs;
    }
   
    /**
     * <pre>
     * html파일여부 체크
     * </pre>
     * 
     * @param files
     * @return
     * @throws Exception
     * @see
     */
    private boolean isHtmlFiles(List<FileVO> files) throws Exception {
		FileVO filevo;
		if (files != null) {
		    for (int i = 0; i < files.size(); i++) {
				filevo = new FileVO();
				filevo = (FileVO) files.get(i);
				//html파일
				if (appCode.getProperty("AFT002", "AFT002", "AFT").equals(filevo.getFileType())
				        || appCode.getProperty("AFT003", "AFT003", "AFT").equals(filevo.getFileType())) {
		
				    	return true;
				
				}
		    }
		}
		return false;
    }

    /**
     * <pre>
     * 본문파일가져오기(html)
     * </pre>
     * 
     * @param files
     * @return
     * @throws Exception
     * @see
     */
    private AppFileVO getAttachFileVOs(AppReqVO appReqVO) throws Exception {
		AppFileVO appFileVO = new AppFileVO();
		FileVO filevo = new FileVO();
		
		filevo.setCompId(appReqVO.getOrgcode());//회사코드
		filevo.setDocId(appReqVO.getItemid());//문서id
		filevo.setFileId(appReqVO.getFileId());
		
		if (appReqVO.getFileId() != null) {
			appFileVO = setAppAttachFile(filevo);
		}
		
		return appFileVO;
    }


    /**
     * <pre> 
     *  결재처리 요청
     * </pre>
     * 
     * appFileVOs
     * 
     * @param appReqVO
     * @return AppDetailVO
     * @see
     */
    public AppResultVO processAppDoc(HeaderVO headerVO, AppFileVOs fileVOs) throws Exception {
		logger.debug("결재처리 요청 서비스 호출");
		logger.printVO(headerVO);
		logger.debug(fileVOs.getFileVOs().size());
	
		for (int i = 0; i < fileVOs.getFileVOs().size(); i++) {
			getContents(fileVOs.getFileVOs().get(i));
		}

		return null;
    }


    /**
     * <pre> 
     *  결재처리 요청(모바일)
     * </pre>
     * 
     * appFileVOs
     * 
     * @param appReqVO
     * @return AppDetailVO
     * @see
     */
    public AppResultVO processDoc(AppActionVO appActionVO) throws Exception {

		logger.debug("결재처리 요청 서비스 호출");
		
		boolean checkRes = false;
		// 사용자 조회
	    UserVO userVO = orgService.selectUserByLoginId(appActionVO.getUserid());
	    
		String encryptedPwd = EnDecode.EncodeBySType(appActionVO.getUserpassword());
		if (orgService.compareApprovalPassword(userVO.getUserUID(), encryptedPwd)) {
		    checkRes = true;
		}
		
		AppResultVO appResultVO = new AppResultVO();
		if (checkRes) {
		    appResultVO.setReqtype(appActionVO.getReqtype());
		    appResultVO.setReqdate(DateUtil.getCurrentDate());
		    appResultVO.setOrgcode(appActionVO.getOrgcode());
		    appResultVO.setUserid(appActionVO.getUserid());
	
		    try {	    	
				appActionVO.setUserid(userVO.getUserUID());
				approvalService.processMobile(appActionVO);
		
				appResultVO.setRespose_code("success");
		    } catch (Exception e) {
				appResultVO.setRespose_code("fail");
				appResultVO.setError_message(e.getMessage().toString());
		    }
		} else {
		    appResultVO.setReqtype(appActionVO.getReqtype());
		    appResultVO.setReqdate(DateUtil.getCurrentDate());
		    appResultVO.setRespose_code("fail");
		    appResultVO.setError_message("비밀번호 인증실패");
		}
		
		return appResultVO;
    }


    /**
     * <pre> 
     *  결재대기함 조회
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @throws Exception
     * @see
     */
    private Page getApprovalWait(AppReqVO appReqVO, String mobileYn, int pageNo, int pagecount) throws Exception {

		SearchVO searchVO = null;
	
		String startDate = "";
		String endDate = "";
		String searchType = "";
		String searchWord = "";
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
		String docAppState = "";
	
		// --------------------------
		// 결재대기함 서비스 호출
		// --------------------------
		logger.debug("#############################결재대기 함");
		if("N".equals(mobileYn)) {
		    docAppState = ListUtil.TransString("APP100,APP200,APP250,APP300,APP302,APP305,APP350,,APP360,APP362,APP365,APP370,APP400,APP402,APP405,APP500,APP550","APP");
		} else{
		    docAppState = ListUtil.TransString("APP100,APP200,APP300,APP302,APP305,APP360,APP362,APP365,APP400,APP402,APP405,APP500","APP");
		}
		String docAppStateDept = ListUtil.TransString("APP201,APP301,APP351,APP401", "APP");
		String docEnfState = ListUtil.TransString("ENF400,ENF500", "ENF");
		String docEnfReciveState = ListUtil.TransString("ENF100,ENF200,ENF250", "ENF");
		String docEnfDisplyWaitState = ListUtil.TransString("ENF300,ENF310", "ENF");
		String procType = ListUtil.TransString("APT003,APT004", "APT");
		String apprProcType = appCode.getProperty("APT003", "APT003", "APT");
		String processorProcType = appCode.getProperty("APT004", "APT004", "APT");
		String docReturnAppState = appCode.getProperty("APP110", "APP110", "APP");
	
		String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
		String deptAdminYn = "N";
	
		String roleCodes = StringUtil.null2str(userVO.getRoleCodes());
		
		if (roleCodes.indexOf(roleId11) != -1 ) {
		    deptAdminYn = "Y";
		}
		
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
	
		// 문서수신대상
		String opt333 = appCode.getProperty("OPT333", "OPT333","OPT"); 
		String recvObject = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(),opt333);
		
		String deptId = getDeptId(userVO.getDeptID());
		
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setDeptId(deptId);// 부서코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setDocAppState(docAppState);
		searchVO.setDocAppStateDept(docAppStateDept);
		searchVO.setDocReturnAppState(docReturnAppState);
		searchVO.setDocEnfState(docEnfState);
		searchVO.setDocEnfReciveState(docEnfReciveState);
		searchVO.setDocEnfDisplyWaitState(docEnfDisplyWaitState);
		searchVO.setProcType(procType);
		searchVO.setApprProcType(apprProcType);
		searchVO.setProcessorProcType(processorProcType);
		searchVO.setDeptAdminYn(deptAdminYn);
		searchVO.setMobileYn(mobileYn);
		searchVO.setRecvObject(recvObject);
		if(!"".equals(StringUtil.null2str(appReqVO.getFromymd()))){
		    searchVO.setSearchType("searchDraftDate");
		    searchVO.setStartDate(appReqVO.getFromymd());
		    searchVO.setEndDate(appReqVO.getToymd());
		}
		Page page = listApprovalService.listApprovalWait(searchVO, pageNo, pagecount);
	
		return page;
    }
    
    /**
     * <pre> 
     *  결재대기함 조회(count)
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @throws Exception
     * @see
     */
    private int getApprovalWaitCount(AppReqVO appReqVO, String mobileYn, int pageNo, int pagecount) throws Exception {
		SearchVO searchVO = null;
	
		String startDate = "";
		String endDate = "";
		String searchType = "";
		String searchWord = "";
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
		String docAppState = "";
	
		// --------------------------
		// 결재대기함 서비스 호출
		// --------------------------
		logger.debug("#############################결재대기 함");
		if("N".equals(mobileYn)) {
		    docAppState = ListUtil.TransString("APP100,APP200,APP250,APP300,APP302,APP305,APP350,,APP360,APP362,APP365,APP370,APP400,APP402,APP405,APP500,APP550","APP");
		} else{
		    docAppState = ListUtil.TransString("APP100,APP200,APP300,APP302,APP305,APP360,APP362,APP365,APP400,APP402,APP405,APP500","APP");
		}
		
		String docAppStateDept = ListUtil.TransString("APP201,APP301,APP351,APP401", "APP");
		String docEnfState = ListUtil.TransString("ENF400,ENF500", "ENF");
		String docEnfReciveState = ListUtil.TransString("ENF100,ENF200,ENF250", "ENF");
		String docEnfDisplyWaitState = ListUtil.TransString("ENF300,ENF310", "ENF");
		String procType = ListUtil.TransString("APT003,APT004", "APT");
		String apprProcType = appCode.getProperty("APT003", "APT003", "APT");
		String processorProcType = appCode.getProperty("APT004", "APT004", "APT");
		String docReturnAppState = appCode.getProperty("APP110", "APP110", "APP");
	
		String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
		String deptAdminYn = "N";
	
		String roleCodes = StringUtil.null2str(userVO.getRoleCodes());
		
		if (roleCodes.indexOf(roleId11) != -1 ) {
		    deptAdminYn = "Y";
		}
		
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
	
		// 문서수신대상
		String opt333 = appCode.getProperty("OPT333", "OPT333","OPT"); 
		String recvObject = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(),opt333);
	
		//
		
		String deptId = getDeptId(userVO.getDeptID());
		
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setDeptId(deptId);// 부서코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setDocAppState(docAppState);
		searchVO.setDocAppStateDept(docAppStateDept);
		searchVO.setDocReturnAppState(docReturnAppState);
		searchVO.setDocEnfState(docEnfState);
		searchVO.setDocEnfReciveState(docEnfReciveState);
		searchVO.setDocEnfDisplyWaitState(docEnfDisplyWaitState);
		searchVO.setProcType(procType);
		searchVO.setApprProcType(apprProcType);
		searchVO.setProcessorProcType(processorProcType);
		searchVO.setDeptAdminYn(deptAdminYn);
		searchVO.setMobileYn(mobileYn);
		searchVO.setRecvObject(recvObject);
		if(!"".equals(StringUtil.null2str(appReqVO.getFromymd()))){
		    searchVO.setSearchType("searchDraftDate");
		    searchVO.setStartDate(appReqVO.getFromymd());
		    searchVO.setEndDate(appReqVO.getToymd());
		}
		int resultCount = listApprovalService.listApprovalWaitCount(searchVO);
	
		return resultCount;
    }


    /**
     * <pre> 
     *  진행문서함
     * </pre>
     * 
     * @return
     * @see
     */
    private Page getListProgressDoc(AppReqVO appReqVO, String mobileYn, int pageNo, int pagecount) throws Exception {
		logger.debug("#############################진행문서함 함");
	
		SearchVO searchVO = null;
	
		String startDate = "";
		String endDate = "";
		String searchType = "";
		String searchWord = "";
	
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
	
		// String docAppState =
		// ListUtil.TransString("APP200,APP250,APP300,APP302,APP305,APP350,APP400,APP402,APP405,APP500",
		// "APP");
		String docAppState 	= ListUtil.TransString("APP010,APP100,APP110", "APP");
		String docAppStateDept 	= ListUtil.TransString("APP201,APP301,APP351,APP401", "APP");
		String docEnfState 	= ListUtil.TransString("ENF400,ENF500", "ENF");
		String procType		= ListUtil.TransString("APT003,APT004","APT");
	
		String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
		String processorProcType = appCode.getProperty("APT004", "APT004", "APT");
		String deptAdminYn = "N";
	
		// UserVO userVO =
		// orgService.selectUserByUserId(appReqVO.getUserid());
		String roleCodes = StringUtil.null2str(userVO.getRoleCodes());
	
		if (roleCodes.indexOf(roleId11) != -1) {
		    deptAdminYn = "Y";
		}
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
		
		String deptId = getDeptId(userVO.getDeptID());
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setDeptId(deptId);// 부서코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setDocAppState(docAppState);
		searchVO.setDocAppStateDept(docAppStateDept);
		searchVO.setDocEnfState(docEnfState);
		searchVO.setDeptAdminYn(deptAdminYn);
		searchVO.setMobileYn(mobileYn);
		searchVO.setProcessorProcType(processorProcType);
		searchVO.setProcType(procType);
		if(!"".equals(StringUtil.null2str(appReqVO.getFromymd()))){
		    searchVO.setStartDate(appReqVO.getFromymd());
		    searchVO.setEndDate(appReqVO.getToymd());
		}
	
		Page page = listApprovalService.listProgressDoc(searchVO, pageNo, pagecount);
	
		return page;
    }
    
    
    /**
     * <pre> 
     *  진행문서함(건수)
     * </pre>
     * 
     * @return
     * @see
     */
    private int getListProgressDocCount(AppReqVO appReqVO, String mobileYn, int pageNo, int pagecount) throws Exception {

		logger.debug("#############################진행문서함 함(count)");
	
		SearchVO searchVO = null;
	
		String startDate = "";
		String endDate = "";
		String searchType = "";
		String searchWord = "";
	
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
	
		// String docAppState =
		// ListUtil.TransString("APP200,APP250,APP300,APP302,APP305,APP350,APP400,APP402,APP405,APP500",
		// "APP");
		String docAppState 	= ListUtil.TransString("APP010,APP100,APP110", "APP");
		String docAppStateDept 	= ListUtil.TransString("APP201,APP301,APP351,APP401", "APP");
		String docEnfState 	= ListUtil.TransString("ENF400,ENF500", "ENF");
		String procType		= ListUtil.TransString("APT003,APT004","APT");
	
		String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
		String processorProcType = appCode.getProperty("APT004", "APT004", "APT");
		String deptAdminYn = "N";
	
		// UserVO userVO =
		// orgService.selectUserByUserId(appReqVO.getUserid());
		String roleCodes = StringUtil.null2str(userVO.getRoleCodes());
	
		if (roleCodes.indexOf(roleId11) != -1) {
		    deptAdminYn = "Y";
		}
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
		
		String deptId = getDeptId(userVO.getDeptID());
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setDeptId(deptId);// 부서코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setDocAppState(docAppState);
		searchVO.setDocAppStateDept(docAppStateDept);
		searchVO.setDocEnfState(docEnfState);
		searchVO.setDeptAdminYn(deptAdminYn);
		searchVO.setMobileYn(mobileYn);
		searchVO.setProcessorProcType(processorProcType);
		searchVO.setProcType(procType);
		if(!"".equals(StringUtil.null2str(appReqVO.getFromymd()))){
		    searchVO.setStartDate(appReqVO.getFromymd());
		    searchVO.setEndDate(appReqVO.getToymd());
		}
	
		int resultCount = listApprovalService.listProgressDocCount(searchVO);
	
		return resultCount;
    }


    /**
     * <pre> 
     *  발송대기함 조회처리
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @throws Exception
     * @see
     */
    private Page getListSendWait(AppReqVO appReqVO, int pageNo, int pagecount) throws Exception {

		SearchVO searchVO = null;
	
		String startDate = "";
		String endDate = "";
		String searchType = "";
		String searchWord = "";
	
		String docAppState = ListUtil.TransString("APP610,APP650,APP660", "APP");
		String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
		String deptAdminYn = "N";
	
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
	
		// UserVO userVO =
		// orgService.selectUserByUserId(appReqVO.getUserid());
		String roleCodes = StringUtil.null2str(userVO.getRoleCodes());
	
		if (roleCodes.indexOf(roleId11) != -1 ) {
		    deptAdminYn = "Y";
		}
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
		
		String deptId = getDeptId(userVO.getDeptID());
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setDeptId(deptId);// 부서코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setDeptAdminYn(deptAdminYn);
		searchVO.setDocAppState(docAppState);
	
		Page page = listSendService.listSendWait(searchVO, pageNo, pagecount);
	
		return page;
    }


    /**
     * 접수대기함조회
     * 
     * <pre> 
     *  접수대기함처리
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @throws Exception
     * @see
     */
    private Page getListReceiveWait(AppReqVO appReqVO, int pageNo, int pagecount) throws Exception {

		SearchVO searchVO = null;
	
		String searchType = "";
		String searchWord = "";
	
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
	
		String docEnfState = ListUtil.TransString("ENF100,ENF200,ENF250", "ENF");
		String enfType = ListUtil.TransString("DET002", "DET");
		String enfTypeAdd = ListUtil.TransString("DET003", "DET");
	
		String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
		String deptAdminYn = "N";
	
		// UserVO userVO =
		// orgService.selectUserByUserId(appReqVO.getUserid());
		String roleCodes = StringUtil.null2str(userVO.getRoleCodes());
	
		if (roleCodes.indexOf(roleId11) != -1) {
		    deptAdminYn = "Y";
		}
	
		String receiveAuthYn = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT341", "OPT341", "OPT"));
	
		if ("2".equals(receiveAuthYn)) {
		    deptAdminYn = "Y";
		}
	
		String deptId = ListUtil.TransString(getDeptId(userVO.getDeptID()));
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setDeptId(deptId);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setDeptAdminYn(deptAdminYn);
		searchVO.setDocEnfState(docEnfState);
		searchVO.setEnfType(enfType);
		searchVO.setEnfTypeAdd(enfTypeAdd);
	
		Page page = listReceiveService.listReceiveWait(searchVO, pageNo, pagecount);
	
		return page;
    }
    
    /**
     * 접수대기함조회(count)
     * 
     * <pre> 
     *  접수대기함처리(count)
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @throws Exception
     * @see
     */
    private int getListReceiveWaitCount(AppReqVO appReqVO, int pageNo, int pagecount) throws Exception {

		SearchVO searchVO = null;
	
		String searchType = "";
		String searchWord = "";
	
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
	
		String docEnfState = ListUtil.TransString("ENF100,ENF200,ENF250", "ENF");
		String enfType = ListUtil.TransString("DET002", "DET");
		String enfTypeAdd = ListUtil.TransString("DET003", "DET");
	
		String roleId11 = AppConfig.getProperty("role_doccharger", "", "role");
		String deptAdminYn = "N";
	
		// UserVO userVO =
		// orgService.selectUserByUserId(appReqVO.getUserid());
		String roleCodes = StringUtil.null2str(userVO.getRoleCodes());
	
		if (roleCodes.indexOf(roleId11) != -1) {
		    deptAdminYn = "Y";
		}
	
		String receiveAuthYn = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT341", "OPT341", "OPT"));
	
		if ("2".equals(receiveAuthYn)) {
		    deptAdminYn = "Y";
		}
		String deptId = ListUtil.TransString(getDeptId(userVO.getDeptID()));
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setDeptId(deptId);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setDeptAdminYn(deptAdminYn);
		searchVO.setDocEnfState(docEnfState);
		searchVO.setEnfType(enfType);
		searchVO.setEnfTypeAdd(enfTypeAdd);
	
		int resultCount = listReceiveService.listReceiveWaitCount(searchVO);
	
		return resultCount;
    }


    /**
     * <pre> 
     *  접수완료함
     * </pre>
     * 
     * @param appReqVO
     * @param pageNo
     * @param pagecount
     * @return
     * @throws Exception
     * @see
     */
    private Page getListReceiveComplete(AppReqVO appReqVO, String mobileYn, int pageNo, int pagecount) throws Exception {

		SearchVO searchVO = null;
	
		String searchType = "";
		String searchWord = "";
		String startDate = "";
		String endDate = "";
	
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
		
		String deptId = getDeptId(userVO.getDeptID());
		
		String docEnfState = ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600", "ENF");
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setDeptId(deptId);// 부서코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setDocEnfState(docEnfState);
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		if(!"".equals(StringUtil.null2str(appReqVO.getFromymd()))){
		    searchVO.setStartDate(appReqVO.getFromymd());
		    searchVO.setEndDate(appReqVO.getToymd());
		}
		searchVO.setMobileYn(mobileYn);
		
		//상세조건
		searchVO.setStartDocNum("");
		searchVO.setEndDocNum("");
		searchVO.setBindingId("");
		searchVO.setBindingName("");
		searchVO.setSearchElecYn("");
		searchVO.setSearchNonElecYn("");
		searchVO.setSearchDetType("");
		searchVO.setDetailSearchYn("N");
		searchVO.setDocType("");
		//상세조건끝
	
		Page page = listCompleteService.listReceiveComplete(searchVO, pageNo, pagecount);
	
		return page;
    }


    /**
     * <pre> 
     *  공람함
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @throws Exception
     * @see
     */
    private Page getListDisplay(AppReqVO appReqVO, String mobileYn, int pageNo, int pagecount) throws Exception {

		SearchVO searchVO = null;
	
		String startDate 	= "";
		String endDate 		= "";
		String searchType 	= "";
		String searchWord 	= "";
	
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
	
		String OptAppDocDisplay = appCode.getProperty("OPT334", "OPT334", "OPT");
		String OptEnfDocDisplay = appCode.getProperty("OPT335", "OPT335", "OPT");
		String optAppDocDpiCode = appCode.getProperty("DPI001", "DPI001", "DPI");
		String optEnfDocDpiCode = appCode.getProperty("DPI002", "DPI002", "DPI");
	
		String docEnfState 	= ListUtil.TransString("ENF600", "ENF");
		String docAppState 	= ListUtil.TransString("APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690", "APP");
		String docAppStateDept 	= ListUtil.TransString("APP200,APP201,APP300,APP301,APP302,APP305,APP400,APP401,APP402,APP405,APP500", "APP");
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
	
		OptionVO OptAppDocDisplayVO = this.envOptionAPIService.selectOption(appReqVO.getOrgcode(), OptAppDocDisplay);
		OptionVO OptEnfDocDisplayVO = this.envOptionAPIService.selectOption(appReqVO.getOrgcode(), OptEnfDocDisplay);
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setOptAppDocDisplayYn(OptAppDocDisplayVO.getUseYn());
		searchVO.setOptAppDocDisplayInfo(OptAppDocDisplayVO.getOptionValue());
		searchVO.setOptEnfDocDisplayYn(OptEnfDocDisplayVO.getUseYn());
		searchVO.setOptEnfDocDisplayInfo(OptEnfDocDisplayVO.getOptionValue());
		searchVO.setOptAppDocDpiCode(optAppDocDpiCode);
		searchVO.setOptEnfDocDpiCode(optEnfDocDpiCode);
		searchVO.setDocAppState(docAppState);
		searchVO.setDocEnfState(docEnfState);
		searchVO.setDocAppStateDept(docAppStateDept);
		searchVO.setWebServiceYn("Y");
		searchVO.setDisplayYn("N");
		searchVO.setMobileYn(mobileYn);
	
		// 상세조건
		searchVO.setStartDocNum("");
		searchVO.setEndDocNum("");
		searchVO.setBindingId("");
		searchVO.setBindingName("");
		searchVO.setSearchElecYn("");
		searchVO.setSearchNonElecYn("");
		searchVO.setSearchDetType("");
		searchVO.setSearchApprovalName("");
		searchVO.setSearchApprTypeApproval("");
		searchVO.setSearchApprTypeExam("");
		searchVO.setSearchApprTypeCoop("");
		searchVO.setSearchApprTypeDraft("");
		searchVO.setSearchApprTypePreDis("");
		searchVO.setSearchApprTypeResponse("");
		searchVO.setSearchApprTypeList("");
		searchVO.setDetailSearchYn("");
		searchVO.setAppDocYn("D");
		searchVO.setEnfDocYn("D");
		if(!"".equals(StringUtil.null2str(appReqVO.getFromymd()))){
		    searchVO.setStartDate(appReqVO.getFromymd());
		    searchVO.setEndDate(appReqVO.getToymd());
		}
		// 상세조건끝
	
		Page page = listCompleteService.listDisplay(searchVO, pageNo, pagecount);
	
		return page;
    }
    
    /**
     * <pre> 
     *  공람함(count)
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @throws Exception
     * @see
     */
    private int getListDisplayCount(AppReqVO appReqVO, String mobileYn, int pageNo, int pagecount) throws Exception {

		SearchVO searchVO = null;
	
		String startDate 	= "";
		String endDate 		= "";
		String searchType 	= "";
		String searchWord 	= "";
	
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
	
		String OptAppDocDisplay = appCode.getProperty("OPT334", "OPT334", "OPT");
		String OptEnfDocDisplay = appCode.getProperty("OPT335", "OPT335", "OPT");
		String optAppDocDpiCode = appCode.getProperty("DPI001", "DPI001", "DPI");
		String optEnfDocDpiCode = appCode.getProperty("DPI002", "DPI002", "DPI");
	
		String docEnfState 	= ListUtil.TransString("ENF600", "ENF");
		String docAppState 	= ListUtil.TransString("APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690", "APP");
		String docAppStateDept 	= ListUtil.TransString("APP200,APP201,APP300,APP301,APP302,APP305,APP400,APP401,APP402,APP405,APP500", "APP");
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
	
		OptionVO OptAppDocDisplayVO = this.envOptionAPIService.selectOption(appReqVO.getOrgcode(), OptAppDocDisplay);
		OptionVO OptEnfDocDisplayVO = this.envOptionAPIService.selectOption(appReqVO.getOrgcode(), OptEnfDocDisplay);
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setOptAppDocDisplayYn(OptAppDocDisplayVO.getUseYn());
		searchVO.setOptAppDocDisplayInfo(OptAppDocDisplayVO.getOptionValue());
		searchVO.setOptEnfDocDisplayYn(OptEnfDocDisplayVO.getUseYn());
		searchVO.setOptEnfDocDisplayInfo(OptEnfDocDisplayVO.getOptionValue());
		searchVO.setOptAppDocDpiCode(optAppDocDpiCode);
		searchVO.setOptEnfDocDpiCode(optEnfDocDpiCode);
		searchVO.setDocAppState(docAppState);
		searchVO.setDocEnfState(docEnfState);
		searchVO.setDocAppStateDept(docAppStateDept);
		searchVO.setWebServiceYn("Y");
		searchVO.setDisplayYn("N");
		searchVO.setMobileYn(mobileYn);
	
		// 상세조건
		searchVO.setStartDocNum("");
		searchVO.setEndDocNum("");
		searchVO.setBindingId("");
		searchVO.setBindingName("");
		searchVO.setSearchElecYn("");
		searchVO.setSearchNonElecYn("");
		searchVO.setSearchDetType("");
		searchVO.setSearchApprovalName("");
		searchVO.setSearchApprTypeApproval("");
		searchVO.setSearchApprTypeExam("");
		searchVO.setSearchApprTypeCoop("");
		searchVO.setSearchApprTypeDraft("");
		searchVO.setSearchApprTypePreDis("");
		searchVO.setSearchApprTypeResponse("");
		searchVO.setSearchApprTypeList("");
		searchVO.setDetailSearchYn("");
		searchVO.setAppDocYn("D");
		searchVO.setEnfDocYn("D");
		if(!"".equals(StringUtil.null2str(appReqVO.getFromymd()))){
		    searchVO.setStartDate(appReqVO.getFromymd());
		    searchVO.setEndDate(appReqVO.getToymd());
		}
		// 상세조건끝
	
		int resultCount = listCompleteService.listDisplayCount(searchVO);
	
		return resultCount;
    }


    /**
     * <pre> 
     *  결재완료함
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @throws Exception
     * @see
     */
    private Page getListApprovalComplete(AppReqVO appReqVO, String mobileYn, int pageNo, int pagecount) throws Exception {

		SearchVO searchVO = null;
	
		String startDate = "";
		String endDate = "";
		String searchType = "";
		String searchWord = "";
	
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
	
		String OptAppDocDisplay = appCode.getProperty("OPT334", "OPT334", "OPT");
		String OptEnfDocDisplay = appCode.getProperty("OPT335", "OPT335", "OPT");
		String optAppDocDpiCode = appCode.getProperty("DPI001", "DPI001", "DPI");
		String optEnfDocDpiCode = appCode.getProperty("DPI002", "DPI002", "DPI");
	
		String docEnfState 		= ListUtil.TransString("ENF600", "ENF");
		String docAppState 		= ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
		String docJudgeState 		= appCode.getProperty("APP620", "APP620", "APP");
		String docJudgeDeptState 	= appCode.getProperty("APP625", "APP625", "APP");
		String docReplaceJudgeState	= appCode.getProperty("APP610", "APP610", "APP");
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
	
		OptionVO OptAppDocDisplayVO = this.envOptionAPIService.selectOption(appReqVO.getOrgcode(), OptAppDocDisplay);
		OptionVO OptEnfDocDisplayVO = this.envOptionAPIService.selectOption(appReqVO.getOrgcode(), OptEnfDocDisplay);
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setOptAppDocDisplayYn(OptAppDocDisplayVO.getUseYn());
		searchVO.setOptAppDocDisplayInfo(OptAppDocDisplayVO.getOptionValue());
		searchVO.setOptEnfDocDisplayYn(OptEnfDocDisplayVO.getUseYn());
		searchVO.setOptEnfDocDisplayInfo(OptEnfDocDisplayVO.getOptionValue());
		searchVO.setOptAppDocDpiCode(optAppDocDpiCode);
		searchVO.setOptEnfDocDpiCode(optEnfDocDpiCode);
		searchVO.setDocAppState(docAppState);
		searchVO.setDocEnfState(docEnfState);
		searchVO.setMobileYn(mobileYn);
		searchVO.setDocJudgeState(docJudgeState);
		searchVO.setDocJudgeDeptState(docJudgeDeptState);
		searchVO.setDocReplaceJudgeState(docReplaceJudgeState);
		// 상세조건
		searchVO.setStartDocNum("");
		searchVO.setEndDocNum("");
		searchVO.setBindingId("");
		searchVO.setBindingName("");
		searchVO.setSearchElecYn("");
		searchVO.setSearchNonElecYn("");
		searchVO.setSearchDetType("");
		searchVO.setSearchApprovalName("");
		searchVO.setSearchApprTypeApproval("");
		searchVO.setSearchApprTypeExam("");
		searchVO.setSearchApprTypeCoop("");
		searchVO.setSearchApprTypeDraft("");
		searchVO.setSearchApprTypePreDis("");
		searchVO.setSearchApprTypeResponse("");
		searchVO.setSearchApprTypeList("");
		searchVO.setDetailSearchYn("");
		searchVO.setAppDocYn("D");
		searchVO.setEnfDocYn("D");
		searchVO.setDocType("");
		// 상세조건끝
	
		Page page = listCompleteService.listApprovalComplete(searchVO, pageNo, pagecount);
	
		return page;
    }


    /**
     * <pre> 
     *  후열함조회 
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @throws Exception
     * @see
     */
    private Page getListRear(AppReqVO appReqVO, int pageNo, int pagecount) throws Exception {

		SearchVO searchVO = null;
	
		String startDate = "";
		String endDate = "";
		String searchType = "";
		String searchWord = "";
	
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
	
		String docAppState = ListUtil.TransString("APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690",
		        "APP");
		String askType = ListUtil.TransString("ART054", "ART");
		String procType = ListUtil.TransString("APT003", "APT");
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setDocAppState(docAppState);
		searchVO.setAskType(askType);
		searchVO.setProcType(procType);
	
		Page page = listCompleteService.listRear(searchVO, pageNo, pagecount);
	
		return page;
    }
    
    /**
     * <pre> 
     *  후열함조회 (count)
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @throws Exception
     * @see
     */
    private int getListRearCount(AppReqVO appReqVO, int pageNo, int pagecount) throws Exception {

		SearchVO searchVO = null;
	
		String startDate = "";
		String endDate = "";
		String searchType = "";
		String searchWord = "";
	
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
	
		String docAppState = ListUtil.TransString("APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690",
		        "APP");
		String askType = ListUtil.TransString("ART054", "ART");
		String procType = ListUtil.TransString("APT003", "APT");
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setDocAppState(docAppState);
		searchVO.setAskType(askType);
		searchVO.setProcType(procType);
	
		int resultCount = listCompleteService.listRearCount(searchVO);
	
		return resultCount;
    }


    /**
     * <pre> 
     *  통보함조회 
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @throws Exception
     * @see
     */
    // jth8172 2012 신결재 TF
    private Page getListInform(AppReqVO appReqVO, int pageNo, int pagecount) throws Exception {

		SearchVO searchVO = null;
	
		String startDate = "";
		String endDate = "";
		String searchType = "";
		String searchWord = "";
	
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
	
		String docAppState = ListUtil.TransString("APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690",
		        "APP");
		String askType = ListUtil.TransString("ART055", "ART");
		String procType = ListUtil.TransString("APT003", "APT");
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setDocAppState(docAppState);
		searchVO.setAskType(askType);
		searchVO.setProcType(procType);
	
		Page page = listCompleteService.listRear(searchVO, pageNo, pagecount);
	
		return page;
    }
    
    /**
     * <pre> 
     *  통보함조회 (count)
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @throws Exception
     * @see
     */
    // jth8172 2012 신결재 TF
    private int getListInformCount(AppReqVO appReqVO, int pageNo, int pagecount) throws Exception {

		SearchVO searchVO = null;
	
		String startDate = "";
		String endDate = "";
		String searchType = "";
		String searchWord = "";
	
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
	
		String docAppState = ListUtil.TransString("APP600,APP610,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690",
		        "APP");
		String askType = ListUtil.TransString("ART055", "ART");
		String procType = ListUtil.TransString("APT003", "APT");
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setDocAppState(docAppState);
		searchVO.setAskType(askType);
		searchVO.setProcType(procType);
	
		int resultCount = listCompleteService.listRearCount(searchVO);
	
		return resultCount;
    }
        
    
    
    
    /**
     * 
     * <pre> 
     *  기관 문서함
     * </pre>
     * @param appReqVO
     * @param pageNo
     * @param pagecount
     * @return
     * @throws Exception
     * @see  
     *
     */
    private Page getListInstitution(AppReqVO appReqVO, int pageNo, int pagecount) throws Exception {

		SearchVO searchVO = null;
	
		String startDate = "";
		String endDate = "";
		String searchType = "";
		String searchWord = "";
	
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
	
		String docAppState = ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
		String docEnfState = ListUtil.TransString("ENF600","ENF");
		
		String readingRange	= appCode.getProperty("DRS004", "DRS004", "DRS"); // 기관,회사문서함 분리 jth8172 2012 신결재 TF
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setDocAppState(docAppState);
		searchVO.setDocEnfState(docEnfState);
		searchVO.setReadingRange(readingRange);
		
		//상세조건
		searchVO.setStartDocNum("");
		searchVO.setEndDocNum("");
		searchVO.setBindingId("");
		searchVO.setBindingName("");
		searchVO.setSearchElecYn("");
		searchVO.setSearchNonElecYn("");
		searchVO.setSearchDetType("");
		searchVO.setSearchApprovalName("");
		searchVO.setSearchApprTypeApproval("");
		searchVO.setSearchApprTypeExam("");
		searchVO.setSearchApprTypeCoop("");
		searchVO.setSearchApprTypeDraft("");
		searchVO.setSearchApprTypePreDis("");
		searchVO.setSearchApprTypeResponse("");
		searchVO.setSearchApprTypeAudit("");
		searchVO.setSearchApprTypeList("");
		searchVO.setDetailSearchYn("N");
		searchVO.setAppDocYn("D");
		searchVO.setEnfDocYn("D");
		//상세조건끝
	
		Page page = listCompleteService.listCompany(searchVO, pageNo, pagecount);
	
		return page;
    }

    
    
    /**
     * 
     * <pre> 
     *  회사 문서함
     * </pre>
     * @param appReqVO
     * @param pageNo
     * @param pagecount
     * @return
     * @throws Exception
     * @see  
     *
     */
    private Page getListCompany(AppReqVO appReqVO, int pageNo, int pagecount) throws Exception {

		SearchVO searchVO = null;
	
		String startDate = "";
		String endDate = "";
		String searchType = "";
		String searchWord = "";
	
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
	
		String docAppState = ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
		String docEnfState = ListUtil.TransString("ENF600","ENF");
		
		String readingRange	= appCode.getProperty("DRS005", "DRS005", "DRS"); // 기관,회사문서함 분리 jth8172 2012 신결재 TF
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setDocAppState(docAppState);
		searchVO.setDocEnfState(docEnfState);
		searchVO.setReadingRange(readingRange);
		
		//상세조건
		searchVO.setStartDocNum("");
		searchVO.setEndDocNum("");
		searchVO.setBindingId("");
		searchVO.setBindingName("");
		searchVO.setSearchElecYn("");
		searchVO.setSearchNonElecYn("");
		searchVO.setSearchDetType("");
		searchVO.setSearchApprovalName("");
		searchVO.setSearchApprTypeApproval("");
		searchVO.setSearchApprTypeExam("");
		searchVO.setSearchApprTypeCoop("");
		searchVO.setSearchApprTypeDraft("");
		searchVO.setSearchApprTypePreDis("");
		searchVO.setSearchApprTypeResponse("");
		searchVO.setSearchApprTypeAudit("");
		searchVO.setSearchApprTypeList("");
		searchVO.setDetailSearchYn("N");
		searchVO.setAppDocYn("D");
		searchVO.setEnfDocYn("D");
		//상세조건끝
	
		Page page = listCompleteService.listCompany(searchVO, pageNo, pagecount);
	
		return page;
    }


    /**
     * <pre> 
     *  일일감사대장조회 
     * </pre>
     * 
     * @param appReqVO
     * @return
     * @throws Exception
     * @see
     */
    private Page getListInspecOpen(AppReqVO appReqVO, int pageNo, int pagecount) throws Exception {
		String compId;
		String deptId;
		String userId;
		String startDate = "";
		String endDate = "";
		String searchCurRange = "";
		String dailyAuditLineChkYn = "N";
		
	
		String isAuthYn = "N";
		boolean isIspectionDept = false;
		String askType	= appCode.getProperty("ART040","ART040","ART"); 
		String askType2	= appCode.getProperty("ART041","ART041","ART");
		String askType3	= appCode.getProperty("ART042","ART042","ART");
		String askType4	= appCode.getProperty("ART043","ART043","ART");
		String askType5	= appCode.getProperty("ART044","ART044","ART");
		String askType6	= appCode.getProperty("ART045","ART045","ART");
		String askType7	= appCode.getProperty("ART046","ART046","ART");
		String askType8	= appCode.getProperty("ART047","ART047","ART");
		String askTypeList = ListUtil.TransString("ART040,ART041,ART042,ART043,ART044,ART045,ART046,ART047", "ART");
		
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
	
		compId = StringUtil.null2str(userVO.getCompID(), "");
		deptId = getDeptId(userVO.getDeptID());
		userId = StringUtil.null2str(userVO.getUserUID(), "");
	
		isIspectionDept = orgService.selectIsOrgRole(deptId, AppConfig.getProperty("role_auditdept", "O006", "role")); // 감사과
		// 여부
		// 체크
		if (isIspectionDept) {
		    isAuthYn = "Y";
		}
			
		String approverRole = AppConfig.getProperty("role_auditor", "", "role");
		String approverAddRole = AppConfig.getProperty("role_ceo", "", "role");
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
	
		String searchDefaultType = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT338", "OPT338", "OPT"));
	
		// 연도/회기별 조회 조건에 따라 검색한다.
		if ("Y".equals(searchDefaultType)) {
		    // 연도 및 회기의 정보에 따라 검색한다.
		    // registSearchTypeValue =
		    // envOptionAPIService.selectOptionValue(compId,
		    // appCode.getProperty("OPT318", "OPT318", "OPT"));
	
		    HashMap<String, Object> returnInfor = listRegistService.returnRegistDate(compId, searchCurRange, startDate, endDate);
	
		    startDate = (String) returnInfor.get("startDate");
		    endDate = (String) returnInfor.get("endDate");
		}
	
		SearchVO searchVO = new SearchVO();
	
		searchVO.setCompId(compId);
		searchVO.setUserId(userId);
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType("");
		searchVO.setSearchWord("");
		searchVO.setApproverRole(approverRole);
		searchVO.setApproverAddRole(approverAddRole);
		searchVO.setDocType("");
		searchVO.setRegistCurRange("");
		searchVO.setRegistSearchTypeYn("");
		searchVO.setRegistSearchTypeValue("");
		searchVO.setAskType(askType);
		searchVO.setAskType2(askType2);
		searchVO.setAskType3(askType3);
		searchVO.setAskType4(askType4);
		searchVO.setAskType5(askType5);
		searchVO.setAskType6(askType6);
		searchVO.setAskType7(askType7);
		searchVO.setAskType8(askType8);
		searchVO.setAskTypeList(askTypeList);
		searchVO.setDailyAuditLineChkYn(dailyAuditLineChkYn);
	
		Page page = new Page();
	
		if ("Y".equals(isAuthYn)) {
		    page = listRegistService.listDailyAuditRegist(searchVO, pageNo, pagecount);
		}
	
		return page;
    }


    /**
     * <pre> 
     *  공람게시판 조회
     * </pre>
     * 
     * @param appReqVO
     * @param pageNo
     * @param pagecount
     * @return
     * @throws Exception
     * @see
     */
    private Page getListDisplayNotice(AppReqVO appReqVO, int pageNo, int pagecount) throws Exception {
		String compId;
		String deptId;
		String userId;
		String startDate = "";
		String endDate = "";
		String readRange = "";
		String searchDeptId = "";
		String searchHeadOffice = "";
		String searchInstitution = "";
		String readingRange = "";
		String headOfficeReadingRange = "";
		String institutionReadingRange = "";
		String searchDeptName = "";
		String displayRange = "";
		String pubPostStdDate = "";
		String headOfficeRowDept = "";
		String institutionRowDept = "";
	
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());
	
		compId = StringUtil.null2str(userVO.getCompID(), "");
		deptId = getDeptId(userVO.getDeptID());
		userId = StringUtil.null2str(userVO.getUserUID(), "");
	
		String itemId = appReqVO.getItemid() + "^chk";
	
		String[] beforeRange = itemId.split("\\^");
	
		if ("chk".equals(beforeRange[1])) {
		    beforeRange[1] = appCode.getProperty("DRS002", "DRS002", "DRS");
		}
	
		readRange = StringUtil.null2str(beforeRange[1], appCode.getProperty("DRS002", "DRS002", "DRS"));
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT331", "OPT331", "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
	
		String opt314 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT314", "OPT314", "OPT")); // 열람범위
		String opt316 = envOptionAPIService.selectOptionValue(compId, appCode.getProperty("OPT316", "OPT316", "OPT")); // 공람게시범위
	
		// 열람범위 확인 후 진행
		if ("1".equals(opt314)) {
		    // 열람범위가 사용자 지정일 경우
	
		    // 열람범위를 부서로 선택한 경우
		    if (readRange.equals(appCode.getProperty("DRS002", "DRS002", "DRS"))) {
			searchDeptId = ListUtil.TransString(deptId);
			readingRange = readRange;
			searchDeptName = StringUtil.null2str(userVO.getDeptName(), "");
	
			// 열람범위를 본부로 선택한 경우
		    } else if (readRange.equals(appCode.getProperty("DRS003", "DRS003", "DRS"))) {
	
			headOfficeRowDept = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty(
			        "role_headoffice", "O003", "role"));
	
			searchDeptId = headOfficeRowDept;
			readingRange = readRange;
			searchDeptName = StringUtil.null2str(listEtcService.getDeptName(compId, deptId, AppConfig.getProperty(
			        "role_headoffice", "O003", "role")), "");
	
			// 열람범위를 기관으로 선택한 경우 // jth8172 2012 신결재 TF
		    } else if (readRange.equals(appCode.getProperty("DRS004", "DRS004", "DRS"))) {
	
			institutionRowDept = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty(
			        "role_institution", "O002", "role"));
			searchDeptId = institutionRowDept;
			readingRange = readRange;
			searchDeptName = StringUtil.null2str(listEtcService.getDeptName(compId, deptId, AppConfig.getProperty(
			        "role_institution", "O002", "role")), "");
	
			// 열람범위를 회사로 선택한 경우  // jth8172 2012 신결재 TF
		    } else if (readRange.equals(appCode.getProperty("DRS005", "DRS005", "DRS"))) {
	
			searchDeptId = compId;
			readingRange = readRange;
			searchDeptName = StringUtil.null2str((orgService.selectOrganization(compId)).getOrgName());  // jth8172 2012 신결재 TF
	
			// 열람범위를 전체로 선택한 경우
		    } else if ("ALL".equals(readRange)) {
			// 부서
			searchDeptId = ListUtil.TransString(deptId);
			// 본부의 하위부서
			searchHeadOffice = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty("role_headoffice",
			        "O003", "role"));
			// 기관의 하위부서
			searchInstitution = listEtcService.getRowDeptIds(compId, deptId, AppConfig.getProperty(
			        "role_institution", "O002", "role"));
	
			headOfficeReadingRange = appCode.getProperty("DRS003", "DRS003", "DRS");
			institutionReadingRange = appCode.getProperty("DRS004", "DRS004", "DRS");
	
			searchDeptName = StringUtil.null2str(listEtcService.getDeptName(compId, deptId, AppConfig.getProperty(
			        "role_institution", "O002", "role")), "");
	
			readingRange = "ALL";
		    }
	
		} else {
		    searchDeptId = ListUtil.TransString(deptId);
		    readingRange = appCode.getProperty("DRS002", "DRS002", "DRS");
		    searchDeptName = StringUtil.null2str(userVO.getDeptName(), "");
		}
	
		if ("1".equals(opt316)) {
		    displayRange = "APP";
		} else if ("2".equals(opt316)) {
		    displayRange = "ENF";
		} else if ("3".equals(opt316)) {
		    displayRange = "ALL";
		}
	
		pubPostStdDate = DateUtil.getCurrentDate();
	
		SearchVO searchVO = new SearchVO();
	
		searchVO.setCompId(compId);
		searchVO.setUserId(userId);
		if ("ALL".equals(readingRange)) {
		    searchVO.setDeptId(searchDeptId);
		    searchVO.setSearchHeadOffice(searchHeadOffice);
		    searchVO.setSearchInstitution(searchInstitution);
		    searchVO.setHeadOfficeReadingRange(headOfficeReadingRange);
		    searchVO.setInstitutionReadingRange(institutionReadingRange);
		    searchVO.setReadingRange(readingRange);
		} else {
		    searchVO.setDeptId(searchDeptId);
		    searchVO.setReadingRange(readingRange);
		}
		searchVO.setDeptName(searchDeptName);
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setDisplayRange(displayRange);
		searchVO.setPubPostStdDate(pubPostStdDate);
	
		Page page = new Page();
	
		if ("ALL".equals(readingRange)) {
		    page = listEtcService.listAllDisplayNotice(searchVO, pageNo, pagecount);
		} else {
		    page = listEtcService.listDisplayNotice(searchVO, pageNo, pagecount);
		}
	
		return page;
    }


    /**
     * <pre> 
     *  등록대장 조회
     * </pre>
     * 
     * @return
     * @see
     */
    private Page getListDocRegist(AppReqVO appReqVO, String mobileYn, int pageNo, int pagecount) throws Exception {

		SearchVO searchVO = null;
	
		String startDate = "";
		String endDate = "";
		String searchType = "";
		String searchWord = "";
		String searchCurRange = "";
		String registSearchTypeYn = "";
		String registSearchTypeValue = "";
		String searchDocType = "";
		String docAppState = "";
		String docEnfState = "";
		
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());	
		
		if("N".equals(mobileYn)){
			docAppState = ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
			docEnfState = ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600", "ENF");		
		}else{
			docAppState = ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
			docEnfState = ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600", "ENF");		
		}
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
	
		String searchDefaultType = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT338", "OPT338",
		        "OPT"));
		registSearchTypeYn = searchDefaultType;
	
		// 연도/회기별 조회 조건에 따라 검색한다.
		if ("Y".equals(searchDefaultType)) {
		    // 연도 및 회기의 정보에 따라 검색한다.
	
		    HashMap<String, Object> returnInfor = listRegistService.returnRegistDate(appReqVO.getOrgcode(), searchCurRange, startDate,
			    endDate);
	
		    startDate = (String) returnInfor.get("startDate");
		    endDate = (String) returnInfor.get("endDate");
		    registSearchTypeValue = AppConfig.getProperty("periodType", "Y", "etc");
		    /*
		     * if ("".equals(searchCurRange)) { ListUtil listCurRangeUtil = new
		     * ListUtil(); searchCurRange =
		     * listCurRangeUtil.returnCurRegist(appReqVO.getOrgcode(),
		     * registSearchTypeValue, startDate, endDate); }
		     */
		}
	
		// String mobileYn = "N";
		
		String deptId = getDeptId(userVO.getDeptID());
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setDeptId(deptId); // 부서코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setDocAppState(docAppState);
		searchVO.setDocEnfState(docEnfState);
		searchVO.setDocType(searchDocType);
		searchVO.setRegistCurRange(searchCurRange);
		searchVO.setRegistSearchTypeYn(registSearchTypeYn);
		searchVO.setRegistSearchTypeValue(registSearchTypeValue);
		searchVO.setMobileYn(mobileYn);
	
		// 상세조건
		searchVO.setStartDocNum("");
		searchVO.setEndDocNum("");
		searchVO.setBindingId("");
		searchVO.setBindingName("");
		searchVO.setSearchElecYn("");
		searchVO.setSearchNonElecYn("");
		searchVO.setSearchDetType("");
		searchVO.setSearchApprovalName("");
		searchVO.setSearchApprTypeApproval("");
		searchVO.setSearchApprTypeExam("");
		searchVO.setSearchApprTypeCoop("");
		searchVO.setSearchApprTypeDraft("");
		searchVO.setSearchApprTypePreDis("");
		searchVO.setSearchApprTypeResponse("");
		searchVO.setSearchApprTypeList("");
		searchVO.setDetailSearchYn("");
		searchVO.setAppDocYn("D");
		searchVO.setEnfDocYn("D");
		searchVO.setDocType("");
		// 상세조건끝
	
		Page page = listRegistService.listDocRegist(searchVO, pageNo, pagecount);
	
		return page;

    }
    
    /**
     * <pre> 
     *  등록대장 조회(count)
     * </pre>
     * 
     * @return
     * @see
     */
    private int getListDocRegistCount(AppReqVO appReqVO, String mobileYn, int pageNo, int pagecount) throws Exception {

		SearchVO searchVO = null;
	
		String startDate = "";
		String endDate = "";
		String searchType = "";
		String searchWord = "";
		String searchCurRange = "";
		String registSearchTypeYn = "";
		String registSearchTypeValue = "";
		String searchDocType = "";
		String docAppState = "";
		String docEnfState = "";
		
		UserVO userVO = orgService.selectUserByLoginId(appReqVO.getUserid());	
		
		if("N".equals(mobileYn)){
			docAppState = ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
			docEnfState = ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600", "ENF");		
		}else{
			docAppState = ListUtil.TransString("APP600,APP610,APP611,APP615,APP620,APP625,APP630,APP640,APP650,APP660,APP670,APP680,APP690","APP");
			docEnfState = ListUtil.TransString("ENF300,ENF310,ENF400,ENF500,ENF600", "ENF");		
		}	
	
		// 기본 조건에 의한 날짜 반환
		ListUtil defaultListUtil = new ListUtil();
		String searchBasicPeriod = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT331", "OPT331",
		        "OPT"));
		HashMap<String, String> returnDate = defaultListUtil.returnDate(searchBasicPeriod, startDate, endDate);
		startDate = (String) returnDate.get("startDate");
		endDate = (String) returnDate.get("endDate");
	
		String searchDefaultType = envOptionAPIService.selectOptionValue(appReqVO.getOrgcode(), appCode.getProperty("OPT338", "OPT338",
		        "OPT"));
		registSearchTypeYn = searchDefaultType;
	
		// 연도/회기별 조회 조건에 따라 검색한다.
		if ("Y".equals(searchDefaultType)) {
		    // 연도 및 회기의 정보에 따라 검색한다.
	
		    HashMap<String, Object> returnInfor = listRegistService.returnRegistDate(appReqVO.getOrgcode(), searchCurRange, startDate,
			    endDate);
	
		    startDate = (String) returnInfor.get("startDate");
		    endDate = (String) returnInfor.get("endDate");
		    registSearchTypeValue = AppConfig.getProperty("periodType", "Y", "etc");
		    /*
		     * if ("".equals(searchCurRange)) { ListUtil listCurRangeUtil = new
		     * ListUtil(); searchCurRange =
		     * listCurRangeUtil.returnCurRegist(appReqVO.getOrgcode(),
		     * registSearchTypeValue, startDate, endDate); }
		     */
		}
	
		// String mobileYn = "N";
		
		String deptId = getDeptId(userVO.getDeptID());
	
		searchVO = new SearchVO();
		searchVO.setCompId(appReqVO.getOrgcode());// 회사코드
		searchVO.setDeptId(deptId); // 부서코드
		searchVO.setUserId(userVO.getUserUID());// 사용자
		searchVO.setStartDate(startDate);
		searchVO.setEndDate(endDate);
		searchVO.setSearchType(searchType);
		searchVO.setSearchWord(searchWord);
		searchVO.setDocAppState(docAppState);
		searchVO.setDocEnfState(docEnfState);
		searchVO.setDocType(searchDocType);
		searchVO.setRegistCurRange(searchCurRange);
		searchVO.setRegistSearchTypeYn(registSearchTypeYn);
		searchVO.setRegistSearchTypeValue(registSearchTypeValue);
		searchVO.setMobileYn(mobileYn);
	
		// 상세조건
		searchVO.setStartDocNum("");
		searchVO.setEndDocNum("");
		searchVO.setBindingId("");
		searchVO.setBindingName("");
		searchVO.setSearchElecYn("");
		searchVO.setSearchNonElecYn("");
		searchVO.setSearchDetType("");
		searchVO.setSearchApprovalName("");
		searchVO.setSearchApprTypeApproval("");
		searchVO.setSearchApprTypeExam("");
		searchVO.setSearchApprTypeCoop("");
		searchVO.setSearchApprTypeDraft("");
		searchVO.setSearchApprTypePreDis("");
		searchVO.setSearchApprTypeResponse("");
		searchVO.setSearchApprTypeList("");
		searchVO.setDetailSearchYn("");
		searchVO.setAppDocYn("D");
		searchVO.setEnfDocYn("D");
		searchVO.setDocType("");
		// 상세조건끝
	
		int resultCount = listRegistService.listDocRegistCount(searchVO);
	
		return resultCount;

    }


    /*
     * 결재목록 조회결과 처리
     */
    private List<AppListVO> convertAppList(List<AppDocVO> inputList) {

		AppListVO appListVO;
		List<AppListVO> outputList = null;
		AppDocVO appDocVO;
		if (inputList != null) {
	
		    outputList = new ArrayList<AppListVO>();
		    int size = inputList.size();
		    for (int i = 0; i < size; i++) {
				appDocVO = new AppDocVO();
				appListVO = new AppListVO();
				appDocVO = (AppDocVO) inputList.get(i);
		
				appListVO.setDocid(appDocVO.getDocId());
				appListVO.setTitle(appDocVO.getTitle());
				appListVO.setUsername(appDocVO.getDrafterId());
				appListVO.setOrgname(appDocVO.getDrafterDeptName());
				appListVO.setAppstatus(appDocVO.getDocState());
				appListVO.setAppdate(appDocVO.getDraftDate());
				appListVO.setElecDocYn(appDocVO.getElectronDocYn());
				appListVO.setUrgencyYn(appDocVO.getUrgencyYn());
				appListVO.setAuditDivision(appDocVO.getAuditDivision());
		
				outputList.add(appListVO);
		    }
		}
	
		return outputList;
    }


    /*
     * 결재목록 조회결과 처리
     */
    private List<AppListVO> convertEnfList(List<EnfDocVO> inputList) {

		AppListVO appListVO;
		List<AppListVO> outputList = null;
		EnfDocVO enfDocVO;
		if (inputList != null) {
	
		    outputList = new ArrayList<AppListVO>();
		    int size = inputList.size();
		    for (int i = 0; i < size; i++) {
				enfDocVO = new EnfDocVO();
				appListVO = new AppListVO();
				enfDocVO = (EnfDocVO) inputList.get(i);
		
				appListVO.setDocid(enfDocVO.getDocId());
				appListVO.setTitle(enfDocVO.getTitle());
				appListVO.setUsername(enfDocVO.getAccepterId());
				appListVO.setOrgname(enfDocVO.getAcceptDeptName());
				appListVO.setAppstatus(enfDocVO.getDocState());
				appListVO.setAppdate(enfDocVO.getAcceptDate());
				appListVO.setElecDocYn(enfDocVO.getElectronDocYn());
				appListVO.setUrgencyYn(enfDocVO.getUrgencyYn());
		
				outputList.add(appListVO);
		    }
	
		}
	
		return outputList;
    }


    /*
     * 결재목록 조회결과 처리
     */
    private List<AppListVO> convertPubPostList(List<PubPostVO> inputList) {

		AppListVO appListVO;
		List<AppListVO> outputList = null;
		PubPostVO pubPostVO;
		if (inputList != null) {
	
		    outputList = new ArrayList<AppListVO>();
		    int size = inputList.size();
		    for (int i = 0; i < size; i++) {
				pubPostVO = new PubPostVO();
				appListVO = new AppListVO();
				pubPostVO = (PubPostVO) inputList.get(i);
		
				appListVO.setDocid(pubPostVO.getDocId());
				appListVO.setTitle(pubPostVO.getTitle());
				appListVO.setUsername(pubPostVO.getPublisherId());
				appListVO.setOrgname(pubPostVO.getPublishDeptName());
				appListVO.setAppstatus("");
				appListVO.setAppdate(pubPostVO.getPublishDate());
				appListVO.setElecDocYn(pubPostVO.getElectronDocYn());
		
				outputList.add(appListVO);
		    }
		}
	
		return outputList;
    }
    
    
    /*
     * 파일조회
     */
    public FileVO selectFile(FileVO fileVO) throws Exception {
    	return (FileVO)commonDAO.get("exchage.getFileInfo",fileVO);
    }
    
    /**
     * 
     * <pre> 
     *  부서반환
     * </pre>
     * @param deptId
     * @return
     * @throws Exception
     * @see  
     *
     */
    public String getDeptId(String deptId) throws Exception{
		String returnValue = CommonUtil.nullTrim(deptId);
	
		try{
		    OrganizationVO org = orgService.selectOrganization(returnValue);
		    String proxyDeptId = org.getProxyDocHandleDeptCode();
		    if (proxyDeptId != null && !"".equals(proxyDeptId)) {
			returnValue = proxyDeptId;
		    }
		}catch(Exception e){
		    logger.error("Exception : "+e);
		}
	
		return returnValue;
    }
    
    
    /**
     * <pre> 
     *  모바일 결재 처리 웹서비스
     * </pre>
     * 
     * @param MobileAppActionVO
     * @return MobileAppResultVO
     * @see
     */
    public MobileAppResultVO processMobileApproval(MobileAppActionVO mobileAppActionVO) throws Exception {

		logger.debug("결재처리 요청 서비스 호출");
		
		boolean checkRes	= false;
	    String opt419 		= appCode.getProperty("OPT419", "OPT419", "OPT"); // 서명사용여부
	    String personalSign = envOptionAPIService.selectOptionValue(mobileAppActionVO.getOrgcode(), opt419);	//개인이미지서명 사용여부
	    
		// 사용자 조회
	    UserVO userVO = orgService.selectUserByLoginId(mobileAppActionVO.getUserid());
	    
		String encryptedPwd = EnDecode.EncodeBySType(mobileAppActionVO.getUserpassword());
		if (orgService.compareApprovalPassword(userVO.getUserUID(), encryptedPwd)) {
		    checkRes = true;
		}
		
		MobileAppResultVO mobileAppResultVO = new MobileAppResultVO();
		if (checkRes) {
			mobileAppResultVO.setReqtype(mobileAppActionVO.getReqtype());
			mobileAppResultVO.setReqdate(DateUtil.getCurrentDate());
			mobileAppResultVO.setOrgcode(mobileAppActionVO.getOrgcode());
			mobileAppResultVO.setUserid(mobileAppActionVO.getUserid());
	
		    try {	    	
		    	mobileAppActionVO.setUserid(userVO.getUserUID());
		    	
		    	MobileQueueVO mobileQueueVO = new MobileQueueVO();
		    	mobileQueueVO.setProcessId(GuidUtil.getGUID());
		    	mobileQueueVO.setDocId(mobileAppActionVO.getDocid());
		    	mobileQueueVO.setCompId(mobileAppActionVO.getOrgcode());
		    	mobileQueueVO.setProcessorId(mobileAppActionVO.getUserid());
		    	mobileQueueVO.setProcessorDeptId(mobileAppActionVO.getDeptcode());
		    	mobileQueueVO.setProcessType(mobileAppActionVO.getActioncode());
		    	mobileQueueVO.setProcessDate(DateUtil.getCurrentDate());
		    	mobileQueueVO.setProcessOpinion(mobileAppActionVO.getAppopinion());
		    	mobileQueueVO.setSignYn(personalSign);
		    	
		    	if(!isExistMobileQueue(mobileQueueVO)) {
		    		int result = insertMobileQueue(mobileQueueVO);
					mobileAppResultVO.setRespose_code("success");
		    	} else {
			    	mobileAppResultVO.setRespose_code("exist");
		    	}
		    } catch (Exception e) {
		    	mobileAppResultVO.setRespose_code("fail");
		    	mobileAppResultVO.setError_message(e.getMessage().toString());
		    }
		} else {
			mobileAppResultVO.setReqtype(mobileAppActionVO.getReqtype());
			mobileAppResultVO.setReqdate(DateUtil.getCurrentDate());
			mobileAppResultVO.setRespose_code("fail");
			mobileAppResultVO.setError_message("비밀번호 인증실패");
		}
		
		return mobileAppResultVO;
    }
    
	// 모바일 결재처리 큐 저장
	private int insertMobileQueue(MobileQueueVO mobileQueueVO) throws Exception {
		return  commonDAO.insert("mobile.insertMobileQueue", mobileQueueVO);
	}
    
	// 모바일 결재처리 큐 존재여부
    private boolean isExistMobileQueue(MobileQueueVO mobileQueueVO) throws Exception {
    	
    	List<MobileQueueVO> mobileQueueVOList = commonDAO.getList("mobile.selectMobileQueueById", mobileQueueVO);
    	if(mobileQueueVOList.size() > 0) return(true);
    	else return(false);
    }
    
}
