/**
 * 
 */
package com.sds.acube.app.common.util;

import java.io.File;
import java.io.FilePermission;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jconfig.Configuration;

import com.sds.acube.jstor.JSTORApi;
import com.sds.acube.jstor.JSTORApiFactory;
import com.sds.acube.app.common.service.IDrmService;


/**
 * Class Name : JStor.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 25. <br> 수 정 자 : Timothy <br> 수정내용 : <br>
 * @author  Timothy
 * @since  2011. 3. 25.
 * @version  1.0
 * @see  com.sds.acube.app.common.util.JStor.java
 */

public class JStor {
    /**
	 */
    @Inject
    @Named("attachService")
    private IDrmService drmService;

    private String ip;
    private int port;
    private String volume;
    private String saveMode;

    Log logger = LogFactory.getLog(this.getClass().getName());


    /**
     * Constructor
     */
    public JStor() {
	this.ip = AppConfig.getProperty("stor_svr", "", "attach");
	this.port = AppConfig.getIntProperty("stor_port", 7404, "attach");
	this.volume = AppConfig.getProperty("stor_vol", "101", "attach");
	this.saveMode = AppConfig.getProperty("stor_savemode", "0", "attach");
    }


    /**
     * 파일 여러개를 저장서버에 등록한다.
     * 
     * @param strFilePath
     *            저장할 파일 fullpath 의 Array
     * @return 저장서버에 저장된 파일의 fileid array
     * @throws Exception
     */
    public String[] regFiles(String strFilePath[]) throws Exception {
	int nConnID; // 커넥션 아이디
	String[][] sInfoRegArr; // 등록 파일 정보
	int nNumOfFile; // 등록 파일 겟수
	int nRet;

	nNumOfFile = strFilePath.length;

	sInfoRegArr = new String[nNumOfFile][4];
	for (int i = 0; i < nNumOfFile; i++) {
	    sInfoRegArr[i][0] = strFilePath[i];
	    sInfoRegArr[i][1] = this.volume;
	    sInfoRegArr[i][2] = saveMode;
	    sInfoRegArr[i][3] = null;
	}

	JSTORApi server = getInstance();
	nConnID = server.JSTOR_Connect(this.ip, this.port);
	if (nConnID < 0) {
	    throw new Exception("Failed to Connect STOR Server");
	}

	nRet = server.JSTOR_FileReg(nConnID, nNumOfFile, sInfoRegArr, 0);
	if (nRet < 0) {
	    logger.error("Failed FileReg : " + server.JSTOR_getErrCode() + ", " + server.JSTOR_getErrMsg());
	    server.JSTOR_Rollback(nConnID);
	    server.JSTOR_Disconnect(nConnID);
	    throw new Exception("Failed FileReg");
	}
	nRet = server.JSTOR_Commit(nConnID);
	if (nRet < 0) {
	    logger.error("Failed to Commit : " + server.JSTOR_getErrCode() + ", " + server.JSTOR_getErrMsg());
	    server.JSTOR_Disconnect(nConnID);
	    throw new Exception("Failed to Commit");
	}

	String strFileID[] = new String[nNumOfFile];
	strFileID = server.JSTOR_getRegFileID();
	for (int i = 0; i < nNumOfFile; i++) {
	    if (strFileID[i] == null || strFileID[i].equals("") || strFileID[i].equals("null")) {
		throw new Exception("Invalid FileID");
	    }
	}
	server.JSTOR_Disconnect(nConnID);

	return strFileID;
    }


