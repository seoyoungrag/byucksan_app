package com.sds.acube.app.mig;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.anyframe.pagination.Page;
import org.apache.commons.io.FileUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.appcom.service.IAppComService;
import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.approval.util.ApprovalUtil;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.LogWrapper;
import com.sds.acube.app.common.util.Transform;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.mig.service.IMigService;
import com.sds.acube.app.mig.vo.MigFileVO;
import com.sds.acube.app.mig.vo.MigVO;
import com.sds.acube.app.mig.vo.RegDocVO;


@Controller("MigController")
@RequestMapping("/app/mig/*.do")
public class MigController {
	
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
	 */
    @Inject
    @Named("migService")
    private IMigService migService;
    
    
    
	protected LogWrapper logger = LogWrapper.getLogger("com.sds.acube.mig");
	private int _reConnectCount = 0;
	HttpURLConnection _connection = null;
	 
	public Connection getConnection(){
    	Connection conn = null;
    	
    	try{
    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    		String connectionString = "jdbc:sqlserver://211.168.82.27:1433;" +
    					"databaseName=ACUBEGW;user=gw_user;password=gw000";
    		conn = DriverManager.getConnection(connectionString);
    	} catch(ClassNotFoundException cnfe) {
    		System.out.println(cnfe.toString());
    	} catch (SQLException se) {
    		System.out.println(se.toString());
    	} 
    	
    	return conn;
    }

