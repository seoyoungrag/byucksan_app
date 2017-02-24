package com.sds.acube.app.ws.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.ws.client.esbservice.EsbAppServiceProvider;
import com.sds.acube.app.ws.service.ILegacyAckService;
import com.sds.acube.app.ws.vo.EsbAppDocVO;
import com.sds.acube.app.ws.vo.LegacyVO;
import com.sds.acube.app.ws.vo.ResultVO;

/**
 * Class Name : LegacyAckService.java <br>
 * Description : 결재처리에 대한 결과를 연계하는 서비스 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2012. 6. 27. <br>
 * 수 정 자 : 김상태 <br>
 * 수정내용 : <br>
 * 
 * @author 윤동원
 * @since 2011. 5. 23.
 * @version 1.0
 * @see com.sds.acube.app.ws.service.impl.LegacyAckService.java
 */

@SuppressWarnings("serial")
@Service("legacyAckService")
public class LegacyAckService extends BaseService implements ILegacyAckService {

	//ESB 클라이언트 서비스 리소스
    //XML설정파일에 등록되어있는 리소스를 가져옴
	@Resource(name = "wsClientAppDocWSService")
    private EsbAppServiceProvider esbAppServiceProvider;

    /**
     * <pre> 
     *  결재연계정보 Ack - Web Service
     * </pre>
     * @param appReqVO
     * @return AppDetailVO
     * @see
     */
    public LegacyVO processAppDoc(EsbAppDocVO esbAppDocVO) throws Exception {
    	LegacyVO legacyVO = new LegacyVO();
    	ResultVO resultVO = new ResultVO();
    	try {
    		legacyVO = esbAppServiceProvider.processAppDoc(esbAppDocVO.getLegacyVO());
    		resultVO.setMessageCode(appCode.getProperty("BPS002", "BPS002", "BPS"));
    	}
    	catch(Exception e) {
    		resultVO.setMessageText(e.getMessage());
    		resultVO.setMessageCode(appCode.getProperty("BPS003", "BPS003", "BPS"));
    	}
    	
    	legacyVO.setResult(resultVO);
    	return legacyVO;
    }
    
    /**
     * <pre>
     * 결재연계정보 Ack - FILE 연계
     * </pre>
     * @param legacyVO
     * @return CmnResVO
     * @throws Exception 
     * 
     */
    public LegacyVO legacyAckFile(EsbAppDocVO esbAppDocVO) throws Exception {
    	JAXBContext jContext = null;
		Marshaller m = null;
		FileWriter fw = null;
		File file = null;
		LegacyVO legacyVO = esbAppDocVO.getLegacyVO();
    	LegacyVO legacyResVO = new LegacyVO();
    	ResultVO resultVO = new ResultVO();
    	
    	String filePath = AppConfig.getProperty("path", "", "legacy/" + legacyVO.getHeader().getReceiveServerId().toUpperCase() + "/send")
    						+ "/" + legacyVO.getReceiver().getOrgCode()
    						+ "/inputDir"
    						+ "/" + legacyVO.getHeader().getDocId();// 파일
		file = new File(filePath);
		
		if(!file.exists()) {
			file.mkdirs();
		}
		
		file = new File(filePath + "/" + legacyVO.getHeader().getDocId() + "_" + esbAppDocVO.getProcOrder() + ".xml");
		
		try {
			jContext = JAXBContext.newInstance(LegacyVO.class);
			m = jContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(legacyVO, file);
			
			file = new File(filePath + "/" + legacyVO.getHeader().getDocId() + "_" + esbAppDocVO.getProcOrder() + ".eof");// EOF 파일
			fw = new FileWriter(file);
			fw.close();
			
			resultVO.setMessageCode(appCode.getProperty("BPS002", "BPS002", "BPS"));
		}
		catch(Exception e) {
			resultVO.setMessageText(e.getMessage());
			resultVO.setMessageCode(appCode.getProperty("BPS003", "BPS003", "BPS"));
		}
		finally {
			if(fw != null) {
				fw.close();
			}
		}

		legacyResVO.setResult(resultVO);
		return legacyResVO;
    }
    
