package com.sds.acube.app.appcom.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import sutil.UtilCommon;
import MarkAny.MaSaferJava.MaDocSafer;

import com.sds.acube.app.appcom.service.IAttachService;
import com.sds.acube.app.appcom.vo.FileHisVO;
import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.appcom.vo.StorFileVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.JStor;
import com.sds.acube.app.common.vo.DrmParamVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;

/**
 * Class Name  : AttachService.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 3. 24. <br> 수 정  자 : Timothy  <br> 수정내용 :  <br>
 * @author   Timothy 
 * @since  2011. 3. 24.
 * @version  1.0 
 * @see  com.sds.acube.app.appcom.service.impl.AttachService.java
 */

@Service("attachService")
public class AttachService extends BaseService implements IAttachService {

    private static final long serialVersionUID = 1L;

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;

    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;
    
    public boolean downloadAttach(StorFileVO storFileVO, DrmParamVO drmParamVO) throws Exception {

	boolean result = false;
	JStor jStor = new JStor();
	
	try {
	    jStor.getFile(storFileVO.getFileid(), storFileVO.getFilepath());
	    result = true;
	    applyDrm(storFileVO.getFilepath(), checkFileType(storFileVO.getType()), drmParamVO, "encode");
	} catch (Exception e) {
       	    String message = "failed DownloadAttach";
       	    logger.error(message);
       	    throw e;
	}
	
	return result;
    }

    
    public boolean downloadAttach(List<StorFileVO> list, DrmParamVO drmParamVO) throws Exception {

	boolean result = false;
	int size = list.size();
	
	if (size > 0) {
	    String[] fileIds = new String[size];
	    String[] filePaths = new String[size];
	    String[] fileTypes = new String[size];
	    JStor jStor = new JStor();
	
	    try {
		for (int loop = 0; loop < size; loop++) {
		    StorFileVO storFileVO = list.get(loop);
		    fileIds[loop] = storFileVO.getFileid();
		    filePaths[loop] = storFileVO.getFilepath();
		    fileTypes[loop] = checkFileType(storFileVO.getType());
		}
		jStor.getFiles(fileIds, filePaths);
		result = true;
		applyDrm(filePaths, fileTypes, drmParamVO, "encode");
	    } catch (Exception e) {
		String message = "failed DownloadAttach";
		logger.error(message);
		throw e;
	    }
	}
	
	return result;
    }
    
    public boolean downloadAttach(String docId, FileVO fileVO, DrmParamVO drmParamVO) throws Exception {

	boolean result = false;
	JStor jStor = new JStor();
	
	try {
	    String fileId = fileVO.getFileId();
	    String filePath = fileVO.getFilePath();
	    jStor.getFile(fileId, filePath);
	    result = true;
	    applyDrm(fileVO.getFilePath(), checkFileType(fileVO.getFileType()), drmParamVO, "encode");
	} catch (Exception e) {
       	    String message = "failed DownloadAttach";
       	    logger.error(message);
       	    throw e;
	}
	
	return result;
    }

    
    public boolean downloadAttach(String docId, List<FileVO> list, DrmParamVO drmParamVO) throws Exception {

	boolean result = false;
	int size = list.size();
	
	if (size > 0) {
	    String[] fileIds = new String[size];
	    String[] filePaths = new String[size];
	    String[] fileTypes = new String[size];
	    JStor jStor = new JStor();
	
	    try {
			for (int loop = 0; loop < size; loop++) {
			    FileVO fileVO = list.get(loop);
			    fileIds[loop] = fileVO.getFileId();
			    filePaths[loop] = fileVO.getFilePath();
			    fileTypes[loop] = checkFileType(fileVO.getFileType());
			}
			jStor.getFiles(fileIds, filePaths);
			result = true;
			applyDrm(filePaths, fileTypes, drmParamVO, "encode");
	    } catch (Exception e) {
			String message = "failed DownloadAttach";
			logger.error(message);
			throw e;
	    }
	}
	
	return result;
    }
    
    
    
