package com.sds.acube.app.idir.org.ws;

import com.sds.acube.app.idir.org.user.IUser;
import com.sds.acube.app.idir.org.user.IUsers;
import com.sds.acube.app.idir.org.user.UserManager;


/**
 * UserService.java 2009-04-01 사용자 웹서비스 호출
 * @author  geena
 */
public class UserService extends BaseService {

    public UserService()
    {
    	super();
    }
    
	/**
	 * LinkedList를 Value Object의 Array로 만드는 부분.
	 * @param iUsers
	 * @return
	 */
	private IUser[] toArrayIUsers(IUsers iUsers) {
		
		if ((iUsers == null) || (iUsers.size() == 0)) 
			return (new IUser[0]);
		
		IUser[] objects = new IUser[iUsers.size()];
		
		for (int i = 0; i < iUsers.size(); i++) 
			objects[i] = iUsers.get(i);
		
		return objects;
	}
	
	/**
	 * 주어진 ID를 가지는 사용자 정보 
     * @param licenseKey 서비스 License Key 
	 * @param strUserID 사용자 ID
	 * @return IUser
	 */
    public IUser getUserByID(String licenseKey, String strUserID)
    {
        UserManager userManager = new UserManager(connectionParam);
        IUser iUser = userManager.getUserByID(strUserID);
        if(iUser == null)
        	lastError = userManager.getLastError();
        return iUser; 
    }
    
	/**
	 * 주어진 주민등록번호를 가지는 사용자 정보
	 * @param licenseKey 서비스 License Key 
	 * @param strResidentNo 사용자 주민등록번호
	 * @return IUser[]
	 */
    public IUser[] getUsersByResidentNo(String licenseKey, String strResidentNo){
    	
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getUsersByResidentNo(strResidentNo);
        
        if(iUsers == null) {
        	lastError = userManager.getLastError();
        }
        
    	return toArrayIUsers(iUsers);
        //return toArrayIUsers(iUsers); 
    }
    
	/**
	 * 주어진 이름을 가진 사용자 정보 
	 * @param licenseKey 서비스 License Key 
	 * @param strUserName 사용자 이름
	 * @param strOrgID 조직 ID
	 * @param nType  검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param bCaseSensitive 대소문자 구분 여부 ( true : 대소문자 구분, false : 대소문자 무시)
	 * @return IUser[]
	 */
	public IUser[] getUsersByName(String licenseKey, String strUserName, String strOrgID, int nType, boolean bCaseSensitive)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getUsersByName(strUserName, strOrgID, nType, bCaseSensitive);
        
        if(iUsers == null) {
        	lastError = userManager.getLastError();
        }	
        
