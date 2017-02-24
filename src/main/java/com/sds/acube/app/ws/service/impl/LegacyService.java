package com.sds.acube.app.ws.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.ws.BindingType;

import org.springframework.stereotype.Service;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.exchange.service.IExchangeService;
import com.sds.acube.app.ws.service.ILegacyService;
import com.sds.acube.app.ws.vo.AppFileVO;
import com.sds.acube.app.ws.vo.EsbAppDocVO;
import com.sds.acube.app.ws.vo.LegacyVO;
import com.sds.acube.app.ws.vo.ResultVO;

/** 
 *  Class Name  : LegacyService.java <br>
 *  Description : 연계처리를 위한 웹서비스 구현 클래스  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정 일 : 2012. 5. 30. <br>
 *  수 정 자 : 김상태  <br>
 *  수정내용 :  <br>
 * 
 *  @author  김상태 
 *  @since 2012. 5. 30.
 *  @version 1.0 
 *  @see  com.sds.acube.app.ws.service.impl.LegacyService.java
 */

@SuppressWarnings("serial")
@Service("legacyService")
@WebService(endpointInterface = "com.sds.acube.app.ws.service.ILegacyService")
@BindingType(javax.xml.ws.soap.SOAPBinding.SOAP11HTTP_MTOM_BINDING)
public class LegacyService extends BaseService implements ILegacyService {

    @Inject
    @Named("exchangeService")
    private IExchangeService exchangeService;

	@WebMethod
	public LegacyVO insertAppDoc(LegacyVO legacyVO) throws Exception {
		// 연계시스템 기본폴더
		String legacyDir = AppConfig.getProperty("path", "", "legacy/" + legacyVO.getHeader().getReceiveServerId().toUpperCase() + "/send");
		// WAS -> STOR 업로드 폴더
		String uploadTemp = AppConfig.getProperty("upload_temp", "", "attach");
		
		LegacyVO legacyResVO = new LegacyVO();
		ResultVO resultVO = new ResultVO();
		StringWriter appsb = new StringWriter();
		List<FileVO> attachFiles = new ArrayList<FileVO>();
		EsbAppDocVO esbappDocVO = new EsbAppDocVO();
		JAXBContext  jContext = null;
		Marshaller m = null;
		
		try {
		    logger.debug("#########################연계시작#########################");
		    
		    if(legacyVO.getHeader() == null) {
		    	throw new Exception("공통(header)정보가 입력되지 않았습니다.");
		    } else if(legacyVO.getReceiver().getOrgCode() == null) {
		    	throw new Exception("수신회사 정보가 입력되지 않았습니다.");
		    } else if(legacyVO.getFiles() == null){
		    	throw new Exception("연계 파일 정보가 입력되지 않았습니다.");
		    }
		    
			for(AppFileVO appFileVO : legacyVO.getFiles()) {
				FileVO fileVO = new FileVO();
		    	String fileName = GuidUtil.getGUID() + "." + CommonUtil.getExt(appFileVO.getFileName());
			    
		    	// 파일정보
		    	if ("attach".equals(appFileVO.getFileType())) {// 첨부
		    		fileVO.setFileType(appCode.getProperty("AFT010", "AFT010", "AFT"));
		    	} else if ("body".equals(appFileVO.getFileType())) {// 본문
		    		fileVO.setFileType(appCode.getProperty("AFT003", "AFT003", "AFT"));
		    	}

		    	File file = new File(legacyDir
		    						+ "/" + legacyVO.getReceiver().getOrgCode()
		    						+ "/" + legacyVO.getHeader().getOriginDocId()
		    						+ "/" + fileName);
		    	if(!file.getParentFile().exists()) {
		    		// 파일이 없다면 LegacyVO 내용에 파일을 첨부한 것이므로 파일을 생성한다.
		    		file.getParentFile().mkdirs();
		    	}
	    		writeFile(appFileVO.getContent().getInputStream(), file.getParentFile().getPath(), fileName);
	    		// WAS Temp폴더로 파일을 복사한다.
	    		copyFile(file, new File(uploadTemp
	    								+ "/" + legacyVO.getReceiver().getOrgCode()
	    								+ "/" + fileName));
		    	fileVO.setDisplayName(appFileVO.getFileName());
		    	fileVO.setFileName(fileName);
		    	fileVO.setFileSize(Integer.valueOf(file.length()+""));

		    	// 첨부파일, 본문파일 set
		    	attachFiles.add(fileVO);
			}
			
			// 파일정보
			esbappDocVO.setAttachFiles(attachFiles);
			// 연계정보
			esbappDocVO.setLegacyVO(legacyVO);
			File file = new File(legacyDir
		    						+ "/" + legacyVO.getReceiver().getOrgCode()
		    						+ "/" + legacyVO.getHeader().getOriginDocId()
		    						+ "/" + GuidUtil.getGUID() + ".xml");
			jContext = JAXBContext.newInstance(LegacyVO.class);
			m = jContext.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(legacyVO, appsb);
			m.marshal(legacyVO, file);
			esbappDocVO.setApprovalInfo(appsb.toString());

			// ---------------------------------------------
		    // 연계기안 처리
		    // ---------------------------------------------
		    exchangeService.insertAppDoc(esbappDocVO);

		    logger.debug("#########################연계종료#########################");
		    
			resultVO.setMessageDate(DateUtil.getCurrentDate());
			resultVO.setMessageCode("1"); // 성공시 1, 실패면 0
		}
		catch (Exception e) {
		    logger.error(e.getMessage());
		    e.printStackTrace();
		    resultVO.setMessageText(e.getMessage().toString());
		    resultVO.setMessageCode("0"); // 성공시 1, 실패면 0
		}
		finally {
			legacyResVO.setResult(resultVO);
			 // 연계시스템 파일 성공/실패백업 폴더로 옮기기
		    for(File file : new File(legacyDir
		    						+ "/" + legacyVO.getReceiver().getOrgCode()
		    						+ "/" + legacyVO.getHeader().getOriginDocId()).listFiles()) {
		    	System.out.println("resultVO.getMessageCode() : " + resultVO.getMessageCode());
		    	if(resultVO.getMessageCode().equals("1")) {
		    		copyFile(file, new File(legacyDir
		    								+ "/success"
		    								+ "/" + legacyVO.getHeader().getOriginDocId()
		    								+ "/" + file.getName()));
		    	} else {
		    		copyFile(file, new File(legacyDir
		    								+ "/fail"
		    								+ "/" + legacyVO.getHeader().getOriginDocId()
		    								+ "/" + file.getName()));
		    	}
		    }
		    
			deleteDirectory(new File(legacyDir + "/" + legacyVO.getReceiver().getOrgCode() + "/" + legacyVO.getHeader().getOriginDocId()).getParentFile(), true);
		}
		
		return legacyResVO;
	}
	
