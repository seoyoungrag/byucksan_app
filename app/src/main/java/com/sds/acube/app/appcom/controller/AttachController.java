package com.sds.acube.app.appcom.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.util.FileUtil;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.approval.service.IApprovalService;
import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.approval.vo.AppLineHisVO;
import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.AppTransUtil;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.PdfUtil;
import com.sds.acube.app.common.util.Transform;
import com.sds.acube.app.common.vo.DrmParamVO;

/**
 * Class Name  : AttachController.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 3. 24. <br> 수 정  자 : Timothy  <br> 수정내용 :  <br>
 * @author   Timothy 
 * @since  2011. 3. 24.
 * @version  1.0 
 * @see  com.sds.acube.app.appcom.controller.AttachController.java
 */

@Controller("attachController")
@RequestMapping("/app/appcom/attach/*.do")
public class AttachController extends BaseController{

    private static final long serialVersionUID = 1L;
    
    /**
	 */
    @Inject
    @Named("approvalService")
    private IApprovalService approvalService;    

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
    
    private String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");

    // fileId + String.fromCharCode(2) + fileName + String.fromCharCode(2) + displayName + String.fromCharCode(2) + 
    // fileType + String.fromCharCode(2) + fileSize + String.fromCharCode(2) + imageWidth + String.fromCharCode(2) + 
    // imageHeight + String.fromCharCode(2) + fileOrder + String.fromCharCode(2) + registerId + String.fromCharCode(2) + 
    // registerName + String.fromCharCode(2) + registDate + String.fromCharCode(4)

    @RequestMapping("/app/appcom/attach/downloadAttach.do")
    public ModelAndView downloadAttach(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AttachController.downloadAttach");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	String fileId = request.getParameter("fileid");
	String fileName = request.getParameter("filename");
	String displayName = request.getParameter("displayname");
	String type = request.getParameter("type");
	String fileIds[] = fileId.split(String.valueOf((char)2));
	String fileNames[] = fileName.split(String.valueOf((char)2));
	String displayNames[] = displayName.split(String.valueOf((char)2));
	
	List<StorFileVO> storFileVOs = new ArrayList<StorFileVO>();
	int filecount = fileIds.length;
	for (int loop = 0; loop < filecount; loop++) {
	    if (fileIds[loop] != null && !"".equals(fileIds[loop])) {
		StorFileVO storFileVO = new StorFileVO();
		storFileVO.setFileid(fileIds[loop]);
		storFileVO.setFilename(fileNames[loop]);
		storFileVO.setDisplayname(displayNames[loop]);
		storFileVO.setType(type);
		storFileVO.setFilepath(uploadTemp + "/" + compId + "/" + fileNames[loop]);

		storFileVOs.add(storFileVO);
	    }
	}

	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(compId);
	drmParamVO.setUserId(userId);
	String applyYN = "N";
	if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
	drmParamVO.setApplyYN(applyYN);
	
	attachService.downloadAttach(storFileVOs, drmParamVO);
	
	StorFileVO retFileVO = new StorFileVO();
	if (fileId.lastIndexOf(String.valueOf((char)2)) == fileId.length()-1) {
	    retFileVO.setFileid(fileId.substring(0, fileId.length()-1));
	    retFileVO.setFilename(fileName.substring(0, fileName.length()-1));
	    retFileVO.setDisplayname(displayName.substring(0, displayName.length()-1));
	} else {
	    retFileVO.setFileid(fileId);
	    retFileVO.setFilename(fileName);
	    retFileVO.setDisplayname(displayName);
	}
	retFileVO.setType(type);
	mav.addObject("file",  (JSONObject)Transform.transformToJson(retFileVO));
	    
	return mav;
    }  
  