    public boolean downloadAttach(String docId, List<FileVO> list,String dirPath, DrmParamVO drmParamVO) throws Exception {

	boolean result = false;
	int size = list.size();
	
	if (size > 0) {
	    String[] fileIds = new String[size];
	    String[] filePaths = new String[size];
	    String[] fileTypes = new String[size];
	    JStor jStor = new JStor();
	
	    try {
		for (int loop = 0; loop < size; loop++) {
		    FileVO fileVO = list.get(loop);
		    fileIds[loop] = fileVO.getFileId();
		    filePaths[loop] = dirPath+"/"+fileVO.getFileName();
		    fileTypes[loop] = checkFileType(fileVO.getFileType());
		}
		jStor.getFiles(fileIds, filePaths);
		result = true;
		applyDrm(filePaths, fileTypes, drmParamVO, "encode");
	    } catch (Exception e) {
		String message = "failed DownloadAttach";
		logger.error(message);
		throw e;
	    }
	}
	
	return result;
    }   
    
    
    
    public StorFileVO uploadAttach(StorFileVO storFileVO, DrmParamVO drmParamVO) throws Exception {
	
	String fileId = "";
	JStor jStor = new JStor();
	
	try {
	    applyDrm(storFileVO.getFilepath(), checkFileType(storFileVO.getType()), drmParamVO, "decode");
	    fileId = jStor.regFile(storFileVO.getFilepath());
	} catch (Exception e) {
       	    String message = "failed UploadAttach : error jStor.regFile";
       	    logger.error(message);
       	    throw e;
	}
	    
       	if (fileId != null) {
       	    storFileVO.setFileid(fileId);
       	} else {
       	    String message = "failed UploadAttach : fileId is null";
       	    logger.error(message);
       	    throw new Exception();
        }

	return storFileVO;
    }
    
    
    public List<StorFileVO> uploadAttach(List<StorFileVO> list, DrmParamVO drmParamVO) throws Exception {
	
	int size = list.size();

	if (size > 0) {
	    String[] fileIds;
	    String[] filePaths = new String[size];
	    String[] fileTypes = new String[size];
	    JStor jStor = new JStor();
	
	    try {	
		for (int loop = 0; loop < size; loop++) {
		    StorFileVO storFileVO = list.get(loop);
		    filePaths[loop] = storFileVO.getFilepath();
		    fileTypes[loop] = checkFileType(storFileVO.getType());
		}
		applyDrm(filePaths, fileTypes, drmParamVO, "decode");
		fileIds = jStor.regFiles(filePaths);
	    } catch (Exception e) {
		String message = "failed UploadAttach : error jStor.regFiles()";
		logger.error(message);
		throw e;
	    }
	    
	    if (fileIds != null) {
		for (int loop = 0; loop < size; loop++) {
		    StorFileVO storFileVO = list.get(loop);
		    storFileVO.setFileid(fileIds[loop]);
		}
	    } else {
		String message = "failed UploadAttach : fileId is null";
		logger.error(message);
		throw new Exception();
	    }
	}

	return list;
    }
    
    
    public FileVO uploadAttach(String docId, FileVO fileVO, DrmParamVO drmParamVO) throws Exception {
	
	String fileId = "";
	JStor jStor = new JStor();
	
	try {
	    applyDrm(fileVO.getFilePath(), checkFileType(fileVO.getFileType()), drmParamVO, "decode");
	    fileId = jStor.regFile(fileVO.getFilePath());
	} catch (Exception e) {
       	    String message = "failed UploadAttach : error jStor.regFile";
       	    logger.error(message);
       	    throw e;
	}
	    
       	if (fileId != null) {
       	    fileVO.setFileId(fileId);
       	} else {
       	    String message = "failed UploadAttach : fileId is null";
       	    logger.error(message);
       	    throw new Exception();
        }

	return fileVO;
    }
    
    
    public List<FileVO> uploadAttach(String docId, List<FileVO> list, DrmParamVO drmParamVO) throws Exception {
	
	int size = list.size();
	String[] fileIds;
	String[] filePaths = new String[size];
	String[] fileTypes = new String[size];
	JStor jStor = new JStor();
	
	try {
	    for (int loop = 0; loop < size; loop++) {
		FileVO fileVO = list.get(loop);
		filePaths[loop] = fileVO.getFilePath();
		fileTypes[loop] = checkFileType(fileVO.getFileType());
	    }
	    applyDrm(filePaths, fileTypes, drmParamVO, "decode");
	    fileIds = jStor.regFiles(filePaths);
	} catch (Exception e) {
       	    String message = "failed UploadAttach : error jStor.regFiles()";
       	    logger.error(message);
       	    throw e;
	}
	    
       	if (fileIds != null) {
       	    for (int loop = 0; loop < size; loop++) {
       		FileVO fileVO = list.get(loop);
       		fileVO.setFileId(fileIds[loop]);
       	    }
       	} else {
       	    String message = "failed UploadAttach : fileId is null";
       	    logger.error(message);
       	    throw new Exception();
        }

	return list;
    }
            
    
    public boolean updateAttach(StorFileVO storFileVO, DrmParamVO drmParamVO) throws Exception {
	
	boolean result = false;
	JStor jStor = new JStor();
	
	try {
	    String fileId = storFileVO.getFileid();
	    String filePath = storFileVO.getFilepath();
	    File file = new File(filePath);
	    if (fileId.length() > 0 && file.exists()) {	
		applyDrm(storFileVO.getFilepath(), checkFileType(storFileVO.getType()), drmParamVO, "decode");
		jStor.replaceFile(storFileVO.getFileid(), storFileVO.getFilepath());
		result = true;
	    } else {
		logger.error("fileId is null or file is not exist. - [fileId:" + fileId + "][filePath:" + filePath+ "]");
	    }
	} catch (Exception e) {
       	    String message = "failed UpdateAttach";
       	    logger.error(message);
       	    throw e;
	}
	
	return result;
    }
    
    
    public boolean updateAttach(List<StorFileVO> list, DrmParamVO drmParamVO) throws Exception {
	
	boolean result = false;
	int filecount = 0;
	int size = list.size();
	List<String> fileIdList = new ArrayList<String>();
	List<String> filePathList = new ArrayList<String>();
	List<String> fileTypeList = new ArrayList<String>();
	JStor jStor = new JStor();
	
	try {
	    for (int loop = 0; loop < size; loop++) {
		StorFileVO storFileVO = list.get(loop);
		String fileId = storFileVO.getFileid();
		String filePath = storFileVO.getFilepath();
		File file = new File(filePath);
		if (fileId.length() > 0 && file.exists()) {	
		    fileIdList.add(fileId);
		    filePathList.add(filePath);
		    fileTypeList.add(checkFileType(storFileVO.getType()));
		} else {
		    logger.error("fileId is null or file is not exist. - [fileId:" + fileId + "][filePath:" + filePath+ "]");
		}
	    }
	    int fileIdCount = fileIdList.size();
	    if (fileIdCount > 0) {
		String[] fileIds = new String[fileIdCount];
		String[] filePaths = new String[fileIdCount];
		String[] fileTypes = new String[fileIdCount];
		for (int loop = 0; loop < fileIdCount; loop++) {
		    fileIds[loop] = fileIdList.get(loop);
		    filePaths[loop] = filePathList.get(loop);
		    fileTypes[loop] = fileTypeList.get(loop);
		}

		applyDrm(filePaths, fileTypes, drmParamVO, "decode");
		filecount = jStor.replaceFiles(fileIds, filePaths);
	    }
	    if (filecount > 0)
		result = true;
	} catch (Exception e) {
       	    String message = "failed UpdateAttach";
       	    logger.error(message);
       	    throw e;
	}
	    
	return result;
    }
    
    
    @SuppressWarnings("unchecked")
    public List<FileVO> listAttach(Map<String, String> map) throws Exception {
		String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
		String aft010 = appCode.getProperty("AFT010", "AFT010", "AFT"); // 연계첨부
		map.put("fileType", aft004);
		map.put("fileExType", aft010);
		return (List<FileVO>) commonDAO.getListMap("appcom.listFile", map);
    }

    
    @SuppressWarnings("unchecked")
    public List<FileHisVO> listAttachHis(Map<String, String> map) throws Exception {
		String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
		String aft010 = appCode.getProperty("AFT010", "AFT010", "AFT"); // 연계첨부
		map.put("fileType", aft004);
		map.put("fileExType", aft010);
		return (List<FileHisVO>) commonDAO.getListMap("appcom.listFileHis", map);
    }

    
    @SuppressWarnings("unchecked")
    public FileVO selectBody(Map<String, String> map) throws Exception {
		String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // 본문(HWP)
		map.put("fileType", "'" + aft001 + "'");
		List<FileVO> fileVOs = commonDAO.getListMap("appcom.listBody", map);
		if (fileVOs.size() > 0) {
		    return fileVOs.get(0);
		} else {
		    return null;
		}
    }
    

