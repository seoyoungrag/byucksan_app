package com.sds.acube.app.ws.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/** 
 *  Class Name  : ApprovalVOs.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2012. 5. 31. <br>
 *  수 정  자 : kimside  <br>
 *  수정내용 :  <br>
 * 
 *  @author  kimside 
 *  @since 2012. 5. 31.
 *  @version 1.0 
 *  @see  com.sds.acube.app.ws.vo.ApprovalVOs.java
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ApproverVOs.class})
@XmlRootElement(name = "approvers")
@XmlType(name = "approvers")
public class ApproverVOs {

    @XmlElement(name="approver", nillable = true)
    protected List<ApproverVO> approver;
	
	public List<ApproverVO> getApproverVOs() {
        return approver;
    }

	public void setApproverVOs(List<ApproverVO> approver) {
        this.approver = approver;
    }

}