    @RequestMapping("/app/mig/mig.do")
    public void searchDept(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	JSONObject mav = new JSONObject();
		
		List<MigVO> resultList = new ArrayList<MigVO>();
		String start = CommonUtil.nullTrim(request.getParameter("start"));
		String end = CommonUtil.nullTrim(request.getParameter("end"));
		String cabinet = CommonUtil.nullTrim(request.getParameter("cabinet"));
		Long startTime = System.currentTimeMillis();
		boolean status = false;
		int endPage = 0;
		
		int pageSize = 50;
		int page = Integer.parseInt(CommonUtil.nullTrim(request.getParameter("page"),"0"));
		
		if(start ==null || start.length() != 6 || page==0){
			MigVO migVo = new MigVO();
			migVo.setStatus("-7");
			migVo.setMsg("리퀘스트 start 불량 : " + start);
			resultList.add(migVo);
		}else{
			Page docPage = null;
			docPage = this.getDocList(start, page, pageSize, cabinet);
			endPage = (int) Math.ceil(docPage.getTotalCount() / pageSize);
			
			if(docPage!=null && docPage.getList()!=null && docPage.getList().size() > 0){
				List<RegDocVO>  docList = (List<RegDocVO>)docPage.getList();
				
				logger.info("docList.size : " + docList.size());
				if(docList!=null && docList.size() > 0){
					// 이관 대상 문서들 loop
					for(int i = 0; i < docList.size(); i++){
						// 3.0서버가 파일을 다 생성했는지 재확인하는 카운트. 문서정보가 변경될때마다 초기화 시킨다.
						this._reConnectCount = 0;
						MigVO migVo = new MigVO();
						
						RegDocVO regDocVO = docList.get(i);
						migVo.setDocId(regDocVO.getDocId());						
						
						Page filePage = this.getFileList(regDocVO.getDocId());
						
						// 이전에 이미 이관한 문서인지 확인하고 맞다면 다음 문서 이관 진행한다.
						if(filePage==null || filePage.getList()==null || filePage.getList().size() == 0){
							continue;
						}else{
							if(this.getStartConnection()){
								// 3.0 서버에 해당 문서 보기에 대한 리퀘스트를 전송한다.
								this.connectOldAcube(regDocVO.getDocId(),cabinet);
								
								List<MigFileVO>  filelist = (List<MigFileVO>)filePage.getList();
								
								// 필요한 첨부 파일이 모두 생성되었는지 확인한다.
								if(this.checkProcessDoneOldAcube(filelist)){
									// 각 파일의 스트림을 받아오자.
									if(this.copyFilesFromOldAcube(filelist)){
										//NDISC에 저장하고 디비에 저장하자.
										List<FileVO> fileVOs = this.makeFileVos(filelist);
										if(this.insertFileInfo(fileVOs)){
											migVo.setStatus("0");
											migVo.setMsg("마이그레이션 완료");
										}else{
											migVo.setStatus("-5");
											migVo.setMsg("파일정보 입력시 오류 발생");
										}
									}
								}else{
									migVo.setStatus("-4");
									migVo.setMsg("3.0 was에서 파일을 찾을 수 없음.");
								}
							}else{
								migVo.setStatus("-3");
								migVo.setMsg("로그인실패");
								resultList.add(migVo);
							}
						}
						status = true;
						resultList.add(migVo);
					}
				}else{
					MigVO migVo = new MigVO();
					migVo.setStatus("-2");
					migVo.setMsg("이관대상 문서 조회 실패");
					resultList.add(migVo);
				}
			}else{
				MigVO migVo = new MigVO();
				migVo.setStatus("-1");
				migVo.setMsg("이관 문서 리스트 조회 실패");
				resultList.add(migVo);
			}
		}
		
		try {
			mav.put("endPage", endPage);
			mav.put("status", status);
			mav.put("page", page);
			mav.put("start", start);
			mav.put("cabinet", cabinet);
			JSONArray jsonarray = new JSONArray();		
			for (int loop = 0; loop < resultList.size(); loop++) {
				jsonarray.put((JSONObject)Transform.transformToJson((MigVO)resultList.get(loop)));
			}
			mav.put("resultList", jsonarray);
			mav.put("durTime", (System.currentTimeMillis() - startTime) / 1000);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		response.setContentType("application/x-json; charset=utf-8");
		response.getWriter().write(mav.toString());

    }
    
    public boolean insertFileInfo(List<FileVO> fileVOs) {
    	try {
    		DrmParamVO drmParamVO = new DrmParamVO();
			drmParamVO.setCompId("A10000");
			drmParamVO.setUserId("Ua67c9c6aa7ac4031ff9");
			
			fileVOs = attachService.uploadAttach("", fileVOs, drmParamVO);
			
			String lineHisId = GuidUtil.getGUID();
			
			// 파일 이력
			List<FileHisVO> fileHisVOs = ApprovalUtil.getFileHis(fileVOs, lineHisId);
			
			// 파일/파일이력
			if (fileVOs.size() > 0) {
			    if (appComService.insertFile(fileVOs) > 0) {
				appComService.insertFileHis(fileHisVOs);
			    }
			}
			
			return true;
		} catch (Exception e) {
			logger.error("fail to insert file info : " + e.getMessage());
			return false;
		}
    	
    }
    
    public List<FileVO> makeFileVos(List<MigFileVO> filelist) {
    	List<FileVO> fileVOs = new ArrayList<FileVO>();
    	
    	for(int i= 0; i<filelist.size();i++){
    		MigFileVO migFileVO = filelist.get(i);
    		FileVO fileVO = new FileVO();
    		fileVOs.add(fileVO);
    		
    		fileVO.setDocId(migFileVO.getDocId());
		    fileVO.setCompId("useformiguseformigg");
		    fileVO.setProcessorId("Ua67c9c6aa7ac4031ff9");
		    fileVO.setTempYn("N");
		    fileVO.setFileName(migFileVO.getFileName());
		    fileVO.setDisplayName(migFileVO.getFileName());
		    String fileType = migFileVO.getAttachType();
		    
		    if(fileType!=null){
		    	if(fileType.equals("0")){
		    		fileVO.setFileType("AFT001");
		    	}else if(fileType.equals("1")){
		    		fileVO.setFileType("AFT007");
		    	}else if(fileType.equals("2")){
		    		fileVO.setFileType("AFT004");
				    int index = fileVO.getFileName().lastIndexOf( "." );
				    if(index > -1 ){
				    	String extension = fileVO.getFileName().substring( index );
					    fileVO.setDisplayName(migFileVO.getDisplayName()+extension);
				    }
		    	}
		    }
			fileVO.setFileSize(fileVO.getFileSize());
		   
		    String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
		    fileVO.setFilePath(uploadTemp + File.separator + "migOldData" + File.separator + migFileVO.getFileName());
		    fileVO.setImageWidth(0);
		    fileVO.setImageHeight(0);
		    fileVO.setFileOrder(i);
		    fileVO.setRegisterId("U2c4ee314eac04031ffd");
		    fileVO.setRegisterName("관리자");
		    fileVO.setDocVersion("");
    	}
    	
    	return fileVOs;
    }
    
    
    
    public boolean getStartConnection() {
		try {
			CookieManager cookieManager = new CookieManager();  
			CookieHandler.setDefault(cookieManager);
			
			URL url = new URL("http://bics.bsco.co.kr/acubecn/CN_MainConnect.jsp?uid=U2826020019228208120&version=2.0&D0=SDS&D1=50a1c4e6e51dbfdb7453ac561acfc14860a9abad3a186ea361f8925c24d995e4d3bc70b954cda3819a2915e25dff3455e0a8477d06e9208bf5ae1f9b0881ed4ea8562d7737ba9e0286b1a8a8f69d47a7bd61f64855230a1224e0b9bb42c699658550fa1a865988c80129e977195a01e1f857b716ad7029f97d991b48ec6b7b21e0915888baa1fccd79b9c204b32548ec1dc0f3ba70102fdb1ccaacb7fb70cd9ceb1ace55692dc03b9817bf534fbc869133385898b183c4382cf27b64ea79f7ef4455f255790704cd7593d9e89a235734789d15f1eda75ecd8aa39d04deda75f82e118e428c2ac322baec1f419334449499b16779a9ef70e51174dff09f801cc52c05efc5cc39bc21c1c55ee6d7a303c20523b34a817119340f2251db6737341bc520f31c7498ec29d2fe3fccd19596f25be6df690e629b38c0459cf65cc095fd864c656731a30eb439b2129a52387dfd06324cc011ac690077bfabd673026e52106213829f6ce55b632949c7fe78760cc16b45fe346c3512104183d06f8c8822dc554cdc3b8853a9fa854501a4c6c550f54e24ebec2a2e5b2494a46824bcf57e964d6ca37ec11b4ace4ff1cf75ffdf943c7be254f00b111abe468728b0df0918ca15f5ef6fb5cc0a46c1707ed534ea0c3f065c0132434ad5bcc0153b9ffcdb079379a3c7ae82ce793121a3ac21db8aeb38f4baf9efa0989a7917f150af8c9bdb337d47811614a8fcdd29e7d2f76d7f20c2cd43d5c7e9e06a0c94a571124d7b9c76910f0df22808f4682dcd4bcee43cb017fb4ac637718df2a27337b804dfea8a729f9c41e2329171267661c0e0e19e98114e807fb8ef19dbe130ef2d0a4eaf9e5f6885390b1f56f4a5a57bedf0a779df8bf5b9dce82736f382578bcfce9f723ae80ab508ac98af057f44c463c2430df89e9e9d1014d7be6f5f66d35470ccc4bd0b3a08d267073e103b44192799eee067&D2=null&D3=null");
            this._connection = (HttpURLConnection)url.openConnection();
            this._connection.setRequestMethod("GET");
            this._connection.connect();
            
            int code = this._connection.getResponseCode();
            
            return true;
		} catch (Exception e) {
			logger.error("fail to cgetStartConnection : " + e.getMessage());
		}
		
		return false;
    }
    
    public boolean copyFilesFromOldAcube(List<MigFileVO> filelist) {
		for(int i = 0; i < filelist.size(); i++){
			MigFileVO migFileVO = filelist.get(i);
    		
    		try {
    			String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
				
				URL url1 = new URL("http://bics.bsco.co.kr/cnupload/useformiguseformigg/" + migFileVO.getFileName());
		    	String path = uploadTemp + File.separator + "migOldData" + File.separator + migFileVO.getFileName();  // 구문서대장은 comp_id와 상관없이 간다. temp경로 인식을 위해 가상으로 만들어준다.
		    	File file = new File(path); file.deleteOnExit(); 
		    	FileUtils.copyURLToFile(url1, file);
		    	
			} catch (Exception e) {
				logger.error("fail to check file existence : " + e.getMessage());
			}
    	}
		return true;
    }
    
    public boolean checkProcessDoneOldAcube(List<MigFileVO> filelist) {
    	if(this._reConnectCount < 5){
    		for(int i = 0; i < filelist.size(); i++){
    			MigFileVO migFileVO = (MigFileVO)filelist.get(i);
        		
        		try {
        			URL url = new URL("http://bics.bsco.co.kr/cnupload/useformiguseformigg/" + migFileVO.getFileName());
        			logger.info("http://bics.bsco.co.kr/cnupload/useformiguseformigg/" + migFileVO.getFileName());
        			
        			this._connection = (HttpURLConnection)url.openConnection();
        			this._connection.setRequestMethod("GET");
        			this._connection.connect();

                    int code = this._connection.getResponseCode();
                    
                    if(code != 200 && code != 304){
                    	Thread.sleep(1000);
                    	this._reConnectCount++;
                    	this.checkProcessDoneOldAcube(filelist);
                    }
				} catch (Exception e) {
					logger.error("fail to check file existence : " + e.getMessage());
				}
        	}
    		return true;
    	}else{
    		logger.error("fail to get files from acube 3.0. cant retry over 5");
    		return false;
    	}
    }
    
    public void connectOldAcube(String docId, String cabinet){
    	try {
    		String cabinetUrl = "";
    		if(cabinet.equals("regi")){
    			cabinetUrl="REGILEDGER";
    		}else{
    			cabinetUrl="RECVLEDGER";
    		}
			//String url = "http://bics.bsco.co.kr/acubecn/approve/document/CN_ApproveDocument.jsp?dataurl="+docId+"&cabinet=REGILEDGER&docstatus=0&linename=0&serialorder=-1&bodytype=gul";
    		//RECVLEDGER: 등록
    		//REGILEDGER: 접수
    		URL u = new URL("http://bics.bsco.co.kr/acubecn/approve/document/DN_ApproveDocument.jsp?dataurl="+docId+"&cabinet="+cabinetUrl+"&docstatus=0&linename=0&serialorder=-1&bodytype=gul");
    		HttpURLConnection  huc = (HttpURLConnection) u.openConnection();
            huc.setRequestMethod("GET");
            huc.setDoInput(true);
            huc.setDoOutput(true);
            huc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStream os = huc.getOutputStream();
            //os.write( requestBody.getBytes("euc-kr") );
            os.flush();
            os.close();
            BufferedReader br = new BufferedReader( new InputStreamReader( huc.getInputStream(), "UTF-8" ), huc.getContentLength() );
            StringBuffer res = new StringBuffer();
            String buf;
            while( ( buf = br.readLine() ) != null ) {
              //res.append(buf);
            }
            
            br.close();
    		
            /*
			URL url = new URL("http://bics.bsco.co.kr/acubecn/approve/document/DN_ApproveDocument.jsp?dataurl="+docId+"&cabinet=REGILEDGER&docstatus=0&linename=0&serialorder=-1&bodytype=gul");
			this._connection = (HttpURLConnection)url.openConnection();
			this._connection.setRequestMethod("GET");
			this._connection.connect();
			*/
			
		} catch (Exception e) {
			logger.error("fail to connect acube 3.0 in connectOldAcube() : " + e.getMessage());
		}
    }
    
    public Page getFileList(String docId) {
    	MigFileVO migfilevo = new MigFileVO();
    	migfilevo.setDocId(docId);
    	
    	Page page = new Page(); 
    	try {
    		page = this.migService.getFileList(migfilevo, 1);
		} catch (Exception e) {
			logger.error("fail to get file list : " + e.getMessage());
		}
		return page;
    }
    
    public Page getDocList(String start, int nPage, int pageSize, String cabinet) { 
    	RegDocVO regDocVO = new RegDocVO();
    	regDocVO.setSearchmonth(start);
    	Page page = new Page(); 
    	try {
    		if(cabinet.equals("regi")){
        		page = this.migService.getList(regDocVO, nPage, pageSize);	
    		}else{
        		page = this.migService.getListRecv(regDocVO, nPage, pageSize);	
    		}
		} catch (Exception e) {
			logger.error("fail to get document list : " + e.getMessage());
		}
		return page;
    }
    

    public RegDocVO getDoc(String docId) { 
    	RegDocVO regDocVO = new RegDocVO();
    	regDocVO.setDocId(docId);
    	try {
    	regDocVO = this.migService.getDoc(docId);
		} catch (Exception e) {
			logger.error("fail to get document list : " + e.getMessage());
		}
		return regDocVO;
    }
    
    public List<MigFileVO> getMigratedFileList(String docId) {

	    Map<String, String> map = new HashMap<String, String>();
	    map.put("docId", docId);
	    List<MigFileVO> fileList = new ArrayList<MigFileVO>();
	    
    	try {
    		fileList = this.migService.getMigratedFileList(map);
		} catch (Exception e) {
			logger.error("fail to get file list : " + e.getMessage());
		}
		return fileList;
    }
    
    @RequestMapping("/app/mig/oldOpener.do")
    public ModelAndView selectMigDoc(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	ModelAndView mav = new ModelAndView("MigController.oldOpener");
    	
		String docId = CommonUtil.nullTrim(request.getParameter("dataurl"));
		String cabinet = CommonUtil.nullTrim(request.getParameter("cabinet"));
		String docstatus = CommonUtil.nullTrim(request.getParameter("docstatus"));
		String linename = CommonUtil.nullTrim(request.getParameter("linename"));
		String serialorder = CommonUtil.nullTrim(request.getParameter("serialorder"));
		String bodytype = CommonUtil.nullTrim(request.getParameter("bodytype"));
		HttpSession session = request.getSession();
		String compId = (String) session.getAttribute("COMP_ID"); // 사용자 소속 회사 아이디
		DrmParamVO drmParamVO = new DrmParamVO();
		drmParamVO.setCompId(compId);
		drmParamVO.setUserId("Ua67c9c6aa7ac4031ff9");
		
		
	    List<StorFileVO> storFileVOs = new ArrayList<StorFileVO>();
	    //RegDocVO regDocVO = this.getDoc(docId);
	    //Page filePage = this.getFileList(docId); //나중에 문서정보에서 get을 통해 값을 할당하도록 해야할 필요도 있을 수 있음.
		//List<MigFileVO> filelist = (List<MigFileVO>)filePage.getList();
	    
	    List<MigFileVO> filelist = this.getMigratedFileList(docId);
	    int filesize = filelist.size();
    	
		for(int pos = 0 ; pos < filesize; pos++){
			MigFileVO migFileVO = filelist.get(pos);
		    //storFileVO에 담아야할 정보들
		    String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
		    migFileVO.setFilePath(uploadTemp + "/" +compId + "/" + migFileVO.getFileName());
		    StorFileVO storFileVO = new StorFileVO();
		    storFileVO.setFileid(migFileVO.getFileId());
		    storFileVO.setFilepath(migFileVO.getFilePath());
		    storFileVOs.add(storFileVO);
		}

	    try{
	    	attachService.downloadAttach(storFileVOs, drmParamVO);
	    } catch (Exception e) {
			mav.addObject("result", "fail");
			mav.addObject("message", "approval.msg.nocontent");				
			logger.error("[" + docId + "][MigController.selectMigDoc][attachService의 downloadAttach 실패]");
	    }
	    
	    mav.addObject("filelist",filelist); //문서를 열기위한 파라메터
	    //3.0에서 사용하던 파라메터 시작
	    mav.addObject("dataurl",docId); 
	    mav.addObject("cabinet",cabinet); 
	    mav.addObject("docstatus",docstatus); 
	    mav.addObject("linename",linename); 
	    mav.addObject("serialorder",serialorder);
	    mav.addObject("bodytype",bodytype);
	    //3.0에서 사용하던 파라메터 끝
	    //첨부파일 가져오는 부분 시작
		Map<String, String> map = new HashMap<String, String>();
		map.put("docId", docId);
		map.put("compId", "useformiguseformigg");
		map.put("tempYn", "N");
	    mav.addObject("fileVOs",appComService.listFile(map));
	    //첨부파일 가져오는 부분 끝
	    
	    //등록대장인지 접수대장인지 구분하여 문서가져오기 시작
	    if((cabinet).equals("RECVLEDGER")){
	    	this.setRecvInfo(mav, map);
	    }else{
	    	this.setRegiInfo(mav, map);
	    }
	    //등록대장인지 접수대장인지 구분하여 문서가져오기 끝
	    
    	return mav;
    }

	private void setRegiInfo(ModelAndView mav, Map<String, String> map) throws Exception {
	    mav.addObject("docInfo",migService.getDocInfo(map));
	    mav.addObject("draftInfo",migService.getDraftInfo(map));
	    mav.addObject("recipeintList",migService.getRecipientsInfo(map));
	    mav.addObject("apprList",migService.getApprList(map));
	    mav.addObject("approverList",migService.getApproverList(map));
	    mav.addObject("attachList",migService.getAttachList(map));
	    mav.addObject("deliverList",migService.getDeliveresList(map));
	}
	
	private void setRecvInfo(ModelAndView mav, Map<String, String> map) throws Exception {
	    mav.addObject("docInfo",migService.getDocInfoRecv(map));
	    mav.addObject("draftInfo",migService.getDraftInfoRecv(map));
	    mav.addObject("recipeintList",migService.getRecipientsInfoRecv(map));
	    mav.addObject("apprList",migService.getApprListRecv(map));
	    mav.addObject("approverList",migService.getApproverListRecv(map));
	    mav.addObject("attachList",migService.getAttachListRecv(map));
	    mav.addObject("deliverList",migService.getDeliveresListRecv(map));
	}
}
