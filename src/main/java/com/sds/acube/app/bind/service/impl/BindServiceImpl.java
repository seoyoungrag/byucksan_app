/**
 * 
 */
package com.sds.acube.app.bind.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.bind.BindConstants;
import com.sds.acube.app.bind.service.BindService;
import com.sds.acube.app.bind.vo.BatchVO;
import com.sds.acube.app.bind.vo.BindAuthVO;
import com.sds.acube.app.bind.vo.BindBatchVO;
import com.sds.acube.app.bind.vo.BindManagerVO;
import com.sds.acube.app.bind.vo.BindUnitVO;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.service.impl.OrgService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.Tree;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.common.vo.UserVO;
import com.sds.acube.app.list.service.IListEtcService;


/**
 * Class Name : BindServiceImpl.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 29. <br> 수 정 자 : I-ON <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 3. 29.
 * @version  1.0
 * @see  com.sds.acube.app.bind.service.impl.BindServiceImpl.java
 */

@Service("bindService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class BindServiceImpl extends BaseService implements BindService, BindConstants {

    private static final long serialVersionUID = 1211245176110782181L;

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDao;
    
    /**
	 */
    @Inject
    @Named("orgService")
    private OrgService orgService;
    
    @Inject
    @Named("listEtcService")
    private IListEtcService listEtcService;


    @SuppressWarnings("unchecked")
    public List<BindVO> getList(Map<String, String> param) throws Exception {
    	return this.commonDao.getList("bind.list", param);
    }
    
    @SuppressWarnings("unchecked")
	public List<BindVO> getList2(Map<String, String> param) throws Exception {
    	return this.commonDao.getList("bind.list2", param);
    }
    
    // 다국어 추가
    @SuppressWarnings("unchecked")
    public List<BindVO> getListResource(Map<String, String> param) throws Exception {
    	return this.commonDao.getList("bind.listResource", param);
    }
    
    @SuppressWarnings("unchecked")
    public List<BindVO> getbindList(BindVO bindVO) throws Exception {
    	return this.commonDao.getList("bind.unit.simple.select.for.bind", bindVO);
    }

    // 다국어 추가
    @SuppressWarnings("unchecked")
    public List<BindVO> getbindListResource(BindVO bindVO) throws Exception {
    	return this.commonDao.getList("bind.unit.simple.select.for.bindResource", bindVO);
    }

    public int insert(BindVO bindVO) throws Exception {
    	
    	//캐비닛 관리자 insert start[
		String adminInfo=bindVO.getAdminInfo();
		String delimiter1 = String.valueOf((char) 2);
		String delimiter2 = String.valueOf((char) 4);
		String admins[] = null;
		
		if(adminInfo!=null&&!adminInfo.isEmpty()){
			admins = adminInfo.split(delimiter2);
			for (String adminStr : admins) {
		    	String admin[] = adminStr.split(delimiter1);
		    	BindManagerVO bindManager = new BindManagerVO();
				bindManager.setBindId(bindVO.getBindId());
				bindManager.setCompId(bindVO.getCompId());
				bindManager.setDeptId(bindVO.getDeptId());
		    	bindManager.setManagerId(admin[0]);
		    	commonDao.insert("bindAdminInfo.insert", bindManager);
			}
		}
		//캐비닛 관리자 insert end]
		
    	//권한 insert start[
		String authInfo=bindVO.getAuthInfo();
		String auths[] = null;
		
		if(authInfo!=null&&!authInfo.isEmpty()){
			auths = authInfo.split(delimiter2);
			for (String authStr : auths) {
		    	String auth[] = authStr.split(delimiter1);
		    	BindAuthVO bindAuth = new BindAuthVO();
		    	bindAuth.setBindId(bindVO.getBindId());
		    	bindAuth.setCompId(bindVO.getCompId());
		    	bindAuth.setDeptId(bindVO.getDeptId());
		    	bindAuth.setAuthDeptId(auth[0]);
		    	bindAuth.setBindAuthority(auth[1]);
		    	commonDao.insert("bindAuthInfo.insert", bindAuth);
			}
		}
		//편철 관리자 insert end]
		
    	return this.commonDao.insert("bind.insert", bindVO);
    }
    
 public int insertShare(BindVO bindVO) throws Exception {
    	
    	//캐비닛 관리자 insert start[
		String adminInfo=bindVO.getAdminInfo();
		String delimiter1 = String.valueOf((char) 2);
		String delimiter2 = String.valueOf((char) 4);
		String admins[] = null;
		
		if(adminInfo!=null&&!adminInfo.isEmpty()){
			admins = adminInfo.split(delimiter2);
			for (String adminStr : admins) {
		    	String admin[] = adminStr.split(delimiter1);
		    	BindManagerVO bindManager = new BindManagerVO();
				bindManager.setBindId(bindVO.getBindId());
				bindManager.setCompId(bindVO.getCompId());
				bindManager.setDeptId(bindVO.getDeptId());
		    	bindManager.setManagerId(admin[0]);
		    	commonDao.insert("bindAdminInfo.insert", bindManager);
			}
		}
		//캐비닛 관리자 insert end]
		
    	//권한 insert start[
		String authInfo=bindVO.getAuthInfo();
		String auths[] = null;
		
		if(authInfo!=null&&!authInfo.isEmpty()){
			auths = authInfo.split(delimiter2);
			for (String authStr : auths) {
		    	String auth[] = authStr.split(delimiter1);
		    	BindAuthVO bindAuth = new BindAuthVO();
		    	bindAuth.setBindId(bindVO.getBindId());
		    	bindAuth.setCompId(bindVO.getCompId());
		    	bindAuth.setDeptId(bindVO.getDeptId());
		    	bindAuth.setAuthDeptId(auth[0]);
		    	bindAuth.setBindAuthority(auth[1]);
		    	commonDao.insert("bindAuthInfo.insert", bindAuth);
			}
		}
		//편철 관리자 insert end]
		
    	return this.commonDao.insert("bind.insertShare", bindVO);
    }
    

    public void _update(BindVO bindVO, BindVO setupBind)throws Exception {
	    BindVO bind = getMinor(bindVO.getCompId(), bindVO.getDeptId(), bindVO.getBindId());

	   /* bind.setModifiedId(setupBind.getModifiedId());*/
	    bind.setCurrentTime(setupBind.getCurrentTime());
	    bind.setArrange(setupBind.getArrange());
	    bind.setBinding(setupBind.getBinding());
	    this.commonDao.modify("bind.update", bind);
    }
    
    public int updateAll(BindVO bindVO) throws Exception {
    	ArrayList<BindVO> resultList = new ArrayList<BindVO>();
    	
    	BindVO setupBind = new BindVO();
    	/*setupBind.setModifiedId(bindVO.getModifiedId());*/
    	setupBind.setCurrentTime(bindVO.getCurrentTime());
    	setupBind.setArrange(bindVO.getArrange());
    	setupBind.setBinding(bindVO.getBinding());
		
    	resultList.add(bindVO);
    	//BindVO rBind = _update(bindVO, setupBind);
	   	
    	BindVO rBind = getMinor(bindVO.getCompId(), bindVO.getDeptId(), bindVO.getBindId());
    	 
    	String query = "bind.listTreebind";
    	String tempBindName = "";
    	String tempUnitName = "";
    	tempUnitName = rBind.getUnitName();
    	tempBindName = rBind.getBindName();
    	rBind.setUnitName(null); //자식 검색을 위해 null, 입력 값을 가지고 있으면 검색이 안됨
    	rBind.setBindName(null);
    	rBind.setIsSelectType(BindVO.TREE_CHILD);
    	rBind.setBindType("SIMPLE");
    	rBind.setLangType(AppConfig.getCurrentLangType());
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query,rBind);
    	rBind.setUnitName(tempUnitName);
    	rBind.setBindName(tempBindName);
    	
        for(BindVO bind: list) {
        	updateAllChildTreeBind(bind, resultList, query);	
        }
        
        for(BindVO bind: resultList) {
        	_update(bind, setupBind);
        }
        return 0;
    }

    
    public int updateAllChildTreeBind(BindVO bindVO, ArrayList<BindVO> resultList, String query) throws Exception {
    	
    	//BindVO rBind = _update(bindVO, setupBind);
    	resultList.add(bindVO);
		
    	bindVO.setUnitName(null); //자식 검색을 위해 null, 입력 값을 가지고 있으면 검색이 안됨
    	bindVO.setBindName(null);
    	bindVO.setIsSelectType(BindVO.TREE_CHILD);
    	bindVO.setBindType("SIMPLE");
    	bindVO.setLangType(AppConfig.getCurrentLangType());
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, bindVO);

    	for(BindVO bind: list) {
    		updateAllChildTreeBind(bind, resultList, query);
        }
    	return 0;
    }

    public int update(BindVO bindVO) throws Exception {
    	
		String adminInfo=bindVO.getAdminInfo();
		String origAdminInfo=bindVO.getOrigAdminInfo();
		
		String delimiter1 = String.valueOf((char) 2);
		String delimiter2 = String.valueOf((char) 4);
		
		
		String admins[] = null;
		String origAdmins[] = null;
		
		//삭제할 관리자 리스트
		if(origAdminInfo!=null&&!origAdminInfo.isEmpty()){
			origAdmins = origAdminInfo.split(delimiter2);
			for (String origAdminStr : origAdmins) {
			    if((adminInfo==null)||(adminInfo.indexOf(origAdminStr)==-1)){
			    	String origAdmin[] = origAdminStr.split(delimiter1);
			    	BindManagerVO bindManager = new BindManagerVO();
					bindManager.setBindId(bindVO.getBindId());
					bindManager.setCompId(bindVO.getCompId());
					bindManager.setDeptId(bindVO.getDeptId());
			    	bindManager.setManagerId(origAdmin[0]);
			    	commonDao.delete("bindAdminInfo.delete", bindManager);
		        }
			}
		}
		
		//추가할 관리자 리스트		
		if(adminInfo!=null&&!adminInfo.isEmpty()){
			admins = adminInfo.split(delimiter2);
			for (String adminStr : admins) {
			    if((origAdminInfo==null)||(origAdminInfo.indexOf(adminStr)==-1)){
			    	String admin[] = adminStr.split(delimiter1);
			    	BindManagerVO bindManager = new BindManagerVO();
					bindManager.setBindId(bindVO.getBindId());
					bindManager.setCompId(bindVO.getCompId());
					bindManager.setDeptId(bindVO.getDeptId());
			    	bindManager.setManagerId(admin[0]);
			    	BindManagerVO resultBindManager = (BindManagerVO) commonDao.get("bindAdminInfo.select", bindManager);
                    if (resultBindManager ==null || resultBindManager.equals("")) {
                      commonDao.insert("bindAdminInfo.insert", bindManager);
                    } else {
                      commonDao.modify("bindAdminInfo.update", bindManager);
                    }
		        }
			}
		}
		
		String authInfo=bindVO.getAuthInfo();
		String origAuthInfo=bindVO.getOrigAuthInfo();
				
		String auths[] = null;
		String origAuths[] = null;
		
		//삭제할 관리자 리스트
		if(origAuthInfo!=null&&!origAuthInfo.isEmpty()){
			origAuths = origAuthInfo.split(delimiter2);
			for (String origAuthStr : origAuths) {
			    if((authInfo==null)||(authInfo.indexOf(origAuthStr)==-1)){
			    	String origAuth[] = origAuthStr.split(delimiter1);
			    	BindAuthVO bindAuth = new BindAuthVO();
			    	bindAuth.setBindId(bindVO.getBindId());
			    	bindAuth.setCompId(bindVO.getCompId());
			    	bindAuth.setDeptId(bindVO.getDeptId());
			    	bindAuth.setAuthDeptId(origAuth[2]);
			    	bindAuth.setBindAuthority(origAuth[1]);
			    	commonDao.delete("bindAuthInfo.delete", bindAuth);
		        }
			}
		}
		
		//추가할 관리자 리스트		
		if(authInfo!=null&&!authInfo.isEmpty()){
			auths = authInfo.split(delimiter2);
			for (String authStr : auths) {
			    if((origAuthInfo==null)||(origAuthInfo.indexOf(authStr)==-1)){
			    	String auth[] = authStr.split(delimiter1);
			    	BindAuthVO bindAuth = new BindAuthVO();
			    	bindAuth.setBindId(bindVO.getBindId());
			    	bindAuth.setCompId(bindVO.getCompId());
			    	bindAuth.setDeptId(bindVO.getDeptId());
			    	bindAuth.setAuthDeptId(auth[0]);
			    	bindAuth.setBindAuthority(auth[1]);
			    	BindAuthVO resultBindAuth = (BindAuthVO) commonDao.get("bindAuthInfo.select", bindAuth);
                    if (resultBindAuth ==null || resultBindAuth.equals("")) {
                      commonDao.insert("bindAuthInfo.insert", bindAuth);
                    } else {
                      commonDao.modify("bindAuthInfo.update", bindAuth);
                    }
		        }
			}
		}
		
    	return this.commonDao.modify("bind.update", bindVO);
    }
    
    public int updateShare(BindVO bindVO) throws Exception {
    	
		String adminInfo=bindVO.getAdminInfo();
		String origAdminInfo=bindVO.getOrigAdminInfo();
		
		String delimiter1 = String.valueOf((char) 2);
		String delimiter2 = String.valueOf((char) 4);
		
		
		String admins[] = null;
		String origAdmins[] = null;
		
		//삭제할 관리자 리스트
		if(origAdminInfo!=null&&!origAdminInfo.isEmpty()){
			origAdmins = origAdminInfo.split(delimiter2);
			for (String origAdminStr : origAdmins) {
		    	String origAdmin[] = origAdminStr.split(delimiter1);
		    	BindManagerVO bindManager = new BindManagerVO();
				bindManager.setBindId(bindVO.getBindId());
				bindManager.setCompId(bindVO.getCompId());
				bindManager.setDeptId(bindVO.getDeptId());
		    	bindManager.setManagerId(origAdmin[0]);
		    	commonDao.delete("bindAdminInfo.delete", bindManager);
			}
		}
		
		//추가할 관리자 리스트		
		if(adminInfo!=null&&!adminInfo.isEmpty()){
			admins = adminInfo.split(delimiter2);
			for (String adminStr : admins) {
		    	String admin[] = adminStr.split(delimiter1);
		    	BindManagerVO bindManager = new BindManagerVO();
				bindManager.setBindId(bindVO.getBindId());
				bindManager.setCompId(bindVO.getCompId());
				bindManager.setDeptId(bindVO.getDeptId());
		    	bindManager.setManagerId(admin[0]);
		    	BindManagerVO resultBindManager = (BindManagerVO) commonDao.get("bindAdminInfo.select", bindManager);
                if (resultBindManager ==null || resultBindManager.equals("")) {
                  commonDao.insert("bindAdminInfo.insert", bindManager);
                } else {
                  commonDao.modify("bindAdminInfo.update", bindManager);
                }
			}
		}
		
		String authInfo=bindVO.getAuthInfo();
		String origAuthInfo=bindVO.getOrigAuthInfo();
				
		String auths[] = null;
		String origAuths[] = null;
		
		//삭제할 관리자 리스트
		if(origAuthInfo!=null&&!origAuthInfo.isEmpty()){
			origAuths = origAuthInfo.split(delimiter2);
			for (String origAuthStr : origAuths) {
			    if((authInfo==null)||(authInfo.indexOf(origAuthStr)==-1)){
			    	String origAuth[] = origAuthStr.split(delimiter1);
			    	BindAuthVO bindAuth = new BindAuthVO();
			    	bindAuth.setBindId(bindVO.getBindId());
			    	bindAuth.setCompId(bindVO.getCompId());
			    	bindAuth.setDeptId(bindVO.getDeptId());
			    	bindAuth.setAuthDeptId(origAuth[2]);
			    	bindAuth.setBindAuthority(origAuth[1]);
			    	commonDao.delete("bindAuthInfo.delete", bindAuth);
		        }
			}
		}
		
		//추가할 관리자 리스트		
		if(authInfo!=null&&!authInfo.isEmpty()){
			auths = authInfo.split(delimiter2);
			for (String authStr : auths) {
			    if((origAuthInfo==null)||(origAuthInfo.indexOf(authStr)==-1)){
			    	String auth[] = authStr.split(delimiter1);
			    	BindAuthVO bindAuth = new BindAuthVO();
			    	bindAuth.setBindId(bindVO.getBindId());
			    	bindAuth.setCompId(bindVO.getCompId());
			    	bindAuth.setDeptId(bindVO.getDeptId());
			    	bindAuth.setAuthDeptId(auth[0]);
			    	bindAuth.setBindAuthority(auth[1]);
			    	BindAuthVO resultBindAuth = (BindAuthVO) commonDao.get("bindAuthInfo.select", bindAuth);
                    if (resultBindAuth ==null || resultBindAuth.equals("")) {
                      commonDao.insert("bindAuthInfo.insert", bindAuth);
                    } else {
                      commonDao.modify("bindAuthInfo.update", bindAuth);
                    }
		        }
			}
		}
		
    	return this.commonDao.modify("bind.updateShare", bindVO);
    }


    public int remove(BindVO bindVO) throws Exception {
    	commonDao.delete("bindAdminInfo.deleteAll", bindVO);
    	commonDao.delete("bindAuthInfo.deleteAll", bindVO);
    	return this.commonDao.delete("bind.delete", bindVO);
    }
    
    public int removeShare(BindVO bindVO) throws Exception {
    	commonDao.delete("bindAdminInfo.deleteAll", bindVO);
    	commonDao.delete("bindAuthInfo.deleteAll", bindVO);
    	return this.commonDao.delete("bind.share.delete", bindVO); //이게 캐비닛을 지우는 .... 맞나?? 아닌가??
    }

    
    public String removeBind(BindVO bindVO) throws Exception {
    	String result = BindVO.SUCCESS;
    	ArrayList<BindVO> resultList = new ArrayList<BindVO>();
		
	   	BindVO rBind = (BindVO) this.commonDao.get("bind.select.selectBind", bindVO);
		if("DST003".equals(rBind.getSendType()))
			return BindVO.REMOVE_BIND_ERROR_DST3;
		else if(rBind.getDocCount()>0)
			return BindVO.REMOVE_BIND_ERROR_DOC;
		else if(rBind.getShareCount()>0)
			return BindVO.REMOVE_BIND_ERROR_SHARED;
		
		rBind.setIsSelectType(BindVO.TREE_CHILD);
		rBind.setBindType("SIMPLE");
		rBind.setLangType(AppConfig.getCurrentLangType());
	   	resultList.add(rBind);
	   	
	   	String query = "bind.listTreebind";
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, rBind);
    	
    	
        for(BindVO bind: list) {
        	result = removeChildTreeBind(bind, resultList, query);
        	if(!BindVO.SUCCESS.equals(result))
    			return result;
        	
        }    	
        for(BindVO bind: resultList) {
        	remove(bind);
        }
        return result;
    }  
    
    public String removeBindShare(BindVO bindVO) throws Exception {
    	String result = BindVO.SUCCESS;
    	ArrayList<BindVO> resultList = new ArrayList<BindVO>();
		
	   	BindVO rBind = (BindVO) this.commonDao.get("bind.select.selectBindShare", bindVO);
		if("DST003".equals(rBind.getSendType()))
			return BindVO.REMOVE_BIND_ERROR_DST3;
		else if(rBind.getDocCount()>0)
			return BindVO.REMOVE_BIND_ERROR_DOC;
		else if(rBind.getShareCount()>0)
			return BindVO.REMOVE_BIND_ERROR_SHARED;
		
		rBind.setIsSelectType(BindVO.TREE_CHILD);
		rBind.setBindType("SIMPLE");
		rBind.setLangType(AppConfig.getCurrentLangType());
	   	resultList.add(rBind);
	   	
	   	String query = "bind.listTreebindShare";
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, rBind);
    	
    	
        for(BindVO bind: list) {
        	result = removeChildTreeBindShare(bind, resultList, query);
        	if(!BindVO.SUCCESS.equals(result))
    			return result;
        	
        }    	
        for(BindVO bind: resultList) {
        	removeShare(bind);
        }
        return result;
    }  
    
    
    
    
    public String removeChildTreeBind(BindVO bindVO, ArrayList<BindVO> resultList, String query) throws Exception {
    	String result = BindVO.SUCCESS;
    	
    	if("DST003".equals(bindVO.getSendType()))
			return BindVO.REMOVE_BIND_ERROR_DST3;
		else if(bindVO.getDocCount()>0)
			return BindVO.REMOVE_BIND_ERROR_DOC;
		else if(bindVO.getShareCount()>0)
			return BindVO.REMOVE_BIND_ERROR_SHARED;
		
        resultList.add(bindVO);
        
    	bindVO.setIsSelectType(BindVO.TREE_CHILD);
    	bindVO.setBindType("SIMPLE");
    	bindVO.setLangType(AppConfig.getCurrentLangType());

    	bindVO.setUnitName(null); //자식 검색을 위해 null 입력 값을 가지고 있으면 검색이 안됨
    	bindVO.setBindName(null);
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, bindVO);
        
    	for(BindVO bind: list) {
    		
    		result = removeChildTreeBind(bind, resultList, query);
    		if(!BindVO.SUCCESS.equals(result))
    			return result;
        }
    	return result;
    }
    
    public String removeChildTreeBindShare(BindVO bindVO, ArrayList<BindVO> resultList, String query) throws Exception {
    	String result = BindVO.SUCCESS;
    	
    	if("DST003".equals(bindVO.getSendType()))
			return BindVO.REMOVE_BIND_ERROR_DST3;
		else if(bindVO.getDocCount()>0)
			return BindVO.REMOVE_BIND_ERROR_DOC;
		else if(bindVO.getShareCount()>0)
			return BindVO.REMOVE_BIND_ERROR_SHARED;
		
        resultList.add(bindVO);
        
    	bindVO.setIsSelectType(BindVO.TREE_CHILD);
    	bindVO.setBindType("SIMPLE");
    	bindVO.setLangType(AppConfig.getCurrentLangType());

    	bindVO.setUnitName(null); //자식 검색을 위해 null 입력 값을 가지고 있으면 검색이 안됨
    	bindVO.setBindName(null);
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, bindVO);
        
    	for(BindVO bind: list) {
    		
    		result = removeChildTreeBindShare(bind, resultList, query);
    		if(!BindVO.SUCCESS.equals(result))
    			return result;
        }
    	return result;
    }

    public BindVO get(String compId, String deptId, String bindId, String unitType) throws Exception {
		BindVO bindVO = new BindVO();
		bindVO.setCompId(compId);
		bindVO.setDeptId(deptId);
		bindVO.setBindId(bindId);
		bindVO.setUnitType(unitType);
		BindVO resultBindVO = null;
		
		resultBindVO = (BindVO) commonDao.get("bind.select", bindVO);
		setInfoManagerAndAuth(resultBindVO, bindId, compId, deptId);
		return resultBindVO;
    }
    
    public BindVO getShare(String compId, String deptId, String bindId, String unitType) throws Exception {
		BindVO bindVO = new BindVO();
		bindVO.setCompId(compId);
		bindVO.setDeptId(deptId);
		bindVO.setBindId(bindId); 
		bindVO.setUnitType(unitType);
		BindVO resultBindVO = null;
		
		resultBindVO = (BindVO) commonDao.get("bind.selectShare", bindVO); 
		setInfoManagerAndAuth(resultBindVO, bindId, compId, deptId);
		return resultBindVO;
    }
    
    //관리자와 권한 정보 셋업
    public void setInfoManagerAndAuth(BindVO resultBindVO, String bindId, String compId, String deptId) throws Exception{
		//관리자 정보를 DB에서 가져옴 start[
		BindManagerVO bindManager = new BindManagerVO();
		List<BindManagerVO> bindManagerList;
		bindManager.setBindId(bindId);
		bindManager.setCompId(compId);
		if(deptId!=null && !deptId.equals("")){
			bindManager.setDeptId(deptId);
		}
		bindManagerList = (List<BindManagerVO>) commonDao.getList("list.listBindAdminInfo", bindManager);
		
		String adminInfo="";
		String delimiter1 = String.valueOf((char) 2);
		String delimiter2 = String.valueOf((char) 4);
		
		int i = 0;
		for(BindManagerVO manager:bindManagerList){
			UserVO user = orgService.selectUserByUserId(manager.getManagerId());
			adminInfo += user.getUserUID() + delimiter1 + user.getUserName() + delimiter1
					+ user.getGradeName() + delimiter1 + user.getDeptID() + delimiter1 + user.getDeptName();
			if(i<bindManagerList.size()-1)
				adminInfo += delimiter2;
			i++;
		}
		if(resultBindVO!=null)
			resultBindVO.setAdminInfo(adminInfo);
		//관리자 정보를 DB에서 가져옴 end]
		
		//권한 정보를 DB에서 가져옴 start[
		BindAuthVO bindAuth = new BindAuthVO();
		List<BindAuthVO> bindAuthList;
		bindAuth.setBindId(bindId);
		bindAuth.setCompId(compId);
		bindAuth.setDeptId(deptId);
		bindAuthList = (List<BindAuthVO>) commonDao.getList("list.listBindAuthInfo", bindAuth);
		
		String authInfo="";
		i = 0;
		for(BindAuthVO auth:bindAuthList){
			OrganizationVO deptVO = orgService.selectOrganization(auth.getAuthDeptId());

			authInfo += auth.getAuthDeptId() + delimiter1 + auth.getBindAuthority() + delimiter1
					+ auth.getAuthDeptId() + delimiter1 + deptVO.getOrgName();
			if(i<bindAuthList.size()-1)
				authInfo += delimiter2;
			i++;
		}
		if(resultBindVO!=null)
			resultBindVO.setAuthInfo(authInfo);
		//권한 정보를 DB에서 가져옴 end]
    }
    
    // 다국어 추가
    public BindVO getResource(String compId, String deptId, String bindId, String unitType, String langType) throws Exception {
		BindVO bindVO = new BindVO();
		bindVO.setCompId(compId);
		bindVO.setDeptId(deptId);
		bindVO.setBindId(bindId);
		bindVO.setUnitType(unitType);
		bindVO.setLangType(langType);
		
		BindVO resultBindVO = null;
		resultBindVO = (BindVO) commonDao.get("bind.selectResource", bindVO);
		
		//관리자 정보를 DB에서 가져옴 start[
		BindManagerVO bindManager = new BindManagerVO();
		List<BindManagerVO> bindManagerList;
		bindManager.setBindId(bindId);
		bindManager.setCompId(compId);
		bindManager.setDeptId(deptId);
		bindManagerList = (List<BindManagerVO>) commonDao.getList("list.listBindAdminInfo", bindManager);
		
		String adminInfo="";
		String delimiter1 = String.valueOf((char) 2);
		String delimiter2 = String.valueOf((char) 4);
		
		int i = 0;
		for(BindManagerVO manager:bindManagerList){
			UserVO user = orgService.selectUserByUserId(manager.getManagerId());
			adminInfo += user.getUserUID() + delimiter1 + user.getUserName() + delimiter1
					+ user.getGradeName() + delimiter1 + user.getDeptID() + delimiter1 + user.getDeptName();
			if(i<bindManagerList.size()-1)
				adminInfo += delimiter2;
			i++;
		}
		
		resultBindVO.setAdminInfo(adminInfo);
		//관리자 정보를 DB에서 가져옴 end]
		
		//권한 정보를 DB에서 가져옴 start[
		BindAuthVO bindAuth = new BindAuthVO();
		List<BindAuthVO> bindAuthList;
		bindAuth.setBindId(bindId);
		bindAuth.setCompId(compId);
		bindAuth.setDeptId(deptId);
		bindAuthList = (List<BindAuthVO>) commonDao.getList("list.listBindAuthInfo", bindAuth);
		
		String authInfo="";
		i = 0;
		for(BindAuthVO auth:bindAuthList){
			OrganizationVO deptVO = orgService.selectOrganization(auth.getAuthDeptId());

			authInfo += auth.getAuthDeptId() + delimiter1 + auth.getBindAuthority() + delimiter1
					+ auth.getAuthDeptId() + delimiter1 + deptVO.getOrgName();
			if(i<bindAuthList.size()-1)
				authInfo += delimiter2;
			i++;
		}
		
		resultBindVO.setAuthInfo(authInfo);
		//관리자 정보를 DB에서 가져옴 end]
		
		return resultBindVO;
    }
    
    public BindVO getMinor(String compId, String deptId, String bindId) throws Exception {
    	BindVO bindVO = new BindVO();
    	bindVO.setCompId(compId);
    	bindVO.setDeptId(deptId);
    	bindVO.setBindId(bindId);
    	return (BindVO) commonDao.get("bind.select.minor", bindVO);
    }
    
    public BindVO getMinorShare(String compId, String deptId, String bindId) throws Exception {
    	BindVO bindVO = new BindVO();
    	bindVO.setCompId(compId);
    	bindVO.setDeptId(deptId);
    	bindVO.setBindId(bindId);
    	return (BindVO) commonDao.get("bind.select.minorShare", bindVO);
    }
    

    // 다국어 추가
    public BindVO getMinorResource(String compId, String deptId, String bindId, String langType) throws Exception {
    	BindVO bindVO = new BindVO();
    	bindVO.setCompId(compId);
    	bindVO.setDeptId(deptId);
    	bindVO.setBindId(bindId);
    	bindVO.setLangType(langType);
    	
    	return (BindVO) commonDao.get("bind.select.minorResource", bindVO);
    }

    @SuppressWarnings("unchecked")
    public List<BindVO> selectBindList(String compId, String deptId, String unitId, String createYear) throws Exception {
		BindVO bindVO = new BindVO();
		bindVO.setCompId(compId);
		bindVO.setDeptId(deptId);
		bindVO.setUnitId(unitId);
		bindVO.setCreateYear(createYear);
		return (List<BindVO>) commonDao.getList("bind.selectBindList", bindVO);
    }


    public int transfer(BindVO vo) throws Exception {
		String nextBindId = getNextBindId();
		vo.setNextBindId(nextBindId);
	
		int result = this.commonDao.insert("bind.transfer.insert", vo);
		if (result > 0) {
		    this.commonDao.modify("bind.transfer.update", vo);
		    if (StringUtils.isEmpty(vo.getOrgBindId())) {
			this.commonDao.modify("bind.transfer.document.insert", vo);
		    }
	
		    //편철함 인계 webservice 주석 jd.park 20120412
		    //folderService.move(vo, get(vo.getCompId(), vo.getTargetId(), nextBindId));
		}
		return result;
    }
    
    public int bindTransfer(BindVO vo) throws Exception {	
    	this.commonDao.insert("bind.transfer.bindAdminInfo", vo); //관리자 정보 인계
    	this.commonDao.insert("bind.transfer.bindAuthInfo", vo); //권한 정보 인계
    	
		int result = this.commonDao.insert("bind.transfer.insert2", vo);
		if (result > 0) {
		    this.commonDao.modify("bind.transfer.update", vo);
		    if (StringUtils.isEmpty(vo.getOrgBindId())) {
			this.commonDao.modify("bind.transfer.document.insert", vo);
			this.commonDao.modify("bind.transfer.board.update", vo);
		    }
	
		    //편철함 인계 webservice 주석 jd.park 20120412
		    //folderService.move(vo, get(vo.getCompId(), vo.getTargetId(), nextBindId));
		}
		return result;
    }


    @SuppressWarnings("unchecked")
    public List<BindVO> getSelectList(Map<String, String> param) throws Exception {
    	return this.commonDao.getList("bind.selectList", param);
    }

    // 다국어 추가
    @SuppressWarnings("unchecked")
    public List<BindVO> getSelectListResource(Map<String, String> param) throws Exception {
    	return this.commonDao.getList("bind.selectListResource", param);
    }
    
    /*@SuppressWarnings("unchecked")
    public BindVO[] getSelectTreeBind(BindVO bindVO) throws Exception {
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList("bind.listTreebind", bindVO);
        String isChildBind = "";
        Tree tree = new Tree();
        BindVO bind = new BindVO();

        BindVO[] resultArray =  new BindVO[list.size()];
        list.toArray(resultArray);
        
    	ArrayList<BindVO> childList = null;
    	BindVO[] childArray = null;

        for(int i = 0; i < resultArray.length; i++) {
        	bind = resultArray[i];

            // 하위장소 setting
        	bind.setIsSelectType(bindVO.TREE_CHILD);
        	bind.setLangType(AppConfig.getCurrentLangType());
        	String tempBindName = "";
        	String tempUnitName = "";
        	tempUnitName = bind.getUnitName();
        	tempBindName = bind.getBindName();
        	bind.setUnitName(null); //자식 검색을 위해 null 입력 값을 가지고 있으면 검색이 안됨
        	bind.setBindName(null);
        	childList = (ArrayList<BindVO>) this.commonDao.getList("bind.listTreebind", bind);
        	bind.setUnitName(tempUnitName);
        	bind.setBindName(tempBindName);
        	childUnitArray =  new bindVO[childList.size()];
        	childList.toArray(childArray);
            // 하위장소여부 setting
            if(childList.size() != 0) {
            	isChildBind = bindVO.CHILD_STATE_YES;
            } else {
            	isChildBind = bindVO.CHILD_STATE_NO;
            }

            bind.setIsChildBind(isChildBind);
            resultArray[i] = bind;

            String unitId = resultArray[i].getUnitId();
            String parentUnitId = resultArray[i].getParentBindId();

            tree.add(unitId, parentUnitId, resultArray[i]);
        }

        tree.toArray(resultArray);

        return resultArray;
    }*/
    
    // 다국어 추가
    @SuppressWarnings("unchecked")
	public BindVO[] getSelectTreeBindResource(BindVO bindVO) throws Exception {

		bindVO.setIsSelectType(BindVO.TREE_CHILD);
		bindVO.setBindId(BindVO.ROOT);
		String query = "bind.listTreebindResource";
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, bindVO);
    	
    	ArrayList<BindVO> resultList = new ArrayList<BindVO>();
    	
        for(BindVO bind: list) {
        	getSelectChildTreeBind(bind, resultList, query);
        }
        
        BindVO[] resultArray =  new BindVO[resultList.size()];
        resultList.toArray(resultArray);

        return resultArray;
    }

    @SuppressWarnings("unchecked")
    public List<BindVO> getSelectTargetList(Map<String, String> param) throws Exception {
    	return this.commonDao.getList("bind.target.selectList", param);
    }
    
    // 다국어 추가
    @SuppressWarnings("unchecked")
    public List<BindVO> getSelectTargetListResource(Map<String, String> param) throws Exception {
    	return this.commonDao.getList("bind.target.selectListResource", param);
    }
 
    @SuppressWarnings("unchecked")
    public BindVO[] getSelectTreeShareBind(BindVO bindVO) throws Exception {
    	
		bindVO.setIsSelectType(BindVO.TREE_CHILD);
		bindVO.setBindId(BindVO.ROOT);
		
		String query = "bind.listTreeShareBind";
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, bindVO);
    	
    	ArrayList<BindVO> resultList = new ArrayList<BindVO>();
    	
        for(BindVO bind: list) {
        	if(bindVO.getBindType()!=null&&bindVO.getBindType().equals("DOC_BOARD")){
        		bind.setDeptId(bindVO.getDeptId());
        		bind.setBindType(bindVO.getBindType());
        	}
        	getSelectChildTreeBind(bind, resultList, query);
        }
        
        
        BindVO[] resultArray =  new BindVO[resultList.size()];
        resultList.toArray(resultArray);

        return resultArray;
    }
    
    public void getSelectChildTreeBind(BindVO bindVO, ArrayList<BindVO> resultList, String query) throws Exception {
    	bindVO.setIsSelectType(BindVO.TREE_CHILD);
    	bindVO.setLangType(AppConfig.getCurrentLangType());
       	String tempBindName = bindVO.getBindName();
    	String tempUnitName = bindVO.getUnitName();
    	bindVO.setUnitName(null); //자식 검색을 위해 null, 입력 값을 가지고 있으면 검색이 안됨
    	bindVO.setBindName(null);
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, bindVO);
    	bindVO.setUnitName(tempUnitName);
    	bindVO.setBindName(tempBindName);

        // 하위장소여부 setting
    	String isChildBind = "";
        if(list.size() != 0) {
        	isChildBind = bindVO.CHILD_STATE_YES;
        } else {
        	isChildBind = bindVO.CHILD_STATE_NO;
        }

        bindVO.setIsChildBind(isChildBind);
        resultList.add(bindVO);
        
    	for(BindVO bind: list) {
        	if(bindVO.getBindType()!=null&&bindVO.getBindType().equals("DOC_BOARD")){
        		bind.setDeptId(bindVO.getDeptId());
        		bind.setBindType(bindVO.getBindType());
        	}
    		getSelectChildTreeBind(bind, resultList, query);
        }
    }
    
    @SuppressWarnings("unchecked")
    public BindVO[] getSelectTreeBind(BindVO bindVO) throws Exception {
    	
		bindVO.setIsSelectType(BindVO.TREE_CHILD);
		bindVO.setBindId(BindVO.ROOT);
		
		String query = "bind.listTreebind";
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, bindVO);
    	
    	ArrayList<BindVO> resultList = new ArrayList<BindVO>();
    	
        for(BindVO bind: list) {
        	if(bindVO.getBindType()!=null&&bindVO.getBindType().equals("DOC_BOARD")){
        		bind.setDeptId(bindVO.getDeptId());
        		bind.setBindType(bindVO.getBindType());
        	}
        	getSelectChildTreeBind(bind, resultList, query);
        }
        
        
        BindVO[] resultArray =  new BindVO[resultList.size()];
        resultList.toArray(resultArray);

        return resultArray;
    }

    @SuppressWarnings("unchecked")
    public BindVO[] getSelectTargetTreeList(BindVO bindVO) throws Exception {
		bindVO.setIsSelectType(BindVO.TREE_CHILD);
		bindVO.setBindId(BindVO.ROOT);
		String query = "bind.target.selectTreeList";
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, bindVO);
    	
    	ArrayList<BindVO> resultList = new ArrayList<BindVO>();

        for(BindVO bind: list) {
        	getSelectChildTreeBind(bind, resultList, query);
        }
        
        
        BindVO[] resultArray =  new BindVO[resultList.size()];
        resultList.toArray(resultArray);

        return resultArray;
    }

    public String getNextBindId() throws Exception {
		BindVO vo = (BindVO) this.commonDao.get("bind.nextVal", null);
		return vo.getBindId();
    }


    public int autoCreate(Map<String, String> param) throws Exception {
    	return this.commonDao.insert("bind.auto.create", param);
    }


    public int share(BindVO bindVO) throws Exception {
    	this.commonDao.insert("bind.transfer.bindAdminInfo", bindVO); //관리자 정보 인계
    	this.commonDao.insert("bind.transfer.bindAuthInfo", bindVO); //권한 정보 인계
    	return this.commonDao.insert("bind.share.insert", bindVO);
    }
    
    public String shareBind(BindVO bindVO) throws Exception {
    	String result = BindVO.SUCCESS;
    	String targetId = bindVO.getTargetId();
    	ArrayList<BindVO> resultList = new ArrayList<BindVO>();
		
	   	BindVO rBind = (BindVO) this.commonDao.get("bind.select.selectBind", bindVO);
		if("DST003".equals(rBind.getSendType()))
			return BindVO.SHARE_BIND_ERROR_DST3;
		if("DST004".equals(rBind.getSendType()))
			return BindVO.SHARE_BIND_ERROR_DST4;
		if("UTT002".equals(rBind.getUnitType()))
			return BindVO.SHARE_BIND_ERROR_UTT002;
		
		rBind.setIsSelectType(BindVO.TREE_CHILD);
		rBind.setBindType("SIMPLE");
		rBind.setTargetId(targetId);
		String nextBindId = getNextBindId();
		rBind.setNextBindId(nextBindId);
		rBind.setLangType(AppConfig.getCurrentLangType());
		share(rBind);
		
	   	String query = "bind.listTreebind";
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, rBind);
    	
        for(BindVO bind: list) {
        	result = shareChildTreeBind(bind, targetId, nextBindId, query);
        	if(!BindVO.SUCCESS.equals(result))
    			return result;
        	
        }
        return result;
    }
    
    public String shareChildTreeBind(BindVO bindVO, String targetId, String parentBindId, String query) throws Exception {
    	String result = BindVO.SUCCESS;
    	
		if("DST003".equals(bindVO.getSendType()))
			return BindVO.SHARE_BIND_ERROR_DST3;
		if("DST004".equals(bindVO.getSendType()))
			return BindVO.SHARE_BIND_ERROR_DST4;
		if("UTT002".equals(bindVO.getUnitType()))
			return BindVO.SHARE_BIND_ERROR_UTT002;
		
	   	bindVO.setTargetId(targetId);
    	bindVO.setParentBindId(parentBindId);
		String nextBindId = getNextBindId();
		bindVO.setNextBindId(nextBindId);
    	bindVO.setIsSelectType(BindVO.TREE_CHILD);
    	bindVO.setBindType("SIMPLE");
    	bindVO.setLangType(AppConfig.getCurrentLangType());
    	share(bindVO);

    	bindVO.setUnitName(null); //자식 검색을 위해 null, 입력 값을 가지고 있으면 검색이 안됨
    	bindVO.setBindName(null);
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, bindVO);

    	for(BindVO bind: list) {
    		result = shareChildTreeBind(bind, targetId, nextBindId, query);
    		if(!BindVO.SUCCESS.equals(result))
    			return result;
        }
    	return result;
    }
   

    @SuppressWarnings("unchecked")
    public List<BindVO> getSharedList(Map<String, String> param) throws Exception {
    	return this.commonDao.getList("bind.share.list", param);
    }


    
    public void removeShareBind(BindVO vo) throws Exception{
    	removeShare(vo);
    	vo.setIsSelectType(BindVO.TREE_CHILD);
    	vo.setBindType("SIMPLE");
		String query = "bind.listTreeShareBind";
    	String tempBindName = "";
    	String tempUnitName = "";
    	tempUnitName = vo.getUnitName();
    	tempBindName = vo.getBindName();
    	vo.setUnitName(null); //자식 검색을 위해 null, 입력 값을 가지고 있으면 검색이 안됨
    	vo.setBindName(null);
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, vo);
        for(BindVO bind: list) {
        	removeChildTreeShareBind(bind, query);
        	removeShare(bind);
        }	
    } 
    
    public void removeChildTreeShareBind(BindVO bindVO, String query) throws Exception {
    	bindVO.setIsSelectType(BindVO.TREE_CHILD);
    	bindVO.setBindType("SIMPLE");
    	bindVO.setLangType(AppConfig.getCurrentLangType());

    	bindVO.setUnitName(null); //자식 검색을 위해 null, 입력 값을 가지고 있으면 검색이 안됨
    	bindVO.setBindName(null);
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, bindVO);
        
    	for(BindVO bind: list) {
    		removeChildTreeShareBind(bind, query);
    		removeShare(bind);
        }
    }

    public BatchVO getBindSearchYear(String compId) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put(COMP_ID, compId);
		BatchVO resultVO;
		resultVO = (BatchVO) this.commonDao.get("bind.search.year", param);
		if(resultVO==null){ //연도를 못 가져 올때 올해 연도를 셋업함
			resultVO = new BatchVO();
			resultVO.setYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
		}
		
		return resultVO;
    }


    public int bindOrder(String compId, String deptId, String bindId, int ordered) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put(COMP_ID, compId);
		param.put(DEPT_ID, deptId);
		param.put(BIND_ID, bindId);
		param.put(ORDERED, String.valueOf(ordered));
		return this.commonDao.modify("bind.update.ordered", param);
    }


    public BindVO getBindByUnitId(String compId, String deptId, String unitId, String period) throws Exception {
	
		Map<String, String> param = new HashMap<String, String>();
		param.put(COMP_ID, compId);
		param.put(DEPT_ID, deptId);
		param.put(UNIT_ID, unitId);
		param.put(CREATE_YEAR, period);
	
		return (BindVO) this.commonDao.get("bind.search.unit.bind", param);
    }


    public boolean exist(String compId, String deptId, String bindName, String createYear, String bindId) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put(COMP_ID, compId);
		param.put(DEPT_ID, deptId);
		param.put(BIND_ID, bindId);
		param.put(BIND_NAME, bindName);
		param.put(CREATE_YEAR, createYear);
		BindVO vo = (BindVO) this.commonDao.get("bind.exist", param);
		if (vo == null) {
		    return false;
		} else {
		    return true;
		}
    }
    
    public boolean existShare(String compId, String deptId, String bindName, String createYear, String bindId) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put(COMP_ID, compId);
		param.put(DEPT_ID, deptId);
		param.put(BIND_ID, bindId);
		param.put(BIND_NAME, bindName);
		param.put(CREATE_YEAR, createYear);
		BindVO vo = (BindVO) this.commonDao.get("bind.existShare", param);
		if (vo == null) {
		    return false;
		} else {
		    return true;
		}
    }

    // 다국어 추가
    public boolean existResource(String compId, String deptId, String bindName, String createYear, String bindId, String langType) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put(COMP_ID, compId);
		param.put(DEPT_ID, deptId);
		param.put(BIND_ID, bindId);
		param.put(BIND_NAME, bindName);
		param.put(CREATE_YEAR, createYear);
		param.put("langType", langType);
		
		BindVO vo = (BindVO) this.commonDao.get("bind.existResource", param);
		if (vo == null) {
		    return false;
		} else {
		    return true;
		}
    }

    @SuppressWarnings("unchecked")
    public List<BindVO> getBindShareList(String compId, String bindId) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put(COMP_ID, compId);
		param.put(BIND_ID, bindId);
		return this.commonDao.getList("bind.share.bindlist", param);
    }


    @SuppressWarnings("unchecked")
    public List<BindBatchVO> getBindBatchTargetList(Map<String, String> param) throws Exception {
    	return this.commonDao.getList("bind.batch.target.list", param);
    }


    public int bindBatchCreate(Map<String, String> param) throws Exception {
    	return this.commonDao.insert("bind.batch.create", param);
    }


    public boolean bindBatchExist(Map<String, String> param) throws Exception {
		BindBatchVO vo = (BindBatchVO) this.commonDao.get("bind.batch.exist", param);
		if (vo == null) {
		    return false;
		} else {
		    return true;
		}
    }


    public int bindBatchRemove(Map<String, String> param) throws Exception {
    	return this.commonDao.insert("bind.batch.remove", param);
    }


    public BindBatchVO getBindBatchTarget(String compId, String unitId, String displayName) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put(COMP_ID, compId);
		param.put(UNIT_ID, unitId);
		param.put(DISPLAY_NAME, displayName);
		return (BindBatchVO) this.commonDao.get("bind.batch.target", param);

    }


    @SuppressWarnings("unchecked")
    public List<BindVO> getBindToPrefix(String compId, String deptId, String prefix, String period) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put(COMP_ID, compId);
		param.put(DEPT_ID, deptId);
		param.put(PREFIX, prefix);
		param.put(CREATE_YEAR, period);
		return this.commonDao.getList("bind.select.prefix", param);
    }
    
    /**
     * 
     * <pre> 
     *  편철함 상태에 따른 편철을 가져옴
     * </pre>
     * @param compId
     * @param deptId
     * @param prefix
     * @param period
     * @param sendType
     * @return
     * @throws Exception
     * @see  
     *
     */
    @SuppressWarnings("unchecked")
    public List<BindVO> getBindToPrefix(String compId, String deptId, String prefix, String period, String sendType) throws Exception {
		Map<String, String> param = new HashMap<String, String>();
		param.put(COMP_ID, compId);
		param.put(DEPT_ID, deptId);
		param.put(PREFIX, prefix);
		param.put(CREATE_YEAR, period);
		param.put(SEND_TYPE,sendType);
		return this.commonDao.getList("bind.select.prefixSendType", param);
	    }
    
   
    public BindVO selectBind(BindVO bindVO) throws Exception {
    	return (BindVO) this.commonDao.get("bind.select.selectBind", bindVO);	
    }
    
    public String getTransposeTreeList(BindVO bindVO, List<BindVO> resultList) throws Exception{
    	String result = BindVO.SUCCESS;
    	if(resultList==null)return result;
    	
    	if("DST003".equals(bindVO.getSendType()))
			return BindVO.TRANSPOSE_BIND_ERROR_DST3;
    	if("DST004".equals(bindVO.getSendType()))
			return BindVO.TRANSPOSE_BIND_ERROR_DST4;
		if("Y".equals(bindVO.getBinding())==false)
			return BindVO.TRANSPOSE_BIND_ERROR_BINDING;
		if(bindVO.getShareCount()>0)
			return BindVO.TRANSPOSE_BIND_ERROR_SHARED;
		
		int bindCount = listEtcService.getAppIngBindCount(bindVO.getCompId(), bindVO.getBindId());
		if(bindCount>0)
			return BindVO.TRANSPOSE_BIND_ERROR_DOC_PROCESS;
		String nextBindId = getNextBindId();
		bindVO.setNextBindId(nextBindId);
     	resultList.add(bindVO);
       	String query = "bind.listTreebind";
    	bindVO.setUnitName(null); //자식 검색을 위해 null, 입력 값을 가지고 있으면 검색이 안됨
    	bindVO.setBindName(null);
    	bindVO.setIsSelectType(BindVO.TREE_CHILD);
    	bindVO.setBindType("SIMPLE");
    	bindVO.setLangType(AppConfig.getCurrentLangType());
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, bindVO);

    	
        for(BindVO bind: list) {
        	result = getTransposeChildTreeList(bind, resultList, nextBindId, query);
        	if(!BindVO.SUCCESS.equals(result))
    			return result;
        }

        return result;
    }
      
    public String getTransposeChildTreeList(BindVO bindVO, List<BindVO> resultList, String parentBindId, String query) throws Exception {
    	String result = BindVO.SUCCESS;    	
    	if("DST003".equals(bindVO.getSendType()))
			return BindVO.TRANSPOSE_BIND_ERROR_DST3;
    	if("DST004".equals(bindVO.getSendType()))
			return BindVO.TRANSPOSE_BIND_ERROR_DST4;
		if("Y".equals(bindVO.getBinding())==false)
			return BindVO.TRANSPOSE_BIND_ERROR_BINDING;
		if(bindVO.getShareCount()>0)
			return BindVO.TRANSPOSE_BIND_ERROR_SHARED;
		
		int bindCount = listEtcService.getAppIngBindCount(bindVO.getCompId(), bindVO.getBindId());
		if(bindCount>0)
			return BindVO.TRANSPOSE_BIND_ERROR_DOC_PROCESS;
		
		String nextBindId = getNextBindId();
		bindVO.setNextBindId(nextBindId);
		bindVO.setParentBindId(parentBindId);
    	resultList.add(bindVO); 
    	bindVO.setUnitName(null); //자식 검색을 위해 null, 입력 값을 가지고 있으면 검색이 안됨
    	bindVO.setBindName(null);
    	bindVO.setIsSelectType(BindVO.TREE_CHILD);
    	bindVO.setBindType("SIMPLE");
    	bindVO.setLangType(AppConfig.getCurrentLangType());
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, bindVO);

    	for(BindVO bind: list) {
        	result = getTransposeChildTreeList(bind, resultList, nextBindId, query);
        	if(!BindVO.SUCCESS.equals(result))
    			return result;;
        }
    	return result;
    }
    
	/* (non-Javadoc)
	 * @see com.sds.acube.app.bind.service.BindService#getBindUnitList(com.sds.acube.app.bind.vo.BindUnitVO)
	 */
	@Override
	public List<BindUnitVO> getBindUnitList(BindUnitVO bindUnitVO)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sds.acube.app.bind.service.BindService#getBindUnitListResource(com.sds.acube.app.bind.vo.BindUnitVO)
	 */
	@Override
	public List<BindUnitVO> getBindUnitListResource(BindUnitVO bindUnitVO)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.sds.acube.app.bind.service.BindService#getSelectTreeBindShare(com.sds.acube.app.bind.vo.BindVO)
	 */
	@Override
	public BindVO[] getSelectTreeBindShare(BindVO bindVO) throws Exception {
    	
		bindVO.setIsSelectType(BindVO.TREE_CHILD);
		bindVO.setBindId(BindVO.ROOT);
		
		String query = "bind.listTreebindShare";
    	ArrayList<BindVO> list = (ArrayList<BindVO>) this.commonDao.getList(query, bindVO);
    	
    	ArrayList<BindVO> resultList = new ArrayList<BindVO>();
    	
        for(BindVO bind: list) {
        	if(bindVO.getBindType()!=null&&bindVO.getBindType().equals("DOC_BOARD")){
        		bind.setDeptId(bindVO.getDeptId());
        		bind.setBindType(bindVO.getBindType());
        	}
        	getSelectChildTreeBind(bind, resultList, query);
        }
        
        
        BindVO[] resultArray =  new BindVO[resultList.size()];
        resultList.toArray(resultArray);

        return resultArray;
	}
	
	
	public BindVO checkroot(BindVO bindVO) throws Exception {
		return (BindVO) this.commonDao.get("bind.board.checkroot", bindVO);	
	}

}
