package com.sds.acube.app.common.util;



public class ConstantList {
	public static final String US_CODE_INITIAL_PAGE = "INITIAL_PAGE";
	public static final String US_CODE_TIMEZONE = "TIMEZONE";
	
    // ACUBE Module List
    public static final String ACUBE = "ACUBE";
//    public static final String IAM = "IAM";
//    public static final String CO = "CO";
//    public static final String BSC = "BSC";
//    public static final String MQ = "MQ";
//    public static final String PKM = "PKM";
//    public static final String PPM = "PPM";
//    public static final String PMM = "PMM";
//    public static final String BMSORG = "bmsorg";
    public static final String COMMON = "common";
    public static final String PORTLET = "portlet";
    
    //  OS List
    public static final String OS = "OS";
    public static final String HP = "HP";
    public static final String SUN = "SUN";
    public static final String IBM = "IBM";
    public static final String WINDOWS = "WINDOWS";
    
    //  WAS List
    public static final String WAS = "WAS";
    public static final String WEBLOGIC = "Weblogic";
    public static final String WEBLOGIC81 = "Weblogic 8.1";        
    public static final String JEUS = "JEUS";
    public static final String JEUS41 = "JEUS 4.1";    
    public static final String AS10G = "as10g";
    
    //  DB List
    public static final String DATABASE = "DB";
    public static final String ORACLE = "Oracle";
    public static final String ORACLE8I = "Oracle 8i";
    public static final String ORACLE9I = "Oracle 9i";    
    public static final String MSSQL = "MS Sql";
    
    // Session Name
    public static final String USER = "User";
    public static final String PERSONAL = "Personal";
        
    // User Information Field
    public static final String LOGIN_ID = "Login ID";
    public static final String USER_ID = "User ID";
    public static final String USER_NAME = "User Name";
    public static final String USER_OTHER_NAME = "User Other Name";
    public static final String GROUP_ID = "Group ID";
    public static final String GROUP_NAME = "Group Name";
    public static final String COMP_ID = "Company ID";
    public static final String COMP_NAME = "Company Name";
    public static final String DEPT_ID = "Dept ID";
    public static final String DEPT_NAME = "Dept Name";
    public static final String PART_ID = "Part ID";
    public static final String PART_NAME = "Part Name";
    public static final String ORG_DISPLAY_NAME = "Organization Name";
    public static final String SYSMAIL = "Sysmail";
	
    // 로그인 결과 상수
    public static final int LOGIN_OBJECT_NULL = 100;
    public static final int LOGIN_SUCCESS = 0;
    public static final int LOGIN_FAIL_NO_ID = 1;
    public static final int LOGIN_FAIL_WRONG_PASSWORD = 2;
    public static final int LOGIN_FAIL_DATABASE_CONNECT = 3;
    public static final int LOGIN_FAIL_DATABASE_QUERY = 4;
    public static final int LOGIN_FAIL_CONCURRENT = 5;
    public static final int LOGIN_FAIL_AUTHORIZATION = 6;
    public static final int LOGIN_SUCCESS_WOORI = 7;
    // 그룹 포탈 ID
    public static final String ROOT_PORTAL_ID = "_ROOT_";
	
	public static final int PORTAL_ADMIN = 0;
	public static final int GROUP_ADMIN = 1;
    
	
	// 권한
    /** Access 정보를 문자열로 만들 때, ID/이름/권한간의 구분자 */
    public static final String DELIM_IN = "|";	//String.valueOf((char) 29);

    /** Access 정보를 문자열로 만들 때, Acl간의 구분자 */
    public static final String DELIM_OUT = "^";	//String.valueOf((char) 31);
        /*     * column 분리자     */    public static final String COL = String.valueOf((char) 2);//'        /*     * row 분리자     */    public static final String ROW = String.valueOf((char) 4);    
    /**
     * ACL Access Type 상수 : 가상그룹
     */
    public static final String TYPE_VIRTUAL_GROUP = "VIRTUAL_GROUP";

    /**
     * ACL Access Type 상수 : 권한그룹
     */
    public static final String TYPE_ACL_GROUP = "ACL_GROUP";

    /**
     * ACL Access Type 상수 : 부서
     */
    public static final String TYPE_DEPT = "DEPT";

    /**
     * ACL Access Type 상수 : 하위부서
     */
    public static final String TYPE_SUB_DEPT = "SUB_DEPT";

    /**
     * ACL Access Type 상수 : 직급
     */
    public static final String TYPE_TITLE = "TITLE";

    /**
     * ACL Access Type 상수 : 사용자
     */
    public static final String TYPE_USER = "USER";

    /**
     * ACL Access Type 상수 : 회사
     */
    public static final String TYPE_COMPANY = "COMPANY";

    /**
     * ACL Access Type 상수 : ROLE
     */
    public static final String TYPE_ROLE = "ROLE";
    public static final String TYPE_ROLE_WOORI = "8888";
    /**
     * ACL Access Type 상수 : PERSONAL GROUP
     */
    public static final String TYPE_PERSONAL_GROUP = "PERSONAL_GROUP";
	
    public static final int ENABLE = 1;
	
    public static final int UNABLE = 0;
	
    public static final String LOGOUTSYS_KEYWORD = "logout_success";
    
    public ConstantList() {
		super();
	}
    
}