    /**
     * 파일 여러개를 입력된 파일ID로 저장서버에 등록한다.
     * 
     * @param strFilePath
     *            저장할 파일 fullpath 의 Array
     * @return 저장서버에 저장된 파일의 fileid array
     * @throws Exception
     */
    public String[] regFiles(String strFileId[], String strFilePath[]) throws Exception {
	int nConnID; // 커넥션 아이디
	String[][] sInfoRegArr; // 등록 파일 정보
	int nNumOfFile; // 등록 파일 겟수

	int nRet;

	nNumOfFile = strFilePath.length;

	sInfoRegArr = new String[nNumOfFile][4];
	for (int i = 0; i < nNumOfFile; i++) {
	    sInfoRegArr[i][0] = strFilePath[i];
	    sInfoRegArr[i][1] = this.volume;
	    sInfoRegArr[i][2] = saveMode;
	    sInfoRegArr[i][3] = ("".equals(strFileId[i])) ? null : strFileId[i];
	}

	JSTORApi server = getInstance();
	nConnID = server.JSTOR_Connect(this.ip, this.port);
	if (nConnID < 0) {
	    throw new Exception("Failed to Connect STOR Server");
	}

	nRet = server.JSTOR_FileReg(nConnID, nNumOfFile, sInfoRegArr, 0);
	if (nRet < 0) {
	    logger.error("Failed FileReg : " + server.JSTOR_getErrCode() + ", " + server.JSTOR_getErrMsg());
	    server.JSTOR_Rollback(nConnID);
	    server.JSTOR_Disconnect(nConnID);
	    throw new Exception("Failed FileReg");
	}
	nRet = server.JSTOR_Commit(nConnID);
	if (nRet < 0) {
	    logger.error("Failed to Commit : " + server.JSTOR_getErrCode() + ", " + server.JSTOR_getErrMsg());
	    server.JSTOR_Disconnect(nConnID);
	    throw new Exception("Failed to Commit");
	}

	String strFileID[] = new String[nNumOfFile];
	strFileID = server.JSTOR_getRegFileID();
	for (int i = 0; i < nNumOfFile; i++) {
	    if (strFileID[i] == null || strFileID[i].equals("") || strFileID[i].equals("null")) {
		throw new Exception("Invalid FileID");
	    }
	}
	server.JSTOR_Disconnect(nConnID);

	return strFileID;
    }


    /**
     * 파일 하나를 저장서버에 등록한다.
     * 
     * @param strFilePath
     * @return
     * @throws Exception
     */
    public String regFile(String strFilePath) throws Exception {
	String[] strs = new String[1];
	String[] rets = null;

	strs[0] = strFilePath;
	rets = regFiles(strs);
	if (rets == null)
	    throw new Exception("There is no result about saving file");
	return rets[0];
    }


    /**
     * 파일 하나를 입력된 파일ID로 저장서버에 등록한다.
     * 
     * @param strFilePath
     * @return
     * @throws Exception
     */
    public String regFile(String strFileId, String strFilePath) throws Exception {
	String[] strFileIds = new String[1];
	String[] strFilePaths = new String[1];
	String[] rets = null;

	strFileIds[0] = strFileId;
	strFilePaths[0] = strFilePath;
	rets = regFiles(strFileIds, strFilePaths);
	if (rets == null)
	    throw new Exception("There is no result about saving file");
	return rets[0];
    }


    /**
     * 파일서버로 부터 파일ID로 파일을 가져온다. 가져오는 이름은 파일ID와 같은 이름으로 가져온다. 같은 이름이 있을 경우, 덮어쓴다.
     * 
     * @param strFileId
     *            파일 ID
     * @param strFilePath
     *            파일 전체 경로
     * @return
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * @throws Exception
     */
    public int getFile(String strFileId, String strFilePath) throws NullPointerException, IllegalArgumentException, Exception {
	return getFile(strFileId, strFilePath, true);
    }


    /**
     * 파일서버로 부터 파일 ID로 파일을 가져온다.
     * 
     * @param strFileId
     *            파일 ID
     * @param strFilePath
     *            파일 전체 경로
     * @param overwrite
     *            덮어쓸지 여부
     * @return
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * @throws Exception
     */
    public int getFile(String strFileId, String strFilePath, boolean overwrite) throws NullPointerException, IllegalArgumentException,
	    Exception {
	return getFiles(new String[] { strFileId }, new String[] { strFilePath }, overwrite);
    }


    /**
     * 파일서버로 부터 여러개의 파일을 가져온다. 이미 있을 경우, 덮어쓴다.
     * 
     * @param strFileIds
     * @param strFilePaths
     * @return
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * @throws Exception
     */
    public int getFiles(String[] strFileIds, String[] strFilePaths) throws NullPointerException, IllegalArgumentException, Exception {
	return getFiles(strFileIds, strFilePaths, true);
    }


