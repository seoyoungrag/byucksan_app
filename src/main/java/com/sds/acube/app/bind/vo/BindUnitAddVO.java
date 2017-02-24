package com.sds.acube.app.bind.vo;

import com.sds.acube.app.bind.BindConstants;


/**
 * Class Name : BindUnitAddVO.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2014. 5. 1. <br> 수 정 자 : Dony <br> 수정내용 : <br>
 * @author  Dony
 * @since  2014. 5. 1.
 * @version  1.0
 * @see  com.sds.acube.app.bind.vo.BindUnitAddVO.java
 */

public class BindUnitAddVO implements BindConstants {

    private String unitId;
    /**
     */
    private String unitAddName;
    /**
     */
    private String unitAddType;
    /**
     */
    private int unitAddOrder;
    
    
    /**
     * <pre>  설명 </pre>
     * @return
     * @see   
     */
    public String getUnitId() {
        return unitId;
    }


    /**
     * <pre>  설명 </pre>
     * @param unitId
     * @see   
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }


    /**
     * <pre>  설명 </pre>
     * @return
     * @see   
     */
    public String getUnitAddName() {
        return unitAddName;
    }


    /**
     * <pre>  설명 </pre>
     * @param unitName
     * @see   
     */
    public void setUnitAddName(String unitAddName) {
        this.unitAddName = unitAddName;
    }

    /**
     * @return the unitAddType
     */
    public String getUnitAddType() {
        return unitAddType;
    }


    /**
     * @param unitAddType the unitAddType to set
     */
    public void setUnitAddType(String unitAddType) {
        this.unitAddType = unitAddType;
    }


    /**
     * @return the unitAddOrder
     */
    public int getUnitAddOrder() {
        return unitAddOrder;
    }


    /**
     * @param unitAddOrder the unitAddOrder to set
     */
    public void setUnitAddOrder(int unitAddOrder) {
        this.unitAddOrder = unitAddOrder;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BindUnitVO [unitId=" + unitId + ", unitAddName=" + unitAddName + ", unitAddType=" + unitAddType + ", unitAddOrder=" + unitAddOrder + "]";
    }

    
 
}


