package com.sds.acube.app.idir.org.bpm;

/**
 * Class Name  : Member.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.idir.org.bpm.Member.java
 */
public class Member {

    /**
	 */
    private String GROUP_ID          = "";
    /**
	 */
    private String USER_ID           = "";
    /**
	 */
    private String USER_NAME         = "";
    /**
	 */
    private String USER_OTHER_NAME   = "";
    /**
	 */
    private String USER_UID          = "";
    /**
	 */
    private String COMP_ID           = "";
    /**
	 */
    private String COMP_NAME         = "";
    /**
	 */
    private String COMP_OTHER_NAME   = "";
    /**
	 */
    private String DEPT_ID           = "";
    /**
	 */
    private String DEPT_NAME         = "";
    /**
	 */
    private String PART_ID           = "";
    /**
	 */
    private String PART_NAME         = "";
    /**
	 */
    private String ORG_DISPLAY_NAME  = "";
    /**
	 */
    private String GRADE_CODE        = "";
    /**
	 */
    private String TITLE_CODE        = "";
    /**
	 */
    private String POSITION_CODE     = "";
    /**
	 */
    private String USER_ORDER        = "";
    /**
	 */
    private String SECURITY_LEVEL    = "";
    /**
	 */
    private String ROLE_CODE         = "";
    /**
	 */
    private String RESIDENT_NO       = "";
    /**
	 */
    private String EMPLOYEE_ID       = "";
    /**
	 */
    private String SYSMAIL           = "";
    /**
	 */
    private String SERVERS           = "";
    /**
	 */
    private String IS_CONCURRENT     = "";
    /**
	 */
    private String IS_PROXY          = "";
    /**
	 */
    private String IS_DELEGATE       = "";
    /**
	 */
    private String IS_EXISTENCE      = "";
    /**
	 */
    private String USER_RID          = "";
    /**
	 */
    private String IS_DELETED        = "";
    /**
	 */
    private String DESCRIPTION       = "";
    /**
	 */
    private String RESERVED1         = "";
    /**
	 */
    private String RESERVED2         = "";
    /**
	 */
    private String RESERVED3         = "";
    /**
	 */
    private String OPTIONAL_GTP_NAME = "";
    /**
	 */
    private String DISPLAY_ORDER     = "";
    /**
	 */
    private String DEFAULT_USER      = "";
    /**
	 */
    private String CERTIFICATION_ID  = "";
    /**
	 */
    private String DUTY_CODE         = "";
    
    /**
	 */
    private String GRADE_NAME        = "";
    /**
	 */
    private String GRADE_OTHER_NAME  = "";

    /**
	 */
    private String ORG_OTHER_NAME     = "";

    /**
	 */
    private String POSITION_OTHER_NAME= "";
    
    /**
	 */
    private String EMAIL              = "";
    /**
	 */
    private String DUTY               = "";
    /**
	 */
    private String PCONLINE_ID        = "";
    /**
	 */
    private String HOMEPAGE           = "";
    /**
	 */
    private String OFFICE_TEL         = "";
    /**
	 */
    private String OFFICE_TEL2        = "";
    /**
	 */
    private String OFFICE_ADDR        = "";
    /**
	 */
    private String OFFICE_DETAIL_ADDR = "";
    /**
	 */
    private String OFFICE_ZIPCODE     = "";
    /**
	 */
    private String OFFICE_FAX         = "";
    /**
	 */
    private String MOBILE             = "";
    /**
	 */
    private String MOBILE2            = "";
    /**
	 */
    private String PAGER              = "";
    /**
	 */
    private String HOME_ADDR          = "";
    /**
	 */
    private String HOME_DETAIL_ADDR   = "";
    /**
	 */
    private String HOME_ZIPCODE       = "";
    /**
	 */
    private String HOME_TEL           = "";
    /**
	 */
    private String HOME_TEL2          = "";
    /**
	 */
    private String HOME_FAX           = "";
    