    /**
     * <pre>
     * 결재연계정보 Ack - FTP 연계
     * </pre>
     * @param legacyVO
     * @return CmnResVO
     * @throws Exception 
     * 
     */
    public LegacyVO legacyAckFTP(EsbAppDocVO esbAppDocVO) throws Exception {
    	JAXBContext jContext = null;
		Marshaller m = null;
		FileWriter fw = null;
		File file = null;
		FileInputStream fis = null;
    	FTPClient ftp = null;
    	LegacyVO legacyVO = esbAppDocVO.getLegacyVO();
    	LegacyVO legacyResVO = new LegacyVO();
    	ResultVO resultVO = new ResultVO();
    	
    	int reply = 0;
    	
    	try {
    		// 전송파일 생성
			//String filePath = AppConfig.getProperty("path", "", "legacy/" + "legacy/" + legacyVO.getHeader().getReceiveServerId().toUpperCase() + "/send")
    		String filePath = AppConfig.getProperty("upload_temp", "", "attach")
    						+ "/" + legacyVO.getReceiver().getOrgCode()
    						+ "/inputDir"
    						+ "/" + legacyVO.getHeader().getDocId();// 파일
			file = new File(filePath + "/" + legacyVO.getHeader().getDocId() + "_" + esbAppDocVO.getProcOrder() +  ".xml");
			
			if(!file.exists()) {
				file.mkdirs();
			}
			
			jContext = JAXBContext.newInstance(LegacyVO.class);
			m = jContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(legacyVO, file);
			
			file = new File(filePath + "/" + legacyVO.getHeader().getDocId() + "_" + esbAppDocVO.getProcOrder() + ".eof");// EOF 파일
			fw = new FileWriter(file);
			fw.close();
			
			// FTP 연결
            ftp = new FTPClient();
            ftp.connect(AppConfig.getProperty("serverIP", "", "legacy/" + legacyVO.getHeader().getReceiveServerId().toUpperCase() + "/send")
            			, Integer.valueOf(AppConfig.getProperty("serverPort", "", "legacy/" + legacyVO.getHeader().getReceiveServerId().toUpperCase() + "/send")));
            
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new Exception("Can`t FTP Server Connected.");
            }
            if(!ftp.login(AppConfig.getProperty("loginId", "", "legacy/" + legacyVO.getHeader().getReceiveServerId().toUpperCase() + "/send")
            				, AppConfig.getProperty("loginPw", "", "legacy/" + legacyVO.getHeader().getReceiveServerId().toUpperCase() + "/send"))) {
            	ftp.logout();
            	throw new Exception("Can`t FTP Server checked login Infomation.");
            }
            ftp.setFileType(FTP.LOCAL_FILE_TYPE);
            ftp.enterLocalPassiveMode();
            ftp.changeWorkingDirectory("/");
            
            String[] pathArray = (AppConfig.getProperty("path", "", "legacy/" + legacyVO.getHeader().getReceiveServerId().toUpperCase() + "/send")
									+ "/" + legacyVO.getReceiver().getOrgCode()
									+ "/" + legacyVO.getHeader().getDocId()).split("/");
            
            for(String path : pathArray) {
				if(!CommonUtil.isNullOrEmpty(path)) {
					try {
						ftp.makeDirectory(path);
						ftp.changeWorkingDirectory(path);
					}
					catch(Exception e) {
						logger.error("FTP Error : " + e.getMessage());
						if(AppConfig.getBooleanProperty("dev_mode", false, "general")){
							e.printStackTrace();
						}
						ftp.changeWorkingDirectory(path);
					}
				}
			}
            
            file = new File(filePath);
            for(File uploadFile : file.listFiles()){
            	fis = new FileInputStream(uploadFile);
            	boolean rst = ftp.storeFile(uploadFile.getName(), fis);
            	logger.debug("rst : " + rst);
            	fis.close();
            }

            resultVO.setMessageCode(appCode.getProperty("BPS002", "BPS002", "BPS"));
    	}
    	catch(Exception e){
			resultVO.setMessageText(e.getMessage());
            resultVO.setMessageCode(appCode.getProperty("BPS003", "BPS003", "BPS"));
    	}
    	finally {
    		if(fw != null) {
    			fw.close();
    		}
    		if(fis != null) {
    			fis.close();
    		}
    		if(ftp != null && ftp.isConnected()) {
    			ftp.disconnect();
    		}
    	}
    	
