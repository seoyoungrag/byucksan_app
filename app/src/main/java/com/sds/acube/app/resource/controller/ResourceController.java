package com.sds.acube.app.resource.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sds.acube.app.common.controller.BaseController;
import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.CommonUtil;
import com.sds.acube.app.common.util.GuidUtil;
import com.sds.acube.app.resource.service.IResourceService;
import com.sds.acube.app.resource.vo.ResourceVO;
import com.sds.acube.app.resource.vo.UpdateTableVO;

@SuppressWarnings("serial")
@Controller("resourceController")
@RequestMapping("/app/resource/*.do")
public class ResourceController extends BaseController {
	
	@Inject
    @Named("messageSource")
    MessageSource messageSource;
	
	@Inject
    @Named("resourceService")
    private IResourceService resourceService;
	
	/**
	 * 
	 * <pre> 
	 *  TGW_APP_OPTION 테이블를 사용하는 화면에서만 사용한다.
	 *  다국어 언어를 조회한다.
	 * </pre>
	 * @param req
	 * @return
	 * @throws Exception
	 * @see  
	 *
	 */
	@RequestMapping("/app/resource/showOptionResource.do") 
	public ModelAndView showOptionResource(HttpServletRequest req) throws Exception {
		HashMap<String, String> resourceMapData = new HashMap<String, String>();
		
		HttpSession session = req.getSession();
		String compId = (String)session.getAttribute("COMP_ID");		
		String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();	
	
		String resourceId = CommonUtil.nullTrim((String)req.getParameter("resourceId"));
		if (resourceId == null) { resourceId = ""; }
		
		// resourceDataYN=YES인경우 이미 한번 다국어 창에서 다국어를 입력한 경우.
		String resourceDataYN = (String)req.getParameter("resourceDataYN");		
		if ("YES".equals(resourceDataYN)) {
			String[] localeLanguage = AppConfig.getArray("lang", new String[]{"ko"}, "locale");
			
			for (int i = 0; i < localeLanguage.length; i++) {
				resourceMapData.put(localeLanguage[i], CommonUtil.nullTrim((String)req.getParameter(localeLanguage[i])));
			}					
		} else {
			// 문자열 'RES'로 시작하면 DB에 다국어가 저장되어 있다는 의미이므로 다국어를 조회한다. 
			if (resourceId.indexOf("RES") == 0) {
				ResourceVO resourceVO = new ResourceVO();
				resourceVO.setCompId(compId);
				resourceVO.setResourceId(resourceId);			
		
				List<ResourceVO> resourceVOs = resourceService.selectResourceList(resourceVO);
				for (int i = 0; i < resourceVOs.size(); i++) {
					resourceVO = (ResourceVO)resourceVOs.get(i);
					
					resourceMapData.put(resourceVO.getLangType(), resourceVO.getResourceName());
				}
			} else {
				String[] localeLanguage = AppConfig.getArray("lang", new String[]{"ko"}, "locale");
				
				for (int i = 0; i < localeLanguage.length; i++) {
					if (localeLanguage[i].equals(langType)) {
						resourceMapData.put(langType, CommonUtil.nullTrim((String)req.getParameter("initMsg")));
					} else {
						resourceMapData.put(localeLanguage[i], "");
					}
				}			
			}			
		}

		ModelAndView mav = new ModelAndView("ResourceController.showResource");
		
		mav.addObject("isOptionUsed", "true");
		mav.addObject("compId", compId);
		mav.addObject("resourceId", resourceId);
		mav.addObject("conditionValue", CommonUtil.nullTrim((String)req.getParameter("conditionValue")));
		mav.addObject("resourceMapData", resourceMapData);
		
		return mav;
	}
	    
