/**
 * 
 */
package com.sds.acube.app.common.properties;

import java.io.FileInputStream;
import java.util.Properties;

import org.jconfig.Configuration;

import com.sds.acube.app.common.util.AppConfig;
import com.sds.acube.app.common.util.LogWrapper;

/**
 * Class Name  : LoadProperties.java <br> Description : properties 파일을 로드한다.  <br> Modification Information <br> <br> 수 정  일 : 2011. 7. 29. <br> 수 정  자 : 신경훈  <br> 수정내용 :  <br>
 * @author   skh0204 
 * @since  2011. 7. 29.
 * @version  1.0 
 * @see  com.sds.acube.app.common.properties.LoadProperties.java
 */

public class LoadProperties {
    
    /**
	 */
    protected static LogWrapper logger = LogWrapper.getLogger("com.sds.acube.app");
    private static String filePath = AppConfig.getProperty("propertiesFile", "", "sendSMS");    
    public static Properties properties = null;
    
    public static void loadProperty() throws Exception {
	
	if (properties != null) {
	    return;
	} else {
	    try {
		properties = new Properties();
		properties.load(new FileInputStream(filePath));
	    } catch (Exception e) {
		logger.debug(e.getMessage());
	    }
	}
    }
}