    	legacyResVO.setResult(resultVO);
    	return legacyResVO;
    }
    
     /**
     * <pre>
     * 결재연계정보 Ack - SFTP 연계
     * </pre>
     * @param legacyVO
     * @return CmnResVO
     * @throws Exception 
     * 
     */
    public LegacyVO legacyAckSFTP(EsbAppDocVO esbAppDocVO) throws Exception {
    	JAXBContext jContext = null;
		Marshaller m = null;
		FileWriter fw = null;
		File file = null;
		
		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		LegacyVO legacyVO = esbAppDocVO.getLegacyVO();
		LegacyVO legacyResVO = new LegacyVO();
    	ResultVO resultVO = new ResultVO();
		JSch jsch = new JSch();

		try {
			// 전송파일 생성
			//String filePath = AppConfig.getProperty("path", "", "legacy/" + legacyVO.getHeader().getReceiveServerId().toUpperCase() + "/send")
			String filePath = AppConfig.getProperty("upload_temp", "", "attach")
    						+ "/" + legacyVO.getReceiver().getOrgCode()
    						+ "/inputDir"
    						+ "/" + legacyVO.getHeader().getDocId();// 파일
			file = new File(filePath + "/" + legacyVO.getHeader().getDocId() + "_" + esbAppDocVO.getProcOrder() + ".xml");
			
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			
			jContext = JAXBContext.newInstance(LegacyVO.class);
			m = jContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(legacyVO, file);
			
			// SFTP 연결
			session = jsch.getSession(AppConfig.getProperty("loginId", "", "legacy/" + legacyVO.getHeader().getReceiveServerId().toUpperCase() + "/send")
									, AppConfig.getProperty("serverIP", "", "legacy/" + legacyVO.getHeader().getReceiveServerId().toUpperCase() + "/send")
									, Integer.valueOf(AppConfig.getProperty("serverPort", "", "legacy/" + legacyVO.getHeader().getReceiveServerId().toUpperCase() + "/send")));
			session.setPassword(AppConfig.getProperty("loginPw", "", "legacy/" + legacyVO.getHeader().getReceiveServerId().toUpperCase() + "/send"));
			
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			
			channel = session.openChannel("sftp");
			channel.connect();
			
			channelSftp = (ChannelSftp) channel;
			
			String[] pathArray = (AppConfig.getProperty("path", "", "legacy/" + legacyVO.getHeader().getReceiveServerId().toUpperCase() + "/send")
									+ "/" + legacyVO.getReceiver().getOrgCode()
									+ "/" + legacyVO.getHeader().getDocId()).split("/");
			
			for(String path : pathArray) {
				if(!CommonUtil.isNullOrEmpty(path)) {
					try {
						channelSftp.mkdir(path);
						channelSftp.cd(path);
					}
					catch(Exception e) {
						logger.error("SFTP Error : " + e.getMessage());
						if(AppConfig.getBooleanProperty("dev_mode", false, "general")){
							e.printStackTrace();
						}
						channelSftp.cd(path);
					}
				}
			}

			channelSftp.put(file.getPath(), file.getName());

			file = new File(filePath + "/" + legacyVO.getHeader().getDocId() + "_" + esbAppDocVO.getProcOrder() + ".eof");// EOF 파일
			fw = new FileWriter(file);
			fw.close();
			
			channelSftp.put(file.getPath(), file.getName());

			resultVO.setMessageCode(appCode.getProperty("BPS002", "BPS002", "BPS"));
		}
		catch(Exception e) {
			resultVO.setMessageText(e.getMessage());
			resultVO.setMessageCode(appCode.getProperty("BPS003", "BPS003", "BPS"));
		}
		finally {
			if(fw != null) {
				fw.close();
			}
			if(channelSftp != null && channelSftp.isConnected()) {
				channelSftp.quit();
				session.disconnect();
			}
		}

		legacyResVO.setResult(resultVO);
    	return legacyResVO;
    }
}