	/**
	 * 
	 * <pre> 
	 * TGW_APP_OPTION 테이블를 사용하는 화면에서만 사용한다.
	 *  다국어 언어를 등록한다.
	 * </pre>
	 * @param req
	 * @return
	 * @throws Exception
	 * @see  
	 * 
	 */
	@RequestMapping("/app/resource/registOptionResource.do")
    public ModelAndView registOptionResource(@RequestBody String resourceArrayData) throws Exception {
		
		String[] localeLanguage = AppConfig.getArray("lang", new String[]{"ko"}, "locale");
		int localeLanguageCount = localeLanguage.length;
		
		JSONArray jsonArray = new JSONArray(resourceArrayData);
		
		// System.out.println("전체 데이터 --> " + jsonArray.toString());
		// System.out.println("갯수  --> " + jsonArray.length());
		
		String compId         = "";
		String resourceId     = "";
		String conditionValue = "";		
		String resourceName   = "";
		String commandType    = "";
		
		int resourceDataCount = 0;
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject resourceMapData = jsonArray.getJSONObject(i);
			
			compId = resourceMapData.getString("compId");
			resourceId = resourceMapData.getString("resourceId");
			conditionValue = resourceMapData.getString("conditionValue");
			
			// 문자열 'RES'로 시작하지 않으면 아직 다국어를 저장하지 않은 경우이므로 unique ID를 생성한다.
			commandType = "update";
			if (resourceId.indexOf("RES") != 0) {
				commandType = "insert";
				resourceId = GuidUtil.getGUID("RES");
			}
			
			int totalSaveCount = 0;
			for (int x = 0; x < localeLanguageCount; x++) {
				resourceName = resourceMapData.getString(localeLanguage[x]);
				
				ResourceVO resourceVO = new ResourceVO();
				
				resourceVO.setCompId(compId);
				resourceVO.setResourceId(resourceId);
				resourceVO.setLangType(localeLanguage[x]);
				resourceVO.setResourceName(resourceName);	
				
				// System.out.println("======= (registResource) ResourceVO --> " + resourceVO.toString());
				
				int saveCount = 0;
				if ("insert".equals(commandType)) {
					saveCount = resourceService.insertResource(resourceVO);
				} else {
					saveCount = resourceService.updateResource(resourceVO);
				}
				totalSaveCount += saveCount;
			}
			
			if (totalSaveCount == localeLanguageCount) {
				resourceDataCount++;
				
				
				// TGW_APP_OPTION 테이블에서 OPTION_VALUE 필드에 다국어 resourceId 값을 설정하고
				// 조건절에서는 OPTION_ID 필드에 conditionValue를 설정하여 업데이트한다.
				UpdateTableVO tableVO = new UpdateTableVO();
				tableVO.setCompId(compId);
				tableVO.setUpdateValue("'" + resourceId + "'");
				tableVO.setConditionValue("'" + conditionValue + "'");
					
				// System.out.println("========= (registResource) insert, UpdateTableVO --> " + tableVO.toString());
				resourceService.updateTableOptionValue(tableVO);
			}
		}
		
		ModelAndView mav = new ModelAndView("ResourceController.AjaxResultResourceJSON");	
		
		if (resourceDataCount == jsonArray.length()) {
			mav.addObject("result", "true");
			// 다국어를 등록했습니다.
			mav.addObject("msg", messageSource.getMessage("resource.msg.inputSuccess", null, new Locale(AppConfig.getCurrentLangType())));
		} else {
			mav.addObject("result", "false");
			// 다국어를 입력시 에러가 발생했습니다.
			mav.addObject("msg", messageSource.getMessage("resource.msg.inputError", null, new Locale(AppConfig.getCurrentLangType())));
		}		
	