    @RequestMapping("/app/appcom/attach/downloadAttachMobile.do")
    public void downloadAttachMobile(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AttachController.downloadAttach");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	String fileId = request.getParameter("fileid");
	String fileName = request.getParameter("filename");
	String displayName = request.getParameter("displayname");
	String type = request.getParameter("type");
	String fileIds[] = fileId.split(String.valueOf((char)2));
	String fileNames[] = fileName.split(String.valueOf((char)2));
	String displayNames[] = displayName.split(String.valueOf((char)2));
	
	List<StorFileVO> storFileVOs = new ArrayList<StorFileVO>();
	int filecount = fileIds.length;
	for (int loop = 0; loop < filecount; loop++) {
	    if (fileIds[loop] != null && !"".equals(fileIds[loop])) {
		StorFileVO storFileVO = new StorFileVO();
		storFileVO.setFileid(fileIds[loop]);
		storFileVO.setFilename(fileNames[loop]);
		storFileVO.setDisplayname(displayNames[loop]);
		storFileVO.setType(type);
		storFileVO.setFilepath(uploadTemp + "/" + compId + "/" + fileNames[loop]);

		storFileVOs.add(storFileVO);
	    }
	}

	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(compId);
	drmParamVO.setUserId(userId);
	String applyYN = "N";
	if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
	drmParamVO.setApplyYN(applyYN);
	
	attachService.downloadAttach(storFileVOs, drmParamVO);
	
	StorFileVO retFileVO = new StorFileVO();
	if (fileId.lastIndexOf(String.valueOf((char)2)) == fileId.length()-1) {
	    retFileVO.setFileid(fileId.substring(0, fileId.length()-1));
	    retFileVO.setFilename(fileName.substring(0, fileName.length()-1));
	    retFileVO.setDisplayname(displayName.substring(0, displayName.length()-1));
	} else {
	    retFileVO.setFileid(fileId);
	    retFileVO.setFilename(fileName);
	    retFileVO.setDisplayname(displayName);
	}
	retFileVO.setType(type);
	mav.addObject("file",  (JSONObject)Transform.transformToJson(retFileVO));
	
    }  
    
    @RequestMapping("/app/appcom/attach/uploadBody.do")
    public ModelAndView uploadBody(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AttachController.uploadAttach");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	List<StorFileVO> storFileVOs = AppTransUtil.transferStorFile(request.getParameter("file"), uploadTemp + "/" + compId);

	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(compId);
	drmParamVO.setUserId(userId);
	String applyYN = "N";
	if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
	drmParamVO.setApplyYN(applyYN);
	
	if (storFileVOs.size() > 0) {
	    if (FileUtil.validateBody(storFileVOs)) {
		List<StorFileVO> resultlist = attachService.uploadAttach(storFileVOs, drmParamVO);

		JSONArray filelist = new JSONArray();
		int listcount = resultlist.size();
		for (int loop = 0; loop < listcount; loop++) {
		    filelist.put((JSONObject)Transform.transformToJson((StorFileVO) resultlist.get(loop)));
		}
		mav.addObject("filelist", filelist);
		mav.addObject("result", "success");
		mav.addObject("message", "success");
	    } else {
		mav.addObject("result", "fail");
		mav.addObject("message", "toosmall");
	    }
	} else {
	    mav.addObject("result", "fail");
	    mav.addObject("message", "not exist body");
	}
	    
	return mav;
    }
    
    
    @RequestMapping("/app/appcom/attach/uploadAttach.do")
    public ModelAndView uploadAttach(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AttachController.uploadAttach");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	List<StorFileVO> files = AppTransUtil.transferStorFile(request.getParameter("file"), uploadTemp + "/" + compId);

	DrmParamVO drmParamVO = new DrmParamVO();
	drmParamVO.setCompId(compId);
	drmParamVO.setUserId(userId);
	String applyYN = "N";
	if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
	drmParamVO.setApplyYN(applyYN);
	
	if (files.size() > 0) {
	    List<StorFileVO> resultlist = attachService.uploadAttach(files, drmParamVO);
	    JSONArray filelist = new JSONArray();
	    int listcount = resultlist.size();
	    for (int loop = 0; loop < listcount; loop++) {
		filelist.put((JSONObject)Transform.transformToJson((StorFileVO) resultlist.get(loop)));
	    }
	    mav.addObject("filelist", filelist);
	    mav.addObject("result", "success");
	    mav.addObject("message", "success");
	} else {
	    mav.addObject("result", "fail");
	    mav.addObject("message", "not exist attach");
	}
	    
	return mav;
    }
    
    
    @RequestMapping("/app/appcom/attach/updateBody.do")
    public ModelAndView updateBody(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AttachController.updateBody");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	List<StorFileVO> storFileVOs = AppTransUtil.transferStorFile(request.getParameter("file"), uploadTemp + "/" + compId);

	JSONArray filelist = new JSONArray();

	int filesize = storFileVOs.size();
	if (filesize > 0) {
	    if (FileUtil.validateBody(storFileVOs)) {
		DrmParamVO drmParamVO = new DrmParamVO();
		drmParamVO.setCompId(compId);
		drmParamVO.setUserId(userId);
		String applyYN = "N";
		if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
		drmParamVO.setApplyYN(applyYN);

		List<String> fileidlist = new ArrayList<String>();
		for (int loop = 0; loop < filesize; loop++) {
		    fileidlist.add(storFileVOs.get(loop).getFileid());
		}
		attachService.uploadAttach(storFileVOs, drmParamVO);
		appComService.updateBody(compId, fileidlist, storFileVOs);
		for (int loop = 0; loop < filesize; loop++) {
		    filelist.put((JSONObject)Transform.transformToJson((StorFileVO) storFileVOs.get(loop)));
		}
		mav.addObject("filelist", filelist);
		mav.addObject("fileidlist", fileidlist);
		mav.addObject("result", "success");
		mav.addObject("message", "success");
	    } else {
		mav.addObject("result", "fail");
		mav.addObject("message", "toosmall");
	    }
	} else {
	    mav.addObject("result", "fail");
	    mav.addObject("message", "notexist");
	}
	    
	return mav;
    }
    
    
    @RequestMapping("/app/appcom/attach/updateAttach.do")
    public ModelAndView updateAttach(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AttachController.updateAttach");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String userId = (String) session.getAttribute("USER_ID"); // 사용자 아이디
	List<StorFileVO> storFileVOs = AppTransUtil.transferStorFile(request.getParameter("file"), uploadTemp + "/" + compId);

	JSONArray filelist = new JSONArray();

	int filesize = storFileVOs.size();
	if (filesize > 0) {
	    DrmParamVO drmParamVO = new DrmParamVO();
	    drmParamVO.setCompId(compId);
	    drmParamVO.setUserId(userId);
	    String applyYN = "N";
	    if((Boolean) session.getAttribute("IS_EXTWEB")) applyYN = "Y";
	    drmParamVO.setApplyYN(applyYN);

	    attachService.updateAttach(storFileVOs, drmParamVO);

	    for (int loop = 0; loop < filesize; loop++) {
		filelist.put((JSONObject)Transform.transformToJson((StorFileVO) storFileVOs.get(loop)));
	    }
	}
	
	mav.addObject("filelist",  filelist);
	    
	return mav;
    }
    
    
    @RequestMapping("/app/appcom/attach/listAttach4Ajax.do")
    public ModelAndView listAttach4Ajax(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AttachController.listAttach4Ajax");
	
	String compId = "";	
	compId = CommonUtil.nullTrim((String) request.getParameter("compId"));
	
	HttpSession session = request.getSession();

	if("".equals(compId)){
	    compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	}
	String docId = (String) request.getParameter("docId");
	String tempYn = (String) request.getParameter("tempYn");
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);
	map.put("tempYn", tempYn);

