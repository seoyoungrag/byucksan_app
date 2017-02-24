/**
 * 
 */
package com.sds.acube.app.ws.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Class Name  : AlarmVO.java <br> Description : 메신져 알림 VO <br> Modification Information <br> <br> 수 정  일 : 2011. 7. 12. <br> 수 정  자 : 신경훈  <br> 수정내용 :  <br>
 * @author   skh0204 
 * @since  2011. 7. 12.
 * @version  1.0 
 * @see  com.sds.acube.app.ws.vo.AlarmVO.java
 */


@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(name = "alarmVO", propOrder = {

    "cmnVO",
    "t_Code",
    "b_Code",
    "sender",
    "senderName",
    "regDate",
    "sendDate",
    "receiver",
    "title",
    "content",
    "msgDivision",
    "msgType",
    "opt1",
    "opt2",
    "opt3",
    "opt4",
    "opt5",
    "tf",
    "linkMessage"
})


public class AlarmVO {
    
    /**
	 */
    private CmnVO cmnVO;
    /**
	 */
    private int t_Code;
    /**
	 */
    private int b_Code;
    /**
	 */
    private String sender;
    /**
	 */
    private String senderName;
    /**
	 */
    private String regDate;
    /**
	 */
    private String sendDate;
    /**
	 */
    private String receiver;
    /**
	 */
    private String title;
    /**
	 */
    private String content;
    /**
	 */
    private int msgDivision;
    /**
	 */
    private int msgType;
    /**
	 */
    private String opt1;
    /**
	 */
    private String opt2;
    /**
	 */
    private String opt3;
    /**
	 */
    private String opt4;
    /**
	 */
    private String opt5;
    /**
	 */
    private int tf;
    /**
	 */
    private String linkMessage;
    
    /**
	 * @return  the cmnVO
	 */
    public CmnVO getCmnVO() {
        return cmnVO;
    }
    /**
	 * @param cmnVO  the cmnVO to set
	 */
    public void setCmnVO(CmnVO cmnVO) {
        this.cmnVO = cmnVO;
    }
    
    /**
	 * @return  the t_Code
	 */
    public int getT_Code() {
        return t_Code;
    }
    /**
	 * @param tCode  the t_Code to set
	 */
    public void setT_Code(int tCode) {
        t_Code = tCode;
    }
    
    /**
	 * @return  the b_Code
	 */
    public int getB_Code() {
        return b_Code;
    }
    /**
	 * @param bCode  the b_Code to set
	 */
    public void setB_Code(int bCode) {
        b_Code = bCode;
    }
    
    /**
	 * @return  the sender
	 */
    public String getSender() {
        return sender;
    }
    /**
	 * @param sender  the sender to set
	 */
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    /**
	 * @return  the senderName
	 */
    public String getSenderName() {
        return senderName;
    }
    /**
	 * @param senderName  the senderName to set
	 */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    
    /**
	 * @return  the regDate
	 */
    public String getRegDate() {
        return regDate;
    }
    /**
	 * @param regDate  the regDate to set
	 */
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
    
    /**
	 * @return  the sendDate
	 */
    public String getSendDate() {
        return sendDate;
    }
    /**
	 * @param sendDate  the sendDate to set
	 */
    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }
    
    /**
	 * @return  the receiver
	 */
    public String getReceiver() {
        return receiver;
    }
    /**
	 * @param receiver  the receiver to set
	 */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    
    /**
	 * @return  the title
	 */
    public String getTitle() {
        return title;
    }
    /**
	 * @param title  the title to set
	 */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
	 * @return  the content
	 */
    public String getContent() {
        return content;
    }
    /**
	 * @param content  the content to set
	 */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
	 * @return  the msgDivision
	 */
    public int getMsgDivision() {
        return msgDivision;
    }
    /**
	 * @param msgDivision  the msgDivision to set
	 */
    public void setMsgDivision(int msgDivision) {
        this.msgDivision = msgDivision;
    }
    
    /**
	 * @return  the msgType
	 */
    public int getMsgType() {
        return msgType;
    }
    /**
	 * @param msgType  the msgType to set
	 */
    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }
    
    /**
	 * @return  the opt1
	 */
    public String getOpt1() {
        return opt1;
    }
    /**
	 * @param opt1  the opt1 to set
	 */
    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }
    
    /**
	 * @return  the opt2
	 */
    public String getOpt2() {
        return opt2;
    }
    /**
	 * @param opt2  the opt2 to set
	 */
    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }
    
    /**
	 * @return  the opt3
	 */
    public String getOpt3() {
        return opt3;
    }
    /**
	 * @param opt3  the opt3 to set
	 */
    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }
    
    /**
	 * @return  the opt4
	 */
    public String getOpt4() {
        return opt4;
    }
    /**
	 * @param opt4  the opt4 to set
	 */
    public void setOpt4(String opt4) {
        this.opt4 = opt4;
    }
    
    /**
	 * @return  the opt5
	 */
    public String getOpt5() {
        return opt5;
    }
    /**
	 * @param opt5  the opt5 to set
	 */
    public void setOpt5(String opt5) {
        this.opt5 = opt5;
    }
    
    /**
	 * @return  the tf
	 */
    public int getTf() {
        return tf;
    }
    /**
	 * @param tf  the tf to set
	 */
    public void setTf(int tf) {
        this.tf = tf;
    }
    
    /**
	 * @return  the linkMessage
	 */
    public String getLinkMessage() {
        return linkMessage;
    }
    /**
	 * @param linkMessage  the linkMessage to set
	 */
    public void setLinkMessage(String linkMessage) {
        this.linkMessage = linkMessage;
    }
    
}