		return mav;
	}
	
	/**
	 * 
	 * <pre> 
	 *  TGW_APP_OPTION 테이블를 제외한 다른 테이블에 대응하는 다국어를 조회합니다.
	 * </pre>
	 * @param req
	 * @return
	 * @throws Exception
	 * @see  
	 *
	 */
	@RequestMapping("/app/resource/showResource.do") 
	public ModelAndView showResource(HttpServletRequest req) throws Exception {
		HashMap<String, String> resourceMapData = new HashMap<String, String>();
		
		HttpSession session = req.getSession();
		String compId = (String)session.getAttribute("COMP_ID");		
		String langType = (String)((Locale)session.getAttribute("LANG_TYPE")).getLanguage();	
	
		String resourceId = CommonUtil.nullTrim((String)req.getParameter("resourceId"));
		if (resourceId == null) { resourceId = ""; }
		
		// resourceDataYN=YES인경우 이미 한번 다국어 창에서 다국어를 입력한 경우.
		String resourceDataYN = (String)req.getParameter("resourceDataYN");		
		if ("YES".equals(resourceDataYN)) {
			String[] localeLanguage = AppConfig.getArray("lang", new String[]{"ko"}, "locale");
			
			for (int i = 0; i < localeLanguage.length; i++) {
				resourceMapData.put(localeLanguage[i], CommonUtil.nullTrim((String)req.getParameter(localeLanguage[i])));
			}					
		} else {
			// 문자열 'RES'로 시작하면 DB에 다국어가 저장되어 있다는 의미이므로 다국어를 조회한다. 
			if (resourceId.indexOf("RES") == 0) {
				ResourceVO resourceVO = new ResourceVO();
				resourceVO.setCompId(compId);
				resourceVO.setResourceId(resourceId);			
		
				List<ResourceVO> resourceVOs = resourceService.selectResourceList(resourceVO);
				for (int i = 0; i < resourceVOs.size(); i++) {
					resourceVO = (ResourceVO)resourceVOs.get(i);
					
					resourceMapData.put(resourceVO.getLangType(), resourceVO.getResourceName());
				}
			} else {
				String[] localeLanguage = AppConfig.getArray("lang", new String[]{"ko"}, "locale");
				
				for (int i = 0; i < localeLanguage.length; i++) {
					if (localeLanguage[i].equals(langType)) {
						resourceMapData.put(langType, CommonUtil.nullTrim((String)req.getParameter("initMsg")));
					} else {
						resourceMapData.put(localeLanguage[i], "");
					}
				}			
			}			
		}

		ModelAndView mav = new ModelAndView("ResourceController.showResource");
		
		mav.addObject("isOptionUsed", "false");
		mav.addObject("compId", compId);
		mav.addObject("resourceId", resourceId);
		mav.addObject("updateField", CommonUtil.nullTrim((String)req.getParameter("updateField")));
		mav.addObject("resourceMapData", resourceMapData);
		
	    return mav;
	}
	
	/**
	 * 
	 * <pre> 
	 *  TGW_APP_OPTION 테이블를 제외한 다른 테이블에 대응하는 다국어를 등록 또는 수정합니다.
	 * </pre>
	 * @param resourceArrayData
	 * @return
	 * @throws Exception
	 * @see  
	 *
	 */
	@RequestMapping("/app/resource/registResource.do")
    public ModelAndView registResource(@RequestBody String resourceArrayData) throws Exception {
		
		String[] localeLanguage = AppConfig.getArray("lang", new String[]{"ko"}, "locale");
		int localeLanguageCount = localeLanguage.length;
		
		JSONArray jsonArray = new JSONArray(resourceArrayData);
		
		// System.out.println("전체 데이터 --> " + jsonArray.toString());
		// System.out.println("갯수  --> " + jsonArray.length());
		
		JSONObject tableMapData = jsonArray.getJSONObject(0);
		String tableName      = tableMapData.getString("tableName");
		String updateFields   = tableMapData.getString("updateFields");
		String conditionField = CommonUtil.nullTrim(tableMapData.getString("conditionField")); 
		String conditionValue = CommonUtil.nullTrim(tableMapData.getString("conditionValue"));
		
		HashMap<String, String> updateFieldMap = new HashMap<String, String>();
		
		String[] updateFieldArray = updateFields.split(",");
		for(int i = 0; i < updateFieldArray.length; i++) {
			updateFieldMap.put(updateFieldArray[i].toUpperCase(), updateFieldArray[i].toUpperCase());
			// System.out.println("-- ['" + i + "'] updateField --> " + CommonUtil.nullTrim(updateFieldArray[i]));
		}
		
		String compId       = "";
		String resourceId   = "";
		String updateField  = "";		
		String resourceName = "";
		String commandType  = "";
		
		int resourceDataCount = 0;
		
		for (int i = 1; i < jsonArray.length(); i++) {
			JSONObject resourceMapData = jsonArray.getJSONObject(i);
			
			compId = resourceMapData.getString("compId");
			resourceId = resourceMapData.getString("resourceId");
			updateField = resourceMapData.getString("updateField");
			
			// 문자열 'RES'로 시작하지 않으면 아직 다국어를 저장하지 않은 경우이므로 unique ID를 생성한다.
			commandType = "update";
			if (resourceId.indexOf("RES") != 0) {
				commandType = "insert";
				resourceId = GuidUtil.getGUID("RES");
			}
			
			int totalSaveCount = 0;
			for (int x = 0; x < localeLanguageCount; x++) {
				resourceName = resourceMapData.getString(localeLanguage[x]);
				
				ResourceVO resourceVO = new ResourceVO();
				
				resourceVO.setCompId(compId);
				resourceVO.setResourceId(resourceId);
				resourceVO.setLangType(localeLanguage[x]);
				resourceVO.setResourceName(resourceName);	
				
				// System.out.println("======= (registResource) ResourceVO --> " + resourceVO.toString());
				
				int saveCount = 0;
				if ("insert".equals(commandType)) {
					saveCount = resourceService.insertResource(resourceVO);
				} else {
					saveCount = resourceService.updateResource(resourceVO);
				}
				totalSaveCount += saveCount;
			}
			
			if (totalSaveCount == localeLanguageCount) {
				resourceDataCount++;
				
				// 수정시에도  resourceId를 변경 테이블의 필드에 업데이트한다. 
				// 왜냐하면 수정할때 이미 resourceId 대상 컬럼값이 변경되므로 이전의 수정 프로세스를 찾아서 일일이 수정하는것보다는 
				// 이전 소스 수정없이 하기 위해서 여기서 무조건 업데이트 한다.
				if (conditionField == null) { conditionField = ""; }
				if (conditionValue == null) { conditionValue = ""; }
					
				if (! "".equals(conditionField) && ! "".equals(conditionValue)) {
					UpdateTableVO tableVO = new UpdateTableVO();
					tableVO.setCompId(compId);
					tableVO.setTableName(tableName);
					tableVO.setUpdateField(updateFieldMap.get(updateField.toUpperCase()));
					tableVO.setUpdateValue("'" + resourceId + "'");
					tableVO.setConditionField(conditionField);
					tableVO.setConditionValue("'" + conditionValue + "'");
					
					// System.out.println("========= (registResource) insert, UpdateTableVO --> " + tableVO.toString());
					resourceService.updateTableByResourceId(tableVO);
				}
			}
		}
		
		ModelAndView mav = new ModelAndView("ResourceController.AjaxResultResourceJSON");	
		
		if (resourceDataCount == (jsonArray.length() - 1)) {
			mav.addObject("result", "true");
			// 다국어를 등록했습니다.
			mav.addObject("msg", messageSource.getMessage("resource.msg.inputSuccess", null, new Locale(AppConfig.getCurrentLangType())));
		} else {
			mav.addObject("result", "false");
			// 다국어를 입력시 에러가 발생했습니다.
			mav.addObject("msg", messageSource.getMessage("resource.msg.inputError", null, new Locale(AppConfig.getCurrentLangType())));
		}		
	
		return mav;
	}	
	
	/**
	 * 
	 * <pre> 
	 *  다국어를 삭제합니다.
	 * </pre>
	 * @param req
	 * @return
	 * @throws Exception
	 * @see  
	 *
	 */
	@RequestMapping("/app/resource/deleteResource.do") 
	public ModelAndView deleteResource(HttpServletRequest req) throws Exception {
	
		HttpSession session = req.getSession();
		String compId = (String)session.getAttribute("COMP_ID");		
		
		String resourceId = CommonUtil.nullTrim((String)req.getParameter("resourceId"));
	
		int deleteCount = 1;
		if (resourceId.indexOf("RES") == 0) {
			ResourceVO resourceVO = new ResourceVO();
			
			resourceVO.setCompId(compId);
			resourceVO.setResourceId(resourceId);
			
			deleteCount = resourceService.deleteResource(resourceVO);
		} 
	
		ModelAndView mav = new ModelAndView("ResourceController.AjaxResultResourceJSON");	
		
		if (deleteCount > 0) {
			mav.addObject("result", "true");
			// 다국어를 삭제했습니다.
			mav.addObject("msg", messageSource.getMessage("resource.msg.deleteSuccess", null, new Locale(AppConfig.getCurrentLangType())));
		} else {
			mav.addObject("result", "false");
			// 다국어를 삭제시 에러가 발생했습니다.
			mav.addObject("msg", messageSource.getMessage("resource.msg.deleteError", null, new Locale(AppConfig.getCurrentLangType())));
		}
	
		return mav;
	}		
	
}