    /**
     * 파일서버로 부터 파일ID로 파일을 가져온다. 가져오는 이름은 파일ID와 같은 이름으로 가져온다.
     * 
     * @param strFileIds
     *            가져올 파일 ID 배열
     * @param strFilePaths
     *            저장할 위치 배열
     * @param overwrite
     *            저장할 파일이 이미 존재할 경우, 덮어쓸지 여부
     * @return 정상적으로 가져온 파일 개수
     * @throws NullPointerException
     * @throws IllegalArgumentException
     */
    public int getFiles(String[] strFileIds, String[] strFilePaths, boolean overwrite) throws NullPointerException,
	    IllegalArgumentException, Exception {
	int nConnID; // 커넥션 아이디
	String[][] sInfoGetArr;
	String[] strNewFileIds;
	String[] strNewFilePaths;
	int nNumOfFile; // 등록 파일 겟수
	int nRet;

	if (strFileIds == null) {
	    throw new Exception("FileIdNULL");
	}
	if (strFilePaths == null) {
	    throw new Exception("FilePathsNULL");
	}
	if (strFileIds.length == 0 || strFilePaths.length == 0) {
	    throw new IllegalArgumentException("ArgumentEmpty");
	}
	if (strFileIds.length != strFilePaths.length) {
	    throw new IllegalArgumentException("ArgumentLengthMismatch");
	}

	strNewFileIds = new String[strFileIds.length];
	strNewFilePaths = new String[strFileIds.length];
	nNumOfFile = 0;

	/** 파일이 있는지 여부 체크 **/

	for (int i = 0; i < strFilePaths.length; i++) {
	    File file = null;
	    file = new File(strFilePaths[i]);
	    if (!overwrite) {
		if (file.exists()) {
		    continue;
		}
	    }
	    File fileDir = file.getParentFile();
	    if (fileDir != null && !fileDir.exists()) {
		if (fileDir.mkdirs()) {
		    if (logger.isDebugEnabled()) {
			logger.info("Create Directory : " + fileDir.getAbsolutePath());
		    }
		}
	    }

	    strNewFileIds[nNumOfFile] = strFileIds[i];
	    strNewFilePaths[nNumOfFile] = strFilePaths[i];
	    nNumOfFile++;
	}

	if (nNumOfFile < 1)
	    return nNumOfFile;
	sInfoGetArr = new String[nNumOfFile][3];
	for (int i = 0; i < nNumOfFile; i++) {
	    sInfoGetArr[i][0] = strFileIds[i];
	    sInfoGetArr[i][1] = strFilePaths[i];
	    sInfoGetArr[i][2] = "-1";

	}

	JSTORApi server = getInstance();
	nConnID = server.JSTOR_Connect(this.ip, this.port);
	if (nConnID < 0) {
	    logger.error("Failed to Connect STOR Server : " + server.JSTOR_getErrCode() + ", " + server.JSTOR_getErrMsg());
	    throw new Exception("Failed to Connect");
	}

	nRet = server.JSTOR_FileGet(nConnID, nNumOfFile, sInfoGetArr, 0);
	if (nRet < 0) {
	    logger.error("Failed FileGet : " + server.JSTOR_getErrCode() + ", " + server.JSTOR_getErrMsg());
	    server.JSTOR_Disconnect(nConnID);
	    throw new Exception("Failed FileGet");
	}

	server.JSTOR_Disconnect(nConnID);

	return nRet;
    }


    /**
     * 저장서버에서 파일을 삭제한다.
     * 
     * @param arrFileId
     * @return 삭제된 파일 개수 ( 1 or 0)
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * @throws Exception
     */
    public int deleteFile(String arrFileId) throws NullPointerException, IllegalArgumentException, Exception {
	return deleteFiles(new String[] { arrFileId });
    }


