package com.sds.acube.app.list.service.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.anyframe.pagination.Page;


import com.sds.acube.app.approval.vo.AppDocVO;
import com.sds.acube.app.common.dao.ICommonDAO;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.vo.AuditDeptVO;
import com.sds.acube.app.list.service.IListCompleteService;
import com.sds.acube.app.list.vo.SearchVO;

/**
 * Class Name  : ListCompleteService.java <br> Description : (완료폴더)기안문서함, 결재완료함, 접수완료함, 공람문서함, 후열문서함, 검사부열람함, 임원문서함, 임원결재함, 주관부서문서함, 본부문서함, 회사문서함, 여신문서함, 거래처완료문서  관련  서비스  <br> Modification Information <br> <br> 수 정  일 : <br> 수 정  자 : <br> 수정내용 : <br>
 * @author   김경훈 
 * @since  2011. 4. 14.
 * @version  1.0 
 * @see  com.sds.acube.app.list.service.impl.ListCompleteService.java
 */

@Service("listCompleteService")
@Transactional(rollbackFor = { Exception.class }, propagation = Propagation.REQUIRED)
public class ListCompleteService extends BaseService implements IListCompleteService{
    
    private static final long serialVersionUID = 1L;
    
    /**
	 */
    @Inject
    @Named("commonDAO")
    private ICommonDAO commonDAO;
    
    /**
	 */
    @Inject
    @Named("envOptionAPIService")
    private IEnvOptionAPIService envOptionAPIService;
   
    
    public Page listDraft(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listDraft", searchVO, pageIndex);
	    
    }
    
    public Page listDraft(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listDraft", searchVO, pageIndex, pageSize);
	    
    }

    public Page listApprovalComplete(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listApprovalComplete", searchVO, pageIndex);
	    
    }
    
    @SuppressWarnings("unchecked")
    public List<AppDocVO> listApprovalComplete(SearchVO searchVO) throws Exception {
	    return this.commonDAO.getList("list.listApprovalCompleteToList", searchVO);
	    
    }
    
    public Page listApprovalComplete(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listApprovalComplete", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listRepresentativeComplete(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listRepresentativeComplete", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listReceiveComplete(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listReceiveComplete", searchVO, pageIndex);
	    
    }
    
    public Page listReceiveComplete(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listReceiveComplete", searchVO, pageIndex, pageSize);
	    
    }
    
    @SuppressWarnings("unchecked")
    public List<AppDocVO> listReceiveComplete(SearchVO searchVO) throws Exception {
	    return this.commonDAO.getList("list.listReceiveCompleteToList", searchVO);
	    
    }
    
    public Page listDisplay(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listDisplay", searchVO, pageIndex);
	    
    }
    
    public Page listDisplay(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listDisplay", searchVO, pageIndex, pageSize);
	    
    }
    
    @SuppressWarnings("unchecked")
    public List<AppDocVO> listDisplay(SearchVO searchVO) throws Exception {
	    return this.commonDAO.getList("list.listDisplayToList", searchVO);
	    
    }
    
    @SuppressWarnings("unchecked")
    public int listDisplayCount(SearchVO searchVO) throws Exception {
	Map map = (Map) this.commonDAO.get("list.displayDocCount", searchVO);   
	Integer num = new Integer("" + map.get("CNT"));
	return num.intValue();
	    
    }  
    
    public Page listRear(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listRear", searchVO, pageIndex);
	    
    }
    
    public Page listRear(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listRear", searchVO, pageIndex, pageSize);
	    
    }
    
    @SuppressWarnings("unchecked")
    public List<AppDocVO> listRear(SearchVO searchVO) throws Exception {
	    return this.commonDAO.getList("list.listRearToList", searchVO);
	    
    }
    
    @SuppressWarnings("unchecked")
    public int listRearCount(SearchVO searchVO) throws Exception {
	Map map = (Map) this.commonDAO.get("list.rearCount", searchVO);   
	Integer num = new Integer("" + map.get("CNT"));
	return num.intValue();
	    
    }  
    
    public Page listExamReading(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listExamReading", searchVO, pageIndex);
	    
    }
    
    @SuppressWarnings("unchecked")
    public List<AuditDeptVO> getListAuditDept(AuditDeptVO auditDeptVO) throws Exception {
	return this.commonDAO.getList("list.listAuditDept", auditDeptVO);
    }
    
    @SuppressWarnings("unchecked")
    public int getAuditDeptCnt(AuditDeptVO auditDeptVO) throws Exception {
	Map map = (Map) this.commonDAO.get("list.auditDeptCount", auditDeptVO);   
	Integer num = new Integer("" + map.get("CNT"));
	return num.intValue();
	    
    }  
    
    public Page listExamReading(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listExamReading", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listOfficer(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listOfficer", searchVO, pageIndex);
	    
    }
    
