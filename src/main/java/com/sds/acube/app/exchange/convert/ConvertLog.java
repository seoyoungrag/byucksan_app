package com.sds.acube.app.exchange.convert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.sds.acube.app.common.util.DateUtil;


/**
 * Class Name : ConvertLog.java <br>
 * Description : 설명 <br>
 * Modification Information <br>
 * <br>
 * 수 정 일 : 2011. 7. 18. <br>
 * 수 정 자 : yucea <br>
 * 수정내용 : <br>
 * 
 * @author yucea
 * @since 2011. 7. 18.
 * @version 1.0
 * @see com.sds.acube.app.exchange.convert.ConvertLog.java
 */

public class ConvertLog {

    private static String fileName = "convert.log";
    private File file;


    public ConvertLog() {
	this.file = new File(fileName);
    }


    public void log(String message) {
	try {
	    String currentTime = DateUtil.getCurrentTime();

	    FileWriter writer = new FileWriter(this.file, true);
	    writer.write(message);
	    writer.write("Time : " + currentTime + "\n");
	    writer.flush();
	    writer.close();
	} catch (IOException e) {
	}
    }
}
