package com.sds.acube.app.common.util;import java.util.HashMap;import java.util.Map;import org.apache.commons.httpclient.HttpClient;import org.apache.commons.httpclient.methods.PostMethod;import com.sds.acube.app.env.vo.OptionVO;
/** * Class Name  : AppEnvOption.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br> * @author   kimside  * @since  2012. 5. 23. * @version  1.0  * @see com.sds.acube.app.common.util.AppEnvOption.java */public class AppEnvOption {    private static Map<String, Map<String, OptionVO>> envMap = new HashMap<String, Map<String, OptionVO>>();    /**	 */    private static boolean isLoad = false;        public Map<String, Map<String, OptionVO>> getInstance() {	return envMap;    }            /**     * compId 에 해당하는 옵션맵를 셋팅한다      * @return String     */    public void setOptions(String compId, Map<String, OptionVO> comMap) {	if (compId != null && comMap != null) {	    envMap.put(compId, comMap);	}    }        /**     * compId 에 해당하는 옵션맵를 반환한다      * @return String     */    public Map<String, OptionVO> getOptions(String compId) {	return envMap.get(compId);    }        /**     * compId - optionId 에 해당하는 옵션을 셋팅한다      * @return String     */    public void setOption(String compId, String optionId, OptionVO optionVO) {	if (compId != null && optionId != null && optionVO != null) {	    (envMap.get(compId)).put(optionId, optionVO);	}    }        /**     * compId - optionId 에 해당하는 옵션객체를 반환한다      * @return String     */    public OptionVO getOption(String compId, String optionId) {	Map<String, OptionVO> compMap = envMap.get(compId);	if (compMap == null) {	    return null;	} else {	    return compMap.get(optionId);	}    }        /**     * compId - optionId 에 해당하는 옵션값를 반환한다      * @return String     */    public String getOptionValue(String compId, String optionId) {	Map<String, OptionVO> compMap = envMap.get(compId);	if (compMap == null) {	    return "";	} else {	    OptionVO optionVO = compMap.get(optionId);	    if (optionVO == null) {		return "";	    } else {		return compMap.get(optionId).getUseYn();	    }	}    }    /**     * compId - optionId 에 해당하는 옵션텍스트를 반환한다      * @return String     */    public String getOptionText(String compId, String optionId) {	Map<String, OptionVO> compMap = envMap.get(compId);	if (compMap == null) {	    return "";	} else {	    OptionVO optionVO = compMap.get(optionId);	    if (optionVO == null) {		return "";	    } else {		return optionVO.getOptionValue();	    }	}    }            /**     * compId 에 해당하는 옵션맵를 삭제한다      * @return String     */    public void removeOptions(String compId) {	if (compId != null) {	    envMap.remove(compId);	}    }        /**     * compId - optionId 에 해당하는 옵션맵를 삭제한다      * @return String     */    public void removeOption(String compId, String optionId) {	if (compId != null && optionId != null) {	    Map<String, OptionVO> compMap = envMap.get(compId);	    if (compMap != null) {		compMap.remove(optionId);	    }	}    }            public void reloadOption(String compId) {    	String[] servers = AppConfig.getArray("was_servers", null, "path");    	if (servers != null)  {    		String wasUri = AppConfig.getProperty("was_uri", "", "path");    		for (int loop = 0; loop < servers.length; loop++) {    			String server = servers[loop].trim();    			if (!"".equals(server)) {    				HttpClient client = new HttpClient();    				try {    					PostMethod post = new PostMethod("http://" + server + wasUri + "/memory.update");    					post.addParameter("syncType", "option");    					post.addParameter("compId", compId);    					int status = 0;    					int trycount = 0;    					while (status != 200 && trycount < 3) {    						client.executeMethod(post);    						status = post.getStatusCode();    						trycount++;    					}    				} catch (Exception e) {    				}    			}    		}    	}    }        /**	 * <pre>  설명 </pre>	 * @return	 * @see   	 */    public boolean getIsLoad() {	return isLoad;    }        public void setIsLoad(boolean isLoad) {	AppEnvOption.isLoad = isLoad;    }        public void clear() {	envMap.clear();    }}