    @SuppressWarnings("unchecked")
    public FileHisVO selectBodyHis(Map<String, String> map) throws Exception {
		String aft001 = appCode.getProperty("AFT001", "AFT001", "AFT"); // 본문(HWP)
		String aft002 = appCode.getProperty("AFT002", "AFT002", "AFT"); // 본문(HTML)
		String aft003 = appCode.getProperty("AFT003", "AFT003", "AFT"); // 본문(TXT)
		map.put("fileType", "'" + aft001 + "', '" + aft002 + "', '" + aft003 + "'");
		List<FileHisVO> fileHisVOs = commonDAO.getListMap("appcom.listBodyHis", map);
		if (fileHisVOs.size() > 0) {
		    return fileHisVOs.get(0);
		} else {
		    return null;
		}
    }
    
    private String checkFileType(String fileType) throws Exception {
		if(fileType==null) fileType = "";
		String aft004 = appCode.getProperty("AFT004", "AFT004", "AFT"); // 첨부
		String aft010 = appCode.getProperty("AFT010", "AFT010", "AFT"); // 연계첨부
		if(aft004.equals(fileType) || aft010.equals(fileType) 
			|| "open".equals(fileType) || "save".equals(fileType)) {
		    fileType = "attach";
		}
		return fileType;
    }

