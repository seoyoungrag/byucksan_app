package com.sds.acube.app.common.dao;

import java.util.List;
import java.util.Map;

import org.anyframe.pagination.Page;
import org.anyframe.query.QueryServiceException;

public interface ICommonDB2DAO {

	/**
	 * VO 또는 Domain객체를 INSERT한다.
	 * 
	 * @param queryId
	 *            Query Mapping File에 정의된 Query ID
	 * @param insertObject
	 *            DB에 추가할 VO또는 Domain Object
	 * @return int DB에 INSERT된 Record의 수
	 * @throws QueryServiceException
	 */
	public abstract int insert(String queryId, Object insertObject)
			throws QueryServiceException;

	public abstract int insertList(String queryId, List<Object> insertList)
			throws QueryServiceException;

	@SuppressWarnings("unchecked")
	public abstract int insertMap(String queryId, Map insertMap)
			throws QueryServiceException;

	/**
	 * VO 또는 Domain객체를 UPDATE한다.
	 * 
	 * @param queryId
	 *            Query Mapping File에 정의된 Query ID
	 * @param updateObject
	 *            DB에 수정할 VO또는 Domain Object
	 * @return int DB에 UPDATE된 Record의 수
	 * @throws QueryServiceException
	 */
	public abstract int modify(String queryId, Object updateObject)
			throws QueryServiceException;

	public abstract int modifyList(String queryId, List<Object> updateList)
			throws QueryServiceException;

	@SuppressWarnings("unchecked")
	public abstract int modifyMap(String queryId, Map updateMap)
			throws QueryServiceException;

	/**
	 * VO 또는 Domain객체를 DELETE한다.
	 * 
	 * @param queryId
	 *            Query Mapping File에 정의된 Query ID
	 * @param deleteObject
	 *            DB에 수정할 VO또는 Domain Object
	 * @return int DB에 DELETE된 Record의 수
	 * @throws QueryServiceException
	 */
	public abstract int delete(String queryId, Object deleteObject)
			throws QueryServiceException;

	@SuppressWarnings("unchecked")
	public abstract int deleteList(String queryId, List deleteList)
			throws QueryServiceException;

	@SuppressWarnings("unchecked")
	public abstract int deleteMap(String queryId, Map deleteMap)
			throws QueryServiceException;

	/**
	 * List 조회
	 * 
	 * @param queryId
	 * @param searchVO
	 * @return
	 * @throws QueryServiceException
	 */
	@SuppressWarnings("unchecked")
	public abstract List getList(String queryId) throws QueryServiceException;

	@SuppressWarnings("unchecked")
	public abstract List getList(String queryId, Object searchVO)
			throws QueryServiceException;

	@SuppressWarnings("unchecked")
	public abstract List getListMap(String queryId, Map searchMap)
			throws QueryServiceException;

	/**
	 * Paging 조회
	 * 
	 * @param queryId
	 * @param searchVO
	 * @return
	 * @throws QueryServiceException
	 */
	public abstract Page getPagingList(String queryId, Object searchVO,
			int pageIndex) throws QueryServiceException;

	public abstract Page getPagingList(String queryId, Object searchVO,
			int pageIndex, int pageSize) throws QueryServiceException;

	public abstract Page getPagingList(String queryId, Object searchVO,
			int pageIndex, int pageSize, int pageUnit)
			throws QueryServiceException;

	/**
	 * 단건 조회
	 * 
	 * @param queryId
	 * @param searchVO
	 * @return
	 * @throws QueryServiceException
	 */
	public abstract Object get(String queryId, Object searchVO)
			throws QueryServiceException;

	@SuppressWarnings("unchecked")
	public abstract Object getMap(String queryId, Map searchMap)
			throws QueryServiceException;

}