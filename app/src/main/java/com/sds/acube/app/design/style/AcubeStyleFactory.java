package com.sds.acube.app.design.style;

import java.util.*;

/**
 * Class Name  : AcubeStyleFactory.java <br> Description : 설명  <br> Modification Information <br> <br> 수 정  일 : 2012. 5. 23. <br> 수 정  자 : kimside  <br> 수정내용 :  <br>
 * @author   kimside 
 * @since  2012. 5. 23.
 * @version  1.0 
 * @see com.sds.acube.app.design.style.AcubeStyleFactory.java
 */
public class AcubeStyleFactory
{
    /**
	 */
    private static AcubeStyleFactory instance;
    private HashMap<String, AcubeStyle> styles;

    private AcubeStyleFactory() {
        styles = new HashMap<String, AcubeStyle>();
    }

    /**
	 * <pre>  설명 </pre>
	 * @return
	 * @see   
	 */
    public static AcubeStyleFactory getInstance()
    {
        if (instance == null) {
            instance = new AcubeStyleFactory();
        }

        return instance;
    }


    public AcubeStyle getStyle() {
        return getStyle("/app/properties/acube_design.properties");
    }
    public AcubeStyle getStyle(String styleFile)
    {
        AcubeStyle style = (AcubeStyle) styles.get(styleFile);
        if (style == null)
        {
            style = new AcubeStyle(styleFile);
            styles.put(styleFile, style);
        }

        return style;
    }

    public void clear() {
        styles.clear();
    }
}
