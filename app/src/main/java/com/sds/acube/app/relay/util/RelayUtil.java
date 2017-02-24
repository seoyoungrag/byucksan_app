package com.sds.acube.app.relay.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

import java.util.List;
import java.util.Random;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.CDATASection;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

import com.sds.acube.app.appcom.vo.FileVO;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.Base64Util;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.common.util.LogWrapper;
import com.sds.acube.app.relay.vo.ContentVO;
import com.sds.acube.app.relay.vo.LineInfoVO;
import com.sds.acube.app.relay.vo.PackInfoVO;
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

import java.nio.channels.*;

/**
 * Class Name  : RelayUtil.java<br>
 * Description : 설명<br>
 * Modification Information<br><br>
 * 수 정 일 : 2012. 5. 23<br>
 * 수 정 자 : kimside<br>
 * 수정내용 : <br>
 * @author   kimside 
 * @since    2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.relay.util.RelayUtil.java
 */
public class RelayUtil {
	
    private LogWrapper log = new LogWrapper("com.sds.acube.app.relay");
	
	
	/**
     * <pre> 
     *  문서유통파일 네임 규칙에 필요한 일련번호 랜덤한 2자릿수 만들기
     * </pre>
     * @author 김상태
     * @return String
     * @throws Exception
     */
	public String getSendSeq() throws Exception {
		return String.valueOf(new Random().nextInt(9)) + String.valueOf(new Random().nextInt(9));
	}
	
	/**
     * <pre> 
     *  줄바꿈 Escape문자열 [\n, \r, \r\n] 을 제거.
     * </pre>
     * @author 김상태
     * @param line
     * @return String
     * @throws Exception
     */
	public String removeLineEscape(String line) throws Exception {
		line = line.replaceAll("\r", "");
		line = line.replaceAll("\n", "");
		line = line.replaceAll("\r\n", "");
		//line = line.replaceAll("&#13;", "");
		line = line.replaceAll("&#xD;", "");
		line = line.replaceAll("&#xA;", "");
		return line;
	}
	
	/**
     * <pre> 
     *  하나이상의 수신처를 구분자 ';'로 합치기 (마지막 ';'문자열은 삭제한다)
     * </pre>
     * @author 김상태
     * @param sList
     * @return String
     * @throws Exception
     */
	public String getSumRecvList(List<PackInfoVO> sList) throws Exception {
		String sumRecv = "";
		for(PackInfoVO packInfoVO : sList) {
			sumRecv += packInfoVO.getReceiveId() + ";";
		}
		
		if( sumRecv.substring(sumRecv.length()-1, sumRecv.length()).equals(";") ) {
			sumRecv = sumRecv.substring(0, sumRecv.length()-1);
		}
		
		return sumRecv;
	}
	
	/**
     * <pre> 
     *  문서유통을 위한 Pubdoc - Content부분의 처음 '<!CDATA[' 부분과 끝 ']]>' 부분을 삭제
     * </pre>
     * @author 김상태
     * @param str
     * @return String
     */
	public String stripCDATA(String str) {
		String str_left = "";
		String str_right = "";
		if (str.indexOf("<![CDATA[") != -1) {
			str = str.replaceFirst("<!\\[CDATA\\[", "");
			int i = str.lastIndexOf("]]>");
			str_left = str.substring(0, i);
			str_right = str.substring(i+3);
			str = str_left + str_right;
		} else {
			return str;
		}
		return stripCDATA(str);
	}

	/**
     * <pre> 
     *  해당 XML화일이 UTF-8포맷인지 체크
     * </pre>
     * @author 김상태
     * @param file
     * @return String
     * @throws Exception
     */
	public String checkXmlUTF(File file) throws Exception {
		// UTF-8일 경우 true, 아닐경우 false
		String fileEncoding = "euc-kr";
		
		FileInputStream fis = null;
		InputStreamReader isReader = null;
		BufferedReader bufReader = null;
		
		try {
		   fis = new FileInputStream(file);
		   isReader = new InputStreamReader(fis, "utf-8");
		   bufReader = new BufferedReader(isReader);
           String line = bufReader.readLine();
           line = line.toLowerCase();
           if(line != null) {
        	   if(line.indexOf("encoding") != -1) {
        		   if(line.indexOf("euc-kr") != -1) {
        			   fileEncoding = "euc-kr";
        		   } else if(line.indexOf("utf-8") != -1) {
        			   fileEncoding = "utf-8";
        		   }
        	   }
           }
		} catch (Exception e) {
			log.error("+ [Error] checkXmlUTF Exception! : " + e.getMessage());
			throw e;
		} finally {
			if(bufReader != null) {
				bufReader.close();
			}
			if(isReader != null) {
				isReader.close();
			}
			if(fis != null) {
				fis.close();
			}
		}
		return fileEncoding;
	}
	
	/**
     * <pre> 
     *  XML - Element Value 확인
     * </pre>
     * @author 김상태
     * @param value
     * @param errCode
     * @return String
	 * @throws Exception 
     */
	public String valueCheckException(String value, String errCode) throws Exception {
		if(CommonUtil.isNullOrEmpty(value)){
			throw new SAXException(errCode);
		} else {
			return value;
		}
	}
	
	/**
     * <pre> 
     *  문자열에서 숫자만 뽑아내기
     * </pre>
     * @author 김상태
     * @param str
     * @return String
     */
	public String getOnlyNumber(String str) {
		if(CommonUtil.isNullOrEmpty(str)) {
			return str;
		}
		StringBuffer sb = new StringBuffer();
		for(char curChar : str.toCharArray()) {
			if(Character.isDigit(curChar)) {
				sb.append(curChar);
			}
		}
		return sb.toString();
	}

	
	/**
     * <pre> 
     *  공문서유통 기본 설정을 확인
     * </pre>
     * @author 김상태
     * @return Boolean
	 * @throws Exception 
     */
	public boolean checkInit() throws Exception {
		try {
			
			if(AppConfig.getProperty("relay_send", "N", "relay").toUpperCase().equals("N")) {
				log.info("+ [Error] 문서유통을 사용하지 않음으로 설정되어 있습니다. ");
				return false;
			}
			valueCheckException(AppConfig.getProperty("relay_send", "", "relay"), "relay.error.rel001");
			valueCheckException(AppConfig.getProperty("relay_send_working", "", "relay"), "relay.error.rel002");
			valueCheckException(AppConfig.getProperty("relay_recv", "", "relay"), "relay.error.rel003");
			valueCheckException(AppConfig.getProperty("relay_recvtemp", "", "relay"), "relay.error.rel004");
			valueCheckException(AppConfig.getProperty("relay_recv_working", "", "relay"), "relay.error.rel005");
			valueCheckException(AppConfig.getProperty("relay_dtd", "", "relay"), "relay.error.rel006");
			
			if(CommonUtil.isNullOrEmpty(AppConfig.getProperty("dtd_version", "acube", "relay"))) {
				log.info("+ [Warn] 문서유통 Send GW가 정의되지 않아 'acube'로 지정합니다. ");
			}
			if(CommonUtil.isNullOrEmpty(AppConfig.getProperty("dtd_version", "2.0", "relay"))) {
				log.info("+ [Warn] 문서유통 DTD Version이 정의되지 않아 '2.0'로 지정합니다. ");
			}
			if(CommonUtil.isNullOrEmpty(AppConfig.getProperty("xsl_version", "2.0", "relay"))) {
				log.info("+ [Warn] 문서유통 XSL Version이 정의되지 않아 '2.0'로 지정합니다. ");
			}
			if(CommonUtil.isNullOrEmpty(AppConfig.getProperty("charset", "euc-kr", "relay"))) {
				log.info("+ [Warn] 문서유통 charset이 정의되지 않아 'euc-kr'로 지정합니다. ");
			}
			if(CommonUtil.isNullOrEmpty(AppConfig.getProperty("encoding", "base64", "relay"))) {
				log.info("+ [Warn] 문서유통 charset이 정의되지 않아 'base64'로 지정합니다. ");
			}
			if(CommonUtil.isNullOrEmpty(AppConfig.getProperty("orgcode_use", "Y", "relay"))) {
				log.info("+ [Warn] 문서유통 orgcode_use이 정의되지 않아 'Y'로 지정합니다. ");
			}
			return true;
		}
		catch(Exception e) {
			if(e.getMessage().toLowerCase().equals("relay.error.rel001")) {
				log.error("+ [Error] 문서유통 발송 폴더가 지정되지 않았습니다. ");
			} else if(e.getMessage().toLowerCase().equals("relay.error.rel002")) {
				log.error("+ [Error] 문서유통 발송작업 폴더가 지정되지 않았습니다. ");
			} else if(e.getMessage().toLowerCase().equals("relay.error.rel003")) {
				log.error("+ [Error] 문서유통 수신 폴더가 지정되지 않았습니다. ");
			} else if(e.getMessage().toLowerCase().equals("relay.error.rel004")) {
				log.error("+ [Error] 문서유통 수신임시 폴더가 지정되지 않았습니다. ");
			} else if(e.getMessage().toLowerCase().equals("relay.error.rel005")) {
				log.error("+ [Error] 문서유통 수신작업 폴더가 지정되지 않았습니다. ");
			} else if(e.getMessage().toLowerCase().equals("relay.error.rel006")) {
				log.error("+ [Error] 문서유통 DTD 폴더가 지정되지 않았습니다. ");
			}
			return false;
		}
	}
	
