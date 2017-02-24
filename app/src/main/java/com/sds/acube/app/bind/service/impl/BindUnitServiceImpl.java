package com.sds.acube.app.bind.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.anyframe.pagination.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sds.acube.app.bind.service.BindUnitService;
import com.sds.acube.app.bind.vo.BindUnitVO;
import com.sds.acube.app.bind.vo.BindVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.Tree;
import com.sds.acube.app.list.vo.SearchVO;


/**
 * Class Name : BindServiceImpl.java <br> Description : 설명 <br> Modification Information <br> <br> 수 정 일 : 2011. 3. 29. <br> 수 정 자 : I-ON <br> 수정내용 : <br>
 * @author  I-ON
 * @since  2011. 3. 29.
 * @version  1.0
 * @see  com.sds.acube.app.bind.service.impl.BindUnitCategoryService.java
 */

@Service("bindUnitService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class BindUnitServiceImpl extends BaseService implements BindUnitService {

    private static final long serialVersionUID = 1211245176110782181L;

    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDao;


    @SuppressWarnings("unchecked")
    public List<BindUnitVO> getTree(Map<String, String> param) throws Exception {
    	return this.commonDao.getList("bind.unit.tree", param);
    }


    @SuppressWarnings("unchecked")
    public List<BindUnitVO> getSelectTree(Map<String, String> param) throws Exception {
    	return this.commonDao.getList("bind.unit.selectTree", param);
    }
    
    public Page listBindUnit(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDao.getPagingList("list.listBindUnit", searchVO, pageIndex);
    }
    
    public Page listBindUnit(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDao.getPagingList("list.listBindUnit", searchVO, pageIndex, pageSize);
    }

    public void getSelectChildTreeBindUnit(BindUnitVO bindUnitVO, ArrayList<BindUnitVO> resultList, String query) throws Exception {
    	bindUnitVO.setIsSelectType(BindVO.TREE_CHILD);
    	bindUnitVO.setLangType(AppConfig.getCurrentLangType());
    	
    	ArrayList<BindUnitVO> list = (ArrayList<BindUnitVO>) this.commonDao.getList(query, bindUnitVO);

        // 하위장소여부 setting
    	String isChildUnit = "";
        if(list.size() != 0) {
        	isChildUnit = BindUnitVO.CHILD_STATE_YES;
        } else {
        	isChildUnit = BindUnitVO.CHILD_STATE_NO;
        }

        bindUnitVO.setIsChildUnit(isChildUnit);
        resultList.add(bindUnitVO);
        
    	for(BindUnitVO bindUnit: list) {
    		getSelectChildTreeBindUnit(bindUnit, resultList, query);
        }
    }
    
    public BindUnitVO[] listTreeBindUnit(BindUnitVO bindUnitVO) throws Exception {
    	bindUnitVO.setIsSelectType(BindUnitVO.TREE_CHILD);
    	bindUnitVO.setUnitId(BindVO.ROOT);
		String query = "list.listTreeBindUnit";
    	ArrayList<BindUnitVO> list = (ArrayList<BindUnitVO>) this.commonDao.getList(query, bindUnitVO);
    	
    	ArrayList<BindUnitVO> resultList = new ArrayList<BindUnitVO>();
    	
        for(BindUnitVO bindUnit: list) {
        	getSelectChildTreeBindUnit(bindUnit, resultList, query);
        }
        
        BindUnitVO[] resultArray =  new BindUnitVO[resultList.size()];
        resultList.toArray(resultArray);

        return resultArray;
    }
    
    public BindUnitVO[] listTreeShareBindUnit(BindUnitVO bindUnitVO) throws Exception {
    	bindUnitVO.setIsSelectType(BindUnitVO.TREE_CHILD);
    	bindUnitVO.setUnitId(BindVO.ROOT);
		String query = "list.listTreeShareBindUnit";
    	ArrayList<BindUnitVO> list = (ArrayList<BindUnitVO>) this.commonDao.getList(query, bindUnitVO);
    	
    	ArrayList<BindUnitVO> resultList = new ArrayList<BindUnitVO>();
    	
        for(BindUnitVO bindUnit: list) {
        	getSelectChildTreeBindUnit(bindUnit, resultList, query);
        }
        
        BindUnitVO[] resultArray =  new BindUnitVO[resultList.size()];
        resultList.toArray(resultArray);

        return resultArray;
    }
    
    
    // 다국어 추가
    public Page listBindUnitResource(SearchVO searchVO, int pageIndex) throws Exception {
    	Page page = this.commonDao.getPagingList("list.listBindUnitResource", searchVO, pageIndex);
       	List<BindUnitVO> list = (List<BindUnitVO>) page.getList();

    	return page;
    }
    
    // 다국어 추가
    public Page listBindUnitResource(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
    	Page page = this.commonDao.getPagingList("list.listBindUnitResource", searchVO, pageIndex, pageSize);
       	List<BindUnitVO> list = (List<BindUnitVO>) page.getList();

    	return page;
    }

    // 다국어 추가
    public BindUnitVO[] listTreeBindUnitResource(BindUnitVO bindUnitVO) throws Exception {
		String query = "list.listTreeBindUnitResource";
    	ArrayList<BindUnitVO> list = (ArrayList<BindUnitVO>) this.commonDao.getList(query, bindUnitVO);
    	
    	ArrayList<BindUnitVO> resultList = new ArrayList<BindUnitVO>();
    	
        for(BindUnitVO bindUnit: list) {
        	getSelectChildTreeBindUnit(bindUnit, resultList, query);
        }
        
        BindUnitVO[] resultArray =  new BindUnitVO[resultList.size()];
        resultList.toArray(resultArray);

        return resultArray;
    }

    public BindUnitVO get(String bindUnitId, BindUnitVO bindUnitVO) throws Exception {
    	return (BindUnitVO) this.commonDao.get("bind.unit.select", bindUnitVO);
    }
    
    // 다국어 추가
    public BindUnitVO getResource(String bindUnitId, BindUnitVO bindUnitVO) throws Exception {
    	return (BindUnitVO) this.commonDao.get("bind.unit.selectResource", bindUnitVO);
    }
    
    public BindUnitVO simpleGet(BindUnitVO bindUnitVO) throws Exception {
    	return (BindUnitVO) this.commonDao.get("bind.unit.simple.select", bindUnitVO);
    }
    
    // 다국어 추가
    public BindUnitVO simpleGetResource(BindUnitVO bindUnitVO) throws Exception {
    	return (BindUnitVO) this.commonDao.get("bind.unit.simple.selectResource", bindUnitVO);
    }

    public int insert(BindUnitVO bindUnitVO) throws Exception {
    	return this.commonDao.insert("bind.unit.insert", bindUnitVO);
    }
    
    public int update(BindUnitVO bindUnitVO) throws Exception {
    	return this.commonDao.modify("bind.unit.update", bindUnitVO);
    }
    
    public int delete(BindUnitVO bindUnitVO) throws Exception {
    	return this.commonDao.delete("bind.unit.delete", bindUnitVO);
    }
    
    public int rename(BindUnitVO bindUnitVO) throws Exception {
    	return this.commonDao.modify("bind.unit.rename", bindUnitVO);
    }
    
    public int simpleInsert(BindUnitVO bindUnitVO) throws Exception {
    	return this.commonDao.insert("bind.unit.simple.insert", bindUnitVO);
    }
    
    public int simpleUpdate(BindUnitVO bindUnitVO) throws Exception {
    	return this.commonDao.modify("bind.unit.simple.update", bindUnitVO);
    }
    
    public int simpleDelete(BindUnitVO bindUnitVO) throws Exception {
    	return this.commonDao.delete("bind.unit.simple.delete", bindUnitVO);
    }
    
    public int simpleRename(BindUnitVO bindUnitVO) throws Exception {
    	return this.commonDao.modify("bind.unit.simple.rename", bindUnitVO);
    }


    @SuppressWarnings("unchecked")
    public List<BindVO> getBindUseList(BindUnitVO bindUnitVO) throws Exception {
    	return this.commonDao.getList("bind.unit.useRows", bindUnitVO);
    }
}
