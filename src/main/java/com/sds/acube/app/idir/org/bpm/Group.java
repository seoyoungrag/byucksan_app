package com.sds.acube.app.idir.org.bpm;

/**
 * Class Name  : Group.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.idir.org.bpm.Group.java
 */
public class Group {

	/**
	 */
	private String GROUP_ID = "";
	/**
	 */
	private String GROUP_NAME = "";
	/**
	 */
	private String GROUP_OTHER_NAME = "";
	/**
	 */
	private String GROUP_TYPE = "";
	/**
	 */
	private String DEPT_ID = "";
	/**
	 */
	private String MAKE_USER_UID = "";
    /**
	 */
    private String MAKE_USER_NAME = "";
    /**
	 */
    private String MAKE_DEPT_NAME = "";
	/**
	 */
	private String MAKE_DATE = "";
    /**
	 */
    private int TOTAL_COUNT = 0;

	/**
	 * <pre>  설명 </pre>
	 * @param GROUP_ID
	 * @see   
	 */
	public void setGROUP_ID(String GROUP_ID) {
		this.GROUP_ID = GROUP_ID;
	}
	/**
	 * <pre>  설명 </pre>
	 * @param GROUP_NAME
	 * @see   
	 */
	public void setGROUP_NAME(String GROUP_NAME) {
		this.GROUP_NAME = GROUP_NAME;
	}
	/**
	 * <pre>  설명 </pre>
	 * @param GROUP_OTHER_NAME
	 * @see   
	 */
	public void setGROUP_OTHER_NAME(String GROUP_OTHER_NAME) {
		this.GROUP_OTHER_NAME = GROUP_OTHER_NAME;
	}
	/**
	 * <pre>  설명 </pre>
	 * @param GROUP_TYPE
	 * @see   
	 */
	public void setGROUP_TYPE(String GROUP_TYPE) {
		this.GROUP_TYPE = GROUP_TYPE;
	}
	/**
	 * <pre>  설명 </pre>
	 * @param DEPT_ID
	 * @see   
	 */
	public void setDEPT_ID(String DEPT_ID) {
		this.DEPT_ID = DEPT_ID;
	}
	/**
	 * <pre>  설명 </pre>
	 * @param MAKE_USER_UID
	 * @see   
	 */
	public void setMAKE_USER_UID(String MAKE_USER_UID) {
		this.MAKE_USER_UID = MAKE_USER_UID;
	}
    /**
	 * <pre>  설명 </pre>
	 * @param MAKE_USER_NAME
	 * @see   
	 */
    public void setMAKE_USER_NAME(String MAKE_USER_NAME) {
        this.MAKE_USER_NAME = MAKE_USER_NAME;
    }
    /**
	 * <pre>  설명 </pre>
	 * @param MAKE_DEPT_NAME
	 * @see   
	 */
    public void setMAKE_DEPT_NAME(String MAKE_DEPT_NAME) {
        this.MAKE_DEPT_NAME = MAKE_DEPT_NAME;
    }
	/**
	 * <pre>  설명 </pre>
	 * @param MAKE_DATE
	 * @see   
	 */
	public void setMAKE_DATE(String MAKE_DATE) {
		this.MAKE_DATE = MAKE_DATE;
	}
    /**
	 * <pre>  설명 </pre>
	 * @param TOTAL_COUNT
	 * @see   
	 */
    public void setTOTAL_COUNT(int TOTAL_COUNT) {
        this.TOTAL_COUNT = TOTAL_COUNT;
    }


	/**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
	public String getGROUP_ID() {
		return this.GROUP_ID;
	}
	/**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
	public String getGROUP_NAME() {
		return this.GROUP_NAME;
	}
	/**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
	public String getGROUP_OTHER_NAME() {
		return this.GROUP_OTHER_NAME;
	}
	/**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
	public String getGROUP_TYPE() {
		return this.GROUP_TYPE;
	}
	/**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
	public String getDEPT_ID() {
		return this.DEPT_ID;
	}
	/**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
	public String getMAKE_USER_UID() {
		return this.MAKE_USER_UID;
	}
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getMAKE_USER_NAME() {
        return this.MAKE_USER_NAME;
    }
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getMAKE_DEPT_NAME() {
        return this.MAKE_DEPT_NAME;
    }
	/**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
	public String getMAKE_DATE() {
		return this.MAKE_DATE;
	}
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public int getTOTAL_COUNT() {
        return this.TOTAL_COUNT;
    }


}
