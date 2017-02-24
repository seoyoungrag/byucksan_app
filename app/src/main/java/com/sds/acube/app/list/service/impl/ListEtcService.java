package com.sds.acube.app.list.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.anyframe.pagination.Page;
import org.anyframe.util.StringUtil;


import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.ResultVO;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.list.service.IListEtcService;
import com.sds.acube.app.list.util.ListUtil;
import com.sds.acube.app.list.vo.SearchVO;

/**
 * Class Name  : ListEtcService.java <br> Description : 공람게시판, 기타  관련  서비스  <br> Modification Information <br> <br> 수 정  일 : <br> 수 정  자 : <br> 수정내용 : <br>
 * @author   김경훈 
 * @since  2011. 5. 18.
 * @version  1.0 
 * @see  com.sds.acube.app.list.service.impl.ListApprovalService.java
 */

@Service("listEtcService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class ListEtcService extends BaseService implements IListEtcService{
    
    private static final long serialVersionUID = 1L;
    
    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
    
    /**
	 */
    @Inject
    @Named("orgService")
    private IOrgService orgService;
    
    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;  
    
    
    public Page listDisplayNotice(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listDisplayNotice", searchVO, pageIndex);
	    
    }    
    
    public Page listDisplayNotice(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listDisplayNotice", searchVO, pageIndex, pageSize);
	    
    }   
    
    @SuppressWarnings("unchecked")
    public List<AppDocVO> listDisplayNotice(SearchVO searchVO) throws Exception {
	    return this.commonDAO.getList("list.listDisplayNoticeToList", searchVO);
	    
    }
    
    public Page listAllDisplayNotice(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listAllDisplayNotice", searchVO, pageIndex);
	    
    }    

    public Page listAllDisplayNotice(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
    	    return this.commonDAO.getPagingList("list.listAllDisplayNotice", searchVO, pageIndex, pageSize);
    	    
    }   
    
    @SuppressWarnings("unchecked")
    public List<AppDocVO> listAllDisplayNotice(SearchVO searchVO) throws Exception {
	    return this.commonDAO.getList("list.listAllDisplayNoticeToList", searchVO);
	    
    }
    
    public String getRowDeptIds(String compId, String deptId, String orgType) throws Exception {
	 String rowDeptId = "''";
	 try{
	     OrganizationVO orgVO = new OrganizationVO();
	     orgVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, orgType);
	     String organId = StringUtil.null2str(orgVO.getOrgID());

	     int loopSize = 0;

	     List<OrganizationVO> org = orgService.selectSubOrganizationListByRoleCode(organId, orgType);

	     loopSize = org.size();

	     if(loopSize > 0){
		 StringBuffer buff = new StringBuffer();

		 for(int i=0; i<loopSize; i++){
		     String getOrgId = org.get(i).getOrgID();

		     if(i != (loopSize -1) ) {
			 buff.append(getOrgId+",");
		     }else{
			 buff.append(getOrgId);
		     }
		 }
		 rowDeptId = ListUtil.TransString(buff.toString());

	     }else{
		 rowDeptId = "''";
	     }
	 }catch(Exception e){
	     logger.debug("exception :"+e);
	 }
	 
	 return rowDeptId;
	 
    }
    
    public String getRowDeptIds(String deptId, String orgType) throws Exception {
	 String rowDeptId = "''";
	 
	 try{
	     int loopSize = 0;

	     List<OrganizationVO> org = orgService.selectSubOrganizationListByRoleCode(deptId, orgType);

	     loopSize = org.size();

	     if(loopSize > 0){
		 StringBuffer buff = new StringBuffer();

		 for(int i=0; i<loopSize; i++){
		     String getOrgId = org.get(i).getOrgID();

		     if(i != (loopSize -1) ) {
			 buff.append(getOrgId+",");
		     }else{
			 buff.append(getOrgId);
		     }
		 }
		 rowDeptId = ListUtil.TransString(buff.toString());

	     }else{
		 rowDeptId = "''";
	     }
	 }catch(Exception e){
	     logger.debug("exception :"+e);
	 }
	 
	 return rowDeptId;
	 
    }
    
    
    public String getDeptId(String compId, String deptId, String orgType) throws Exception {
	 String returnDeptId = "";
	 
	 try{
	     OrganizationVO orgVO = new OrganizationVO();
	     orgVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, orgType);
	     returnDeptId  = StringUtil.null2str(orgVO.getOrgID());
	 }catch(Exception e) {
	     logger.debug("exception :"+e);
	 }
	 return returnDeptId;
    }
    
    public String getDeptName(String compId, String deptId, String orgType) throws Exception {
	 String returnDeptName = "";
	 
	 try{
	     OrganizationVO orgVO = new OrganizationVO();
	     orgVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, orgType);
	     returnDeptName  = StringUtil.null2str(orgVO.getOrgName());
	 }catch(Exception e) {
	     logger.debug("exception :"+e);
	 }
	 return returnDeptName;
   }
    
    public OrganizationVO getDeptInfo(String compId, String deptId, String orgType) throws Exception {
	OrganizationVO returnDeptInfo = new OrganizationVO();
	 
	 try{
	     OrganizationVO orgVO = new OrganizationVO();
	     orgVO = orgService.selectHeadOrganizationByRoleCode(compId, deptId, orgType);
	     returnDeptInfo  = orgVO;
	 }catch(Exception e) {
	     logger.debug("exception :"+e);
	 }
	 return returnDeptInfo;
    }
    
    
    @SuppressWarnings("unchecked")
    public int getAppIngBindCount(String compId, String bindingId) throws Exception {
	int returnCnt = 0;
	compId = StringUtil.null2str(compId,"");
	bindingId = StringUtil.null2str(bindingId,"");
	
	String docAppState = ListUtil.TransString("APP600,","APP");
	
	try{

	    Map<String, String> getMap = new HashMap<String, String>();
	    getMap.put("compId", compId);
	    getMap.put("bindingId", bindingId);
	    getMap.put("docAppState", docAppState);

	    Map map = (Map) this.commonDAO.getMap("list.getAppIngBindCount", getMap);   
	    Integer num = new Integer("" + map.get("CNT"));
	    returnCnt = num.intValue();
	    
	}catch(Exception e) {
	    logger.debug("exception :"+e);
	}
	
	return returnCnt;
    }
    
    
    public Page listAppIngBind(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listAppIngBind", searchVO, pageIndex);
	    
    } 
    
    
    public Map<String,String> returnDetailSearchApprRole(String compId)throws Exception {
	Map<String,String> map = new HashMap<String, String>();
	
	String opt003G = "N";
	String opt053G = "N";  // jth8172 2012 신결재 TF
	String opt009G = "N";
	String opt019G = "N";
	
	String opt003 = appCode.getProperty("OPT003", "OPT003", "OPT"); // 협조
	String opt004 = appCode.getProperty("OPT004", "OPT004", "OPT"); // 병렬협조
	String opt005 = appCode.getProperty("OPT005", "OPT005", "OPT"); // 부서협조
	 // jth8172 2012 신결재 TF
	String opt053 = appCode.getProperty("OPT053", "OPT053", "OPT"); // 협조
	String opt054 = appCode.getProperty("OPT054", "OPT054", "OPT"); // 병렬협조
	String opt055 = appCode.getProperty("OPT055", "OPT055", "OPT"); // 부서협조

	String opt009 = appCode.getProperty("OPT009", "OPT009", "OPT"); // 감사
	String opt010 = appCode.getProperty("OPT010", "OPT010", "OPT"); // 부서감사
	String opt021 = appCode.getProperty("OPT021", "OPT021", "OPT"); // 일상감사
	String opt022 = appCode.getProperty("OPT022", "OPT022", "OPT"); // 준법감시
	String opt023 = appCode.getProperty("OPT023", "OPT023", "OPT"); // 감사위원
	String opt019 = appCode.getProperty("OPT019", "OPT019", "OPT"); // 선람
	
	String opt003Yn = StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt003), "N");
	String opt004Yn = StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt004), "N");
	String opt005Yn = StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt005), "N");
	 // jth8172 2012 신결재 TF
	String opt053Yn = StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt053), "N");
	String opt054Yn = StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt054), "N");
	String opt055Yn = StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt055), "N");

	String opt009Yn = StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt009), "N");
	String opt010Yn = StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt010), "N");
	String opt019Yn = StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt019), "N");
	String opt021Yn = StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt021), "N");
	String opt022Yn = StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt022), "N");
	String opt023Yn = StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,opt023), "N");
	
	if("Y".equals(opt003Yn) || "Y".equals(opt004Yn) || "Y".equals(opt005Yn)) {
	    opt003G = "Y";
	}
	 // jth8172 2012 신결재 TF
	if("Y".equals(opt053Yn) || "Y".equals(opt054Yn) || "Y".equals(opt055Yn)) {
	    opt053G = "Y";
	}
	
	if("Y".equals(opt009Yn) || "Y".equals(opt010Yn) || "Y".equals(opt021Yn) || "Y".equals(opt022Yn) || "Y".equals(opt023Yn)) {
	    opt009G = "Y";
	}
	
	if("Y".equals(opt019Yn)) {
	    opt019G = "Y";
	}
	
	
	map.put("opt003G", opt003G);
	map.put("opt053G", opt053G);  // jth8172 2012 신결재 TF
	map.put("opt009G", opt009G);
	map.put("opt019G", opt019G);
	
	return map;
    }
    
    public Map<String,String> returnDetailSearchApprRole(String compId, ArrayList<String> arrList)throws Exception {
	Map<String,String> map = new HashMap<String, String>();
	
	String mapValue = "";
	int nSize = arrList.size();
	
	for(int i=0; i < nSize; i++){
	    mapValue = StringUtil.null2str(envOptionAPIService.selectOptionValue(compId,arrList.get(i)), "N");
	    
	    map.put(arrList.get(i).toString(),mapValue);
	}
	
	return map;
    }
    
    
    public ResultVO deleteBizProc(String compId, String docId) throws Exception{
	ResultVO resultVO = new ResultVO();
	
	Map<String, String> map = new HashMap<String,String>();
	
	map.put("compId",compId);
	map.put("docId",docId);
	
	int result = commonDAO.deleteMap("list.deleteBizProc", map);
	
	if (result > 0) {
	    resultVO.setResultCode("success");
	    resultVO.setResultMessageKey("approval.msg.deleted.rejected.history");
	} else {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.fail.delete.history");
	}
	
	return resultVO;
	
    }
    
    
    public ResultVO deleteDocToMgr(String compId, String docId) throws Exception{
	ResultVO resultVO = new ResultVO();
	
	Map<String, String> map = new HashMap<String,String>();
	
	map.put("compId",compId);
	map.put("docId",docId);
	
	int result = commonDAO.deleteMap("list.deleteDocToMgr", map);
	
	if (result > 0) {
	    resultVO.setResultCode("success");
	    resultVO.setResultMessageKey("approval.msg.deleted.rejected.history");
	} else {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.fail.delete.history");
	}
	
	return resultVO;
	
    }
    
    
    public ResultVO deleteAccHis(String compId, String hisId) throws Exception{
	ResultVO resultVO = new ResultVO();
	
	Map<String, String> map = new HashMap<String,String>();
	
	map.put("compId",compId);
	map.put("hisId",hisId);
	
	int result = commonDAO.deleteMap("list.deleteAccHis", map);
	
	if (result > 0) {
	    resultVO.setResultCode("success");
	    resultVO.setResultMessageKey("approval.msg.deleted.rejected.history");
	} else {
	    resultVO.setResultCode("fail");
	    resultVO.setResultMessageKey("approval.msg.fail.delete.history");
	}
	
	return resultVO;
	
    }
    
}