    /**
     * 저장서버에서 파일을 여러개 삭제한다.
     * 
     * @param arrFileIds
     *            삭제할 파일 ID 배열
     * @return 삭제된 파일 개수
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * @throws Exception
     */
    public int deleteFiles(String[] arrFileIds) throws NullPointerException, IllegalArgumentException, Exception {
	int nConnID; // 커넥션 아이디
	int nNumOfFile; // 등록 파일 겟수
	int nRet;

	if (arrFileIds == null) {
	    throw new Exception("FileIdsNULL");
	}
	if (arrFileIds.length == 0) {
	    throw new Exception("FilePathsNULL");
	}

	nNumOfFile = arrFileIds.length;

	for (int i = 0; i < nNumOfFile; i++) {
	}

	JSTORApi server = getInstance();
	nConnID = server.JSTOR_Connect(this.ip, this.port);
	if (nConnID < 0) {
	    logger.error("Failed to Connect STOR Server : " + server.JSTOR_getErrCode() + ", " + server.JSTOR_getErrMsg());
	    throw new Exception("Failed to Connect");
	}

	nRet = server.JSTOR_FileDel(nConnID, nNumOfFile, arrFileIds);
	if (nRet < 0) {
	    logger.error("Failed FileDel : " + server.JSTOR_getErrCode() + ", " + server.JSTOR_getErrMsg());
	    server.JSTOR_Rollback(nConnID);
	    server.JSTOR_Disconnect(nConnID);
	    throw new Exception("Failed FileDel");
	}

	nRet = server.JSTOR_Commit(nConnID);
	if (nRet < 0) {
	    logger.error("Failed to Commit : " + server.JSTOR_getErrCode() + ", " + server.JSTOR_getErrMsg());
	    server.JSTOR_Disconnect(nConnID);
	    throw new Exception("Failed FileDel");
	}
	server.JSTOR_Disconnect(nConnID);

	return nRet;

    }


    public int replaceFile(String strFileId, String strFilePath) throws NullPointerException, IllegalArgumentException, Exception {
	return replaceFiles(new String[] { strFileId }, new String[] { strFilePath });
    }


    /**
     * 기존 있던 파일을 대치한다.
     * 
     * @param strFileIds
     *            대치할 파일 ID 배열
     * @param strFilePaths
     *            대치할 파일 전체경로
     * @return 대치된 파이 개수
     * @throws NullPointerException
     * @throws IllegalArgumentException
     * @throws Exception
     */
    public int replaceFiles(String[] strFileIds, String[] strFilePaths) throws NullPointerException, IllegalArgumentException, Exception {
	int nConnID; // 커넥션 아이디
	String[][] sInfoRepArr; // 등록 파일 정보
	int nNumOfFile; // 파일개수
	int nRet;

	if (strFileIds == null) {
	    throw new Exception("FileIdNULL");
	}
	if (strFilePaths == null) {
	    throw new Exception("FilePathsNULL");
	}
	if (strFileIds.length == 0 || strFilePaths.length == 0) {
	    throw new IllegalArgumentException("ArgumentEmpty");
	}
	if (strFileIds.length != strFilePaths.length) {
	    throw new IllegalArgumentException("ArgumentLengthMismatch");
	}

	nNumOfFile = strFileIds.length;

	sInfoRepArr = new String[nNumOfFile][3];

	for (int i = 0; i < nNumOfFile; i++) {
	    sInfoRepArr[i][0] = strFileIds[i];
	    sInfoRepArr[i][1] = strFilePaths[i];
	    sInfoRepArr[i][2] = saveMode;

	}

	JSTORApi server = getInstance();
	nConnID = server.JSTOR_Connect(this.ip, this.port);
	if (nConnID < 0) {
	    logger.error("Failed to Connect STOR Server : " + server.JSTOR_getErrCode() + ", " + server.JSTOR_getErrMsg());
	    throw new Exception("Failed FileRep");
	}

	nRet = server.JSTOR_FileRep(nConnID, nNumOfFile, sInfoRepArr, 0);

	if (nRet < 0) {
	    logger.error("Failed FileRep : " + server.JSTOR_getErrCode() + ", " + server.JSTOR_getErrMsg());
	    server.JSTOR_Rollback(nConnID);
	    server.JSTOR_Disconnect(nConnID);
	    throw new Exception("Failed FileRep");
	}

	nRet = server.JSTOR_Commit(nConnID);
	if (nRet < 0) {
	    logger.error("Failed to Commit STOR Server : " + server.JSTOR_getErrCode() + ", " + server.JSTOR_getErrMsg());
	    server.JSTOR_Disconnect(nConnID);
	    throw new Exception("Failed FileRep");
	}

	server.JSTOR_Disconnect(nConnID);
	return nRet;
    }