        return toArrayIUsers(iUsers); 
	}
	

	/**
	 * 주어진 직무명을 가지는 사용자 정보 
	 * @param licenseKey 서비스 License Key 
	 * @param strDutyName 직무명
	 * @return IUser[]
	 */
	public IUser[] getUsersByDutyName(String licenseKey, String strDutyName)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getUsersByDutyName(strDutyName);
        
        if(iUsers == null) {
        	lastError = userManager.getLastError();
        }
        
        return toArrayIUsers(iUsers); 
	}

	
	/**
	 * 주어진 직무명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param licenseKey 서비스 License Key 
	 * @param strDutyName  직무명
	 * @param nDocsPerPage  페이지당 출력 리스트 개수 
	 * @param nCurrentPage  현재 출력 페이지
	 * @return IUser[]
	 */
	public IUser[] getUsersByDutyNamePerPage(String licenseKey, String strDutyName, int nDocsPerPage, int nCurrentPage)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getUsersByDutyName(strDutyName, nDocsPerPage, nCurrentPage);
        
        if(iUsers == null) {
        	lastError = userManager.getLastError();
        }
        
        return toArrayIUsers(iUsers); 			
	}
	
	/**
	 * 주어진 직급명를 가지는 사용자 정보 
	 * @param licenseKey 서비스 License Key 
	 * @param strGradeName 직급명
	 * @return IUser[]
	 */
	public IUser[] getUsersByGradeName(String licenseKey, String strGradeName)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getUsersByGradeName(strGradeName);
        
        if(iUsers == null) {
        	lastError = userManager.getLastError();
        }
        
        return toArrayIUsers(iUsers);
	}
	
	/**
	 * 주어진 직급명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param licenseKey 서비스 License Key 
	 * @param strGradeName  직급명
	 * @param nDocsPerPage  페이지당 출력 리스트 개수 
	 * @param nCurrentPage  현재 출력 페이지
	 * @return IUser[]
	 */
	public IUser[] getUsersByGradeNamePerPage(String licenseKey, String strGradeName, int nDocsPerPage, int nCurrentPage)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getUsersByGradeName(strGradeName, nDocsPerPage, nCurrentPage);
        
        if(iUsers == null)
        	lastError = userManager.getLastError();
        	
        return toArrayIUsers(iUsers); 			
	}
	
	/**
	 * 주어진 직급명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param licenseKey 서비스 License Key 
	 * @param strGradeName  직급명
	 * @param strOrgID 		조직 ID
	 * @param nType  		검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 	페이지당 출력 리스트 개수 
	 * @param nCurrentPage 	현재 출력 페이지
	 * @return IUser[]
	 */
	public IUser[] getUsersByGradeNamePerPageAndScope(String licenseKey, String strGradeName, String strOrgID, int nType,
			int nDocsPerPage, int nCurrentPage)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getUsersByGradeName(strGradeName, strOrgID, nType, nDocsPerPage, nCurrentPage);
        
        if(iUsers == null)
        	lastError = userManager.getLastError();
        	
        return toArrayIUsers(iUsers); 	
	}
	
	/**
	 * 주어진 직위명를 가지는 사용자 정보 
	 * @param licenseKey 서비스 License Key 
	 * @param strPositionName 직위명
	 * @return IUser[]
	 */
	public IUser[] getUsersByPositionName(String licenseKey, String strPositionName)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getUsersByPositionName(strPositionName);
        
        if(iUsers == null)
        	lastError = userManager.getLastError();
        	
        return toArrayIUsers(iUsers); 
	}
	
	/**
	 * 주어진 직위명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param licenseKey 서비스 License Key 
	 * @param strPositionName  직위명
	 * @param nDocsPerPage  페이지당 출력 리스트 개수 
	 * @param nCurrentPage  현재 출력 페이지
	 * @return IUser[]
	 */
	public IUser[] getUsersByPositionNamePerPage(String licenseKey, String strPositionName, int nDocsPerPage, int nCurrentPage) 
	{	
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getUsersByPositionName(strPositionName, nDocsPerPage, nCurrentPage);
        
        if(iUsers == null)
        	lastError = userManager.getLastError();
        	
        return toArrayIUsers(iUsers); 	
	}
	
	/**
	 * 주어진 직위명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param licenseKey 서비스 License Key 
	 * @param strPositionName   직위명
	 * @param strOrgID 			조직 ID
	 * @param nType  			검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @return IUser[]
	 */
	public IUser[] getUsersByPositionNameScope(String licenseKey, String strPositionName, String strOrgID, int nType)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getUsersByPositionName(strPositionName, strOrgID, nType);
        
        if(iUsers == null)
        	lastError = userManager.getLastError();
        	
        return toArrayIUsers(iUsers); 
	}
	
	/**
	 * 주어진 직위명을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param licenseKey 서비스 License Key 
	 * @param strPositionName   직위명
	 * @param strOrgID 			조직 ID
	 * @param nType  			검색 type ( 0 : 전체 검색(Group), 1 : 회사내 검색, 2 :  부서내 검색)
	 * @param nDocsPerPage 		페이지당 출력 리스트 개수 
	 * @param nCurrentPage 		현재 출력 페이지
	 * @return IUser[]
	 */
	public IUser[] getUsersByPositionNamePerPageAndScope(String licenseKey, String strPositionName, String strOrgID, int nType,
								 	  	 int nDocsPerPage, int nCurrentPage)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getUsersByPositionName(strPositionName, strOrgID, nType, 
        		nDocsPerPage, nCurrentPage);
        
        if(iUsers == null)
        	lastError = userManager.getLastError();
        	
        return toArrayIUsers(iUsers); 
	}
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.
	 * @param licenseKey 서비스 License Key 
	 * @param strDeptName  조직명
	 * @return IUser[]
	 */
	public IUser[] getUsersByDeptName(String licenseKey, String strDeptName)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getUsersByDeptName(strDeptName);
        
        if(iUsers == null)
        	lastError = userManager.getLastError();
        	
        return toArrayIUsers(iUsers); 
	}   
	
	/**
	 * 주어진 이름을 가진 부서의 사용자 정보 검색.(검색 개수 설정)
	 * @param licenseKey 서비스 License Key 
	 * @param strDeptName  조직명
	 * @param bCaseSensitive 대소문자 구분 여부 (true : 대소문자 구분 / false : 대소문자 무시)
	 * @param bTrim 공백 문자 제거 여부 (true : 공백 문자 제거 / false : 공백문자 제거 하지 않음)
	 * @return IUser[]
	 */
	public IUser[] getUsersByDeptNameWithOption(String licenseKey, String strDeptName, 
									 boolean bCaseSensitive,
									 boolean bTrim)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getUsersByDeptName(strDeptName, bCaseSensitive, bTrim);
        
        if(iUsers == null)
        	lastError = userManager.getLastError();
        	
        return toArrayIUsers(iUsers); 
	}
	
	/**
	 * 주어진 UID를 가지는 사용자 정보
	 * @param licenseKey 서비스 License Key  
	 * @param strUserUID
	 * @return IUser
	 */
	public IUser getUserByUID(String licenseKey, String strUserUID)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUser iUser = userManager.getUserByUID(strUserUID);
        
        if(iUser == null)
        	lastError = userManager.getLastError();
        	
        return iUser; 
	}
	
	/**
	 * 주어진 부서 ID를 가지는 사용자 정보 
	 * @param licenseKey 서비스 License Key 
	 * @param strDeptID 부서ID
	 * @return IUser[]
	 */
	public IUser[] getUsersByDeptID(String licenseKey, String strDeptID)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getUsersByDeptID(strDeptID);
        
        if(iUsers == null)
        	lastError = userManager.getLastError();
        	
        return toArrayIUsers(iUsers); 
	}
	
	
	/**
	 * 주어진 부서 ID를 가지는 사용자 정보 
	 * @param licenseKey 서비스 License Key 
	 * @param strDeptID 부서ID
	 * @param nOrgType  조직 Type
	 * @return IUser[]
	 */
	public IUser[] getUsersByDeptIDScope(String licenseKey, String strDeptID, int nOrgType)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getUsersByDeptID(strDeptID, nOrgType);
        
        if(iUsers == null)
        	lastError = userManager.getLastError();
        	
        return toArrayIUsers(iUsers); 
	}
	
	/**
	 * 주어진 회사 ID를 가지는 사용자 정보 
	 * @param licenseKey 서비스 License Key 
	 * @param strCompID 부서ID
	 * @return IUser[]
	 */
	public IUser[] getUsersByCompID(String licenseKey, String strCompID)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getUsersByCompID(strCompID);
        
        if(iUsers == null)
        	lastError = userManager.getLastError();
        	
        return toArrayIUsers(iUsers); 
	}
	
	/**
	 * 주어진 ID를 가지는 사용자와 관련된 사용자 정보
	 * @param licenseKey 서비스 License Key 
	 * @param strUserID 사용자 ID
	 * @return IUser[]
	 */
	public IUser[] getRelatedUsersByID(String licenseKey, String strUserID)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getRelatedUsersByID(strUserID);
        
        if(iUsers == null)
        	lastError = userManager.getLastError();
        	
        return toArrayIUsers(iUsers); 
	}
	
	/**
	 * 주어진 사용자의 겸직 사용자 정보
	 * @param licenseKey 서비스 License Key  
	 * @param strUserID 사용자 ID
	 * @return IUser[]
	 */
	public IUser[] getConcurrentUsersByID(String licenseKey, String strUserID)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUsers iUsers = userManager.getConcurrentUsersByID(strUserID);
        
        if(iUsers == null)
        	lastError = userManager.getLastError();
        
        return toArrayIUsers(iUsers);         
	}
	
	
	/**
	 * 주어진 Certification ID를 가지는 사용자 정보 
	 * @param licenseKey 서비스 License Key 
	 * @param strCertificationID 인증서 ID
	 * @return IUser
	 */
