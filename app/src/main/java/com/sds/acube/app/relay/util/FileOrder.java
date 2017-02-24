/**
 * 
 */
package com.sds.acube.app.relay.util;

import java.io.File;
import java.util.Comparator;

/** 
 *  Class Name  : FileOrder.java <br>
 *  Description : 설명  <br>
 *  Modification Information <br>
 *  <br>
 *  수 정  일 : 2012. 6. 11. <br>
 *  수 정  자 : 김상태  <br>
 *  수정내용 :  <br>
 * 
 *  @author  김상태 
 *  @since 2012. 6. 11.
 *  @version 1.0 
 *  @see  com.sds.acube.app.relay.util.FileOrder.java
 */

public class FileOrder implements Comparator<File> {
	
	public int compare(File f1, File f2) {
		int rst = 0;
		if(f1.lastModified() > f2.lastModified()) {
			rst = 1;
		} else if(f1.lastModified() == f2.lastModified()) {
			rst = 0;
		} else {
			rst = -1;
		}
		
		return rst;
	}

}