    /**
     * 파일을 저장서버에 복사한다.
     * 
     * @param fileId
     *            복사대상 fileid
     * @return 복사되어 저장된 파일의 fileid
     * @throws Exception
     */
    public String copyFiles(String fileId) throws Exception {
	String[] strs = new String[1];
	String[] rets = null;

	strs[0] = fileId;
	rets = copyFiles(strs);
	if (rets == null || rets.length < 1)
	    throw new Exception("There is no result about saving file");
	return rets[0];
    }


    /**
     * 파일 여러개를 저장서버에 복사한다.
     * 
     * @param fileIds
     *            복사대상 fileid array
     * @return 복사되어 저장된 파일의 fileid array
     * @throws Exception
     */
    public String[] copyFiles(String fileIds[]) throws Exception {
	int nConnID; // 커넥션 아이디
	String[][] sInfoCpyArr; // 등록 파일 정보
	int nNumOfFile; // 등록 파일 겟수

	int nRet;

	nNumOfFile = fileIds.length;

	sInfoCpyArr = new String[nNumOfFile][6];
	for (int i = 0; i < nNumOfFile; i++) {
	    sInfoCpyArr[i][0] = fileIds[i];
	    sInfoCpyArr[i][1] = this.volume;
	    sInfoCpyArr[i][2] = saveMode;
	    sInfoCpyArr[i][3] = "1"; // 로컬서버로 저장
	    sInfoCpyArr[i][4] = "";
	    sInfoCpyArr[i][5] = "";
	}

	JSTORApi server = getInstance();
	nConnID = server.JSTOR_Connect(this.ip, this.port);
	if (nConnID < 0) {
	    throw new Exception("Failed to Connect STOR Server");
	}

	nRet = server.JSTOR_FileCpy(nConnID, nNumOfFile, sInfoCpyArr);
	if (nRet < 0) {
	    logger.error("Failed FileCpy : " + server.JSTOR_getErrCode() + ", " + server.JSTOR_getErrMsg());
	    server.JSTOR_Rollback(nConnID);
	    server.JSTOR_Disconnect(nConnID);
	    throw new Exception("Failed FileCpy");
	}
	nRet = server.JSTOR_Commit(nConnID);
	if (nRet < 0) {
	    logger.error("Failed to Commit : " + server.JSTOR_getErrCode() + ", " + server.JSTOR_getErrMsg());
	    server.JSTOR_Disconnect(nConnID);
	    throw new Exception("Failed to Commit");
	}

	String strFileID[] = new String[nNumOfFile];
	strFileID = server.JSTOR_getNewCpyFileID();
	for (int i = 0; i < nNumOfFile; i++) {
	    if (strFileID[i] == null || strFileID[i].equals("") || strFileID[i].equals("null")) {
		throw new Exception("Invalid FileID");
	    }
	}
	server.JSTOR_Disconnect(nConnID);

	return strFileID;
    }


    /**
     * 디렉토리를 생성한다.
     * 
     * @param strServerPath
     * @return
     */
    private boolean createDirectory(String strServerPath) {
	File objFile = new File(strServerPath);
	if (objFile.exists())
	    return true;

	boolean bRet = false;
	try {
	    bRet = objFile.mkdir();
	    if (bRet) {
		String strAction = "read, write, delete";
		new FilePermission(strServerPath, strAction);
	    }
	} catch (SecurityException e) {
	    return false;
	}

	return bRet;
    }

    /**
     * JSTORApi 인스턴스를 생성한다.
     * 
     * @return 생성된 JSTORApi
     */
    private JSTORApi getInstance() {
    	String jStorApiType = AppConfig.getProperty("jstor_api_type", "ndisc", "attach");
    	String jStorSrvType = AppConfig.getProperty("jstor_svr_type", "unix", "attach");
    	String ndiscCache = AppConfig.getProperty("ndisc_cache", "false", "attach");

    	return new JSTORApiFactory().getInstance(jStorApiType, jStorSrvType, ndiscCache);
    }
}