/*	public IUser getUserByCertificationID(String licenseKey, String strCertificationID)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUser iUser = userManager.getUserByCertificationID(strCertificationID);
        if(iUser == null)
        	lastError = userManager.getLastError();
        return iUser; 
	}
	
*/
//20090414 추가
	
	/**
	 * 주어진 System Email Address를 가지는 사용자 정보 
	 * @param licenseKey 서비스 License Key 
	 * @param strSysmail System Email Address
	 * @return IUser
	 */
	public IUser getUserBySysmail(String licenseKey, String strSysmail)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUser iUser = userManager.getUserBySysmail(strSysmail);
        if(iUser == null)
        	lastError = userManager.getLastError();
        return iUser; 
	}
	
	/**
	 * 주어진 ID와 PW로 로그인 인증
	 * @param licenseKey 서비스 License Key 
	 * @param strUserID 사용자 ID
	 * @param strUserPWD 사용자 PassWord
	 * @param nType 		로그인 Data 비교 방법
	 * 						0 : DB Data 값 그대로 비교 
	 * 						1 : sutil에서 제공하는 디코딩 모듈 사용
	 * 						2 : sutil 모듈로 DB 데이타만 디코딩 후 비교
	 * @return boolean
	 */
	public boolean authenticate(String licenseKey, String strUserID, String strUserPWD, int nType)
	{
        UserManager userManager = new UserManager(connectionParam);
        IUser iUser = userManager.getUserByID(strUserID, strUserPWD, nType);
        
        if(iUser == null){
        	lastError = userManager.getLastError();
        }
        
        if(iUser.getLoginResult()==IUser.LOGIN_SUCCESS)
        	return true;
        
       	return false;
	}
	
	
    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public String getLastError()
    {
        return lastError;  
    }
 
    /**
	 */
    private String lastError;

}

