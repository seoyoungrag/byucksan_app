package com.sds.acube.app.env.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import com.sds.acube.app.common.service.IOrgService;
import com.sds.acube.app.common.service.impl.BaseService;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.DateUtil;
import com.sds.acube.app.common.vo.OrganizationVO;
import com.sds.acube.app.env.service.IEnvDocNumRuleService;
import com.sds.acube.app.env.service.IEnvOptionAPIService;
import com.sds.acube.app.env.util.DocNumRuleManager;
import com.sds.acube.app.env.vo.OptionVO;

/**
 * Class Name  : EnvDocNumRuleService.java <br> Description : 문서번호 생성규칙에 따라 문서번호를 설정  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 2. <br> 수 정  자 : jd.park  <br> 수정내용 :  <br>
 * @author   jd.park 
 * @since  2012. 5. 2.
 * @version  1.0 
 * @see  com.sds.acube.app.env.service.impl.EnvDocNumRuleService.java
 */

@SuppressWarnings("serial")
@Service("envDocNumRuleService")
public class EnvDocNumRuleService extends BaseService implements
		IEnvDocNumRuleService {

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
	 
	/* (non-Javadoc)
	 * @see com.sds.acube.app.env.service.IEnvDocNumRuleService#getDocNum(java.lang.String, java.lang.String)
	 * 생성된 규칙에 맞는 문서번호를 구함
	 */
	public String getDocNum(String deptId, String compId, String classNumber) throws Exception {
		String resultDocNum = "";	//결과값
		String docNumRule 	= "";	//문서번호 생성규칙
		String docNumRuleYn = "";	//생셩규칙 사용여부
		String deptName 	= ""; 	//부서명
		String deptAcronym 	= ""; 	//부서약어
		String session 		= ""; 	//회기/년도		
		
		DocNumRuleManager docNumRuleManager = new DocNumRuleManager();	//문서번호 생성 관리 호출
		
		//옵션 사용여부 가져오기

		docNumRuleYn = envOptionAPIService.selectOptionValue(compId, "OPT418");
		
		//문서번호 생성규칙 가져오기
		if(docNumRuleYn.equals("Y")){
	 		HashMap<String, OptionVO> OPTG400 = envOptionAPIService.selectOptionGroupMap(compId, "OPTG400");
			if(OPTG400 !=null){
				docNumRule =((OptionVO)OPTG400.get("OPT418")).getOptionValue();			
			}			
		}
		
		//부서정보 가져오기
		OrganizationVO org = orgService.selectOrganization(deptId);
		
		//기간유형(Y:연도 , P:회기) 가져오기
		String periodType = AppConfig.getProperty("periodType", "Y", "etc");
		
		//생성규칙 :를 구분자로  list 생성
		List<String> list = docNumRuleManager.getListFromLine(docNumRule);
		
		//항목에 해당하는 값 구하기
		if(list !=null && list.size()>0){
			for(String sType : list){
				//부서명
				if(sType.equals("DEPTNAME")){
					//부서명 가져오기
					if(org !=null){
						deptName = CommonUtil.nullTrim(org.getOrgName());								
					}
					resultDocNum +=deptName;
				//부서약어
				}else if(sType.equals("DEPTACRO")){
					//부서약어 가져오기
					if(org !=null){								
						deptAcronym = CommonUtil.nullTrim(org.getOrgAbbrName());			
					}
					resultDocNum +=deptAcronym;
				//년도/회기
				}else if(sType.equals("SESSION")){					
					// 회기 가져오기
					if(periodType.equals("P")){
						session = envOptionAPIService.getCurrentPeriodStr(compId);
					// 연도 구하기
					}else{
						session = DateUtil.getCurrentYear();			
					}
					resultDocNum +=session;
				//분류번호
				}else if(sType.equals("CLASSNUM")){
					//문서분류
					if(classNumber.equals("")){
						classNumber = "[분류기호]";
					}
					resultDocNum +=classNumber;
				//-
				}else{
					resultDocNum +="-";
				}
			}
		}
		return resultDocNum.substring(0, resultDocNum.length()-1);
	}

}
