package com.sds.acube.app.common.service.impl;

import java.io.Serializable;

import com.sds.acube.app.common.util.AppCode;
import com.sds.acube.app.common.util.LogWrapper;
import com.sds.acube.app.common.util.MemoryUtil;

/**
 * Class Name  : BaseService.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2011. 3. 24. <br> 수 정  자 : redcomet  <br> 수정내용 :  <br>
 * @author   redcomet 
 * @since  2011. 3. 24.
 * @version  1.0 
 * @see  com.sds.acube.app.common.service.impl.BaseService.java
 */

@SuppressWarnings("serial")
public class BaseService implements Serializable {
    
    /**
	 */
    protected LogWrapper logger = LogWrapper.getLogger("com.sds.acube.app");
    
//  protected Configuration appConfig = AppConfig;
    /**
	 */
    protected AppCode appCode = MemoryUtil.getCodeInstance();
    
    public BaseService() {}

}