    public Page listOfficer(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listOfficer", searchVO, pageIndex, pageSize);
	    
    }
    
    @SuppressWarnings("unchecked")
    public List<AppDocVO> listOfficer(SearchVO searchVO) throws Exception {
	    return this.commonDAO.getList("list.listOfficerToList", searchVO);
	    
    }
    
    public Page listOfficerApproval(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listOfficerApproval", searchVO, pageIndex);
	    
    }
    
    public Page listOfficerApproval(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listOfficerApproval", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listConductTeam(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listConductTeam", searchVO, pageIndex);
	    
    }
    
    public Page listConductTeam(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listConductTeam", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page listHQ(SearchVO searchVO, int pageIndex) throws Exception {
	String searchQueryId 	= "list.listHQ";
	String compId 		= searchVO.getCompId();

	// 열람범위 확인 후 쿼리 변경	
	String opt361 = appCode.getProperty("OPT361", "OPT361", "OPT"); // 접수문서 열람범위 - 1:사용자가 열람범위유형 지정, 2:열람범위를 부서로 고정
	opt361 = envOptionAPIService.selectOptionValue(compId, opt361);

	if("2".equals(opt361) ){
	    searchQueryId = "list.listHQOnlyApp";
	}
	
	return this.commonDAO.getPagingList(searchQueryId, searchVO, pageIndex);

    }
    
    public Page listHQ(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	String searchQueryId 	= "list.listHQ";
	String compId 		= searchVO.getCompId();

	// 열람범위 확인 후 쿼리 변경	
	String opt361 = appCode.getProperty("OPT361", "OPT361", "OPT"); // 접수문서 열람범위 - 1:사용자가 열람범위유형 지정, 2:열람범위를 부서로 고정
	opt361 = envOptionAPIService.selectOptionValue(compId, opt361);

	if("2".equals(opt361) ){
	    searchQueryId = "list.listHQOnlyApp";
	}
	
	return this.commonDAO.getPagingList(searchQueryId, searchVO, pageIndex, pageSize);

    }

    
   
    //기관문서함
    public Page listInstitution(SearchVO searchVO, int pageIndex) throws Exception {
	
	String searchQueryId 	= "list.listInstitution";
	String compId 		= searchVO.getCompId();
	
	// 열람범위 확인 후 쿼리 변경	
	String opt361 = appCode.getProperty("OPT361", "OPT361", "OPT"); // 접수문서 열람범위 - 1:사용자가 열람범위유형 지정, 2:열람범위를 부서로 고정
	opt361 = envOptionAPIService.selectOptionValue(compId, opt361);
	
	if("2".equals(opt361) ){
	    searchQueryId = "list.listInstitutionOnlyApp";
	}

	return this.commonDAO.getPagingList(searchQueryId, searchVO, pageIndex);
	    
    }
    
    public Page listInstitution(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	String searchQueryId 	= "list.listInstitution";
	String compId 		= searchVO.getCompId();

	// 열람범위 확인 후 쿼리 변경	
	String opt361 = appCode.getProperty("OPT361", "OPT361", "OPT"); // 접수문서 열람범위 - 1:사용자가 열람범위유형 지정, 2:열람범위를 부서로 고정
	opt361 = envOptionAPIService.selectOptionValue(compId, opt361);

	if("2".equals(opt361) ){
	    searchQueryId = "list.listInstitutionOnlyApp";
	}

	return this.commonDAO.getPagingList(searchQueryId, searchVO, pageIndex, pageSize);

    }
    
    @SuppressWarnings("unchecked")
    public List<AppDocVO> listInstitution(SearchVO searchVO) throws Exception {
	String searchQueryId 	= "list.listInstitutionToList";
	String compId 		= searchVO.getCompId();

	// 열람범위 확인 후 쿼리 변경	
	String opt361 = appCode.getProperty("OPT361", "OPT361", "OPT"); // 접수문서 열람범위 - 1:사용자가 열람범위유형 지정, 2:열람범위를 부서로 고정
	opt361 = envOptionAPIService.selectOptionValue(compId, opt361);

	if("2".equals(opt361) ){
	    searchQueryId = "list.listInstitutionToListOnlyApp";
	}
	
	return this.commonDAO.getList(searchQueryId, searchVO);
	    
    }    
    
    
    public Page listCompany(SearchVO searchVO, int pageIndex) throws Exception {
	
	String searchQueryId 	= "list.listCompany";
	String compId 		= searchVO.getCompId();
	
	// 열람범위 확인 후 쿼리 변경	
	String opt361 = appCode.getProperty("OPT361", "OPT361", "OPT"); // 접수문서 열람범위 - 1:사용자가 열람범위유형 지정, 2:열람범위를 부서로 고정
	opt361 = envOptionAPIService.selectOptionValue(compId, opt361);
	
	if("2".equals(opt361) ){
	    searchQueryId = "list.listCompanyOnlyApp";
	}

	return this.commonDAO.getPagingList(searchQueryId, searchVO, pageIndex);
	    
    }
    
    public Page listCompany(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	String searchQueryId 	= "list.listCompany";
	String compId 		= searchVO.getCompId();

	// 열람범위 확인 후 쿼리 변경	
	String opt361 = appCode.getProperty("OPT361", "OPT361", "OPT"); // 접수문서 열람범위 - 1:사용자가 열람범위유형 지정, 2:열람범위를 부서로 고정
	opt361 = envOptionAPIService.selectOptionValue(compId, opt361);

	if("2".equals(opt361) ){
	    searchQueryId = "list.listCompanyOnlyApp";
	}

	return this.commonDAO.getPagingList(searchQueryId, searchVO, pageIndex, pageSize);

    }
    
    @SuppressWarnings("unchecked")
    public List<AppDocVO> listCompany(SearchVO searchVO) throws Exception {
	String searchQueryId 	= "list.listCompanyToList";
	String compId 		= searchVO.getCompId();

	// 열람범위 확인 후 쿼리 변경	
	String opt361 = appCode.getProperty("OPT361", "OPT361", "OPT"); // 접수문서 열람범위 - 1:사용자가 열람범위유형 지정, 2:열람범위를 부서로 고정
	opt361 = envOptionAPIService.selectOptionValue(compId, opt361);

	if("2".equals(opt361) ){
	    searchQueryId = "list.listCompanyToListOnlyApp";
	}
	
	return this.commonDAO.getList(searchQueryId, searchVO);
	    
    }
    
    public Page LoanBusiness(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listLoanBusiness", searchVO, pageIndex);
	    
    }
    
    public Page LoanBusiness(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	    return this.commonDAO.getPagingList("list.listLoanBusiness", searchVO, pageIndex, pageSize);
	    
    }
    
    public Page ListCustomerComplete(SearchVO searchVO, int pageIndex) throws Exception {
	    return this.commonDAO.getPagingList("list.listCustomerComplete", searchVO, pageIndex);
	    
    }
    
    public Page listDeptAssistant(SearchVO searchVO, int pageIndex) throws Exception {
	return this.commonDAO.getPagingList("list.listDeptAssistant", searchVO, pageIndex);

    }

    public Page listDeptAssistant(SearchVO searchVO, int pageIndex, int pageSize) throws Exception {
	return this.commonDAO.getPagingList("list.listDeptAssistant", searchVO, pageIndex, pageSize);

    }
}
