/**
 * 
 */
package com.sds.acube.app.bind.vo;

import com.sds.acube.app.approval.vo.AppDocVO;


/**
 * Class Name : BindDocVO.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 5. 17. <br> 수 정 자 : I-ON <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 5. 17.
 * @version  1.0
 * @see  com.sds.acube.app.bind.vo.BindDocVO.java
 */

public class BindDocVO extends AppDocVO {

    /**
	 */
    private int idx;
    /**
	 */
    private String docNumber;
    /**
	 */
    private String bindId;
    /**
	 */
    private String receivers;
    /**
	 */
    private int totalCount;


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public int getIdx() {
	return idx;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param idx
	 * @see   
	 */
    public void setIdx(int idx) {
	this.idx = idx;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getDocNumber() {
	return docNumber;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param docNumber
	 * @see   
	 */
    public void setDocNumber(String docNumber) {
	this.docNumber = docNumber;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public int getTotalCount() {
	return totalCount;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param totalCount
	 * @see   
	 */
    public void setTotalCount(int totalCount) {
	this.totalCount = totalCount;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getBindId() {
	return bindId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param bindId
	 * @see   
	 */
    public void setBindId(String bindId) {
	this.bindId = bindId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getReceivers() {
	return receivers;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param receivers
	 * @see   
	 */
    public void setReceivers(String receivers) {
	this.receivers = receivers;
    }

}
