package com.sds.acube.app.common.dao.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Repository;

import org.anyframe.pagination.Page;
import org.anyframe.query.dao.AbstractDao;
import org.anyframe.query.QueryService;
import org.anyframe.query.QueryServiceException;

import com.sds.acube.app.common.dao.ICommonDB2DAO;

/**
 * 공통 Dao Query Id와 Object를 이용해 Insert, Update, Delete Page조회,
 * List조회, 단건 조회를 수행한다.
 * 
 * @author F/W G Hong Junghwan
 * 
 */

@Repository("commonDB2DAO")
public class CommonDB2DAO extends AbstractDao implements ICommonDB2DAO {

    public CommonDB2DAO() {
	this.setCreateId("");
	this.setFindByPkPostfix("");
	this.setFindListPostfix("");
	this.setFindPrefix("");
	this.setRemoveId("");
	this.setUpdateId("");
    }
    
    @Inject
    public void setQueryServiceDB2(QueryService queryServiceDB2) {
	super.setQueryService(queryServiceDB2);
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#insert(java.lang.String, java.lang.Object)
     */
    public int insert(String queryId, Object insertObject)
    throws QueryServiceException {
	return this.create(queryId, insertObject);
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#insert(java.lang.String, java.util.List)
     */
    public int insertList(String queryId, List<Object> insertList)
    throws QueryServiceException {
	for (Object insert : insertList) {
	    this.insert(queryId, insert);
	}
	return insertList.size();
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#insert(java.lang.String, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public int insertMap(String queryId, Map insertMap)
    throws QueryServiceException {
	return this.create(queryId, insertMap);
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#modify(java.lang.String, java.lang.Object)
     */
    public int modify(String queryId, Object updateObject)
    throws QueryServiceException {
	return this.update(queryId, updateObject);
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#modify(java.lang.String, java.util.List)
     */
    public int modifyList(String queryId, List<Object> updateList)
    throws QueryServiceException {
	for (Object update : updateList) {
	    this.update(queryId, update);
	}
	return updateList.size();
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#modify(java.lang.String, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public int modifyMap(String queryId, Map updateMap)
    throws QueryServiceException {
	return this.update(queryId, updateMap);
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#delete(java.lang.String, java.lang.Object)
     */
    public int delete(String queryId, Object deleteObject)
    throws QueryServiceException {
	return this.remove(queryId, deleteObject);
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#delete(java.lang.String, java.util.List)
     */
    @SuppressWarnings("unchecked")
    public int deleteList(String queryId, List deleteList)
    throws QueryServiceException {
	for (Object delete : deleteList) {
	    this.remove(queryId, delete);
	}
	return deleteList.size();
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#delete(java.lang.String, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public int deleteMap(String queryId, Map deleteMap)
    throws QueryServiceException {
	return this.remove(queryId, deleteMap);
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#getList(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List getList(String queryId) throws QueryServiceException {
	return (List) this.findList(queryId, new Object[] {});
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#getList(java.lang.String, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public List getList(String queryId, Object searchVO)
    throws QueryServiceException {
	return (List) this.findList(queryId, searchVO);
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#getList(java.lang.String, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public List getListMap(String queryId, Map searchMap)
    throws QueryServiceException {
	return (List) this.findList(queryId, searchMap);
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#getPagingList(java.lang.String, java.lang.Object, int)
     */
    public Page getPagingList(String queryId, Object searchVO, int pageIndex)
    throws QueryServiceException {
	return getPagingList(queryId, searchVO, pageIndex, 10);
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#getPagingList(java.lang.String, java.lang.Object, int, int)
     */
    public Page getPagingList(String queryId, Object searchVO, int pageIndex,
	    int pageSize) throws QueryServiceException {
	return getPagingList(queryId, searchVO, pageIndex, pageSize, 10);
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#getPagingList(java.lang.String, java.lang.Object, int, int, int)
     */
    public Page getPagingList(String queryId, Object searchVO, int pageIndex,
	    int pageSize, int pageUnit) throws QueryServiceException {
	return this.findListWithPaging(queryId, searchVO, pageIndex, pageSize,
		pageUnit);
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#get(java.lang.String, java.lang.Object)
     */
    public Object get(String queryId, Object searchVO)
    throws QueryServiceException {
	return  this.findByPk(queryId, searchVO);
    }

    /* (non-Javadoc)
     * @see com.sds.acube.app.common.dao.impl.ICommonDAO#get(java.lang.String, java.util.Map)
     */
    @SuppressWarnings("unchecked")
    public Object getMap(String queryId, Map searchMap)
    throws QueryServiceException {
	return this.findByPk(queryId, searchMap);
    }
}