    private boolean applyDrm(String filePath, String fileType, DrmParamVO drmParamVO, String type) throws Exception {
		String[] filePaths = {filePath};
		String[] fileTypes = {fileType};
		return applyDrm(filePaths, fileTypes, drmParamVO, type);
    }
    
    private boolean applyDrm(String[] filePaths, String[] fileTypes, DrmParamVO drmParamVO, String type) throws Exception {
		String useYN = AppConfig.getProperty("use_yn", "", "drm");
		if(useYN != null && "N".equals(useYN)) return true;
	
		boolean extWeb = false;
		String drmYN = envOptionAPIService.selectOption(drmParamVO.getCompId(), appCode.getProperty("OPT319", "OPT319", "OPT")).getUseYn();
		if(drmYN == null || "".equals(drmYN)) drmYN = "N"; 
		String applyYN = drmParamVO.getApplyYN();
		if(applyYN != null && !"".equals(applyYN)) {
		    drmYN = applyYN;
		    if("Y".equals(applyYN)) {
			extWeb = true;
		    }
		}
	
		if("N".equals(drmYN)) return true;
	
		// --------------- DRM(MarkAny) 적용 Start ---------------
		try {
		    BufferedInputStream inputStream = null;
		    BufferedOutputStream outputStream = null;
	
		    int filePathsLength = filePaths.length;
		    for (int loop = 0; loop < filePathsLength; loop++) {
				if("encode".equals(type) && !"attach".equals(fileTypes[loop])) {
				    continue;
				}
				
				String filePath = filePaths[loop];
				
				if(filePath==null || "".equals(filePath)) {
				    continue;
				}
		
				File ori_File = new File(filePath);		// 원본 파일 경로		
				File tmp_File = new File(filePath + ".tmp");	// 임시 파일 경로		
				File trg_File = new File(filePath);		// 대상 파일 경로
				
				if(!ori_File.exists()) {
				    continue;
				}
		
				String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
		
				ori_File.renameTo(tmp_File);
				inputStream = new BufferedInputStream(new FileInputStream(tmp_File));
				outputStream = new BufferedOutputStream(new FileOutputStream(trg_File));
		
				// ------ 입력 파라미터 설정
				MaDocSafer mds = new MaDocSafer();
				mds.setStream_File(inputStream);			// 파일 InputStream
				mds.setStr_UserID(drmParamVO.getUserId());		// 사용자 아이디
				//mds.setStr_UserID("drm01");				// Test용
				mds.setStr_CompanyLevel(drmParamVO.getCompId());	// 계열사 코드
				mds.setStr_Modul("02");					// 메뉴 코드(02:전자결재)								//Test용
				mds.setStr_FileName(fileName);				// 파일명
				mds.setStr_FileExtension(fileExt);			// 파일 확장자
				mds.setI_FileLength(tmp_File.length());			// 파일 길이(Size)
				mds.setStr_FileID(UtilCommon.idMake("DRM"));		// 파일 아이디
				mds.setStr_MarkanyDrmInfoPath(AppConfig.getProperty("config_file", "", "drm"));	// DRM Server,IP 설정 파일
			    mds.setIs_out_usr(extWeb);                            	// 외부망 접속 : true, 내부망 접속 : false
				mds.setStr_poolname(AppConfig.getProperty("datasource", "", "drm"));			// DRM POOL_NAME
				//mds.setStr_jdbc_url(AppConfig.getProperty("db_url", "", "drm"));			// DRM DB URL
				//mds.setStr_db_user_id(AppConfig.getProperty("db_id", "", "drm"));			// DRM DB Conn ID
				//mds.setStr_db_user_pwd(AppConfig.getProperty("db_password", "", "drm"));		// DRM DB Conn PW
		
		
				// ------ 암호화 파일 체크
				String isEnc = mds.isEncryptedFile(tmp_File);
				if(isEnc == null) isEnc = "";
		
				String file_dec = "";
		
				if(isEnc.equals("1") && "encode".equals(type)) {
				    // 일반 파일이므로 암호화를 호출함
				    try {
					logger.debug("[[DRM LOG]] 일반 파일입니다.");
					file_dec = mds.fileEncrypt(outputStream);
		
					// 복호화 체크
					if(file_dec!=null && file_dec.equals("0")){
					    logger.debug("[[DRM LOG]] 암호화 성공(fileEncrypt)");
					}else{
					    logger.debug("[[DRM LOG]] 암호화 실패(fileEncrypt)");
					}
				    }catch (Exception e) {
					logger.error("[[DRM LOG]] mds.fileDecrypt Error : 파일의 경로가 맞지 않거나 파일이 없습니다.");
				    }
				    finally{
					inputStream.close();
					outputStream.close();
					tmp_File.delete();
				    }
		
				} else if(isEnc.equals("0") && "decode".equals(type)) {
				    // 암호화 파일이므로 복호화를 호출함
				    try {
						logger.debug("[[DRM LOG]] 암호화 파일입니다.");
						file_dec = mds.fileDecrypt(outputStream);
						inputStream.close();
						outputStream.close();
			
						// 복호화 체크
						if(file_dec!=null && file_dec.equals("0")){
						    logger.debug("[[DRM LOG]] 복호화 성공(fileDecrypt)");
						    tmp_File.delete();
						}else{
						    logger.debug("[[DRM LOG]] 복호화 실패(fileDecrypt)");
						    trg_File.delete();
						    tmp_File.renameTo(ori_File);
						}
				    }catch (Exception e) {
				    	logger.error("[[DRM LOG]] mds.fileDecrypt Error : 파일의 경로가 맞지 않거나 파일이 없습니다.");
				    }
				} else {
					    inputStream.close();
					    outputStream.close();
					    trg_File.delete();
					    tmp_File.renameTo(ori_File);
				}
		    }
		    return true;
		} catch (Exception e) {
		    logger.error("[[DRM LOG]] 파일의 경로가 맞지 않거나 파일이 없습니다.(BufferedInputStream Exception)");
		    return false;
		}
		// --------------- DRM(MarkAny) 적용 End ---------------
    }

}
