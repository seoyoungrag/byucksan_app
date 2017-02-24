package com.sds.acube.app.bind.vo;

import com.sds.acube.app.bind.BindConstants;


/**
 * Class Name : BindUnitVO.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 4. 7. <br> 수 정 자 : I-ON <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 4. 7.
 * @version  1.0
 * @see  com.sds.acube.app.bind.vo.BindUnitVO.java
 */

public class BindUnitVO implements BindConstants {

    /**
	 */
    private String compId;
    /**
	 */
    private String deptId;
    /**
	 */
    private String unitId;
    /**
	 */
    private String unitName;
    /**
	 */
    private String parentId;
    /**
	 */
    private String unitType;
    /**
	 */
    private String retentionPeriod;
    /**
	 */
    private String applied;
    /**
	 */
    private String created;
    /**
	 */
    private String modified;
    /**
	 */
    private String description;
    /**
	 */
    private int childCount;
    /**
	 */
    private int bindCount;
    /**
	 */
    private String serialNumber;

	/**
	 * 언어 타입
	 */
	private String langType;
	
	/**
	 * 리소스 Id
	 */
	private String resourceId;
	
	/**
	 * 단위 업무의 부모 Id
	 */	
	private String parentUnitId;
	
	/**
	 * 단위 업무 순번
	 */	
	private long unitOrder; 
	
	/**
	 * 단위 업무 깊이
	 */	
	private long unitDepth;

	/**
	 * 자식이 있는지
	 */	
	private String isChildUnit;

	/**
	 * 자식을 찾을 건지("C"), 형제를 찾을 건지
	 */	
	private String isSelectType;

	/**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getCompId() {
    	return compId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param compId
	 * @see   
	 */
    public void setCompId(String compId) {
    	this.compId = compId;
    }


	/**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
	public String getDeptId() {
		return deptId;
	}


	/**
	 * <pre>  설명 </pre>
	 * @param deptId
	 * @see   
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}


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
    public String getUnitName() {
    	return unitName;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param unitName
	 * @see   
	 */
    public void setUnitName(String unitName) {
    	this.unitName = unitName;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getParentId() {
    	return parentId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param parentId
	 * @see   
	 */
    public void setParentId(String parentId) {
    	this.parentId = parentId;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getUnitType() {
    	return unitType;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param unitType
	 * @see   
	 */
    public void setUnitType(String unitType) {
    	this.unitType = unitType;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getRetentionPeriod() {
    	return retentionPeriod;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param retentionPeriod
	 * @see   
	 */
    public void setRetentionPeriod(String retentionPeriod) {
    	this.retentionPeriod = retentionPeriod;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getApplied() {
    	return applied;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param applied
	 * @see   
	 */
    public void setApplied(String applied) {
    	this.applied = applied;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getCreated() {
    	return created;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param created
	 * @see   
	 */
    public void setCreated(String created) {
    	this.created = created;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getModified() {
    	return modified;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param modified
	 * @see   
	 */
    public void setModified(String modified) {
    	this.modified = modified;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getDescription() {
    	return description;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param description
	 * @see   
	 */
    public void setDescription(String description) {
    	this.description = description;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public int getChildCount() {
    	return childCount;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param childCount
	 * @see   
	 */
    public void setChildCount(int childCount) {
    	this.childCount = childCount;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public int getBindCount() {
    	return bindCount;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param bindCount
	 * @see   
	 */
    public void setBindCount(int bindCount) {
    	this.bindCount = bindCount;
    }


    /**
	 * <pre>  설명 </pre>
	 * @param serialNumber
	 * @see   
	 */
    public void setSerialNumber(String serialNumber) {
    	this.serialNumber = serialNumber;
    }


    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getSerialNumber() {
    	return serialNumber;
    }


	/**
	 * @return the langType
	 */
	public String getLangType() {
		return langType;
	}


	/**
	 * @param langType the langType to set
	 */
	public void setLangType(String langType) {
		this.langType = langType;
	}


	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}


	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the parentUnitId
	 */
	public String getParentUnitId() {
		return parentUnitId;
	}


	/**
	 * @param parentUnitId the parentUnitId to set
	 */
	public void setParentUnitId(String parentUnitId) {
		this.parentUnitId = parentUnitId;
	}


	/**
	 * @return the unitOrder
	 */
	public long getUnitOrder() {
		return unitOrder;
	}


	/**
	 * @param unitOrder the unitOrder to set
	 */
	public void setUnitOrder(long unitOrder) {
		this.unitOrder = unitOrder;
	}


	/**
	 * @return the unitDepth
	 */
	public long getUnitDepth() {
		return unitDepth;
	}


	/**
	 * @param unitDepth the unitDepth to set
	 */
	public void setUnitDepth(long unitDepth) {
		this.unitDepth = unitDepth;
	}
	
    /**
	 * @return the isChildUnit
	 */
	public String getIsChildUnit() {
		return isChildUnit;
	}


	/**
	 * @param isChildUnit the isChildUnit to set
	 */
	public void setIsChildUnit(String isChildUnit) {
		this.isChildUnit = isChildUnit;
	}
	
    /**
	 * @return the isSelectType
	 */
	public String getIsSelectType() {
		return isSelectType;
	}


	/**
	 * @param isSelectType the isSelectType to set
	 */
	public void setIsSelectType(String isSelectType) {
		this.isSelectType = isSelectType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BindUnitVO [applied=" + applied + ", bindCount=" + bindCount
				+ ", childCount=" + childCount + ", compId=" + compId
				+ ", created=" + created + ", deptId=" + deptId
				+ ", description=" + description + ", langType=" + langType
				+ ", modified=" + modified + ", parentId=" + parentId
				+ ", resourceId=" + resourceId + ", retentionPeriod=" + retentionPeriod
				+ ", serialNumber=" + serialNumber + ", unitId=" + unitId
				+ ", unitName=" + unitName + ", unitType=" + unitType 
				+ ", parentUnitId=" + parentUnitId + ", unitOrder=" + unitOrder
				+ ", unitDepth=" + unitDepth + "]";
	}
}