    /**
	 */
    private int TOTAL_COUNT = 0;

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
    public int getTOTAL_COUNT() {
        return this.TOTAL_COUNT;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getCERTIFICATION_ID() {
        return CERTIFICATION_ID;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param certification_id
	 * @see   
	 */
    public void setCERTIFICATION_ID(String certification_id) {
        CERTIFICATION_ID = certification_id;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getCOMP_ID() {
        return COMP_ID;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param comp_id
	 * @see   
	 */
    public void setCOMP_ID(String comp_id) {
        COMP_ID = comp_id;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getCOMP_NAME() {
        return COMP_NAME;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param comp_name
	 * @see   
	 */
    public void setCOMP_NAME(String comp_name) {
        COMP_NAME = comp_name;
    }
    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getCOMP_OTHER_NAME() {
        return COMP_OTHER_NAME;
    }
    
    /**
	 * <pre>  설명 </pre>
	 * @param comp_other_name
	 * @see   
	 */
    public void setCOMP_OTHER_NAME(String comp_other_name) {
        COMP_OTHER_NAME = comp_other_name;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getDEFAULT_USER() {
        return DEFAULT_USER;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param default_user
	 * @see   
	 */
    public void setDEFAULT_USER(String default_user) {
        DEFAULT_USER = default_user;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getDEPT_ID() {
        return DEPT_ID;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param dept_id
	 * @see   
	 */
    public void setDEPT_ID(String dept_id) {
        DEPT_ID = dept_id;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getDEPT_NAME() {
        return DEPT_NAME;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param dept_name
	 * @see   
	 */
    public void setDEPT_NAME(String dept_name) {
        DEPT_NAME = dept_name;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param description
	 * @see   
	 */
    public void setDESCRIPTION(String description) {
        DESCRIPTION = description;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getDISPLAY_ORDER() {
        return DISPLAY_ORDER;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param display_order
	 * @see   
	 */
    public void setDISPLAY_ORDER(String display_order) {
        DISPLAY_ORDER = display_order;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getDUTY_CODE() {
        return DUTY_CODE;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param duty_code
	 * @see   
	 */
    public void setDUTY_CODE(String duty_code) {
        DUTY_CODE = duty_code;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getEMPLOYEE_ID() {
        return EMPLOYEE_ID;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param employee_id
	 * @see   
	 */
    public void setEMPLOYEE_ID(String employee_id) {
        EMPLOYEE_ID = employee_id;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getGRADE_CODE() {
        return GRADE_CODE;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param grade_code
	 * @see   
	 */
    public void setGRADE_CODE(String grade_code) {
        GRADE_CODE = grade_code;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getGRADE_NAME() {
        return GRADE_NAME;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param grade_name
	 * @see   
	 */
    public void setGRADE_NAME(String grade_name) {
        GRADE_NAME = grade_name;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getGROUP_ID() {
        return GROUP_ID;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param group_id
	 * @see   
	 */
    public void setGROUP_ID(String group_id) {
        GROUP_ID = group_id;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getIS_CONCURRENT() {
        return IS_CONCURRENT;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param is_concurrent
	 * @see   
	 */
    public void setIS_CONCURRENT(String is_concurrent) {
        IS_CONCURRENT = is_concurrent;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getIS_DELEGATE() {
        return IS_DELEGATE;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param is_delegate
	 * @see   
	 */
    public void setIS_DELEGATE(String is_delegate) {
        IS_DELEGATE = is_delegate;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getIS_DELETED() {
        return IS_DELETED;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param is_deleted
	 * @see   
	 */
    public void setIS_DELETED(String is_deleted) {
        IS_DELETED = is_deleted;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getIS_EXISTENCE() {
        return IS_EXISTENCE;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param is_existence
	 * @see   
	 */
    public void setIS_EXISTENCE(String is_existence) {
        IS_EXISTENCE = is_existence;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getIS_PROXY() {
        return IS_PROXY;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param is_proxy
	 * @see   
	 */
    public void setIS_PROXY(String is_proxy) {
        IS_PROXY = is_proxy;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getOPTIONAL_GTP_NAME() {
        return OPTIONAL_GTP_NAME;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param optional_gtp_name
	 * @see   
	 */
    public void setOPTIONAL_GTP_NAME(String optional_gtp_name) {
        OPTIONAL_GTP_NAME = optional_gtp_name;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getORG_DISPLAY_NAME() {
        return ORG_DISPLAY_NAME;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param org_display_name
	 * @see   
	 */
    public void setORG_DISPLAY_NAME(String org_display_name) {
        ORG_DISPLAY_NAME = org_display_name;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getPART_ID() {
        return PART_ID;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param part_id
	 * @see   
	 */
    public void setPART_ID(String part_id) {
        PART_ID = part_id;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getPART_NAME() {
        return PART_NAME;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param part_name
	 * @see   
	 */
    public void setPART_NAME(String part_name) {
        PART_NAME = part_name;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getPOSITION_CODE() {
        return POSITION_CODE;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param position_code
	 * @see   
	 */
    public void setPOSITION_CODE(String position_code) {
        POSITION_CODE = position_code;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getRESERVED1() {
        return RESERVED1;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param reserved1
	 * @see   
	 */
    public void setRESERVED1(String reserved1) {
        RESERVED1 = reserved1;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getRESERVED2() {
        return RESERVED2;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param reserved2
	 * @see   
	 */
    public void setRESERVED2(String reserved2) {
        RESERVED2 = reserved2;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getRESERVED3() {
        return RESERVED3;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param reserved3
	 * @see   
	 */
    public void setRESERVED3(String reserved3) {
        RESERVED3 = reserved3;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getRESIDENT_NO() {
        return RESIDENT_NO;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param resident_no
	 * @see   
	 */
    public void setRESIDENT_NO(String resident_no) {
        RESIDENT_NO = resident_no;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getROLE_CODE() {
        return ROLE_CODE;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param role_code
	 * @see   
	 */
    public void setROLE_CODE(String role_code) {
        ROLE_CODE = role_code;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getSECURITY_LEVEL() {
        return SECURITY_LEVEL;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param security_level
	 * @see   
	 */
    public void setSECURITY_LEVEL(String security_level) {
        SECURITY_LEVEL = security_level;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getSERVERS() {
        return SERVERS;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param servers
	 * @see   
	 */
    public void setSERVERS(String servers) {
        SERVERS = servers;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getSYSMAIL() {
        return SYSMAIL;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param sysmail
	 * @see   
	 */
    public void setSYSMAIL(String sysmail) {
        SYSMAIL = sysmail;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getTITLE_CODE() {
        return TITLE_CODE;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param title_code
	 * @see   
	 */
    public void setTITLE_CODE(String title_code) {
        TITLE_CODE = title_code;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getUSER_ID() {
        return USER_ID;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param user_id
	 * @see   
	 */
    public void setUSER_ID(String user_id) {
        USER_ID = user_id;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getUSER_NAME() {
        return USER_NAME;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param user_name
	 * @see   
	 */
    public void setUSER_NAME(String user_name) {
        USER_NAME = user_name;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getUSER_ORDER() {
        return USER_ORDER;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param user_order
	 * @see   
	 */
    public void setUSER_ORDER(String user_order) {
        USER_ORDER = user_order;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getUSER_OTHER_NAME() {
        return USER_OTHER_NAME;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param user_other_name
	 * @see   
	 */
    public void setUSER_OTHER_NAME(String user_other_name) {
        USER_OTHER_NAME = user_other_name;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getUSER_RID() {
        return USER_RID;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param user_rid
	 * @see   
	 */
    public void setUSER_RID(String user_rid) {
        USER_RID = user_rid;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getUSER_UID() {
        return USER_UID;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param user_uid
	 * @see   
	 */
    public void setUSER_UID(String user_uid) {
        USER_UID = user_uid;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getDUTY() {
        return DUTY;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param duty
	 * @see   
	 */
    public void setDUTY(String duty) {
        DUTY = duty;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getEMAIL() {
        return EMAIL;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param email
	 * @see   
	 */
    public void setEMAIL(String email) {
        EMAIL = email;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getHOME_ADDR() {
        return HOME_ADDR;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param home_addr
	 * @see   
	 */
    public void setHOME_ADDR(String home_addr) {
        HOME_ADDR = home_addr;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getHOME_DETAIL_ADDR() {
        return HOME_DETAIL_ADDR;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param home_detail_addr
	 * @see   
	 */
    public void setHOME_DETAIL_ADDR(String home_detail_addr) {
        HOME_DETAIL_ADDR = home_detail_addr;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getHOME_FAX() {
        return HOME_FAX;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param home_fax
	 * @see   
	 */
    public void setHOME_FAX(String home_fax) {
        HOME_FAX = home_fax;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getHOME_TEL() {
        return HOME_TEL;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param home_tel
	 * @see   
	 */
    public void setHOME_TEL(String home_tel) {
        HOME_TEL = home_tel;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getHOME_TEL2() {
        return HOME_TEL2;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param home_tel2
	 * @see   
	 */
    public void setHOME_TEL2(String home_tel2) {
        HOME_TEL2 = home_tel2;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getHOME_ZIPCODE() {
        return HOME_ZIPCODE;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param home_zipcode
	 * @see   
	 */
    public void setHOME_ZIPCODE(String home_zipcode) {
        HOME_ZIPCODE = home_zipcode;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getHOMEPAGE() {
        return HOMEPAGE;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param homepage
	 * @see   
	 */
    public void setHOMEPAGE(String homepage) {
        HOMEPAGE = homepage;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getMOBILE() {
        return MOBILE;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param mobile
	 * @see   
	 */
    public void setMOBILE(String mobile) {
        MOBILE = mobile;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getMOBILE2() {
        return MOBILE2;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param mobile2
	 * @see   
	 */
    public void setMOBILE2(String mobile2) {
        MOBILE2 = mobile2;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getOFFICE_ADDR() {
        return OFFICE_ADDR;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param office_addr
	 * @see   
	 */
    public void setOFFICE_ADDR(String office_addr) {
        OFFICE_ADDR = office_addr;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getOFFICE_DETAIL_ADDR() {
        return OFFICE_DETAIL_ADDR;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param office_detail_addr
	 * @see   
	 */
    public void setOFFICE_DETAIL_ADDR(String office_detail_addr) {
        OFFICE_DETAIL_ADDR = office_detail_addr;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getOFFICE_FAX() {
        return OFFICE_FAX;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param office_fax
	 * @see   
	 */
    public void setOFFICE_FAX(String office_fax) {
        OFFICE_FAX = office_fax;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getOFFICE_TEL() {
        return OFFICE_TEL;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param office_tel
	 * @see   
	 */
    public void setOFFICE_TEL(String office_tel) {
        OFFICE_TEL = office_tel;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getOFFICE_TEL2() {
        return OFFICE_TEL2;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param office_tel2
	 * @see   
	 */
    public void setOFFICE_TEL2(String office_tel2) {
        OFFICE_TEL2 = office_tel2;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getOFFICE_ZIPCODE() {
        return OFFICE_ZIPCODE;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param office_zipcode
	 * @see   
	 */
    public void setOFFICE_ZIPCODE(String office_zipcode) {
        OFFICE_ZIPCODE = office_zipcode;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getPAGER() {
        return PAGER;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param pager
	 * @see   
	 */
    public void setPAGER(String pager) {
        PAGER = pager;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getPCONLINE_ID() {
        return PCONLINE_ID;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param pconline_id
	 * @see   
	 */
    public void setPCONLINE_ID(String pconline_id) {
        PCONLINE_ID = pconline_id;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getGRADE_OTHER_NAME() {
        return GRADE_OTHER_NAME;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param grade_other_name
	 * @see   
	 */
    public void setGRADE_OTHER_NAME(String grade_other_name) {
        GRADE_OTHER_NAME = grade_other_name;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getORG_OTHER_NAME() {
        return ORG_OTHER_NAME;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param org_other_name
	 * @see   
	 */
    public void setORG_OTHER_NAME(String org_other_name) {
        ORG_OTHER_NAME = org_other_name;
    }


    
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getPOSITION_OTHER_NAME() {
        return POSITION_OTHER_NAME;
    }

    
    /**
	 * <pre>  설명 </pre>
	 * @param position_other_name
	 * @see   
	 */
    public void setPOSITION_OTHER_NAME(String position_other_name) {
        POSITION_OTHER_NAME = position_other_name;
    }


}