	/**
	 * 
	 * <pre> 
	 *  문서유통 Pack 파일을 작업 폴더에서 전송 폴더로 파일 복사
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
			log.error("+ [Error] RelayUtil.copyFile() Exception! : " + e.getMessage());
			throw e;
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
	 *  문서유통 Pack 생성을 위해 만들었던 파일/폴더들 삭제
	 * </pre>
	 * @param path
	 * @param recursion
	 * @return Boolean
	 */
	/*
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
	*/
	
	/**
	 * 
	 * <pre> 
	 *  Xml을 만들기위한 기본 설정
	 * </pre>
	 * @param DTD
	 * @return Transformer
	 * @throws Exception  
	 */
	public Transformer getTrans(String DTD) throws Exception {
		TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        trans.setOutputProperty(OutputKeys.METHOD, "xml");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
        trans.setOutputProperty(OutputKeys.VERSION, "1.0");
        // Base64언어셋 설정문제로 주석처리
        trans.setOutputProperty(OutputKeys.ENCODING, AppConfig.getProperty("charset", "", "relay"));
        trans.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, DTD);
        return trans;
	}
	
	/**
     * <pre> 
     *  Pubdoc - Head 부분 DOM 반환
     * </pre>
     * @author 김상태
     * @param PackInfoVO
     * @return Document
	 * @throws DOMException 
	 * @throws Exception 
     */
	public Document getHeadXml(PackInfoVO packInfoVO) throws DOMException, Exception {
		Document xml = new DocumentImpl();

		// Element : head
		Element head = xml.createElement("head");
		xml.appendChild(head);
		
		// Element : organ
		Element organ = xml.createElement("organ");
    	organ.setTextContent(packInfoVO.getOrgan());
		head.appendChild(organ);
		
		// Element : receiptinfo
		Element receiptinfo = xml.createElement("receiptinfo");
		head.appendChild(receiptinfo);
		
		
		// Element : recipient
		Element recipient = xml.createElement("recipient");
		if(packInfoVO.getSendCount() > 1){
			recipient.setAttribute("refer", "true");
		} else {
			recipient.setAttribute("refer", "false");
		}
		receiptinfo.appendChild(recipient);
		
		// Element : rec
		Element rec = xml.createElement("rec");
		rec.setTextContent(packInfoVO.getRec());
		recipient.appendChild(rec);
		
		// Element : via
		Element via = xml.createElement("via");
		via.setTextContent(packInfoVO.getVia());
		
		if(!packInfoVO.getVia().equals("")) {
			receiptinfo.appendChild(via);
		}

		return xml;
	}
	
	/**
     * <pre> 
     *  Pubdoc - Body 부분 DOM 반환
     * </pre>
     * @author 김상태
     * @param pack
     * @return Document
	 * @throws Exception 
	 * @throws DOMException 
     */
	public Document getBodyXml(PackInfoVO packInfoVO) throws DOMException, Exception {
		Document xml = new DocumentImpl();
		
		// Element : title
		Element body = xml.createElement("body");
		body.setAttribute("separate", packInfoVO.getSeparate());
		xml.appendChild(body);
		
		// Element : title
		Element title = xml.createElement("title");
		title.setTextContent(packInfoVO.getTitle());
		body.appendChild(title);
		
		// Element : contents
		Element content = xml.createElement("content");
		// CDATA
		CDATASection pcdata = xml.createCDATASection(packInfoVO.getBodyContent());
		content.appendChild(pcdata);
		body.appendChild(content);
		
		return xml;
	}
	
	/**
     * <pre> 
     *  Pubdoc - Foot 부분 DOM 반환
     * </pre>
     * @author 김상태
     * @param pack
     * @return Document
	 * @throws Exception 
	 * @throws DOMException 
     */
	public Document getFootXml(PackInfoVO packInfoVO) throws DOMException, Exception {
		Document xml = new DocumentImpl();
		boolean checking = true;

		// Element : foot
		Element foot = xml.createElement("foot");
		xml.appendChild(foot);
		
		// Element : sendername
		Element sendername = xml.createElement("sendername");
		sendername.setTextContent(packInfoVO.getSenderTitle());
		foot.appendChild(sendername);
		
		// Element : seal ( 첨부파일중 관인 또는 서명인이 있을경우는 false 없을경우는 true )
		Element seal = xml.createElement("seal");
		foot.appendChild(seal);
		for(FileVO fileVO : packInfoVO.getAttach()) {
			if(fileVO.getFileType().toUpperCase().equals("AFT005") || fileVO.getFileType().toUpperCase().equals("AFT006")) {
				checking = false;
				seal.setAttribute("omit", "false");
				
				// Element : seal - img
				Element img = xml.createElement("img");
				img.setAttribute("src", fileVO.getDisplayName());
				img.setAttribute("alt", fileVO.getDisplayName());
				/* 이미지 크기를 못가져와서 일단 막아 둡니다.
				img.setAttribute("width", String.valueOf(fileVO.getImageWidth()));
				img.setAttribute("height", String.valueOf(fileVO.getImageHeight()));
				*/
				
				seal.appendChild(img);
			}
		}
		
		if(checking) {
			seal.setAttribute("omit", "true");
		}

		// Element : approvalinfo
		Element approvalinfo = xml.createElement("approvalinfo");
		foot.appendChild(approvalinfo);
		
		// Element : approval
		for(LineInfoVO lineInfo : packInfoVO.getApproval()) {
			if(Integer.parseInt(lineInfo.getLineOrder()) == packInfoVO.getApproval().size()){
				lineInfo.setLineOrder("final");
			}
			Element line = (Element) xml.importNode(getLineListXml(lineInfo, packInfoVO.getAttach(), "approval").getDocumentElement(), true);
			approvalinfo.appendChild(line);
		}
		
		// Element : assist
		for(LineInfoVO lineInfo : packInfoVO.getAssist()) {
			if(Integer.parseInt(lineInfo.getLineOrder()) == packInfoVO.getAssist().size() ){
				lineInfo.setLineOrder("final");
			}
			Element line = (Element) xml.importNode(getLineListXml(lineInfo, packInfoVO.getAttach(), "assist").getDocumentElement(), true);
			approvalinfo.appendChild(line);
		}

		// Element : processinfo
		Element processinfo = xml.createElement("processinfo");
		foot.appendChild(processinfo);
		
		// Element : regnumber
		Element regnumber = xml.createElement("regnumber");
		regnumber.setAttribute("regnumbercode", packInfoVO.getRegNumberCode());
		regnumber.setTextContent(packInfoVO.getRegNumber());
		processinfo.appendChild(regnumber);
		
		// Element : enforcedate
		Element enforcedate = xml.createElement("enforcedate");
		enforcedate.setTextContent(packInfoVO.getEnforceDate());
		processinfo.appendChild(enforcedate);
		
		// Element : sendinfo
		Element sendinfo = xml.createElement("sendinfo");
		foot.appendChild(sendinfo);
		
		// Element : zipcode
		Element zipcode = xml.createElement("zipcode");
		zipcode.setTextContent(packInfoVO.getZipcode());
		sendinfo.appendChild(zipcode);
		
		// Element : address
		Element address = xml.createElement("address");
		address.setTextContent(packInfoVO.getAddress());
		sendinfo.appendChild(address);
		
		// Element : homeurl
		Element homeurl = xml.createElement("homeurl");
		homeurl.setTextContent(packInfoVO.getHomeUrl());
		if(!packInfoVO.getHomeUrl().equals("")) {
			sendinfo.appendChild(homeurl);
		}
		
		// Element : email
		Element email = xml.createElement("email");
		email.setTextContent(packInfoVO.getEmail());
		if(!packInfoVO.getEmail().equals("")) {
			sendinfo.appendChild(email);
		}
		
		// Element : telephone
		Element telephone = xml.createElement("telephone");
		telephone.setTextContent(packInfoVO.getTelephone());
		sendinfo.appendChild(telephone);
		
		// Element : fax
		Element fax = xml.createElement("fax");
		fax.setTextContent(packInfoVO.getFax());
		sendinfo.appendChild(fax);
		
		// Element : publication
		Element publication = xml.createElement("publication");
		publication.setAttribute("code", packInfoVO.getPublication());
		if(packInfoVO.getPublication().substring(0, 1).equals("1")) {
			publication.setTextContent("공개");
		} else if(packInfoVO.getPublication().substring(0, 1).equals("2")){
			publication.setTextContent("부분공개");
		} else {
			publication.setTextContent("비공개");
		}
		sendinfo.appendChild(publication);

		for(FileVO fileVO : packInfoVO.getAttach()) {
			if(fileVO.getFileType().toUpperCase().equals("AFT009")) {
				// Element : symbol
				Element symbol = xml.createElement("symbol");
				
				// Element : symbol - img
				Element img = xml.createElement("img");
				img.setAttribute("src", fileVO.getDisplayName());
				img.setAttribute("alt", fileVO.getDisplayName());
				/* 이미지 크기를 못가져와서 일단 막아 둡니다.
				img.setAttribute("width", String.valueOf(fileVO.getImageWidth()));
				img.setAttribute("height", String.valueOf(fileVO.getImageHeight()));
				*/
				
				symbol.appendChild(img);
				sendinfo.appendChild(symbol);
			} else if(fileVO.getFileType().toUpperCase().equals("AFT008")) {
				// Element : logo
				Element logo = xml.createElement("logo");
				
				// Element : logo - img
				Element img = xml.createElement("img");
				img.setAttribute("src", fileVO.getDisplayName());
				img.setAttribute("alt", fileVO.getDisplayName());
				/* 이미지 크기를 못가져와서 일단 막아 둡니다.
				img.setAttribute("width", String.valueOf(fileVO.getImageWidth()));
				img.setAttribute("height", String.valueOf(fileVO.getImageHeight()));
				*/
				
				logo.appendChild(img);
				sendinfo.appendChild(logo);
			}
		}
		
		// Element : campaign
		if(!packInfoVO.getHeadCampaign().equals("") || !packInfoVO.getFootCampaign().equals("")) {
			Element campaign = xml.createElement("campaign");
			
			// Element : headcampaign
			if(!packInfoVO.getHeadCampaign().equals("")) {
				Element headcampaign = xml.createElement("headcampaign");
				headcampaign.setTextContent(packInfoVO.getHeadCampaign());
				campaign.appendChild(headcampaign);
			}
			
			// Element : footcampaign
			if(!packInfoVO.getFootCampaign().equals("")) {
				Element footcampaign = xml.createElement("footcampaign");
				footcampaign.setTextContent(packInfoVO.getFootCampaign());
				campaign.appendChild(footcampaign);
			}

			foot.appendChild(campaign);
		}
		
		return xml;
	}
	
	/**
     * <pre> 
     *  Pubdoc - Attach 부분 DOM 반환
     * </pre>
     * @author 김상태
     * @param packInfoVO
     * @return Document
     */
	public Document getAttachXml(PackInfoVO packInfoVO) {
		Document xml = new DocumentImpl();
		
		// Element : attach
		Element attach = xml.createElement("attach");
		xml.appendChild(attach);
		
		for(FileVO fileVO : packInfoVO.getAttach()) {
			if(fileVO.getFileType().toUpperCase().equals("AFT004")) {
				// Element : title
				Element att_title = xml.createElement("title");
				att_title.setTextContent(fileVO.getDisplayName());
				attach.appendChild(att_title);
			}
		}
		
		return xml;
	}
	
	/**
     * <pre> 
     *  Pubdoc - foot - approvalinfo 결재, 협조 라인 DOM 반환
     * </pre>
     * @author 김상태
     * @param lineInfo
     * @param attachVOs
     * @param lineName
     * @return Document
	 * @throws Exception 
	 * @throws DOMException 
     */
	public Document getLineListXml(LineInfoVO lineInfo, List<FileVO> attachVOs, String lineName) throws DOMException, Exception {
		Document xml = new DocumentImpl();
		Element line = xml.createElement(lineName);
		
		line.setAttribute("order", lineInfo.getLineOrder());
		
		// Element : signposition
		Element signposition = xml.createElement("signposition");
		signposition.setTextContent(lineInfo.getSignposition());
		line.appendChild(signposition);
		
		// Element : type
		Element type = xml.createElement("type");
		if(lineInfo.getType().toUpperCase().equals("ART010")) {
			type.setTextContent("기안");
		} else if(lineInfo.getType().toUpperCase().equals("ART020")) {
			type.setTextContent("검토");
		} else if(lineInfo.getType().toUpperCase().equals("ART050")) {
			type.setTextContent("결재");
		} else if(lineInfo.getType().toUpperCase().equals("ART051")) {
			type.setTextContent("전결");
		} else if(lineInfo.getType().toUpperCase().equals("ART052")) {
			type.setTextContent("대결");
		} else if(lineInfo.getType().toUpperCase().equals("ART053")) { // 1인 결재시 공문서 유통에 타입이 없어서 전결로 발송함
			type.setTextContent("전결");
		} else if(lineInfo.getType().toUpperCase().equals("ART030")) {
			type.setTextContent("협조");
		}
		line.appendChild(type);
		
		
		// Element : name
		Element name = xml.createElement("name");
		name.setTextContent(lineInfo.getName());
		if(lineName.toLowerCase().equals("approval")) {
			line.appendChild(name);
		} else if(!CommonUtil.isNullOrEmpty(lineInfo.getName())){
			line.appendChild(name);
		}
		
		if(!CommonUtil.isNullOrEmpty(lineInfo.getSignimage())){
			// Element : signimage
			Element signimage = xml.createElement("signimage");
			
			for(FileVO fileVO : attachVOs) {
				if(fileVO.getDisplayName().equals(lineInfo.getSignimage())) {
					Element img = xml.createElement("img");
					img.setAttribute("src", fileVO.getDisplayName());
					img.setAttribute("alt", lineInfo.getName());
					/* 이미지 크기를 못가져와서 일단 막아 둡니다.
					img.setAttribute("width", String.valueOf(fileVO.getImageWidth()));
					img.setAttribute("height", String.valueOf(fileVO.getImageHeight()));
					 */
					signimage.appendChild(img);
				}
			}
			
			line.appendChild(signimage);
		}
		
		// Element : date
		Element date = xml.createElement("date");
		date.setTextContent(lineInfo.getPdate());
		line.appendChild(date);
		
		// Element : time
		Element time = xml.createElement("time");
		time.setTextContent(lineInfo.getPtime());
		
		line.appendChild(time);
		xml.appendChild(line);

		return xml;
	}
	
	/**
     * <pre> 
     *  Pack DOM변환
     * </pre>
     * @author 김상태
     * @param packInfoVO
     * @return Document
	 * @throws DOMException, Exception 
     */
	public Document getMakePackXml(PackInfoVO packInfoVO) throws DOMException, Exception {
		Document xml = new DocumentImpl();
		
		// Element : pack
		Element pack = xml.createElement("pack");
		// 공문서 유통표준에는 pack(Elemnet)의 filename(Attribute)를 필수요소로 기재하지만
		// 실제 문서유통시에는 filename(Attribute) 넣으면 오류가 발생함
		// 일단 제외하고 문서를 만들기로 결정 (2012-04-05 : 유진 수석-김상태)
		// pack.setAttribute("filename", packVO.getFilename());
		xml.appendChild(pack);
		
		// Element : header
		Element header = xml.createElement("header");
		pack.appendChild(header);
		
		// Element : send-orgcode
		Element send_orgcode = xml.createElement("send-orgcode");
		send_orgcode.setTextContent(packInfoVO.getSendOrgCode());
		header.appendChild(send_orgcode);
		
		// Element : send-id
		Element send_id = xml.createElement("send-id");
		send_id.setTextContent(packInfoVO.getSendDeptId());
		header.appendChild(send_id);
		
		// Element : send-name
		Element send_name = xml.createElement("send-name");
		send_name.setTextContent(Base64Util.encode(packInfoVO.getSendOrgName().getBytes(AppConfig.getProperty("charset", "EUC-KR", "relay"))));
		header.appendChild(send_name);
		
		// Element : receive-id
		Element receive_id = xml.createElement("receive-id");
		receive_id.setTextContent(packInfoVO.getReceiveId());
		header.appendChild(receive_id);
		
		// Element : date
		Element date = xml.createElement("date");
		date.setTextContent(packInfoVO.getSendDate());
		header.appendChild(date);
		
		// Element : title
		Element title = xml.createElement("title");
		title.setTextContent(Base64Util.encode(packInfoVO.getTitle().getBytes(AppConfig.getProperty("charset", "EUC-KR", "relay"))));
		header.appendChild(title);
		
		// Element : doc-id
		Element doc_id = xml.createElement("doc-id");
		doc_id.setTextContent(packInfoVO.getDocId());
		header.appendChild(doc_id);
		
		// Element : doc-type
		Element doc_type = xml.createElement("doc-type");
		doc_type.setAttribute("dept", Base64Util.encode(packInfoVO.getSendDeptName().getBytes(AppConfig.getProperty("charset", "EUC-KR", "relay"))));
		doc_type.setAttribute("name", Base64Util.encode(packInfoVO.getSendName().getBytes(AppConfig.getProperty("charset", "EUC-KR", "relay"))));
		doc_type.setAttribute("type", packInfoVO.getDocType());
		header.appendChild(doc_type);
		
		// Element : send-gw
		Element send_gw = xml.createElement("send-gw");
		send_gw.setTextContent(Base64Util.encode(packInfoVO.getSendGw().getBytes(AppConfig.getProperty("charset", "EUC-KR", "relay"))));
		header.appendChild(send_gw);
		
		// Element : dtd-version
		Element dtd_version = xml.createElement("dtd-version");
		dtd_version.setTextContent(packInfoVO.getDtdVersion());
		header.appendChild(dtd_version);
		
		// Element : xsl-version
		Element xsl_version = xml.createElement("xsl-version");
		xsl_version.setTextContent(packInfoVO.getXslVersion());
		header.appendChild(xsl_version);
		
		// Element : contents
		Element contents = xml.createElement("contents");
		pack.appendChild(contents);
		for(ContentVO contentVO : packInfoVO.getContents()) {
			Element content = xml.createElement("content");
			content.setAttribute("content-role", contentVO.getContentContentRole());
			content.setAttribute("content-transfer-encoding", contentVO.getContentContentTransperEncoding());
			content.setAttribute("filename", Base64Util.encode(contentVO.getContentFilename().getBytes(AppConfig.getProperty("charset", "EUC-KR", "relay"))));
			content.setAttribute("content-type", contentVO.getContentContentType());
			content.setAttribute("charset", contentVO.getContentCharset());
			contents.appendChild(content);
		}
		
		return xml;
	}
	
	/**
     * <pre> 
     *  XPaht를 이용한 Xml NodeList 가져오기
     * </pre>
     * @author 김상태
     * @param xml
     * @param xPath
     * @return NodeList
	 * @throws Exception 
     */
	public NodeList getXmlList(Document xml, String xPath) throws Exception {
		XPath xpath = XPathFactory.newInstance().newXPath();
		return (NodeList)xpath.evaluate(xPath, xml, XPathConstants.NODESET);
	}
	
	/**
     * <pre> 
     *  XPaht를 이용한 Xml Element/Attribute Text 값 가져오기
     * </pre>
     * @author 김상태
     * @param xml
     * @param xPath
     * @param errCode
     * @return String
	 * @throws Exception 
     */
	public String getXmlText(Document xml, String xPath, String errCode) throws Exception {
		XPath xpath = XPathFactory.newInstance().newXPath();
		if(!errCode.equals("")) {
			return valueCheckException(String.valueOf(xpath.evaluate(xPath, xml, XPathConstants.STRING)), errCode);
		} else {
			return String.valueOf(xpath.evaluate(xPath, xml, XPathConstants.STRING));
		}
	}
	
	/**
     * <pre> 
     *  XPaht를 이용한 Xml Element 가져오기
     * </pre>
     * @author 김상태
     * @param xml
     * @param xPath
     * @param errCode
     * @return String
	 * @throws Exception 
     */
	public void getXmlElement(Document xml, String xPath, String errCode) throws Exception {
		XPath xpath = XPathFactory.newInstance().newXPath();
		if(!errCode.equals("")) {
			if(xpath.evaluate(xPath, xml, XPathConstants.BOOLEAN).toString().equals("false")){
				valueCheckException("", errCode);
			}
		}
	}
	
	/**
     * <pre> 
     *  Xml에서 Content내용을 추출하여 ContentVO로 만들기
     * </pre>
     * @author 김상태
     * @param xml
     * @param xPath
     * @param errCode
     * @return ContentVO
	 * @throws Exception 
     */
	public ContentVO unContent(Element ele) throws Exception {
		ContentVO contentVO = new ContentVO();
		contentVO.setContentContentTransperEncoding(ele.getAttribute("content-transfer-encoding"));
		contentVO.setContentContentRole(ele.getAttribute("content-role"));
		contentVO.setContentCharset(ele.getAttribute("charset"));
		contentVO.setContentFilename(new String(Base64Util.decode(ele.getAttribute("filename")), AppConfig.getProperty("charset", "EUC-KR", "relay")));
		contentVO.setContentContentType(ele.getAttribute("content-type"));
		contentVO.setContentStorFileName(GuidUtil.getGUID() + "." + CommonUtil.getExt(new String(Base64Util.decode(ele.getAttribute("filename")), AppConfig.getProperty("charset", "EUC-KR", "relay"))));
		return contentVO;
	}

	/**
     * <pre> 
     *  수신한 문서유통파일 해체하여 PackInfoVO에 넣기 (pack.xml)
     *  </pre>
     * @author 김상태
     * @param packInfoVO
	 * @throws Exception 
     */
	public void unPack(PackInfoVO packInfoVO, Document xml, File recvFile) throws Exception {
		packInfoVO.setSendOrgCode(getXmlText(xml, "/pack/header/send-orgcode", "relay.error.rel150"));
		packInfoVO.setSendDeptId(getXmlText(xml, "/pack/header/send-id", "relay.error.rel151"));
		packInfoVO.setSendOrgName(new String(Base64Util.decode(getXmlText(xml, "/pack/header/send-name", "relay.error.rel152")), AppConfig.getProperty("charset", "EUC-KR", "relay")));
		packInfoVO.setReceiveId(getXmlText(xml, "/pack/header/receive-id", "relay.error.rel153"));
		packInfoVO.setSendDate(getXmlText(xml, "/pack/header/date", "relay.error.rel154"));
		packInfoVO.setTitle(new String(Base64Util.decode(getXmlText(xml, "/pack/header/title", "relay.error.rel155")), AppConfig.getProperty("charset", "EUC-KR", "relay")));
		packInfoVO.setDocId(getXmlText(xml, "/pack/header/doc-id", "relay.error.rel156"));
		packInfoVO.setSendDeptName(new String(Base64Util.decode(getXmlText(xml, "/pack/header/doc-type/@dept", "")), AppConfig.getProperty("charset", "EUC-KR", "relay")));
		packInfoVO.setSendName(new String(Base64Util.decode(getXmlText(xml, "/pack/header/doc-type/@name", "")), AppConfig.getProperty("charset", "EUC-KR", "relay")));
		packInfoVO.setDocType(getXmlText(xml, "/pack/header/doc-type/@type", "relay.error.rel159"));
		packInfoVO.setSendGw(new String(Base64Util.decode(getXmlText(xml, "/pack/header/send-gw", "relay.error.rel160")), AppConfig.getProperty("charset", "EUC-KR", "relay")));
		packInfoVO.setDtdVersion(getXmlText(xml, "/pack/header/dtd-version", "relay.error.rel161"));
		packInfoVO.setXslVersion(getXmlText(xml, "/pack/header/xsl-version", "relay.error.rel162"));
		
		getXmlElement(xml, "/pack", "relay.error.rel101");
		getXmlElement(xml, "/pack/header", "relay.error.rel102");
		getXmlElement(xml, "/pack/header/doc-type", "relay.error.rel103");
		getXmlElement(xml, "/pack/contents", "relay.error.rel104");
		getXmlElement(xml, "/pack/header/doc-type/@type", "relay.error.rel106");
		getXmlElement(xml, "/pack/header/doc-type/@dept", "relay.error.rel107");
		getXmlElement(xml, "/pack/header/doc-type/@name", "relay.error.rel108");
		
		String workBase = recvFile.getPath().replaceAll("\\\\", "/");
		String sendFolder = AppConfig.getProperty("relay_send_working", "", "relay").replaceAll("\\\\", "/");

		FileReader regularReader =  new FileReader(recvFile);
		BufferedReader regularBr = new BufferedReader(regularReader);

		FileReader reader = null;
		BufferedReader br = null;
		FileOutputStream regular = null;
		
		try {
			String line = "";
			String lineEtc = "";
			String subString = "";
			
			regular = new FileOutputStream(recvFile.getParent() + "/regularPack.xml");
			while((line = regularBr.readLine()) != null){
				line = line.replaceAll("><", ">\r\n<");
				regular.write((line + "\r\n").getBytes());
			}
			regular.flush();
			regular.close();
			
			reader =  new FileReader(new File(recvFile.getParent() + "/regularPack.xml"));
			br = new BufferedReader(reader);
			
			while((line = br.readLine()) != null){
				if(line.indexOf("<content ") != -1) {
					ContentVO contentVO = new ContentVO();
					
					// <content 엘리먼트 전체가져오기
					while(line.indexOf(">") == -1) {
						line += br.readLine();
						line = line.replaceAll("\'", "\"");
						line = removeLineEscape(line);
						line = line.trim();
					}

					// 구분
					if(line.indexOf("content-role=\"") != -1) {
						subString = line.substring(line.indexOf("content-role=\"") + 14);
						subString = subString.substring(0, subString.indexOf("\""));
						contentVO.setContentContentRole(subString);
					}
					
					// 변환코드
					if(line.indexOf("content-transfer-encoding=\"") != -1) {
						subString = line.substring(line.indexOf("content-transfer-encoding=\"") + 27);
						subString = subString.substring(0, subString.indexOf("\""));
						contentVO.setContentContentTransperEncoding(subString);
					}
					
					// 파일속성
					if(line.indexOf("content-type=\"") != -1) {
						subString = line.substring(line.indexOf("content-type=\"") + 14);
						subString = subString.substring(0, subString.indexOf("\""));
						contentVO.setContentContentType(subString);
					}
					
					// 파일속성
					if(line.indexOf("charset=\"") != -1) {
						subString = line.substring(line.indexOf("charset=\"") + 9);
						subString = subString.substring(0, subString.indexOf("\""));
						contentVO.setContentCharset(subString);
					}
					
					// 파일명
					if(line.indexOf("filename=\"") != -1) {
						subString = line.substring(line.indexOf("filename=\"") + 10);
						subString = subString.substring(0, subString.indexOf("\""));

						if(workBase.indexOf(sendFolder) == -1) {
							if(CommonUtil.isNullOrEmpty(subString)) {
								contentVO.setContentFilename("return.txt");
								contentVO.setContentStorFileName(GuidUtil.getGUID() + ".txt");
							} else {
								String fileName = GuidUtil.getGUID() + "." + CommonUtil.getExt(new String(Base64Util.decode(subString), AppConfig.getProperty("charset", "EUC-KR", "relay")));
								contentVO.setContentFilename(new String(Base64Util.decode(subString), AppConfig.getProperty("charset", "EUC-KR", "relay")));
								contentVO.setContentStorFileName(fileName);
							}
						} else {
							contentVO.setContentFilename(new String(Base64Util.decode(subString), AppConfig.getProperty("charset", "EUC-KR", "relay")));
							contentVO.setContentStorFileName(new String(Base64Util.decode(subString), AppConfig.getProperty("charset", "EUC-KR", "relay")));
						}
					}
					
					// 메세지 내용
					if(line.indexOf("/>") == -1) {
						if(workBase.indexOf(sendFolder) == -1) {
							FileOutputStream fos = null;
							try {
								fos = new FileOutputStream(recvFile.getParent() + "/" + contentVO.getContentStorFileName());

								if(line.indexOf("</content>") != -1) {
									lineEtc = line.substring(line.indexOf(">") + 1, line.indexOf("</content>"));
									fos.write(Base64Util.decode(removeLineEscape(lineEtc)));
								} else {
									fos.write(Base64Util.decode(removeLineEscape(line.substring(line.indexOf(">") + 1))));
									while((line = br.readLine()) != null) {
										if(line.indexOf("</content>") != -1) {
											line = line.substring(0, line.indexOf("</content>"));
											if(CommonUtil.isNullOrEmpty(removeLineEscape(line))){
												break;
											} else {
												fos.write(Base64Util.decode(removeLineEscape(line)));
												break;
											}
										}
										fos.write(Base64Util.decode(removeLineEscape(line)));
									}
								}
								fos.flush();
							}
							catch(Exception e){
								log.error("+ [Error] RelayUtil.unPack() Exception! : " + e.getMessage());
								throw e;
							}
							finally {
								if(fos != null) {
									fos.close();
								}
							}
						}
					}
					packInfoVO.getContents().add(contentVO);
				}
			}
		}
		catch(Exception e) {
			log.error("+ [Error] RelayUtil.unPack() Exception! : " + e.getMessage());
			throw e;
		}
		finally {
			if(br != null) {
				br.close();
			}
			if(reader != null) {
				reader.close();
			}
			if(regular != null){
				regular.close();
			}
			if(regularBr != null) {
				regularBr.close();
			}
			if(regularReader != null){
				regularReader.close();
			}
			
		}

		if(workBase.indexOf(sendFolder) == -1) {
			unContentMakeFile(packInfoVO);
		}
	}
	
	/**
     * <pre> 
     *  Xml에서 추출한 Content내용 File로 만들기
     *  </pre>
     * @author 김상태
     * @param packInfoVO
	 * @throws Exception
     */
	public void unContentMakeFile(PackInfoVO packInfoVO) throws Exception {
		int fileOrder = 1;
		for(ContentVO contentVO : packInfoVO.getContents()) {
			FileVO fileVO = new FileVO();
			String makeFile = AppConfig.getProperty("relay_recv_working", "", "relay")
							+ "/" + packInfoVO.getFilename().substring(0, 7)
							+ "/" + packInfoVO.getFilename().substring(0, 30)
							+ "/";
			File file = new File(makeFile + contentVO.getContentStorFileName());
			
			if(contentVO.getContentContentRole().toLowerCase().equals("pubdoc")) {
				FileInputStream fis = null;
				InputStreamReader isReader = null;
				BufferedReader bufReader = null;
				
				String line = "";
				String content = "";
					
				try {
					fis =  new FileInputStream(makeFile + "/" + contentVO.getContentStorFileName());
					isReader = new InputStreamReader(fis, AppConfig.getProperty("charset", "EUC-KR", "relay"));
					bufReader = new BufferedReader(isReader);

					while((line = bufReader.readLine()) != null) {
						content += line + "\r\n";
					}
				}
				catch (Exception e){
					log.error("+ [Error] RelayUtil.unContentMakeFile() Exception! : " + e.getMessage());
					throw e;
				}
				finally {
					if(fis != null) {
						fis.close();
					}
				}
				
				String beforeContent = "<?xml version=\"1.0\" encoding=\"EUC-KR\"?>\n"
										+ "<!DOCTYPE pubdoc [<!ENTITY nbsp \"&#160;\">]>\n"
										+ "<pubdoc>\n"
										+ "<body>\n"
										+ "<content>\n";
				String afterContent = "</content>\n"
										+ "</body>\n"
										+ "</pubdoc>";
				int spos = 0;
				int epos = content.length();
				
				spos = content.indexOf("<content>") + 9;
				epos = content.lastIndexOf("</content>");
				
				content = content.substring(spos, epos);
				
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(makeFile + "/content.xml");
					fos.write((beforeContent + content + afterContent).getBytes(AppConfig.getProperty("charset", "EUC-KR", "relay")));
					fos.close();
				}
				catch (Exception e) {
					log.error("+ [Error] RelayUtil.unContentMakeFile() Exception! : " + e.getMessage());
					throw e;
				}
				finally {
					if(fos != null) {
						fos.close();
					}
				}	
				
				FileVO contentFileVO = new FileVO();
				File contentFile = new File(makeFile + "/content.xml");
				
				contentFileVO.setFileType("AFT011");
				contentFileVO.setFilePath(makeFile);
				contentFileVO.setCompId(packInfoVO.getFilename().substring(7, 14));
				contentFileVO.setProcessorId(AppConfig.getProperty("relay_adminUid", "", "relay"));
				contentFileVO.setDocId(packInfoVO.getDocId());
				contentFileVO.setDisplayName("content.xml");
				contentFileVO.setFileName(GuidUtil.getGUID() + ".xml");
				contentFileVO.setRegisterId(AppConfig.getProperty("relay_adminUid", "", "relay"));
				contentFileVO.setRegisterName(AppConfig.getProperty("relay_adminName", "", "relay"));
				contentFileVO.setFileOrder(fileOrder++);
				contentFileVO.setFileSize((int)contentFile.length());
				contentFileVO.setTempYn("N");
				
				packInfoVO.getAttach().add(contentFileVO);

				fileVO.setFileType("AFT012");
			} else if(contentVO.getContentContentRole().toLowerCase().equals("attach_body")) {
				fileVO.setFileType("AFT013");
				packInfoVO.setSeparate("true");
			} else if(contentVO.getContentContentRole().toLowerCase().equals("attach")) {
				fileVO.setFileType("AFT004");
			} else if(contentVO.getContentContentRole().toLowerCase().equals("seal")) {
				fileVO.setFileType("AFT005");
			} else if(contentVO.getContentContentRole().toLowerCase().equals("sign")) {
				fileVO.setFileType("AFT007");
			} else if(contentVO.getContentContentRole().toLowerCase().equals("logo")) {
				fileVO.setFileType("AFT008");
			} else if(contentVO.getContentContentRole().toLowerCase().equals("symbol")) {
				fileVO.setFileType("AFT009");
			}
			
			fileVO.setFilePath(makeFile);
			fileVO.setCompId(packInfoVO.getFilename().substring(7, 14));
			fileVO.setProcessorId(AppConfig.getProperty("relay_adminUid", "", "relay"));
			fileVO.setDocId(packInfoVO.getDocId());
			fileVO.setDisplayName(contentVO.getContentFilename());
			fileVO.setFileName(contentVO.getContentStorFileName());
			fileVO.setRegisterId(AppConfig.getProperty("relay_adminUid", "", "relay"));
			fileVO.setRegisterName(AppConfig.getProperty("relay_adminName", "", "relay"));
			fileVO.setFileOrder(fileOrder++);
			fileVO.setFileSize((int)file.length());
			fileVO.setTempYn("N");
			packInfoVO.getAttach().add(fileVO);

		}
	}
	
	/**
     * <pre> 
     *  수신한 문서유통파일 해체하여 PackInfoVO에 넣기 (pubdoc.xml)
     *  </pre>
     * @author 김상태
     * @param packInfoVO
	 * @throws Exception 
     */
	public void unPubdoc(PackInfoVO packInfoVO, Document xml) throws SAXException, Exception {
		packInfoVO.setOrgan(getXmlText(xml, "/pubdoc/head/organ", ""));
		packInfoVO.setRec(getXmlText(xml, "/pubdoc/head/receiptinfo/recipient/rec", ""));
		packInfoVO.setRefer(getXmlText(xml, "/pubdoc/head/receiptinfo/recipient/@refer", ""));
		packInfoVO.setVia(getXmlText(xml, "/pubdoc/head/receiptinfo/via", ""));
		
		packInfoVO.setSeparate(getXmlText(xml, "/pubdoc/body/@separate", ""));
		packInfoVO.setTitle(getXmlText(xml, "/pubdoc/body/title", "relay.error.rel208"));
		packInfoVO.setBodyContent(getXmlText(xml, "/pubdoc/body/content", "relay.error.rel209"));
		
		packInfoVO.setSenderTitle(getXmlText(xml, "/pubdoc/foot/sendername", "relay.error.rel211"));
		packInfoVO.setOmit(getXmlText(xml, "/pubdoc/foot/seal/@omit", ""));
		packInfoVO.setRegNumber(getXmlText(xml, "/pubdoc/foot/processinfo/regnumber", "relay.error.rel221"));
		packInfoVO.setRegNumberCode(getXmlText(xml, "/pubdoc/foot/processinfo/regnumber/@regnumbercode", "relay.error.rel264"));
		packInfoVO.setEnforceDate(getXmlText(xml, "/pubdoc/foot/processinfo/enforcedate", "relay.error.rel222"));
		packInfoVO.setReceiptNumber(getXmlText(xml, "/pubdoc/foot/processinfo/receipt/number", ""));
		packInfoVO.setReceiptDate(getXmlText(xml, "/pubdoc/foot/processinfo/receipt/number/date", ""));
		packInfoVO.setReceiptTime(getXmlText(xml, "/pubdoc/foot/processinfo/number/time", ""));
		packInfoVO.setZipcode(getXmlText(xml, "/pubdoc/foot/sendinfo/zipcode", ""));
		packInfoVO.setAddress(getXmlText(xml, "/pubdoc/foot/sendinfo/address", ""));
		packInfoVO.setHomeUrl(getXmlText(xml, "/pubdoc/foot/sendinfo/homeurl", ""));
		packInfoVO.setTelephone(getXmlText(xml, "/pubdoc/foot/sendinfo/telephone", ""));
		packInfoVO.setFax(getXmlText(xml, "/pubdoc/foot/sendinfo/fax", ""));
		packInfoVO.setEmail(getXmlText(xml, "/pubdoc/foot/sendinfo/email", ""));
		packInfoVO.setPublication(getXmlText(xml, "/pubdoc/foot/sendinfo/publication/@code", "relay.error.rel231"));
		packInfoVO.setPublicationText(getXmlText(xml, "/pubdoc/foot/sendinfo/publication", "relay.error.rel231"));
		packInfoVO.setHeadCampaign(getXmlText(xml, "/pubdoc/foot/campaign/headcampaign", ""));
		packInfoVO.setFootCampaign(getXmlText(xml, "/pubdoc/foot/campaign/footcampaign", ""));
		
		String omit = getXmlText(xml, "/pubdoc/foot/seal/@omit", "");
		if(omit.toLowerCase().equals("false")){
			getXmlText(xml, "/pubdoc/foot/seal/img/@src", "relay.error.rel256");
			String seal = getXmlText(xml, "/pubdoc/foot/seal/img/@src", "relay.error.rel256");
			for(FileVO fileVO : packInfoVO.getAttach()) {
				if(fileVO.getDisplayName().equals(seal)) {
					String size = "";
					size = getOnlyNumber(getXmlText(xml, "/pubdoc/foot/seal/img/@width", ""));
					fileVO.setImageWidth(size.equals("")?0:Integer.parseInt(size));
					size = getOnlyNumber(getXmlText(xml, "/pubdoc/foot/seal/img/@height", ""));
					fileVO.setImageHeight(size.equals("")?0:Integer.parseInt(size));
				}
			}
		}
		
		NodeList approvalNodes = getXmlList(xml, "/pubdoc/foot/approvalinfo/approval");
		for(int repeat = 0 ; repeat < approvalNodes.getLength() ; repeat++) {
			LineInfoVO lineInfoVO = new LineInfoVO();
			lineInfoVO.setPtime("approval");
			lineInfoVO.setLineOrder(String.valueOf(repeat+1));
			lineInfoVO.setSignposition(getXmlText(xml, "/pubdoc/foot/approvalinfo/approval[" + (repeat+1) + "]/signposition", "relay.error.rel215"));
			lineInfoVO.setType(getXmlText(xml, "/pubdoc/foot/approvalinfo/approval[" + (repeat+1) + "]/type", "relay.error.rel216"));
			lineInfoVO.setName(getXmlText(xml, "/pubdoc/foot/approvalinfo/approval[" + (repeat+1) + "]/name", "relay.error.rel217"));
			lineInfoVO.setSignimage(getXmlText(xml, "/pubdoc/foot/approvalinfo/approval[" + (repeat+1) + "]/signimage/img/@src", ""));
			for(FileVO fileVO : packInfoVO.getAttach()) {
				if(fileVO.getDisplayName().equals(lineInfoVO.getSignimage())) {
					String size = "";
					size = getOnlyNumber(getXmlText(xml, "/pubdoc/foot/approvalinfo/approval[" + (repeat+1) + "]/signimage/img/@width", ""));
					fileVO.setImageWidth(size.equals("")?0:Integer.parseInt(size));
					size = getOnlyNumber(getXmlText(xml, "/pubdoc/foot/approvalinfo/approval[" + (repeat+1) + "]/signimage/img/@height", ""));
					fileVO.setImageHeight(size.equals("")?0:Integer.parseInt(size));
				}
			}
			lineInfoVO.setPdate(getXmlText(xml, "/pubdoc/foot/approvalinfo/approval[" + (repeat+1) + "]/date", "relay.error.rel219"));
			lineInfoVO.setPtime(getXmlText(xml, "/pubdoc/foot/approvalinfo/approval[" + (repeat+1) + "]/time", ""));
			packInfoVO.getApproval().add(lineInfoVO);
		}
		
		NodeList assistNodes = getXmlList(xml, "/pubdoc/foot/approvalinfo/assist");
		for(int repeat = 0 ; repeat < assistNodes.getLength() ; repeat++) {
			LineInfoVO lineInfoVO = new LineInfoVO();
			lineInfoVO.setPtime("assist");
			lineInfoVO.setLineOrder(String.valueOf(repeat+1));
			lineInfoVO.setSignposition(getXmlText(xml, "/pubdoc/foot/approvalinfo/assist[" + (repeat+1) + "]/signposition", "relay.error.rel215"));
			lineInfoVO.setType(getXmlText(xml, "/pubdoc/foot/approvalinfo/assist[" + (repeat+1) + "]/type", "relay.error.rel216"));
			lineInfoVO.setName(getXmlText(xml, "/pubdoc/foot/approvalinfo/assist[" + (repeat+1) + "]/name", ""));
			lineInfoVO.setSignimage(getXmlText(xml, "/pubdoc/foot/approvalinfo/assist[" + (repeat+1) + "]/signimage/img/@src", ""));
			for(FileVO fileVO : packInfoVO.getAttach()) {
				if(fileVO.getDisplayName().equals(lineInfoVO.getSignimage())) {
					String size = "";
					size = getOnlyNumber(getXmlText(xml, "/pubdoc/foot/approvalinfo/assist[" + (repeat+1) + "]/signimage/img/@width", ""));
					fileVO.setImageWidth(size.equals("")?0:Integer.parseInt(size));
					size = getOnlyNumber(getXmlText(xml, "/pubdoc/foot/approvalinfo/assist[" + (repeat+1) + "]/signimage/img/@height", ""));
					fileVO.setImageHeight(size.equals("")?0:Integer.parseInt(size));
				}
			}
			lineInfoVO.setPdate(getXmlText(xml, "/pubdoc/foot/approvalinfo/assist[" + (repeat+1) + "]/date", "relay.error.rel219"));
			lineInfoVO.setPtime(getXmlText(xml, "/pubdoc/foot/approvalinfo/assist[" + (repeat+1) + "]/time", ""));
			packInfoVO.getAssist().add(lineInfoVO);
		}
		for(FileVO fileVO : packInfoVO.getAttach()) {
			String size = "";
			if(fileVO.getDisplayName().equals(getXmlText(xml, "/pubdoc/foot/sendinfo/logo/img/@src", ""))) {
				size = getOnlyNumber(getXmlText(xml, "/pubdoc/foot/sendinfo/logo/img/@width", ""));
				fileVO.setImageWidth(size.equals("")?0:Integer.parseInt(size));
				size = getOnlyNumber(getXmlText(xml, "/pubdoc/foot/sendinfo/logo/img/@height", ""));
				fileVO.setImageHeight(size.equals("")?0:Integer.parseInt(size));
			} else if(fileVO.getDisplayName().equals(getXmlText(xml, "/pubdoc/foot/sendinfo/symbol/img/@src", ""))) {
				size = getOnlyNumber(getXmlText(xml, "/pubdoc/foot/sendinfo/symbol/img/@width", ""));
				fileVO.setImageWidth(size.equals("")?0:Integer.parseInt(size));
				size = getOnlyNumber(getXmlText(xml, "/pubdoc/foot/sendinfo/symbol/img/@height", ""));
				fileVO.setImageHeight(size.equals("")?0:Integer.parseInt(size));
			}
		}
		
		getXmlElement(xml, "/pubdoc", "relay.error.rel201");
		getXmlElement(xml, "/pubdoc/head", "relay.error.rel202");
		getXmlElement(xml, "/pubdoc/head/organ", "relay.error.rel203");
		getXmlElement(xml, "/pubdoc/head/receiptinfo", "relay.error.rel204");
		getXmlElement(xml, "/pubdoc/head/receiptinfo/recipient", "relay.error.rel205");
		getXmlElement(xml, "/pubdoc/head/receiptinfo/recipient/rec", "relay.error.rel206");
		getXmlElement(xml, "/pubdoc/body", "relay.error.rel207");
		getXmlElement(xml, "/pubdoc/body/title", "relay.error.rel208");
		getXmlElement(xml, "/pubdoc/body/content", "relay.error.rel209");
		getXmlElement(xml, "/pubdoc/foot", "relay.error.rel210");
		getXmlElement(xml, "/pubdoc/foot/sendername", "relay.error.rel211");
		getXmlElement(xml, "/pubdoc/foot/seal", "relay.error.rel212");
		getXmlElement(xml, "/pubdoc/foot/approvalinfo", "relay.error.rel213");
		getXmlElement(xml, "/pubdoc/foot/approvalinfo/approval", "relay.error.rel214");
		getXmlElement(xml, "/pubdoc/foot/processinfo", "relay.error.rel214");
		getXmlElement(xml, "/pubdoc/foot/processinfo/regnumber", "relay.error.rel214");
		getXmlElement(xml, "/pubdoc/foot/processinfo/enforcedate", "relay.error.rel214");
		getXmlElement(xml, "/pubdoc/foot/sendinfo", "relay.error.rel226");
		getXmlElement(xml, "/pubdoc/foot/sendinfo/zipcode", "relay.error.rel227");
		getXmlElement(xml, "/pubdoc/foot/sendinfo/address", "relay.error.rel228");
		getXmlElement(xml, "/pubdoc/foot/sendinfo/telephone", "relay.error.rel229");
		getXmlElement(xml, "/pubdoc/foot/sendinfo/fax", "relay.error.rel230");
		getXmlElement(xml, "/pubdoc/foot/sendinfo/publication", "relay.error.rel231");
	}
	
	/**
     * <pre> 
     *  유통문서 Ack 파일 생성 처리
     *  </pre>
     * @author 김상태
     * @param packInfoVO
     * @param ackThye
     * @paran wType
     * @return Document
	 * @throws Exception 
     */
	public Document getAckXml(PackInfoVO packInfoVO, String ackType, String wType) throws Exception {
		Document xml = new DocumentImpl();
	
		// Element : pack
		Element pack = xml.createElement("pack");
		// 공문서 유통표준에는 pack(Elemnet)의 filename(Attribute)를 필수요소로 기재하지만
		// 실제 문서유통시에는 filename(Attribute) 넣으면 오류가 발생함
		// 일단 제외하고 문서를 만들기로 결정 (2012-04-05 : 유진[수석]-김상태)
		// pack.setAttribute("filename", packVO.getFilename());
		xml.appendChild(pack);
		
		// Element : header
		Element header = xml.createElement("header");
		pack.appendChild(header);
		
		// Element : send-orgcode
		Element send_orgcode = xml.createElement("send-orgcode");
		send_orgcode.setTextContent(packInfoVO.getSendOrgCode());
		header.appendChild(send_orgcode);
		
		// Element : send-id
		Element send_id = xml.createElement("send-id");
		send_id.setTextContent(packInfoVO.getSendDeptId());
		header.appendChild(send_id);

		// Element : send-name
		Element send_name = xml.createElement("send-name");
		send_name.setTextContent(wType.toUpperCase().equals("FILE")?Base64Util.encode(packInfoVO.getSendOrgName().getBytes(AppConfig.getProperty("charset", "EUC-KR", "relay"))):packInfoVO.getSendOrgName());
		header.appendChild(send_name);
		
		// Element : receive-id
		Element receive_id = xml.createElement("receive-id");
		receive_id.setTextContent(packInfoVO.getReceiveId());
		header.appendChild(receive_id);
		
		// Element : date
		Element date = xml.createElement("date");
		date.setTextContent(packInfoVO.getSendDate());
		header.appendChild(date);
		
		// Element : title
		Element title = xml.createElement("title");
		title.setTextContent(wType.toUpperCase().equals("FILE")?Base64Util.encode(packInfoVO.getTitle().getBytes(AppConfig.getProperty("charset", "EUC-KR", "relay"))):packInfoVO.getTitle());
		header.appendChild(title);
		
		// Element : doc-id
		Element doc_id = xml.createElement("doc-id");
		doc_id.setTextContent(packInfoVO.getDocId());
		header.appendChild(doc_id);
		
		// Element : doc-type
		Element doc_type = xml.createElement("doc-type");
		if ("accept".equals(ackType) || "req-resend".equals(ackType)) {
			doc_type.setAttribute("dept", wType.toUpperCase().equals("FILE")?Base64Util.encode(packInfoVO.getSendDeptName().getBytes(AppConfig.getProperty("charset", "EUC-KR", "relay"))):packInfoVO.getSendDeptName());
			doc_type.setAttribute("name", wType.toUpperCase().equals("FILE")?Base64Util.encode(packInfoVO.getSendName().getBytes(AppConfig.getProperty("charset", "EUC-KR", "relay"))):packInfoVO.getSendName());
		} else {
			doc_type.setAttribute("dept", "");
			doc_type.setAttribute("name", "");
		}			
		doc_type.setAttribute("type", ackType);
		header.appendChild(doc_type);
		
		// Element : send-gw
		Element send_gw = xml.createElement("send-gw");
		send_gw.setTextContent(wType.toUpperCase().equals("FILE")?Base64Util.encode(AppConfig.getProperty("send_gw", "", "relay").getBytes(AppConfig.getProperty("charset", "EUC-KR", "relay"))):AppConfig.getProperty("send_gw", "", "relay"));
		header.appendChild(send_gw);
		
		// Element : dtd-version
		Element dtd_version = xml.createElement("dtd-version");
		dtd_version.setTextContent(AppConfig.getProperty("dtd_version", "", "relay"));
		header.appendChild(dtd_version);
		
		// Element : xsl-version
		Element xsl_version = xml.createElement("xsl-version");
		xsl_version.setTextContent(AppConfig.getProperty("xsl_version", "", "relay"));
		header.appendChild(xsl_version);
		
		// Element : contents
		Element contents = xml.createElement("contents");
		pack.appendChild(contents);
		
		for(ContentVO contentVO : packInfoVO.getContents()) {
			if(contentVO.getContentContentRole().toLowerCase().equals("return")) {
				Element content = xml.createElement("content");
				content.setAttribute("content-role", contentVO.getContentContentRole());
				content.setAttribute("content-transfer-encoding", contentVO.getContentContentTransperEncoding());
				content.setAttribute("filename", contentVO.getContentFilename());
				content.setAttribute("content-type", contentVO.getContentContentType());
				content.setAttribute("charset", contentVO.getContentCharset());
				if(!CommonUtil.isNullOrEmpty(contentVO.getContent())) {
					content.setTextContent(wType.toUpperCase().equals("FILE")?Base64Util.encode(contentVO.getContent().getBytes(AppConfig.getProperty("charset", "EUC-KR", "relay"))):contentVO.getContent());
				}
				contents.appendChild(content);
			}
		}
		
		return xml;
	}
}