	List<FileVO> attachlist = attachService.listAttach(map);
	
	JSONArray jArray = new JSONArray();
	int attachcount = attachlist.size();
	for (int loop = 0; loop < attachcount; loop++) {
	    FileVO fileVO = attachlist.get(loop);
	    jArray.put(Transform.transformToJson(fileVO));
	}
	
	mav.addObject("filelist",  jArray);
	    
	return mav;
    }

    
    @RequestMapping("/app/appcom/attach/listAttach.do")
    public ModelAndView listAttach(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AttachController.listAttach");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String docId = (String) request.getParameter("docId");
	String tempYn = (String) request.getParameter("tempYn");
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);
	map.put("tempYn", tempYn);

	List<FileVO> attachlist = attachService.listAttach(map);	
	mav.addObject("file",  attachlist);
	    
	return mav;
    }

    
    @RequestMapping("/app/appcom/attach/selectBody.do")
    public ModelAndView selectBody(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AttachController.selectBody");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String docId = (String) request.getParameter("docId");
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);

	FileVO fileVO = attachService.selectBody(map);	
	mav.addObject("hwpfile",  fileVO);
	    
	return mav;
    }

    
    @RequestMapping("/app/appcom/attach/selectBodyHis.do")
    public ModelAndView selectBodyHis(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AttachController.selectBodyHis");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String docId = (String) request.getParameter("docId");
	String fileHisId = (String) request.getParameter("hisId");
	
	// 본문 문서 파일 종류(HWP, DOC, HTML)
	String bodyType = (String) request.getParameter("bodyType");
	mav.addObject("bodyType", bodyType);
	
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);
	map.put("fileHisId", fileHisId);

	FileHisVO fileHisVO = attachService.selectBodyHis(map);	
	mav.addObject("hwpfilehis", fileHisVO);
	
	// 결재경로 이력 조회
	List<AppLineHisVO> appLineHisVOs = approvalService.listAppLineHis(compId, docId, fileHisId);
	mav.addObject("appLineHis", appLineHisVOs);
	
	// HTML 편집기에서 결재경로 팝업창에서 몬서조회를 하면 제목에 undefined 표시를 하는 오류를 수정하기
	// 위해서 문서정보를 조회하여 제목을 구한다.
	if ("html".equals(bodyType)) {
		Map<String, String> docMap = new HashMap<String, String>();
		docMap.put("docId", docId);
		docMap.put("compId", compId);
		
	    Object DocInfo = appComService.selectDocInfo(docMap);
	    if (docId.indexOf("APP") != -1) {
	    	AppDocVO appDocVO = (AppDocVO) DocInfo;
	    	
	    	mav.addObject("title", appDocVO.getTitle());
	    }
	} 
	    
	return mav;
    }

    
    @RequestMapping("/app/appcom/attach/listAttachHis.do")
    public ModelAndView listAttachHis(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AttachController.listAttachHis");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String docId = (String) request.getParameter("docId");
	String fileHisId = (String) request.getParameter("hisId");
	Map<String, String> map = new HashMap<String, String>();
	map.put("compId", compId);
	map.put("docId", docId);
	map.put("fileHisId", fileHisId);

	List<FileHisVO> attachlist = attachService.listAttachHis(map);
	
	mav.addObject("fileHis",  attachlist);
	    
	return mav;
    }
    
    
    @RequestMapping("/app/appcom/attach/listFile.do")
    public ModelAndView listFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AttachController.listAttach");
	
	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String docId = (String) request.getParameter("docId");
	Map<String, String> map = new HashMap<String, String>();
	map.put("docId", docId);
	map.put("compId", compId);
	List<FileVO> attachlist = appComService.listFile(map);

	JSONArray jArray = new JSONArray();
	int attachcount = attachlist.size();
	for (int loop = 0; loop < attachcount; loop++) {
	    FileVO fileVO = attachlist.get(loop);
	    jArray.put(Transform.transformToJson(fileVO));
	}
	mav.addObject("filelist",  jArray);
	    
	return mav;
    }
    

    @RequestMapping("/app/appcom/attach/esb/listAttachErp.do")
    public ModelAndView listAttachErp(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AttachController.listAttachErp");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String docId = (String) request.getParameter("docId");
	Map<String, String> map = new HashMap<String, String>();
	map.put("docId", docId);
	map.put("compId", compId);
	List<FileVO> attachlist = appComService.listFile(map);

	JSONArray jArray = new JSONArray();
	int attachcount = attachlist.size();
	for (int loop = 0; loop < attachcount; loop++) {
	    FileVO fileVO = attachlist.get(loop);
	    jArray.put(Transform.transformToJson(fileVO));
	}
	
	mav.addObject("fileHis",  attachlist);
	    
	return mav;
    }
    
    @RequestMapping("/app/appcom/attach/generatePdf.do")
    public ModelAndView generatePdf(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView("AttachController.generatePdf");

	HttpSession session = request.getSession();
	String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
	String filename = request.getParameter("filename");
	
	String sourcePath = uploadTemp + "/" + compId + "/" + filename;
	String targetPath = "";
	
	StorFileVO fileVO = new StorFileVO();
	if (!"".equals(sourcePath)) {
	    File file = new File(sourcePath);
	    if (file.exists()) {	
		int pos = sourcePath.lastIndexOf(".");
		if (pos != -1 && pos < sourcePath.length()) {
		    targetPath = sourcePath.substring(0, pos + 1) + "pdf";
		} else {
		    targetPath += ".pdf";
		}
		int result = PdfUtil.generatePDF(sourcePath, targetPath);
		if (result > 0) {
		    int epos = targetPath.lastIndexOf("/");
		    if (epos != -1 && epos < targetPath.length()) {
			filename = targetPath.substring(epos + 1);
		    }
		    fileVO.setFilepath(filename);
		    mav.addObject("file",  (JSONObject)Transform.transformToJson(fileVO));

		    mav.addObject("result",  "success");
		    mav.addObject("message", "appcom.msg.fail.notcorrect.hwpfileinfo");
		} else {
		    mav.addObject("file",  (JSONObject)Transform.transformToJson(fileVO));
		    mav.addObject("result",  "fail");
		    mav.addObject("message", "appcom.msg.fail.generate.pdffile");
		}
	    } else {
		mav.addObject("file",  (JSONObject)Transform.transformToJson(fileVO));
		mav.addObject("result",  "fail");
		mav.addObject("message", "appcom.msg.fail.generate.pdffile");
	    }
	} else {
	    mav.addObject("file",  (JSONObject)Transform.transformToJson(fileVO));
	    mav.addObject("result",  "fail");
	    mav.addObject("message", "appcom.msg.fail.notcorrect.hwpfileinfo");
	}
	    
	return mav;
    }
}