	/**
     * <pre> 
     *  파일처리
     * </pre>
     * 
     * @param input
     * @param output
     * @throws IOException
     * @see
     */
    public void writeFile(final InputStream input, String filePath, String fileName) throws IOException {
    	final byte[] buffer = new byte[4096];
    	int n = 0;
    	n = input.read(buffer);
    	FileOutputStream outFile = new FileOutputStream(filePath + "/" + fileName);

    	try {
    		while (-1 != n) {
    			outFile.write(buffer, 0, n);
    			n = input.read(buffer);
    		}
    		outFile.close();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    	finally {
    		if (outFile != null) {
    			outFile.close();
    		}
    	}
    }
    
    /**
	 * 
	 * <pre> 
	 *  source 파일을 target으로 파일 복사
	 * </pre>
	 * @param source
	 * @param target
	 * @throws Exception
	 */
	public void copyFile(File source, File target) throws Exception {
		//스트림, 채널 선언
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		FileChannel fcin = null;
		FileChannel fcout = null;
		try {
			if(!target.getParentFile().exists()) {
				target.getParentFile().mkdirs();
			}
			//스트림 생성
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(target.getPath() + ".tmp");
			//채널 생성
			fcin = inputStream.getChannel();
			fcout = outputStream.getChannel();
			//채널을 통한 스트림 전송
			long size = fcin.size();
			fcin.transferTo(0, size, fcout);
		} catch (Exception e) {
		} finally {
			//자원 해제
			if(fcout != null) {
				fcout.close();
			}
			if(fcin != null) {
				fcin.close();
			}
			if(outputStream != null) {
				outputStream.close();
			}
			if(inputStream != null) {
				inputStream.close();
			}
			
			for(File file : target.getParentFile().listFiles()) {
				if(file.getName().equals(target.getName() + ".tmp")) {
					if(!file.renameTo(target)){
						target.delete();
						file.renameTo(target);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * <pre> 
	 *  연계시스템 생성으로 만들었던 파일/폴더들 삭제
	 * </pre>
	 * @param path
	 * @param recursion
	 * @return Boolean
	 */
	public boolean deleteDirectory(File path, boolean recursion) {
		if(!path.exists()) {
			return false;
		}
		File[] files = path.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				deleteDirectory(file, false);
			} else {
				file.delete();
			}
		}
		
		if(recursion) {
			return recursion;
		} else {
			return path.delete();
		}
	}
	
	
}
