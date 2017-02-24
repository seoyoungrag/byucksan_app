/**
 * 
 */
package com.sds.acube.app.mig.service;

import java.util.List;
import java.util.Map;

import org.anyframe.pagination.Page;

import com.sds.acube.app.mig.vo.ApprInfoVO;
import com.sds.acube.app.mig.vo.ApproverInfoVO;
import com.sds.acube.app.mig.vo.AttachInfoVO;
import com.sds.acube.app.mig.vo.DeliveresInfoVO;
import com.sds.acube.app.mig.vo.DocInfoVO;
import com.sds.acube.app.mig.vo.DraftInfoVO;
import com.sds.acube.app.mig.vo.MigFileVO;
import com.sds.acube.app.mig.vo.RecipientsInfoVO;
import com.sds.acube.app.mig.vo.RegDocVO;


public interface IMigService { 
	public Page getList(RegDocVO regDocVO, int page, int pageSize) throws Exception ;
	public Page getListRecv(RegDocVO regDocVO, int nPage, int pageSize) throws Exception ;
	public Page getFileList(MigFileVO migFileVO, int page) throws Exception ;
	public RegDocVO getDoc(String docId);
	public List<MigFileVO> getMigratedFileList(Map<String, String> map) throws Exception;
	public DocInfoVO getDocInfo(Map<String, String> map) throws Exception;
	public DraftInfoVO getDraftInfo(Map<String, String> map) throws Exception;
	public List<RecipientsInfoVO> getRecipientsInfo(Map<String, String> map) throws Exception;
	public List<ApprInfoVO> getApprList(Map<String, String> map) throws Exception;
	public List<ApproverInfoVO> getApproverList(Map<String, String> map) throws Exception;
	public List<AttachInfoVO> getAttachList(Map<String, String> map) throws Exception;
	public List<DeliveresInfoVO> getDeliveresList(Map<String, String> map) throws Exception;
	public DraftInfoVO getDraftInfoRecv(Map<String, String> map) throws Exception;
	public List<RecipientsInfoVO> getRecipientsInfoRecv(Map<String, String> map) throws Exception;
	public List<DeliveresInfoVO> getDeliveresListRecv(Map<String, String> map)	throws Exception;
	public List<AttachInfoVO> getAttachListRecv(Map<String, String> map) throws Exception;
	public List<ApproverInfoVO> getApproverListRecv(Map<String, String> map) throws Exception;
	public List<ApprInfoVO> getApprListRecv(Map<String, String> map) throws Exception;
	public DocInfoVO getDocInfoRecv(Map<String, String> map) throws Exception;
	public List<MigFileVO> getCabFileList(Map<String, String> map) throws Exception;
	public Page getCabList(RegDocVO regDocVO, int page, int pageSize) throws Exception;
	public int updateBoardContent(Map<String, String> map) throws Exception;	
	public List<MigFileVO> isDuplicate(Map<String, String> map) throws Exception;	
}
