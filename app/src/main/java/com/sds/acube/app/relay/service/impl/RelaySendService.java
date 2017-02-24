package com.sds.acube.app.relay.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.Base64Util;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.JStor;
import com.sds.acube.app.common.util.LogWrapper;
import com.sds.acube.app.relay.service.IRelaySendService;
import com.sds.acube.app.relay.util.RelayUtil;
import com.sds.acube.app.relay.vo.ContentVO;
import com.sds.acube.app.relay.vo.LineInfoVO;
import com.sds.acube.app.relay.vo.PackInfoVO;
import com.sds.acube.app.relay.vo.RelayVO;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

/**
 * Class Name  : RelaySendService.java <br> Description : 문서유통(송신) 서비스  <br> Modification Information <br> <br> 수 정  일 : 2012. 4. 19. <br> 수 정  자 : 김상태  <br> 수정내용 :  <br>
 * @author                     김상태 
 * @since                    2012. 4. 19.
 * @version                    1.0 
 * @see com.sds.acube.app.relay.service.impl.RelaySendService.java
 */

@Service("relaySendService")
@SuppressWarnings("serial")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class RelaySendService extends BaseService implements IRelaySendService{

	@Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
	
	private RelayUtil relayUtil = new RelayUtil();
	
	private DocumentBuilderFactory factory = null;
	private DocumentBuilder builder = null;
	
	private LogWrapper log = null;
	
	/**
	 * <pre>문서유통 발신 서비스 생성자 : XML 유효성 검사시 Xml에 따라서 DTD를 선택하게 하는 XML Builder를 생성한다.</pre>
	 * @throws Exception
	 */
	public RelaySendService() throws Exception {
		
		log = new LogWrapper("com.sds.acube.app.relay");
		
		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		
		builder.setErrorHandler(new ErrorHandler() {
			public void fatalError(SAXParseException e) throws SAXException {
				throw e;
			}
			public void error(SAXParseException e) throws SAXParseException {
				throw e;
			}
			public void warning(SAXParseException e) throws SAXParseException {
				log.warn(" + " + e.getMessage());
			}
		});
		builder.setEntityResolver(new EntityResolver(){
			public InputSource resolveEntity(String pubilcId, String systemId) throws SAXException, IOException {
				if(systemId.endsWith("pack.dtd")) {
					return new InputSource(AppConfig.getProperty("relay_dtd", "", "relay") + "/pack.dtd");
				} else if(systemId.endsWith("pubdoc.dtd")) {
					return new InputSource(AppConfig.getProperty("relay_dtd", "", "relay") + "/pubdoc.dtd");
				} else {
					return null;
				}
			}
		});
	}
	
	/**
     * <pre> 
     *  스케줄러 Relay Queue 발송문서 리스트 가져오기
     * </pre>
     * @param relayQueueVO
     * @return List<RelayQueueVO>
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
	public List<RelayVO> getSendListInfo(RelayVO relayQueueVO) throws Exception {
    	return commonDAO.getList("relay.getSendListInfo", relayQueueVO);
	}
	
	/**
     * <pre> 
     *  문서유통(발송문서) Pack을 구성하기위한 정보 가져오기
     *  (수신처가 하나 이상일 경우 모두 값이 같지만 수신처ID receive_id가 다르기때문에
     *  List형식으로 가져온다.
     * </pre>
     * @param relayQueueVO
     * @return List<PackInfoVO>
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
	public List<PackInfoVO> getSendPackListInfo(RelayVO relayQueueVO) throws Exception {
		return commonDAO.getList("relay.getSendPackListInfo", relayQueueVO);
	}
    
    /**
     * <pre> 
     *  문서유통(발송문서) Ack Pack을 구성하기위한 정보 가져오기
     *  (수신처가 하나 이상일 경우 모두 값이 같지만 수신처ID receive_id가 다르기때문에
     *  List형식으로 가져온다.
     * </pre>
     * @param relayQueueVO
     * @return List<PackInfoVO>
     * @exception Exception
     */
    @SuppressWarnings("unchecked")
	public List<PackInfoVO> getSendAckPackListInfo(RelayVO relayQueueVO) throws Exception {
		return commonDAO.getList("relay.getSendAckPackListInfo", relayQueueVO);
	}
    
    /**
     * <pre> 
     *  발송처리가 끝난(오류는 따로 문서상태값과, 로그 테이블에 정보를 남긴다.) 큐를 삭제한다.
     * </pre>
     * @param relayQueueVO
     * @return Integer
     * @exception Exception
     */
    public int deleteRelayQueue(RelayVO relayQueueVO) throws Exception {
		return commonDAO.delete("relay.deleteRelayQueue", relayQueueVO);
	}
    
    /**
     * <pre> 
     *   Pubdoc 정보를 생성해서 xml 완성 후 File형태로 반환
     * </pre>
     * @param packInfoVO
     * @return File
	 * @throws Exception 
     */
	@SuppressWarnings("unchecked")
	public File getMakePubdocXml(PackInfoVO packInfoVO) throws Exception {
		Document xml = new DocumentImpl();
		StringWriter stringOut = new StringWriter();
	    
	    // Pubdoc- Approval/Assist Line
		// 결재라인 정보를 가져와서 결재라인(APPROVAL), 협조라인(ASSIST)로 구분해서 pacInfoVO에 추가한다.
	    List<LineInfoVO> approvalInfoVOs = commonDAO.getList("relay.getAppLineInfo", packInfoVO);
	    for(LineInfoVO lineInfoVO : approvalInfoVOs) {
	    	if(lineInfoVO.getPart().toUpperCase().equals("APPROVAL")) {
	    		packInfoVO.getApproval().add(lineInfoVO);
	    	} else {
	    		packInfoVO.getAssist().add(lineInfoVO);
	    	}
	    }
			
		// Element : pubdoc
		Element pubdoc = xml.createElement("pubdoc");
		xml.appendChild(pubdoc);

		// Element : head
		// pubdoc-head에 해당하는 xml노드를 만들어서 붙인다.
		pubdoc.appendChild(xml.importNode(relayUtil.getHeadXml(packInfoVO).getDocumentElement(), true));

		// Element : body
		// pubdoc-body에 해당하는 xml노드를 만들어서 붙인다.
		pubdoc.appendChild(xml.importNode(relayUtil.getBodyXml(packInfoVO).getDocumentElement(), true));

		// Element : foot
		// pubdoc-foot에 해당하는 xml노드를 만들어서 붙인다.
		pubdoc.appendChild(xml.importNode(relayUtil.getFootXml(packInfoVO).getDocumentElement(), true));

		// Element : attach
		// pubdoc-attach에 해당하는 xml노드를 만들어서 붙인다.
		Element attach = (Element) xml.importNode(relayUtil.getAttachXml(packInfoVO).getDocumentElement(), true);
		if(attach.hasChildNodes()) {
			pubdoc.appendChild(attach);
		}
		
		// pubdoc.xml stylesheel 선언부분을 넣는다.
		Node pi = xml.createProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"siheng.xsl\"");
		xml.insertBefore(pi, pubdoc);
		
		File tmpPubdocFile = new File(AppConfig.getProperty("relay_send_working", "", "relay")
				+ "/" + packInfoVO.getFilename().substring(0, 7)
				+ "/" + packInfoVO.getFilename().substring(0, 30)
				+ "/" + GuidUtil.getGUID() + ".xml");
        Transformer trans = relayUtil.getTrans("pubdoc.dtd");
        
        // pubdoc.xml Docuemtn객체를 반환할 StringWriter형태로 만든다.
        trans.transform(new DOMSource(xml), new StreamResult(stringOut));
        
        FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(tmpPubdocFile, false);
			fos.write(relayUtil.stripCDATA(stringOut.toString()).getBytes(AppConfig.getProperty("charset", "", "relay")));
			fos.close();
		}
		catch (Exception e) {
			log.error("+ [Error] RelaySendService.getAttach() Exception! : " + e.getMessage());
			throw e;
		}
		finally {
			if(fos != null) {
				fos.close();
			}
		}

        // 문서본문추출XML에는 <![CDATA[, ]]> 들어있다.
        // 문서를 발송할 시에는 <![CDATA[, ]]> 태그가 들어가면 안되기 때문에 pubdoc.xml 문서유통 규격을 구성하기 위해
        // stripCDATA를 호출하여 해당부분을 제거한후 String으로 반환한다.
        // sax나 별도의 오픈소스를 사용하지 않고 W3C DOM객체를 사용하면
        // Element에 들어가는 내용중 <,>,',",& 은 특수문자 치환처리가 되기 때문에 String 처리함
        
        // 문자열 내에 <![CDATA[, ]]>를 제거해주는 stripCDATA를 호출하여 제거한 후 File 형식으로 반환함 
	    return tmpPubdocFile;
	}

    /**
     * <pre> 
     *  문서유통을 위한(Pack - Content : pubdoc) 본문부분 생성
     * </pre>
     * @author 김상태
     * @return ContentVO
     * @throws Exception
     */
	public void getContentPubdoc(PackInfoVO packInfoVO) throws Exception {
		ContentVO contentVO = new ContentVO();

		File pubdocFile = getMakePubdocXml(packInfoVO);

		contentVO.setContentContentRole("pubdoc");
		contentVO.setContentFilename(pubdocFile.getName());
		contentVO.setContentContentType("text/xml");
		contentVO.setContentContentTransperEncoding(AppConfig.getProperty("encoding", "", "relay"));
		contentVO.setContentCharset(AppConfig.getProperty("charset", "", "relay"));
		contentVO.setContentStorFileName(pubdocFile.getName());
		
		packInfoVO.getContents().add(contentVO);
		
		FileVO fileVO = new FileVO();
		fileVO.setFileName(pubdocFile.getName());
		fileVO.setDisplayName("pubdoc.xml");
		fileVO.setFilePath(AppConfig.getProperty("relay_send_working", "", "relay")
							+ "/" + packInfoVO.getFilename().substring(0, 7)
							+ "/" + packInfoVO.getFilename().substring(0, 30) + "/");
		fileVO.setFileType("AFT012");
		packInfoVO.getAttach().add(fileVO);
	}

	/**
     * <pre> 
     *  문서유통을 위한(Pack - Content : attach_body, attach, seal, sign, logo, symbol) 본문부분 생성
     * </pre>
     * @author 김상태
     * @param fileVO
     * @return ContentVO
     * @throws Exception
     */
	public ContentVO getContentEtc(FileVO fileVO) throws Exception {
		ContentVO contentVO = new ContentVO();
		
		String fileExt = fileVO.getDisplayName();
		String contentType = "application/octet-stream";
		String fileType = "";
		
		// 파일 확장자를 통해 ContentType을 설정
		if(fileExt.toLowerCase().endsWith(".txt")) {
			contentType = "text/plain";
		} else if(fileExt.toLowerCase().endsWith(".xml")) {
			contentType = "text/xml";
		} else if(fileExt.toLowerCase().endsWith(".html") || fileExt.toLowerCase().endsWith(".htm")) {
			contentType = "text/html";
		} else if(fileExt.toLowerCase().endsWith(".xsl")) {
			contentType = "text/xsl";
		} else if(fileExt.toLowerCase().endsWith(".zip")) {
			contentType = "multipart/x-zip";
		} else if(fileExt.toLowerCase().endsWith(".gzip")) {
			contentType = "multipart/x-gzip";
		} else if(fileExt.toLowerCase().endsWith(".doc")) {
			contentType = "application/msword";
		} else if(fileExt.toLowerCase().endsWith(".xls")) {
			contentType = "application/vnd.ms-excel";
		} else if(fileExt.toLowerCase().endsWith(".ppt")) {
			contentType = "application/vnd.ms-powerpoint";
		} else if(fileExt.toLowerCase().endsWith(".bmp")) {
			contentType = "image/bmp";
		} else if(fileExt.toLowerCase().endsWith(".gif")) {
			contentType = "image/gif";
		} else if(fileExt.toLowerCase().endsWith(".jpeg") || fileExt.toLowerCase().endsWith(".jpg")) {
			contentType = "image/jpg";
		} else if(fileExt.toLowerCase().endsWith(".mpg") || fileExt.toLowerCase().endsWith(".mpeg") || fileExt.toLowerCase().endsWith(".mpe")) {
			contentType = "video/mpeg";
		} else if(fileExt.toLowerCase().endsWith(".wav")) {
			contentType = "audio/x-wav";
		}
		
		//첨부파일 종류별 Role 구분
		if(fileVO.getFileType().toUpperCase().equals("AFT012")) {
			fileType = "pubdoc";
		} else if(fileVO.getFileType().toUpperCase().equals("AFT013")) {
			fileType = "attach_body";
		} else if(fileVO.getFileType().toUpperCase().equals("AFT004")) {
			fileType = "attach";
		} else if(fileVO.getFileType().toUpperCase().equals("AFT005") || fileVO.getFileType().toUpperCase().equals("AFT006")) {
			fileType = "seal";
		} else if(fileVO.getFileType().toUpperCase().equals("AFT007")) {
			fileType = "sign";
		} else if(fileVO.getFileType().toUpperCase().equals("AFT008")) {
			fileType = "logo";
		} else if(fileVO.getFileType().toUpperCase().equals("AFT009")) {
			fileType = "symbol";
		}

		if(fileType.toLowerCase().equals("gpki")) {
			//TODO  전자서명이 적용된 암호화 : gpki
			// 개발할지??? 말지??? 문제가 많아서 실제 사용하는 곳이 한곳도 없다는데..
		} else {
			// 본문부 : attach_body, 로고 : logo, 심볼 : symbol, 첨부 : attach, 사인 : sign, 관인 : seal
			contentVO.setContentContentRole(fileType);
			contentVO.setContentFilename(fileVO.getFileName());
			contentVO.setContentContentType(contentType);
			contentVO.setContentContentTransperEncoding(AppConfig.getProperty("encoding", "", "relay"));
			contentVO.setContentCharset(AppConfig.getProperty("charset", "", "relay"));
		}

		return contentVO;
	}
	
	/**
     * <pre> 
     *  발송문서 첨부파일 전체 STOR -> WAS 가져옴
     *  filePath : AppConfig에 relay_send_working 폴더 + 발송회사(compId) + fileName + doc_id 을 통합하여 저장한다
     *  imageDate : base64 Encoding처리된(Bytes) 첨부파일이 들어감
     * </pre>
     * @param packInfoVO
     * @return List<FileVO>
	 * @throws NullPointerException, IllegalArgumentException, Exception  
     */
	@SuppressWarnings("unchecked")
	public List<FileVO> getAttach(PackInfoVO packInfoVO) throws NullPointerException, IllegalArgumentException, Exception {
		JStor jStor = new JStor();

		// 문서의 첨부파일 리스트를 가져온다.
	    List<FileVO> fileVOs =  commonDAO.getList("relay.getAttachListInfo", packInfoVO);
	    String filePath = AppConfig.getProperty("relay_send_working", "", "relay") +
			  			  "/" + packInfoVO.getFilename().substring(0, 7) +
			  			  "/" + packInfoVO.getFilename().substring(0, 30) +  //곽경종 substring 사용하는 이유?
			  			  "/";
	    if(fileVOs.size() > 0) {
			for(FileVO fileVO : fileVOs) {
				fileVO.setFilePath(filePath);
				// 저장서버에서 실제 파일을 내려 받는다.
				jStor.getFile(fileVO.getFileId(), fileVO.getFilePath() + fileVO.getFileName());

				// 본문내용추출XML의 경우 UTF-8(추출 OCX:고정) 선언에 한글이 들어가 있어서 length, size가 한글은 2로 인식하지 않고 1로 인식해서 오류 발생
				// 해당 내용은 InputStreamReader와 char[]로 별도 처리한다.
				if(fileVO.getFileType().toUpperCase().equals("AFT011")) {
					int numRead = 0;
					String chars = "";
					InputStream is = null;
					InputStreamReader isr = null;
		
					File file = new File(fileVO.getFilePath() + fileVO.getFileName());
					
					try{
						// 공문서형식 본문의 한글 때문에 InputStreamReader처리
						char[] cbuf = new char[54];
						
						is = new FileInputStream(file);
						isr = new InputStreamReader(is, relayUtil.checkXmlUTF(file));
						
						while( (numRead = isr.read(cbuf)) != -1) {
							chars += new String(cbuf, 0, numRead);
						}
					}
					catch(Exception e) {
						log.error("+ [Error] RelaySendService.getAttach() Exception! : " + e.getMessage());
						throw e;
					}
					finally {
						if(isr != null) {
							isr.close();
						}
						if(is != null) {
							is.close();
						}
					}
					// 본문내용추출XML에서 <content>와 </content> 사이에 있는 내용만 가져온다.
					if(chars.indexOf("<content>") != -1 && chars.indexOf("</content>") != -1) {
						String strContent = chars.substring(chars.indexOf("<content>") + 9, chars.indexOf("</content>"));
						//본문내용추출XML 추출 내용을 입력
						packInfoVO.setBodyContent(strContent);
					}
				}
			}
		}
	    return fileVOs;
	}
	
	/**
     * <pre> 
     *   DTD 검사를 위한 첨부 내용을 제외한 PackVO를 Xml형태로 변환하여 File로 저장
     * </pre>
     * @param packInfoVO
     * @return File
	 * @throws Exception 
     */
	public File makePackXmlDTD(PackInfoVO packInfoVO) throws Exception {
		// 문서유통 환경설정에 있는 설정 값이 가져와 packInfoVO에 넣는다. (app-config.xml - category : relay)
		packInfoVO.setSendGw(AppConfig.getProperty("send_gw", "", "relay"));
		packInfoVO.setDtdVersion(AppConfig.getProperty("dtd_version", "", "relay"));
		packInfoVO.setXslVersion(AppConfig.getProperty("xsl_version", "", "relay"));
		
		String filePath = AppConfig.getProperty("relay_send_working", "", "relay")
					  	+ "/" + packInfoVO.getFilename().substring(0, 7)
					  	+ "/" + packInfoVO.getFilename().substring(0, 30)  //곽경종 substring 이유.?
					  	+ "/";
		File tmpPackFile = new File(filePath + packInfoVO.getFilename());

		// packInfoVO를 Document로 변환
		Document xml = relayUtil.getMakePackXml(packInfoVO);
		Transformer trans = relayUtil.getTrans("pack.dtd");   //곽경종  Transformer?
		
		// Document xml을 파일로 변환
        trans.transform(new DOMSource(xml), new StreamResult(tmpPackFile));
        
        return tmpPackFile;
	}
	
	/**
     * <pre> 
     *   PackVO를 Xml형태로 변환하여 File로 저장
     * </pre>
     * @param packInfoVO
	 * @throws Exception 
     */
	public File makePackXmlFile(PackInfoVO packInfoVO, File tmpPackFile) throws Exception {
		// pack을 저장하기위한 버퍼
		StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
        	br = new BufferedReader(new FileReader(tmpPackFile));
        	
        	 // pack파일을 버퍼에 넣기
        	String line = "";
	        while((line = br.readLine()) != null){
	        	sb.append(line + "\r\n");
	        }
	        
	        // contents - pubdoc 이후 부분 짤라내기
	        String beforePack = sb.substring(0, sb.indexOf("<contents>") + 10).toString() + "\r\n";
	        // contents부분 이후 짤라내기
	        String afterPack = sb.substring(sb.indexOf("</contents>"), sb.length()).toString();  //곽경종   짤라내는 이유?
	        
        	fw = new FileWriter(tmpPackFile, false);
        	bw = new BufferedWriter(fw);
        	
        	// 파일을 인코딩해서 pack파일 완성
        	bw.write(beforePack);
        	bw.flush();   // 곽경종  ???????
        	
        	for(FileVO fileVO : packInfoVO.getAttach()) {
				for(ContentVO contentVO : packInfoVO.getContents()) {
					if(contentVO.getContentFilename().equals(fileVO.getFileName())) {
						bw.write("<content content-role=\"" + contentVO.getContentContentRole()
									+ "\" content-transfer-encoding=\"" + contentVO.getContentContentTransperEncoding()
									+ "\" filename=\"" + Base64Util.encode(fileVO.getDisplayName().getBytes(AppConfig.getProperty("charset", "EUC-KR", "relay")))
									+ "\" content-type=\"" + contentVO.getContentContentType()
									+ "\" charset=\"" + contentVO.getContentCharset()
									+ "\">\r\n");
						byte[] buf = new byte[54];
						int numRead = 0;
						
						InputStream is = null;
						try {
							is = new FileInputStream(fileVO.getFilePath() + fileVO.getFileName());
							
							while( (numRead = is.read(buf)) != -1) {
								bw.write(Base64Util.encode(buf, 0, numRead) + "\r\n");
							}
							bw.write("</content>\r\n");
							bw.flush();
						}
						catch(Exception e) {
							log.error("+ [Error] RelaySendService.makePackXmlFile() Exception! : " + e.getMessage());
							throw e;
						}
						finally {
							if(is != null) {
								is.close();  //곽경종   close 하는 이유??
							}
						}
						
					}
				}
			}
        	bw.write(afterPack);
			bw.flush();
        }
        catch(Exception e) {
			log.error("+ [Error] RelaySendService.makePackXmlFile() Exception! : " + e.getMessage());
			throw e;
		}
        finally {
        	if(br != null) {
				br.close();
			}
			if(bw != null) {
				bw.close();
			}
			if(fw != null) {
				fw.close();
			}
        }

        return tmpPackFile;
	}
}
